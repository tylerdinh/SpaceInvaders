package spaceinvaders.framework;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;



/**
 * 	A Keyboard is the Key Listener for the game framework that handles incoming
 * 	Key Events from the Event Dispatch Thread and makes them thread-safe for the
 * 	game loop Thread. This Keyboard should be made the Key Listener for the 
 * 	Component of the window that the game's screen is drawn on in order to receive 
 * 	all key events. It is a requirement of the game loop Thread that accesses this
 * 	Keyboard to first poll() the Keyboard before it attempts to check if any key
 * 	inputs have occured. Polling the keyboard starts the thread-safe process of
 * 	taking all key Events that occurred the previous frame using them to update
 * 	the necessary input flags and counters so that the game can determine a wide
 * 	range of key inputs. If the game loop fails to poll() first, then the 
 * 	subsequent inputs processed by the game will be inaccurate, if detected at all.
 * 	
 * 	The Keyboard class gives the game the ability to determine which frame a key
 * 	was pressed or released, if a key is held down, and what modifiers if any were
 * 	active at the time, as well as access all of these Keys in order of which they
 * 	occurred.
 * 	
 */
public class Keyboard implements KeyListener {

	
	
	/** Key code number for an undefined key */
	public static final int UNDEFINED 			= KeyEvent.VK_UNDEFINED;
	
	/** Key code number for the CANCEL key */
	public static final int CANCEL 				= KeyEvent.VK_CANCEL;
	/** Key code number for the BACK SPACE key */
	public static final int BACK_SPACE		 	= KeyEvent.VK_BACK_SPACE;
	/** Key code number for the TAB key */
	public static final int TAB 				= KeyEvent.VK_TAB;
	/** Key code number for the ENTER key */
	public static final int ENTER 				= KeyEvent.VK_ENTER;
	/** Key code number for the CLEAR key */
	public static final int CLEAR 				= KeyEvent.VK_CLEAR;
	/** Key code number for the SHIFT key */
	public static final int SHIFT 				= KeyEvent.VK_SHIFT;
	/** Key code number for the CONTROL key */
	public static final int CONTROL 			= KeyEvent.VK_CONTROL;
	/** Key code number for the ALT key */
	public static final int ALT 				= KeyEvent.VK_ALT;
	/** Key code number for the PAUSE key */
	public static final int PAUSE 				= KeyEvent.VK_PAUSE;
	/** Key code number for the CAPS LOCK key */
	public static final int CAPS_LOCK			= KeyEvent.VK_CAPS_LOCK;
	/** Key code number for the ESCAPE key */
	public static final int ESCAPE 				= KeyEvent.VK_ESCAPE;
	/** Key code number for the SPACE key */
	public static final int SPACE 				= KeyEvent.VK_SPACE;
	/** Key code number for the PAGE UP key */
	public static final int PAGE_UP 			= KeyEvent.VK_PAGE_UP;
	/** Key code number for the PAGE DOWN key */
	public static final int PAGE_DOWN			= KeyEvent.VK_PAGE_DOWN;
	/** Key code number for the END key */
	public static final int END					= KeyEvent.VK_END;
	/** Key code number for the HOME key */
	public static final int HOME				= KeyEvent.VK_HOME;

	/** Key code number for the LEFT arrow key */
	public static final int LEFT				= KeyEvent.VK_LEFT;
	/** Key code number for the UP arrow key */
	public static final int UP					= KeyEvent.VK_UP;
	/** Key code number for the RIGHT arrow key */
	public static final int RIGHT				= KeyEvent.VK_RIGHT;
	/** Key code number for the DOWN arrow key */
	public static final int DOWN				= KeyEvent.VK_DOWN;
	
	/** Key code number for the COMMA "," key */
	public static final int COMMA				= KeyEvent.VK_COMMA;
	/** Key code number for the MINUS "-" key */
	public static final int MINUS				= KeyEvent.VK_MINUS;
	/** Key code number for the PERIOD "." key */
	public static final int PERIOD				= KeyEvent.VK_PERIOD;
	/** Key code number for the SLASH "/" key */
	public static final int SLASH				= KeyEvent.VK_SLASH;
	
