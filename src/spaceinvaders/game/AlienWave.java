package spaceinvaders.game;

import java.awt.Graphics2D;
import java.util.ArrayList;

import spaceinvaders.framework.Bounds;
import spaceinvaders.framework.Screen;



/**
 *	The Alien Wave represents all Aliens that attack in a single level/round of the 
 *	game. The Wave goes through two phases when started, DEPLOY and ATTACK. During 
 *	the DEPLOY phase the Aliens of the Wave are moved into position that they will 
 *	be in for the level. During the ATTACK phase the Aliens move and advance towards 
 *	and attack the player. 
 *	
 *	How the Aliens in the Wave are deployed and attack are determined by the Wave's 
 *	set Alien Deployment Strategy and Alien Attack Strategy. Different Deployment 
 *	and Attack Strategies can be created and added to a Wave to change the behavior 
 *	of a Wave in the game, creating unique combinations of movement, formation and 
 *	attack patterns each level. A Wave MUST have a set Deployment and Attack Strategy 
 *	or it will not be able to be started and updated, and will be set to the INVALID 
 *	phase.	
 *	
 */
public class AlienWave {

	
	/** Represents the DEPLOY PHASE of the Wave */
	private static final String DEPLOY_PHASE = "DEPLOY_PHASE";
	/** Represents the ATTACK PHASE of the Wave */
	private static final String ATTACK_PHASE = "ATTACK_PHASE";
	/** Phase state when the Wave does not have a DEPLOY or ATTACK Strategy */
	private static final String INVALID_PHASE = "INVALID_PHASE";
	
	
	/** Current phase that the Wave is in */
	private String phase;
	
	/** Bounds of the area that the Aliens in the Wave are restricted to */
	private Bounds area;
	
	/** All Aliens that exist in the Wave, alive or dead */
	private ArrayList<Alien> aliens;
	/** All Aliens in the Wave in their attack formation */
	private Alien[][] formation;
	
	/** Strategy that determines how Aliens are deployed and in what positions */
	private AlienDeploymentStrategy deploymentStrat;
	/** Strategy that determines how Aliens move and attack */
	private AlienAttackStrategy attackStrat;
	
	
	
	
	/**
	 *	Creates a new, empty, Alien Wave.
	 */
	public AlienWave()
	{
		this.aliens = new ArrayList<Alien>();
		this.area = new Bounds( 0, 0, Screen.getScreenWidth(), Screen.getScreenHeight() );
		this.phase = INVALID_PHASE;
	}
	
	public ArrayList<Laser> getAllActiveLasers() {
		ArrayList<Laser> allActiveLasers = new ArrayList<Laser>();
		for (int i = 0; i < this.aliens.size(); i++) {
			Alien currentAlien = this.aliens.get(i);
			allActiveLasers.addAll(currentAlien.getAllActiveLasers());
		}
		return allActiveLasers;
	}
	
	public ArrayList<Alien> getAllAliens() {
		return this.aliens;
	}
	
	
	
//==============================================================================
//							STRATEGY		
//==============================================================================
	
	/**
	 * 	Sets the Deployment Strategy for this Alien Wave. The Deployment 
	 * 	Strategy handles exactly how the Aliens move into position before the 
	 * 	attack phase starts. An Alien Wave MUST have a Deployment Strategy or 
	 * 	none of its Aliens will be deployed into the battle.
	 */
	public void setDeploymentStrategy( AlienDeploymentStrategy strategy )
	{
		this.deploymentStrat = strategy;
		
		// If the strategy is null, then Wave is now INVALID
		if ( strategy == null )
			this.phase = INVALID_PHASE;
	}
	
	/**
	 * 	Returns true if this Wave has a Deployment Strategy and that Strategy 
	 * 	is currently in the middle of deploying the Aliens in the Wave.
	 */
	public boolean isDeploying()
	{
		return	this.deploymentStrat != null && this.deploymentStrat.isDeploying();
	}
	
