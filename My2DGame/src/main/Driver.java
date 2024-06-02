package main;

import javax.swing.JFrame;

public class Driver {
	
	public static JFrame window;
	
	public static void main(String[] args) {

		window = new JFrame();		
		
		// WINDOW PROPERTIES
		window.setTitle("Link's Reawakening");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel); 
		
		// LOAD SETTINGS
		gamePanel.config.loadConfig();
		if (gamePanel.fullScreenOn) {
			window.setUndecorated(true);	
		}
		
		// RESIZE WINDOW
		window.pack(); 
		window.setLocationRelativeTo(null);
		window.setVisible(true);		
		
		// START
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}
}