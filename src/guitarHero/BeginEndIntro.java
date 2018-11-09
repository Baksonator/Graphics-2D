package guitarHero;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import imageGenerator.ImageCollector;
import imageGenerator.ImageGenerator;

public class BeginEndIntro {
	
	BufferedImage beginLetters;
	BufferedImage endLetters;
	BufferedImage scoreImage;
	private BufferedImage beginImage;
	private BufferedImage endImage;
	
	public BeginEndIntro () {
		beginImage = ImageCollector.guitarHero;
	}
	
	public BufferedImage endScoreImage(int score) {
		endLetters = ImageCollector.gameOver;
		scoreImage = ImageCollector.score;
		BufferedImage numbersImage = joinNumberImages(score);
		BufferedImage[] images = {endLetters, scoreImage, numbersImage};
		
		endImage = ImageGenerator.joinBufferedImageArrayVerticalCentered(images);
		return ImageGenerator.joinBufferedImageArrayVerticalCentered(images);
	}
	
	// creates image out of score
	public static BufferedImage joinNumberImages(int number) {
		ArrayList<BufferedImage> images = new ArrayList<>();
		while (number > 0) {
			int i = number % 10;
			BufferedImage digit;
			if (i == 9) {
				digit = ImageCollector.numbersSet[10];
			} else {
				digit = ImageCollector.numbersSet[i];
			}
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
