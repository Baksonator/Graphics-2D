package project;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import imageGenerator.ImageCollector;
import imageGenerator.ImageGenerator;
import project.Transition.TransitionType;
import rafgfxlib.GameHost;
import rafgfxlib.GameHost.GFMouseButton;
import rafgfxlib.GameState;
import rafgfxlib.Util;

public class ChickenInvaderFrame extends GameState {

	private int screenWidth;
	private int screentHeight;
	
	private Random r = new Random();
	
	private Color backgroundColor;
	private Star[] stars;
	
	private BufferedImage normalShip = Util.loadImage("spaceart/png/player.png");
	private BufferedImage leftShip = Util.loadImage("spaceart/png/playerLeft.png");
	private BufferedImage rightShip = Util.loadImage("spaceart/png/playerRight.png");
	private BufferedImage damagedShip = Util.loadImage("spaceart/png/playerDamaged.png");
	
	private HeroShip heroShip;
	
	private Meteor[] meteors;
	private Meteor[] destroyedMeteors;
	
	private EnemyShip[] enemyShips;
	
	private HeroLaserShot[] heroLaserShots;
	private HeroLaserShotHit[] heroLaserShotHit;
	
	private EnemyLaserShot[] enemyLaserShots;
	private EnemyLaserShotHit[] enemyLaserShotHit;
	
	private boolean finishedFizzle = false;
	private int rndval = 1;
	private boolean isFilled[][];
	private final static int RAND_MASKS[] = {
			0x00000001, 0x00000003, 0x00000006, 0x0000000C, 0x00000014, 0x00000030,
			0x00000060, 0x000000B8, 0x00000110, 0x00000240, 0x00000500, 0x00000CA0,
			0x00001B00, 0x00003500, 0x00006000, 0x0000B400, 0x00012000, 0x00020400,
			0x00072000, 0x00090000, 0x00140000, 0x00300000, 0x00400000, 0x00D80000,
			0x01200000, 0x03880000, 0x07200000, 0x09000000, 0x14000000, 0x32800000,
			0x48000000, 0xA3000000
	};
	private int size;
	private int bitWidth;
	private int mask;
	private BufferedImage gameOverImage;
	
	private BufferedImage heroSingle[];
	private BufferedImage heroColumn[];
	private BufferedImage heroSet;
	private BufferedImage noise;
	private BufferedImage basicTile;
	private BufferedImage charSet[];
	private BufferedImage emptyChar;
	private BufferedImage team;
	private BufferedImage continueGame;
	private BufferedImage continueGameRed;
	private BufferedImage exitGame;
	private BufferedImage exitGameRed;
	private BufferedImage epicQuest;
	private BufferedImage gameOver;
	private BufferedImage guitarHero;
	private BufferedImage presentation;
	private BufferedImage snake;
	