	/** Key code number for the '0' key */
	public static final int DIGIT_0				= KeyEvent.VK_0;
	/** Key code number for the '1' key */
	public static final int DIGIT_1				= KeyEvent.VK_1;
	/** Key code number for the '2' key */
	public static final int DIGIT_2				= KeyEvent.VK_2;
	/** Key code number for the '3' key */
	public static final int DIGIT_3				= KeyEvent.VK_3;
	/** Key code number for the '4' key */
	public static final int DIGIT_4				= KeyEvent.VK_4;
	/** Key code number for the '5' key */
	public static final int DIGIT_5				= KeyEvent.VK_5;
	/** Key code number for the '6' key */
	public static final int DIGIT_6				= KeyEvent.VK_6;
	/** Key code number for the '7' key */
	public static final int DIGIT_7				= KeyEvent.VK_7;
	/** Key code number for the '8' key */
	public static final int DIGIT_8				= KeyEvent.VK_8;
	/** Key code number for the '9' key */
	public static final int DIGIT_9				= KeyEvent.VK_9;
	
	/** Key code number for the SEMICOLON ";" key */
	public static final int SEMICOLON			= KeyEvent.VK_SEMICOLON;
	/** Key code number for the EQUALS "=" key */
	public static final int EQUALS				= KeyEvent.VK_EQUALS;
	
	/** Key code number for the letter 'A' key */
	public static final int A					= KeyEvent.VK_A;
	/** Key code number for the letter 'B' key */
	public static final int B					= KeyEvent.VK_B;
	/** Key code number for the letter 'C' key */
	public static final int C					= KeyEvent.VK_C;
	/** Key code number for the letter 'D' key */
	public static final int D					= KeyEvent.VK_D;
	/** Key code number for the letter 'E' key */
	public static final int E					= KeyEvent.VK_E;
	/** Key code number for the letter 'F' key */
	public static final int F					= KeyEvent.VK_F;
	/** Key code number for the letter 'G' key */
	public static final int G					= KeyEvent.VK_G;
	/** Key code number for the letter 'H' key */
	public static final int H					= KeyEvent.VK_H;
	/** Key code number for the letter 'I' key */
	public static final int I					= KeyEvent.VK_I;
	/** Key code number for the letter 'J' key */
	public static final int J					= KeyEvent.VK_J;
	/** Key code number for the letter 'K' key */
	public static final int K					= KeyEvent.VK_K;
	/** Key code number for the letter 'L' key */
	public static final int L					= KeyEvent.VK_L;
	/** Key code number for the letter 'M' key */
	public static final int M					= KeyEvent.VK_M;
	/** Key code number for the letter 'N' key */
	public static final int N					= KeyEvent.VK_N;
	/** Key code number for the letter 'O' key */
	public static final int O					= KeyEvent.VK_O;
	/** Key code number for the letter 'P' key */
	public static final int P					= KeyEvent.VK_P;
	/** Key code number for the letter 'Q' key */
	public static final int Q					= KeyEvent.VK_Q;
	/** Key code number for the letter 'R' key */
	public static final int R					= KeyEvent.VK_R;
	/** Key code number for the letter 'S' key */
	public static final int S					= KeyEvent.VK_S;
	/** Key code number for the letter 'T' key */
	public static final int T					= KeyEvent.VK_T;
	/** Key code number for the letter 'U' key */
	public static final int U					= KeyEvent.VK_U;
	/** Key code number for the letter 'V' key */
	public static final int V					= KeyEvent.VK_V;
	/** Key code number for the letter 'W' key */
	public static final int W					= KeyEvent.VK_W;
	/** Key code number for the letter 'X' key */
	public static final int X					= KeyEvent.VK_X;
	/** Key code number for the letter 'Y' key */
	public static final int Y					= KeyEvent.VK_Y;
	/** Key code number for the letter 'Z' key */
	public static final int Z					= KeyEvent.VK_Z;
	
	/** Key code number for the OPEN BRACKET key */
	public static final int OPEN_BRACKET		= KeyEvent.VK_OPEN_BRACKET;
	/** Key code number for the BACK SLASH key */
	public static final int BACK_SLASH			= KeyEvent.VK_BACK_SLASH;
	/** Key code number for the CLOSE BRACKET key */
	public static final int CLOSE_BRACKET		= KeyEvent.VK_CLOSE_BRACKET;
	
