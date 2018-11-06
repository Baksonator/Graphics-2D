package project;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class EnemyShip {

	private int posX;
	private int posY;
	private int health;
	private BufferedImage image;
	private int maxLeft;
	private int maxRight;
	private boolean isDead;
	
	public EnemyShip(BufferedImage image, int maxLeft, int maxRight) {
		this.image = image;
		this.maxLeft = maxLeft;
		this.maxRight = maxRight;
		health = 3;
		isDead = true;
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
		if (this.health <= 0) {
			isDead = true;
		}
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public int getMaxLeft() {
		return maxLeft;
	}

	public void setMaxLeft(int maxLeft) {
		this.maxLeft = maxLeft;
	}

	public int getMaxRight() {
		return maxRight;
	}

	public void setMaxRight(int maxRight) {
		this.maxRight = maxRight;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	
}
