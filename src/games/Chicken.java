package games;

import rafgfxlib.Util;

public class Chicken extends GameObject {

	public Chicken(int positionX, int positionY) {
		super(positionX, positionY);
		setImage(Util.loadImage("spaceart/png/player.png"));
		setFrameName("chickenFrame");
	}
	
	public Chicken() {
		this(300, 300);
	}

}
