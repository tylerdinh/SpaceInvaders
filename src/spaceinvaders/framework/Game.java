package spaceinvaders.framework;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.SwingUtilities;



/**
 * 	The Game class is the base abstract class for a Game in the engine. When you 
 * 	make your own game this is the class that your main class should extend and 
 * 	only two methods are REQUIRED for you to override, the methods onGameStart() 
 * 	and onShutDown(). 
 * 
 * 	The onGameStart() is invoked at the start of the game loop AFTER all inputs 
 * 	and the Game Window itself has been fully setup. You should expect to create 
 * 	all of your Scenes here and add them to the Game's Scene Controller, then 
 * 	tell the Controller which Scene it should enter first. From here the game 
 * 	loop will take over and invoke the inputs, update and render steps on that 
 * 	indicated Scene.
 * 
 * 	The onShutDown() method is invoked after the game loop has ended either 
 * 	because of something in a Scene has triggered this through the shutDownGame() 
 * 	method such as if the player chooses to quit, or the because the close "X" 
 * 	button of the Window was clicked. The most common use for this is if the 
 * 	player tries to close the Window when they have unsaved game progress, in 
 * 	which case the game might ask the player to first save before closing. You 
 * 	will likely not need this method at all and so can be left blank. NOTE: The 
 * 	same method is invoked on the Scene Controller and the currently active 
 * 	Scene as well where this same process can be handled instead.
 * 	
 * 	More information about how this class actually works and how the game loop 
 * 	is implemented by looking through the methods in the GAME LOOP section of 
 * 	the class.
 * 	
 */
public abstract class Game {

	
	/** Default amount of time the game loop sleeps, in milliseconds */
	private static final long DEFAULT_SLEEP = 1L;
	
	
	
	/** Title of the Game */
	private String title;
	
	/** Window for the Game */
	private GameWindow window;
	/** Keyboard device for the Game Window */
	private Keyboard keyboard;
	
	/** Time for the Game and game loop */
	private Time time;
	
	/** Thread that the game loop runs on */
	private Thread gameThread;
	/** True if the game Thead should be running */
	private boolean gameThreadRunning;
	
	/** Amount of delay between iterations of the game loop */
	private long sleepTime;
	
	
	/** Font used to display the frame rate */
	private Font fpsFont;
	/** Color the frame rate is displayed in */
	private Color fpsColor;
	/** True if the frame rate should be displayed on screen */
	private boolean fpsVisible;
	
	
	/** Controller for all Scenes in the Game */
	private SceneController sceneController;
	
	
	
	
	/** Creates a new Game with the given Title */
	public Game( String title )
	{
		this.title = title;
		this.sleepTime = DEFAULT_SLEEP;
		
		this.fpsFont = new Font( "Courier New", Font.PLAIN, 14 );
		this.fpsColor = new Color( 255, 0, 0 );
		this.fpsVisible = true;
		
		this.sceneController = new SceneController();
	}
	
	
	
//==============================================================================
//								LAUNCH		
//==============================================================================
	
	/**
	 * 	Handles initializing and launching the given Game on the Event Dispatch 
	 * 	Thread of the Swing GUI system. This is the only way to fully initialize 
	 * 	and start a Game, as the init() method is private.
	 */
	protected static void launchGame( Game game )
	{
		// Activate the GUI on the event dispatch thread
		SwingUtilities.invokeLater( new Runnable() {
			@Override public void run() {
				game.init();
			}
		});
	}
	
//==============================================================================
//								INITIALIZE
//==============================================================================
	
	/**
	 * 	Initializes the Game, the Game Window, the Keyboard inputs and creates 
	 * 	and starts the game thread that controls the game's loop (input, update, 
	 * 	render & sleep).
	 */
	private void init()
	{
		// Initialize the Game Window
		this.window = new GameWindow();
		this.window.setTitle( this.title );
		this.window.addWindowListener( new WindowAdapter() {
			@Override public void windowClosing( WindowEvent e ) {
				Game.this.gameWindowClosed();
			}
		});
		this.window.init();
		
		// Setup the Keyboard input device and attach it to the 
		//	Game Window and Scene Controller
		this.keyboard = new Keyboard();
		this.window.setKeyboard( this.keyboard );
		this.sceneController.setKeyboard( this.keyboard );
		
		// Start the game Thread. This Thread handles the game loop 
		// seperately from the input Thread so that inputs can be 
		// triggered at any point of the game loop
		this.gameThread = new Thread( new Runnable() {
			@Override public void run() {
				Game.this.initGameThread();
			}
		});
		this.gameThread.start();
	}
	
//==============================================================================
//								GAME THREAD
//==============================================================================
	
