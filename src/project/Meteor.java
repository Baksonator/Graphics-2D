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
	private float angle;
	private float rotation;
	
	public Meteor(BufferedImage image, int maxWidth) {
		this.image = image;
		Random r = new Random();
		coolDown = (r.nextInt(30) + 1) * 61;
		posX = r.nextInt(maxWidth);
		posY = -image.getHeight() / 2;
		angle = (float)(Math.random() * Math.PI * 2.0);
		rotation = (float)(Math.random() - 0.5) * 0.3f;
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

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
}
