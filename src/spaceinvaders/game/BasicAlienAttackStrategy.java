package spaceinvaders.game;

import java.util.ArrayList;

import spaceinvaders.framework.Sound;



/**
 * 	A Basic Alien Attack Strategy handles the base Attack Strategy that 
 * 	the original Space Invaders game uses during each level. Aliens will 
 * 	move side-to-side, advancing forward one row after each time they 
 * 	reach the edge of the screen until they reach the bottom of the screen.
 * 	
 */
public class BasicAlienAttackStrategy extends AlienAttackStrategy {

	
	
	/** Current Wave attacking */
	private AlienWave wave;
	
	
	/** The current row of Aliens to move */
	private int rowToMove;
	
	/** Current direction of movement along the x-axis */
	private int xMoveDirection;
	/** Distance to move along x-axis each step */
	private int xMoveDistance;
	/** Distance to move along y-axis each step */
	private int yMoveDistance;
	
	/** Amount of delay between movements */
	private double moveDelay;
	/** Timer for the movements */
	private double moveTimer;
	
	/** True if the Wave should advance forward next movement step */
	private boolean moveForward;
	
	
	/** */
	private double minAttackDelay;
	/** */
	private double maxAttackDelay;
	/** */
	private double currentAttackDelay;
	/** */
	private double attackTimer;
	
	
	/** True if the Strategy is currently active */
	private boolean attacking;
	
	/** Sound effect for the Aliens' lasers */
	private Sound laserSFX;
	/** Sound effects for the Aliens' movements */
	private ArrayList<Sound> moveSFX;
	/** Index of next movement SFX to play */
	private int moveSoundIndex;
	/** True if the SFX have been loaded (or opened) */
	private boolean sfxLoaded;
		
	
	
	/** 
	 * 	Creates a new Basic Attack Strategy that mimics the original Space 
	 * 	Invaders game.
	 */
	public BasicAlienAttackStrategy()
	{
		this.minAttackDelay = 0.4;
		this.maxAttackDelay = 1.1;
		
		this.laserSFX = new Sound( "res/sounds/alien_shoot.wav" );
		
		this.moveSFX = new ArrayList<Sound>();
		this.moveSFX.add( new Sound( "res/sounds/alien_move1.wav" ) );
		this.moveSFX.add( new Sound( "res/sounds/alien_move2.wav" ) );
		this.moveSFX.add( new Sound( "res/sounds/alien_move3.wav" ) );
		this.moveSFX.add( new Sound( "res/sounds/alien_move4.wav" ) );
		this.moveSoundIndex = 0;
		
		this.loadAllSFX();
	}
	
	
	
//==============================================================================
//							ATTACK	
//==============================================================================
	
	/**
	 * 	Sets up the details for the start of the attack phase for the given 
	 * 	Alien Wave. This will calculate all of the movement and attack frequency 
	 * 	details for the wave.
	 */
	@Override public void startAttack( AlienWave wave )
	{
		// Open the Sound effects for the Alien's  
		//	movement and attacks if not alread
		if ( !this.sfxLoaded )
			this.loadAllSFX();
		
		// Store the Wave we are attacking with
		this.wave = wave;
		
		// Grab a couple Aliens so we can calculate the movement size
		Alien first = null;
		Alien second = null;
		int i = 0;
		while ( i < wave.getTotalAliens() && second == null )
		{
			Alien alien = this.wave.getAlien( i );
			if ( alien != null )
			{
				if ( first == null )
					first = alien;
				else 
					second = alien;
			}
			i++;
		}
		
		// Calculate the movement details
		this.rowToMove = this.wave.getTotalAlienRows() - 1;
		this.xMoveDirection = 1;
		this.moveDelay = 0.2;
		this.xMoveDistance = (int)Math.abs( second.getX() - first.getX() );
		this.yMoveDistance = this.xMoveDistance;
		this.attacking = true;
		this.moveForward = false;
		
		// Reset the attack timer
		this.resetAttackDelay();
	}
	
	/**
	 *	Returns true if the Aliens in the Wave that this Strategy is controlling 
	 *	are currently still attacking. The Strategy is considered to be active 
	 *	if this Strategy has been started and all Alien's in the Wave are still 
	 *	alive.
	 */
	@Override boolean isAttacking()
	{
		return	this.attacking		&& 
				this.wave != null 	&& 
				!this.wave.isDefeated();
	}
	
//==============================================================================
//							UPDATE	
//==============================================================================
	
	/**
	 * 	Updates the Attack Strategy if it is currently attacking. The Basic 
	 * 	Alien Attack Strategy handles the side-to-side movement of the Aliens 
	 * 	as they advance towards the player and handles the frequency of attacks
	 * 	by Aliens in the front row.
	 */
	@Override public void update( double secsPerFrame )
	{
		// Not currently attacking, nothing to do
		if ( !this.attacking )
			return;
		
		// Update the movement and attacks
		this.updateMovement( secsPerFrame );
		this.updateAttacks( secsPerFrame );
	}
	
