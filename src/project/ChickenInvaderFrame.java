package project;

import java.awt.Color;
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
	}

	@Override
	public String getName() {
		return "chickenFrame";
	}

	@Override
	public void handleKeyDown(int arg0) {
		// TODO Auto-generated method stub
		
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
