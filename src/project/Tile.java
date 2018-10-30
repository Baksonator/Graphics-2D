package project;

import java.awt.image.BufferedImage;

public class Tile {
	
	public BufferedImage image = null;
	public int offsetX = 0;
	public int offsetY = 0;
	@SuppressWarnings("unused")
	public int tileID = 0;
	
	public Tile(BufferedImage image, int ID, int tileH)
	{
		this.image = image;
		tileID = ID;
		if(image != null)
		{
			offsetX = 0;
			offsetY = -(image.getHeight() - tileH);
		}
	}
}
