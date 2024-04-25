package spaceinvaders.game;

import java.awt.Color;
import java.awt.Graphics2D;

import spaceinvaders.framework.GameObject;



/**
 * 	The Star is a Game Object used in the background space of the game to 
 * 	represent flickering Stars in outer space. A Star will flicker from one 
 * 	Color to another over a specified "flicker time".
 * 	
 */
public class Star extends GameObject {

	
	/** First Color to flicker between */
	private Color firstColor;
	/** Second Color to flicker between */	
	private Color secondColor;
	/** Current Color of the Star when flickering */
	private Color currentColor;
	
	/** Timer for the flicker effect */
	private double flickerTimer;
	/** Total time it takes the Star to flicker between Colors */
	private double totalFlickerTime;
	/** Represents the direction of the flicker between the two Colors */
	private int flickerDirection;
	
	
	
	/** 
	 * 	Creates a new Star that will flicker between full white and 
	 * 	full black.
	 */
	public Star()
	{
		this( new Color(255, 255, 255), new Color(0, 0, 0) );
	}
	
	/**
	 * 	Creates a new Star that will flicker between the given Color 
	 * 	and white.
	 */
	public Star( Color color )
	{
		this( color, new Color(0, 0, 0) );
	}
	
	/**
	 * 	Creates a new Star that will flicker between the two given 
	 * 	Colors.
	 */
	public Star( Color first, Color second )
	{
		this.firstColor = first;
		this.secondColor = second;
		this.currentColor = this.firstColor;
		
		this.flickerDirection = 1;
		this.totalFlickerTime = 0.5;
	}
	
	
	
//==============================================================================
//							FLICKER	
//==============================================================================
	
	/**
	 * 	Sets the total amount of time it will take the Star to "flicker", or 
	 * 	move from its first Color to its second Color.
	 */
	public void setTotalFlickerTime( double time )
	{
		this.totalFlickerTime = time;
	}
	
//==============================================================================
//							UPDATE	
//==============================================================================
	
	/**
	 * 	Updates the Star's flickering pattern each frame.
	 */
	@Override public void update( double secsPerFrame )
	{
		// Update the flicker timer
		this.flickerTimer += secsPerFrame * this.flickerDirection;
		double percent = this.flickerTimer / this.totalFlickerTime;
		if ( percent > 1 )
			percent = 1;
		if ( percent < 0 )
			percent = 0;
		
		// Calculate difference between the colors
		int distRed = this.secondColor.getRed() - this.firstColor.getRed();
		int distGreen = this.secondColor.getGreen() - this.firstColor.getGreen();
		int distBlue = this.secondColor.getBlue() - this.firstColor.getBlue();
		
		// Calculate the current color
		int red = this.firstColor.getRed() + (int)(distRed * percent);
		int green = this.firstColor.getGreen() + (int)(distGreen * percent);
		int blue = this.firstColor.getBlue() + (int)(distBlue * percent);
		this.currentColor = new Color( red, green, blue );
		
		// Change direction of the flicker when reached on end
		if ( percent == 1 )
			this.flickerDirection = -1;
		else if ( percent == 0 )
			this.flickerDirection = +1;
	}
	
//==============================================================================
//							RENDER	
//==============================================================================
	
	/**
	 * 	Renders the Star onto the given Graphics. This method will draw the 
	 * 	Star with its current color.
	 */
	@Override public void render( Graphics2D g )
	{
		// Grab the Star, or Game Object's, position and size
		int x = (int)this.getX();
		int y = (int)this.getY();
		int w = (int)this.getWidth();
		int h = (int)this.getHeight();
		
		// Draw the Star with current flicker Color
		g.setColor( this.currentColor );
		g.fillOval( x, y, w, h );
	}
	
}
