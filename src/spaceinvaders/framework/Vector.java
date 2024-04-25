package spaceinvaders.framework;



/**
 * 	Represents a location is 2D-space used for position, speed, or anything
 * 	else that has a directional x/y component to it. The data and calculations 
 * 	in this class are fundamental mathematics to any sort of simulation of 
 * 	physics.
 * 	
 */
public class Vector {

	
	/** Default epsilon used to test equal to */
	public static final double EPSILON = 0.0001;
	
	
	/** X-coordinate of the Vector */
	private double x;
	/** Y-coordinate of the Vector */
	private double y;
	
	
	
	/** Creates a new Vector at the origin */
	public Vector()
	{
		this( 0, 0 );
	}
	
	/** Creates a new Vector with the given xy-components */
	public Vector( double x, double y )
	{
		this.x = x;
		this.y = y;
	}
	
	
	
//==============================================================================
//							POLAR
//==============================================================================
	
	/**
	 * 	Static method that creates a new Vector with the given length and 
	 * 	pointing in the given direction.
	 */
	public static Vector polar( double degrees, double length )
	{
		double rads = Math.toRadians( degrees );
		double x = length * Math.cos( rads );
		double y = length * Math.signum( rads );
		return	new Vector( x, y );
	}
	
//==============================================================================
//							TRANSFORMATIONS
//==============================================================================
	
	/** 
	 * 	Shift the Vector by the given xy-distances 
	 */
	public void translate( double tx, double ty ) 
	{
		this.x += tx;
		this.y += ty;
	}
	
	/** 
	 * 	Shift the Vector by the given Vector amount
	 */
	public void translate( Vector t )
	{
		this.x += t.x;
		this.y += t.y;
	}
	
	/** 
	 * 	Stretch or shrink the Vector by given xy-scales. 
	 */
	public void scale( double sx, double sy ) 
	{
		this.x *= sx;
		this.y *= sy;
	} 
	
	/** 
	 * 	Rotates the Vector about the given number of degrees 
	 */
	public void rotate( double degrees ) 
	{
		double rad = Math.toRadians( degrees );
		double tmp = (this.x * Math.cos(rad) - this.y * Math.sin(rad));
		this.y = (this.x * Math.sin(rad) + this.y * Math.cos(rad));
		this.x = tmp;
	}
	
	/**
	 * 	Rotates the Vector a given number of degrees around with the given 
	 * 	second Vector as the origin of the rotation.
	 */
	public void rotate( double degrees, Vector origin )
	{
		double rad = Math.toRadians( degrees );
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);
		double tmp = origin.x + ((this.x - origin.x) * cos - (this.y - origin.y) * sin);
		this.y = origin.y + ((this.x - origin.x) * sin + (this.y - origin.y) * cos);
		this.x = tmp;
	}
	
//==============================================================================
//							SCALAR MATH		
//==============================================================================
	
	/** 
	 * 	Adds this Vector to given and returns result. 
	 */
	public Vector add( Vector v ) 
	{
		return new Vector( this.x + v.x, this.y + v.y );
	}
	
	/** 
	 * 	Subtracts given Vector from this one and returns the result. 
	 */
	public Vector sub( Vector v ) 
	{
		return new Vector( this.x - v.x, this.y - v.y );
	}
	
	/** 
	 * 	Multiplies this Vector by given scalar value and returns the result.
	 */
	public Vector mul( double scalar ) 
	{
		return new Vector( scalar * this.x, scalar * this.y );
	}
	
	/** 
	 * 	Divides this Vector by given scalar value and returns the result.
	 */
	public Vector div( double scalar ) 
	{
		return new Vector( this.x / scalar, this.y / scalar );
	}
	
//=====================================================================================
//							ALGEBRA
//=====================================================================================
	
	/** 
	 * 	Returns distance between this Vector and a given Vector 
	 */
	public double distanceTo( Vector v )
	{
		return  Math.sqrt( (this.x - v.x)*(this.x - v.x) + (this.y - v.y)*(this.y - v.y) );
	}
	
	/** 
	 * 	Returns the angle between this Vector and a given Vector in degrees
	 */
	public double directionTo( Vector v )
	{
		double rad = Math.atan2( (v.y - this.y), (v.x - this.x) );
		return	Math.toDegrees( rad );
	}
	
	/** 
	 * 	Returns midpoint Vector between this Vector and a given Vector 
	 */
	public Vector midpoint( Vector v )
	{
		return  new Vector( (this.x + v.x) / 2.0f, (this.y + v.y) / 2.0f );
	}
	
	/** 
	 * 	Returns the length of this Vector. 
	 */
	public double length() 
	{		
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}
	
	/** 
	 * 	Returns the squared length of this Vector. 
	 */
	public double lengthSqr() 
	{
		return this.x * this.x + this.y * this.y;
	}
	
//==============================================================================
//							UTILITY
//==============================================================================
	
	/**
	 * 	Returns true if this Vector is equal to zero. Meaning BOTH its x- and
	 * 	y-components are zero.
	 */
	public boolean isZero()
	{
		return	this.equals( new Vector(), EPSILON );
	}
	
	/**
	 * 	Returns true if the given Object is also a Vector with the same xy-
	 * 	components as this one.
	 */
	@Override public boolean equals( Object obj )
	{
		if ( !(obj instanceof Vector) )
			return false;
		
		Vector that = (Vector) obj;
		return	this.equals( that, EPSILON );
	}
	
	/** 
	 * 	Returns true if two vectors are equal within the given
	 * 	epsilon value.
	 */
	public boolean equals( Vector that, double epsilon )
	{
		// Same object
		if ( this == that )	return true;
		
		// Grab values
		final double thisX = Math.abs( this.x );
		final double thisY = Math.abs( this.y );
		final double thatX = Math.abs( that.x );
		final double thatY = Math.abs( that.y );
		
		// Calculate bounds
		final double minX = thatX - epsilon;
		final double maxX = thatX + epsilon;
		final double minY = thatY - epsilon;
		final double maxY = thatY + epsilon;
		
		// Check within bounds
		return	thisX >= minX && thisX <= maxX &&
				thisY >= minY && thisY <= maxY;
	}
	
	/**
	 * 	Returns a String representation of the Vector in (x, y) form.
	 */
	@Override public String toString() 
	{
		return  "(" + this.x + "," + this.y + ")";
	}
	
//==============================================================================
//							GETTERS & SETTERS		
//==============================================================================
	
	/** Returns the x-component of the Vector */
	public void setX( double x )
	{
		this.x = x;
	}
	
	/** Sets the x-component of the Vector */
	public double getX()
	{
		return this.x;
	}
	
	/** Returns the y-component of the Vector */
	public void setY( double y )
	{
		this.y = y;
	}
	
	/** Returns the y-component of the Vector */
	public double getY()
	{
		return this.y;
	}
	
}