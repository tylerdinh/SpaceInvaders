package spaceinvaders.game;

import java.awt.Graphics2D;
import java.util.ArrayList;

import spaceinvaders.framework.Animation;
import spaceinvaders.framework.Bounds;
import spaceinvaders.framework.ImageLoader;
import spaceinvaders.framework.Keyboard;
import spaceinvaders.framework.Scene;
import spaceinvaders.framework.Screen;
import spaceinvaders.framework.Sound;

public class GameplayScene extends Scene {
	
	private PlayerShip ship;
	private Bounds playingArea;
	
	private Space space;
	
	private Sound backgroundSound;
	private Sound laserSound;
	
	private ArrayList<AlienWave> alienWaves;
	private AlienWave currentAlienWave;
	
	private ArrayList<Explosion> activeExplosions;

	public GameplayScene() {
		super("Gamescreen");
		this.createPlayerShip();
		this.createPlayerArea();
		this.createAlienWaves();
		this.space = Space.get();
		this.backgroundSound = new Sound("res/sounds/corneria.wav");
		this.laserSound = new Sound("res/sounds/shoot.wav");
		this.activeExplosions = new ArrayList<Explosion>();
		
		// TODO Auto-generated constructor stub
	}
	
	
	public void createPlayerArea() {
		
		double screenWidth = Screen.getScreenWidth();
		double screenHeight = Screen.getScreenHeight();
		
		this.playingArea = new Bounds(screenWidth, screenHeight);
	}
	
	public void createPlayerShip() {
		int shipW = 50;
		int shipH = 50;
		
		this.ship = new PlayerShip();
		this.ship.setHP(3);
		
		double screenW = Screen.getScreenWidth();
		double shipX = (screenW - shipW) / 2;
		double screenH = Screen.getScreenHeight();
		double shipY = screenH - shipH;
		
		this.ship.setBounds(shipX, shipY, shipW, shipH);
		this.ship.setImage(ImageLoader.loadImage("res/images/player/", "ship.png"));
		
	}
	
