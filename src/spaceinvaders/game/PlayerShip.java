package spaceinvaders.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import spaceinvaders.framework.GameObject;
import spaceinvaders.framework.ImageLoader;
import spaceinvaders.framework.Screen;
import spaceinvaders.framework.Sound;

public class PlayerShip extends GameObject {
	
	private static final int shipW = 50;
	private static final int shipH = 50;
	
	private BufferedImage shipImg;
	
	private int moveDirection;
	
	private double speed;

	private ArrayList<Laser> lasers = new ArrayList<Laser>();
	
	private int maxLasers;
	
	private double cooldown;
	private double cooldownTimer;
	
	private int HP;
	
	
	public PlayerShip() {
		super("Ship");
		this.moveDirection = 0;
		this.speed = 500;
		this.maxLasers = 5;
		this.cooldown = 0.3;
		this.cooldownTimer = 0.3;
	}
	
	public int getHP() {
		return this.HP;
	}
	
	public void setHP(int HP) {
		this.HP = HP;
	}
	
	public void setImage(BufferedImage img) {
		this.shipImg = img;
	}
	
	public void setMoveDirection(int moveDirection) {
		this.moveDirection = moveDirection;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public void reduceHP(int amount) {
		if (this.HP > 0) {
			this.HP = this.HP - amount;
		}
	}
	
	public boolean isAlive() {
		if (this.HP > 0) {
			return true;
		}
		
		else {
			return false;
		}
	}
	
	public boolean isDead() {
		return (!this.isAlive());
	}
	
	
	public void fireLaser() {
		
		boolean cooldownExpired = (this.cooldownTimer <= 0);
		
		if ((this.lasers.size() < this.maxLasers) && cooldownExpired) {

			
			Laser laser = new Laser(Color.WHITE);
			laser.setBounds(this.getCenterX(), this.getY(), 5, 10);
			laser.setSpeedY(-1300);
			lasers.add(laser);
			
			
			
			this.cooldownTimer = this.cooldown;
			
			
		}
	}
	
	public ArrayList<Laser> getAllActiveLasers() {
		return this.lasers;
	}
	
	
	@Override
	public void update(double secsPerFrame) {
		
		this.updateMovement(secsPerFrame);
		this.updateLasers(secsPerFrame);
		this.updateCooldown(secsPerFrame);
	}
	
	public void updateMovement(double secsPerFrame) {
		if (moveDirection != 0) {
			this.setX(this.getX() + this.speed * secsPerFrame * this.moveDirection);
		}
		this.moveDirection = 0;
	}
	
	public void updateLasers(double secsPerFrame) {
		for (int i = 0; i < this.lasers.size(); i++) {
			Laser laser = this.lasers.get(i);
			
			
			if (Screen.isOffScreen(laser) || !laser.isActive()) {
				this.lasers.remove(i);
			}
			
			else {
				laser.update(secsPerFrame);
				
			}
			
		}
	}
	
	public void updateCooldown(double secsPerFrame) {
		if (this.cooldownTimer > 0) {
			this.cooldownTimer -= secsPerFrame;
		}
	}
	

	@Override
	public void render(Graphics2D g) {
		this.renderMovement(g);
		this.renderLasers(g);
	}
	
	public void renderMovement(Graphics2D g) {
		int currX = (int)this.getX();
		int currY = (int)this.getY();
		
		g.drawImage(this.shipImg, currX, currY, shipW, shipH, null);
		
	}
	
	
	public void renderLasers(Graphics2D g) {
		for (Laser laser : this.lasers) {
			laser.render(g);
		}
	}

	
}
