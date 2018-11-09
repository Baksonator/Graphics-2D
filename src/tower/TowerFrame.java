package tower;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import imageGenerator.ImageCollector;
import project.SpriteSheet;
import project.Tile;
import project.Transition;
import project.Transition.TransitionType;
import rafgfxlib.GameHost;
import rafgfxlib.GameHost.GFMouseButton;
import rafgfxlib.GameState;
import rafgfxlib.Util;

public class TowerFrame extends GameState {

	private static final int TILE_W = 64;
	private static final int TILE_H = 64;

	private static final int TILE_W2 = 640;
	private static final int TILE_H2 = 60;

	private int camX = 0;
	private int camY = 0;
	private int camX2 = 0;
	private int cx = 0;
	private int cy = 0;

	private int frameInterval = 2;
	private int frameCountdown = 0;
	private int animFrame = 0;

	private Tile tile1, tile2;
	private SpriteSheet sheet, charSheetR, charSheetL;

	boolean jump = false;
	boolean fall = false;
	boolean right = false;
	boolean left = false;
	boolean last = true;
	boolean check = true;
	boolean check2 = false;
	private int grav = -35;

	private int gameOverTime = 61 * 30;

	public TowerFrame(GameHost host) {
		super(host);
		tile1 = new Tile(ImageCollector.getBackgroundTile(), 1, TILE_H);
		sheet = new SpriteSheet(Util.loadImage("twrRes/wallProba.png"), 1, 16);
		tile2 = new Tile(ImageCollector.getFence(), 1, TILE_H);

		charSheetL = new SpriteSheet(ImageCollector.getJumperL(), 14, 3);
		charSheetR = new SpriteSheet(ImageCollector.getJumperR(), 14, 3);
	}

	@Override
	public String getName() {
		return "TowerFrame";
	}

	@Override
	public void handleKeyDown(int keyCode) {
		if (keyCode == KeyEvent.VK_LEFT) {
			if (!right) {
				left = true;
				last = true;
			}
		}
		if (keyCode == KeyEvent.VK_RIGHT) {
			if (!left) {
				right = true;
				last = false;
			}
		}

		if (keyCode == KeyEvent.VK_UP) {
			jump = true;
		}

	}

	@Override
	public void handleKeyUp(int keyCode) {
		if (keyCode == KeyEvent.VK_LEFT) {
			left = false;
		}
		if (keyCode == KeyEvent.VK_RIGHT) {
			right = false;
		}

	}

	@Override
	public void handleMouseDown(int arg0, int arg1, GFMouseButton arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleMouseMove(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleMouseUp(int arg0, int arg1, GFMouseButton arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean handleWindowClose() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void render(Graphics2D g, int sw, int sh) {
		for (int y = -5; y <= 15; ++y) {
			for (int x = 0; x <= 20; ++x) {
				g.drawImage(tile1.image, x * TILE_W + tile1.offsetX - camX, y * TILE_H + tile1.offsetY - camY, null);
			}
		}

		frameCountdown--;
		if (frameCountdown < 0) {
			animFrame = (animFrame + 1) % sheet.getColumnCount();
			frameCountdown = frameInterval;
		}
		for (int y = -5; y <= 20; ++y) {
			if (y % 2 == 0) {
				sheet.drawTo(g, (sw - TILE_W2) / 2, y * TILE_H2 + tile1.offsetY - camY, 0, (camX2 + y) % 15);
			} else {
				sheet.drawTo(g, (sw - TILE_W2) / 2, y * TILE_H2 + tile1.offsetY - camY, 0, (camX2 + y + 8) % 15);
			}

		}

		Color c1 = new Color(0, 0, 0, 0);
		Color c2 = new Color(0, 0, 0, 255);

		GradientPaint gp = new GradientPaint(0, 0, c1, (sw - TILE_W2) / 2, 0, c2);
		g.setPaint(gp);
		g.fillRect(0, 0, (sw - TILE_W2) / 2, sh); // leva senka tornja

		gp = new GradientPaint((sw + TILE_W2) / 2, 0, c2, sw, 0, c1);
		g.setPaint(gp);
		g.fillRect((sw + TILE_W2) / 2, 0, sw, sh); // desna senka tornja

		gp = new GradientPaint(0, 0, c2, 0, 200, c1);
		g.setPaint(gp);
		g.fillRect(0, 0, sw, 200); // gornja maska

		// crtanje lika
		if (camY == 0)

		{
			if (right) {
				charSheetR.drawTo(g, sw / 2 + 0 - 20, sh / 2, camX2 % 8, 0);

			} else if (left) {
				charSheetL.drawTo(g, sw / 2 + 0 - 20, sh / 2, 13 - (camX2 % 8), 0);
			} else {
				if (last) {
					charSheetL.drawTo(g, sw / 2 + 0 - 20, sh / 2, 6, 0);
				} else {
					charSheetR.drawTo(g, sw / 2 + 0 - 20, sh / 2, 7, 0);
				}
			}
		} else {
			if (last) {
				charSheetL.drawTo(g, sw / 2 + 0 - 20, sh / 2, 3, 2);
			} else {
				charSheetR.drawTo(g, sw / 2 + 0 - 20, sh / 2, 11, 2);
			}
		}
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect((sw - TILE_W2) / 2, sh / 2 + 64 - camY, 640, 10);
		g.setColor(Color.DARK_GRAY);
		g.fillRect((sw - TILE_W2) / 2, sh / 2 + 74 - camY, 640, 10);
		g.setColor(Color.BLACK);
		g.fillRect((sw - TILE_W2) / 2, sh / 2 + 84 - camY, 640, 10);
	}

	@Override
	public void resumeState() {
		// TODO Auto-generated method stub

	}

	@Override
	public void suspendState() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {

		if (gameOverTime > 0) {
			// gameOverTime--;
		} else {
			TransitionType transType = TransitionType.LeftRightSquash;
			Transition.transitionTo("mainFrame", transType, 1.0f);
		}

		if (jump == true) {
			if (camY > 64) {
				camY = 0;
				// cy = 0;
				jump = false;
				grav = -35;
			} else {
				camY += grav;
				if (grav < 0) {
					grav -= 0.1 * (grav - 35);
					if (grav >= 0)
						grav = 2;

				} else {
					grav += grav * 0.5;
				}
			}
		}

		if (left == true) {

			camX += 2;
			if (camX > 64)
				camX = 0;
			camX2++;
			if (camX2 > 15)
				camX2 = 0;
		}
		if (right == true) {
			camX -= 2;
			if (camX < 0)
				camX = 64;
			camX2--;
			if (camX2 < 0)
				camX2 = 15;
		}

	}

}