	/** Key code number for the Numpad '0' key */
	public static final int NUMPAD_0			= KeyEvent.VK_NUMPAD0;
	/** Key code number for the Numpad '1' key */
	public static final int NUMPAD_1			= KeyEvent.VK_NUMPAD1;
	/** Key code number for the Numpad '2' key */
	public static final int NUMPAD_2			= KeyEvent.VK_NUMPAD2;
	/** Key code number for the Numpad '3' key */
	public static final int NUMPAD_3			= KeyEvent.VK_NUMPAD3;
	/** Key code number for the Numpad '4' key */
	public static final int NUMPAD_4			= KeyEvent.VK_NUMPAD4;
	/** Key code number for the Numpad '5' key */
	public static final int NUMPAD_5			= KeyEvent.VK_NUMPAD5;
	/** Key code number for the Numpad '6' key */
	public static final int NUMPAD_6			= KeyEvent.VK_NUMPAD6;
	/** Key code number for the Numpad '7' key */
	public static final int NUMPAD_7			= KeyEvent.VK_NUMPAD7;
	/** Key code number for the Numpad '8' key */
	public static final int NUMPAD_8			= KeyEvent.VK_NUMPAD8;
	/** Key code number for the Numpad '9' key */
	public static final int NUMPAD_9			= KeyEvent.VK_NUMPAD9;
	
	/** Key code number for the MULTIPLY key on the Numpad */
	public static final int MULTIPLY			= KeyEvent.VK_MULTIPLY;
	/** Key code number for the ADD key on the Numpad */
	public static final int ADD					= KeyEvent.VK_ADD;
	/** Key code number for the SEPERATOR "," key on the Numpad */
	public static final int SEPARATOR			= KeyEvent.VK_SEPARATOR;
	/** Key code number for the SUBTRACT key on the Numpad */
	public static final int SUBTRACT			= KeyEvent.VK_SUBTRACT;
	/** Key code number for the DECIMAL key on the Numpad */
	public static final int DECIMAL				= KeyEvent.VK_DECIMAL;
	/** Key code number for the DIVIDE key on the Numpad */
	public static final int DIVIDE				= KeyEvent.VK_DIVIDE;
	
	/** Key code number for the F1 function key */
	public static final int F1					= KeyEvent.VK_F1;
	/** Key code number for the F2 function key */
	public static final int F2					= KeyEvent.VK_F2;
	/** Key code number for the F3 function key */
	public static final int F3					= KeyEvent.VK_F3;
	/** Key code number for the F4 function key */
	public static final int F4					= KeyEvent.VK_F4;
	/** Key code number for the F5 function key */
	public static final int F5					= KeyEvent.VK_F5;
	/** Key code number for the F6 function key */
	public static final int F6					= KeyEvent.VK_F6;
	/** Key code number for the F7 function key */
	public static final int F7					= KeyEvent.VK_F7;
	/** Key code number for the F8 function key */
	public static final int F8					= KeyEvent.VK_F8;
	/** Key code number for the F9 function key */
	public static final int F9					= KeyEvent.VK_F9;
	/** Key code number for the F10 function key */
	public static final int F10					= KeyEvent.VK_F10;
	/** Key code number for the F11 function key */
	public static final int F11					= KeyEvent.VK_F11;
	/** Key code number for the F12 function key */
	public static final int F12					= KeyEvent.VK_F12;
	
	/** Key code number for the DELETE key */
	public static final int DELETE				= KeyEvent.VK_DELETE;
	/** Key code number for the NUM LOCK key */
	public static final int NUM_LOCK			= KeyEvent.VK_NUM_LOCK;
	/** Key code number for the SCROLL LOCK key */
	public static final int SCROLL_LOCK			= KeyEvent.VK_SCROLL_LOCK;
	/** Key code number for the PRINT SCREEN key */
	public static final int PRINTSCREEN			= KeyEvent.VK_PRINTSCREEN;
	/** Key code number for the INSERT key */
	public static final int INSERT				= KeyEvent.VK_INSERT;
	/** Key code number for the HELP key */
	public static final int HELP				= KeyEvent.VK_HELP;
	/** Key code number for the META key */
	public static final int META				= KeyEvent.VK_META;
	/** Key code number for the BACK QUOTE key */
	public static final int BACK_QUOTE			= KeyEvent.VK_BACK_QUOTE;
	/** Key code number for the QUOTE key */
	public static final int QUOTE				= KeyEvent.VK_QUOTE;
	
