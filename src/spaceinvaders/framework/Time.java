package spaceinvaders.framework;



/**
 * 	The Time class manages the game's clock timing and frame rate calculations 
 * 	and allows Game Obejcts that need to know how long the game has been running.
 * 	
 * 	SINGLETON PATTERN:
 * 	- This class is what is called a SINGLETON object. Meaning... 
 * 		(1) only one instance of the class is allowed to be created at a time, and 
 * 		(2) the only way to get access to this instance is to call the static 
 * 			method getInstance() which ensures no matter what Game Object or Scene 
 * 			accesses the Time instance they will always be using the exact same 
 * 			one.
 * 	
 */
public class Time {

	
	/** Total elapsed time in seconds since the game was started */
	private double totalTime;
	
	/** Time of the system in nane-seconds when the previous frame started */
	private long lastFrameNS;
	
	/** Amount of time in seconds that has elapsed during the current frame */
	private double frameTime;
	
	/** Timer used for calculating the FPS (in seconds) */
	private double fpsTimer;
	/** Number of frames during the current second of time */
	private int frameCount;
	/** Number of frames passed in the most recent second of time */
	private int frameRate;
	/** Total number of frames that have completed in the game */
	private long totalFrames;
	
	/** True if the Time has been initialized */
	private boolean initialized;
	
	
	/** Current instance of the Time */
	private static Time instance;
	
	
	
	/** 
	 * 	Private constructor to setup a singleton, which prevents new instances 
	 * 	of the Time to be created.
	 */
	private Time()
	{
		
	}
	
	
	
//==============================================================================
//							SINGLETON
//==============================================================================
	
	/**
	 * 	Returns the current instance of the Time system for the game. This 
	 * 	ensures that all areas of the game engine have access to the same Time 
	 * 	object to pull accurate time and frame data about the game.
	 */
	public static Time getInstance()
	{
		if ( instance == null )
			instance = new Time();
		return instance;
	}
	
//==============================================================================
//							INITIALIZE
//==============================================================================
	
	/**
	 * 	Initializes the Time so that it is synced with the start of the game 
	 * 	loop so that it accurately tracks the frame and total game time.
	 */
	protected void init()
	{
		this.lastFrameNS = System.nanoTime();
		this.totalTime = 0;
		this.frameTime = 0;
		this.totalFrames = 0;
		this.frameCount = 0;
		this.frameRate = 0;
		this.fpsTimer = 0L;
		this.initialized = true;
	}
	
	/**
	 * 	Returns true if the Time system has been initialized. The Time system 
	 * 	must be initialized before it will update during the game loop to ensure 
	 * 	that the Time information is accurate to the running of the game.
	 */
	public boolean isInitialzed()
	{
		return	this.initialized;
	}
	
//==============================================================================
//							CALCULATE
//==============================================================================
	
	/**
	 * 	Calculates the elapsed time since the last call and updates the frame 
	 * 	and time data for the Time system of the game. This method should be 
	 * 	called after each frame of the game loop. The Time must first be 
	 * 	initialized before this method will update anything.
	 */
	public void calculate()
	{
		// Not initialized then bail
		if ( !this.isInitialzed() )
			return;
		
		// Calculate the elapsed time this frame
		long currentFrameNS = System.nanoTime();
		double frameTimeNS = currentFrameNS - lastFrameNS;
		double frameTimeSecs = (double)( frameTimeNS / 1.0e9 );
		this.lastFrameNS = currentFrameNS;
		this.frameTime = frameTimeSecs;
		
		// Update the total and frame times
		this.totalTime += frameTimeSecs;
		this.fpsTimer += frameTimeSecs;
		
		// Update the frames and frame rate
		this.totalFrames ++;
		this.frameCount ++;
		if ( this.fpsTimer >= 1 )
		{
			// Second has elapsed, update the current frame rate
			this.fpsTimer -= 1;
			this.frameRate = this.frameCount;
			this.frameCount = 0;
		}
	}
	
//==============================================================================
//							FRAME RATE
//==============================================================================
	
	/**
	 * 	Returns the total number of frames that have completed since the game 
	 * 	started running. If the Time has not yet been initialized this method  
	 * 	returns -1.
	 */
	public long getTotalFrames()
	{
		if ( !this.isInitialzed() )
			return -1;
		
		return this.totalFrames;
	}
	
	/**
	 * 	Returns the current number of frames per second the game is running at 
	 * 	which is calculated by this Time instance. If the Time has not yet been 
	 * 	initialized this method returns -1.
	 */
	public int getFrameRate()
	{
		if ( !this.isInitialzed() )
			return -1;
		
		return this.frameRate;
	}
	
//==============================================================================
//							TIME
//==============================================================================
	
	/**
	 * 	Returns the time in seconds that has elapsed beteween the current frame 
	 * 	and the previous frame of the game. This is the value used by the Game 
	 * 	to update and process inputs on each Scene of the game during the game 
	 * 	loop. If the Time has not yet been initialized this method will return -1.
	 */
	public double getFrameTime()
	{
		if ( !this.isInitialzed() )
			return -1;
		
		return	this.frameTime;
	}
	
	/**
	 * 	Returns the total amount of time in seconds that has elapsed since the 
	 * 	start of the Game. If the Time has not yet been initialized this method 
	 * 	will return -1.
	 */
	public double getTotalTime()
	{
		if ( !this.isInitialzed() )
			return -1;
		
		return	this.totalTime;
	}
	
}