	public ChickenInvaderFrame(GameHost host) {
		super(host);
		
		screentHeight = host.getHeight();
		screenWidth = host.getWidth();
		
		prepareArt();
		
		backgroundColor = new Color(94, 63, 107);
		
		isFilled = new boolean[screenWidth][screentHeight];
		size = screenWidth * screentHeight;
		bitWidth = getBitWidth(size);
		mask = RAND_MASKS[bitWidth - 1];
		gameOverImage = ImageCollector.gameOver;
		
		stars = new Star[50];
		
		BufferedImage malaZvezda = Util.loadImage("spaceart/png/Background/starSmall.png");
		BufferedImage velikaZvezda = Util.loadImage("spaceart/png/Background/starBig.png");
		
		for (int i = 0; i < stars.length; i++) {
			if ((r.nextInt(100) + 1) + 17 >= 50) {
				stars[i] = new Star(malaZvezda, host.getWidth(), host.getHeight());
			} else {
				stars[i] = new Star(velikaZvezda, host.getWidth(), host.getHeight());
			}
		}
		
		heroShip = new HeroShip(normalShip, 10 + normalShip.getWidth() / 2, screenWidth - 10 - normalShip.getWidth() / 2);
		heroShip.setPosX(screenWidth / 2);
		heroShip.setPosY(screentHeight - 10);
		
		meteors = new Meteor[5];
		
		BufferedImage maliMeteor = Util.loadImage("spaceart/png/meteorSmall.png");
		BufferedImage velikiMeteor = Util.loadImage("spaceart/png/meteorBig.png");
		
		for (int i = 0; i < meteors.length; i++) {
			if (i < 2) {
				meteors[i] = new Meteor(velikiMeteor, host.getWidth());
			} else {
				meteors[i] = new Meteor(maliMeteor, host.getWidth());
			}
		}
		
		destroyedMeteors = new Meteor[meteors.length * 2];
		
		BufferedImage maliUnisteniMeteor = Util.loadImage("spaceart/png/meteorMinor.png");
		BufferedImage velikiUnisteniMeteor = Util.loadImage("spaceart/png/meteorSmall.png");
		
		for (int i = 0; i < destroyedMeteors.length; i++) {
			if (i < 4) {
				destroyedMeteors[i] = new Meteor(velikiUnisteniMeteor, host.getWidth());
			} else {
				destroyedMeteors[i] = new Meteor(maliUnisteniMeteor, host.getWidth());
			}
		}
		
		enemyShips = new EnemyShip[3];
		
		BufferedImage neprijateljSlika = Util.loadImage("spaceart/png/enemyShip.png");
		
		for (int i = 0; i < enemyShips.length; i++) {
			enemyShips[i] = new EnemyShip(neprijateljSlika, 10 + neprijateljSlika.getWidth() / 2,
					screenWidth - 10 - neprijateljSlika.getWidth() / 2);
			enemyShips[i].setPosY(10 + i * neprijateljSlika.getHeight());
			enemyShips[i].setPosX(enemyShips[i].getMaxLeft() 
					+ r.nextInt(enemyShips[i].getMaxRight() - enemyShips[i].getMaxLeft()));
			
		}
		
		heroLaserShots = new HeroLaserShot[10];
		
		for (int i = 0; i < heroLaserShots.length; i++) {
			heroLaserShots[i] = new HeroLaserShot(Util.loadImage("spaceart/png/laserGreen.png"));
		}
		
		heroLaserShotHit = new HeroLaserShotHit[enemyShips.length];
		
		for (int i = 0; i < heroLaserShotHit.length; i++) {
			heroLaserShotHit[i] = new HeroLaserShotHit(Util.loadImage("spaceart/png/laserGreenShot.png"));
		}
		
		enemyLaserShots = new EnemyLaserShot[30];
		
		for (int i = 0; i < enemyLaserShots.length; i++) {
			enemyLaserShots[i] = new EnemyLaserShot(Util.loadImage("spaceart/png/laserRed.png"));
		}
		
		enemyLaserShotHit = new EnemyLaserShotHit[30];
		
		for (int i = 0; i < enemyLaserShotHit.length; i++) {
			enemyLaserShotHit[i] = new EnemyLaserShotHit(Util.loadImage("spaceart/png/laserRedShot.png"));
		}
	}
	
	private int getBitWidth(int n) {
		int width = 0;
		
		while (n > 0) {
			n >>= 1;
			width++;
		}
		
		return width;
 	}
	