	/**
	 * 	Invoked from the game Thread, this method handles the "game loop" of 
	 * 	the game including the start-up and loading steps, followed by the 
	 * 	input, update and render steps, and lastly handles the shutdown step.
	 */
	private void initGameThread()
	{
		// Note that the Thread has started
		this.gameThreadRunning = true;
		
		// Allow the specific game to handle any setup when the game starts,
		//	such as loading Game Scenes, music, Game Objects and other data
		this.onGameStart();
		
		// Start the Timer for game and its frame rate
		this.time = Time.getInstance();
		this.time.init();
		
		// Start the game loop itself
		while ( this.gameThreadRunning )
		{
			// Calculate the elapsed time per frame 
			this.time.calculate();
			double secsPerFrame = this.time.getFrameTime();
			
			// Update the game loop
			this.gameloop( secsPerFrame );
		}
		
		// Game loop has ended, handle shutdown
		this.shutdownGame();
	}
	
	/**
	 * 	Invoked at the start of the game loop this method allows for the specific 
	 * 	game to setup any Scenes and Game Objects it needs to be ready at the start 
	 * 	of the game. 
	 */
	abstract protected void onGameStart();
	
	/**
	 * 	Handles one iteration of the game's loop, this method will process the inputs, 
	 * 	update and render the Scene Controller and its current Scene in the game.
	 */
	private void gameloop( double delta )
	{
		this.processInputs( delta );
		this.update( delta );
		this.render();
		this.sleep( this.sleepTime );
	}
	
	/**
	 * 	Handles processing the Keyboard inputs that occured during the previous 
	 * 	frame. This method will process the Keyboard and then notify the Scene 
	 * 	Controller have the current Scene do the same.
	 */
	private void processInputs( double secsPerFrame )
	{
		this.keyboard.process();
		this.sceneController.processInputs( secsPerFrame );
	}
	
	/** Handles updating the Scene Controller's current Scene in the game */
	private void update( double secsPerFrame )
	{
		this.sceneController.update( secsPerFrame );
	}
	
	/**
	 * 	Handles rendering the current frame of the game. This method can be 
	 * 	intimidating because we are actually handling the Graphics and painting 
	 * 	of the Canvas ourselves in the Game Window. If you go look, we have set 
	 * 	the Canvas to IGNORE REPAINT because we don't want the Canvas to re-draw 
	 * 	everytime the window moves or resized, we want it to draw based on our 
	 * 	game loop. Because of this, there's some background stuff you see here 
	 * 	that we can get into later but not important right now you understand it.
	 */
	private void render()
	{
		// Attempt to render the single frame, if anything fails we 
		//	re-do it all ...
		do 
		{
			// Following loop ensures that the contents of the drawing
			//	buffer are consistent in case the underlying canvas, or
			//	the graphics context, is changed or recreated in some way
			do 
			{
				Graphics2D g = null;
				try {
					// Grab the graphics from the Window's buffer and 
					// render the next frame
					g = this.window.getFrameGraphics();
					this.clearFrame( g );
					this.renderFrame( g );
					
					// Render the frame rate if visible
					if ( this.fpsVisible )
						this.renderFPS( g );
					
				} finally {
					// Dispose of graphics after rendering is done
					if ( g != null )
						g.dispose();
				}
			
			// If buffer contents were restored, this means that it 
			//	was lost during this iteration due to some error outside
			//	of this method and thus we reattempt the render since it
			//	most likely didn't complete
			} while ( this.window.frameContentsRestored() );
			
			// Made it out of first loop then rendering was successful,
			//	now we show the newly drawn buffer
			this.window.showNextFrame();
			
		// If for some reasone the buffer's graphics contents was lost
		// during the rendering or the swapping of buffers, then we need
		// to re-attempt the whole thing
		} while ( this.window.frameContentsLost() );
	}
	
