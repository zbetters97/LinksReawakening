package main;

import javax.swing.JFrame;

public class Main {
	
	public static JFrame window;
	
	public static void main(String[] args) {

		window = new JFrame();		
		
		// WINDOW PROPERTIES
		window.setTitle("2D Adventure");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		// LOAD SETTINGS
		gamePanel.config.loadConfig();
		if (gamePanel.fullScreenOn) {
			window.setUndecorated(true);	
		}
		
		window.pack(); // resizes window relative to its contents	
		window.setLocationRelativeTo(null);
		window.setVisible(true);		
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}
}