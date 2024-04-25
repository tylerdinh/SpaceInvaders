package spaceinvaders.framework;



/**
 * 	Represents a 2D rectangular area in the game world. Used by objects to
 * 	outline the space they occupy in the world and is used to determine if
 * 	two objects are overlapping or intersecting in the world.
 * 	
 */
public class Bounds {

	
	/** Top left position of the Bounds */
	private Vector topLeft;
	
	/** Width of the Bounds */
	private double width;
	/** Height of the Bounds */
	private double height;
	
	
	
	
	/** 
	 * 	Creates a new set of Bounds with default size of 100 and placed at
	 * 	the origin.
	 */
	public Bounds()
	{
		this( 0, 0, 0, 0 );
	}
	
	/** 
	 * 	Creates a new set of Bounds with the given size and placed at the 
	 * 	origin.
	 */
	public Bounds( double width, double height )
	{
		this( 0, 0, width, height );
	}
	
	/** 
	 * 	Creates a new set of Bounds at the given position.
	 */
	public Bounds( Vector pos )
	{
		this( pos, 0, 0 );
	}
	
	/** 
	 * 	Creates a new set of Bounds with the given size and top left Vector
	 * 	position
	 */
	public Bounds( Vector pos, double width, double height )
	{
		this( pos.getX(), pos.getY(), width, height );
	}
	
	/** 
	 * 	Creates a new set of Bounds with the given size and top left position
	 * 	at the given xy-coordinates.
	 */
	public Bounds( double x, double y, double width, double height )
	{
		this.topLeft = new Vector( x, y );
		this.width = width;
		this.height = height;
	}
	
	
	
//==============================================================================
//							INTERSECTION / OVERLAP
//==============================================================================
	
	/**
	 * 	Returns true if these Bounds and the given Bounds overlap at all.
	 */
	public boolean intersects( Bounds other )
	{
		double myLeft = this.getX();
		double myRight = myLeft + this.getWidth();
		double myTop = this.getY();
		double myBottom = myTop + this.getHeight();
		
		double otherLeft = other.getX();
		double otherRight = otherLeft + other.getWidth();
		double otherTop = other.getY();
		double otherBottom = otherTop + other.getHeight();
		
		// Too far to left
		if ( myRight < otherLeft )
			return false;
		// Too far to right
		if ( myLeft > otherRight )
			return false;
		// Too far up
		if ( myBottom < otherTop )
			return false;
		// Too far down
		if ( myTop > otherBottom )
			return false;
		
		// Otherwise, they intersect
		return true;
	}
	
	/** 
	 * 	Returns true if the given Vector position is contained inside of
	 * 	these Bounds.
	 */
	public boolean contains( Vector p )
	{
		double myLeft = this.getX();
		double myRight = myLeft + this.getWidth();
		double myTop = this.getY();
		double myBottom = myTop + this.getHeight();
		
		return	p.getX() > myLeft && p.getX() < myRight	&&
				p.getY() > myTop && p.getY() < myBottom;				
	}
	
	/** 
	 * 	Returns true if the given set of Bounds is completely contained 
	 * 	within this one.
	 */
	public boolean contains( Bounds other )
	{
		return	this.contains( other.getTopLeft() )	    &&
				this.contains( other.getBottomRight() ) &&
				this.contains( other.getTopRight() ) 	&&
				this.contains( other.getBottomLeft() );
	}
	
//==============================================================================
//							POSITION
//==============================================================================
	
	/**
	 * 	Returns the x-component of the top-left corner of these Bounds.
	 */
	public double getX()
	{
		return	this.topLeft.getX();
	}
	
	/**
	 * 	Returns the y-component of the top-left corner of these Bounds.
	 */
	public double getY()
	{
		return	this.topLeft.getY();
	}
	
	/**
	 * 	Returns the top-left corner of these Bounds.
	 */
	public Vector getTopLeft()
	{
		return	this.topLeft;
	}
	
	/**
	 * 	Returns the bottom-left corner of these Bounds.
	 */
	public Vector getBottomLeft()
	{
		double x = this.topLeft.getX();
		double y = this.topLeft.getY();
		return	new Vector(
				x, y + this.height
			);
	}
	
	/**
	 * 	Returns the top-right corner of these Bounds.
	 */
	public Vector getTopRight()
	{
		double x = this.topLeft.getX();
		double y = this.topLeft.getY();
		return	new Vector(
				x + this.width, y
			);
	}
	
	/**
	 * 	Returns the bottom-right corner of these Bounds.
	 */
	public Vector getBottomRight()
	{
		double x = this.topLeft.getX();
		double y = this.topLeft.getY();
		return	new Vector(
				x + this.width, y + this.height
			);
	}
	
	/**
	 * 	Sets the x-component of the top-left corner of these Bounds.
	 */
	public void setX( double x )
	{
		this.topLeft.setX( x );
	}
	
	/**
	 * 	Sets the y-component of the top-left corner of these Bounds.
	 */
	public void setY( double y )
	{
		this.topLeft.setY( y );
	}
	
	/**
	 * 	Sets the top-left corner of these Bounds.
	 */
	public void setTopLeft( double x, double y )
	{
		this.topLeft.setX( x );
		this.topLeft.setY( y );
	}
	
	/**
	 * 	Sets the top-left corner of these Bounds.
	 */
	public void setTopLeft( Vector pos )
	{
		this.topLeft = pos;
	}

	/**
	 * 	Returns the x-coordinate of the center position of these Bounds.
	 */
	public double getCenterX()
	{
		return	this.topLeft.getX() + this.width / 2;
	}
	
	/**
	 * 	Returns the y-coordinate of the center position of these Bounds.
	 */
	public double getCenterY()
	{
		return	this.topLeft.getY() + this.height / 2;
	}
	
	/**
	 * 	Returns the center position of these Bounds.
	 */
	public Vector getCenter()
	{
		double x = this.topLeft.getX() + this.width / 2;
		double y = this.topLeft.getY() + this.height / 2;
		return	new Vector( x, y );
	}
	
	/**
	 * 	Sets the x-coordinate of the center position of these Bounds.
	 */
	public void setCenterX( double x )
	{
		this.setX( x - this.width / 2 );
	}
	
	/**
	 * 	Sets the y-coordinate of the center position of these Bounds.
	 */
	public void setCenterY( double y )
	{
		this.setY( y - this.height / 2 );
	}
	
	/**
	 * 	Sets the center position of these Bounds.
	 */
	public void setCenter( double x, double y )
	{
		this.setTopLeft( x - this.width / 2, y - this.height / 2 );
	}
	
	/**
	 * 	Sets the center position of these Bounds.
	 */
	public void setCenter( Vector pos )
	{
		this.setTopLeft( pos.getX() - this.width / 2, pos.getY() - this.height / 2 );
	}
	
//==============================================================================
//							SIZE
//==============================================================================
	
	/**
	 * 	Returns the width of these Bounds.
	 */
	public double getWidth()
	{
		return	this.width;
	}
	
	/**
	 * 	Returns the height of these Bounds.
	 */
	public double getHeight()
	{
		return	this.height;
	}
	
	/**
	 * 	Sets the width of these Bounds.
	 */
	public void setWidth( double w )
	{
		this.width = w;
	}
	
	/**
	 * 	Sets the height of these Bounds.
	 */
	public void setHeight( double h )
	{
		this.height = h;
	}
	
	/**
	 * 	Sets the width and height of these Bounds.
	 */
	public void setSize( double w, double h )
	{
		this.width = w;
		this.height = h;
	}
	
}