	/** Key code number for the numeric keypad UP arow key */
	public static final int KP_UP				= KeyEvent.VK_KP_UP;
	/** Key code number for the numeric keypad DOWN arow key */
	public static final int KP_DOWN				= KeyEvent.VK_KP_DOWN;
	/** Key code number for the numeric keypad LEFT arow key */
	public static final int KP_LEFT				= KeyEvent.VK_KP_LEFT;
	/** Key code number for the numeric keypad RIGHT arow key */
	public static final int KP_RIGHT			= KeyEvent.VK_KP_RIGHT;
	
	/** Key code number for the AMPERSAND key */
	public static final int AMPERSAND			= KeyEvent.VK_AMPERSAND;
	/** Key code number for the ASTERISK key */
	public static final int ASTERISK			= KeyEvent.VK_ASTERISK;
	/** Key code number for the DOUBLE QUOTE key */
	public static final int QUOTE_DOUBLE		= KeyEvent.VK_QUOTEDBL;
	/** Key code number for the LESS THAN key */
	public static final int LESS_THAN			= KeyEvent.VK_LESS;
	/** Key code number for the GREATER THAN key */
	public static final int GREATER_THAN		= KeyEvent.VK_GREATER;
	/** Key code number for the LEFT BRACE key */
	public static final int BRACE_LEFT			= KeyEvent.VK_BRACELEFT;
	/** Key code number for the RIGHT BRACE key */
	public static final int BRACE_RIGHT			= KeyEvent.VK_BRACERIGHT;
	
	/** Key code number for the "@" key */
	public static final int AT					= KeyEvent.VK_AT;
	/** Key code number for the ":" key */
	public static final int COLON				= KeyEvent.VK_COLON;
	/** Key code number for the "^" key */
	public static final int CIRCUMFLEX			= KeyEvent.VK_CIRCUMFLEX;
	/** Key code number for the "$" key */
	public static final int DOLLAR				= KeyEvent.VK_DOLLAR;
	/** Key code number for the EURO SIGN key */
	public static final int EURO_SIGN			= KeyEvent.VK_EURO_SIGN;
	/** Key code number for the EXCLAMATION MARK key */
	public static final int EXCLAMATION_MARK	= KeyEvent.VK_EXCLAMATION_MARK;
	
	/** Key code number for the LEFT PARENTHESIS key */
	public static final int LEFT_PARENTHESIS	= KeyEvent.VK_LEFT_PARENTHESIS;
	/** Key code number for the "#" key */
	public static final int NUMBER_SIGN			= KeyEvent.VK_NUMBER_SIGN;
	/** Key code number for the "+" key */
	public static final int PLUS				= KeyEvent.VK_PLUS;
	/** Key code number for the RIGHT PARENTHESIS key */
	public static final int RIGHT_PARENTHESIS	= KeyEvent.VK_RIGHT_PARENTHESIS;
	/** Key code number for the "_" key */
	public static final int UNDERSCORE			= KeyEvent.VK_UNDERSCORE;
	
