package spaceinvaders.game;



/**
 * 	Base class for all Alien Deployment Strategies in the Game. A Deployment Strategy 
 * 	handles how the Aliens all fly into a level, or Wave, when the level first starts 
 * 	and determines exactly what the formation and placement of the Aliens are when the 
 * 	wave starts. 
 * 	
 */
public abstract class AlienDeploymentStrategy {

	
	
	/**
	 * 	Begins the deployment phase for the given Alien Wave. This method will handle 
	 * 	the setup and positioning of all of the Aliens in a grid formation, and then 
	 * 	retunrs a 2D array representing the Aliens ending positions after the Deployment 
	 * 	completes.
	 */
	abstract public Alien[][] startDeployment( AlienWave wave );
	
	/**
	 * 	Returns true if this Deployment Strategy is still in the process of deploying 
	 * 	Aliens into the area.
	 */
	abstract boolean isDeploying();
	
	/**
	 * 	Updates the Deployment Strategy each frame. This method should move the Aliens 
	 * 	into position and update any information that is used to determine if the 
	 * 	deployment phase has compeleted yet.
	 */
	abstract public void update( double secsPerFrame );
	
}

