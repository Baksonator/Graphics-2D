package games;

import imageGenerator.ImageCollector;

public class Chicken extends GameObject {

	public Chicken(int positionX, int positionY) {
		super(positionX, positionY);
		setImage(ImageCollector.chickenObject);
		setFrameName("chickenFrame");
	}
	
	public Chicken() {
		this(300, 300);
	}

}
