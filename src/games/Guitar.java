package games;

import imageGenerator.ImageCollector;

public class Guitar extends GameObject {

	public Guitar(int positionX, int positionY) {
		super(positionX, positionY);
		setImage(ImageCollector.guitarObject);
		setFrameName("GuitarHeroFrame");
	}
	
	public Guitar() {
		this(50, 50);
	}
	

}
