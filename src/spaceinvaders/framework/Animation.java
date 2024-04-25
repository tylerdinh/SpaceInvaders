package spaceinvaders.framework;

import java.awt.image.BufferedImage;
import java.util.ArrayList;



/**
 * 	An Animation handles the logic and images of an Animation for a Game Object. 
 * 	The Animation collects together images that make up the Animation and moves 
 * 	between each image over a specified amount of duration. The Animation has 
 * 	two modes, SINGLE and LOOP. A SINGLE Animation only plays one time, from 
 * 	start to finish, and needs to be reset to be played again. A LOOP Animation 
 * 	however will play from start to finish, repeatidly.
 * 	
 */
public class Animation {

	
	/** Animation Mode that will play the Animation ONE Time */
	public static final String SINGLE_MODE = "SINGLE";
	/** Animation Mode that will loop the Animation until stopped */
	public static final String LOOP_MODE = "LOOP";
	
	
	/** All Images in the Animation */
	private ArrayList<BufferedImage> frames;
	
	/** Current Mode of the Animation */
	private String mode;
	
	/** Current frame of the Animation */
	private int currentFrame;
	
	/** Timer for the Animation */
	private double frameTimer;
	/** Amount of time for each frame of the Animation */
	private double frameDuration;
	
	/** True if the Animation is paused */
	private boolean paused;
	
	
	
	
	/**
	 * 	Creates a new default LOOPING Animation with a frame duration of half 
	 * 	a second.
	 */
	public Animation()
	{
		this( LOOP_MODE, 0.5 );
	}
	
	/**
	 * 	Creates a new Animation with the given mode and with a frame duration  
	 * 	of half a second.
	 */
	public Animation( String animationMode )
	{
		this( animationMode, 0.5 );
	}
	
	/** Creates a new default LOOPING Animation with the given frame duration */
	public Animation( double frameDuration )
	{
		this( LOOP_MODE, frameDuration );
	}
	
	/** Creates a new Animation with the given mode and frame duration */
	public Animation( String animationMode, double frameDuration )
	{
		if ( animationMode != SINGLE_MODE && animationMode != LOOP_MODE )
			throw new IllegalArgumentException( "Animation mode must be set to either Animation.SINGLE_MODE or Animation.LOOP_MODE");
		
		this.frames = new ArrayList<BufferedImage>();
		this.mode = animationMode;
		this.frameDuration = frameDuration;
	}
	
	
	
//==============================================================================
//							UPDATE			
//==============================================================================
	
	/**
	 * 	Handles updating the Animation's current animation frame if it is not 
	 * 	paused, empty or finished yet. 
	 */
	public void update( double secsPerFrame )
	{
		// Animation is paused, nothing to do...
		if ( this.paused )
			return;
		
		// Animation is empty or finished, nothing to do...
		if ( this.isEmpty() || this.isFinished() )
			return;
		
		// Update the timer and check if the current frame 
		//	has elapsed or not...
		this.frameTimer += secsPerFrame;
		if ( this.frameTimer < this.frameDuration )
			return;
		
		// Frame time has elapsed, reset the timer and 
		//	move to the next frame of the Animation
		this.nextFrame();
	}
	
//==============================================================================
//							CONTROLS			
//==============================================================================
	
	/** 
	 * 	Returns true if this Animation is paused. When an Animation is paused 
	 * 	its frame timer and current frame will remain frozen in time until the 
	 * 	Animation is resumed. 
	 */
	public boolean isPaused()
	{
		return	this.paused;
	}
	
	/** 
	 * 	Pauses this Animation. When an Animation is paused its frame timer and 
	 * 	current frame will remain frozen in time until the Animation is resumed.
	 */
	public void pause()
	{
		this.setPaused( true );
	}
	
	/** 
	 * 	Resumes this Animation from where it was last paused. If the Animation 
	 * 	was not currently paused then this method will have no effect.
	 */
	public void resume()
	{
		this.setPaused( false );
	}
	
	/** 
	 * 	Set to true to pause the Animation, or false to resume it. When an 
	 * 	Animation is paused its frame timer and current frame will remain frozen 
	 * 	in time until the Animation is resumed. 
	 */
	public void setPaused( boolean pause )
	{
		this.paused = pause;
	}
	
	/** Resets the Animation back to the first frame */
	public void reset()
	{
		this.currentFrame = 0;
		this.frameTimer = 0;
	}
	
	/**
	 * 	Moves the Animation to the next frame, if possible, and resets the 
	 * 	Animation's frame timer. If the Animation is set to SINGLE mode and it 
	 * 	is already on the last frame of the Animation then this method will do 
	 * 	nothing.
	 */
	public void nextFrame()
	{
		// SINGLE Animation and already on the last frame, 
		//	nothing to do...
		int lastFrame = this.getLastFrameIndex();
		if ( this.mode == SINGLE_MODE && this.currentFrame == lastFrame )
			return;
		
		// Otherwise, we move to the next frame and reset
		//	the timer
		this.frameTimer = 0;
		this.currentFrame = (this.currentFrame + 1) % this.frames.size();
	}
	
