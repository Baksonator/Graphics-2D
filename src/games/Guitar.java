package games;

import rafgfxlib.Util;

public class Guitar extends GameObject {

	public Guitar(int positionX, int positionY) {
		super(positionX, positionY);
		setImage(Util.loadImage("objects/guitar_red.jpg"));
		setFrameName("GuitarHeroFrame");
	}
	
	public Guitar() {
		this(50, 50);
	}
	

}
