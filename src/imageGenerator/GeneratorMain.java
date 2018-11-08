package imageGenerator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import rafgfxlib.Util;

public class GeneratorMain {

	public static void main(String[] args) {
//		BufferedImage skalirana = ImageGenerator.scaleImageJava(Util.loadImage("spaceart/png/meteorSmall.png"), 
//				20, 20);
//		File outputFile = new File("spaceart/png/meteorMinor.png");
//		try {
//			ImageIO.write(skalirana, "png", outputFile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		joinLetterImages();
	}
	
	public static void cutAndScaleArray() {
		for (int i = 0; i < 26; i++) {
			BufferedImage isecena = ImageGenerator.cutTile(i * 8, 16, 8, 16, Util.loadImage("tileset/font.png"));
			
			BufferedImage skalirana = ImageGenerator.scaleImageJava(isecena, 64, 64);
			
			String s = "";
			if (i % 2 == 0) {
				int br = i / 2;
				s = Character.toString((char)(br + 13 + 65));
			} else {
				int br = i / 2;
				s = Character.toString((char)(br + 13 + 97));
			}
			
			File outputFile = new File("tileset/fontset/" + s.toUpperCase() + (i % 2 + 1) + ".png");
			
			try {
				ImageIO.write(skalirana, "png", outputFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void cutAndScale() {
		BufferedImage isecena = ImageGenerator.cutTile(208, 0, 8, 12, Util.loadImage("tileset/font.png"));
		
		BufferedImage skalirana = ImageGenerator.scaleImageJava(isecena, 64, 64);
		
//		ImageViewer.showImageWindow(skalirana, "Bleja");
		
		File outputFile = new File("tileset/fontset/ .png");
		try {
			ImageIO.write(skalirana, "png", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void mergeIntoSheet() {
		
		BufferedImage[] source = new BufferedImage[16];
		for (int i = 0; i < 16; i++) {
			source[i] = Util.loadImage("tileset/charset/hero" + i);
		}
		
		BufferedImage output = new BufferedImage(source[0].getWidth() * 4, source[0].getHeight() * 4, 
				source[0].getType());
		
		Graphics2D g2d = output.createGraphics();
		
		for (int i = 0; i < 16; i++) {
			g2d.drawImage(source[i], (i % 4) * 16, (i % 4) * 32, source[i].getWidth(), source[i].getHeight(), null);
		}
		
		g2d.dispose();
	}

	public static void joinImages(BufferedImage[] arrayImg) {
		
//		for (int i = 0; i < arrayImg.length; i++) {
//			arrayImg[i] = Util.loadImage("tileset/charset/heroSet" + (i + 1) + ".png");
//		}
		
		BufferedImage konacna = ImageGenerator.joinBufferedImageArray(arrayImg);
		
		File outputFile = new File("tileset/charset/heroSet.png");
		
		try {
			ImageIO.write(konacna, "png", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void joinLetterImages() {
		String word = "Continue game";
		BufferedImage[] arrayImg = new BufferedImage[word.length()];
		
		for (int i = 0; i < arrayImg.length; i++) {
			char c = word.charAt(i);
			int ascii = (int)c;
			int br;
			if (ascii < 97) {
				br = 1;
			} else {
				br = 2;
			}
			if (ascii < 65) {
				arrayImg[i] = Util.loadImage("tileset/fontset/" + word.charAt(i) + ".png");
				continue;
			}
			System.out.println("tileset/fontset/" + word.charAt(i) + br + ".png");
			arrayImg[i] = Util.loadImage("tileset/fontset/" + word.charAt(i) + br + ".png");
		}
		
		BufferedImage konacna = ImageGenerator.joinBufferedImageArray(arrayImg);
		
		File outputFile = new File("tileset/fontset/ContinueGame.png");
		
		try {
			ImageIO.write(konacna, "png", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void joinLetterImagesVertical() {
		BufferedImage[] arrayImg = new BufferedImage[4];
		
		arrayImg[0] = Util.loadImage("tileset/fontset/TeamMembers.png");
		arrayImg[1] = Util.loadImage("tileset/fontset/Nevena.png");
		arrayImg[2] = Util.loadImage("tileset/fontset/Marko.png");
		arrayImg[3] = Util.loadImage("tileset/fontset/Bogdan.png");
		
		BufferedImage konacna = ImageGenerator.joinBufferedImageArrayVerticalCentered(arrayImg);
		
		File outputFile = new File("tileset/fontset/TeamMembersList.png");
		
		try {
			ImageIO.write(konacna, "png", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void promeniBojuSlike() {
		BufferedImage image = Util.loadImage("tileset/fontset/ExitGame.png");
		
		BufferedImage konacna = ImageGenerator.promeniBoju(image, Color.red);
		
		File outputFile = new File("tileset/fontset/ExitGameRed.png");
		
		try {
			ImageIO.write(konacna, "png", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void kreirajNoise() {
		WritableRaster raster = Util.createRaster(1080, 720, false);
		int rgb[] = new int[3];
		
		// Sum (noise) dobijamo upotrebom slucajnih brojeva
		Random rnd = new Random();

		for(int y = 0; y < raster.getHeight(); y++)
		{
			for(int x = 0; x < raster.getWidth(); x++)
			{
				// Za noise u boji, sva tri kanala postavljamo nezavisno
				rgb[0] = rnd.nextInt(256);
				rgb[1] = rnd.nextInt(256);
				rgb[2] = rnd.nextInt(256);
				
				raster.setPixel(x, y, rgb);
			}
		}
		
		File outputFile = new File("tileset/Noise.png");
		
		try {
			ImageIO.write(Util.rasterToImage(raster), "png", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
