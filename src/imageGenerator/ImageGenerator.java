package imageGenerator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Random;

import rafgfxlib.ImageViewer;
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
				Util.pointSample(source, srcX, srcY, rgb);
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
	
	public static BufferedImage joinBufferedImage(BufferedImage img1, BufferedImage img2) {

        //do some calculate first
        int wid = img1.getWidth()+img2.getWidth();
        int height = Math.max(img1.getHeight(),img2.getHeight());
        //create a new buffer and draw two image into the new image
        BufferedImage newImage = new BufferedImage(wid,height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        //fill background
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, wid, height);
        //draw image
        g2.setColor(oldColor);
        g2.drawImage(img1, null, 0, 0);
        g2.drawImage(img2, null, img1.getWidth(), 0);
        g2.dispose();
        return newImage;
    }

	public static BufferedImage joinBufferedImageArray(BufferedImage[] arrayImg) {

        //do some calculate first
        int wid = 0;
        int height = 0;
        for (int i = 0; i < arrayImg.length; i++) {
        	wid += arrayImg[i].getWidth();
        	if (height < arrayImg[i].getHeight()) {
        		height = arrayImg[i].getHeight();
        	}
        }
        //create a new buffer and draw two image into the new image
        BufferedImage newImage = new BufferedImage(wid,height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        //fill background
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, wid, height);
        //draw image
        g2.setComposite(AlphaComposite.Src);
        for (int i = 0; i < arrayImg.length; i++) {
        	g2.drawImage(arrayImg[i], null, arrayImg[i].getWidth() * i, 0);
        }
        g2.dispose();
        return newImage;
    }
	
	public static BufferedImage joinBufferedImageArrayVertical(BufferedImage[] arrayImg) {

        //do some calculate first
        int wid = 0;
        int height = 0;
        for (int i = 0; i < arrayImg.length; i++) {
        	height += arrayImg[i].getHeight();
        	if (wid < arrayImg[i].getWidth()) {
        		wid = arrayImg[i].getWidth();
        	}
        }
        //create a new buffer and draw two image into the new image
        BufferedImage newImage = new BufferedImage(wid,height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        //fill background
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, wid, height);
        //draw image
        g2.setComposite(AlphaComposite.Src);
        for (int i = 0; i < arrayImg.length; i++) {
        	g2.drawImage(arrayImg[i], null, 0, arrayImg[i].getHeight() * i);
        }
        g2.dispose();
        return newImage;
    }
	
	public static BufferedImage joinBufferedImageArrayVerticalCentered(BufferedImage[] arrayImg) {

        //do some calculate first
        int wid = 0;
        int height = 0;
        int offset[] = new int[arrayImg.clone().length];
        for (int i = 0; i < arrayImg.length; i++) {
        	height += arrayImg[i].getHeight();
        	if (wid < arrayImg[i].getWidth()) {
        		wid = arrayImg[i].getWidth();
        	}
        }
        for (int i = 0; i < arrayImg.length; i++) {
        	offset[i] = (wid - arrayImg[i].getWidth()) / 2;
        }
        //create a new buffer and draw two image into the new image
        BufferedImage newImage = new BufferedImage(wid,height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        //fill background
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, wid, height);
        //draw image
        g2.setComposite(AlphaComposite.Src);
        for (int i = 0; i < arrayImg.length; i++) {
        	g2.drawImage(arrayImg[i], null, offset[i], arrayImg[i].getHeight() * i);
        }
        g2.dispose();
        return newImage;
    }
	
	public static BufferedImage promeniBoju(BufferedImage source, Color color) {
		int w = source.getWidth();
        int h = source.getHeight();
        BufferedImage dyed = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g = dyed.createGraphics();
        g.drawImage(source, 0,0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(color);
        g.fillRect(0,0,w,h);
        g.dispose();
        
        return dyed;
	}
	
}
