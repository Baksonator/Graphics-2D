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
import java.util.Random;

import imageGenerator.ImageCollector;
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
	
	private boolean isTransition = false;
	
	private int gameOverTime = 91;
	
	public ChickenInvaderFrame(GameHost host) {
		super(host);
		
		screentHeight = host.getHeight();
		screenWidth = host.getWidth();
		
		backgroundColor = new Color(94, 63, 107);
		
		isFilled = new boolean[screenWidth][screentHeight];
		size = screenWidth * screentHeight;
		bitWidth = getBitWidth(size);
		mask = RAND_MASKS[bitWidth - 1];
		gameOverImage = ImageCollector.getGameOver();
		
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
	
	private void resetParameters() {
		isTransition = true;
		
		finishedFizzle = false;
		rndval = 1;
		gameOverTime = 91;
		
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
		
		heroShip = new HeroShip(normalShip, 10 + normalShip.getWidth() / 2, screenWidth - 10 - normalShip.getWidth() / 2);
		heroShip.setPosX(screenWidth / 2);
		heroShip.setPosY(screentHeight - 10);
		
		isTransition = false;
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
	
	@Override
	public void render(Graphics2D g, int sw, int sh) {
		if (!isTransition) {
			if (heroShip.getHitDuration() > 0) {
				Point2D center = new Point2D.Float(sw / 2, sh / 2);
				float radius = (float)(Math.sqrt((sw - sw / 2) * (sw - sw / 2) + (sh - sh / 2) * (sh - sh / 2)));
				float dist[] = new float[2];
				if (heroShip.getHitDuration() > 45) {
					dist[0] = ((float)0.5f * (heroShip.getHitDuration() - 46) / 46) + 0.7f;
					if (dist[0] >= 1.0f) {
						dist[0] = 0.99f;
					}
					dist[1] = 1.0f;
				} else {
					dist[0] = ((float)0.5f * (46 - heroShip.getHitDuration()) / 46) + 0.7f;
					if (dist[0] >= 1.0f) {
						dist[0] = 0.99f;
					}
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
						g.drawImage(ImageCollector.blurredEnemy[(enemy.getHitDuration() - 1) / 5], enemy.getPosX() - enemy.getImage().getWidth() / 2,
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
		}
		
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
		if (finishedFizzle) {
			if (gameOverTime > 0) {
				gameOverTime--;
			} else {
				TransitionType transType = TransitionType.LeftRightSquash;
				Transition.transitionTo("mainFrame", transType, 1.0f);
				resetParameters();
			}
		}
		
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
					heroShip.setHitDuration(91);
					
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
		
		int cnt = 0;
		for (EnemyShip enemy : enemyShips) {
			if (r.nextInt(10000) + 1 > 9900 && enemy.isDead()) {
				enemy.setDead(false);
				enemy.setHealth(3);
				enemy.setHitDuration(0);
				enemy.setPosY(10 + cnt * enemy.getImage().getHeight());
				enemy.setPosX(enemy.getMaxLeft() 
						+ r.nextInt(enemy.getMaxRight() - enemy.getMaxLeft()));
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
			cnt++;
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
