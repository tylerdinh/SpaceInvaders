package spaceinvaders.framework;



/**
 * 	The Screen class is a static class that stores details for the Game Window's 
 * 	size AND the Game's screen, or Canvas component, that the game is drawn on 
 * 	inside the Window. This gives all Scenes and Game Objects a place to access 
 * 	the current Screen size so that they can determine when they are, or are 
 * 	not on Screen, or how big the Game's screen is in general. This class also 
 * 	contains a method that will determine if a Game Obejct is still on screen or 
 * 	not based on that's Object's Bounds.
 * 	
 */
public class Screen {

	
	/** Default width of the Game Window */
	private static final int DEFAULT_WIDTH = 1300;
	/** Default height of the Game Window */
	private static final int DEFAULT_HEIGHT = 700;
	
	
	
	/** Current width of the Game Window */
	private static int windowWidth = DEFAULT_WIDTH;
	/** Current height of the Game Window */
	private static int windowHeight = DEFAULT_HEIGHT;
	
	/** Current width of the screen the Game is drawn on */
	private static int screenWidth;
	/** Current height of the screen the Game is drawn on */
	private static int screenHeight;
	
	/** Bounds for the Screen used to determine when Objects are screen */
	private static Bounds screenBounds = new Bounds( 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT );
	
	
	
	
//==============================================================================
//							SCREEN				
//==============================================================================
	
	public static boolean isOffScreen(GameObject obj) {
		return !screenBounds.contains(obj.getBounds());
	}
	
	/**
	 * 	Returns true if the given Game Object is currently on the screen. An 
	 * 	Object is on screen if its Bounds intserct the Screen's Bounds at all. 
	 * 	If the Bounds do not intersect then the Game Object is not shown on the 
	 * 	game's Screen and can be dealt with accordingly.
	 */
	public static boolean isOnScreen( GameObject obj )
	{
		return	screenBounds.intersects( obj.getBounds() );
	}
	
	/**
	 * 	Returns true if the given Vector coordinate would be visible on Screen. 
	 * 	A point is on screen if it is within the Screen's Bounds.
	 */
	public static boolean isOnScreen( Vector point )
	{
		return	screenBounds.contains( point );
	}
	
	/** 
	 * 	Used by the Game and/or Game Window to update the current size of the 
	 * 	Screen Width so that the rest of the game can use it if necessary.
	 */
	protected static void setScreenSize( int w, int h )
	{
		screenWidth = w;
		screenHeight = h;
		screenBounds = new Bounds( 0, 0, w, h );
	}
	
	/**
	 * 	Returns the current width of the canvas, or "screen", that the Game is 
	 * 	drawn on. This can be used for Game Objects or anything controlling them 
	 *	to figure out if an Object is currently "on screen" or not.
	 */
	public static int getScreenWidth()
	{
		return	screenWidth;
	}
	
	/**
	 * 	Returns the current height of the canvas, or "screen", that the Game is 
	 * 	drawn on. This can be used for Game Objects or anything controlling them 
	 *	to figure out if an Object is currently "on screen" or not.
	 */
	public static int getScreenHeight()
	{
		return	screenHeight;
	}
	
//==============================================================================
//							WINDOW				
//==============================================================================
	
	/**
	 * 	Returns the current width of the Game Window itself. This includes the 
	 * 	game screen, the menu bar along the top of the Window, and the Window's 
	 * 	boreder.
	 */
	public static int getWindowWidth()
	{
		return	windowWidth;
	}
	
	/**
	 * 	Returns the current height of the Game Window itself. This includes the 
	 * 	game screen, the menu bar along the top of the Window, and the Window's 
	 * 	boreder.
	 */
	public static int getWindowHeight()
	{
		return	windowHeight;
	}
	
}