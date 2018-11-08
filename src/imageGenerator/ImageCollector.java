package imageGenerator;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import rafgfxlib.Util;

public class ImageCollector {

	public static BufferedImage heroSingle[];
	public static BufferedImage heroColumn[];
	public static BufferedImage heroSet;
	public static BufferedImage noise;
	public static BufferedImage basicTile;
	public static BufferedImage charSet[];
	public static BufferedImage emptyChar;
	public static BufferedImage team;
	public static BufferedImage continueGame;
	public static BufferedImage continueGameRed;
	public static BufferedImage exitGame;
	public static BufferedImage exitGameRed;
	public static BufferedImage epicQuest;
	public static BufferedImage gameOver;
	public static BufferedImage guitarHero;
	public static BufferedImage presentation;
	public static BufferedImage snake;
	
	public ImageCollector() {
		heroSingle = new BufferedImage[16];
		int column = 0;
		for (int i = 0; i < heroSingle.length; i++) {
			if (i != 0 && i % 4 == 0) {
				column++;
			}
			heroSingle[i] = ImageGenerator.scaleImageJava(ImageGenerator.cutTile((i % 4) * 16, column * 32,
					16, 32, Util.loadImage("tileset/character.png")), 64, 64);
		}
		
		heroColumn = new BufferedImage[4];
		for (int i = 0; i < heroColumn.length; i++) {
			BufferedImage[] helpArray = {heroSingle[4 * i], heroSingle[4 * i + 1], heroSingle[4 * i + 2],
					heroSingle[4 * i + 3]}; 
			heroColumn[i] = ImageGenerator.joinBufferedImageArray(helpArray);
		}
		
		heroSet = ImageGenerator.joinBufferedImageArrayVertical(heroColumn);
		
		WritableRaster raster = Util.createRaster(1080, 720, false);
		int rgb[] = new int[3];
		
		Random rnd = new Random();

		for(int y = 0; y < raster.getHeight(); y++)
		{
			for(int x = 0; x < raster.getWidth(); x++)
			{
				rgb[0] = rnd.nextInt(256);
				rgb[1] = rnd.nextInt(256);
				rgb[2] = rnd.nextInt(256);
				
				raster.setPixel(x, y, rgb);
			}
		}
		
		noise = Util.rasterToImage(raster);
		
		basicTile = ImageGenerator.scaleImageJava(ImageGenerator.cutTile(0, 0, 16, 16,
				Util.loadImage("tileset/Overworld.png")), 64, 64);
		
		charSet = new BufferedImage[52];
		column = 0;
		for (int i = 0; i < charSet.length; i++) {
			if (i != 0 && i % 26 == 0) {
				column++;
			}
			charSet[i] = ImageGenerator.scaleImageJava(ImageGenerator.cutTile((i % 26) * 8, column * 16,
					8, 16, Util.loadImage("tileset/font.png")), 64, 64);
		}
		
		emptyChar = ImageGenerator.scaleImageJava(ImageGenerator.cutTile(208, 0, 8, 16,
				Util.loadImage("tileset/font.png")), 64, 64);
		
		BufferedImage nevenaWord = ImageGenerator.joinLetterImages("Nevena Dresevic", charSet, emptyChar);
		BufferedImage markoWord = ImageGenerator.joinLetterImages("Marko Matovic", charSet, emptyChar);
		BufferedImage bogdanWord = ImageGenerator.joinLetterImages("Bogdan Bakarec", charSet, emptyChar);
		BufferedImage teamMembers = ImageGenerator.joinLetterImages("Team members", charSet, emptyChar);
		
		BufferedImage helpArray[] = new BufferedImage[4];
		
		helpArray[0] = teamMembers;
		helpArray[1] = nevenaWord;
		helpArray[2] = markoWord;
		helpArray[3] = bogdanWord;
		
		team = ImageGenerator.joinBufferedImageArrayVerticalCentered(helpArray);
		
		continueGame = ImageGenerator.joinLetterImages("Continue game", charSet, emptyChar);
		continueGameRed = ImageGenerator.promeniBoju(continueGame, Color.RED);
		
		exitGame = ImageGenerator.joinLetterImages("Exit game", charSet, emptyChar);
		exitGameRed = ImageGenerator.promeniBoju(exitGame, Color.RED);
		
		epicQuest = ImageGenerator.joinLetterImages("Epic Quest", charSet, emptyChar);
		
		gameOver = ImageGenerator.joinLetterImages("Game over", charSet, emptyChar);
		
		File outputFile = new File("tileset/fontset/bleja.png");
		
		try {
			ImageIO.write(gameOver, "png", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		guitarHero = ImageGenerator.joinLetterImages("Guitar Hero", charSet, emptyChar);
		
		BufferedImage teamBANTer = ImageGenerator.joinLetterImages("Team BANTer", charSet, emptyChar);
		BufferedImage presents = ImageGenerator.joinLetterImages("presents", charSet, emptyChar);
		
		helpArray = new BufferedImage[2];
		helpArray[0] = teamBANTer;
		helpArray[1] = presents;
		
		presentation = ImageGenerator.joinBufferedImageArrayVerticalCentered(helpArray);
		
		snake = ImageGenerator.joinLetterImages("Snake", charSet, emptyChar);
	}
}
