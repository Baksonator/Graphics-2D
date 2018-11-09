package games;

import imageGenerator.ImageCollector;

public class Tower extends GameObject{

	public Tower(int positionX, int positionY) {
		super(positionX, positionY);
		setImage(ImageCollector.getTowerObject());
		setFrameName("TowerFrame");
	}
	
	public Tower() {
		this(500, 100);
	}

}
