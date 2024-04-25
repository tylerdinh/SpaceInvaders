package spaceinvaders.game;

import java.awt.Graphics2D;

import spaceinvaders.framework.GameObject;

public class Bomb extends GameObject {
	
	private double explosionRadius;
	private boolean isExploded;
	

	public Bomb() {
		super("Bomb");
		this.explosionRadius = 50;
		this.isExploded = false;
	}
	
	@Override
	public void update(double secsPerFrame) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}
	
	//@Override
	//public boolean intersects(GameObject g) {
		
		//if (super.int)
	//}

}
