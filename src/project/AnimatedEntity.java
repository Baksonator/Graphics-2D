package project;

import java.awt.Graphics;

public class AnimatedEntity {

	private int posX, posY;
	private SpriteSheet mySheet;
	private int animationID = 0;
	private int animFrame = 0;
	private boolean animPlaying = false;
	private int frameInterval = 2;
	private int frameCountdown = 0;

	public AnimatedEntity(SpriteSheet sheet, int X, int Y) {
		posX = X;
		posY = Y;
		mySheet = sheet;
	}

	public void update() {
		if (animPlaying) {
			frameCountdown--;
			if (frameCountdown < 0) {
				animFrame = (animFrame + 1) % mySheet.getColumnCount();
				frameCountdown = frameInterval;
			}
		}
	}

	public void draw(Graphics g) {
		mySheet.drawTo(g, posX, posY, animFrame, animationID);
	}

	public void play() {
		animPlaying = true;
	}

	public void pause() {
		animPlaying = false;
	}

	public void stop() {
		animPlaying = false;
		animFrame = 0;
		frameCountdown = frameInterval;
	}
	
	public int getAnimation() {
		return animationID;
	}

	public void setAnimation(int anim) {
		if (anim >= 0 && anim < mySheet.getRowCount())
			animationID = anim;
	}

	public int getFrame() {
		return animFrame;
	}

	public void setFrame(int frame) {
		if (frame >= 0 && frame < mySheet.getColumnCount())
			animFrame = frame;
	}

	public boolean isPlaying() {
		return animPlaying;
	}

	public int getAnimationInterval() {
		return frameInterval;
	}

	public void setAnimationInterval(int i) {
		if (i >= 0)
			frameInterval = i;
	}

	public void setPosition(int x, int y) {
		posX = x;
		posY = y;
	}

	public int getPositionX() {
		return posX;
	}

	public int getPositionY() {
		return posY;
	}

	public void move(int movX, int movY) {
		posX += movX;
		posY += movY;
	}
}
