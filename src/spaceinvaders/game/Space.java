package spaceinvaders.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import spaceinvaders.framework.Screen;

public class Space {
	private static Space space;
	private ArrayList<Star> stars;
	
	private Space() {
		this.stars = new ArrayList<Star>();
		createStars();
	}
	
	public void createStars() {
		for (int i = 0; i < 1000; i++) {
			
			int colorVal = (int) (Math.random() * 255);
			Star s = new Star(new Color(colorVal, colorVal, colorVal));
			
			int x = (int) (Math.random() * Screen.getScreenWidth());
			int y = (int) (Math.random() * Screen.getScreenHeight());
			
			int size = (int) (Math.random() * 6 + 1);
			
			s.setBounds(x, y, size, size);
			
			double flickerTime = (Math.random() * 0.6) + 0.6;
			s.setTotalFlickerTime(flickerTime);
			this.stars.add(s);
			
			
			
		}
	}
	
	public static Space get() {
		if (space == null) {
			space = new Space();
		}
		return space;
	}
	
	public void update(double secsPerFrame) {
		this.updateStars(secsPerFrame);
	}
	
	private void updateStars(double secsPerFrame) {
		for (int i = 0; i < this.stars.size(); i++) {
			this.stars.get(i).update(secsPerFrame);
		}
		
	}
	
	public void render(Graphics2D g) {
		this.renderStars(g);
	}
	
	private void renderStars(Graphics2D g) {
		
		for (int i = 0; i < this.stars.size(); i++) {
			this.stars.get(i).render(g);
			
		}
		
	}

}
