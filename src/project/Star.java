package project;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Star {

	private int posX;
	private int posY;
	private BufferedImage image;
	private int moveY;
	
	public Star(BufferedImage image, int maxWidth, int maxHeight) {
		this.image = image;
		Random r = new Random();
		posX = r.nextInt(maxWidth);
		posY = r.nextInt(maxHeight);
		moveY = 1 + r.nextInt(5);
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public int getMoveY() {
		return moveY;
	}

	public void setMoveY(int moveY) {
		this.moveY = moveY;
	}
	
}
