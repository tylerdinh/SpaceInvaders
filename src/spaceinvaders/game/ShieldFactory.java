package spaceinvaders.game;

import java.awt.image.BufferedImage;

import spaceinvaders.framework.ImageLoader;



/**
 * 	The Shield Factory will handle the creation of the Shields in the Game. A 
 * 	Shield is actually made up of several smaller Game Objects working together 
 * 	to form the full Shield, and as the different sections are individually hit 
 * 	they will be destroyed. Once all inidividual sections are destroyed the Shield 
 * 	itself is considered to be destroyed. Because each Shield is made of 6 smaller 
 * 	Game Objects, each with their own unique images, this class handles the complex 
 * 	creation of a Shield.
 * 	 
 */
public class ShieldFactory {

	
	/** Filename for the Image of the top-left section of a Shield */
	private static final String TOP_LEFT_IMAGE = "shield_top_left.png";
	/** Filename for the Image of the top-middle section of a Shield */
	private static final String TOP_MID_IMAGE = "shield_top_mid.png";
	/** Filename for the Image of the top-right section of a Shield */
	private static final String TOP_RIGHT_IMAGE = "shield_top_right.png";
	
	/** Filename for the Image of the bottom-left section of a Shield */
	private static final String BOT_LEFT_IMAGE = "shield_bot_left.png";
	/** Filename for the Image of the bottom-middle section of a Shield */
	private static final String BOT_MID_IMAGE = "shield_bot_mid.png";
	/** Filename for the Image of the bottom-right section of a Shield */
	private static final String BOT_RIGHT_IMAGE = "shield_bot_right.png";
	
	/** Directory of which all Shield section images are stored */
	private static final String DIRECTORY = "res/images/shields/";
	
	
	
	/** Image used for the top-left section of a Shield */
	private BufferedImage topLeftImg;
	/** Image used for the top-middle section of a Shield */
	private BufferedImage topMidImg;
	/** Image used for the top-right section of a Shield */
	private BufferedImage topRightImg;
	/** Image used for the bottom-left section of a Shield */
	private BufferedImage botLeftImg;
	/** Image used for the bottom-middle section of a Shield */
	private BufferedImage botMidImg;
	/** Image used for the bottom-right section of a Shield */
	private BufferedImage botRightImg;
	
	
	
	/**
	 * 	Creates the Sheild Factory which will load all necessary files for 
	 * 	creating Shields in the game.
	 */
	public ShieldFactory()
	{
		this.topLeftImg 	= ImageLoader.loadImage( DIRECTORY, TOP_LEFT_IMAGE ); 
		this.topMidImg 		= ImageLoader.loadImage( DIRECTORY, TOP_MID_IMAGE ); 
		this.topRightImg 	= ImageLoader.loadImage( DIRECTORY, TOP_RIGHT_IMAGE ); 
		this.botLeftImg 	= ImageLoader.loadImage( DIRECTORY, BOT_LEFT_IMAGE ); 
		this.botMidImg 		= ImageLoader.loadImage( DIRECTORY, BOT_MID_IMAGE ); 
		this.botRightImg 	= ImageLoader.loadImage( DIRECTORY, BOT_RIGHT_IMAGE ); 
	}
	
	
	
//==============================================================================
//								SHIELDS		
//==============================================================================
	
}
