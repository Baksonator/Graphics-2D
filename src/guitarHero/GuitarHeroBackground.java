package guitarHero;

import java.awt.Polygon;
import java.awt.image.WritableRaster;
import rafgfxlib.Util;

/* 
 * Class that creates background for Guitar Hero game.
 */
public class GuitarHeroBackground {
	
	// final raster that represents the background
	private WritableRaster raster;
	// 2 colors for perlin noise
	private int[] color1 = { 0, 0, 0 };
	private int[] color2 = { 255, 0, 0 };
	// color of the road for the circles
	private int[] road = {0, 0, 0};
	
	// creation of background with default colors for perlin noise - red and black
	// and default color for the road - black
	public GuitarHeroBackground() {
		raster = createRoadWithPerlinNoise(road, color1, color2);
	}
	
	public GuitarHeroBackground(int[] firstColor, int[] secondColor) {
		color1 = firstColor;
		color2 = secondColor;
		raster = createPerlinNoise(color1, color2);
	}
	
	// creates background for the game with road and lines
	public WritableRaster createRoadWithPerlinNoise(int[] road, int[] color1, int[] color2) {
		WritableRaster perlinRaster = createPerlinNoise(color1, color2);
		int width = perlinRaster.getWidth();
		int height = perlinRaster.getHeight();
		raster = new Util().createRaster(width, height, false);
		
		int[] red = {255, 0, 0};
		
		// road for circles
		Polygon polygon = new Polygon();
		polygon.addPoint(2 * width / 3,  height / 4);
		polygon.addPoint(width / 3, height / 4);
		polygon.addPoint(width / 5, height);
		polygon.addPoint(4 * width / 5, height);
		
		// width of every line on which the circles will be moving
		int lineWidth = width / 50;
		Polygon middleLine = new Polygon();
		middleLine.addPoint(width / 2,  height / 4);
		middleLine.addPoint(width / 2 - lineWidth, height);
		middleLine.addPoint(width / 2 + lineWidth, height);
		
		Polygon leftLine = new Polygon();
		leftLine.addPoint(5 * width / 12,  height / 4);
		leftLine.addPoint(width / 3 - lineWidth, height);
		leftLine.addPoint(width / 3 + lineWidth, height);
		
		Polygon rightLine = new Polygon();
		rightLine.addPoint(7 * width / 12,  height / 4);
		rightLine.addPoint(2 * width / 3 - lineWidth, height);
		rightLine.addPoint(2 * width / 3 + lineWidth, height);
		
		int[] color = new int[3];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (middleLine.contains(x, y) || leftLine.contains(x, y) || rightLine.contains(x, y)) {
					raster.setPixel(x, y, red);
				}
				else if (polygon.contains(x, y)) {
					raster.setPixel(x, y, road);
				}
				else {
					perlinRaster.getPixel(x, y, color);
					raster.setPixel(x, y, color);
				}
			}
		}
		
		return raster;
	}
	
	// creates perlin noise with 2 colors as a raster
	public WritableRaster createPerlinNoise(int[] color1, int[] color2) {
		
		int octaves = 10;
		int octaveSize = 2;
		float persistence = 0.75f;
		int width = (int)Math.pow(octaveSize, octaves);
		int height = width;
		
		WritableRaster target = Util.createRaster(width, height, false);
		
		float[][] tempMap = new float[width][height];
		float[][] finalMap = new float[width][height];
		float multiplier = 1.0f;
		
		for(int o = 0; o < octaves; ++o) {
			float[][] octaveMap = new float[octaveSize][octaveSize];
			for(int x = 0; x < octaveSize; ++x) {
				for(int y = 0; y < octaveSize; ++y) {
					octaveMap[x][y] = ((float)Math.random() - 0.5f) * 2.0f;
				}
			}
			
			Util.floatMapRescale(octaveMap, tempMap);
			Util.floatMapMAD(tempMap, finalMap, multiplier);
			
			octaveSize *= 2;
			multiplier *= persistence;
		}
		
		Util.mapFloatMapToRaster(finalMap, -1.0f, 1.0f, color1, color2, target);
		
		return target;
	}
	

	public WritableRaster getRaster() {
		return raster;
	}
	
}
