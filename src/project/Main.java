package project;

import guitarHero.GuitarHeroFrame;
import rafgfxlib.GameHost;
import snake.SnakeFrame;

public class Main {

	public static void main(String[] args) {
		GameHost host = new GameHost("BANTer", 1080, 720, false);
//		GameHost host = new GameHost("BANTer", 900, 900, false);
		
		host.setUpdateRate(61);
		
		new MainFrame(host);
		new ChickenInvaderFrame(host);
//		new GuitarHeroFrame(host);
		new SnakeFrame(host);
		
		host.setState("chickenFrame");
//		host.setState("GuitarHeroFrame");
//		host.setState("SnakeFrame");
	}

}
