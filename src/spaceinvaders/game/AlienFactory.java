package spaceinvaders.game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import spaceinvaders.framework.Animation;
import spaceinvaders.framework.ImageLoader;



/**
 * 	The Alien Factory will handle the creation of all of the different Aliens in the 
 * 	Game. Different Aliens have different Animations and/or different characteristics 
 * 	and this Factory will handle putting together the right details for each type of 
 * 	Alien in the Game.
 * 	
 */
public class AlienFactory {

	
	/** Filename for the first Image of Crab Alien */
	private static final String CRAB1 = "crab1.png";
	/** Filename for the second Image of Crab Alien */
	private static final String CRAB2 = "crab2.png";
	
	/** Filename for the first Image of Octopus Alien */
	private static final String OCTOPUS1 = "octopus1.png";
	/** Filename for the second Image of Octopus Alien */
	private static final String OCTOPUS2 = "octopus2.png";
	
	/** Filename for the first Image of Squid Alien */
	private static final String SQUID1 = "squid1.png";
	/** Filename for the second Image of Squid Alien */
	private static final String SQUID2 = "squid2.png";
	
	/** Filename for the Alien UFO */
	private static final String UFO = "ufo.png";
	
	/** Directory of which all Alien Images are stored */
	private static final String DIRECTORY = "res/images/aliens/";
	
	
	
	/** First Image for the Crab Alien */
	private BufferedImage crabImg1;
	/** Second Image for the Crab Alien */
	private BufferedImage crabImg2;
	
	/** First Image for the Octopus Alien */
	private BufferedImage octopusImg1;
	/** Second Image for the Octopus Alien */
	private BufferedImage octopusImg2;
	
	/** First Image for the Squid Alien */
	private BufferedImage squidImg1;
	/** Second Image for the Squid Alien */
	private BufferedImage squidImg2;
	
	/** Image for the Alien UFO */
	private BufferedImage ufoImg;
	
	
	
	
	/**
	 * 	Creates the Alien Factory which will load all necessary files for 
	 * 	creating different Aliens in the game.
	 */
	public AlienFactory()
	{
		this.crabImg1 = ImageLoader.loadImage( DIRECTORY, CRAB1 );
		this.crabImg2 = ImageLoader.loadImage( DIRECTORY, CRAB2 );
		
		this.octopusImg1 = ImageLoader.loadImage( DIRECTORY, OCTOPUS1 );
		this.octopusImg2 = ImageLoader.loadImage( DIRECTORY, OCTOPUS2 );
		
		this.squidImg1 = ImageLoader.loadImage( DIRECTORY, SQUID1 );
		this.squidImg2 = ImageLoader.loadImage( DIRECTORY, SQUID2 );
		
		this.ufoImg = ImageLoader.loadImage( DIRECTORY, UFO );
	}
	
	
	
//==============================================================================
//								WAVES		
//==============================================================================
	
	/**
	 * 	Creates and returns ALL of the Alien Waves for each level of the game. 
	 * 	This method will load all Aliens into each Wave and set the Deployment 
	 * 	and Attack Strategies for each Wave.
	 */
	public ArrayList<AlienWave> createAllWaves()
	{
		ArrayList<AlienWave> waves = new ArrayList<AlienWave>();
		waves.add( this.createWaveOne() );
		return waves;
	}
	
	/**
	 * 	Creates and returns the first Alien Wave in the game. This method will 
	 * 	load all of the Aliens into the Wave, and set the Deployment and Attack 
	 * 	Strategies for the Wave.
	 */
	private AlienWave createWaveOne()
	{
		int aliensPerRow = 11;
		AlienWave wave = this.createWave( aliensPerRow * 2, aliensPerRow * 2, aliensPerRow );
		wave.setDeploymentStrategy( new BasicAlienDeploymentStrategy( aliensPerRow ) );
		wave.setAttackStrategy( new BasicAlienAttackStrategy() );
		return wave;
	}
	
	/**
	 * 	Creates a new Alien Wave and loads the given number of Octupus, Crab 
	 * 	and Squid Aliens to the Wave, in that order.
	 */
	private AlienWave createWave( int octopuses, int crabs, int squids )
	{
		AlienWave wave = new AlienWave();
		
		for ( int i = 0; i < octopuses; i++ )
			wave.addAlien( this.createOctopus() );
		for ( int i = 0; i < octopuses; i++ )
			wave.addAlien( this.createCrab() );
		for ( int i = 0; i < octopuses; i++ )
			wave.addAlien( this.createSquid() );
		
		return wave;
	}
	
//==============================================================================
//								ALIENS		
//==============================================================================
	
	/** Handles creating a new Alien Crab and setting up its Animation */
	private Alien createCrab()
	{
		Animation crabAnimation = new Animation( 0.7 );
		crabAnimation.addFrame( this.crabImg1 );
		crabAnimation.addFrame( this.crabImg2 );
		
		Alien crab = new Alien( "Crab" );
		crab.setAnimation( crabAnimation );
		return crab;
	}
	
	/** Handles creating a new Alien Octopus and setting up its Animation */
	private Alien createOctopus()
	{
		Animation octoAnimation = new Animation( 0.7 );
		octoAnimation.addFrame( this.octopusImg1 );
		octoAnimation.addFrame( this.octopusImg2 );
		
		Alien octopus = new Alien( "Octopus" );
		octopus.setAnimation( octoAnimation );
		return octopus;
	}
	
	/** Handles creating a new Alien Squid and setting up its Animation */
	private Alien createSquid()
	{
		Animation squidAnimation = new Animation( 0.7 );
		squidAnimation.addFrame( this.squidImg1 );
		squidAnimation.addFrame( this.squidImg2 );
		
		Alien squid = new Alien( "Squid" );
		squid.setAnimation( squidAnimation );
		return squid;
	}
	
}
