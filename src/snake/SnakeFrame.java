package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import guitarHero.GuitarHeroFrame;
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
		if (keyCode == KeyEvent.VK_DOWN) {
			hero.setAnimation(ANIM_DOWN);
			hero.play();
			direction = 2;
		}
		else if (keyCode == KeyEvent.VK_UP) {
			hero.setAnimation(ANIM_UP);
			hero.play();
			direction = 0;
		}
		else if (keyCode == KeyEvent.VK_LEFT) {
			hero.setAnimation(ANIM_RIGHT);
			hero.play();
			direction = 3;
		}
		else if (keyCode == KeyEvent.VK_RIGHT) {
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
		
		if (isOver) {
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
	public void checkForApple() {
		Rectangle head = snakeBody.get(0);
		tailX = snakeBody.get(snakeBody.size() - 1).x;
		tailY = snakeBody.get(snakeBody.size() - 1).y;
		if ((Math.abs(head.x - apple.getPositionX()) <= 20) && (Math.abs(head.y - apple.getPositionY()) <= 20)) {
			Rectangle newRect = new Rectangle(tailX, tailY + bodyHeight, bodyWidth, bodyHeight);
			snakeBody.add(newRect);
			newApple();
        }
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
	
	@Override
	public void update() {
		
		timer--;
		if (timer <= 0 && !isIntro && !isOver) {
			moveSnake();
			timer = 10;
		} 
		
		checkForApple();
		checkCollisions();
	}

}
