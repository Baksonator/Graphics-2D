package guitarHero;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import imageGenerator.ImageGenerator;
import rafgfxlib.Util;

public class BeginEndIntro {
	
	BufferedImage beginLetters;
	BufferedImage endLetters;
	BufferedImage scoreImage;
	private BufferedImage beginImage;
	private BufferedImage endImage;
	
	public BeginEndIntro () {
		beginLetters = joinLetterImages("Guitar hero");
		beginImage = beginLetters;
		
//		beginImage = getImage("tileset/fontset/GuitarHero.png");
//		endImage = getImage("tileset/fontset/Gameover.png");
	}
	
	public BufferedImage endScoreImage(int score) {
		System.out.println("SCORE - " + score);
		endLetters = joinLetterImages("Game over");
		scoreImage = joinLetterImages("score ");
		BufferedImage[] images = {endLetters, scoreImage};
		
		endImage = ImageGenerator.joinBufferedImageArrayVerticalCentered(images);
		return endImage;
	}
	
	public BufferedImage getImage(String imagePath) {
		BufferedImage image = Util.loadImage(imagePath);
		WritableRaster source = image.getRaster();
		WritableRaster target = Util.createRaster(source.getWidth(), source.getHeight(), false);
		
		int rgb[] = new int[3];
		
		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth(); x++) {
				source.getPixel(x, y, rgb);
				rgb[0] = 255 - rgb[0];
				rgb[1] = 255 - rgb[1];
				rgb[2] = 255 - rgb[2];
				target.setPixel(x, y, rgb);
			}
		}
		
		return Util.rasterToImage(target);
	}
	
	public static BufferedImage joinLetterImages(String title) {
		BufferedImage[] arrayImg = new BufferedImage[title.length()];
		
		for (int i = 0; i < arrayImg.length; i++) {
			char c = title.charAt(i);
			int ascii = (int)c;
			int br;
			if (ascii < 97) {
				br = 1;
			} else {
				br = 2;
			}
			if (ascii < 65) {
				arrayImg[i] = Util.loadImage("tileset/fontset/" + title.charAt(i) + ".png");
				continue;
			}
			arrayImg[i] = Util.loadImage("tileset/fontset/" + title.charAt(i) + br + ".png");
		}
		
		return ImageGenerator.joinBufferedImageArray(arrayImg);
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
		saveImage("tileset/fontset/GuitarHero.png", beginLetters);
		saveImage("tileset/fontset/Gameover.png", endLetters);
	}

	public BufferedImage getBeginImage() {
		return beginImage;
	}

	public void setBeginImage(BufferedImage beginImage) {
		this.beginImage = beginImage;
	}

	public BufferedImage getEndImage() {
		return endImage;
	}

	public void setEndImage(BufferedImage endImage) {
		this.endImage = endImage;
	}
	
}
