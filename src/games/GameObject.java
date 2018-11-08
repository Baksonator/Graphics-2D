package games;

import java.awt.image.BufferedImage;

import imageGenerator.ImageGenerator;

public class GameObject {
	
	private int positionX;
	private int positionY;
	private BufferedImage image;
	private boolean drawGame = false;
	private boolean playing = false;
	private String frameName;
	
	public GameObject(int positionX, int positionY) {
		super();
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	public void shouldDrawObject(int camX, int camY, int width, int height) {
		if (camX <= positionX && positionX <= width 
				&& camY <= positionY && positionY <= height) {
			drawGame = true;
		} else {
			drawGame = false;
		}
	}
	
	public void wantsToPlay(int heroX, int heroY, int camX, int camY) {
		if (positionX - camX <= heroX && heroX <= positionX  - camX + 100
			 && positionY - camY <= heroY && heroY <= positionY - camY + 100) {
			playing = true;
		} else {
			playing = false;
		}
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
		image = ImageGenerator.scaleImage(image, 100, 100);
		this.image = image;
	}

	public boolean isDrawGame() {
		return drawGame;
	}

	public void setDrawGame(boolean drawGame) {
		this.drawGame = drawGame;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public String getFrameName() {
		return frameName;
	}

	public void setFrameName(String frameName) {
		this.frameName = frameName;
	}

}
