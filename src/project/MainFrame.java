package project;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import rafgfxlib.GameFrame;
import rafgfxlib.Util;

public class MainFrame extends GameFrame {

	private Tilemap mapa;
	
	private SpriteSheet heroSheet;
	private AnimatedEntity heroj;
	
	private static final int ANIM_DOWN = 0;
	private static final int ANIM_LEFT = 3;
	private static final int ANIM_UP = 2;
	private static final int ANIM_RIGHT = 1;
	
	private boolean isIntro = true;
	private int wordPos = 719;
	private boolean isCentered = false;
	private int centerTimer = 180;
	private BufferedImage[] introArray = new BufferedImage[3];
	private int currentIntroImage = 0;
	
	private boolean isPaused = false;
	private BufferedImage[] pausedArray = new BufferedImage[4];
	private boolean continueRed = false;
	private boolean exitRed = false;
	private int screenWidth;
	private int screenHeight;
	private BufferedImage noise;
	
	public MainFrame() {
		
		super("BANTer", 1080, 720);
		
		screenHeight = 720;
		screenWidth = 1080;
		
		setUpdateRate(60);
		
		mapa = new Tilemap();
		
		heroSheet = new SpriteSheet("tileset/charset/heroSet.png", 4, 4);
		heroSheet.setOffsets(32, 64);
		
		heroj = new AnimatedEntity(heroSheet, 556, 392);
		
		introArray[0] = Util.loadImage("tileset/fontset/TeamBANTerPresents.png");
		introArray[1] = Util.loadImage("tileset/fontset/EpicQuest.png");
		introArray[2] = Util.loadImage("tileset/fontset/TeamMembersList.png");
		
		pausedArray[0] = Util.loadImage("tileset/fontset/ContinueGame.png");
		pausedArray[1] = Util.loadImage("tileset/fontset/ContinueGameRed.png");
		pausedArray[2] = Util.loadImage("tileset/fontset/ExitGame.png");
		pausedArray[3] = Util.loadImage("tileset/fontset/ExitGameRed.png");
		noise = Util.loadImage("tileset/Noise.png");
		
		startThread();
	}

	private static final long serialVersionUID = -7327351674628534737L;

	@Override
	public void handleKeyDown(int keyCode) {
		if (keyCode == KeyEvent.VK_ESCAPE) {
			isPaused = true;
		}
		
		if(keyCode == KeyEvent.VK_DOWN)
		{
			heroj.setAnimation(ANIM_DOWN);
			heroj.play();
		}
		else if(keyCode == KeyEvent.VK_UP)
		{
			heroj.setAnimation(ANIM_UP);
			heroj.play();
		}
		else if(keyCode == KeyEvent.VK_LEFT)
		{
			heroj.setAnimation(ANIM_LEFT);
			heroj.play();
		}
		else if(keyCode == KeyEvent.VK_RIGHT)
		{
			heroj.setAnimation(ANIM_RIGHT);
			heroj.play();
		}
	}

