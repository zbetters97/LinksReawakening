package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Key;

public class UI {
	
	GamePanel gp;
	Font itemCount, itemGet, winGame;
	
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	
	BufferedImage keyCount;
	
	public boolean gameFinished = false;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		// FONT DECLARATION
		itemCount = new Font("Arial", Font.PLAIN, 40);
		itemGet = new Font("Arial", Font.PLAIN, 25);
		winGame = new Font("Arial", Font.BOLD, 65);
		
		// ITEM COUNT
		OBJ_Key key = new OBJ_Key(gp);
		keyCount = key.image;
	}
	
	public void displayMessage(String text) {		
		message = text;
		messageOn = true;
	}
	public void draw(Graphics2D g2) {
		
		g2.setFont(itemCount);
		g2.setColor(Color.white);
		
		if (!gameFinished) {
			
			// DISPLAY KEY COUNT			
			g2.drawImage(keyCount, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
			g2.drawString("" + gp.player.hasKey, 65, 65);
			
			// DISPLAY MESSAGE
			if (messageOn) {
				
				g2.setFont(itemGet);
				int textLength = (int)g2.getFontMetrics().getStringBounds(message, g2).getWidth();			
				int x = gp.screenWidth / 2 - textLength / 2;
				int y = gp.screenHeight / 2 + (gp.tileSize * 4);				
				g2.drawString(message, x, y);
				
				// DISPLAY FOR 2 SECONDS
				messageCounter++;
				if (messageCounter > 120) {
					messageCounter = 0;
					messageOn = false;
				}
			}
		}
		else {		
			
			// DISPLAY WIN GAME
			String text = "YOU FOUND THE TREASURE!";
			int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();			
			int x = gp.screenWidth / 2 - textLength / 2;
			int y = gp.screenHeight / 2 - (gp.tileSize * 3);			
			g2.drawString(text, x, y);
			
			// DISPLAY WIN GAME
			g2.setFont(winGame);
			
			text = "CONGRATULATIONS!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();			
			x = gp.screenWidth / 2 - textLength / 2;
			y = gp.screenHeight / 2 + (gp.tileSize * 3);			
			g2.drawString(text, x, y);
			
			gp.gameThread = null;
		}
	}
}














