package snake;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import guitarHero.BeginEndIntro;
import imageGenerator.ImageGenerator;
import rafgfxlib.Util;

public class SnakeImages {
	
	private BufferedImage introImage;
	private BufferedImage appleImage;
	private BufferedImage backgroundImage;
	private BufferedImage endImage;
	
	public SnakeImages() {
		introImage = Util.loadImage("tileset/fontset/SNAKE.png");
		endImage = Util.loadImage("tileset/fontset/Gameover.png");
		saveImages();
	}
	
	public BufferedImage createBackgroundImage(int width, int height) {
		WritableRaster raster = Util.createRaster(width, height, false);
		
		int[] rgb = {0, 0, 0};
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				raster.setPixel(x, y, rgb);
			}
		}
		
		return Util.rasterToImage(raster);
	}
	
	public void saveImage(String imagePath, BufferedImage image) {
		File outputFile = new File(imagePath);
			try {
				ImageIO.write(image, "png", outputFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void saveImages() {
		saveImage("tileset/fontset/SNAKE.png", introImage);
	}

	public BufferedImage getIntroImage() {
		return introImage;
	}

	public void setIntroImage(BufferedImage introImage) {
		this.introImage = introImage;
	}

	public BufferedImage getAppleImage() {
		return appleImage;
	}

	public void setAppleImage(BufferedImage appleImage) {
		this.appleImage = appleImage;
	}

	public BufferedImage getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(BufferedImage backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public BufferedImage getEndImage() {
		return endImage;
	}

	public void setEndImage(BufferedImage endImage) {
		this.endImage = endImage;
	}
}