	private void prepareArt() {
		heroSingle = new BufferedImage[16];
		int column = 0;
		for (int i = 0; i < heroSingle.length; i++) {
			if (i != 0 && i % 4 == 0) {
				column++;
			}
			heroSingle[i] = ImageGenerator.scaleImageJava(ImageGenerator.cutTile((i % 4) * 16, column * 32,
					16, 32, Util.loadImage("tileset/character.png")), 64, 64);
		}
		
		heroColumn = new BufferedImage[4];
		for (int i = 0; i < heroColumn.length; i++) {
			BufferedImage[] helpArray = {heroSingle[4 * i], heroSingle[4 * i + 1], heroSingle[4 * i + 2],
					heroSingle[4 * i + 3]}; 
			heroColumn[i] = ImageGenerator.joinBufferedImageArray(helpArray);
		}
		
		heroSet = ImageGenerator.joinBufferedImageArrayVertical(heroColumn);
		
		WritableRaster raster = Util.createRaster(1080, 720, false);
		int rgb[] = new int[3];
		
		Random rnd = new Random();

		for(int y = 0; y < raster.getHeight(); y++)
		{
			for(int x = 0; x < raster.getWidth(); x++)
			{
				rgb[0] = rnd.nextInt(256);
				rgb[1] = rnd.nextInt(256);
				rgb[2] = rnd.nextInt(256);
				
				raster.setPixel(x, y, rgb);
			}
		}
		
		noise = Util.rasterToImage(raster);
		
		basicTile = ImageGenerator.scaleImageJava(ImageGenerator.cutTile(0, 0, 16, 16,
				Util.loadImage("tileset/Overworld.png")), 64, 64);
		
		charSet = new BufferedImage[52];
		column = 0;
		for (int i = 0; i < charSet.length; i++) {
			if (i != 0 && i % 26 == 0) {
				column++;
			}
			charSet[i] = ImageGenerator.scaleImageJava(ImageGenerator.cutTile((i % 26) * 8, column * 16,
					8, 16, Util.loadImage("tileset/font.png")), 64, 64);
		}
		
		emptyChar = ImageGenerator.scaleImageJava(ImageGenerator.cutTile(208, 0, 8, 16,
				Util.loadImage("tileset/font.png")), 64, 64);
		
		BufferedImage nevenaWord = ImageGenerator.joinLetterImages("Nevena Dresevic", charSet, emptyChar);
		BufferedImage markoWord = ImageGenerator.joinLetterImages("Marko Matovic", charSet, emptyChar);
		BufferedImage bogdanWord = ImageGenerator.joinLetterImages("Bogdan Bakarec", charSet, emptyChar);
		BufferedImage teamMembers = ImageGenerator.joinLetterImages("Team members", charSet, emptyChar);
		
		BufferedImage helpArray[] = new BufferedImage[4];
		
		helpArray[0] = teamMembers;
		helpArray[1] = nevenaWord;
		helpArray[2] = markoWord;
		helpArray[3] = bogdanWord;
		
		team = ImageGenerator.joinBufferedImageArrayVerticalCentered(helpArray);
		
		continueGame = ImageGenerator.joinLetterImages("Continue game", charSet, emptyChar);
		continueGameRed = ImageGenerator.promeniBoju(continueGame, Color.RED);
		
		exitGame = ImageGenerator.joinLetterImages("Exit game", charSet, emptyChar);
		exitGameRed = ImageGenerator.promeniBoju(exitGame, Color.RED);
		
		epicQuest = ImageGenerator.joinLetterImages("Epic Quest", charSet, emptyChar);
		
		gameOver = ImageGenerator.joinLetterImages("Game over", charSet, emptyChar);
		
		guitarHero = ImageGenerator.joinLetterImages("Guitar Hero", charSet, emptyChar);
		
		BufferedImage teamBANTer = ImageGenerator.joinLetterImages("Team BANTer", charSet, emptyChar);
		BufferedImage presents = ImageGenerator.joinLetterImages("presents", charSet, emptyChar);
		
		helpArray = new BufferedImage[2];
		helpArray[0] = teamBANTer;
		helpArray[1] = presents;
		
		presentation = ImageGenerator.joinBufferedImageArrayVerticalCentered(helpArray);
		
		snake = ImageGenerator.joinLetterImages("Snake", charSet, emptyChar);
	}

	@Override
	public String getName() {
		return "chickenFrame";
	}

	@Override
	public void handleKeyDown(int keyCode) {
		if (keyCode == KeyEvent.VK_SPACE) {
			for (HeroLaserShot laserShot : heroLaserShots) {
				if (!laserShot.isRendered()) {
					laserShot.setPosX(heroShip.getPosX());
					laserShot.setPosY(heroShip.getPosY() - heroShip.getImage().getHeight() / 2 - 10);
					laserShot.setRendered(true);
					break;
				}
			}
		}
	}

