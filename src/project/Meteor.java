package project;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Meteor {

	private int posX;
	private int posY;
	private BufferedImage image;
	private int coolDown;
	private int moveX;
	private int moveY;
	
	public Meteor(BufferedImage image, int maxWidth) {
		this.image = image;
		Random r = new Random();
		coolDown = (r.nextInt(30) + 1) * 61;
		posX = r.nextInt(maxWidth);
		posY = -image.getHeight() / 2;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(posX - image.getWidth() / 2, posY - image.getHeight() / 2,
				image.getWidth(), image.getHeight());
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

	public int getCoolDown() {
		return coolDown;
	}

	public void setCoolDown(int coolDown) {
		this.coolDown = coolDown;
	}

	public int getMoveX() {
		return moveX;
	}

	public void setMoveX(int moveX) {
		this.moveX = moveX;
	}

	public int getMoveY() {
		return moveY;
	}

	public void setMoveY(int moveY) {
		this.moveY = moveY;
	}
	
}
