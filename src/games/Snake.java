package games;

import rafgfxlib.Util;

public class Snake extends GameObject {

	public Snake(int positionX, int positionY) {
		super(positionX, positionY);
		setImage(Util.loadImage("objects/snake.jpg"));
		setFrameName("SnakeFrame");
	}
	
	public Snake() {
		this(500, 500);
	}
}
