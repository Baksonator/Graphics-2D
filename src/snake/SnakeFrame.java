package snake;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import project.AnimatedEntity;
import project.SpriteSheet;
import rafgfxlib.GameHost;
import rafgfxlib.GameHost.GFMouseButton;
import rafgfxlib.GameState;

public class SnakeFrame extends GameState{
	
	private SnakeImages images;
	private boolean isIntro = true;
	private int wordPos = 719;
	private boolean isCentered = false;
	private int centerTimer = 180;
	private boolean isOver = false;
	private Apple apple;
	private int width, height;
	
	private ArrayList<Rectangle> snakeBody = new ArrayList<>();
	private int bodyWidth = 25, bodyHeight = 25;
	private int headX, headY;
	private int tailX, tailY;
	private int direction = 0;
	// speed for the snake
	private int timer = 10;
	
	private SpriteSheet heroSheet;
	private AnimatedEntity hero;
	private static final int ANIM_DOWN = 0;
	private static final int ANIM_LEFT = 1;
	private static final int ANIM_UP = 2;
	private static final int ANIM_RIGHT = 3;
	
	private static final int SMALL_APPLE_MAX = 100;
	private SmallApple[] smallApples = new SmallApple[SMALL_APPLE_MAX];
	
	private boolean eatenApple = false;
	private int spinTime = 50;
	private int deadTime = 70;
	private int gameOverTime = 150;
	
	public SnakeFrame(GameHost host) {
		super(host);
		
		width = host.getWidth();
		height = host.getHeight();
		apple = new Apple(width, height);
		
		headX = width / 2;
		headY = height / 2;
		tailX = headX;
		tailY = headY;
		
		// add head
		snakeBody.add(new Rectangle(headX, headY, bodyWidth, bodyHeight));
		for (int i = 1; i < 3; i++) {
			tailY += bodyWidth;
			Rectangle rec = new Rectangle(tailX, tailY, bodyWidth, bodyHeight);
			snakeBody.add(rec);
		}
		
		heroSheet = new SpriteSheet("tileset/charset/heroSet.png", 4, 4);
		heroSheet.setOffsets(32, 64);
		
		hero = new AnimatedEntity(heroSheet, headX + bodyHeight, headY + bodyWidth);
		hero.setAnimation(ANIM_UP);
		
		images = new SnakeImages();
		
		for (int i = 0; i < SMALL_APPLE_MAX; i++) {
			smallApples[i] = new SmallApple();
		}
		
	}
	
	public void setEverythingBack() {
		isIntro = true;
		wordPos = 719;
		isCentered = false;
		centerTimer = 180;
		isOver = false;
		direction = 0;
		timer = 10;
		SmallApple[] smallApples = new SmallApple[SMALL_APPLE_MAX];
		
		eatenApple = false;
		spinTime = 50;
		deadTime = 70;
		gameOverTime = 150;
		
		new SnakeFrame(host);
	}

	@Override
	public String getName() {
		return "SnakeFrame";
	}

	@Override
	public void handleKeyDown(int keyCode) {
		/*   	UP
		    LEFT + RIGHT
		      	DOWN
	      		  0
			   3  +  1
			      2 
		*/
		if (keyCode == KeyEvent.VK_DOWN && direction != 0) {
			hero.setAnimation(ANIM_DOWN);
			hero.play();
			direction = 2;
		}
		else if (keyCode == KeyEvent.VK_UP && direction != 2) {
			hero.setAnimation(ANIM_UP);
			hero.play();
			direction = 0;
		}
		else if (keyCode == KeyEvent.VK_LEFT && direction != 1) {
			hero.setAnimation(ANIM_RIGHT);
			hero.play();
			direction = 3;
		}
		else if (keyCode == KeyEvent.VK_RIGHT && direction != 3) {
			hero.setAnimation(ANIM_LEFT);
			hero.play();
			direction = 1;
		}
	}

