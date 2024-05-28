package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entity.*;
import object.*;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	
	Font PK_DS;
	
	public boolean gameFinished = false;
	
	// TITLE SCREEN STATE
	public int titleScreenState = 0;
	
	// MENU OPTION SELECTION
	public int commandNum = 0;
	
	// OPTIONS MEU
	int subState = 0;
	
	
	// ITEM MENU
	public int slotCol = 0;
	public int slotRow = 0;

	// DIALOGUE HANDLER	
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean messageOn = false;
	public String currentDialogue = "";
	
	// HUD
	BufferedImage heart_full, heart_half, heart_empty, arrow_full, arrow_empty, rupee_hud, boots_hud;
	public String arrow_count, rupee_count = "0";
	
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
		
		// CREATE HUD
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_empty = heart.image3;
		
		Entity arrows = new OBJ_Arrows(gp);
		arrow_full = arrows.image;
		arrow_empty = arrows.image2;
		
		Entity rupees = new OBJ_Rupee_Blue(gp);
		rupee_hud = rupees.down1;
		
		Entity boots = new OBJ_Boots(gp);
		boots_hud = boots.down1;		
	}
	
	public void addMessage(String text) {
		message.add(text);		
		messageCounter.add(0);
	}
	
	public void drawMessage() {
		
		int messageX = gp.tileSize;
		int messageY = gp.tileSize * 4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
		
		for (int i = 0; i< message.size(); i++) {
			
			if (message.get(i) != null) {
				
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX+2, messageY+2);
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				
				int counter = messageCounter.get(i) + 1;
				messageCounter.set(i, counter);
				messageY += gp.tileSize;
				
				if (messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
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
		if (gp.gameState == gp.playState || gp.gameState == gp.itemState) {
			drawHUD();
			drawMessage();
		}		
		// PAUSE STATE
		if (gp.gameState == gp.pauseState) {
			drawHUD();
			drawPauseScreen();
		}		
		// DIALOGUE STATE
		if (gp.gameState == gp.dialogueState) {
			drawHUD();
			drawDialogueScreen();
		}
		// CHARACTER STATE
		if (gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory();
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
						
			y = gp.tileSize * 3;			
			int width = gp.screenWidth - (gp.tileSize * 4);
			int height = gp.screenHeight - (gp.tileSize * 4);			
			drawSubWindow(x, y, width, height);
			
			// TEXT COLOR
			g2.setColor(Color.WHITE);
			g2.setFont(g2.getFont().deriveFont(42F));
			
			// NAME SELECT TITLE
			text = "YOUR NAME, PLEASE";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			
			// DISPLAY NAME
			if (gp.player.name.length() <= 10) 
				text = "-> " + gp.player.name + "_";
			else 
				text = "-> " + gp.player.name;		
			x = gp.screenWidth / 3;
			y += gp.tileSize * 1.5;
			g2.drawString(text, x, y);			
			
			// DISPLAY ON-SCREEN KEYBOARD
			x = gp.tileSize * 3;
			y += gp.tileSize;			
			
			String keyboard = "";
			
			if (gp.keyH.isCapital) keyboard = "QWERTYUIOPASDFGHJKLZXCVBNM";	
			else keyboard = "qwertyuiopasdfghjklzxcvbnm";
			
			for (int i = 0; i < keyboard.length(); i++) {	
				
				// NEW LINE (RESET X)
				if (keyboard.charAt(i) == 'A' || keyboard.charAt(i) == 'Z') {
					x = gp.tileSize * 3;
					y+= gp.tileSize;
				}
				if (keyboard.charAt(i) == 'a' || keyboard.charAt(i) == 'z') {
					x = gp.tileSize * 3;
					y+= gp.tileSize;
				}
				
				// HIGHLIGHT SELECTED LETTER
				x += gp.tileSize;		
				if (commandNum == i) 
					g2.drawString("(" + keyboard.charAt(i) + ")", x, y);
				else
					g2.drawString(" " + keyboard.charAt(i) + " ", x, y);
			} 	
			
			// DEL BUTTON (SAME Y AS KEYBOARD)
			x += gp.tileSize - 10;		
			if (commandNum == 26) 
				g2.drawString("(DEL)", x, y);	
			else
				g2.drawString(" DEL ", x, y);	
			
			x += gp.tileSize + 20;		
			if (commandNum == 27) 
				g2.drawString("(CAP)", x, y);	
			else
				g2.drawString(" CAP ", x, y);	

			// BACK / ENTER BUTTONS
			x = gp.screenWidth / 3;
			y += gp.tileSize * 1.8;
			g2.drawString("GO BACK", x, y);
			if (commandNum == 28) 
				g2.drawString(">", x - gp.tileSize / 2, y);	
			
			x += gp.tileSize * 4;		
			g2.drawString("ENTER", x, y);
			if (commandNum == 29 && gp.player.name.length() > 0) 
				g2.drawString(">", x - gp.tileSize / 2, y);	
		}
	}
		
	public void drawPauseScreen() {
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));

		// SUB WINDOW
		int frameX = gp.tileSize * 2;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 12;
		int frameHeight = gp.tileSize * 9;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch(subState) {
			case 0: options_top(frameX, frameY); break;
			case 1: options_fullscreenNotif(frameX, frameY); break;
			case 2: options_controls(frameX, frameY); break;
			case 3: options_quitGameConfirm(frameX, frameY); break;
		}
		gp.keyH.spacePressed = false;
	}
	
	public void options_top(int frameX, int frameY) {
		
		int textX;
		int textY;
		
		// TITLE
		String text = "OPTIONS";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		// FULL SCREEN ON/OFF
		textX = frameX + gp.tileSize;
		textY += gp.tileSize;
		g2.drawString("Full Screen", textX, textY);		
		if (commandNum == 0) {
			
			g2.drawString(">", textX - 25, textY);		
			if (gp.keyH.spacePressed) {
				if (gp.fullScreenOn) {
					gp.fullScreenOn = false;
				}
				else {
					gp.fullScreenOn = true;
				}
				subState = 1;
			}
		}
		
		// MUSIC VOLUME
		textY += gp.tileSize;
		g2.drawString("Music", textX, textY);
		if (commandNum == 1) g2.drawString(">", textX - 25, textY);
		
		// SOUND EFFECTS VOLUME
		textY += gp.tileSize;
		g2.drawString("Sound Effects", textX, textY);
		if (commandNum == 2) g2.drawString(">", textX - 25, textY);
		
		// CONTROLS
		textY += gp.tileSize;
		g2.drawString("Controls", textX, textY);
		if (commandNum == 3) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.spacePressed) {
				subState = 2;
				commandNum = 0;
			}
		}
		
		// QUIT GAME
		textY += gp.tileSize;
		g2.drawString("Quit", textX, textY);
		if (commandNum == 4) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.spacePressed) {
				subState = 3;
				commandNum = 0;
			}
		}
		
		// BACK
		textY += gp.tileSize * 2;
		g2.drawString("Save and Exit", textX, textY);
		if (commandNum == 5) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.spacePressed) {				
				commandNum = 0;
				subState = 0;
				gp.gameState = gp.playState;
			}
		}
				
		// FULL SCREEN CHECK BOX
		textX = frameX + (int)(gp.tileSize * 4.5);
		textY = frameY + gp.tileSize * 2 - (gp.tileSize / 2);
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, 24, 24);
		if (gp.fullScreenOn) g2.fillRect(textX, textY, 24, 24);
						
		// MUSIC SLIDER
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24); // 120/5 = 24
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		// SOUND EFFECTS SLIDER
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24);
		g2.drawRect(textX, textY, 120, 24); // 120/5 = 24
		volumeWidth = 24 * gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		gp.config.saveConfig();
	}
	
	public void options_fullscreenNotif(int frameX, int frameY) {
		
		int textX = frameX + gp.tileSize;
		int textY = frameX + gp.tileSize;
		
		currentDialogue = "The change will take effect\nafter restarting the game.";
		
		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
		// BACK
		textY = frameY + gp.tileSize * 8;
		g2.drawString("Back", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			
			if (gp.keyH.spacePressed) {
				commandNum = 0;
				subState = 0;
			}
		}
	}
	
	public void options_controls(int frameX, int frameY) {
		
		int textX;
		int textY;
		
		String text = "CONTROLS";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize * 2;
		
		g2.drawString("MOVE", textX, textY); textY += gp.tileSize;
		g2.drawString("ACTION", textX, textY); textY += gp.tileSize;
		g2.drawString("USE ITEM", textX, textY); textY += gp.tileSize;
		g2.drawString("CHARACTER SCREEN", textX, textY); textY += gp.tileSize;
		g2.drawString("OPTIONS", textX, textY); textY += gp.tileSize;
		
		textX = frameX + gp.tileSize * 6;
		textY = frameY + gp.tileSize * 2;
		g2.drawString("WASD / ARROW KEYS", textX, textY); textY += gp.tileSize;
		g2.drawString("SPACEBAR", textX, textY); textY += gp.tileSize;
		g2.drawString("F / G / SHIFT", textX, textY); textY += gp.tileSize;
		g2.drawString("E", textX, textY); textY += gp.tileSize;
		g2.drawString("ESC", textX, textY); textY += gp.tileSize;
		
		// BACK
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize * 8;
		g2.drawString("Back", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			
			if (gp.keyH.spacePressed) {
				commandNum = 3;
				subState = 0;
			}
		}
	}
	
	public void options_quitGameConfirm(int frameX, int frameY) {
		
		// TITLE
		String text = "Go back to the title screen?";
		int textX = getXforCenteredText(text);
		int textY = frameX + gp.tileSize * 2;		
		g2.drawString(text, textX, textY);
		
		// YES
		text = "YES";
		textX = getXforCenteredText(text);
		textY += gp.tileSize * 3;
		g2.drawString(text, textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			
			if (gp.keyH.spacePressed) {
				subState = 0;				
				gp.gameState = gp.titleState;
				gp.stopMusic();
				gp.playMusic(0);
			}
		}
		
		// NO
		text = "NO";
		textX = getXforCenteredText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX - 25, textY);
			
			if (gp.keyH.spacePressed) {
				commandNum = 4;
				subState = 0;
			}
		}
	}
	
	public void drawHUD() {
		
		int x = gp.tileSize / 2;
		int y = gp.tileSize / 2;
		int i = 0;
		
		// DRAW MAX LIFE		
		while (i < gp.player.maxLife / 2) {
			g2.drawImage(heart_empty, x, y, null);
			i++;
			x += gp.tileSize / 1.7;
		}
		
		x = gp.tileSize / 2;
		y = gp.tileSize / 2;
		i = 0;
		
		// DRAW CURRENT LIFE
		while (i < gp.player.life) {			
			g2.drawImage(heart_half, x, y, null);
			i++;
			
			if (i < gp.player.life) 
				g2.drawImage(heart_full, x, y, null);
			
			i++;
			x += gp.tileSize / 1.7;
		}
		
		// DRAW RUPEES
		x = (gp.tileSize * gp.maxScreenCol) - (gp.tileSize * 4);
		y = gp.tileSize / 2;
		
		g2.drawImage(rupee_hud, x, y, gp.tileSize -  10, gp.tileSize - 10, null);	
		
		x += 35;
		y += gp.tileSize - 17;	
		
		rupee_count = Integer.toString(gp.player.rupee);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
		g2.drawString(rupee_count, x, y);	

		// DRAW ARROWS
		x += 30;
		y = gp.tileSize / 2;
		
		if (gp.player.arrows != 0 && gp.player.canShoot) {
			g2.drawImage(arrow_full, x, y, null);
			
			x += 30;
			y += gp.tileSize - 17;	
			
			arrow_count = Integer.toString(gp.player.arrows);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
			g2.drawString(arrow_count, x, y);	
		}
		else {
			g2.drawImage(arrow_empty, x, y, null);
			x += 30;
		}
		
		// DRAW BOOTS
		x += 25;
		y = gp.tileSize / 2;
		
		if (gp.player.canRun)
			g2.drawImage(boots_hud, x, y, gp.tileSize - 10, gp.tileSize - 10, null);
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
	
	public void drawCharacterScreen() {
		
		// WINDOW
		final int frameX = gp.tileSize;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize * 5;
		final int frameHeight = gp.tileSize * 10;		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		int textX = gp.tileSize * 3 + 5;
		int textY = frameY + gp.tileSize - 15;
		final int lineHeight = 35;		
		
		g2.drawString(gp.player.name, textX, textY); textY += lineHeight;
		
		// NAMES
		textX = frameX + 20;
		g2.drawString("Level", textX, textY); textY += lineHeight;
		g2.drawString("Life", textX, textY); textY += lineHeight;
		g2.drawString("Strength", textX, textY); textY += lineHeight;
		g2.drawString("Dexterity", textX, textY); textY += lineHeight;
		g2.drawString("Attack", textX, textY); textY += lineHeight;
		g2.drawString("Defense", textX, textY); textY += lineHeight;
		g2.drawString("EXP", textX, textY);	textY += lineHeight;
		g2.drawString("Next Level", textX, textY); textY += lineHeight;
		g2.drawString("Rupees", textX, textY); textY += lineHeight + 25;
		g2.drawString("Weapon", textX, textY); textY += lineHeight + 10;
		g2.drawString("Shield", textX, textY);
		
		// VALUES
		int tailX = (frameX + frameWidth) - 30; // right-aligned
		textY = frameY + gp.tileSize - 15 + lineHeight; // reset Y
		
		drawCharacterText(gp.player.level, tailX, textY); textY += lineHeight;
		String life = gp.player.life + " / " + gp.player.maxLife; 
		drawCharacterText(life, tailX, textY); textY += lineHeight;
		drawCharacterText(gp.player.strength, tailX, textY); textY += lineHeight;
		drawCharacterText(gp.player.dexterity, tailX, textY); textY += lineHeight;
		drawCharacterText(gp.player.attack, tailX, textY); textY += lineHeight;
		drawCharacterText(gp.player.defense, tailX, textY); textY += lineHeight;
		drawCharacterText(gp.player.exp, tailX, textY); textY += lineHeight;
		drawCharacterText(gp.player.nextLevelEXP, tailX, textY); textY += lineHeight;
		drawCharacterText(gp.player.rupee, tailX, textY); textY += lineHeight;
		
		g2.drawImage(gp.player.currentWeapon.down1, tailX - 31, textY - 10, null);
		textY += gp.tileSize + 5;
		g2.drawImage(gp.player.currentShield.down1, tailX - 31, textY - 15, null);
	}
	
	public void drawInventory() {
		
		// ITEM WINDOW
		int frameX = gp.tileSize * 9;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 6;
		int frameHeight = gp.tileSize * 5;		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
				
		// SLOTS
		final int slotXStart = frameX + 20;
		final int slotYStart = frameY + 20;
		int slotX = slotXStart; 
		int slotY = slotYStart;
		int slotSize = gp.tileSize + 3;
		
		// CURSOR
		int cursorX = slotXStart + (slotSize * slotCol);
		int cursorY = slotYStart + (slotSize * slotRow);
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;		
		
		// DRAW PLAYER ITEMS
		for (int i = 0; i < gp.player.inventory.size(); i++) {
						
			// EQUIP CURSOR
			if (gp.player.inventory.get(i) == gp.player.currentWeapon || 
					gp.player.inventory.get(i) == gp.player.currentShield) {
				
				g2.setColor(new Color(240,190,90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}
			
			g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
			slotX += slotSize;
			
			if (i == 4 || i == 9 || i == 14) {
				slotX = slotXStart;
				slotY += gp.tileSize;
			}
		}
		
		// DRAW CURSOR
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		
		// DESCRIPTION WINDOW
		int dFrameX = frameX;
		int dFrameY = frameY + frameHeight;
		int dFrameWidth = frameWidth;
		int dFrameHeight = gp.tileSize * 3;
		
		// DESCRIPTION TEXT
		int textX = dFrameX + 20;
		int textY = dFrameY + gp.tileSize;
		
		g2.setFont(g2.getFont().deriveFont(28F));
		
		int itemIndex = getItemIndexOnSlot();
		if (itemIndex < gp.player.inventory.size()) {
			
			drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
			
			for (String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
				g2.drawString(line, textX, textY);	
				textY += 32;
			}
		}
	}
	
	public int getItemIndexOnSlot() {		
		int itemIndex = slotCol + (slotRow * 5);
		return itemIndex;
	}
	
	public void drawCharacterText(int playerValue, int tailX, int textY) {
		String value = String.valueOf(playerValue);
		int textX = getXforRightAlignText(value, tailX);
		g2.drawString(value, textX, textY);
	}
	public void drawCharacterText(String value, int tailX, int textY) {
		int textX = getXforRightAlignText(value, tailX);
		g2.drawString(value, textX, textY);
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		// BLACK COLOR (RGB, Transparency)
		Color c = new Color(0,0,0,220);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 25, 25); // 25px round corners
		
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
	public int getXforRightAlignText(String text, int tailX) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
}