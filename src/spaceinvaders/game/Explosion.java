package spaceinvaders.game;

import java.awt.Graphics2D;

import spaceinvaders.framework.Animation;
import spaceinvaders.framework.GameObject;



/**
 * 	An Explosion is a specific type of Game Object designed to play an Animation 
 * 	repeatidly over a specified amount of time to represent different sized  
 * 	explosions in the game. The total length of the Explosion and the number of 
 * 	cycles the set Animation is played for can be independently adjusted in this 
 * 	class to give different effects without having to draw different Animations.
 * 	
 */
public class Explosion extends GameObject {

	
	
	/** Animation for the Explosion */
	private Animation animation;
	/** Total number of times the Explosion's Animation plays */
	private int totalAnimationCycles;
	/** Total number of animation cycles completed */
	private int animationCyclesCompleted;
	
	/** Total time the Explosion persists */
	private double totalDuration;
	
	
	
	
//==============================================================================
//							CONTROLS		
//==============================================================================
	
	/** Starts the Explosion. This resets the Animation back to the start */
	public void start()
	{
		this.animationCyclesCompleted = 0;
		if ( this.animation != null )
			this.animation.reset();
	}
	
	/** 
	 * 	Returns true if this Explosion has finished. Meaning, it has played for 
	 * 	its sets duration of time.
	 */
	public boolean isFinished()
	{
		return	this.animationCyclesCompleted >= this.totalAnimationCycles;
	}
	
//==============================================================================
//							UPDATE		
//==============================================================================
	
	/**
	 *	Updates the Explosion if it has not finished yet. This will update the 
	 *	Animation if it exists and counts the number of cycles of the Animation 
	 *	that has completed thus far.
	 */
	@Override public void update( double secsPerFrame )
	{
		// Explosion has finished playing, nothing to do
		if ( this.isFinished() )
			return;
		
		// No Animation, nothing to update ...
		if ( this.animation == null )
			return;
		
		// Animation has finished one play through last frame, 
		//	update our count and reset the Animation
		if ( this.animation.isFinished() )
		{
			this.animationCyclesCompleted ++;
			this.animation.reset();
		}
		
		// Update the Animation
		this.animation.update( secsPerFrame );
	}
	
//==============================================================================
//							RENDER		
//==============================================================================
	
	/**
	 *	Renders the Explosion's Animation to the screen if it has NOT finished 
	 *	yet.
	 */
	@Override public void render(Graphics2D g)
	{
		// Explosion has finished playing, nothing to render
		if ( this.isFinished() )
			return;
		
		// No Animation, nothing to render ...
		if ( this.animation == null || this.animation.isEmpty() )
			return;
		
		// Otherwise, render the Explosion's Animation over 
		//	the Explosion's area
		int x = (int)this.getX();
		int y = (int)this.getY();
		int w = (int)this.getWidth();
		int h = (int)this.getHeight();
		g.drawImage( this.animation.getCurrentFrame(), x, y, w, h, null );
	}
	
//==============================================================================
//							ANIMATION		
//==============================================================================
	
	/** 
	 * 	Sets the Animation that this Explosion will play while it persists. The 
	 * 	length of time the given Animation will play for is determined by the 
	 * 	Explosion's total duration and number of animation cycles it is set to. 
	 */
	public void setAnimation( Animation anim )
	{
		this.animation = anim;
		this.animation.setMode( Animation.SINGLE_MODE );
		this.updateAnimationDuration();
	}
	
	/**
	 * 	Sets the total number of times the Explosion's Animation should play 
	 * 	during the time the Explosion persists for. This will affect the length 
	 * 	of time the Explosion's Animation plays one-time through.
	 */
	public void setAnimationCycles( int cycles )
	{
		this.totalAnimationCycles = cycles;
		this.updateAnimationDuration();
	}
	
	/** 
	 * 	Sets the total amount of time that this Explosion should persist in 
	 * 	the game before it disappears. This will affect the length of time the 
	 * 	Explosion's Animation plays one-time through.
	 */
	public void setTotalDuration( double duration )
	{
		this.totalDuration = duration;
		this.updateAnimationDuration();
	}
	
	/**
	 * 	Handles updating the Explosion's Animation's length of time of a single 
	 * 	frame based on the current duration of the Explosion and the number of 
	 * 	cycles the Explosion should persist for.
	 */
	private void updateAnimationDuration()
	{
		// No Animation, nothing to update
		if ( this.animation == null )
			return;
		
		// Calculate the time of a single frame 
		//	of the Animation
		double cycleDuration = 0;
		if ( this.totalAnimationCycles > 0 )
			cycleDuration = this.totalDuration / this.totalAnimationCycles;
		double frameDuration = cycleDuration / this.animation.getTotalFrames();
		this.animation.setFrameDuration( frameDuration );
	}
}

