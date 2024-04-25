package spaceinvaders.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import spaceinvaders.framework.ImageLoader;
import spaceinvaders.framework.Keyboard;
import spaceinvaders.framework.Scene;
import spaceinvaders.framework.SceneController;
import spaceinvaders.framework.Screen;
import spaceinvaders.framework.Sound;

public class TitlescreenScene extends Scene {
	
	private Color firstTitleColor;
	private Color secondTitleColor;
	private Color shadowColor;
	private Color currentTitleColor;
	
	private double totalFadeTime;
	private double fadeTimer;
	private int fadeDirection;
	
	private ArrayList<Star> stars;
	
	private boolean startButtonVisible;
	private double startButtonDelay;
	private double startButtonTimer;
	private Color startButtonColor;
	private Color startButtonShadowColor;
	
	private Space space;
	
	private Sound backgroundSound;
	private BufferedImage background;
	

	public TitlescreenScene() {
		super("Titlescreen");
		
		this.stars = new ArrayList<Star>();
		
		this.firstTitleColor = new Color(255, 132, 0);
		this.secondTitleColor = new Color(255, 200, 0);
		this.shadowColor = new Color(255, 0, 0);
		this.currentTitleColor = this.firstTitleColor;
		
		this.totalFadeTime = 2;
		this.fadeDirection = +1;
		
		this.space = Space.get();
		
		this.startButtonVisible = false;
		this.startButtonDelay = 0;
		this.startButtonTimer = 0;
		this.startButtonColor = new Color(255, 0, 0);
		this.startButtonShadowColor = new Color(0, 255, 0);
		
		this.backgroundSound = new Sound("res/sounds/observatory.wav");
		this.background = ImageLoader.loadImage("res/backgrounds/", "observatory.jpg");
		
		
		
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
		System.out.println("Titlescreen ENTERED");
		this.backgroundSound.open();
		this.backgroundSound.loop();
		
	}

	@Override
	public void exit() {
		this.backgroundSound.close();
		
	}

	@Override
	public void processInputs(double secsPerFrame, Keyboard keys) {
		
		if (keys.keyDownOnce(Keyboard.SPACE) && this.startButtonVisible) {
			this.startGame();
		}
		
	}
	
	private void startGame() {
		SceneController controller = this.getSceneController();
		controller.setCurrentScene("Gamescreen");
	}

	@Override
	public void update(double secsPerFrame) {
			this.updateTitleFlash(secsPerFrame);
			this.space.update(secsPerFrame);
			this.updateStartButtonDelay(secsPerFrame);
		
		
		
	}
	
	
	private void updateTitleFlash(double secsPerFrame) {
		this.fadeTimer += secsPerFrame * this.fadeDirection;
		double percent = this.fadeTimer / this.totalFadeTime;
		
		if (percent > 1) {
			percent = 1;
		}
		
		if (percent < 0) {
			percent = 0;
		}
		
		//calculate difference between the colors
		int distRed = this.secondTitleColor.getRed() - this.firstTitleColor.getRed();
		int distGreen = this.secondTitleColor.getGreen() - this.firstTitleColor.getGreen();
		int distBlue = this.secondTitleColor.getBlue() - this.firstTitleColor.getBlue();
		
		//calculate new color
		
		int red = this.firstTitleColor.getRed() + (int)(distRed * percent);
		int green = this.firstTitleColor.getGreen() + (int)(distGreen * percent);
		int blue = this.firstTitleColor.getBlue() + (int)(distBlue * percent);
		
		this.currentTitleColor = new Color(red, green, blue);
		
		if (percent == 1) {
			this.fadeDirection = -1;
		}
		else if (percent == 0) {
			this.fadeDirection = +1;
		}
		
	}
	
	private void updateStartButtonDelay(double secsPerFrame) {
		this.startButtonTimer += secsPerFrame;
		
		if (this.startButtonVisible) {
			return;
		}
		
		if (this.startButtonTimer >= this.startButtonDelay) {
			this.startButtonVisible = true;
		}
		
		
	}

	@Override
	public void render(Graphics2D g) {
		
		//g.drawImage(this.background, 0, 0, Screen.getScreenWidth(), Screen.getScreenHeight(), null);
		
		this.space.render(g);
		this.renderTitle(g);
		
		if (this.startButtonVisible) {
			this.renderStartButton(g);
		}
		
	}
	

	
	private void renderTitle(Graphics2D g) {
		Font titleFont = new Font("SpaceInvaders", Font.PLAIN, 80);
		g.setFont(titleFont);
		FontMetrics fm = g.getFontMetrics();
		
		String title = "Space Invaders!";
		int titleW = fm.stringWidth(title);
		int screenW = Screen.getScreenWidth();
		int titleX = (screenW - titleW) / 2;
		
		//draw shadow
		g.setColor(this.shadowColor);
		g.drawString(title, titleX - 3, 100 - 3);
		
		//draw text
		g.setColor(this.currentTitleColor);
		g.drawString(title, titleX, 100);
		
	}
	
	private void renderStartButton(Graphics2D g) {
		Font startFont = new Font("SpaceInvaders", Font.PLAIN, 40);
		g.setFont(startFont);
		FontMetrics fm = g.getFontMetrics();
		
		String buttonText = "Press Space";
		int titleW = fm.stringWidth(buttonText);
		int screenW = Screen.getScreenWidth();
		int titleX = (screenW - titleW) / 2;
		
		//draw shadow
		g.setColor(this.startButtonShadowColor);
		g.drawString(buttonText, titleX-3, 500-3);
		
		//draw text
		g.setColor(this.startButtonColor);
		g.drawString(buttonText, titleX, 500);
	}
	

	@Override
	public void onShutDown() {
		// TODO Auto-generated method stub
		
	}

}
