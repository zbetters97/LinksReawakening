package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	
	Font PK_DS;
	
	public boolean gameFinished = false;
	
	// MENU OPTION SELECTION
	public int commandNum = 0;
	
	// TITLE SCREEN STATE
	public int titleScreenState = 0;
	
	// PLAYER NAME
	public String playerName = "";
	
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	
	public String currentDialogue = "";
	
	public UI(GamePanel gp) {
		
		this.gp = gp;
		
		// FONT DECLARATION		
		try {
			InputStream is;
			
			is = getClass().getResourceAsStream("/font/pokemon-ds.ttf");
			PK_DS = Font.createFont(Font.TRUETYPE_FONT, is);
		} 
		catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void displayMessage(String text) {		
		message = text;
		messageOn = true;
	}
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(PK_DS);
		g2.setColor(Color.white);
		
		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}		
		// PLAY STATE
		if (gp.gameState == gp.playState) {
			
		}		
		// PAUSE STATE
		if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}		
		// DIALOGUE STATE
		if (gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
		}
	}
	
	public void drawTitleScreen() {
		
		// BACKGROUND
		g2.setColor(new Color(65, 90, 255));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// SUBTITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
		String text = "The Legend of Zelda";
		int x = getXforCenteredText(text);
		int y = gp.tileSize / 2;
				
		// TEXT COLOR
		g2.setColor(Color.RED);
		g2.drawString(text, x, y);
		
		// TITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));
		text = "LINK'S AWAKENING";
		x = getXforCenteredText(text);
		y += gp.tileSize * 1.5;
		
		// TEXT SHADOW
		g2.setColor(Color.BLACK);
		g2.drawString(text, x+5, y+5);
		
		// TEXT COLOR
		g2.setColor(Color.WHITE);
		g2.drawString(text, x, y);
					
		// MAIN TITLE SCREEN
		if (titleScreenState == 0) {			
			
			// MARIN IMAGE
			x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2; // center sprite
			y += gp.tileSize - (gp.tileSize / 2);
			g2.drawImage(gp.player.sing, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
			
			// LINK IMAGE
			x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2; // center sprite
			y += gp.tileSize * 4;
			g2.drawImage(gp.player.sit, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
			
			// MENU OPTIONS
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
			text = "NEW GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize * 3;
			g2.drawString(text, x, y);
			if (commandNum == 0) g2.drawString(">", x - gp.tileSize / 2, y);
			
			text = "LOAD GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize / 1.2;
			g2.drawString(text, x, y);
			if (commandNum == 1) g2.drawString(">", x - gp.tileSize / 2, y);			
			
			text = "QUIT";
			x = getXforCenteredText(text);
			y += gp.tileSize / 1.2;
			g2.drawString(text, x, y);
			if (commandNum == 2) g2.drawString(">", x - gp.tileSize / 2, y);
			
		}
		// NEW GAME SELECTED
		else if (titleScreenState == 1) {
			
			g2.setColor(Color.WHITE);
			g2.setFont(g2.getFont().deriveFont(42F));
			
			text = "YOUR NAME, PLEASE";
			x = getXforCenteredText(text);
			y += gp.tileSize * 2;
			g2.drawString(text, x, y);
			
			if (playerName.length() <= 10)
				text = "-> " + playerName + "_";
			else
				text = "-> " + playerName;
			x = gp.screenWidth / 3;
			y += gp.tileSize * 1.5;
			g2.drawString(text, x, y);			
			
			x = gp.tileSize * 3;
			y += gp.tileSize;			
			String keyboard = "QWERTYUIOPASDFGHJKLZXCVBNM";
			for (int i = 0; i < keyboard.length(); i++) {	
				
				if (keyboard.charAt(i) == 'A' || keyboard.charAt(i) == 'Z') {
					x = gp.tileSize * 3;
					y+= gp.tileSize;
				}
				
				x += gp.tileSize;		
				if (commandNum == i) 
					g2.drawString("(" + keyboard.charAt(i) + ")", x, y);
				else
					g2.drawString(" " + keyboard.charAt(i) + " ", x, y);
			} 	
			
			x += gp.tileSize;		
			if (commandNum == 26) 
				g2.drawString("(DEL)", x, y);	
			else
				g2.drawString(" DEL ", x, y);	

			x = gp.screenWidth / 3;
			y += gp.tileSize * 1.5;
			g2.drawString("GO BACK", x, y);
			if (commandNum == 27) g2.drawString(">", x - gp.tileSize / 2, y);	
			
			x += gp.tileSize * 4;		
			g2.drawString("ENTER", x, y);
			if (commandNum == 28 && playerName.length() > 0) 
				g2.drawString(">", x - gp.tileSize / 2, y);	
		}
	}
	
	public void drawPauseScreen() {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50F));
		
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 2;
		
		g2.drawString(text, x, y);
	}
	
	public void drawDialogueScreen() {
		
		int x = gp.tileSize * 2;
		int y = gp.screenWidth / 2;
		int width = gp.screenWidth - (gp.tileSize * 4);
		int height = gp.tileSize * 4;
		
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 37F));
		x += gp.tileSize;
		y += gp.tileSize;
		
		// DIALOGUE LINE BREAK
		for (String line : currentDialogue.split("\n")) { 
			g2.drawString(line, x, y);	
			y += 40;
		}
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		// BLACK COLOR (RGB, Transparency)
		Color c = new Color(0,0,0,220);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 25, 25); // 35px round corners
		
		// WHITE COLOR (RGB)
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 15, 15);
	}
	
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = (gp.screenWidth / 2) - (length / 2);
		return x;
	}
}