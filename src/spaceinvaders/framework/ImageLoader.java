package spaceinvaders.framework;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;



/**
 * 	Utility class that handles the process for loading Images from files in the Game.
 */
public class ImageLoader {

	
	/** 
	 * 	Loads an image for the given directory and filename. Any error results
	 * 	in this method returning null. 
	 */
	public static BufferedImage loadImage( String directory, String filename )
	{
		// Attempt to load file
		BufferedImage img = null;
		InputStream stream = null;
		try {
			stream = new FileInputStream( directory + filename );
			img = ImageIO.read( stream );
			
		} catch ( Exception e ) {
			
			e.printStackTrace();
		} finally {
			try {
				stream.close();
			} catch ( IOException ioe ) {
				ioe.printStackTrace();
			}
		}
		return img;
	}
	
}