	@Override
	public void handleKeyUp(int keyCode) {
		if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP ||
				keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
			moveSnake();
	    }
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

	@Override
	public boolean handleWindowClose() {
		// TODO Auto-generated method stub
		return false;
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
		// effect when snake eats itself
		if (isOver && deadTime >= 0) {
			
			graphics.drawImage(images.createBackgroundImage(width, height), 0, 0, null);
			graphics.drawImage(apple.getImage(), apple.getPositionX(), apple.getPositionY(), null);
			
			if (deadTime % 7 == 0) {
				
				graphics.setColor(Color.RED);
				for (int i = 0; i < snakeBody.size(); i++) {
					Rectangle rec = snakeBody.get(i);
					graphics.setColor(Color.RED);
					graphics.fillRect(rec.x, rec.y, rec.width, rec.height);
					graphics.setColor(Color.GREEN);
					graphics.drawRect(rec.x, rec.y, rec.width, rec.height);
				}
				hero.setPosition(snakeBody.get(0).x + bodyWidth, snakeBody.get(0).y + bodyHeight);
				hero.draw(graphics);
			}
			
		} else if (isOver) {
			renderImage(graphics, width, height, images.getEndImage());
		} else if (isIntro) {
			renderImage(graphics, width, height, images.getIntroImage());
		} else {
			graphics.drawImage(images.createBackgroundImage(width, height), 0, 0, null);
			graphics.drawImage(apple.getImage(), apple.getPositionX(), apple.getPositionY(), null);
			
			graphics.setColor(Color.RED);
			for (int i = 0; i < snakeBody.size(); i++) {
				Rectangle rec = snakeBody.get(i);
				graphics.setColor(Color.RED);
				graphics.fillRect(rec.x, rec.y, rec.width, rec.height);
				graphics.setColor(Color.GREEN);
				graphics.drawRect(rec.x, rec.y, rec.width, rec.height);
			}
			hero.setPosition(snakeBody.get(0).x + bodyWidth, snakeBody.get(0).y + bodyHeight);
			hero.draw(graphics);
			
			if (eatenApple && spinTime >= 0) {
				if (spinTime % 4 == 0) spinHero();
				hero.draw(graphics);
				if (spinTime == 1) faceHero();
			}
			
			// draw small apples
			AffineTransform transform = new AffineTransform(); 
			for (int i = 0; i < smallApples.length; i++) {
				if (smallApples[i].life >= 0) {
					transform.setToIdentity();
					transform.translate(smallApples[i].posX, smallApples[i].posY);
					transform.rotate(smallApples[i].angle);
					transform.translate(-16.0, -16.0);
					
					graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
		                    (float)smallApples[i].life / (float)smallApples[i].lifeMax));

					graphics.drawImage(smallApples[i].getSmallAppleImage(), transform, null);
				}
			}
			
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

	// keeps snake moving depending on the direction
	public void moveSnake () {
		 // move the body
        for (int i = snakeBody.size() - 1; i > 0; i--) {
        	snakeBody.get(i).x = snakeBody.get(i - 1).x;
        	snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }

        // move the head
        // left
        if (direction == 3) {
        	snakeBody.get(0).x -= bodyWidth;
        }
        // right
        else if (direction == 1) {
        	snakeBody.get(0).x += bodyWidth;
        }
        // up
        else if (direction == 0) {
        	snakeBody.get(0).y -= bodyHeight; 
        }
        // down
        else if (direction == 2) {
        	snakeBody.get(0).y += bodyHeight; 
        }
	}
	
	// if snake ate apple, create new apple and extend body
	public boolean checkForApple() {
		Rectangle head = snakeBody.get(0);
		tailX = snakeBody.get(snakeBody.size() - 1).x;
		tailY = snakeBody.get(snakeBody.size() - 1).y;
		if ((Math.abs(head.x - apple.getPositionX()) <= 20) && (Math.abs(head.y - apple.getPositionY()) <= 20)) {
			Rectangle newRect = new Rectangle(tailX, tailY + bodyHeight, bodyWidth, bodyHeight);
			snakeBody.add(newRect);
			makeSmallApples(apple.getPositionX(), apple.getPositionY(), 3.0f, 200, 5);
			newApple();
			return true;
        }
		return false;
	}
	