	/**
	 * 	Sets the Attack Strategy for this Alien Wave. The Attack Strategy 
	 * 	handles how the Aliens move and attack during the attack phase. An 
	 * 	Alien Wave MUST have an Attack Strategy or none of its Aliens will 
	 * 	move or attack during battle.
	 */
	public void setAttackStrategy( AlienAttackStrategy strategy )
	{
		this.attackStrat = strategy;
		
		// If the strategy is null, then Wave is now INVALID
		if ( strategy == null )
			this.phase = INVALID_PHASE;
	}
	
	/** 
	 * 	Returns true if this Wave has an Attack Strategy and that Strategy is 
	 * 	currently in the middle of controlling the Wave.
	 */
	public boolean isAttacking()
	{
		return	this.attackStrat != null && this.attackStrat.isAttacking();
	}
	
	/**
	 * 	Returns true if this Wave is INVALID. A Wave MUST have a set Deployment 
	 * 	and Attack Strategy or it cannot function properly in the game and is 
	 * 	thus considered INVALID.
	 */
	public boolean isInvalid()
	{
		return	this.deploymentStrat == null || 
				this.attackStrat == null;
	}
	
//==============================================================================
//							START	
//==============================================================================
	
	/**
	 * 	Starts this Alien Wave if it has both a Deployment and Attack Strategy. 
	 * 	If so, then the Wave is put into the Deployment phase and the Deployment 
	 * 	Strategy is started.
	 */
	public void start()
	{
		// No valid Deployment and Attack Strategies then the Wave
		//	can't be started....
		if ( this.deploymentStrat == null || this.attackStrat == null )
		{
			this.phase = INVALID_PHASE;
			return;
		}
		
		// Start the deployment and store the formation it creates
		this.formation = this.deploymentStrat.startDeployment( this );
		this.phase = DEPLOY_PHASE;
	}
	
//==============================================================================
//							UPDATE		
//==============================================================================
	
	/**
	 *	Updates the current Phase of the Alien Wave, if it has both a Deployment 
	 *	and Attack Strategy.
	 */
	public void update( double secsPerFrame )
	{
		// If there is no deployment or attack Strategy then
		//	there's nothing to update ...
		if ( (this.deploymentStrat == null || this.attackStrat == null) || this.isDefeated()) {
			return;	
		}
		
		// Update the current strategy
		if ( this.phase == DEPLOY_PHASE )
			this.updateDeployment( secsPerFrame );
		else if ( this.phase == ATTACK_PHASE )
			this.updateAttack( secsPerFrame );
		
		// Update all active Aliens
		this.updateAliens( secsPerFrame );
	}
	
	/**
	 * 	Updates the Deployment Phase of the Alien Wave. Once the Deployment 
	 * 	Strategy is done this method will start the Attack Strategy.
	 */
	private void updateDeployment( double secsPerFrame )
	{
		// Not deploying anymore, switch to the attack phase
		if ( !this.deploymentStrat.isDeploying() )
		{
			this.phase = ATTACK_PHASE;
			this.attackStrat.startAttack( this );
			return;
		}
		
		// Otherwise, continue updating the dpeloyment.
		else
			this.deploymentStrat.update( secsPerFrame );
	}
	
	/** Updates the Attack Phase of the Alien Wave if its still attacking */
	private void updateAttack( double secsPerFrame )
	{
		// No longer attacking, nothing to do
		if ( !this.attackStrat.isAttacking() )
			return;
		
		this.attackStrat.update( secsPerFrame );
	}
	
	/** Updates all Aliens that are still alive in the Wave */
	private void updateAliens( double secsPerFrame )
	{
		// No formation of Aliens yet, then nothing to update
		if ( this.formation == null )
			return;
		
		// Update all Aliens that are still in formation
		for ( int r = 0; r < this.formation.length; r++ )
			for ( int c = 0; c < this.formation[r].length; c++ )
			{
				Alien alien = this.formation[r][c];
				if ( alien != null )
					alien.update( secsPerFrame );
			}
	}
	
//==============================================================================
//							RENDER	
//==============================================================================
	
