package spaceinvaders;

import spaceinvaders.framework.Game;
import spaceinvaders.framework.SceneController;
import spaceinvaders.game.GameplayScene;
import spaceinvaders.game.TitlescreenScene;



/**
 * 	The Space Invaders Game itself, this class is the top-level class and houses 
 * 	the main method. This class on start up will handle creating all of the 
 * 	different Scenes in the Game and then enters the first one. From there, the 
 * 	parent Game class handles the rest of process and the different Scenes is 
 * 	where the real code will go. In a much more complicated Game than ours this 
 * 	class would have significantly more work to do.
 * 	
 */
public class SpaceInvaders extends Game {
	
	
	
	/** Creates a new Space Invaders Game */
	public SpaceInvaders()
	{
		super( "Space Invaders!" );
	}
	
	
	
//==============================================================================
//							LAUNCH
//==============================================================================
	
	/**
	 * 	This is the top-level class of our Game and thus this is where we would 
	 * 	really have the main() method instead of some other "PlayXxx" class like 
	 * 	I've had you do in the past. This main method should create the Space 
	 * 	Invaders Game and properly launch it by invoking the launchGame() method. 
	 * 	This method handles the full setup of the Game, its Game Window, and 
	 * 	starting the game loop. (See the Game class launchGame() method for more 
	 * 	information).
	 */
	public static void main( String[] args )
	{
		SpaceInvaders game = new SpaceInvaders();
		Game.launchGame(game);
		// TODO: Create the game and properly launch it.
	}
	
//==============================================================================
//							START UP		
//==============================================================================
	
	/**
	 * 	Invoked from the Game class at the start of the game loop but after the 
	 * 	Game and its Game Window has already been created and initialized. This 
	 * 	method should be used to create ALL of the different Scenes in the Game 
	 * 	as well as setting up any other details that need to be setup before the 
	 * 	game enters its first Scene. Once the start-up is complete this method 
	 * 	should access the Scene Controller and have it enter the Game's first 
	 * 	Scene by name, whatever Scene that is (for example, the TitlescreenScene).
	 */
	@Override protected void onGameStart()
	{
		// Load the game fonts we want to add 
		this.loadFont( "res/fonts/Arcade.ttf" );
		this.loadFont( "res/fonts/Space Invaders.ttf" );
		
		
		TitlescreenScene titlescreen = new TitlescreenScene();
		GameplayScene gamescreen = new GameplayScene();
		
		// TODO: Create all of the different Scenes in the Game, enter the first one
		SceneController controller = this.getSceneController();
		controller.addScene(titlescreen);
		controller.addScene(gamescreen);
		
		controller.setCurrentScene("Titlescreen");
	}
	
//==============================================================================
//							SHUT DOWN		
//==============================================================================
	
	/**
	 * 	Invoked at the end of the game loop when the Game has been shutdown, 
	 * 	either because some in Game event triggered it or because the player 
	 * 	clicked the close button on the Window. This is where we would handle 
	 * 	things such as un-saved game data before the game finally terminates. 
	 * 	NOTE: This method is also invoked on the currently active State in the 
	 * 	State Controller which is where we would probably handle most of this. 
	 * 	Likely for you, this method will remain blank until you create a much 
	 * 	more complicated Game then we will in class.
	 */
	@Override protected void onShutDown()
	{
		// Nothing to do ...
	}
	
}
