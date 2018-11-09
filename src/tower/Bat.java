package tower;

import java.util.Random;

public class Bat {
	int x, y;
	int life;
	int frame;

	public Bat(int sw, int sh, int life) {
		Random r = new Random();
		this.x = r.nextInt(sw/2) ;
		this.y = r.nextInt(sh/2);
		this.life = life;
		this.frame = r.nextInt() % 6;
	}

	public void move() {
		frame = (frame + 1) % 6;
		life--;
		Random r = new Random();
		int x1 = r.nextInt();
		int y1 = r.nextInt();
		if (x1 % 2 == 0)
			x += 4;
		else
			x -= 4;
		if (y1 % 2 == 0)
			y += 4;
		else
			y -= 4;
	}

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
}
