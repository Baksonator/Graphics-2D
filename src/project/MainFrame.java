package project;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import games.Chicken;
import games.GameObject;
import games.Guitar;
import games.Snake;
import imageGenerator.ImageCollector;
import project.Transition.TransitionType;
import rafgfxlib.GameHost;
import rafgfxlib.GameHost.GFMouseButton;
import rafgfxlib.GameState;

public class MainFrame extends GameState {

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

	private Guitar guitar;
	private Snake snake;
	private Chicken chicken;
	private GameObject[] games = new GameObject[3];
//	private BufferedImage guitarHero;
//	private int guitarPositionX = 0, guitarPositionY = 0;
//	private boolean guitarUp = false;
//	private int deltaGuitar = 1;
//	private int guitarTimer = 30;
//	private int guitarCam = 0;
	
	public MainFrame(GameHost host) {
		super(host);
		
		screenHeight = 720;
		screenWidth = 1080;
		
		mapa = new Tilemap();
		
		heroSheet = new SpriteSheet(ImageCollector.getHeroSet(), 4, 4);
		heroSheet.setOffsets(32, 64);
		
		heroj = new AnimatedEntity(heroSheet, 556, 392);
		
		introArray[0] = ImageCollector.getPresentation();
		introArray[1] = ImageCollector.getEpicQuest();
		introArray[2] = ImageCollector.getTeam();
		
		pausedArray[0] = ImageCollector.getContinueGame();
		pausedArray[1] = ImageCollector.getContinueGameRed();
		pausedArray[2] = ImageCollector.getExitGame();
		pausedArray[3] = ImageCollector.getExitGameRed();
		noise = ImageCollector.getNoise();
		
		guitar = new Guitar();
		snake = new Snake();
		chicken = new Chicken();
		games[0] = guitar;
		games[1] = snake;
		games[2] = chicken;
	}

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
		int x1 = x0 + (host.getWidth() / Tilemap.TILE_W) + 1;
		int y0 = mapa.getCamY() / Tilemap.TILE_H;
		int y1 = y0 + (host.getHeight() / Tilemap.TILE_H) + 1;
		
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
		
		
//		if ( guitarPositionY == 0) {
//			guitarPositionY = 4 * Tilemap.TILE_H + mapa.getTileSet()[0].offsetY - mapa.getCamY();
//			guitarCam = mapa.getCamY();
//		}
//		if (guitarUp) {
//			guitarPositionY = guitarPositionY + deltaGuitar - guitarCam;
//		} else {
//			guitarPositionY = guitarPositionY - deltaGuitar - guitarCam;
//		}
		
		for (GameObject game : games) {
			if (game.isDrawGame()) {
				g.drawImage(game.getImage(), game.getPositionX() - mapa.getCamX(), game.getPositionY() - mapa.getCamY(), null);
			}
		}
		
		heroj.draw(g);
		
	}
	
	// method that checks which game objects should be visible and if a player wants to play
	public void checkGameObjects() {
		for (GameObject game : games) {
			game.shouldDrawObject(mapa.getCamX(), mapa.getCamY(), screenWidth, screenHeight);
			
			if (game.isDrawGame()) {
				game.wantsToPlay(heroj.getPositionX(), heroj.getPositionY(), mapa.getCamX(), mapa.getCamY());
				if (game.isPlaying()) {
//					host.setState(game.getFrameName());
					TransitionType transType = TransitionType.LeftRightSquash;
					Transition.transitionTo(game.getFrameName(), transType, 1.0f);
					mapa.setCamX(0);
					mapa.setCamY(0);
				}
			}
		}
	}

	@Override
	public void update() {
		if(host.isKeyDown(KeyEvent.VK_LEFT)) {
			mapa.setCamX(mapa.getCamX() - 10);
		}
		else if(host.isKeyDown(KeyEvent.VK_RIGHT)) {
			mapa.setCamX(mapa.getCamX() + 10);
		}
		else if(host.isKeyDown(KeyEvent.VK_UP)) {
			mapa.setCamY(mapa.getCamY() - 10);
		}
		else if(host.isKeyDown(KeyEvent.VK_DOWN)) {
			mapa.setCamY(mapa.getCamY() + 10);
		}
		
		heroj.update();
		
		checkGameObjects();
		
//		if (guitarUp) {
//			guitarTimer--;
//			if (guitarTimer == 0) {
//				guitarUp = false;
//				guitarTimer = 30;
//			}
//		} else {
//			guitarTimer--;
//			if (guitarTimer == 0) {
//				guitarUp = true;
//				guitarTimer = 30;
//			}
//		}
	}

	@Override
	public String getName() {
		return "mainFrame";
	}

	@Override
	public boolean handleWindowClose() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resumeState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suspendState() {
		// TODO Auto-generated method stub
		
	}
}
