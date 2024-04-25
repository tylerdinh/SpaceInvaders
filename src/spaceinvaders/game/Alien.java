package spaceinvaders.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import spaceinvaders.framework.Animation;
import spaceinvaders.framework.GameObject;
import spaceinvaders.framework.Screen;



/**
 * 	Represents a basic Alien Invader in the game. Aliens have a name, an Animation 
 * 	and a set of Lasers they can fire in the game.
 */
public class Alien extends GameObject {

	
	/** Animation for the Alien */
	private Animation animation;
	
	/** All active Lasers shot by this Alien */
	private ArrayList<Laser> lasers = new ArrayList<Laser>();
	/** Color of each Laser shot by this Alien */
	private Color laserColor;
	
	/** True if this Alien is still alive */
	private boolean alive;
	
	
	
	/** Creates a new Alien with the given name */
	public Alien( String name )
	{
		super( name );
		
		this.laserColor = new Color(255, 255, 255);
		this.lasers = new ArrayList<Laser>();
		this.alive = true;
	}
	
	
	
//==============================================================================
//							ANIMATIONS		
//==============================================================================
	
	/** Sets the Animation used to animate this Alien on screen */
	public void setAnimation( Animation anim )
	{
		this.animation = anim;
	}
	
//==============================================================================
//							LASERS		
//==============================================================================
	
	/**
	 * 	Fires a new Laser from the front of the Alien.
	 */
	public void fireLaser()
	{
		Laser laser = new Laser( this.laserColor );
		double laserX = this.getX() + this.getWidth() / 2;
		double laserY = this.getY() + this.getHeight();
		laser.setSize( 4, 10 );
		laser.setCenterPosition( laserX, laserY );
		laser.setSpeed( 0, 300 );
		this.lasers.add( laser );
	}
	
	public ArrayList<Laser> getAllActiveLasers() {
		return this.lasers;
	}

//==============================================================================
//							ALIVE/DEAD		
//==============================================================================
	
	/** Returns true if this Alien is alive */
	public boolean isAlive()
	{
		return	this.alive;
	}
	
	/** Returns true if this Alien is dead */
	public boolean isDead()
	{
		return	!this.alive;
	}
	
	/** 
	 * 	Set to true to indicate this Alien is alive or false to indicate they 
	 * 	have been killed. 
	 */
	public void setAlive( boolean alive )
	{
		this.alive = alive;
	}
	
//==============================================================================
//							UPDATE		
//==============================================================================
	
	/** Updates this Alien's Animations and any active Lasers it may have fired */
	@Override public void update( double secsPerFrame )
	{
		// Update Animations if the Alien is alive...
		if ( this.isAlive() && this.animation != null )
			this.animation.update( secsPerFrame );
		
		
		// Update the Lasers regardless of if the Alien is 
		//	alive or dead, otherwise Lasers that were fired 
		//	before they dead would magically disappear
		this.updateLasers( secsPerFrame );
	}
	
	/**
	 * 	Updates each of the active Lasers from the Aliens and handles when 
	 * 	they have moved off screen.
	 */
	private void updateLasers( double secsPerFrame )
	{
		int i = 0;
		while ( i < this.lasers.size() )
		{
			Laser laser = this.lasers.get(i);
			laser.update( secsPerFrame );
			
			if ( Screen.isOffScreen( laser ) || !laser.isActive() )
				this.lasers.remove(i);
			else
				i ++;
		}
	}
	
//==============================================================================
//							RENDER		
//==============================================================================
	
	/**
	 *	Renders the Alien's Animations and Lasers. If the Alien is dead then 
	 *	only the Alien's remaining lasers it fired prior will be rendered.
	 */
	@Override public void render( Graphics2D g )
	{
		if ( this.isAlive() && this.animation != null )
			this.renderAnimation( g );
		
		this.renderLasers( g );
	}
	
	/** Renders the current image frame of the Alien's Animation to the screen */
	private void renderAnimation( Graphics2D g )
	{
		// Get the bounds of the Alien
		int x = (int)this.getX();
		int y = (int)this.getY();
		int w = (int)this.getWidth();
		int h = (int)this.getHeight();
		
		// Draw the current animation frame
		g.drawImage( this.animation.getCurrentFrame(), x, y, w, h, null );
	}
	
	/** Renders all remaining active Lasers the Alien fired */
	private void renderLasers( Graphics2D g )
	{
		for ( Laser laser : this.lasers )
			laser.render( g );
	}
	
}