	@Override
	public void handleKeyUp(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMouseDown(int x, int y, GFMouseButton button) {
		if (button == GFMouseButton.Left) {
			TransitionType transType = TransitionType.LeftRightSquash;
			Transition.transitionTo("mainFrame", transType, 0.5f);
		}
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
	
	public void renderGameOver(Graphics2D g, int sw, int sh) {
		g.setPaint(Color.RED);
		for (int i = 0; i < 10000; i++) {
			if (!finishedFizzle) {
				
//				int x, y;
//				y = rndval & 0x000FF;
//				x = (rndval & 0x1FF00) >>> 8;
//				int lsb = rndval & 1;
//				rndval = rndval >> 1;
//				if (lsb == 1) {
//					rndval ^= 0x00012000;
//				}
//				if (x < 1080 && y < 720) {
//					isFilled[x][y] = true;
//				}
//				if (rndval == 1) {
//					finishedFizzle = true;
//				}
				
				
				int x = (int)(0.5 + rndval % screenWidth) | 0;
				int y = (int)(Math.ceil(rndval / screentHeight) - 1.0) | 0;
				
				if (x < screenWidth && y < screentHeight && x >= 0 && y >= 0) {
					isFilled[x][y] = true;
				}
				
				do {
					rndval = (rndval >> 1) ^ ((rndval & 1) * mask);
				} while (rndval > size);
				if (rndval == 1) {
					finishedFizzle = true;
				}
			}
		}
		for (int y = 0; y < screentHeight; y++) {
			for (int x = 0; x < screenWidth; x++) {
				if (isFilled[x][y]) {
					g.drawLine(x, y, x, y);
				}
			}
		}
		if (finishedFizzle) {
			g.drawImage(gameOverImage, screenWidth / 2 - gameOverImage.getWidth() / 2,
					screentHeight / 2 - gameOverImage.getHeight() / 2, null);
		}
	}
	
	private static void bilSampleA(WritableRaster src, float u, float v, int[] color)
	{
		float[] a = new float[4];
		float[] b = new float[4];
		
		int[] UL = new int[4];
		int[] UR = new int[4];
		int[] LL = new int[4];
		int[] LR = new int[4];

		int x0 = (int)u;
		int y0 = (int)v;
		int x1 = x0 + 1;
		int y1 = y0 + 1;
		
		u -= x0;
		v -= y0;
		
		if(x0 < 0) x0 = 0;
		if(y0 < 0) y0 = 0;
		if(x1 < 0) x1 = 0;
		if(y1 < 0) y1 = 0;
		
		if(x0 >= src.getWidth()) x0 = src.getWidth() - 1;
		if(y0 >= src.getHeight()) y0 = src.getHeight() - 1;
		if(x1 >= src.getWidth()) x1 = src.getWidth() - 1;
		if(y1 >= src.getHeight()) y1 = src.getHeight() - 1;
		
		src.getPixel(x0, y0, UL);
		src.getPixel(x1, y0, UR);
		src.getPixel(x0, y1, LL);
		src.getPixel(x1, y1, LR);
		
		Util.lerpRGBAif(UL, UR, u, a);
		Util.lerpRGBAif(LL, LR, u, b);
		
		color[0] = (int)(Util.lerpF(a[0], b[0], v));
		color[1] = (int)(Util.lerpF(a[1], b[1], v));
		color[2] = (int)(Util.lerpF(a[2], b[2], v));
		color[3] = (int)(Util.lerpF(a[3], b[3], v));
		
		Util.clampRGBA(color);
	}
	
	private BufferedImage blurImage(BufferedImage image, int radius) {
		WritableRaster source = image.getRaster();
		WritableRaster target = Util.createRaster(image.getWidth(), image.getHeight(), true);
		
		int rgb[] = new int[4];
		int accum[] = new int[4];
		
		int sampleCount = 32;
		
		int width = source.getWidth();
		int height = source.getHeight();
		
		for(int y = 0; y < height; y++)
		{	
			for(int x = 0; x < width; x++)
			{
				accum[0] = 0; accum[1] = 0; accum[2] = 0;
				
				int help[] = new int[4];
				source.getPixel(x, y, help);
				
				for(int i = 0; i < sampleCount; i++)
				{
					// Za svaki uzorak odredjujemo nasumicnu koordinatu unutar
					// (radius*2) x (radius*2) kvadrata oko trenutnog piksela
					float X = (float)(Math.random() - 0.5) * radius * 2.0f;
					float Y = (float)(Math.random() - 0.5) * radius * 2.0f;
					
					// Uzimamo bilinearan uzorak
					bilSampleA(source, x + X, y + Y, rgb);
					
					// I dodajemo ga na sumu
					accum[0] += rgb[0];
					accum[1] += rgb[1];
					accum[2] += rgb[2];
				}
				
				// Konacan piksel nam je prosjek uzetih uzoraka
				rgb[0] = accum[0] / sampleCount;
				rgb[1] = accum[1] / sampleCount;
				rgb[2] = accum[2] / sampleCount;
				rgb[3] = help[3];
				
				target.setPixel(x, y, rgb);
			}
		}
		
		return Util.rasterToImage(target);
	}

	@Override
	public void render(Graphics2D g, int sw, int sh) {
		if (heroShip.getHitDuration() > 0) {
			Point2D center = new Point2D.Float(sw / 2, sh / 2);
			float radius = (float)(Math.sqrt((sw - sw / 2) * (sw - sw / 2) + (sh - sh / 2) * (sh - sh / 2)));
			float dist[] = new float[2];
			if (heroShip.getHitDuration() > 45) {
				dist[0] = ((float)(heroShip.getHitDuration() - 46) / 46);
				dist[1] = 1.0f;
			} else {
				dist[0] = ((float)(46 - heroShip.getHitDuration()) / 46);
				dist[1] = 1.0f;
			}
			Color[] colors = {backgroundColor, Color.RED};
			RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);
			g.setPaint(p);
		} else {
			g.setPaint(backgroundColor);
		}
		
		g.fillRect(0, 0, sw, sh);
		
		for (Star s : stars) {
			g.drawImage(s.getImage(), s.getPosX(), s.getPosY(), null);
		}
		
		AffineTransform transform = new AffineTransform();
		
		for (Meteor m : meteors) {
			if (m.getCoolDown() == 0) {
				transform.setToIdentity();
				transform.translate(m.getPosX(), m.getPosY());
				transform.rotate(m.getAngle());
				transform.translate(-m.getImage().getWidth() / 2, -m.getImage().getHeight() / 2);
				
				g.drawImage(m.getImage(), transform, null);
			}
		}
		
		for (Meteor dm : destroyedMeteors) {
			if (dm.getCoolDown() == 0) {
				transform.setToIdentity();
				transform.translate(dm.getPosX(), dm.getPosY());
				transform.rotate(dm.getAngle());
				transform.translate(-dm.getImage().getWidth() / 2, -dm.getImage().getHeight() / 2);
				
				g.drawImage(dm.getImage(), transform, null);
			}
		}
		
		for (EnemyShip enemy : enemyShips) {
			if (!enemy.isDead()) {
				if (enemy.getHitDuration() > 0) {
					BufferedImage blurredImage = blurImage(enemy.getImage(), enemy.getHitDuration() / 5);
					g.drawImage(blurredImage, enemy.getPosX() - enemy.getImage().getWidth() / 2,
							enemy.getPosY() + enemy.getImage().getHeight() / 2, null);
				} else {
					g.drawImage(enemy.getImage(), enemy.getPosX() - enemy.getImage().getWidth() / 2,
							enemy.getPosY() + enemy.getImage().getHeight() / 2, null);
				}
			}
		}
		
		for (HeroLaserShot heroLaserShot : heroLaserShots) {
			if (heroLaserShot.isRendered()) {
				g.drawImage(heroLaserShot.getImage(), heroLaserShot.getPosX() - heroLaserShot.getImage().getWidth() / 2,
						heroLaserShot.getPosY() - heroLaserShot.getImage().getHeight() / 2, null);
			}
		}
		
		for (EnemyLaserShot enemyLaserShot : enemyLaserShots) {
			if (enemyLaserShot.isRendered()) {
				g.drawImage(enemyLaserShot.getImage(), enemyLaserShot.getPosX() - enemyLaserShot.getImage().getWidth() / 2,
						enemyLaserShot.getPosY() - enemyLaserShot.getImage().getHeight() / 2, null);
			}
		}
		
		Composite prevComp = g.getComposite();
		for (HeroLaserShotHit heroLaserShotHit : heroLaserShotHit) {
			if (heroLaserShotHit.getLife() > 0) {
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						(float)heroLaserShotHit.getLife() / (float)heroLaserShotHit.getDuration()));
				g.drawImage(heroLaserShotHit.getImage(), heroLaserShotHit.getPosX() - 
						heroLaserShotHit.getImage().getWidth() / 2, heroLaserShotHit.getPosY() - 
						heroLaserShotHit.getImage().getHeight() / 2, null);
			}
		}
		
