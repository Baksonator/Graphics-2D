package guitarHero;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
		
//		cutAndScaleScore();
//		beginImage = getImage("tileset/fontset/GuitarHero.png");
//		endImage = getImage("tileset/fontset/Gameover.png");
	}
	
	public BufferedImage endScoreImage(int score) {
//		System.out.println("SCORE - " + score);
		endLetters = joinLetterImages("Game over");
		scoreImage = joinLetterImages("score");
		BufferedImage numbersImage = joinNumberImages(score);
		BufferedImage[] images = {endLetters, scoreImage, numbersImage};
		
		endImage = ImageGenerator.joinBufferedImageArrayVerticalCentered(images);
		return ImageGenerator.joinBufferedImageArrayVerticalCentered(images);
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
	
	// creates image out of score
	public static BufferedImage joinNumberImages(int number) {
		ArrayList<BufferedImage> images = new ArrayList<>();
		while (number > 0) {
			int i = number % 10;
			BufferedImage digit = Util.loadImage("tileset/numbeset/" + i + ".png");
			images.add(digit);
			number /= 10;
		}
		
		BufferedImage[] arrayImg = new BufferedImage[images.size()];
		
		int m = 0;
		for (int i = images.size() - 1; i >= 0; i--) {
			arrayImg[m++] = images.get(i);
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
	
	// method that creates pictures for numbers 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
	public static void cutAndScaleScore() {
		int count = 0;
		for (int i = 27; i < 30; i++) {
			for (int j = 0; j <= 24; j += 8) {
				BufferedImage isecena = ImageGenerator.cutTile(i * 8, j, 8, 8, Util.loadImage("tileset/numbeset.png"));
				BufferedImage skalirana = ImageGenerator.scaleImageJava(isecena, 64, 64);
		
				File outputFile = new File("tileset/fontset/" + count + ".png");
				
				try {
					ImageIO.write(skalirana, "png", outputFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				count++;
			}
		}
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