	/**
	 * 	Handles clearing the Game's screen, or Canvas, so that the next frame 
	 * 	can be drawn. If this is not done then you would see all frames of the 
	 * 	Game existing at once creating a smearing affect on the screen.
	 */
	private void clearFrame( Graphics2D g )
	{
		Composite comp = g.getComposite();
		g.setComposite( AlphaComposite.Clear );
		g.fillRect( 0, 0, this.window.getScreenWidth(), this.window.getScreenHeight() );
		g.setComposite( comp );
	}
	
	/**
	 * 	Handles loading in the proper SETTINGS for both quality and performance 
	 * 	reasons and then rendering the Scene Controller's current Scene in the 
	 * 	Game.
	 */
	private void renderFrame( Graphics2D g )
	{
		// Load the Rendering Hint settings so that the graphics are 
		// calculated with performance 
		g.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED );
		g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF );
		
		// Render the current Scene
		this.sceneController.render( g );
	}
	
	/**
	 * 	Handles rendering the current frame rate to the top-left corner of the 
	 * 	screen for testing purposes.
	 */
	private void renderFPS( Graphics2D g )
	{
		// Set the font and color of graphics
		g.setFont( this.fpsFont );
		g.setColor( this.fpsColor );
		
		// Calculate position to draw fps
		// Render the fps
		String fps = "FPS: " + this.time.getFrameRate();
		FontMetrics fm = g.getFontMetrics();
		g.drawString( fps, 20, fm.getAscent() + 5 );
	}
	
	/**
	 * 	Handles pausing the game thread for a specified amount of milliseconds. 
	 * 	This is necessary for the player to be able to see the current frame 
	 * 	on the screen and can be used to control exactly how many frames the 
	 * 	game runs per minute.
	 */
	private void sleep( long ms )
	{
		try { 
			Thread.sleep( ms ); 
		} catch ( InterruptedException e ) { 
			// Nothing to do...
		}
	}
	
//==============================================================================
//								SHUTDOWN	
//==============================================================================
	
	/**
	 * 	Invoked whenever the close button ("X" button) of the Game Window has 
	 * 	been clicked. This method will switch of the game Thread loop which will 
	 * 	ultimately result in the Game and Window closing, but in the proper 
	 * 	order 
	 */
	private void gameWindowClosed()
	{
		this.gameThreadRunning = false;
	}
	
	/**
	 * 	Invoked when the game loop has stopped running. This method will first 
	 * 	handle any last minute details for the game such as saving the player's 
	 * 	progress, and then properly shuts down the Game Window.
	 */
	private void shutdownGame()
	{
		// First have the game handle any shutdown details such as unloading 
		//	assets or saving game data that the player has not saved yet
		this.onShutDown();
		
		// Second give the currently active Scene a shot and handling any other 
		//	shutdown details.
		this.sceneController.onShutDown();
		
		// Finally, close the Window.
		this.window.setVisible( false );
		this.window.dispose();
	}
	
	/**
	 * 	Invoked when the game is shutdown, this allows the specific game to 
	 * 	handle any last minute details before the game stops such as saving 
	 * 	any unsaved progress from the player.
	 */
	abstract protected void onShutDown();
	
//==============================================================================
//							SCENE CONTROLLER
//==============================================================================
	
	/**
	 * 	Returns the Scene Controller that handles all of the Scenes in the game 
	 * 	and how to switch between them.
	 */
	public SceneController getSceneController() 
	{
		return	this.sceneController;
	}
	
//==============================================================================
//								FONTS	
//==============================================================================
	
	/**
	 * 	Handles loading the Font file at the given 
	 */
	public void loadFont( String filepath )
	{
		try {
			File fontFile = new File( filepath );
			Font font = Font.createFont( Font.TRUETYPE_FONT, fontFile );
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont( font );
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
}
