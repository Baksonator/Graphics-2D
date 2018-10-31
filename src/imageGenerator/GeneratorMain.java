package imageGenerator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import rafgfxlib.ImageViewer;
import rafgfxlib.Util;

public class GeneratorMain {

	public static void main(String[] args) {
		
	}
	
	public static void cutAndScale() {
		BufferedImage isecena = ImageGenerator.cutTile(48, 96, 16, 32, Util.loadImage("tileset/character.png"));
		
		BufferedImage skalirana = ImageGenerator.scaleImageJava(isecena, 64, 64);
		
//		ImageViewer.showImageWindow(skalirana, "Bleja");
		
		File outputFile = new File("tileset/charset/hero15.png");
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

}
