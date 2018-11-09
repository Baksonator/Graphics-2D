package games;

import imageGenerator.ImageCollector;

public class Snake extends GameObject {

	public Snake(int positionX, int positionY) {
		super(positionX, positionY);
		setImage(ImageCollector.snakeObject);
		setFrameName("SnakeFrame");
	}
	
	public Snake() {
		this(500, 500);
	}
}
