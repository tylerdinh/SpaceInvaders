package spaceinvaders.framework;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;



/**
 * 	The Game Window is the GUI for the Game itself. The Window handles all of 
 * 	the rendering of the Game and the setup of the Game's screen, or Canvas 
 * 	Component, that it is drawn too.
 * 	
 */
public class GameWindow extends JFrame {

		
	
	/** Width of the Window itself */
	private int width;
	/** Height of the Window itself */
	private int height;
	
	
	/** Canvas the Game is drawn on */
	private Canvas canvas;
	
	/** Buffered Strategy used to render to the Canvas */
	private BufferStrategy bufferStrat;
	
	
	/** Keyboard for all Key Events in the Game */
	private Keyboard keyboard;
	
	
	/** True if the Window has been initialized */
	private boolean initialized;
	
	
	
	
	/** Creates a new Game Window */
	public GameWindow()
	{
		this.width = Screen.getWindowWidth();
		this.height = Screen.getWindowHeight();
	}
	
	
	
//==============================================================================
//								INITIALIZE				
//==============================================================================
	
	/** 
	 * 	Initializes the Game's Window, its size and starts the process for 
	 * 	creating all of the Components and their configurations.
	 */
	public void init()
	{
		// Can't initialize a Game Window more than once..
		if ( this.initialized )
			return;
		
		// Set the width and height of the Window
		this.setSize( this.width, this.height );
		
		// Configure all of the Window Components and setup the 
		//	Listeneres for different events
		this.createFrameworkGUI();
	}
	
	/**
	 * 	Handles all of the setup for the Window, the Canvas that the game is  
	 *	drawn onto, the Keyboard inputs (if linked already), and how the game is 
	 *	actually rendered to the Screen.
	 */
	protected void createFrameworkGUI()
	{
		// Setup the Window details
		this.setLocationByPlatform( false );
		this.setLocation( 10, 10 );
		this.setResizable( false );
		this.getContentPane().setLayout( null );
		this.getContentPane().setBackground( new Color(60, 60, 60) );
		this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		
		
		// Setup the Canvas which acts as the "game's" screen, which all 
		//	of the game is drawn too in the Window. A Canvas is a blank 
		//	Component that is only designed to be rendered on, that's it.
		this.canvas = new Canvas();
		this.canvas.setBackground( new Color(0, 0, 0) );
		this.canvas.setIgnoreRepaint( true );
		this.getContentPane().add( this.canvas );
		// This Listener will be notified if the window changes size so 
		//	that the Canvas size will be updated 
		this.getContentPane().addComponentListener( new ComponentAdapter() {
			@Override public void componentResized( ComponentEvent e ) {
				GameWindow.this.onResize();
			}
		} );
		
		
		// If the Keyboard has already been linked, we can add it to 
		//	the Canvas as a Keyboard Listener now...
		if ( this.keyboard != null )
			this.canvas.addKeyListener( this.keyboard );
		
		
		// Start the Window... 
		this.setVisible( true );
		
		
		// ... and setup the double-buffered rendering that ensure 
		//	each frame is rendered IN FULL before its shown to screen
		this.canvas.createBufferStrategy( 2 );
		this.bufferStrat = this.canvas.getBufferStrategy();
		
		
		// Request forcus so Canvas will immediately start receiving 
		//	keyboard inputs...
		this.canvas.requestFocus();
	}
	
//==============================================================================
//								RESIZE				
//==============================================================================
	
	/**
	 * 	Invoked whenever the Game Window itself is resized, this method will 
	 * 	recalculate the size of the Canvas so that it fits perfect to the 
	 * 	inside of the Game Window's content pane.
	 */
	private void onResize()
	{
		// Screen is null, the Window has not been initialized yet 
		//	and thus nothing to do here
		if ( this.canvas == null )
			return;
		
		// Calculate and set the size of the Canvas, or "screen"
		int w = this.getContentPane().getWidth();
		int h = this.getContentPane().getHeight();
		this.canvas.setSize( w, h );
		this.canvas.setLocation( 0, 0 );
		
		// Set the size for the Screen class so that all Game Objects can 
		//	reference an accurate screen size
		Screen.setScreenSize( w, h );
	}
	
//==============================================================================
//								KEYBOARD				
//==============================================================================
	
	/**
	 * 	Sets the Keyboard that the Game Window will use to track key inputs 
	 * 	from the player. This method will properly remove the previous Keyboard 
	 * 	and setup the new Keyboard with the Window's Canvas.
	 */
	public void setKeyboard( Keyboard newKeyboard )
	{
		// Switch out the keyboards
		Keyboard oldKeyboard = this.keyboard;
		this.keyboard = newKeyboard;
		
		// No Canvas yet, then nothing else to do
		if ( this.canvas == null )
			return;
		
		// Otehrwise, disable the old Keyboard and register the new
		if ( oldKeyboard != null )
			this.canvas.removeKeyListener( oldKeyboard );
		if ( newKeyboard != null )
			this.canvas.addKeyListener( newKeyboard );
	}
	
//==============================================================================
//								GRAPHICS				
//==============================================================================
	
	/**
	 * 	Returns the most recent render Graphics from the Window's buffer 
	 * 	strategy that can be used to render things to the Game's Screen, or 
	 * 	Canvas.
	 */
	protected Graphics2D getFrameGraphics()
	{
		return	(Graphics2D) this.bufferStrat.getDrawGraphics();
	}
	
	/**
	 * 	Returns true if the drawing buffer was recently restored from a lost 
	 * 	state. If this has occured the buffer would be reset to the default 
	 * 	background color (white) and the current frame would need to be redrawn.
	 */
	protected boolean frameContentsRestored()
	{
		return	this.bufferStrat.contentsRestored();
	}
	
	/**
	 * 	Makes the most recently drawn frame visible on the Window's screen, or 
	 * 	Canvas.
	 */
	protected void showNextFrame()
	{
		this.bufferStrat.show();
	}
	
	/**
	 * 	Returns true if the current drawing buffer was lost. This can occur 
	 * 	for several reasons when you are handling custom rendering, this is 
	 * 	used by the game loop to know when it should re-draw the current frame.
	 * 	Once a lost buffer is restored it will be reset to its default color 
	 * 	and nothing that was previously drawn to it will be present.
	 */
	protected boolean frameContentsLost()
	{
		return	this.bufferStrat.contentsLost();
	}
	
//==============================================================================
//								SCREEN				
//==============================================================================
	
	/** Returns the current width of the Game's screen, or Canvas */
	public int getScreenWidth()
	{
		if ( this.canvas == null )
			return -1;
		
		return this.canvas.getWidth();
	}
	
	/** Returns the current height of the Game's screen, or Canvas */
	public int getScreenHeight()
	{
		if ( this.canvas == null )
			return -1;
		
		return this.canvas.getHeight();
	}
	
}