	/**
	 * 	Returns true if this Animation is finished. An Animation will be 
	 * 	considered finished when its set to SINGLE animation mode and it has 
	 * 	finished its last frame of its Animation. A LOOPING Animation will never 
	 * 	be considered finished.
	 */
	public boolean isFinished()
	{
		return	this.mode == SINGLE_MODE 					&& 
				this.currentFrame == this.getLastFrameIndex() 	&&
				this.frameTimer >= this.frameDuration;
	}
	
	/**
	 * 	Returns the index number of the last frame of this Animation. If this 
	 * 	Animation is zero this method returns -1.
	 */
	private int getLastFrameIndex()
	{
		if ( this.isEmpty() )
			return -1;
		
		return	this.frames.size() - 1;
	}
	
//==============================================================================
//							FRAMES			
//==============================================================================
	
	/** 
	 * 	Returns the amount of time that must elapse before this Animation moves 
	 * 	to the next frame.
	 */
	public double getFrameDuration()
	{
		return	this.frameDuration;
	}
	
	/** 
	 * 	Sets the amount of time that must elapse before this Animation moves to 
	 * 	the next frame. If the given duration is negative this method throws 
	 * 	an IllegalArgumentException.
	 */
	public void setFrameDuration( double duration )
	{
		if ( duration < 0 )
			throw new IllegalArgumentException( "Animation frame duration must be a non-negative value" );
		
		this.frameDuration = duration;
	}
	
	/** 
	 * 	Loads a new animation frame using the image at the given directory and 
	 * 	filename, if it exists.
	 */
	public void addFrame( String directory, String filename )
	{
		BufferedImage frame = ImageLoader.loadImage( directory, filename );
		if ( frame != null )
			this.addFrame( frame );
	}
	
	/** Adds the given frame Image to the end of the Animation */
	public void addFrame( BufferedImage frame )
	{
		this.frames.add( frame );
	}
	
	/** Removes and returns the frame at the given index, if it exists */
	public BufferedImage removeFrame( int index )
	{
		if ( index < 0 || index >= this.frames.size() )
			return null;
		
		return	this.frames.remove( index );
	}
	
	/** Removes and returns all animation frames from this Animation */
	public ArrayList<BufferedImage> removeAllFrames()
	{
		ArrayList<BufferedImage> removed = new ArrayList<BufferedImage>();
		removed.addAll( this.frames );
		this.frames.clear();
		this.reset();
		return removed;
	}
	
	/** Returns the animation frame Image at the given index, if it exists */
	public BufferedImage getFrame( int index )
	{
		if ( index < 0 || index >= this.frames.size() )
			return null;
		
		return	this.frames.get( index );
	}
	
	/** Returns a list of all of the animation frame Images in this Animation */
	public ArrayList<BufferedImage> getAllFrames()
	{
		ArrayList<BufferedImage> allFrames = new ArrayList<BufferedImage>();
		allFrames.addAll( this.frames );
		return allFrames;
	}
	
	/** Returns the animation Image of the current frame of the Animation */
	public BufferedImage getCurrentFrame()
	{
		return	this.getFrame( this.currentFrame );
	}
	
	/** Returns the total number of frames in this Animation */
	public int getTotalFrames()
	{
		return	this.frames.size();
	}
	
	/**
	 * 	Returns true if this Animation is empty, meaning it has zero frames in  
	 * 	its Aniamtion.
	 */
	public boolean isEmpty()
	{
		return	this.frames.isEmpty();
	}
	
//==============================================================================
//							MODE			
//==============================================================================
	
	/**
	 * 	Returns true if this Animation's mode is set to SINGLE. An Animation set 
	 * 	to SINGLE mode will play its Animation one time through from start to 
	 * 	finish, but does not repeat. A SINGLE Animation will need to be reset 
	 * 	before it will can be played again.
	 */
	public boolean isSingleAnimation()
	{
		return	this.mode == SINGLE_MODE;
	}
	
	/**
	 * 	Returns true if this Animation's mode is set to LOOP. An Animation set 
	 * 	to LOOP will continuously play its Animation from start to finish.
	 */
	public boolean isLoopingAnimation()
	{
		return	this.mode == LOOP_MODE;
	}
	
	/**
	 * 	Returns the current mode for this Animation. The mode determines how the 
	 * 	Animation is played. If in SINGLE mode the Animation will only play one 
	 * 	time through from start to finish. If in LOOP mode then the Animation will 
	 * 	continuously play from start to finish.
	 */
	public String getMode() 
	{
		return	this.mode;
	}
	
	/**
	 * 	Sets the current mode for this Animation. The mode determines how the 
	 * 	Animation is played. If in SINGLE mode the Animation will only play one 
	 * 	time through from start to finish. If in LOOP mode then the Animation will 
	 * 	continuously play from start to finish. If the given mode is NOT the 
	 * 	SINGLE or LOOP mode for the Animation class then this method will throw 
	 * 	an IllegalArgumentException.
	 */
	public void setMode( String mode ) 
	{
		if ( mode != SINGLE_MODE && mode != LOOP_MODE )
			throw new IllegalArgumentException( "Animation mode must be set to either Animation.SINGLE_MODE or Animation.LOOP_MODE");
		
		this.mode = mode;
	}
	
}
