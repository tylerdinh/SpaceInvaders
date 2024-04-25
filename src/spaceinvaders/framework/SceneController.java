package spaceinvaders.framework;

import java.awt.Graphics2D;
import java.util.ArrayList;



/**
 * 	The Scene Controller is what manages all of the different Scens of the Game, 
 * 	and is the middle-man between the Game Engine itself and all of the different 
 * 	Scenes. The Controller handles which Scene should be active in the Game and 
 * 	passes all game loop calls (inputs, update and render) to that Scene when 
 * 	invoked by the game loop. 
 * 
 * 	Once a Scene is added to the Controller it can be set as the currently active 
 * 	Scene at any point by referencing its name to the Controller. Once set, the 
 * 	currently active Scene (if one exists) will be exited first. Then, if the 
 * 	given Scene exists then it will be entered. These methods for Scenes handle 
 * 	what should be done at the start and end of the Scene. 
 * 	
 */
public class SceneController {

	
	
	/** All Scenes currently added to the Controller */
	private ArrayList<Scene> scenes;
	/** Currently active Scene in the Game */
	private Scene currentScene;
	
	/** Keyboard device for the Game */
	private Keyboard keyboard;
	
	
	
	
	/** Creates a new Scene Controller used to manage all Scenes in the Game */
	public SceneController()
	{
		this.scenes = new ArrayList<Scene>();
	}
	
	
	
//==============================================================================
//							GAME LOOP	
//==============================================================================
	
	/**
	 * 	Processes the inputs on the currently active Scene, if there is one. 
	 * 	This method will pass the Controller's Keyboard to the Scene so that it 
	 * 	can check for any necessary inputs.
	 */
	public void processInputs( double secsPerFrame )
	{
		if ( this.currentScene != null )
			this.currentScene.processInputs( secsPerFrame, this.keyboard );
	}
	
	/**
	 * 	Updates the currently active Scene, if there is one. This method calls 
	 * 	update on the current Scene to have it update all of its Game Objects 
	 * 	and other details.
	 */
	public void update( double secsPerFrame )
	{
		if ( this.currentScene != null )
			this.currentScene.update( secsPerFrame );
	}
	
	/**
	 * 	Renders the currently active Scene to the given Graphics, if there is 
	 * 	one. This method calls render on the current Scene to have it render 
	 * 	all of its Game Objects to the Graphics for the Game's screen.
	 */
	public void render( Graphics2D g )
	{
		if ( this.currentScene != null )
			this.currentScene.render( g );
	}
	
	/**
	 * 	Invoked whenever the game has been shutdown either from within game by 
	 * 	the player or by closing the Game Window itself. This method will give 
	 * 	the currently active Scene a chance to handle any details necessary such 
	 * 	as saving game progress before the game finally ends.
	 */
	public void onShutDown()
	{
		if ( this.currentScene != null )
			this.currentScene.onShutDown();
	}
	
//==============================================================================
//							CURRENT SCENE
//==============================================================================
	
	/**
	 * 	Sets the currently active Scene to the one with the given name, if it 
	 * 	exists in this Controller. If the given Scene does not exist or the 
	 * 	given name is null this method will effectively remove the current 
	 * 	Scene.
	 */
	public void setCurrentScene( String name )
	{
		// Exit the current Scene
		if ( this.currentScene != null )
			this.currentScene.exit();
		
		// Set the new current Scene and enter it 
		//	if it exists...
		this.currentScene = this.getScene( name );
		if ( this.currentScene != null )
			this.currentScene.enter();
	}
	
	/**
	 * 	Returns the currently active Scene for this Scene Controller. When the 
	 * 	game processes inputs, updates game objects and renders the current 
	 * 	frame this is done on the currently active Scene only.
	 */
	public Scene getCurrentScene()
	{
		return	this.currentScene;
	}
	
	/**
	 * 	Returns the name of the currently active Scene for this Scene Controller. 
	 * 	When the game processes inputs, updates game objects and renders the 
	 * 	current frame this is done on the currently active Scene only. if there 
	 * 	is not a current Scene then this method returns null.
	 */
	public String getCurrentSceneName()
	{
		if ( this.currentScene == null )
			return null;
		
		return this.currentScene.getName();
	}
	
//==============================================================================
//							SCENES		
//==============================================================================
	
	/**
	 * 	Adds the given Scene to this Scene Controller and attaches the Controler 
	 * 	to the Scene
	 */
	public void addScene( Scene scene )
	{
		this.scenes.add( scene );
		scene.attachSceneController( this );
	}
	
	/**
	 * 	Removes the given Scene from this Scene Controller and detaches the 
	 * 	Controller from the Scene.
	 */
	public void removeScene( Scene scene )
	{
		this.scenes.remove( scene );
		scene.detachSceneController( this );
	}
	
	/**
	 * 	Removes and returns all Scenes from this Scene Controller and detaches  
	 * 	the Controller from each Scene.
	 */
	public ArrayList<Scene> removeAllScenes()
	{
		ArrayList<Scene> removed = new ArrayList<Scene>();
		removed.addAll( this.scenes );
		for ( Scene scene : removed )
			this.removeScene( scene );
		return removed;
	}
	
	/**
	 * 	Returns the Scene in this Controller by the given name, or null if no 
	 * 	Scene by this name exists.
	 */
	public Scene getScene( String name )
	{
		for ( Scene scene : this.scenes )
			if ( scene.isNamed( name ) )
				return scene;
		return null;
	}
	
	/** Returns a list of all Scenes in this Scene Controller */
	public ArrayList<Scene> getAllScenes()
	{
		ArrayList<Scene> allScenes = new ArrayList<Scene>();
		allScenes.addAll( this.scenes );
		return allScenes;
	}
	
	/** Returns the total number of Scenes in this Scene Controller */
	public int getTotalScenes()
	{
		return	this.scenes.size();
	}
	
//==============================================================================
//							KEYBOARD
//==============================================================================
	
	/**
	 * 	Returns the Keyboard device that tracks all keyboard events in the Game. 
	 * 	This Keyboard can be used to determine which keys have been pressed, 
	 * 	held or released each frame along with other types of keyboard events.
	 */
	public Keyboard getKeyboard()
	{
		return	this.keyboard;
	}
	
	/**
	 * 	Sets the Keyboard device that tracks all keyboard events in the Game. 
	 * 	This Keyboard can be used to determine which keys have been pressed, 
	 * 	held or released each frame along with other types of keyboard events.
	 */
	public void setKeyboard( Keyboard keys )
	{
		this.keyboard = keys;
	}
	
}
