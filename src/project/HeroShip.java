package project;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class HeroShip {

	private int posX;
	private int posY;
	private int health;
	private int livesLeft;
	private BufferedImage image;
	private boolean isDamaged = false;
	private int maxLeft;
	private int maxRight;
	private int hitDuration;
	private boolean isDead = false;
	
	public HeroShip(BufferedImage image, int maxLeft, int maxRight) {
		this.image = image;
		this.maxLeft = maxLeft;
		this.maxRight = maxRight;
		health = 10;
		livesLeft = 3;
		hitDuration = 0;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(posX - image.getWidth() / 2, posY - image.getHeight() / 2,
				image.getWidth(), image.getHeight());
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		if (posX <= maxLeft || posX >= maxRight) {
			return;
		}
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
		if (this.health <= 4) {
			isDamaged = true;
		}
	}

	public int getLivesLeft() {
		return livesLeft;
	}

	public void setLivesLeft(int livesLeft) {
		this.livesLeft = livesLeft;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public boolean isDamaged() {
		return isDamaged;
	}

	public void setDamaged(boolean isDamaged) {
		this.isDamaged = isDamaged;
	}

	public int getHitDuration() {
		return hitDuration;
	}

	public void setHitDuration(int hitDuration) {
		this.hitDuration = hitDuration;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	
}
