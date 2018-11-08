package project;

import rafgfxlib.Util;

public class Tilemap {

	public static final int TILE_W = 64;
	public static final int TILE_H = 64;
	
	private int mapW = 100;
	private int mapH = 100;
	
	private int camX = 0;
	private int camY = 0;

	private Tile[] tileSet = new Tile[32];
	private int[][] tileMap = new int[mapW][mapH];
	
	public Tilemap() {
		
		tileSet[0] = new Tile(Util.loadImage("tileset/Basic_tile.png"), 0, TILE_H);

//		Koristicemo ovaj deo kod random generisanja drugih tipova polja
//		for(int y = 0; y < mapH; ++y)
//		{
//			for(int x = 0; x < mapW; ++x)
//			{
//				tileMap[x][y] = Math.abs(rnd.nextInt()) % 3;
//			}
//		}
		
	}

	public int getMapW() {
		return mapW;
	}

	public void setMapW(int mapW) {
		this.mapW = mapW;
	}

	public int getMapH() {
		return mapH;
	}

	public void setMapH(int mapH) {
		this.mapH = mapH;
	}

	public int getCamX() {
		return camX;
	}

	public void setCamX(int camX) {
		this.camX = camX;
	}

	public int getCamY() {
		return camY;
	}

	public void setCamY(int camY) {
		this.camY = camY;
	}
	
	public Tile[] getTileSet() {
		return tileSet;
	}

	public void setTileSet(Tile[] tileSet) {
		this.tileSet = tileSet;
	}

	public int[][] getTileMap() {
		return tileMap;
	}

	public void setTileMap(int[][] tileMap) {
		this.tileMap = tileMap;
	}
	
}
