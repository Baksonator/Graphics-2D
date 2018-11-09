package snake;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import imageGenerator.ImageCollector;
import rafgfxlib.Util;

public class SnakeImages {
	
	private BufferedImage introImage;
	private BufferedImage appleImage;
	private BufferedImage backgroundImage;
	private BufferedImage endImage;
	
	public SnakeImages() {
		introImage = ImageCollector.snake;
		endImage = ImageCollector.gameOver;
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
