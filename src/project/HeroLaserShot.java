package project;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class HeroLaserShot {

	private int posX;
	private int posY;
	private BufferedImage image;
	private boolean isRendered = false;
	
	public HeroLaserShot(BufferedImage image) {
		this.image = image;
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

	public boolean isRendered() {
		return isRendered;
	}

	public void setRendered(boolean isRendered) {
		this.isRendered = isRendered;
	}
	
}