	/**
	 * 	Handles the side-to-side movment of all the Aliens, determines when they 
	 * 	reach the each of the bounds, and if so moves them forward one step.
	 */
	private void updateMovement( double secsPerFrame )
	{
		// Update the movement Timer and check if elapsed
		this.moveTimer += secsPerFrame;
		if ( this.moveTimer < this.moveDelay )
			return;
		this.moveTimer -= this.moveDelay;
		
		// First at the start of the next column of Aliens we need
		//	to make sure they have room to move to the side. If 
		//	moving to the side would place them outside the bounds 
		//	of the Wave then its time to advance forward
		int frontRow = this.wave.getTotalAlienRows() - 1;
		if ( !this.moveForward && this.rowToMove == frontRow )
		{
			// Grab the first Alien, either on left or right, based 
			//	on the direction they are currently moving
			Alien firstAlien;
			if ( this.xMoveDirection == - 1 )
				firstAlien = this.wave.getAlien( this.rowToMove, 0 );
			else
				firstAlien = this.wave.getAlien( this.rowToMove, this.wave.getTotalAlienColumns() - 1 );
			
			// Determine if moving this first Alien would place them 
			//	outside the bounds we need to switch on the forward 
			//	movement
			double newX = firstAlien.getX() + this.xMoveDistance * this.xMoveDirection;
			if ( newX < this.wave.getLeftBoundary() || newX + firstAlien.getWidth() > this.wave.getRightBoundary() )
				this.moveForward = true;
		}
		
		// Move each Alien in the row either side-to-side or forward
		//	based on the current movement state
		for ( int c = 0; c < this.wave.getTotalAlienColumns(); c++ )
		{
			Alien alien = this.wave.getAlien( this.rowToMove, c ); 
			if ( this.moveForward )
				alien.setY( alien.getY() + this.yMoveDistance );
			else
				alien.setX( alien.getX() + this.xMoveDistance * this.xMoveDirection );
		}
		
		// Move to the next row behind the one we just moved and 
		//	handle when reached the top of a column.
		this.rowToMove --;
		if ( this.rowToMove < 0 )
		{
			this.rowToMove = frontRow;
			
			// Moving forward and reached the last row, all Aliens 
			//	have finished advancing and we revert back to the 
			//	normal side-to-side movement.
			if ( this.moveForward )
			{
				this.xMoveDirection *= -1;
				this.moveForward = false;
			}
		}
		
		// Play the next movement Sound effect in the cycle
		Sound sfx = this.moveSFX.get( this.moveSoundIndex );
		sfx.stop();
		sfx.start();
		this.moveSoundIndex = ( this.moveSoundIndex + 1 ) % this.moveSFX.size();
	}
	
	/**
	 * 	Handles the attack pattern of all the Aliens, determines how often they 
	 * 	shoot and which Aliens shoot.
	 */
	private void updateAttacks( double secsPerFrame )
	{
		// If there are no more Aliens in the Wave, then 
		//	nothing left to attack
		if ( this.wave.isDefeated() )
			return;
		
		// Update the Timer
		this.attackTimer += secsPerFrame;
		if ( this.attackTimer < this.currentAttackDelay )
			return;
		
		// Attack timer has expired, reset it to a new value
		this.resetAttackDelay();
		
		// Time for another attack, randomly choose the
		//	front Alien of one of the columns
		Alien attacker = null;
		while ( attacker == null )
		{
			int col = (int)(Math.random() * this.wave.getTotalAlienColumns());
			int row = this.wave.getTotalAlienRows() - 1;
			
			while ( attacker == null && row >= 0 )
			{
				attacker = this.wave.getAlien( row, col );
				row --;
			}
		}
		
		// Fire the Laser!
		this.laserSFX.stop();
		attacker.fireLaser();
		this.laserSFX.start();
	}
	
	/**
	 * 	Resets the attack delay value to a new randomly selected amount of 
	 * 	time between the set minimum and maximum attack delay values.
	 */
	private void resetAttackDelay()
	{
		double range = this.maxAttackDelay - this.minAttackDelay;
		this.currentAttackDelay = Math.random() * range + this.minAttackDelay;
		this.attackTimer = 0;
	}
	
//==============================================================================
//								SOUND	
//==============================================================================
	
	/**
	 * 	Loads (or opens) all of the Sound effects this Attack Strategy is using 
	 * 	for the Alien's movements and attacks.
	 */
	public void loadAllSFX()
	{
		this.laserSFX.open();
		
		for ( Sound sfx : this.moveSFX )
			sfx.open();
		
		this.moveSoundIndex = 0;
		this.sfxLoaded = true;
	}
	
	/**
	 * 	Unloads (or closes) all of the Sound effects this Attack Strategy is using 
	 * 	for the Alien's movements and attacks.
	 */
	public void unloadAllSFX()
	{
		this.laserSFX.close();
		
		for ( Sound sfx : this.moveSFX )
			sfx.close();
		
		this.sfxLoaded = false;
	}
	
}

