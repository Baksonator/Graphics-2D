package imageGenerator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import rafgfxlib.Util;

/**
 * Util klasa sa svim mogucim pomocnim metodama za manipulaciju slikama koje
 * nemamo na raspolaganju
 * 
 * @author Bogdan
 *
 */
public class ImageGenerator {

	/**
	 * Skalira sliku pomocu point sample-a, vezbe 3, zadatak 1
	 * 
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
		for (int y = 0; y < scaleH; y++) {
			// Izracunavamo 0 - 1 faktor trenutne Y koordinate u odnosu na visinu
			float fy = (float) y / scaleH;

			for (int x = 0; x < scaleW; x++) {
				// Izracunavamo 0 - 1 faktor trenutne X koordinate u odnosu na sirinu
				float fx = (float) x / scaleW;

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
	 * 
	 * @param x      gornja leva x koordinata
	 * @param y      gornja leva y koordinata
	 * @param width  sirina rezultujuce slike
	 * @param height visina rezultujice slike
	 * @param sheet  izvorna slika
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

		// do some calculate first
		int wid = img1.getWidth() + img2.getWidth();
		int height = Math.max(img1.getHeight(), img2.getHeight());
		// create a new buffer and draw two image into the new image
		BufferedImage newImage = new BufferedImage(wid, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		Color oldColor = g2.getColor();
		// fill background
		g2.setPaint(Color.WHITE);
		g2.fillRect(0, 0, wid, height);
		// draw image
		g2.setColor(oldColor);
		g2.drawImage(img1, null, 0, 0);
		g2.drawImage(img2, null, img1.getWidth(), 0);
		g2.dispose();
		return newImage;
	}

	public static BufferedImage joinBufferedImageArray(BufferedImage[] arrayImg) {

		// do some calculate first
		int wid = 0;
		int height = 0;
		for (int i = 0; i < arrayImg.length; i++) {
			wid += arrayImg[i].getWidth();
			if (height < arrayImg[i].getHeight()) {
				height = arrayImg[i].getHeight();
			}
		}
		// create a new buffer and draw two image into the new image
		BufferedImage newImage = new BufferedImage(wid, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		// fill background
		g2.setComposite(AlphaComposite.Clear);
		g2.fillRect(0, 0, wid, height);
		// draw image
		g2.setComposite(AlphaComposite.Src);
		for (int i = 0; i < arrayImg.length; i++) {
			g2.drawImage(arrayImg[i], null, arrayImg[i].getWidth() * i, 0);
		}
		g2.dispose();
		return newImage;
	}

	public static BufferedImage joinBufferedImageArrayVertical(BufferedImage[] arrayImg) {

		// do some calculate first
		int wid = 0;
		int height = 0;
		for (int i = 0; i < arrayImg.length; i++) {
			height += arrayImg[i].getHeight();
			if (wid < arrayImg[i].getWidth()) {
				wid = arrayImg[i].getWidth();
			}
		}
		// create a new buffer and draw two image into the new image
		BufferedImage newImage = new BufferedImage(wid, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		// fill background
		g2.setComposite(AlphaComposite.Clear);
		g2.fillRect(0, 0, wid, height);
		// draw image
		g2.setComposite(AlphaComposite.Src);
		for (int i = 0; i < arrayImg.length; i++) {
			g2.drawImage(arrayImg[i], null, 0, arrayImg[i].getHeight() * i);
		}
		g2.dispose();
		return newImage;
	}

	public static BufferedImage joinBufferedImageArrayVerticalCentered(BufferedImage[] arrayImg) {

		// do some calculate first
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
		// create a new buffer and draw two image into the new image
		BufferedImage newImage = new BufferedImage(wid, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		// fill background
		g2.setComposite(AlphaComposite.Clear);
		g2.fillRect(0, 0, wid, height);
		// draw image
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
		BufferedImage dyed = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = dyed.createGraphics();
		g.drawImage(source, 0, 0, null);
		g.setComposite(AlphaComposite.SrcAtop);
		g.setColor(color);
		g.fillRect(0, 0, w, h);
		g.dispose();

		return dyed;
	}

	public static BufferedImage joinLetterImages(String word, BufferedImage[] letters, BufferedImage empty) {
		BufferedImage[] arrayImg = new BufferedImage[word.length()];

		for (int i = 0; i < arrayImg.length; i++) {
			char c = word.charAt(i);
			int ascii = (int) c;
			if (ascii < 97) {
				if (ascii < 65) {
					arrayImg[i] = empty;
				} else {
					arrayImg[i] = letters[2 * (ascii - 65)];
				}
			} else {
				arrayImg[i] = letters[2 * (ascii - 97) + 1];
			}
		}

		BufferedImage konacna = ImageGenerator.joinBufferedImageArray(arrayImg);

		return konacna;
	}

	public static BufferedImage blurImage(BufferedImage image, int radius) {
		WritableRaster source = image.getRaster();
		WritableRaster target = Util.createRaster(image.getWidth(), image.getHeight(), true);

		int rgb[] = new int[4];
		int accum[] = new int[4];

		int sampleCount = 32;

		int width = source.getWidth();
		int height = source.getHeight();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				accum[0] = 0;
				accum[1] = 0;
				accum[2] = 0;

				int help[] = new int[4];
				source.getPixel(x, y, help);

				for (int i = 0; i < sampleCount; i++) {
					// Za svaki uzorak odredjujemo nasumicnu koordinatu unutar
					// (radius*2) x (radius*2) kvadrata oko trenutnog piksela
					float X = (float) (Math.random() - 0.5) * radius * 2.0f;
					float Y = (float) (Math.random() - 0.5) * radius * 2.0f;

					// Uzimamo bilinearan uzorak
					bilSampleA(source, x + X, y + Y, rgb);

					// I dodajemo ga na sumu
					accum[0] += rgb[0];
					accum[1] += rgb[1];
					accum[2] += rgb[2];
				}

				// Konacan piksel nam je prosjek uzetih uzoraka
				rgb[0] = accum[0] / sampleCount;
				rgb[1] = accum[1] / sampleCount;
				rgb[2] = accum[2] / sampleCount;
				rgb[3] = help[3];

				target.setPixel(x, y, rgb);
			}
		}

		return Util.rasterToImage(target);
	}

	private static void bilSampleA(WritableRaster src, float u, float v, int[] color) {
		float[] a = new float[4];
		float[] b = new float[4];

		int[] UL = new int[4];
		int[] UR = new int[4];
		int[] LL = new int[4];
		int[] LR = new int[4];

		int x0 = (int) u;
		int y0 = (int) v;
		int x1 = x0 + 1;
		int y1 = y0 + 1;

		u -= x0;
		v -= y0;

		if (x0 < 0)
			x0 = 0;
		if (y0 < 0)
			y0 = 0;
		if (x1 < 0)
			x1 = 0;
		if (y1 < 0)
			y1 = 0;

		if (x0 >= src.getWidth())
			x0 = src.getWidth() - 1;
		if (y0 >= src.getHeight())
			y0 = src.getHeight() - 1;
		if (x1 >= src.getWidth())
			x1 = src.getWidth() - 1;
		if (y1 >= src.getHeight())
			y1 = src.getHeight() - 1;

		src.getPixel(x0, y0, UL);
		src.getPixel(x1, y0, UR);
		src.getPixel(x0, y1, LL);
		src.getPixel(x1, y1, LR);

		Util.lerpRGBAif(UL, UR, u, a);
		Util.lerpRGBAif(LL, LR, u, b);

		color[0] = (int) (Util.lerpF(a[0], b[0], v));
		color[1] = (int) (Util.lerpF(a[1], b[1], v));
		color[2] = (int) (Util.lerpF(a[2], b[2], v));
		color[3] = (int) (Util.lerpF(a[3], b[3], v));

		Util.clampRGBA(color);
	}

	public static BufferedImage setTransparentBackground(BufferedImage image) {
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		// fill background
		g2.setComposite(AlphaComposite.Clear);
		g2.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
		// draw image
		g2.setComposite(AlphaComposite.Src);

		g2.drawImage(image, 0, 0, null);
		g2.dispose();

		return newImage;
	}

	public static BufferedImage mirrorFlip(BufferedImage image) {
		WritableRaster source = image.getRaster();
		WritableRaster target = Util.createRaster(image.getWidth(), image.getHeight(), true);
		int rgb[] = new int[4];

		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth(); x++) {
				source.getPixel(x, y, rgb);
				target.setPixel(source.getWidth() - x - 1, y, rgb);
			}
		}
		return Util.rasterToImage(target);
	}
}
