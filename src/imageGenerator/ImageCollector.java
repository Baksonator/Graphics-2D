package imageGenerator;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Random;

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
	public static BufferedImage blurredEnemy[];
	public static BufferedImage chickenObject;
	public static BufferedImage guitarObject;
	public static BufferedImage snakeObject;
	public static BufferedImage greenApple;
	public static BufferedImage greenSmallApple;
	public static BufferedImage redSmallApple;
	public static BufferedImage yellowSmallApple;
	public static BufferedImage numbersSet[];
	public static BufferedImage score;
	
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
		
		guitarHero = ImageGenerator.joinLetterImages("Guitar Hero", charSet, emptyChar);
		
		BufferedImage teamBANTer = ImageGenerator.joinLetterImages("Team BANTer", charSet, emptyChar);
		BufferedImage presents = ImageGenerator.joinLetterImages("presents", charSet, emptyChar);
		
		helpArray = new BufferedImage[2];
		helpArray[0] = teamBANTer;
		helpArray[1] = presents;
		
		presentation = ImageGenerator.joinBufferedImageArrayVerticalCentered(helpArray);
		
		snake = ImageGenerator.joinLetterImages("SNAKE", charSet, emptyChar);
		
		blurredEnemy = new BufferedImage[20];
		
		for (int i = 0; i < blurredEnemy.length; i++) {
			blurredEnemy[i] = ImageGenerator.blurImage(Util.loadImage("spaceart/png/enemyShip.png"), i + 1);
		}
		
		chickenObject = ImageGenerator.scaleImageJava(Util.loadImage("spaceart/png/player.png"), 100, 100);
		guitarObject = ImageGenerator.scaleImageJava(Util.loadImage("objects/guitar.png"), 100, 100);
		snakeObject = ImageGenerator.scaleImageJava(Util.loadImage("objects/snake.png"), 100, 100);
		
		greenApple = ImageGenerator.scaleImageJava(Util.loadImage("objects/green_apple.png"), 40, 40);
		greenSmallApple = ImageGenerator.scaleImageJava(Util.loadImage("objects/green_apple.png"), 30, 30);
		redSmallApple = ImageGenerator.scaleImageJava(Util.loadImage("objects/red_small_apple.png"), 30, 30);
		yellowSmallApple = ImageGenerator.scaleImageJava(Util.loadImage("objects/green_apple.png"), 30, 30);
		
		
		numbersSet = new BufferedImage[12];
		int count = 0;
		for (int j = 0; j <= 24; j += 8) {
			for (int i = 27; i < 30; i++) {
				BufferedImage isecena = ImageGenerator.cutTile(i * 8, j, 8, 8, Util.loadImage("tileset/font.png"));
				numbersSet[count++] = ImageGenerator.scaleImageJava(isecena, 64, 64);
			}
		}
		
		score = ImageGenerator.joinLetterImages("score", charSet, emptyChar);
	}

	public static BufferedImage[] getHeroSingle() {
		return heroSingle;
	}

	public static BufferedImage[] getHeroColumn() {
		return heroColumn;
	}

	public static BufferedImage getHeroSet() {
		return heroSet;
	}

	public static BufferedImage getNoise() {
		return noise;
	}

	public static BufferedImage getBasicTile() {
		return basicTile;
	}

	public static BufferedImage[] getCharSet() {
		return charSet;
	}

	public static BufferedImage getEmptyChar() {
		return emptyChar;
	}

	public static BufferedImage getTeam() {
		return team;
	}

	public static BufferedImage getContinueGame() {
		return continueGame;
	}

	public static BufferedImage getContinueGameRed() {
		return continueGameRed;
	}

	public static BufferedImage getExitGame() {
		return exitGame;
	}

	public static BufferedImage getExitGameRed() {
		return exitGameRed;
	}

	public static BufferedImage getEpicQuest() {
		return epicQuest;
	}

	public static BufferedImage getGameOver() {
		return gameOver;
	}

	public static BufferedImage getGuitarHero() {
		return guitarHero;
	}

	public static BufferedImage getPresentation() {
		return presentation;
	}

	public static BufferedImage getSnake() {
		return snake;
	}

	public static BufferedImage[] getBlurredEnemy() {
		return blurredEnemy;
	}

	public static BufferedImage getChickenObject() {
		return chickenObject;
	}

	public static BufferedImage getGuitarObject() {
		return guitarObject;
	}

	public static BufferedImage getSnakeObject() {
		return snakeObject;
	}

	public static BufferedImage getGreenApple() {
		return greenApple;
	}

	public static BufferedImage getGreenSmallApple() {
		return greenSmallApple;
	}

	public static BufferedImage getRedSmallApple() {
		return redSmallApple;
	}

	public static BufferedImage getYellowSmallApple() {
		return yellowSmallApple;
	}

	public static BufferedImage[] getNumbersSet() {
		return numbersSet;
	}

	public static BufferedImage getScore() {
		return score;
	}
	
}