	/** Key code number for the Microsoft Windows "WINDOWS" key */
	public static final int WINDOWS				= KeyEvent.VK_WINDOWS;
	
	
	
	
	/** 
	 * 	Bitmask value that indicates the Shift key was down during input. 
	 * 	NOTE: This value is the same as the extended modifier for AWT events.
	 */
	public static final int SHIFT_MASK	= 1 << 6;
	/** 
	 * 	Bitmask value that indicates the Control key was down during input. 
	 * 	NOTE: This value is the same as the extended modifier for AWT events.
	 */
	public static final int CTRL_MASK 	= 1 << 7;
	/** 
	 * 	Bitmask value that indicates the Meta key was down during input. 
	 * 	NOTE: This value is the same as the extended modifier for AWT events.
	 */
	public static final int META_MASK 	= 1 << 8;
	/** 
	 * 	Bitmask value that indicates the Alt key was down during input. 
	 * 	NOTE: This value is the same as the extended modifier for AWT events.
	 */
	public static final int ALT_MASK 	= 1 << 9;
	/** 
	 * 	Bitmask value that indicates the first mouse button was down during input. 
	 * 	NOTE: This value is the same as the extended modifier for AWT events.
	 */
	public static final int MOUSE_BUTTON1_MASK 	= 1 << 10;
	/** 
	 * 	Bitmask value that indicates the second mouse button was down during input. 
	 * 	NOTE: This value is the same as the extended modifier for AWT events.
	 */
	public static final int MOUSE_BUTTON2_MASK 	= 1 << 11;
	/** 
	 * 	Bitmask value that indicates the third mouse button was down during input. 
	 * 	NOTE: This value is the same as the extended modifier for AWT events.
	 */
	public static final int MOUSE_BUTTON3_MASK 	= 1 << 12;
	/** 
	 * 	Bitmask value that indicates the forth mouse button was down during input. 
	 * 	NOTE: This value is the same as the extended modifier for AWT events.
	 */
	public static final int MOUSE_BUTTON4_MASK 	= 1 << 14;
	/** 
	 * 	Bitmask value that indicates the fifth mouse button was down during input. 
	 * 	NOTE: This value is the same as the extended modifier for AWT events.
	 */
	public static final int MOUSE_BUTTON5_MASK 	= 1 << 15;
	
	
	/** Key code value for the Shift modifier */
	private static final int SHIFT_MASK_KEY = KeyEvent.VK_SHIFT;
	/** Key code value for the Control modifier */
	private static final int CTRL_MASK_KEY = KeyEvent.VK_CONTROL;
	/** Key code value for the Meta modifier */
	private static final int META_MASK_KEY = KeyEvent.VK_META;
	/** Key code value for the Alt modifier */
	private static final int ALT_MASK_KEY = KeyEvent.VK_ALT;
	
	/** Total number of key code values to poll */
	private static final int TOTAL_KEYS = 256;
	
	
	/** All Key presses that occured on the Swing Event Thread */
	private List<Integer> eventThreadPressed = new ArrayList<Integer>();
	/** All Key presses to be processed by the game Thread */
	private List<Integer> gameThreadPressed = new ArrayList<Integer>();
	
	/** All Key releases that occured on the Swing Event Thread */
	private List<Integer> eventThreadReleased = new ArrayList<Integer>();
	/** All Key releases to be processed by the game Thread */
	private List<Integer> gameThreadReleased = new ArrayList<Integer>();
	
	/** All typed Keys that occured on the Swing Event Thread */
	private List<String> eventThreadTyped = new ArrayList<String>();
	/** All typed Keys to be processed by the game Thread */
	private List<String> gameThreadTyped = new ArrayList<String>();
	
	/** True if a key is currently down */
	private boolean[] keys = new boolean[ TOTAL_KEYS ];
	/** Number of consecutive frames each key is down */
	private int[] polledKeys = new int[ TOTAL_KEYS ];
	
	/** Total number of keys currently down */
	private int keysDownCount = 0;
	/** Total number of consecutive frames a key has been down */
	private int polledKeysDown = 0;
	
	/** Modifier mask for inputs that occurred on Swing Event Thread */
	private int modifiers = 0;
	/** Modifier mask for current frame of game Thread */
	private int polledModifiers = 0;
	
	
	
	
//==============================================================================
//	  						KEY INPUTS
//==============================================================================
    
	/** Returns true if the key with the given key code is currently pressed */
	public boolean keyDown( int keyCode )
	{
		return	this.polledKeys[ keyCode ] > 0;
	}
	
	/**
	 * 	Returns true if the key with the given key code is currently pressed
	 * 	while the given modifier mask is active.
	 */
	public boolean keyDown( int keyCode, int bitmask )
	{
		return	 this.keyDown( keyCode )	&&
				(this.polledModifiers & bitmask) != bitmask;
	}
	
	/**
	 * 	Returns true if the key with the given key code was just pressed this
	 * 	frame and was not down on previous frame.
	 */
	public boolean keyDownOnce( int keyCode )
	{
		return	this.polledKeys[ keyCode ] == 1;
	}
	
	/**
	 * 	Returns true if the key with the given key code was just pressed this
	 * 	frame and was not down on previous frame, and the given modifier mask
	 * 	is active.
	 */
	public boolean keyDownOnce( int keyCode, int bitmask )
	{
		return	 this.keyDownOnce( keyCode )	&&
				(this.polledModifiers & bitmask) != bitmask;
	}
	
	/**
	 * 	Returns true if the key with the given key code was just released during
	 * 	this frame.
	 */
	public boolean keyReleased( int keyCode )
	{
		return	this.gameThreadReleased.contains( keyCode );
	}
	
