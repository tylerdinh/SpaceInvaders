package spaceinvaders.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import spaceinvaders.framework.GameObject;
import spaceinvaders.framework.Vector;



/**
 * 	Represents a simple laser in the game that can be fired at any angle.
 */
public class Laser extends GameObject {

	
	
	/** Color for the Laser */
	private Color color;
	
	/** The current speed of the Laser */
	private Vector speed;
	
	//** If the Laser is active */
	private boolean active;
	
	
	
	
	
	/** Creates a new Laser with the given Color */
	public Laser( Color c )
	{
		this.color = c;
		this.speed = new Vector();
		this.active = true;
	}
	
//==============================================================================
//							ACTIVE		
//==============================================================================
	
	public boolean isActive() {
		return this.active;
	}
	
	public void setActive(boolean activity) {
		this.active = activity;
	}
	
//==============================================================================
//							SPEED		
//==============================================================================
	
	/**
	 * 	Sets the distance the Laser moves along the x-axis per second.
	 */
	public void setSpeedX( int xSpeed )
	{
		this.speed.setX( xSpeed );
	}
	
	/**
	 * 	Sets the distance the Laser moves along the y-axis per second.
	 */
	public void setSpeedY( int ySpeed )
	{
		this.speed.setY( ySpeed );
	}
	
	/**
	 * 	Sets the distance the Laser moves along both the x- and y-axis per 
	 * 	second.
	 */
	public void setSpeed( int xSpeed, int ySpeed )
	{
		this.speed.setX( xSpeed );
		this.speed.setY( ySpeed );
	}
	
//==============================================================================
//							UPDATE
//==============================================================================
	
	/**
	 * 	Updates the Laser's position over time based on its current speed.
	 */
	@Override public void update( double secsPerFrame )
	{
		if ( this.speed.isZero() )
			return;
		
		Vector move = this.speed.mul( secsPerFrame );
		this.setX( this.getX() + move.getX() );
		this.setY( this.getY() + move.getY() );
	}
	
//==============================================================================
//							RENDER		
//==============================================================================
	
	/**
	 * 	Renders the Laser to the screen based on the direction the Laser is 
	 * 	traveling in indicated by its current speed Vector.
	 */
	@Override public void render( Graphics2D g )
	{
		// No speed, then nothing to render 
		if ( this.speed.isZero() )
			return;
		
		// Grab the bounds
		int x = (int)this.getX();
		int y = (int)this.getY();
		int w = (int)this.getWidth();
		int h = (int)this.getHeight();
		
		// Calculate the rotation transform so that the 
		//	laser is drawn in the direction its traveling
		AffineTransform original = g.getTransform();
		double dir = Math.atan2( this.speed.getY(), this.speed.getX() ) + Math.PI / 2;
		AffineTransform transform = new AffineTransform();
		transform.translate( x + w/2, y + w/2 );
		transform.rotate( dir );
		transform.translate( -(x + w/2), -(y + w/2) );
		g.setTransform( transform );
		
		// Draw the Laser
		g.setColor( this.color );
		g.fillRect( x, y, w, h );
		
		// Replace the original transform
		g.setTransform( original );
	}
	
}