	public void createAlienWaves() {
		AlienFactory af = new AlienFactory();
		this.alienWaves = af.createAllWaves();
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unload() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enter() {
		System.out.println("Gamescreen ENTERED");
		this.laserSound.open();
		this.backgroundSound.open();
		this.backgroundSound.loop();
		this.startAlienWave(0);
		
		
	}
	
	public void startAlienWave(int waveIndex) {
		if (this.alienWaves.get(waveIndex) == null) {
			return;
		}
		
		this.currentAlienWave = this.alienWaves.get(waveIndex);
		this.alienWaves.remove(waveIndex);
		
		this.currentAlienWave.setAreaBounds(this.playingArea);
		this.currentAlienWave.start();
		
	}

	@Override
	public void exit() {
		this.laserSound.close();
		this.backgroundSound.close();
		
	}

	@Override
	public void processInputs(double secsPerFrame, Keyboard keys) {
		if (this.currentAlienWave.isDeploying()) {
			return;
		}
		
		if (keys.keyDownOnce(Keyboard.SPACE)) {

			this.laserSound.stop();
			this.ship.fireLaser();
			this.laserSound.start();
			
		}
		
		if (keys.keyDown(Keyboard.LEFT)) {
			this.ship.setMoveDirection(-1);
		}
		
		if (keys.keyDown(Keyboard.RIGHT)) {
			this.ship.setMoveDirection(1);
		}
		
		//ship fires bomb
		/**if (keys.keyDown(Keyboard.B)) {
			this.ship.fireBomb();
		}
		*/
		
		//ship does sephiroth final smash
		/**if (keys.keyDown(Keyboard.S)) {
			this.ship.activateSephiroth();
		}
		*/
		
	}
	

	@Override
	public void update(double secsPerFrame) {
		if (this.ship.getHP() > 0) {
			this.updatePlayer(secsPerFrame);
			this.updateAlienWave(secsPerFrame);
			
		}
		
		this.space.update(secsPerFrame);
		this.checkCollisions();
		this.updateExplosions(secsPerFrame);
		
	}
	
	
	public void updatePlayer(double secsPerFrame) {
		this.ship.update(secsPerFrame);
		if (!this.playingArea.contains(this.ship.getBounds())) {
			double left = this.playingArea.getX();
			double right = this.playingArea.getX() + this.playingArea.getWidth();
			
			double shipLeft = this.ship.getX();
			double shipRight = this.ship.getX() + this.ship.getWidth();
			
			if (shipLeft < left) {
				this.ship.setX(left);
				
			}
			
			if (shipRight > right) {
				this.ship.setX(right-this.ship.getWidth());
			}
		}
		

	}
	
	public void updateAlienWave(double secsPerFrame) {
		if (this.currentAlienWave == null) {
			return;
		}
		
		this.currentAlienWave.update(secsPerFrame);
	}
	
	public void updateExplosions(double secsPerFrame) {
		for (int i = 0; i < this.activeExplosions.size(); i++) {
			Explosion activeExplosion = this.activeExplosions.get(i);
			if (!activeExplosion.isFinished()) {
				activeExplosion.update(secsPerFrame);
			}
			
			if (activeExplosion.isFinished()) {
				this.activeExplosions.remove(activeExplosion);
			}
		}
	}
	
	public void checkCollisions() {
		if ((this.currentAlienWave == null) || this.ship.getHP() == 0) {
			return;
		}
		
		
		
		//handles if ship laser hits alien in wave
		for (int i = 0; i < this.currentAlienWave.getAllAliens().size(); i++) {
			Alien currentAlien = this.currentAlienWave.getAlien(i);
			for (int j = 0; j < this.ship.getAllActiveLasers().size(); j++) {
				Laser currentLaser = this.ship.getAllActiveLasers().get(j);
				if (currentAlien.isAlive()) {
					if (currentLaser.intersects(currentAlien)) {
						this.handleAlienLaserCollision(currentAlien, currentLaser);
					}
				}
			}
			
		}
		
			
		//handles if alien laser hits ship
		for (int i = 0; i < this.currentAlienWave.getAllActiveLasers().size(); i++) {
			Laser currentLaser = this.currentAlienWave.getAllActiveLasers().get(i);
				if (currentLaser.intersects(this.ship)) {
					this.handlePlayerLaserCollision(this.ship, currentLaser);
				}
			
		
		}	
		
	}
	
	public void handleAlienLaserCollision(Alien alien, Laser laser) {
		System.out.println("ALIEN KILLED");
		alien.setAlive(false);
		laser.setActive(false);
		Animation a = new Animation();
		Explosion e = new Explosion();
		
		e.setHeight(alien.getHeight());
		e.setWidth(alien.getWidth());
		e.setX(alien.getX());
		e.setY(alien.getY());
		
		e.setTotalDuration(0.5);
		e.setAnimationCycles(1);
		
		for (int i = 1; i <= 5; i++) {
			a.addFrame("res/images/sfx/", "explosion" + i + ".png");
		}
		
		e.setAnimation(a);
		
		this.activeExplosions.add(e);
	}
		
	public void handlePlayerLaserCollision(PlayerShip ship, Laser laser) {
		System.out.println("PLAYER KILLED");
		laser.setActive(false);
		ship.reduceHP(1);
		
		if (ship.getHP() == 0) {
			
			Animation a = new Animation();
			Explosion e = new Explosion();
			
			e.setHeight(ship.getHeight());
			e.setWidth(ship.getWidth());
			e.setX(ship.getX());
			e.setY(ship.getY());
			
			e.setTotalDuration(0.5);
			e.setAnimationCycles(1);

			for (int i = 1; i <= 5; i++) {
				a.addFrame("res/images/sfx/", "explosion" + i + ".png");
			}
			
			e.setAnimation(a);
			
			this.activeExplosions.add(e);
			
		}
		
		
	}
	


	@Override
	public void render(Graphics2D g) {
		
		if (this.ship.getHP() > 0) {
			this.ship.render(g);
		}
		
		this.space.render(g);
		this.renderExplosions(g);
		
		if (this.currentAlienWave != null) {
			this.currentAlienWave.render(g);
		}
		
	}
	
	public void renderExplosions(Graphics2D g) {
		for (int i = 0; i < this.activeExplosions.size(); i++) {
			Explosion activeExplosion = this.activeExplosions.get(i);
			activeExplosion.render(g);
		}
	}
	
	

	@Override
	public void onShutDown() {
		// TODO Auto-generated method stub
		this.backgroundSound.close();
	}

}
