package spaceinvaders.game;

import spaceinvaders.framework.Screen;

/**
 * 	A Basic Alien Deployment Strategy represents the base Deployment Startegy 
 * 	that the original Space Invaders game uses to setup each Alien Wave in the 
 * 	game. Aliens deployed by this Strategy will start off the top of the screen 
 * 	and move down row-by-row until they are in position.
 * 	
 */
public class BasicAlienDeploymentStrategy extends AlienDeploymentStrategy {

	
	
	/** Current Wave being deployed */
	private AlienWave wave;
	/** All Aliens in formation */
	private Alien[][] aliens;
	
	/** Number of Aliens to deploy in a single row */
	private int aliensPerRow;
	
	/** Number of rows down the screen to advance */
	private int rowsToAdvance;
	
	/** Current row being moved next step */
	private int rowToMove;
	
	/** Numebr of cycles remaining in the deployment */
	private int moveCyclesRemaining;
	/** Distance to move a row each step */
	private int moveDistance;
	/** Amount of delay between movements */
	private double moveDelay;
	/** Timer for the movements */
	private double moveTimer;
	
	/** True if the current wave is still being deployed */
	private boolean deploying;
	
	
	
	
	/** 
	 * 	Creates a new Basic Deployment Strategy that mimics how the Aliens 
	 * 	enter in the original Space Invaders game. The specified number of 
	 * 	Aliens per row will determine the number of rows and columns a given 
	 * 	Alien Wave is deployed into.
	 */
	public BasicAlienDeploymentStrategy( int aliensPerRow  )
	{
		this( aliensPerRow, 0 );
	}
	
	/** 
	 * 	Creates a new Basic Deployment Strategy that mimics how the Aliens 
	 * 	enter in the original Space Invaders game. The specified number of 
	 * 	Aliens per row will determine the number of rows and columns a given 
	 * 	Alien Wave is deployed into. The given number of rows to advance will 
	 * 	determine how many rows down the screen the Aliens should move before 
	 * 	the Wave starts. In the orginal game as you advance each wave begins 
	 * 	their attack one row closer to the player than the previous round.
	 */
	public BasicAlienDeploymentStrategy( int aliensPerRow, int rowsToAdvance  )
	{
		this.aliensPerRow = aliensPerRow;
		this.rowsToAdvance = rowsToAdvance;
	}
	
	
	
//==============================================================================
//							DEPLOYMENT	
//==============================================================================
	
	/**
	 * 	Sets up the Basic Deployment Strategy for the given Alien Wave. This 
	 * 	will calculate the starting positions of all of the Aliens in the Wave 
	 * 	in a grid formation.
	 */
	@Override public Alien[][] startDeployment( AlienWave wave )
	{
		// Store the wave being deployed
		this.wave = wave;
		
		// Calculate the area and starting details for the wave
		int totalAliens = this.wave.getTotalAliens();
		int rows = totalAliens / this.aliensPerRow;
		int cols = totalAliens / rows;
		int screenW = Screen.getScreenWidth();
		int rowSpacing = 10;
		int colSpacing = 5;
		int size = 30;
		int waveW = cols * size + (cols - 1) * colSpacing;
		int waveH = rows * size + (rows - 1) * rowSpacing;
		int waveX = (screenW - waveW) / 2;
		int waveY = -waveH;
		int rowH = size + rowSpacing;
		
		// Calculate the movement details
		this.moveDelay = 0.1;
		this.rowToMove = rows - 1;
		this.moveDistance = rowH;
		this.moveCyclesRemaining = rows + this.rowsToAdvance;
		
		// Setup the starting positions and size for the wave
		this.aliens = new Alien[rows][cols];
		int i = 0;
		for ( int r = 0; r < rows; r++ )
			for ( int c = 0; c < cols; c++ )
			{
				int x = waveX + (size + colSpacing) * c;
				int y = waveY + (size + rowSpacing) * r;
				
				Alien alien = wave.getAlien( i );
				alien.setBounds( x, y, size, size );
				this.aliens[r][c] = alien;
				
				i++;
			}
		
		// Start the deployment
		this.deploying = true;
		
		// Return the formation
		return this.aliens;
	}
	
	/**
	 * 	Returns true if the Deployment Strategy is currently in the middle of 
	 * 	the deployment process.
	 */
	@Override public boolean isDeploying()
	{
		return	this.deploying;
	}
	
//==============================================================================
//							UPDATE	
//==============================================================================
	
	/**
	 * 	Updates the Deployment Strategy if currently deploying. The Basic 
	 * 	Deployment Strategy simply moves Aliens down from the top of the screen 
	 * 	into a grid position.
	 */
	@Override public void update( double secsPerFrame )
	{
		// Finished deploying then nothing to do
		if ( !this.deploying )
			return;
		
		// Update the movement delay timer...
		this.moveTimer += secsPerFrame;
		if ( this.moveTimer < this.moveDelay )
			return;
		this.moveTimer -= this.moveDelay;
		
		// Timer has elapsed, move the next row of 
		//	Aliens down a step
		for ( Alien alien : this.aliens[this.rowToMove] )
			alien.setY( alien.getY() + this.moveDistance );
		
		// Move to the next row above, if reached the top
		//	then restart back to the first row...
		this.rowToMove --;
		if ( this.rowToMove < 0 )
		{
			this.rowToMove = this.aliens.length - 1;
			this.moveCyclesRemaining --;
		}
		
		// Once the calculated number of move cycles have
		//	elapsed the deployment is complete
		if ( this.moveCyclesRemaining == 0 )
			this.deploying = false;
	}
	
}

