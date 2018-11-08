package guitarHero;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import imageGenerator.ImageGenerator;
import project.AnimatedEntity;
import project.SpriteSheet;
import rafgfxlib.GameHost;
import rafgfxlib.GameHost.GFMouseButton;
import rafgfxlib.GameState;
import rafgfxlib.Util;

public class GuitarHeroFrame extends GameState {
	
	private static final long serialVersionUID = 12345678L;
	private GuitarHeroBackground guitarHeroBackground;
	private BufferedImage backgroundImage;
	private int width;
	private int height;
	private ArrayList<Circle> circles = new ArrayList<>();
	private int circlesCount = 30;
	private int pause = 0;
	private SpriteSheet heroSheet;
	private AnimatedEntity hero;
	private static final int ANIM_DOWN = 0;
	private static final int ANIM_LEFT = 1;
	private static final int ANIM_UP = 2;
	private static final int ANIM_RIGHT = 3;
	private int[] scoreCirce;
	private int score = 0;
	private Music music;
	
	private BeginEndIntro beginEndIntro;
	private BufferedImage introImage;
	private BufferedImage endImage;
	private boolean isIntro = true;
	private int wordPos = 719;
	private boolean isCentered = false;
	private int centerTimer = 180;
	private boolean isOver = false;
	
	private boolean isLeft = false;
	private boolean isRight = false;
	private int transitionTimer = 10;
	private int deltaTransition;
	
	private boolean musicOn = false;
	Random r = new Random();
	
	private int gameOverTime = 150;
	
	public GuitarHeroFrame(GameHost gameHost) {
		super(gameHost);
		
		width = gameHost.getWidth();
		height = gameHost.getHeight();
		scoreCirce = new int[circlesCount];
		
		for (int i = 0; i < circlesCount; i++) {
			pause += Math.abs(r.nextInt(40)) + 10;
			circles.add(new Circle(width, height, pause));
		}
		
		guitarHeroBackground = new GuitarHeroBackground();
		backgroundImage = Util.rasterToImage(guitarHeroBackground.getRaster());
		backgroundImage = ImageGenerator.scaleImage(backgroundImage, width, height);
		
		heroSheet = new SpriteSheet("tileset/charset/heroSet.png", 4, 4);
		heroSheet.setOffsets(32, 64);
		
		hero = new AnimatedEntity(heroSheet, width / 2, height);
		hero.setAnimation(ANIM_UP);
		
		music = new Music();
		beginEndIntro = new BeginEndIntro();
		introImage = beginEndIntro.getBeginImage();
		
		deltaTransition = width / 60;
	}
	
	public void setEverythingBack() {
		circlesCount = 30;
		pause = 0;
		isIntro = true;
		wordPos = 719;
		isCentered = false;
		centerTimer = 180;
		isOver = false;
		
		isLeft = false;
		isRight = false;
		transitionTimer = 10;
		
		new GuitarHeroFrame(host);
	}

	@Override
	public void handleKeyDown(int keyCode) {
		if(keyCode == KeyEvent.VK_DOWN) {
			hero.setAnimation(ANIM_DOWN);
			hero.play();
		}
		else if(keyCode == KeyEvent.VK_UP) {
			hero.setAnimation(ANIM_UP);
			hero.play();
		}
		else if (keyCode == KeyEvent.VK_LEFT) {
			hero.setAnimation(ANIM_RIGHT);
			hero.play();
		}
		else if (keyCode == KeyEvent.VK_RIGHT) {
			hero.setAnimation(ANIM_LEFT);
			hero.play();
		}
	}