	public void spinHero() {
		if (hero.getAnimation() == ANIM_DOWN) {
			hero.setAnimation(ANIM_RIGHT);
		} else if (hero.getAnimation() == ANIM_RIGHT) {
			hero.setAnimation(ANIM_UP);
		} else if (hero.getAnimation() == ANIM_UP) {
			hero.setAnimation(ANIM_LEFT);
		} else if (hero.getAnimation() == ANIM_LEFT) {
			hero.setAnimation(ANIM_DOWN);
		}
		hero.play();
	}
	
	public void faceHero() {
		if (direction == 2) {
			hero.setAnimation(ANIM_DOWN);
		}
		else if (direction == 0) {
			hero.setAnimation(ANIM_UP);
		}
		else if (direction == 3) {
			hero.setAnimation(ANIM_RIGHT);
		}
		else if (direction == 1) {
			hero.setAnimation(ANIM_LEFT);
		}
		hero.play();
	}
	
	// create new apple
	public void newApple() {
		apple.setRandomPositions();
		for (int i = snakeBody.size() - 1; i >= 0; i--) {
			Rectangle body = snakeBody.get(i);
			if ((Math.abs(body.x - apple.getPositionX()) <= 10) && (Math.abs(body.y - apple.getPositionY()) <= 10)) {
				apple.setPositionX(apple.getPositionX() + 10);
				apple.setPositionY(apple.getPositionY() + 10);
			}
		}
	}
	
	// if snake hit the screen or bit herself, end the game
	public void checkCollisions() {
		Rectangle head = snakeBody.get(0);
		// ate herself
		for (int i = snakeBody.size() - 1; i > 0; i--) {
			Rectangle body = snakeBody.get(i);
			if ((Math.abs(head.x - body.x) < bodyWidth / 2) && (Math.abs(head.y - body.y) < bodyHeight / 2)) {
				isOver = true;
			}
        }
		// screen boundaries
		if (head.x >= width || head.y >= height ||
				head.x <= 0 || head.y <= 0) {
			isOver = true;
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
			deadTime--;
			
			 if (gameOverTime <= 0) {
				 returnToMainFrame();
		     }
		} else {
			
			timer--;
			if (timer <= 0 && !isIntro && !isOver) {
				moveSnake();
				timer = 10;
			} 
			
			if (checkForApple()) {
				eatenApple = true;
			}
			
			if (eatenApple) {
				spinTime--;
			}
			
			if (spinTime <= 0) {
				eatenApple = false;
				spinTime = 50;
			}
			
			checkCollisions();
			
			for (SmallApple smallApple : smallApples) {
				if (smallApple.life >= 0) {
					smallApple.updateSmallApple();
				}
			}
		}
		
	}
	
	public void makeSmallApples(float cX, float cY, float radius, int life, int count) {
		for (int i = 0; i < smallApples.length; i++) {
			SmallApple smallApple = smallApples[i];
			if (smallApple.life <= 0) {
			
				smallApple.life = smallApple.lifeMax = (int)(Math.random() * life * 0.5) + life / 2;
				smallApple.posX = cX;
				smallApple.posY = cY;
				double angle = Math.random() * Math.PI * 2.0;
				double speed = Math.random() * radius;
				smallApple.dX = (float)(Math.cos(angle) * speed);
				smallApple.dY = (float)(Math.sin(angle) * speed);
				smallApple.angle = (float)(Math.random() * Math.PI * 2.0);
				smallApple.rot = (float)(Math.random() - 0.5) * 0.3f;
				
				count--;
				if(count <= 0) return;
			}
		}
	}

}
