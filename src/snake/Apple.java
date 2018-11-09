package snake;

import java.awt.image.BufferedImage;
import java.util.Random;
import imageGenerator.ImageCollector;

public class Apple {
	
	private int positionX, positionY;
	private BufferedImage image;
	private Random r = new Random();
	private int screenWidth, screenHeight;
	
	public Apple(int screenWidth, int screenHeight) {
		image = ImageCollector.greenApple;
		
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		setRandomPositions();
	}
	
	public void setRandomPositions() {
		positionX = Math.abs(r.nextInt(screenWidth) - image.getWidth());
		positionY = Math.abs(r.nextInt(screenHeight) - image.getHeight());
	}
	
	public int getPositionX() {
		return positionX;
	}
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	public int getPositionY() {
		return positionY;
	}
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