	@Override
	public void handleKeyUp(int keyCode) {
		if (keyCode == KeyEvent.VK_RIGHT) {
			if (hero.getPositionX() < 2 * width / 3) {
				isRight = true;
				//hero.setPosition(hero.getPositionX() + width / 6, hero.getPositionY());
			}
		}
		if (keyCode == KeyEvent.VK_LEFT) {
			if (hero.getPositionX() > width / 3 + 1) {
				isLeft = true;
				//hero.setPosition(hero.getPositionX() - width / 6, hero.getPositionY());
			}
		}
		hero.setAnimation(ANIM_UP);
		hero.stop();
		hero.setFrame(5);
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

	
	public void renderImage(Graphics2D graphics, int width, int height, BufferedImage image) {
		graphics.setPaint(Color.WHITE);
		graphics.fillRect(0, 0, width, height);
		
		if (image.getHeight() / 2 + wordPos <= height / 2 && centerTimer > 0) {
			isCentered = true;
		}
		
		if (isCentered && centerTimer > 0) {
			centerTimer--;
			graphics.drawImage(image, width / 2 - image.getWidth() / 2, wordPos, null);
			return;
		}
		
		if (wordPos + image.getHeight() > 0) {
			graphics.drawImage(image, width / 2 - image.getWidth() / 2, wordPos, null);
			wordPos -= 5;
		} else {
			wordPos = 319;
			isCentered = false;
			centerTimer = 180;
			isIntro = false;
		}
	}
	
	@Override
	public void render(Graphics2D graphics, int width, int height) {
		//isIntro = false;
		if (isIntro) {
//			if (!musicOn) {
//				music.playMusic();
//			}
			renderImage(graphics, width, height, introImage);
		} else if (isOver) {
			renderImage(graphics, width, height, endImage);
//			music.stopMusic();
		} else {
			//music.playMusic();
			graphics.drawImage(backgroundImage, 0, 0, null);
			for (int i = 0; i < circlesCount; i++) {
				Circle circle = circles.get(i);
				if (circle.time <= 0) {
					graphics.setColor(circle.color);
					graphics.fillOval((int)circle.startX, (int)circle.startY, (int)circle.circleWidth, (int)circle.circleHeight);
				}
			}
			hero.draw(graphics);
		}
		
	}
	
	public void returnToMainFrame() {
		host.setState("mainFrame");
		setEverythingBack();
	}

	@Override
	public void update() {
		
		if (isOver) {
			gameOverTime--;
			
			 if (gameOverTime <= 0) {
				 returnToMainFrame();
		     }
			 
		}
		// if game has ended show score 
		if (circles.get(circlesCount - 1).startY >= height && endImage == null) {
			endImage = beginEndIntro.endScoreImage(score);
			isOver = true;
		}
		else if (!isIntro) {
		
			for (int i = 0; i < circlesCount; i++) {
				Circle circle = circles.get(i);
				circle.time--;
				
				if (circle.time <= 0) {
					// left circle
					if (circle.randomRoad == 1) {
						circle.startX += circle.deltaX;
						circle.startY += circle.deltaY;
					}
					// if it is a middle circle, it goes vertically
					else if (circle.randomRoad == 2) {
						circle.startY += circle.deltaY;
					}
					// right circle
					else {
						circle.startX += circle.deltaX;
						circle.startY += circle.deltaY;
					}
					circle.circleWidth += circle.sizeIncrement;
					circle.circleHeight += circle.sizeIncrement;
					
					// increase score if hero has collected the circle
					if (Math.abs(circle.startY - hero.getPositionY()) < circle.circleHeight && 
							Math.abs(circle.startX - hero.getPositionX()) < circle.circleWidth) {
						if (scoreCirce[i] == 0) {
							score++;
						}
						scoreCirce[i] = 1;
						
					}
				}
			}
		}
		
		if (isLeft) {
			hero.setPosition(hero.getPositionX() - deltaTransition, hero.getPositionY());
			hero.setAnimation(ANIM_UP);
			transitionTimer--;
			if (transitionTimer == 0) {
				isLeft = false;
				transitionTimer = 10;
			}
		}
		
		if (isRight && transitionTimer >= 0) {
			hero.setPosition(hero.getPositionX() + deltaTransition, hero.getPositionY());
			hero.setAnimation(ANIM_UP);
			transitionTimer--;
			if (transitionTimer == 0) {
				isRight = false;
				transitionTimer = 10;
			}
		}
		
		
		hero.update();
	}

	@Override
	public String getName() {
		return "GuitarHeroFrame";
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