	/**
	 * 	Returns true if the key with the given key code was just released during
	 * 	this frame and the given modifier mask was active.
	 */
	public boolean keyReleased( int keyCode, int bitmask )
	{
		return	 this.keyReleased( keyCode )	&&
				(this.polledModifiers & bitmask) != bitmask;
	}
	
	/** Returns true if there is any keyboard key that is currently pressed */
	public boolean isAnyKeyDown()
	{
		return 	this.polledKeysDown > 0;
	}
	
	/** Returns true if there were no keys pressed until this frame */
	public boolean isAnyKeyDownOnce()
	{
		return 	this.polledKeysDown == 1;
	}
	
	/**
	 * 	Returns a list of key codes for all of the Keys that were pressed
	 * 	during the last frame.
	 */
	public List<Integer> getKeysPressed()
	{
		return	new ArrayList<Integer>( this.gameThreadPressed );
	}
	
	/**
	 * 	Returns a list of key codes for all of the Keys that were pressed
	 * 	but now have been released during the last frame.
	 */
	public List<Integer> getKeysReleased()
	{
		return	new ArrayList<Integer>( this.gameThreadReleased );
	}
	
	/**
	 * 	Returns a list of key codes for all of the Keys that were typed
	 * 	during the last frame.
	 */
	public List<String> getKeysTyped()
	{
		return	new ArrayList<String>( this.gameThreadTyped );
	}
	
	/**
	 * 	Returns true if the given key code value matches any of the modifier
	 * 	keys (Shift, CTRL, Meta or Alt).
	 */
	public static boolean isModifierKey( int keyCode )
	{
		return	keyCode == SHIFT_MASK_KEY	||
				keyCode == CTRL_MASK_KEY	||
				keyCode == META_MASK_KEY	||
				keyCode == ALT_MASK_KEY;				
	}
	
	/**
	 * 	Returns the current modifier mask for the keyboard. The modifier mask
	 * 	is a bitmask that indicates which modifier keys (Shift, CTRL, Meta or
	 * 	Alt) are currently pressed.
	 */
	public int getModifiers()
	{
		return 	this.polledModifiers;
	}
	
	/** Returns true if the Shift modifier key is down */
	public boolean isShiftDown()
	{
		return	(this.polledModifiers & SHIFT_MASK) != 0;
	}
	
	/** Returns true if the Control modifier key is down */
	public boolean isControlDown()
	{
		return	(this.polledModifiers & CTRL_MASK) != 0;
	}
	
	/** Returns true if the Meta modifier key is down */
	public boolean isMetaDown()
	{
		return	(this.polledModifiers & META_MASK) != 0;
	}
	
	/** Returns true if the Alt modifier key is down */
	public boolean isAltDown()
	{
		return	(this.polledModifiers & ALT_MASK) != 0;
	}
	
	/** Returns true if any modifier keys or buttons are currently active */
	public boolean isModifierActive()
	{
		return	this.polledModifiers != 0;
	}
	
//==============================================================================
//		  					PROCESS INPUTS
//==============================================================================
    
	/**
	 * 	Processes all Key Events that occurred during the frame and updates the 
	 * 	key frame counts and modifiers. NOTE: This method must be done once a  
	 * 	frame or the inputs for the game will be out of sync with the current  
	 * 	frame of the game, and NO other threads should invoked any of the key  
	 * 	input methods until after poll() has returned or potential thread-ing 
	 * 	errors could occur. This Keyboard class assumes that the game Thread  
	 * 	will not invoked any other methods until after poll() is complete.
	 */
	public synchronized void process()
	{
		// Swap the input queues
		this.swapQueues();		
		
		// Update the polled counts
		this.pollKeys();
		
		// Update the modifier mask
		this.pollModifiers();
	}
	
