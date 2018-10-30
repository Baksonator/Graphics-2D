package project;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import rafgfxlib.GameFrame;

public class MainFrame extends GameFrame {

	private Tilemap mapa;
	
	private SpriteSheet heroSheet;
	private AnimatedEntity heroj;
	
	private static final int ANIM_DOWN = 0;
	private static final int ANIM_LEFT = 1;
	private static final int ANIM_UP = 2;
	private static final int ANIM_RIGHT = 3;
	
	public MainFrame() {
		
		super("BANTer", 640, 480);
		
		setUpdateRate(60);
		
		mapa = new Tilemap();
		
		heroSheet = new SpriteSheet("tileset/characterTemporary.png", 10, 4);
		heroSheet.setOffsets(64, 64);
		
		heroj = new AnimatedEntity(heroSheet, 352, 272);
		
		startThread();
	}

	private static final long serialVersionUID = -7327351674628534737L;

	@Override
	public void handleKeyDown(int keyCode) {
		if(keyCode == KeyEvent.VK_DOWN)
		{
			heroj.setAnimation(ANIM_DOWN);
			heroj.play();
		}
		else if(keyCode == KeyEvent.VK_UP)
		{
			heroj.setAnimation(ANIM_UP);
			heroj.play();
		}
		else if(keyCode == KeyEvent.VK_LEFT)
		{
			heroj.setAnimation(ANIM_LEFT);
			heroj.play();
		}
		else if(keyCode == KeyEvent.VK_RIGHT)
		{
			heroj.setAnimation(ANIM_RIGHT);
			heroj.play();
		}
	}

	@Override
	public void handleKeyUp(int keyCode) {
		if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP ||
				keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT)
		{
			heroj.stop();
			heroj.setFrame(5);
		}
	}

	@Override
	public void handleMouseDown(int arg0, int arg1, GFMouseButton arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMouseMove(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMouseUp(int arg0, int arg1, GFMouseButton arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleWindowDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleWindowInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D g, int sw, int sh) {
		int x0 = mapa.getCamX() / Tilemap.TILE_W;
		int x1 = x0 + (getWidth() / Tilemap.TILE_W) + 1;
		int y0 = mapa.getCamY() / Tilemap.TILE_H;
		int y1 = y0 + (getHeight() / Tilemap.TILE_H) + 1;
		
		if(x0 < 0) x0 = 0;
		if(y0 < 0) y0 = 0;
		if(x1 < 0) x1 = 0;
		if(y1 < 0) y1 = 0;
		
		if(x0 >= mapa.getMapW()) x0 = mapa.getMapW() - 1;
		if(y0 >= mapa.getMapH()) y0 = mapa.getMapH() - 1;
		if(x1 >= mapa.getMapW()) x1 = mapa.getMapW() - 1;
		if(y1 >= mapa.getMapH()) y1 = mapa.getMapH() - 1;
		
		Tile[] tileSet = mapa.getTileSet();
		for(int y = y0; y <= y1; ++y)
		{
			for(int x = x0; x <= x1; ++x)
			{
				g.drawImage(tileSet[0].image, 
						x * Tilemap.TILE_W + tileSet[0].offsetX - mapa.getCamX(), 
						y * Tilemap.TILE_H + tileSet[0].offsetY - mapa.getCamY(), 
						null);
			}
		}
		
		heroj.draw(g);
	}

	@Override
	public void update() {
		if(isKeyDown(KeyEvent.VK_LEFT)) {
			mapa.setCamX(mapa.getCamX() - 10);
		}
		else if(isKeyDown(KeyEvent.VK_RIGHT)) {
			mapa.setCamX(mapa.getCamX() + 10);
		}
		else if(isKeyDown(KeyEvent.VK_UP)) {
			mapa.setCamY(mapa.getCamY() - 10);
		}
		else if(isKeyDown(KeyEvent.VK_DOWN)) {
			mapa.setCamY(mapa.getCamY() + 10);
		}
		
		heroj.update();
	}

	public static void main(String[] args) {
		GameFrame gf = new MainFrame();
		gf.initGameWindow();
	}
}
