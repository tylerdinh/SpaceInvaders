package spaceinvaders.framework;

import java.awt.Graphics2D;



/**
 * 	Base class for all objects that are part of the game and should follow
 * 	the update, render pattern of the game's loop. The game object also
 * 	has a Position in the game world as well as bounds that outline the
 * 	Object's area it occupies in the world and can be used to determine if
 * 	two Object's have intersected.
 * 	
 */
public abstract class GameObject {

	
	
	/** Name of the Game Object */
	private String name;
	
	/** Bounds for the Game Object */
	private Bounds bounds;
	
	
	
	/** Creates a new Game Object */
	public GameObject()
	{
		this( null );
	}
	
	/** Creates a new Game Object with the given name */
	public GameObject( String name )
	{
		this.name = name;
		this.bounds = new Bounds();
	}
	
	
	
//==============================================================================
//								GAME LOOP			
//==============================================================================
	
	/** 
	 * 	Handles updating this Game Object each frame over the given length
	 * 	of time, in seconds.
	 */
	abstract public void update( double secsPerFrame );
	
	/** 
	 * 	Handles rendering the Game Object to the given Graphics each frame.
	 */
	abstract public void render( Graphics2D g );
	
//==============================================================================
//								INTERSECTION			
//==============================================================================
	
	/**
	 * 	Returns true if this Game Object and the given Game Object overlap at
	 * 	all in the game world. This is true if the Bounds for the two objects
	 * 	overlap at all.
	 */
	public boolean intersects( GameObject obj )
	{
		return	this.intersects( obj.getBounds() );
	}
	
	/**
	 * 	Returns true if this Game Object's Bounds intersects the given set of 
	 * 	Bounds at all in the game world. This is true if the Bounds for the this 
	 * 	Game Object overlap the given set at all.
	 */
	public boolean intersects( Bounds bounds )
	{
		return	this.bounds.intersects( bounds );
	}
	
	/**
	 * 	Returns true if the given Vector position is contained in this Object's
	 * 	Bounds.
	 */
	public boolean contains( Vector point )
	{
		return	this.bounds.contains( point );
	}
	
	/**
	 * 	Returns true if the given Game Object is fully contained with this one.
	 * 	This is true if the Bounds of the given object are completely contained
	 * 	in this Object's Bounds.
	 */
	public boolean contains( GameObject obj )
	{
		return	this.bounds.contains( obj.getBounds() );
	}
	
//==============================================================================
//								BOUNDS			
//==============================================================================
	
	/**
	 * 	Returns the Bounds for this Game Object. The Bounds outline the area of
	 * 	the game world this Object occupies.
	 */
	public Bounds getBounds()
	{
		return	this.bounds;
	}
	
	/**
	 * 	Sets the Bounds for this Game Object. This will update the Object's
	 * 	top left corner position as well as its width and height.
	 */
	public void setBounds( Vector pos, double w, double h )
	{
		this.bounds.setTopLeft( pos );
		this.bounds.setSize( w, h );
	}
	
	/**
	 * 	Sets the Bounds for this Game Object. This will update the Object's
	 * 	top left corner position as well as its width and height.
	 */
	public void setBounds( double x, double y, double w, double h )
	{
		this.bounds.setTopLeft( x, y );
		this.bounds.setSize( w, h );
	}
	
//==============================================================================
//								POSITION			
//==============================================================================
	
	/**
	 * 	Returns the position of this Game Object in the game world. This is
	 * 	the location of the top left corner of its Bounds.
	 */
	public Vector getPosition()
	{
		return	this.bounds.getTopLeft();
	}
	
	/**
	 * 	Returns the x-coordinate of the position of this Game Object in the 
	 * 	game world. This is the location of the top left corner of its Bounds.
	 */
	public double getX()
	{
		return	this.bounds.getX();
	}
	
	/**
	 * 	Returns the y-coordinate of the position of this Game Object in the 
	 * 	game world. This is the location of the top left corner of its Bounds.
	 */
	public double getY()
	{
		return	this.bounds.getY();
	}
	
	/**
	 * 	Sets the position of this Game Object in the game world. This is
	 * 	the location of the top left corner of its Bounds.
	 */
	public void setPosition( Vector pos )
	{
		this.bounds.setTopLeft( pos );
	}
	
	/**
	 * 	Sets the position of this Game Object in the game world. This is
	 * 	the location of the top left corner of its Bounds.
	 */
	public void setPosition( double x, double y )
	{
		this.bounds.setTopLeft( x, y );
	}
	
	/**
	 * 	Sets the x-coordinate of the position of this Game Object in the game 
	 * 	world. This is the location of the top left corner of its Bounds.
	 */
	public void setX( double x )
	{
		this.bounds.setX( x );
	}
	
	/**
	 * 	Sets the y-coordinate of the position of this Game Object in the game 
	 * 	world. This is the location of the top left corner of its Bounds.
	 */
	public void setY( double y )
	{
		this.bounds.setY( y );
	}
	
	/**
	 * 	Returns the position of the center of this Game Object's Bounds in the 
	 * 	game world. 
	 */
	public Vector getCenterPosition()
	{
		return	this.bounds.getCenter();
	}
	
	/**
	 * 	Returns the x-coordinate of the position of the center of this Game 
	 * 	Object's Bounds in the game world. 
	 */
	public double getCenterX()
	{
		return	this.bounds.getCenterX();
	}
	
	/**
	 * 	Returns the y-coordinate of the position of the center of this Game 
	 * 	Object's Bounds in the game world. 
	 */
	public double getCenterY()
	{
		return	this.bounds.getCenterY();
	}
	
	/**
	 * 	Sets the center of this Game Object's Bounds to the given position in 
	 * 	the game world.
	 */
	public void setCenterPosition( double x, double y )
	{
		this.bounds.setCenter( x, y );
	}
	
	/**
	 * 	Sets the center of this Game Object's Bounds to the given position in 
	 * 	the game world.
	 */
	public void setCenterPosition( Vector pos )
	{
		this.bounds.setCenter( pos );
	}
	
	/**
	 * 	Sets the x-coordinate of the center of this Game Object's Bounds in the 
	 * 	game world.
	 */
	public void setCenterX( double x )
	{
		this.bounds.setCenterX( x );
	}
	
	/**
	 * 	Sets the y-coordinate of the center of this Game Object's Bounds in the 
	 * 	game world.
	 */
	public void setCenterY( double y )
	{
		this.bounds.setCenterY( y );
	}
	
//==============================================================================
//							SIZE
//==============================================================================
	
	/**
	 * 	Returns the width of these Bounds.
	 */
	public double getWidth()
	{
		return	this.bounds.getWidth();
	}
	
	/**
	 * 	Returns the height of these Bounds.
	 */
	public double getHeight()
	{
		return	this.bounds.getHeight();
	}
	
	/**
	 * 	Sets the width of these Bounds.
	 */
	public void setWidth( double w )
	{
		this.bounds.setWidth( w );
	}
	
	/**
	 * 	Sets the height of these Bounds.
	 */
	public void setHeight( double h )
	{
		this.bounds.setHeight( h );
	}
	
	/**
	 * 	Sets the width and height of these Bounds.
	 */
	public void setSize( double w, double h )
	{
		this.bounds.setSize( w, h );
	}
	
//==============================================================================
//							NAME			
//==============================================================================
	
	/**
	 * 	Returns the name of this Game Object
	 */
	public String getName() 
	{
		return	this.name;
	}
	
	/**
	 * 	Sets the name of this Game Object
	 */
	public void setName( String name ) 
	{
		this.name = name;
	}
	
}
