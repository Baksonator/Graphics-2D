package guitarHero;

import java.awt.Color;
import java.util.Random;

/*
 * Circle - object that hero needs to collect.
 */
public class Circle {
	
	// current width and height of a circle, changes through time
	float circleWidth, circleHeight;
	int screenWidth, screenHeight;
	// random number from 1 to 3 that determines on which line (road) the circle moves
	int randomRoad;
	// speed of circle movement and size increment
	int speed = 150;
	// current circle coordinates
	float startX, startY;
	float deltaX, deltaY;
	// end circle coordinates
	float endX, endY;
	float beginSize = 10, endSize = 50;
	float sizeIncrement;
	// life time of a circle
	int time;
	// random color from -> BLUE, YELLOW, GREEN, WHITE, DARK_GRAY, CYAN
	Color color;
	
	Random r = new Random();
	
	Circle(int screenWidth, int screenHeight, int time) {
		this.randomRoad = Math.abs(r.nextInt()) % 3 + 1;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.circleHeight = beginSize;
		this.circleWidth = beginSize;
		this.time = time;
		setColor();
		
		startX = this.getStartCircleX(randomRoad, screenWidth);
		startY = this.getStartCircleY(randomRoad, screenHeight);
		// TODO: fix middle
		if (randomRoad != 2) {
			endX = this.getEndCircleX(randomRoad, screenWidth) - screenWidth / 25;
			endY = this.getEndCircleY(randomRoad, screenHeight);
		} else {
			endX = this.getEndCircleX(randomRoad, screenWidth) - screenWidth / 25;
			endY = this.getEndCircleY(randomRoad, screenHeight);
		}
		deltaX = (endX - startX) / speed;
		deltaY = (endY - startY) / speed;
		sizeIncrement = (endSize - beginSize) / speed;
	}
	
	Circle() {
		this(700, 700, 1);
	}
	
	public int getStartCircleX(int random, int screenWidth) {
		int position = screenWidth / 2;
		
		switch(random) {
		case 1:
			// left circle
			position = 5 * screenWidth / 12;
			break;
		case 2:
			// middle circle
			position = screenWidth / 2;
			break;
		case 3:
			// right circle
			position = 7 * screenWidth / 12;
			break;
		}
		
		return position;
	}
	
	public int getStartCircleY(int random, int screenHeight) {
		return screenHeight / 4;
	}
	
	public int getEndCircleX(int random, int screenWidth) {
		int position = screenWidth / 2;
		
		switch(random) {
		case 1:
			// left circle
			position = screenWidth / 3;
			break;
		case 2:
			// middle circle
			position = screenWidth / 2;
			break;
		case 3:
			// right circle
			position = 2 * screenWidth / 3;
			break;
		}
		
		return position;
	}

	public Color getColor() {
		return color;
	}

	public void setColor() {
		int random = Math.abs(r.nextInt()) % 5 + 1;
		Color c;
		switch(random) {
			case 1:
				c = Color.BLUE;
				break;
			case 2:
				c = Color.YELLOW;
				break;
			case 3:
				c = Color.GREEN;
				break;
			case 4:
				c = Color.WHITE;
				break;
			case 5:
				c = Color.DARK_GRAY;
				break;
			default:
				c = Color.CYAN;
		}
		this.color = c;
	}
	
	public int getEndCircleY(int random, int screenHeight) {
		return screenHeight;
	}

	public float getCircleWidth() {
		return circleWidth;
	}

	public void setCircleWidth(float circleWidth) {
		this.circleWidth = circleWidth;
	}

	public float getCircleHeight() {
		return circleHeight;
	}

	public void setCircleHeight(float circleHeight) {
		this.circleHeight = circleHeight;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public float getStartX() {
		return startX;
	}

	public void setStartX(float startX) {
		this.startX = startX;
	}

	public float getStartY() {
		return startY;
	}

	public void setStartY(float startY) {
		this.startY = startY;
	}

	public int getRandomRoad() {
		return randomRoad;
	}

	public void setRandomRoad(int randomRoad) {
		this.randomRoad = randomRoad;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public float getBeginSize() {
		return beginSize;
	}

	public void setBeginSize(float beginSize) {
		this.beginSize = beginSize;
	}

	public float getEndSize() {
		return endSize;
	}

	public void setEndSize(float endSize) {
		this.endSize = endSize;
	}

	public float getSizeIncrement() {
		return sizeIncrement;
	}

	public void setSizeIncrement(float sizeIncrement) {
		this.sizeIncrement = sizeIncrement;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
}