	@Override
	public void handleKeyUp(int keyCode) {
		if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP ||
				keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT)
		{
			heroj.stop();
			heroj.setFrame(5);
		}
	}

	@Override
	public void handleMouseDown(int x, int y, GFMouseButton button) {
		if (isPaused) {
			if (button == GFMouseButton.Left) {
				if (x >= screenWidth / 2 - pausedArray[0].getWidth() / 2 &&
						x <= screenWidth / 2 + pausedArray[0].getWidth() / 2 &&
						y >= screenHeight / 3 && y <= screenHeight / 3 + pausedArray[0].getHeight()) {
					isPaused = false;
					continueRed = false;
				}
				if (x >= screenWidth / 2 - pausedArray[2].getWidth() / 2 &&
						x <= screenWidth / 2 + pausedArray[2].getWidth() / 2 &&
						y >= 2 * screenHeight / 3 && y <= 2 * screenHeight / 3 + pausedArray[2].getHeight()) {
					// zatvori prozor
				}
			}
		}
	}

	@Override
	public void handleMouseMove(int x, int y) {
		if (isPaused) {
			if (x >= screenWidth / 2 - pausedArray[0].getWidth() / 2 &&
					x <= screenWidth / 2 + pausedArray[0].getWidth() / 2 &&
					y >= screenHeight / 3 && y <= screenHeight / 3 + pausedArray[0].getHeight()) {
				continueRed = true;
			} else {
				continueRed = false;
			}
			if (x >= screenWidth / 2 - pausedArray[2].getWidth() / 2 &&
					x <= screenWidth / 2 + pausedArray[2].getWidth() / 2 &&
					y >= 2 * screenHeight / 3 && y <= 2 * screenHeight / 3 + pausedArray[2].getHeight()) {
				exitRed = true;
			} else {
				exitRed = false;
			}
		}
	}

	@Override
	public void handleMouseUp(int arg0, int arg1, GFMouseButton arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleWindowDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleWindowInit() {
		// TODO Auto-generated method stub
		
	}
	
	public void renderIntro(Graphics2D g, int sw, int sh) {
		g.setPaint(Color.WHITE);
		g.fillRect(0, 0, sw, sh);
		
		BufferedImage introImage = introArray[currentIntroImage];
		
		if (introImage.getHeight() / 2 + wordPos <= sh / 2 && centerTimer > 0) {
			isCentered = true;
		}
		
		if (isCentered && centerTimer > 0) {
			centerTimer--;
			g.drawImage(introImage, sw / 2 - introImage.getWidth() / 2, wordPos, null);
			return;
		}
		
		if (wordPos + introImage.getHeight() > 0) {
			g.drawImage(introImage, sw / 2 - introImage.getWidth() / 2, wordPos, null);
			wordPos -= 5;
		} else {
			currentIntroImage++;
			centerTimer = 180;
			isCentered = false;
			wordPos = 719;
			if (currentIntroImage >= introArray.length) {
				isIntro = false;
				isPaused = true;
			}
		}
	}
	
	public void renderPause(Graphics2D g, int sw, int sh) {
		g.drawImage(noise, 0, 0, null);
		
		if (!continueRed) {
			g.drawImage(pausedArray[0], sw / 2 - pausedArray[0].getWidth() / 2, sh / 3, null);
		} else {
			g.drawImage(pausedArray[1], sw / 2 - pausedArray[0].getWidth() / 2, sh / 3, null);
		}
		
		if (!exitRed) {
			g.drawImage(pausedArray[2], sw / 2 - pausedArray[2].getWidth() / 2, 2 * sh / 3, null);
		} else {
			g.drawImage(pausedArray[3], sw / 2 - pausedArray[2].getWidth() / 2, 2 * sh / 3, null);
		}
	}

	@Override
	public void render(Graphics2D g, int sw, int sh) {
		if (isIntro) {
			renderIntro(g, sw, sh);
			return;
		}
		
		if (isPaused) {
			renderPause(g, sw, sh);
			return;
		}
		
		int x0 = mapa.getCamX() / Tilemap.TILE_W;
		int x1 = x0 + (getWidth() / Tilemap.TILE_W) + 1;
		int y0 = mapa.getCamY() / Tilemap.TILE_H;
		int y1 = y0 + (getHeight() / Tilemap.TILE_H) + 1;
		
		if(x0 < 0) x0 = 0;
		if(y0 < 0) y0 = 0;
		if(x1 < 0) x1 = 0;
		if(y1 < 0) y1 = 0;
		
		if(x0 >= mapa.getMapW()) x0 = mapa.getMapW() - 1;
		if(y0 >= mapa.getMapH()) y0 = mapa.getMapH() - 1;
		if(x1 >= mapa.getMapW()) x1 = mapa.getMapW() - 1;
		if(y1 >= mapa.getMapH()) y1 = mapa.getMapH() - 1;
		
		Tile[] tileSet = mapa.getTileSet();
		for(int y = y0; y <= y1; ++y)
		{
			for(int x = x0; x <= x1; ++x)
			{
				g.drawImage(tileSet[0].image, 
						x * Tilemap.TILE_W + tileSet[0].offsetX - mapa.getCamX(), 
						y * Tilemap.TILE_H + tileSet[0].offsetY - mapa.getCamY(), 
						null);
			}
		}
		
		heroj.draw(g);
	}

	@Override
	public void update() {
		if(isKeyDown(KeyEvent.VK_LEFT)) {
			mapa.setCamX(mapa.getCamX() - 10);
		}
		else if(isKeyDown(KeyEvent.VK_RIGHT)) {
			mapa.setCamX(mapa.getCamX() + 10);
		}
		else if(isKeyDown(KeyEvent.VK_UP)) {
			mapa.setCamY(mapa.getCamY() - 10);
		}
		else if(isKeyDown(KeyEvent.VK_DOWN)) {
			mapa.setCamY(mapa.getCamY() + 10);
		}
		
		heroj.update();
	}

	public static void main(String[] args) {
		GameFrame gf = new MainFrame();
		gf.initGameWindow();
	}
}
