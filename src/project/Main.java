package project;

import rafgfxlib.GameHost;

public class Main {

	public static void main(String[] args) {
		GameHost host = new GameHost("BANTer", 1080, 720, false);
		
		host.setUpdateRate(61);
		
		new MainFrame(host);
		new ChickenInvaderFrame(host);
		
		host.setState("chickenFrame");
	}

}
