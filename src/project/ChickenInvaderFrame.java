package project;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

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
	
	public ChickenInvaderFrame(GameHost host) {
		super(host);
		
		screentHeight = host.getHeight();
		screenWidth = host.getWidth();
		
		backgroundColor = new Color(94, 63, 107);
		
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
		g.setPaint(backgroundColor);
		g.fillRect(0, 0, sw, sh);
		
		for (Star s : stars) {
			g.drawImage(s.getImage(), s.getPosX(), s.getPosY(), null);
		}
		
		for (HeroLaserShot heroLaserShot : heroLaserShots) {
			if (heroLaserShot.isRendered()) {
				g.drawImage(heroLaserShot.getImage(), heroLaserShot.getPosX() - heroLaserShot.getImage().getWidth() / 2,
						heroLaserShot.getPosY() - heroLaserShot.getImage().getHeight() / 2, null);
			}
		}
		
		for (Meteor m : meteors) {
			if (m.getCoolDown() == 0) {
				g.drawImage(m.getImage(), m.getPosX() - m.getImage().getWidth() / 2,
						m.getPosY() - m.getImage().getHeight() / 2, null);
			}
		}
		
		for (Meteor dm : destroyedMeteors) {
			if (dm.getCoolDown() == 0) {
				g.drawImage(dm.getImage(), dm.getPosX() - dm.getImage().getWidth() / 2,
						dm.getPosY() - dm.getImage().getHeight() / 2, null);
			}
		}
		
		for (EnemyShip enemy : enemyShips) {
			if (!enemy.isDead()) {
				g.drawImage(enemy.getImage(), enemy.getPosX() - enemy.getImage().getWidth() / 2,
						enemy.getPosY() + enemy.getImage().getHeight() / 2, null);
			}
		}
		
//		for (HeroLaserShot heroLaserShot : heroLaserShots) {
//			if (heroLaserShot.isRendered()) {
//				g.drawImage(heroLaserShot.getImage(), heroLaserShot.getPosX() - heroLaserShot.getImage().getWidth() / 2,
//						heroLaserShot.getPosY() - heroLaserShot.getImage().getHeight() / 2, null);
//			}
//		}
		
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
		g.drawImage(heroShip.getImage(), heroShip.getPosX() - normalShip.getWidth() / 2,
				heroShip.getPosY() - normalShip.getHeight(), null);
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
		for (Star s : stars) {
			s.setPosY(s.getPosY() + 5);
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
				if (m.getBounds().intersects(heroShip.getBounds())) {
					heroShip.setHealth(heroShip.getHealth() - 1);
					
					destroyedMeteors[br * 2].setCoolDown(0);
					destroyedMeteors[br * 2].setPosX(m.getPosX());
					destroyedMeteors[br * 2].setPosY(m.getPosY());
					destroyedMeteors[br * 2].setMoveX((heroShip.getPosX() - m.getPosX()) / 3);
					
					destroyedMeteors[br * 2 + 1].setCoolDown(0);
					destroyedMeteors[br * 2 + 1].setPosX(m.getPosX());
					destroyedMeteors[br * 2 + 1].setPosY(m.getPosY());
					destroyedMeteors[br * 2 + 1].setMoveX(-(heroShip.getPosX() - m.getPosX()) / 3);
					
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
				dm.setPosY(dm.getPosY() - 3);
				dm.setPosX(dm.getPosX() + dm.getMoveX());
				if (dm.getPosY() <= 0 || dm.getPosX() >= screenWidth || dm.getPosX() <= 0) {
					dm.setCoolDown(1);
				}
			}
		}
		
		for (EnemyShip enemy : enemyShips) {
			if (r.nextInt(10000) + 1 > 9900 && enemy.isDead()) {
				enemy.setDead(false);
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
