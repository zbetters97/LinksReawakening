package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Key;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	
	public boolean gameFinished = false;	
	
	Font arial_25, arial_40, arial_65B;
	
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	
	public String currentDialogue = "";
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		// FONT DECLARATION
		arial_25 = new Font("Arial", Font.PLAIN, 25);
		arial_40 = new Font("Arial", Font.PLAIN, 40);		
		arial_65B = new Font("Arial", Font.BOLD, 65);
	}
	
	public void displayMessage(String text) {		
		message = text;
		messageOn = true;
	}
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		if (gp.gameState == gp.playState) {			
		}
		if (gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		if (gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
		}
	}
	
	public void drawPauseScreen() {
		
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
		
		g2.setFont(arial_25);
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