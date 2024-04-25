package spaceinvaders.framework;

import java.awt.Graphics2D;



/**
 * 	A Scene is the base class for all of the different parts that make up a 
 * 	game such as a Titlescreen, a Pause Screen, or different game world Scenes 
 * 	like an overworld or an underworld. Scenes exist in the Game's Scene 
 * 	Controller which ALL Scenes are given access to when they are added to a 
 * 	Scene Controller. All Scenes also have access to the Keyboard device that 
 * 	the Game uses for keyboard inputs so that each Scene can properly process 	
 * 	inputs during that step of the game loop.
 * 
 * 	There is always one Scene that is considered the currently active Scene and 
 * 	will be the Scene that all inputs, updates and rendering is done for in the 
 * 	Scene Controller. When a Scene is set as the currently active Scene in the 
 * 	Scene Controller the previous Scene will first be exited and the new Scene 
 * 	entered. At any point any Scene can access the State Controller and have 
 * 	another Scene entered. For example, the Titlescreen Scene may have the Game 
 * 	Select Screen entered whenever the player advances past the final stage of 
 * 	the Title Screen.
 * 	
 */
public abstract class Scene {

	
	/** Name of the Scene */
	private String name;
	
	/** Scene Controller that this Scene belongs too */
	private SceneController controller;
	
	
	
	
	/** Creates a new Scene with the given name */
	public Scene( String name )
	{
		this.name = name;
	}
	
	
	
//==============================================================================
//							SCENE CONTROLLER
//==============================================================================
	
	/**
	 * 	Used by the Scene Controller to attach itself to this Scene when it is 
	 * 	added to the Controller. Scenes need access to their Controller so that 
	 * 	they can decide when the Game should move to a new Scene. The Controller 
	 * 	also contains global Scene information that can be loaded and retrieved 
	 * 	by key-names between Scenes. For example, the player's character may be 
	 * 	stored in the Controller so that multiple Scenes have access to the same 
	 * 	player Game Object.
	 */
	protected void attachSceneController( SceneController controller )
	{
		// Same Controller as before then ignore...
		if ( this.controller == controller ) 
			return;
		
		// Remove ourselves from the old Controller
		if ( this.controller != null )
			this.controller.removeScene( this );
		
		// Save the new Controller
		this.controller = controller;
	}
	
	/**
	 * 	Invoked by the Scene Controller when this Scene is removed from it. This 
	 * 	method will detach the given Scene Controller from the Scene if it is 
	 * 	the Scene's current Controller. If the Controller is not the Scene's 
	 * 	current Controller then this method does nothing.
	 */
	protected void detachSceneController( SceneController controller )
	{
		if ( this.controller == controller )
			this.controller = null;
	}
	
	/**
	 * 	Used by the Scene Controller to attach itself to this Scene when it is 
	 * 	added to the Controller. Scenes need access to their Controller so that 
	 * 	they can decide when the Game should move to a new Scene. The Controller 
	 * 	also contains global Scene information that can be loaded and retrieved 
	 * 	by key-names between Scenes. For example, the player's character may be 
	 * 	stored in the Controller so that multiple Scenes have access to the same 
	 * 	player Game Object.
	 */
	public SceneController getSceneController()
	{
		return	this.controller;
	}
	
//==============================================================================
//							LOAD
//==============================================================================
	
	/**
	 * 	Handles loading any Scene specific objects and details before the Scene 
	 * 	is ever entered. This should be used when a Scene has a bunch of data 
	 * 	to load suchs and images or sounds that may delay the start of the Scene 
	 * 	when its entered.
	 */
	abstract public void load();
	
	/**
	 * 	Handles unloading all of the Scene specific objects and details from 
	 * 	the Scene. This should be used to dispose of any files and data that 
	 * 	the Game no longer needs because this Scene is likely to not be entered 
	 * 	again, or at least not for awhile.
	 */
	abstract public void unload();
	
//==============================================================================
//							ENTER/EXIT
//==============================================================================
	
	/**
	 * 	Invoked by the Scene Controller when this Scene becomes the currently 
	 * 	active Scene in the Game.
	 */
	abstract public void enter();
	
	/**
	 * 	Invoked by the Scene Controller when this Scene is no longer the 
	 * 	currently active Scene in the game.
	 */
	abstract public void exit();
	
//==============================================================================
//							GAME LOOP	
//==============================================================================
	
	/**
	 * 	Invoked by the Scene Controller to have this Scene process all necessary 
	 * 	inputs from the given Keyboard when this Scene is the currently active 
	 * 	Scene. The specified secsPerFrame respresents the time in seconds that 
	 * 	has passed since the last call to processInputs() was made which can be 
	 * 	used for time-sensitive inputs and calculations.
	 */
	abstract public void processInputs( double secsPerFrame, Keyboard keys );
	
	/**
	 * 	Invoked by the Scene Controller to have this Scene update all of its 
	 * 	Game Objects and other details when this Scene is the currently active 
	 * 	Scene. The specified secsPerFrame respresents the time in seconds that 
	 * 	has passed since the last call to update() was made which can be used 
	 * 	for time-sensitive updates and calculations.
	 */
	abstract public void update( double secsPerFrame );
	
	/**
	 * 	Invoked by the Scene Controller to have this Scene render all of its 
	 * 	Game Objects and other details to the screen when this Scene is the 
	 * 	currently active Scene. The given Graphics instance is the graphics 
	 * 	from the Game Window's screen that displays the game in the Window.
	 */
	abstract public void render( Graphics2D g );
	
	/**
	 * 	Invoked by the Scene Controller whenever the game has been shutdown. 
	 * 	This method gives the Scene a chance to handle any final details that 
	 * 	need to be taken care of before the game ends such as saving any of 
	 * 	the player's progress so they don't lose it.
	 */
	abstract public void onShutDown();
	
//==============================================================================
//							KEYBOARD
//==============================================================================
	
	/**
	 * 	Returns the Keyboard device from the Scene's State Controller, if it 
	 * 	has one. If this Scene does not have an attached Scene Controller this 
	 * 	method will return null.
	 */
	public Keyboard getKeyboard()
	{
		if ( this.controller == null )
			return null;
		
		return this.controller.getKeyboard();
	}
	
//==============================================================================
//							NAME
//==============================================================================
	
	/** Returns the name of this Scene */
	public String getName()
	{
		return	this.name;
	}
	
	/** Sets the name of this Scene */
	public void setName( String name )
	{
		this.name = name;
	}
	
	/** Returns true if this Scene has the given name */
	public boolean isNamed( String name )
	{
		return	this.name != null && this.name.equals( name );
	}
	
}
