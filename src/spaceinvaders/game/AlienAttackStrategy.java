package spaceinvaders.game;



/**
 * 	Base class for all Alien Attack Strategies in the Game. An Attack 
 * 	Strategy handles how the Aliens move and when each of them attack 
 * 	the Player.
 * 	
 */
public abstract class AlienAttackStrategy {

	
	
	/**
	 * 	Begins the attack phase for the given Alien Wave. This method will 
	 * 	handle the setup details for the movement and attackign of the given 
	 * 	Wave of Aliens.
	 */
	abstract public void startAttack( AlienWave wave );
	
	/**
	 * 	Returns true if this Attack Strategy is still in the process of 
	 * 	attacking.
	 */
	abstract boolean isAttacking();
	
	/**
	 * 	Updates the Attack Strategy each frame. This method should update 
	 * 	the movement pattern and attack pattern of the current Alien Wave.
	 */
	abstract public void update( double secsPerFrame );
	
}

