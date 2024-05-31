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
		
	// TITLE SCREEN STATE
	public int titleScreenState = 0;
	
	// MENU OPTION SELECTION
	public int commandNum = 0;
	
	// HUD
	BufferedImage heart_full, heart_half, heart_empty, rupee, boots_hud;
	public String rupee_count = "0";
	
	// OPTIONS MEU
	int subState = 0;
		
	// ITEM MENU
	public int playerSlotCol = 0;
	public int playerSlotRow = 0;
	
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	
	// DIALOGUE HANDLER	
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean messageOn = false;
	public String currentDialogue = "";
	
	// TRANSITION
	int counter = 0;
	
	public Entity npc;
	public Entity newItem;
		
	// GAME OVER
	public int deathSprite = 0;
	public int deathCounter = 0;
	
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
		Entity heart = new COL_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_empty = heart.image3;
		
		Entity rupees = new COL_Rupee_Blue(gp);
		rupee = rupees.down1;	
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
		// CHARACTER INVENTORY STATE
		if (gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory(gp.player, true);
		}
		// GAME OVER STATE
		if (gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
		// TRANSITION STATE
		if (gp.gameState == gp.transitionState) {
			drawTransition();
		}
		// TRADE STATE
		if (gp.gameState == gp.tradeState) {
			drawTradeScreen();
		}
		if (gp.gameState == gp.itemGetState) {
			drawHUD();
			drawItemDialogue();
		}
	}
	
	public void drawTitleScreen() {
		
		// BACKGROUND
		g2.setColor(new Color(65, 90, 255));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// SUBTITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
		String text = "The Legend of Zelda";
		int x = getXforCenteredText(text);
		int y = gp.tileSize / 2;
				
		// TEXT COLOR
		g2.setColor(Color.RED);
		g2.drawString(text, x, y);
		
		// TITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));
		text = "LINK'S REAWAKENING";
		x = getXforCenteredText(text);
		y += gp.tileSize * 1.5;
		
		// TEXT SHADOW
		g2.setColor(Color.BLACK);
		g2.drawString(text, x+10, y+5);
		
		// TEXT COLOR
		g2.setColor(Color.WHITE);
		g2.drawString(text, x+10, y);
					
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
			drawSubWindow(x+40, y, width, height);
			
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
			x = gp.tileSize * 2;
			y += gp.tileSize;			
			
			String keyboard = "";
			
			if (gp.keyH.isCapital) keyboard = "QWERTYUIOPASDFGHJKLZXCVBNM";	
			else keyboard = "qwertyuiopasdfghjklzxcvbnm";
			
			for (int i = 0; i < keyboard.length(); i++) {	
				
				// NEW LINE (RESET X)
				if (keyboard.charAt(i) == 'A' || keyboard.charAt(i) == 'Z') {
					x = gp.tileSize * 2;
					y+= gp.tileSize;
				}
				if (keyboard.charAt(i) == 'a' || keyboard.charAt(i) == 'z') {
					x = gp.tileSize * 2;
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
			x += gp.tileSize + 10;		
			if (commandNum == 26) 
				g2.drawString("(DEL)", x, y);	
			else
				g2.drawString(" DEL ", x, y);	
			
			x += gp.tileSize + 30;		
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
	
	public void drawHUD() {
		
		int x = gp.tileSize / 2;
		int y = gp.tileSize / 2;
		int i = 0;
		
		// DRAW MAX LIFE		
		while (i < gp.player.maxLife / 2) {
			g2.drawImage(heart_empty, x, y, null);
			i++;
			x += gp.tileSize / 1.6;
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
			x += gp.tileSize / 1.6;
		}
		
		// DRAW ITEM SLOT
		x = gp.tileSize * 14;
		y = gp.tileSize / 2;
		g2.setColor(new Color(240,190,90));
		g2.fillRect(x - 10, y - 10, gp.tileSize + 20, gp.tileSize + 20);		
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(x - 10, y - 10, gp.tileSize + 20, gp.tileSize + 20);	
		
		// DRAW ITEM
		if (gp.player.currentItem != null) {			
			g2.drawImage(gp.player.currentItem.down1, x, y, gp.tileSize, gp.tileSize, null);
			
			// DRAW ARROW COUNT
			if (gp.player.currentItem.name.equals("Hylian Bow")) {	
				String arrowCount = Integer.toString(gp.player.arrows);		
				g2.setColor(Color.BLACK);
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 27F));
				g2.drawString(arrowCount, x + 35, y + gp.tileSize);
			}
		}
		
		// DRAW RUPEES
		x = gp.tileSize * 14 - 20;
		y = gp.tileSize * 10 + 20;		
		g2.drawImage(rupee, x, y, gp.tileSize - 5, gp.tileSize - 5, null);	
		
		x += gp.tileSize - 8;
		y += gp.tileSize - 12;	
		
		rupee_count = Integer.toString(gp.player.rupees);
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 45F));
		g2.drawString(rupee_count, x, y);				
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
				
		// BACK
		textY += gp.tileSize * 2;
		g2.drawString("Save and Close", textX, textY);
		if (commandNum == 4) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.spacePressed) {				
				commandNum = 0;
				subState = 0;
				gp.gameState = gp.playState;
			}
		}

		// QUIT GAME
		textY += gp.tileSize;
		g2.drawString("Quit to Title Screen", textX, textY);
		if (commandNum == 5) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.spacePressed) {
				subState = 3;
				commandNum = 0;
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
		
		// TITLE
		String text = "CONTROLS";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		// LABELS
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize * 2;		
		g2.drawString("MOVE", textX, textY); textY += gp.tileSize;
		g2.drawString("ACTION", textX, textY); textY += gp.tileSize;
		g2.drawString("USE ITEM", textX, textY); textY += gp.tileSize;
		g2.drawString("CYCLE ITEMS", textX, textY); textY += gp.tileSize;
		g2.drawString("CHARACTER SCREEN", textX, textY); textY += gp.tileSize;
		g2.drawString("OPTIONS", textX, textY); textY += gp.tileSize;
		
		// VALUES
		textX = frameX + gp.tileSize * 6;
		textY = frameY + gp.tileSize * 2;
		g2.drawString("WASD / ARROW KEYS", textX, textY); textY += gp.tileSize;
		g2.drawString("SPACEBAR", textX, textY); textY += gp.tileSize;
		g2.drawString("SHIFT", textX, textY); textY += gp.tileSize;
		g2.drawString("T", textX, textY); textY += gp.tileSize;
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
		String text = "Return to the title screen?";
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
				commandNum = 5;
				subState = 0;
			}
		}
	}
	
	public void drawCharacterScreen() {
		
		// WINDOW
		final int frameX = gp.tileSize;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize * 5;
		final int frameHeight = gp.tileSize * 9;		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(40F));
		
		int textX = gp.tileSize * 2 + 23;
		int textY = frameY + gp.tileSize - 10;
		final int lineHeight = 40;		
		
		g2.drawString("- " + gp.player.name + " -", textX, textY); textY += lineHeight + 10;
		
		// LABELS
		g2.setFont(g2.getFont().deriveFont(32F));
		textX = frameX + 30;
		g2.drawString("Level", textX, textY); textY += lineHeight;
		g2.drawString("Life", textX, textY); textY += lineHeight;
		g2.drawString("Strength", textX, textY); textY += lineHeight;
		g2.drawString("Dexterity", textX, textY); textY += lineHeight;
		g2.drawString("Attack", textX, textY); textY += lineHeight;
		g2.drawString("Defense", textX, textY); textY += lineHeight;
		g2.drawString("EXP", textX, textY);	textY += lineHeight;
		g2.drawString("Next Level", textX, textY); textY += lineHeight;
		
		// VALUES
		int tailX = (frameX + frameWidth) - 30; // right-aligned
		textY = frameY + gp.tileSize + lineHeight; // reset Y
		
		drawCharacterText(gp.player.level, tailX, textY); textY += lineHeight;
		String life = gp.player.life + " / " + gp.player.maxLife; 
		drawCharacterText(life, tailX, textY); textY += lineHeight;
		drawCharacterText(gp.player.strength, tailX, textY); textY += lineHeight;
		drawCharacterText(gp.player.dexterity, tailX, textY); textY += lineHeight;
		drawCharacterText(gp.player.attack, tailX, textY); textY += lineHeight;
		drawCharacterText(gp.player.defense, tailX, textY); textY += lineHeight;
		drawCharacterText(gp.player.exp, tailX, textY); textY += lineHeight;
		drawCharacterText(gp.player.nextLevelEXP, tailX, textY);
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
	
	public void drawInventory(Entity entity, boolean cursor) {
		
		// ITEM WINDOW
		int frameX = 0;
		int frameY = 0;
		int frameWidth = 0;
		int frameHeight = 0;
		int slotCol = 0;
		int slotRow = 0;
				
		// PLAYER INVENTORY
		if (entity == gp.player) {
			frameX = gp.tileSize * 9;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize * 6;
			frameHeight = gp.tileSize * 5;		
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		}
		// NPC INVENTORY
		else {
			frameX = gp.tileSize * 2;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize * 6;
			frameHeight = gp.tileSize * 5;		
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
				
		// SLOTS
		final int slotXStart = frameX + 20;
		final int slotYStart = frameY + 20;
		int slotX = slotXStart; 
		int slotY = slotYStart;
		int slotSize = gp.tileSize + 3;
		
		// DRAW ITEMS
		for (int i = 0; i < entity.inventory.size(); i++) {
			
			// EQUIPPED CURSOR
			if (entity.inventory.get(i) == entity.currentItem) {				
				g2.setColor(new Color(240,190,90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}			
			
			g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
			slotX += slotSize;
			
			if (i == 4 || i == 9 || i == 14) {
				slotX = slotXStart;
				slotY += gp.tileSize;
			}
		}
		
		if (cursor) {
			
			int cursorX = slotXStart + (slotSize * slotCol);
			int cursorY = slotYStart + (slotSize * slotRow);
			int cursorWidth = gp.tileSize;
			int cursorHeight = gp.tileSize;		
			
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
			
			int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
			if (itemIndex < entity.inventory.size()) {
				
				drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
				
				for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
					g2.drawString(line, textX, textY);	
					textY += 32;
				}
			}
		}	
		
	}
	public int getItemIndexOnSlot(int slotCol, int slotRow) {		
		int itemIndex = slotCol + (slotRow * 5);
		return itemIndex;
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
	public void drawItemDialogue() {
		
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
		g2.drawImage(newItem.down1, gp.player.screenX, gp.player.screenY - gp.tileSize, null);
		g2.drawImage(gp.player.itemGet, gp.player.screenX, gp.player.screenY, null);
	}
	
	public void drawTradeScreen() {
					
		switch (subState) {
			case 0: trade_select(); break;
			case 1: trade_buy(); break;
			case 2: trade_sell(); break;
		}
		
		gp.keyH.spacePressed = false;
	}
	public void trade_select() {
				
		drawDialogueScreen();
		
		// DRAW OPTIONS WINDOW
		int x = gp.tileSize * 11;
		int y = gp.tileSize * 4;
		int width = gp.tileSize * 3;
		int height =(gp.tileSize * 4);
		drawSubWindow(x, y, width, height);
				
		// DRAW TRADE MENU OPTIONS
		x += gp.tileSize;		
		y += gp.tileSize * 1.2;
		g2.drawString("Buy", x, y);
		if (commandNum == 0) {
			g2.drawString(">", x-24, y);
			if (gp.keyH.spacePressed) 
				subState = 1;
		}
		
		y += gp.tileSize;
		g2.drawString("Sell", x, y);
		if (commandNum == 1) {
			g2.drawString(">", x-24, y);
			if (gp.keyH.spacePressed)
				subState = 2;
		}
		
		y += gp.tileSize;
		g2.drawString("Leave", x, y);
		if (commandNum == 2) {
			g2.drawString(">", x-24, y);
			if (gp.keyH.spacePressed) {
				gp.gameState = gp.dialogueState;
				gp.ui.commandNum = 0;
				gp.ui.subState = 0;
				gp.ui.currentDialogue = "Scram, kid!";
			}	
		}
	}
	public void trade_buy() {
		
		drawInventory(gp.player, false);
		drawInventory(npc, true);
		
		// DRAW PRICE WINDOW
		int x = gp.tileSize * 2;
		int y = gp.tileSize * 9;
		int width = gp.tileSize * 6;
		int height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Back", x+24, y+60);
		
		// DRAW PLAYER RUPEE WINDOW
		x = gp.tileSize * 9;
		y = gp.tileSize * 9;
		width = gp.tileSize * 6;
		height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("Rupees: " + gp.player.rupees, x+24, y+60);
		
		// DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
		if (itemIndex < npc.inventory.size()) {

			x = (int) (gp.tileSize * 5.7);
			y = (int) (gp.tileSize * 5.5);
			width = (int) (gp.tileSize * 2.3);
			height = gp.tileSize;
			drawSubWindow(x, y, width, height);
			g2.drawImage(rupee, x+5, y+8, 32, 32, null);
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 37F));
			int price = npc.inventory.get(itemIndex).price;
			String text = Integer.toString(price);
			x = getXforRightAlignText(text, gp.tileSize * 7);
			g2.drawString(text, x+32, y+35);	
			
			
			// BUY AN ITEM
			if (gp.keyH.spacePressed) {
				
				if (npc.inventory.get(itemIndex).price > gp.player.rupees) {
					subState = 0;
					gp.gameState = gp.dialogueState;
					currentDialogue = "Hey! You don't have enough rupees!";
					drawDialogueScreen();
				}
				else if (gp.player.inventory.size() == gp.player.maxInventorySize) {
					subState = 0;
					gp.gameState = gp.dialogueState;
					currentDialogue = "Looks like you don't have enough room, kid!";
					drawDialogueScreen();
				}
				else {
					gp.player.rupees -= npc.inventory.get(itemIndex).price;
					
					if (gp.player.rupees < 0)
						gp.player.rupees = 0;
					
					gp.player.inventory.add(npc.inventory.get(itemIndex));
					
					if (npc.inventory.get(itemIndex).type == gp.player.type_item) 
						gp.player.items.add(npc.inventory.get(itemIndex));
					npc.inventory.remove(itemIndex);					
				}
			}
		}		
	}
	public void trade_sell() {
		
		// DRAW PLAYER INVENTORY
		drawInventory(gp.player, true);
		drawInventory(npc, false);
				
		// DRAW PRICE WINDOW
		int x = gp.tileSize * 2;
		int y = gp.tileSize * 9;
		int width = gp.tileSize * 6;
		int height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Back", x+24, y+60);
		
		// DRAW PLAYER RUPEE WINDOW
		x = gp.tileSize * 9;
		y = gp.tileSize * 9;
		width = gp.tileSize * 6;
		height = gp.tileSize * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("Rupees: " + gp.player.rupees, x+24, y+60);
		
		// DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
		if (itemIndex < gp.player.inventory.size()) {

			x = (int) (gp.tileSize * 12.7);
			y = (int) (gp.tileSize * 5.5);
			width = (int) (gp.tileSize * 2.3);
			height = gp.tileSize;
			drawSubWindow(x, y, width, height);
			g2.drawImage(rupee, x+5, y+8, 32, 32, null);
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 37F));
			int price = gp.player.inventory.get(itemIndex).price;
			String text = Integer.toString(price);
			x = getXforRightAlignText(text, gp.tileSize * 14);
			g2.drawString(text, x+32, y+35);	
			
			// SELL AN ITEM
			if (gp.keyH.spacePressed) {
				
				// MAIN ITEM NOT SELLABLE
				if (gp.player.inventory.get(itemIndex).type == gp.player.type_item) {
					commandNum = 0;
					subState = 0;
					gp.gameState = gp.dialogueState;
					currentDialogue = "I think you should hold onto that!";
				}
				else {
					gp.player.rupees += price / 2;
					npc.inventory.add(gp.player.inventory.get(itemIndex));
					gp.player.inventory.remove(itemIndex);
				}
			}
		}		
	}
		
	public void drawTransition() {
		
		// DARKEN SCREEN
		counter++;
		g2.setColor(new Color(0,0,0, counter * 5)); 
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// STOP DARKENING SCREEN
		if (counter == 50) {
			counter = 0;			
			gp.gameState = gp.playState;
			
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
			gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
			
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;
		}		
	}
	
	public void drawGameOverScreen() {
				
		if (deathSprite < 18)
			playerDeathAnimation();		
		else {
						
			if (!gp.music.clip.isActive())
				gp.playMusic(0);
			
			g2.setColor(new Color(0,0,0,200)); // REDUCED OPACITY BLACK
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			String text;
			int x;
			int y;		
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
			
			text = "Game Over";
			
			// TITLE SHADOW
			g2.setColor(Color.BLACK);
			x = getXforCenteredText(text);
			y = gp.tileSize * 4;
			g2.drawString(text, x, y);
			
			// TITLE
			g2.setColor(new Color(190,0,25));
			g2.drawString(text, x - 4, y - 4);
			
			// CONTINUE
			g2.setColor(Color.WHITE);
			g2.setFont(g2.getFont().deriveFont(40F));
			text = "Continue";
			x = getXforCenteredText(text);
			y += gp.tileSize * 3;
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x - 40, y);
			}
			
			// QUIT
			text = "Save and Quit";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				g2.drawString(">", x - 40, y);
			}
		}
	}
	public void playerDeathAnimation() {
						
		// CHANGE IMAGE EVERY 6 FRAMES
		deathCounter++;
		if (deathCounter > 6) {
			
			// SPIN PLAYER SPRITE 3 TIMES
			if (deathSprite == 0 || deathSprite == 3 || deathSprite == 6) 
				gp.player.image = gp.player.die1;		
			else if (deathSprite == 1 || deathSprite == 4 || deathSprite == 7) 
				gp.player.image = gp.player.die2;		
			else if (deathSprite == 2 || deathSprite == 5 || deathSprite == 8) 
				gp.player.image = gp.player.die3;		
			else if (deathSprite == 9) 
				gp.player.image = gp.player.die4;
			
			// RESET FRAME COUNTER
			deathCounter = 0;
			deathSprite++;
		}
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