	/**
	 * 	Handles swapping the event lists that catch Key Events from the Event
	 * 	Thread with the game lists that are accessed by the game's main thread,
	 * 	so that the game can access all inputs that occurred during this frame.
	 */
	private synchronized void swapQueues()
	{
		// Swap Pressed Keys
		List<Integer> swapPressed = this.eventThreadPressed;
		(this.eventThreadPressed = this.gameThreadPressed).clear();
		this.gameThreadPressed = swapPressed;
		
		// Swap Released Keys 
		List<Integer> swapReleased = this.eventThreadReleased;
		(this.eventThreadReleased = this.gameThreadReleased).clear();
		this.gameThreadReleased = swapReleased;
		
		// Swap Typed Keys
		List<String> swapTyped = this.eventThreadTyped;
		(this.eventThreadTyped = this.gameThreadTyped).clear();
		this.gameThreadTyped = swapTyped;
	}
	
	/** Updates the frame counts for all keys from the last frame */
	private synchronized void pollKeys()
	{
		// Update the frame counts for each key
		for ( int i = 0; i < this.keys.length; i++ )
		{
			// Update the key counts
			boolean down = this.keys[i];
			if ( down )	this.polledKeys[i]   ++;
			else		this.polledKeys[i] = 0;
		}
		
		// Track number of consecutive frames that any key
		//	has been down. If there are no keys currently
		//	down then reset the count.
		if ( this.keysDownCount > 0 )	this.polledKeysDown   ++;
		else 							this.polledKeysDown = 0;
	}
	
	/** 
	 * 	Updates the current modifier bitmask based on which modifier keys are 
	 * 	currently down, including any mouse buttons.
	 */
	private synchronized void pollModifiers()
	{
		// Swap the modifier masks
		int mask = this.modifiers;
		this.modifiers = 0;
		this.polledModifiers = mask;
	}
	
//==============================================================================
//		  					KEY LISTENER
//==============================================================================
    
	/**
	 * 	Invoked by the Event Dispatching Thread of the application that this
	 * 	Keyboard is registered too as a KeyListener. This method will update 
	 * 	the state of the key that was pressed so that the Keyboard can 
	 * 	accurately poll keys during each frame of the game, as well as update
	 * 	the current modifiers and key counts.
	 */
	@Override public synchronized void keyPressed( KeyEvent e ) 
	{
		// Check that the key code is in range
		int code = e.getKeyCode();
		if ( code < 0 || code >= this.keys.length )
			return;
		
		// Activate the key
		boolean prevDown = this.keys[ code ];
		this.keys[ code ] = true;
		
		// Update key's down count
		if ( !prevDown )
			this.keysDownCount ++;
		
		// Store the key press
		this.eventThreadPressed.add( e.getKeyCode() );
		
		// Update the modifier mask, account for both types
		//	of modifiers in case the caller uses an old mask 
		//	from KeyEvent class instead of the extends mask
		//	values.
		this.modifiers = 	this.modifiers 		| 
							e.getModifiers()	|
							e.getModifiersEx();
	}
	
	/**
	 * 	Invoked by the Event Dispatching Thread of the application that this
	 * 	Keyboard is registered too as a KeyListener. This method will update 
	 * 	the state of the key that was pressed so that the Keyboard can 
	 * 	accurately poll keys during each frame of the game, as well as update
	 * 	the current modifiers and key counts.
	 */
	@Override public synchronized void keyReleased( KeyEvent e ) 
	{
		// Check that the key code is in range
		int code = e.getKeyCode();
		if ( code < 0 || code >= this.keys.length )
			return;
		
		// Deactivate the key
		this.keys[ code ] = false;
		this.keysDownCount --;
		
		// Store the key release
		this.eventThreadReleased.add( e.getKeyCode() );
		
		// Update the modifier mask, account for both types
		//	of modifiers in case the caller uses an old mask 
		//	from KeyEvent class instead of the extends mask
		//	values.
		this.modifiers = 	this.modifiers 		| 
							e.getModifiers()	|
							e.getModifiersEx();
	}
	
	/**
	 * 	Invoked by the Event Dispatching Thread of the application that this
	 * 	Keyboard is registered too as a KeyListener. This method will collect
	 * 	all typed keys for the current frame, as well as update the current
	 * 	modifiers that are active.
	 */
	@Override public synchronized void keyTyped( KeyEvent e ) 
	{
		// Store the key typed
		this.eventThreadTyped.add( "" + e.getKeyCode() );
		
		// Update the modifier mask, account for both types
		//	of modifiers in case the caller uses an old mask 
		//	from KeyEvent class instead of the extends mask
		//	values.
		this.modifiers = 	this.modifiers 		| 
							e.getModifiers()	|
							e.getModifiersEx();
	}
	
}