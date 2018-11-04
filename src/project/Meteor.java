package project;

import java.awt.image.BufferedImage;

public class Meteor {

	private int posX;
	private int posY;
	private BufferedImage image;
	private int coolDown;
	
	public Meteor(BufferedImage image) {
		this.image = image;
	}
}
