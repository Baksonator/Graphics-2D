package imageGenerator;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import rafgfxlib.Util;

/**
 * Util klasa sa svim mogucim pomocnim metodama za manipulaciju slikama koje nemamo na raspolaganju
 * @author Bogdan
 *
 */
public class ImageGenerator {
	
	/**
	 * Skalira sliku pomocu point sample-a, vezbe 3, zadatak 1
	 * @param sourceImage
	 * @param scaleW
	 * @param scaleH
	 * @return
	 */
	public static BufferedImage scaleImage(BufferedImage sourceImage, int scaleW, int scaleH) {
		
		WritableRaster source = sourceImage.getRaster();
		
		WritableRaster targetBil = Util.createRaster(scaleW, scaleH, false);
		
		int rgb[] = new int[4];
		
		// Petlje su postavljene da prodju sve piksele ciljne slike, gdje cemo
		// za svaki takav da na osnovu izvorne slike odredimo adekvanu boju
		for(int y = 0; y < scaleW; y++)
		{
			// Izracunavamo 0 - 1 faktor trenutne Y koordinate u odnosu na visinu  
			float fy = (float)y / scaleW;
			
			for(int x = 0; x < scaleH; x++)
			{
				// Izracunavamo 0 - 1 faktor trenutne X koordinate u odnosu na sirinu  
				float fx = (float)x / scaleH;
				
				// Jednostavnom proporcijom dobijamo koordinate "prevedene" 
				// u prostor originalne slike, koje mogu biti decimalne
				float srcX = fx * source.getWidth();
				float srcY = fy * source.getHeight();
				
				// Uzimamo bilinearni uzorak i upisujemo ga u jedan raster
				Util.bilSampleA(source, srcX, srcY, rgb);
				targetBil.setPixel(x, y, rgb);
			}
		}
		
		return Util.rasterToImage(targetBil);
	}
	
	/**
	 * Sece deo slike
	 * @param x gornja leva x koordinata
	 * @param y gornja leva y koordinata
	 * @param width sirina rezultujuce slike
	 * @param height visina rezultujice slike
	 * @param sheet izvorna slika
	 * @return rezultujuca slika
	 */
	public static BufferedImage cutTile(int x, int y, int width, int height, BufferedImage sheet) {
		BufferedImage tile = new BufferedImage(width, height, sheet.getType());
		
		Graphics g = tile.getGraphics();
		
		g.drawImage(sheet, 0, 0, width, height, x, y, x + width, y + height, null);
		g.dispose();
		
		return tile;
	}
	
	public static BufferedImage scaleImageJava(BufferedImage source, int scaleW, int scaleH) {
		
		BufferedImage output = new BufferedImage(scaleW, scaleH, source.getType());
		
		Graphics2D g2d = output.createGraphics();
		g2d.drawImage(source, 0, 0, scaleW, scaleH, null);
		g2d.dispose();
		
		return output;
	}

}
