package project;

import java.awt.image.BufferedImage;

public class HeroLaserShotHit {

	private int posX;
	private int posY;
	private BufferedImage image;
	private int life;
	private int duration;
	
	public HeroLaserShotHit(BufferedImage image) {
		this.image = image;
		life = 0;
		duration = 91;
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

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
}