	/** Render all active Aliens in the Wave */
	public void render( Graphics2D g )
	{
		// No foramtion yet, then nothing to render
		if ( (this.formation == null) || this.isDefeated() ) {
				return;
		}
		
		// Render all Aliens that are still active in formation
		for ( int r = 0; r < this.formation.length; r++ )
			for ( int c = 0; c < this.formation[r].length; c++ )
			{
				Alien alien = this.formation[r][c];
				if ( alien != null )
					alien.render( g );
			}
	}
	
//==============================================================================
//							ALIENS	
//==============================================================================
	
	/** Adds the given Alien into this Wave */
	public void addAlien( Alien alien )
	{
		this.aliens.add( alien );
	}
	
	/** 
	 * 	Returns the Alien in this Wave at the given index number, or null if the 
	 * 	given index does not exist. 
	 */
	public Alien getAlien( int index ) 
	{
		if ( index < 0 || index >= this.aliens.size() )
			return null;
		
		return	this.aliens.get( index );
	}
	
	/**
	 * 	Returns the Alien in this Wave at the given row and column position of 
	 * 	its formation. The formation is determined by the Deployment Strategy, 
	 * 	and if there is no formation created yet then this method returns null.
	 */
	public Alien getAlien( int row, int col )
	{
		boolean inBounds = 	this.formation != null					&&
							row >= 0 && row < this.formation.length	&&
							col >= 0 && col < this.formation[row].length;
		if ( !inBounds )
			return null;
		
		return this.formation[row][col];
	}
	
	/**
	 * 	Returns the total number of Aliens that are in this Wave, both alive and 
	 * 	dead.
	 */
	public int getTotalAliens()
	{
		return	this.aliens.size();
	}
	
	/** Returns the total number of rows of Aliens in the Wave */
	public int getTotalAlienRows()
	{
		if ( this.formation == null )
			return -1;
		
		return	this.formation.length;
	}
	
	/** Returns the total number of columns of Aliens in the Wave */
	public int getTotalAlienColumns()
	{
		if ( this.formation == null )
			return -1;
		
		if ( this.formation.length == 0 )
			return - 1;
		
		return	this.formation[0].length;
	}
	
	/** Returns true if all of the Aliens in this Wave have been defeated */
	public boolean isDefeated()
	{
		for (int i = 0; i < this.aliens.size(); i++) {
			if (this.aliens.get(i).isAlive()) {
				return false;
			}
		}
		
		return	true;
	}
	
//==============================================================================
//							AREA	
//==============================================================================
	
	/** 
	 * 	Sets the Bounds for the area that the Wave should exist in. Both the 
	 * 	Deployment and Attack Strategies will use these Bounds to properly place 
	 * 	and move the Aliens in the Wave.
	 */
	public void setAreaBounds( Bounds b )
	{
		this.area = new Bounds();
		this.area.setX( b.getX() );
		this.area.setY( b.getY() );
		this.area.setWidth( b.getWidth() );
		this.area.setHeight( b.getHeight() );
	}
	
	/**
	 * 	Returns the width of the area that the Wave should exist in. Both the 
	 * 	Deployment and Attack Strategies will use these Bounds to properly place 
	 * 	and move the Aliens in the Wave.
	 */
	public int getAreaWidth()
	{
		return	(int)this.area.getWidth();
	}
	
	/**
	 * 	Returns the height of the area that the Wave should exist in. Both the 
	 * 	Deployment and Attack Strategies will use these Bounds to properly place 
	 * 	and move the Aliens in the Wave.
	 */
	public int getAreaHeight()
	{
		return	(int)this.area.getHeight();
	}
	
	/**
	 * 	Returns the x-coordinate of the left side of the area that the Wave should 
	 * 	exist in. Both the Deployment and Attack Strategies will use these Bounds 
	 * 	to properly place and move the Aliens in the Wave.
	 */
	public int getLeftBoundary()
	{
		return	(int)this.area.getX();
	}
	
	/**
	 * 	Returns the x-coordinate of the right side of the area that the Wave should 
	 * 	exist in. Both the Deployment and Attack Strategies will use these Bounds 
	 * 	to properly place and move the Aliens in the Wave.
	 */
	public int getRightBoundary()
	{
		return	(int)(this.area.getX() + this.area.getWidth());
	}
	
}

