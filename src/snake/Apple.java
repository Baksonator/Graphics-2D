package snake;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Random;
import imageGenerator.ImageGenerator;
import rafgfxlib.Util;

public class Apple {
	
	private int positionX, positionY;
	private BufferedImage image;
	private Random r = new Random();
	private int screenWidth, screenHeight;
	
	public Apple(int screenWidth, int screenHeight) {
		image = Util.loadImage("tileset/green_apple.jpg");
		image = ImageGenerator.scaleImage(image, 30, 30);
		image = appleImage(image);
		
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		setRandomPositions();
	}
	
	public void setRandomPositions() {
		positionX = Math.abs(r.nextInt(screenWidth) - image.getWidth());
		positionY = Math.abs(r.nextInt(screenHeight) - image.getHeight());
	}
	
	public BufferedImage appleImage(BufferedImage image) {
		
		WritableRaster source = image.getRaster();
		WritableRaster target = Util.createRaster(source.getWidth(), source.getHeight(), false);
		
		int[] rgb = {0, 0, 0};
		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth(); x++) {
				source.getPixel(x, y, rgb);
				if (rgb[0] >= 250 && rgb[1] >= 250 && rgb[2] >= 250) {
					rgb[0] = 0;
					rgb[1] = 0;
					rgb[2] = 0;
				}
				target.setPixel(x, y, rgb);
			}
		}
		
		return Util.rasterToImage(target);
	}
	
	public int getPositionX() {
		return positionX;
	}
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	public int getPositionY() {
		return positionY;
	}
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
