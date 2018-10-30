package imageGenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import rafgfxlib.ImageViewer;
import rafgfxlib.Util;

public class GeneratorMain {

	public static void main(String[] args) {
		BufferedImage isecena = ImageGenerator.cutTile(0, 0, 16, 16, Util.loadImage("tileset/Overworld.png"));
		
		BufferedImage skalirana = ImageGenerator.scaleImage(isecena, 64, 64);
		
		ImageViewer.showImageWindow(skalirana, "Bleja");
		
		File outputFile = new File("tileset/Basic_tile.png");
		try {
			ImageIO.write(skalirana, "png", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
