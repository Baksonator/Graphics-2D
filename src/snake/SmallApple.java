package snake;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Random;

import imageGenerator.ImageGenerator;
import rafgfxlib.Util;

public class SmallApple {
	
	public float posX;
	public float posY;
	public float dX;
	public float dY;
	public int life = 0;
	public int lifeMax = 0;
	public float angle = 0.0f;
	public float rot = 0.0f;
	public float radius = 3.0f;
	private BufferedImage smallAppleImage;
	
	public SmallApple() {
		smallAppleImage = createSmallAppleImage();
	}
	
	public static BufferedImage createSmallAppleImage() {
		Random r = new Random();
		int random = r.nextInt(10) % 3 + 1;
		BufferedImage smallApple;
		
		switch (random) {
		case 0:
			smallApple = Util.loadImage("objects/green_small_apple.png");
			//smallApple = Util.loadImage("objects/blue_dark_apple.jpg");
			break;
		case 1:
			smallApple = Util.loadImage("objects/red_small_apple.png");		
			//smallApple = Util.loadImage("objects/red_dark_apple.jpg");		
			break;
		case 2:
			smallApple = Util.loadImage("objects/yellow_small_apple.png");
			break;
		default:
			smallApple = Util.loadImage("objects/green_small_apple.jpg");
			break;
		}
		
		smallApple = ImageGenerator.scaleImage(smallApple, 30, 30);
//		int width = 30;
//		int height = 30;
//		
//		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//		//BufferedImage newImage = smallApple;
//        Graphics2D g2 = newImage.createGraphics();
//        //fill background
//        g2.setComposite(AlphaComposite.Clear);
//        g2.fillRect(0, 0, width, height);
//        //draw image
//        g2.setComposite(AlphaComposite.Src);
//        g2.drawImage(smallApple, null, 0, 0);
////        for (int i = 0; i < arrayImg.length; i++) {
////        	g2.drawImage(arrayImg[i], null, arrayImg[i].getWidth() * i, 0);
////        }
//        g2.dispose();
//        return newImage;
//		
		WritableRaster source = smallApple.getRaster();
		WritableRaster target = Util.createRaster(source.getWidth(), source.getHeight(), false);
		
		int[] rgb = {0, 0, 0};
		for (int y = 0; y < source.getHeight(); y++) {
			for (int x = 0; x < source.getWidth(); x++) {
				source.getPixel(x, y, rgb);
				if (rgb[0] >= 240 && rgb[1] >= 240 && rgb[2] >= 240) {
					rgb[0] = 0;
					rgb[1] = 0;
					rgb[2] = 0;
				}
				target.setPixel(x, y, rgb);
			}
		}
		
		return Util.rasterToImage(target);
	}
	
	public void updateSmallApple() {
		life--;
		posX += dX;
		posY += dY;
		dX *= 0.99f;
		dY *= 0.99f;
		dY += 0.1f;
		angle += rot;
		rot *= 0.99f;
	}
	
	public void makeSmallApple(float positionX, float positionY, int life) {
		life = lifeMax = (int)(Math.random() * life * 0.5) + life / 2;
		posX = positionX;
		posY = positionY;
		double angle = Math.random() * Math.PI * 2.0;
		double speed = Math.random() * radius;
		dX = (float)(Math.cos(angle) * speed);
		dY = (float)(Math.sin(angle) * speed);
		angle = (float)(Math.random() * Math.PI * 2.0);
		rot = (float)(Math.random() - 0.5) * 0.3f;
	}

	public BufferedImage getSmallAppleImage() {
		return smallAppleImage;
	}

	public void setSmallAppleImage(BufferedImage smallAppleImage) {
		this.smallAppleImage = smallAppleImage;
	}
	

}
