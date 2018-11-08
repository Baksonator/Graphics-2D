package project;

import guitarHero.GuitarHeroFrame;
import imageGenerator.ImageCollector;
import rafgfxlib.GameHost;
import snake.SnakeFrame;

public class Main {

	public static void main(String[] args) {
		GameHost host = new GameHost("BANTer", 1080, 720, false);
		
		host.setUpdateRate(61);
		
		new ImageCollector();
		new MainFrame(host);
		new ChickenInvaderFrame(host);
		new GuitarHeroFrame(host);
		new SnakeFrame(host);
		new Transition(host);
		
		host.setState("chickenFrame");
//		host.setState("GuitarHeroFrame");
//		host.setState("SnakeFrame");
	}

}