		g.setComposite(prevComp);
		
		if (heroShip.getHitDuration() > 0) {
			if ((heroShip.getHitDuration() < 61 && heroShip.getHitDuration() >= 50) ||
					(heroShip.getHitDuration() < 41 && heroShip.getHitDuration() >= 30) ||
					(heroShip.getHitDuration() < 21 && heroShip.getHitDuration() >= 10) ||
					(heroShip.getHitDuration() < 81 && heroShip.getHitDuration() >= 70)) {
				g.drawImage(heroShip.getImage(), heroShip.getPosX() - normalShip.getWidth() / 2,
						heroShip.getPosY() - normalShip.getHeight(), null);
			}
		} else {
			g.drawImage(heroShip.getImage(), heroShip.getPosX() - normalShip.getWidth() / 2,
					heroShip.getPosY() - normalShip.getHeight(), null);
		}
		
		for (EnemyLaserShotHit enemyLaserShotHit : enemyLaserShotHit) {
			if (enemyLaserShotHit.getLife() > 0) {
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						(float)enemyLaserShotHit.getLife() / (float)enemyLaserShotHit.getDuration()));
				g.drawImage(enemyLaserShotHit.getImage(), enemyLaserShotHit.getPosX() - 
						enemyLaserShotHit.getImage().getWidth() / 2, enemyLaserShotHit.getPosY() - 
						enemyLaserShotHit.getImage().getHeight() / 2, null);
			}
		}
		
		g.setComposite(prevComp);
		
		if (heroShip.isDead()) {
			renderGameOver(g, sw, sh);
		}
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
		if (heroShip.isDead()) {
			return;
		}
		
		for (Star s : stars) {
			s.setPosY(s.getPosY() + s.getMoveY());
			if (s.getPosY() >= screentHeight) {
				s.setPosY(s.getPosY() - screentHeight);
			}
		}
		
		int br = 0;
		for (Meteor m : meteors) {
			if (m.getCoolDown() == 0) {
				m.setPosY(m.getPosY() + 5);
				if (m.getPosY() >= screentHeight) {
					m.setCoolDown((r.nextInt(30) + 1) * 61);
					m.setPosX(r.nextInt(screenWidth));
					m.setPosY(-m.getImage().getHeight() / 2);
				}
				m.setAngle(m.getAngle() + m.getRotation());
				if (m.getBounds().intersects(heroShip.getBounds())) {
					heroShip.setHealth(heroShip.getHealth() - 1);
					
					destroyedMeteors[br * 2].setCoolDown(0);
					destroyedMeteors[br * 2].setPosX(m.getPosX());
					destroyedMeteors[br * 2].setPosY(m.getPosY());
					destroyedMeteors[br * 2].setMoveX(5 - r.nextInt(11));
					destroyedMeteors[br * 2].setMoveY(1 + r.nextInt(5));
					
					destroyedMeteors[br * 2 + 1].setCoolDown(0);
					destroyedMeteors[br * 2 + 1].setPosX(m.getPosX());
					destroyedMeteors[br * 2 + 1].setPosY(m.getPosY());
					destroyedMeteors[br * 2 + 1].setMoveX(5 - r.nextInt(11));
					destroyedMeteors[br * 2 + 1].setMoveY(1 + r.nextInt(5));
					
					m.setCoolDown((r.nextInt(30) + 1) * 61);
					m.setPosX(r.nextInt(screenWidth));
					m.setPosY(-m.getImage().getHeight() / 2);
				}
			} else {
				m.setCoolDown(m.getCoolDown() - 1);
			}
			
			br++;
		}
		
		for (Meteor dm : destroyedMeteors) {
			if (dm.getCoolDown() == 0) {
				dm.setPosY(dm.getPosY() - dm.getMoveY());
				dm.setPosX(dm.getPosX() + dm.getMoveX());
				if (dm.getPosY() <= 0 || dm.getPosX() >= screenWidth || dm.getPosX() <= 0) {
					dm.setCoolDown(1);
				}
				dm.setAngle(dm.getAngle() + dm.getRotation());
			}
		}
		
		for (EnemyShip enemy : enemyShips) {
			if (r.nextInt(10000) + 1 > 9900 && enemy.isDead()) {
				enemy.setDead(false);
				enemy.setHealth(3);
			}
			if (!enemy.isDead()) {
				enemy.setHitDuration(enemy.getHitDuration() - 1);
			}
			if (!enemy.isDead() && r.nextInt(100) == 43) {
				for (EnemyLaserShot enemyLaserShot : enemyLaserShots) {
					if (!enemyLaserShot.isRendered()) {
						enemyLaserShot.setPosX(enemy.getPosX());
						enemyLaserShot.setPosY(enemy.getPosY() + enemy.getImage().getHeight() / 2 + 10);
						enemyLaserShot.setRendered(true);
						break;
					}
				}
			}
		}
		
		for (HeroLaserShot heroLaserShot : heroLaserShots) {
			if (heroLaserShot.isRendered()) {
				heroLaserShot.setPosY(heroLaserShot.getPosY() - 5);
				if (heroLaserShot.getPosY() <= 0) {
					heroLaserShot.setRendered(false);
				}
				
				int nrEnemy = 0;
				for (EnemyShip enemy : enemyShips) {
					if (!enemy.isDead() && heroLaserShot.getBounds().intersects(enemy.getBounds())) {
						enemy.setHealth(enemy.getHealth() - 1);
						enemy.setHitDuration(100);
						
						heroLaserShot.setRendered(false);
						
						heroLaserShotHit[nrEnemy].setPosX(heroLaserShot.getPosX());
						heroLaserShotHit[nrEnemy].setPosY(enemy.getPosY() + enemy.getImage().getHeight() / 2 + 5);
						heroLaserShotHit[nrEnemy].setLife(91);
					}
					nrEnemy++;
				}
			}
		}
		
		for (HeroLaserShotHit heroLaserShotHit : heroLaserShotHit) {
			if (heroLaserShotHit.getLife() > 0) {
				heroLaserShotHit.setLife(heroLaserShotHit.getLife() - 1);
			}
		}
		
		int nrShot = 0;
		for (EnemyLaserShot enemyLaserShot : enemyLaserShots) {
			if (enemyLaserShot.isRendered()) {
				enemyLaserShot.setPosY(enemyLaserShot.getPosY() + 5);
				if (enemyLaserShot.getPosY() >= screentHeight) {
					enemyLaserShot.setRendered(false);
				}
				
				if (enemyLaserShot.getBounds().intersects(heroShip.getBounds())) {
					heroShip.setHealth(heroShip.getHealth() - 1);
					heroShip.setHitDuration(91);
					
					enemyLaserShot.setRendered(false);
					
					enemyLaserShotHit[nrShot].setPosX(enemyLaserShot.getPosX());
					enemyLaserShotHit[nrShot].setPosY(heroShip.getPosY() - heroShip.getImage().getHeight() / 2 - 5);
					enemyLaserShotHit[nrShot].setLife(91);
				}
			}
			
			nrShot++;
		}
		
		for (EnemyLaserShotHit enemyLaserShotHit : enemyLaserShotHit) {
			if (enemyLaserShotHit.getLife() > 0) {
				enemyLaserShotHit.setLife(enemyLaserShotHit.getLife() - 1);
			}
		}
		
		heroShip.setHitDuration(heroShip.getHitDuration() - 1);
		
		if (host.isKeyDown(KeyEvent.VK_LEFT)) {
			heroShip.setImage(leftShip);
			heroShip.setPosX(heroShip.getPosX() - 5);
		} else if (host.isKeyDown(KeyEvent.VK_RIGHT)) {
			heroShip.setImage(rightShip);
			heroShip.setPosX(heroShip.getPosX() + 5);
		} else {
			if (!heroShip.isDamaged()) {
				heroShip.setImage(normalShip);
			} else {
				heroShip.setImage(damagedShip);
			}
		}
	}
	
}
