package application;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import data.Progress;
import entity.Entity;
import entity.Entity.Action;
import entity.equipment.EQP_Flippers;
import entity.item.ITM_Bomb;
import entity.item.ITM_Bow;

public class UI {
	
	private GamePanel gp;
	private Graphics2D g2;	
	public Font PK_DS;
	
	// TITLE SCREEN STATE
	public int titleScreenState = 0;
	
	// MENU OPTION SELECTION
	public int commandNum = 0;
	
	// HUD
	private BufferedImage dialogue_next, dialogue_finish, zTargetLock, zTargetLocked;
	private BufferedImage flippers, heart_4, heart_3, heart_2, heart_1, heart_0, rupee, key, boss_key;
	private String rupee_count = "0";
	public int rupeeCount = 0;
	private int rCounter = 0;
	private int zTargetCounter = 0;
	private int zTargetDirection = 0;
	
	// AREA TITLE
	public String mapName = "";
	public int mapNameAlpha = 0;
	public int mapNameCounter = 0;
	
	// OPTIONS MEU
	public int subState = 0;
		
	// ITEM MENU
	public int playerSlotCol = 0;
	public int playerSlotRow = 0;	
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	
	public int inventoryScreen = 0;
	
	// DIALOGUE HANDLER	
	public boolean messageOn = false;
	public String currentDialogue = "";
	public int dialogueIndex = 0;
	public int textSpeed = 0;
	private int dialogueCounter = 0;	
	public int charIndex = 0;
	public String combinedText = "";
	private boolean canSkip = false;
	
	// PLAYER RESPONSE
	public boolean response = false;
	public int responseSet = 0;
	public int offset = 0;
	
	// HINT
	public String hint = "";
	public boolean showHint = false;
	
	// TRANSITION
	private int counter = 0;
	
	// NPC ITEM TRANSACTION
	public Entity npc;
	public Entity newItem;
	public int newItemIndex;
		
	// GAME OVER
	public int deathSprite = 0;
	public int deathCounter = 0;
	
	public String transitionDirection;
	
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
		
		heart_0 = setup("/ui/heart_0", gp.tileSize / 2, gp.tileSize / 2);
		heart_1 = setup("/ui/heart_1", gp.tileSize / 2, gp.tileSize / 2);
		heart_2 = setup("/ui/heart_2", gp.tileSize / 2, gp.tileSize / 2);
		heart_3 = setup("/ui/heart_3", gp.tileSize / 2, gp.tileSize / 2);
		heart_4 = setup("/ui/heart_4", gp.tileSize / 2, gp.tileSize / 2);
		
		rupee = setup("/ui/rupee");
		key = setup("/ui/key");
		boss_key = setup("/ui/key_boss");
		
		flippers = new EQP_Flippers(gp).down1;
		
		dialogue_next = setup("/ui/dialogue_next"); 
		dialogue_finish = setup("/ui/dialogue_finish"); 
		zTargetLock = setup("/ui/lockon_target", 48 + 20, 48 + 20);
		zTargetLocked = setup("/ui/lockon_locked", 48 + 20, 48 + 20);
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
		else if (gp.gameState == gp.playState || gp.gameState == gp.objectState ||
				gp.gameState == gp.fallingState || gp.gameState == gp.drowningState) {
			drawHUD();
			drawEnemyHPBar(gp.enemy);
		}		
		// PAUSE STATE
		else if (gp.gameState == gp.pauseState) {
			drawHUD();
			drawEnemyHPBar(gp.enemy);
			drawPauseScreen();
		}		
		// INVENTORY STATE
		else if (gp.gameState == gp.inventoryState) {
			drawHUD();			
			drawInventory();					
		}
		// DIALOGUE STATE
		else if (gp.gameState == gp.dialogueState) {
			drawHUD();
			drawDialogueScreen(true);
		}
		// SCENE STATE
		else if (gp.gameState == gp.cutsceneState) {
			drawScene();
		}
		// TRADE STATE
		else if (gp.gameState == gp.tradeState) {
			drawHUD();
			drawTradeScreen();
		}
		// ITEM GET STATE
		else if (gp.gameState == gp.itemGetState) {
			drawHUD();
			drawItemGetScreen();
		}
		// TRANSITION STATE
		else if (gp.gameState == gp.transitionState) {
			drawTransition();
		}
		// SLEEP STATE
		else if (gp.gameState == gp.sleepState) {
			drawSleepScreen();
		}
		// GAME OVER STATE
		else if (gp.gameState == gp.gameOverState && gp.saveLoad.ready) {
			drawGameOverScreen();
		}
	}
	
	// TITLE
	private void drawTitleScreen() {
		
		// BACKGROUND
		g2.setColor(new Color(65, 90, 255));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// BACKGROUND IMAGE
		BufferedImage image = null;		
		try { image = ImageIO.read(getClass().getResourceAsStream("/misc/MENU_TITLE.png")); }
		catch (IOException e) { }
		g2.drawImage(image, 0, 0, gp.screenWidth, gp.screenHeight, null);
		
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
		
		g2.setColor(Color.WHITE);
		g2.drawString(text, x+10, y+5);
		
		g2.setColor(Color.BLACK);
		g2.drawString(text, x+10, y);
		g2.setColor(Color.WHITE);
		
		// MAIN TITLE SCREEN
		if (titleScreenState == 0) {				
			menu_main();			
		}
		// NEW GAME SELECTED
		else if (titleScreenState == 1) {
			menu_newGame();
		}
		// LOAD GAME SELECTED
		else if (titleScreenState == 2) {			
			menu_loadGame();
		}
	}
	private void menu_main() {
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 52F));
		
		// MENU OPTIONS		
		String text = "NEW GAME";
		int x = getXforCenteredText(text);
		int y = (gp.tileSize * 9) - 20;
		g2.drawString(text, x, y);
		if (commandNum == 0) g2.drawString(">", x - gp.tileSize / 2, y);
		
		text = "LOAD GAME";
		x = getXforCenteredText(text);
		y += gp.tileSize + 15;
		g2.drawString(text, x, y);
		if (commandNum == 1) g2.drawString(">", x - gp.tileSize / 2, y);			
		
		text = "QUIT";
		x = getXforCenteredText(text);
		y += gp.tileSize + 15;
		g2.drawString(text, x, y);
		if (commandNum == 2) g2.drawString(">", x - gp.tileSize / 2, y);
	}
	private void menu_newGame() {
	
		// TEXT COLOR
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(42F));
		
		int x = 66;
		int y = gp.tileSize * 3;			
		int width = gp.screenWidth - (gp.tileSize * 4);
		int height = gp.screenHeight - (gp.tileSize * 4);			
		drawSubWindow(x + 40, y, width, height);
		
		// NAME SELECT TITLE
		String text = "YOUR NAME, PLEASE";
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
		
		if (gp.keyH.capital) keyboard = "QWERTYUIOPASDFGHJKLZXCVBNM";	
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
	private void menu_loadGame() {
		
		// TEXT COLOR
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(35F));
		
		String text = "";
		int x = 66 + gp.tileSize;
		int y = gp.tileSize * 3;			
		int width = gp.tileSize * 10;
		int height = gp.tileSize * 2;	
						
		if (commandNum == 0) drawSubWindow(x+40, y, width, height, Color.GREEN);		
		else drawSubWindow(x+40, y, width, height);	
		
		y += gp.tileSize * 2;	
		if (commandNum == 1) drawSubWindow(x+40, y, width, height, Color.GREEN);				
		else drawSubWindow(x+40, y, width, height);	
		
		y += gp.tileSize * 2;	
		if (commandNum == 2) drawSubWindow(x+40, y, width, height, Color.GREEN);			
		else drawSubWindow(x+40, y, width, height);		
		
		if (gp.saveLoad.loadFileData(0) == null) text = "1)";			
		else text = "1) " + gp.saveLoad.loadFileData(0);
		x = gp.tileSize * 4;
		y = (gp.tileSize * 4) + 15;	
		g2.drawString(text, x, y);		
		
		if (gp.saveLoad.loadFileData(1) == null) text = "2)";			
		else text = "2) " + gp.saveLoad.loadFileData(1);
		y += gp.tileSize * 2;	
		g2.drawString(text, x, y);	
		
		if (gp.saveLoad.loadFileData(2) == null) text = "3)";			
		else text = "3) " + gp.saveLoad.loadFileData(2);
		y += gp.tileSize * 2;	
		g2.drawString(text, x, y);				
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 52F));
		text = "GO BACK";
		x = getXforCenteredText(text);
		y += (gp.tileSize * 2) + 25;
		g2.drawString(text, x, y);
		if (commandNum == 3) 
			g2.drawString(">", x - gp.tileSize / 2, y);
	}
	
	// HUD
	private void drawHUD() {
		
		int x = gp.tileSize / 2;
		int y = gp.tileSize / 2;
		int i = 0;
		
		// DRAW MAX LIFE		
		while (i < gp.player.maxLife / 4) {
			g2.drawImage(heart_0, x, y, null);
			i++;
			x += gp.tileSize / 1.6;
		}
		
		x = gp.tileSize / 2;
		y = gp.tileSize / 2;
		
		// PLAYER LIFE ALL FULL HEARTS
		if (gp.player.life % 4 == 0) {			
			for (i = 0; i < gp.player.life; i += 4) {
				g2.drawImage(heart_4, x, y, null);	
				x += gp.tileSize / 1.6;
			}			
		}
		// QUARTER HEARTS
		else {			
			BufferedImage heart = heart_4;
			i = 0; int c = 0; 
			while (i < gp.player.life) {
				
				// QUARTERS OF HEARTS REMAINING				
				if (i + 4 <= gp.player.life) { heart = heart_4; c = 4; }
				else if (i + 3 == gp.player.life) { heart = heart_3; c = 3; }
				else if (i + 2 == gp.player.life) { heart = heart_2; c = 2; }
				else if (i + 1 == gp.player.life) { heart = heart_1; c = 1; }				
				
				g2.drawImage(heart, x, y, null);
				x += gp.tileSize / 1.6;
				i += c;				
			}
		}
		
		if (gp.gameState == gp.playState || gp.gameState == gp.dialogueState || 
				gp.gameState == gp.objectState || gp.gameState == gp.fallingState) {
			
			if (mapNameCounter > 0) {
				drawMapName();
				mapNameCounter--;
			}
			
			drawItemSlot();			
			if (gp.currentArea == gp.dungeon) drawKeys();
			drawRupees();								
			
			drawZTarget();
			drawChargeBar();
		}		
		else if (gp.gameState == gp.inventoryState) {
			drawRupees();
		}
						
		// DRAW HINT 
		if (showHint && hint.length() > 0) {
			g2.setColor(Color.WHITE);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));			
			x = getXforCenteredText(hint);
			y = gp.tileSize * 11;						
			g2.drawString(hint, x, y);
		}
						
		// DEBUG HUD
		if (gp.keyH.debug) {				
			drawDebug();
		}
	}
	private void drawMapName() {		
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 75F));
		
		int x = getXforCenteredText(mapName);
		int y = gp.tileSize * 5;
		
		g2.setColor(new Color(0,0,0,mapNameAlpha));
		g2.drawString(mapName, x, y+5);
		
		g2.setColor(new Color(255,255,255,mapNameAlpha));
		g2.drawString(mapName, x, y);
		
	    if (mapNameCounter >= 60) {
	    	mapNameAlpha += 5; 
			if (mapNameAlpha > 255) mapNameAlpha = 255;	
	    } 
	    else {
	    	mapNameAlpha -= 5; 
			if (0 > mapNameAlpha) mapNameAlpha = 0;	
	    }
	}
	private void drawItemSlot() {
		
		// ITEM SLOT DISABLED
		if (gp.player.disabled_actions.contains(gp.player.action) || gp.gameState != gp.playState) {
			changeAlpha(g2, 0.6f);
		}
		
		int x = gp.tileSize * 14;
		int y = gp.tileSize / 2;
		
		// DRAW ITEM CIRCLE
		g2.setColor(new Color(240,190,90,240));
		g2.fillOval(x - 15, y - 15, gp.tileSize + 30, gp.tileSize + 30);	
		
		g2.setColor(new Color(217,217,217,240));
		g2.setStroke(new BasicStroke(3));
		g2.drawOval(x - 15, y - 15, gp.tileSize + 30, gp.tileSize + 30);	
		
		// DRAW ITEM BUTTON
		g2.setColor(Color.BLACK);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 27F));
		g2.drawString(KeyEvent.getKeyText(gp.btn_X), x-2, y+13);
		
		if (gp.player.currentItem != null) {	
						
			g2.drawImage(gp.player.currentItem.down1, x, y, gp.tileSize, gp.tileSize, null);
			
			// DRAW ARROW COUNT
			if (gp.player.currentItem.name.equals(ITM_Bow.itmName)) {					
				drawItemCount(1, x + 35, y, Color.BLACK, 27F);					
			}
			// DRAW BOMB COUNT
			else if (gp.player.currentItem.name.equals(ITM_Bomb.itmName)) {					
				drawItemCount(2, x + 35, y, Color.BLACK, 27F);
			}
		}	
		
		gp.player.changeAlpha(g2, 1f);
	}
	private void drawKeys() {
		
		int x;
		int y;
		
		// DRAW BOSS KEY IMAGE
		if (gp.player.boss_key > 0) {				
			x = gp.tileSize * 14 - 20;
			y = gp.tileSize * 8 + 30;					
			g2.drawImage(boss_key, x, y, gp.tileSize - 5, gp.tileSize - 5, null);	
		}
		
		// DRAW DUNGEON KEY IMAGE
		x = gp.tileSize * 14 - 20;
		y = gp.tileSize * 9 + 25;					
		g2.drawImage(key, x, y, gp.tileSize - 5, gp.tileSize - 5, null);	
		
		// DRAW DUNGEON KEY COUNT
		x += gp.tileSize - 8;
		y += gp.tileSize - 12;		
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 45F));
		g2.drawString(Integer.toString(gp.player.keys), x, y);				
	}
	private void drawRupees() {
		
		// DRAW RUPEE IMAGE
		int x = gp.tileSize * 14 - 20;
		int y = gp.tileSize * 10 + 20;		
		g2.drawImage(rupee, x, y, gp.tileSize - 5, gp.tileSize - 5, null);	
						
		// DRAW RUPEE COUNT
		x += gp.tileSize - 8;
		y += gp.tileSize - 12;	
		
		if (rupeeCount >= gp.player.walletSize)
			rupeeCount = gp.player.walletSize;
		
		if (gp.player.rupees < rupeeCount) {
			if (rCounter == 2) { 
				playWalletSE(); 
				gp.player.rupees++; 
				rCounter = 0; 
			}
			else rCounter++;
		}
		else if (gp.player.rupees > rupeeCount) {
			if (rCounter == 2) { 
				playWalletSE(); 
				gp.player.rupees--; 
				rCounter = 0; 
			}
			else rCounter++;			
		}
								
		if (gp.player.walletSize == 99) rupee_count = String.format("%02d", gp.player.rupees);
		else if (gp.player.walletSize == 999) rupee_count = String.format("%03d", gp.player.rupees);
		else if (gp.player.walletSize == 9999) rupee_count = String.format("%04d", gp.player.rupees);
		
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 45F));
		g2.drawString(rupee_count, x, y);			
	}
	private void drawZTarget() {
		
		if (gp.player.lockedTarget == null) {
			
			Entity target = null;			
			int currentDistance = 6;	
			
			for (Entity e : gp.enemy[gp.currentMap]) {
				
				if (e != null && e != gp.player.lockedTarget && e.canTarget && !e.dying) {

					// ENEMY WITHIN 6 TILES
					int enemyDistance = e.getTileDistance(gp.player);
					if (enemyDistance < currentDistance) {
						currentDistance = enemyDistance;
						target = e;							
					}
				}
			}
			
			// ANIMATE ICON UP AND DOWN
			if (target != null) {
								
				if (zTargetCounter < 20 && zTargetDirection == 0) {			
					zTargetCounter++;
				}
				else if (zTargetCounter < 20 && zTargetDirection == 1) {	
					zTargetCounter--;
				}
				if (zTargetCounter == 20) {
					zTargetCounter--;
					zTargetDirection = 1;
				}
				else if (zTargetCounter == 0) {
					zTargetCounter++;
					zTargetDirection = 0;
				}
				
				target.offCenter();
				
				int x = target.tempScreenX - 10;
				int y = target.tempScreenY - 30 + zTargetCounter;
				
				g2.drawImage(zTargetLock, x, y, null);	
			}	
		}
		else {
			// LOCKON IMAGE			
			gp.player.lockedTarget.offCenter();
			g2.drawImage(zTargetLocked, 
					gp.player.lockedTarget.tempScreenX - 10, 
					gp.player.lockedTarget.tempScreenY - 10, 
					null);
			
		}
	}
	private void drawChargeBar() {
		
		if (gp.player.charge > 0) {
			
			int x = gp.player.getScreenX() - 7;
			int y = gp.player.getScreenY() - 20;			
			int width = 62;
			int height = 10;		
			int charge = gp.player.charge;						
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8F));
			
			g2.fillRect(x, y, width, height);	
			
			if (gp.player.charge < 120) g2.setColor(Color.WHITE);
			else g2.setColor(new Color(0,240,0));
			
			g2.setStroke(new BasicStroke(2));
			g2.drawRect(x, y, width, height);	
			
			if (40 > charge) g2.setColor(new Color(0,105,0)); 	
			if (80 > charge && charge >= 40) g2.setColor(new Color(0,155,0)); 	
			else if (120 > charge && charge >= 80) g2.setColor(new Color(0,205,0)); 	
			else if (charge >= 120) g2.setColor(new Color(0,240,0));
			
			g2.fillRect(x + 1, y + 1, (charge / 2), height - 2);
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
		}
	}
	private void drawEnemyHPBar(Entity[][] enemies) {
		
		for (int i = 0; i < enemies[1].length; i++) {
		
			Entity enemy = enemies[gp.currentMap][i];
			
			if (enemy != null && enemy.inFrame() && !enemy.sleep) {
				
				// BOSS HEALTH BAR
				if (enemy.type == enemy.type_boss) {
					
					// LENGTH OF HALF HEART
					double oneScale = (double)gp.tileSize * 8 / enemy.maxLife; 
					
					// LENGTH OF ENEMY HEALTH
					double hpBarValue = oneScale * enemy.life; 
					
					int x = (gp.screenWidth / 2) - (gp.tileSize * 4);
					int y = (int) (gp.tileSize * 1.5);
					
					// DARK GRAY OUTLINE
					g2.setColor(new Color(35,35,35)); 
					g2.fillRect(x - 1, y - 1, (gp.tileSize * 8) + 2, 12);
					
					// RED BAR
					g2.setColor(new Color(255,0,30)); 
					g2.fillRect(x, y, (int)hpBarValue, 10);
													
					// DRAW BOSS NAME
					g2.setFont(g2.getFont().deriveFont(Font.BOLD, 38f));
					g2.setColor(Color.WHITE);
					x = getXforCenteredText(enemy.name);
					g2.drawString(enemy.name, x, y - 8);
				}
				
				// REGULAR ENEMY HEALTH BAR (UNLOCKED WHEN GAME IS COMPLETED)
				else if (Progress.gameCompleted) {
					
					// LENGTH OF HALF HEART
					double oneScale = (double)gp.tileSize / enemy.maxLife; 
					
					// LENGTH OF ENEMY HEALTH
					double hpBarValue = oneScale * enemy.life; 
					
					g2.setColor(new Color(35,35,35)); // DARK GRAY OUTLINE
					g2.fillRect(enemy.getScreenX() - 1, enemy.getScreenY() - 16, gp.tileSize + 2, 10);
					
					g2.setColor(new Color(255,0,30)); // RED BAR
					g2.fillRect(enemy.getScreenX(), enemy.getScreenY() - 15, (int)hpBarValue, 8);
					
					// REMOVE BAR AFTER 10 SECONDS
					enemy.hpBarCounter++;
					if (enemy.hpBarCounter > 600) {
						enemy.hpBarCounter = 0;
						enemy.hpBarOn = false;						
					}
				}				
			}			
		}				
	}
	private void drawDebug() {
		
		int x = 10; 
		int y = gp.tileSize * 6; 
		int lineHeight = 20;
		
		String timeOfDay = "";
		switch (gp.eManager.lighting.dayState) {
			case 0: timeOfDay = "DAY"; break;
			case 1: timeOfDay = "DUSK"; break;
			case 2: timeOfDay = "NIGHT"; break;
			case 3: timeOfDay = "DAWN"; break;
		}
		
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.BOLD, 50));
		g2.drawString(timeOfDay, x, y - gp.tileSize);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20f));						
		
		g2.drawString("WorldX: " + gp.player.worldX, x , y); 
		y += lineHeight;
		g2.drawString("WorldY: " + gp.player.worldY, x , y); 
		y += lineHeight;
		g2.drawString("Column: " + (gp.player.worldX + gp.player.hitbox.x) / gp.tileSize, x , y);
		y += lineHeight;
		g2.drawString("Row: " + (gp.player.worldY + gp.player.hitbox.y) / gp.tileSize, x , y);
		y += lineHeight;
		g2.drawString("Time Counter: " + gp.eManager.lighting.dayCounter, x, y);
		y += lineHeight;
		g2.drawString("Blood moon cycle: " + gp.eManager.lighting.bloodMoonCounter, x, y);
		
		g2.setColor(Color.RED);
		g2.drawRect(gp.player.screenX + gp.player.hitbox.x, gp.player.screenY + gp.player.hitbox.y, 
				gp.player.hitbox.width, gp.player.hitbox.height);
			
		g2.setColor(new Color(255,0,0,100));
		for (int i = 0; i < gp.pFinder.pathList.size(); i++) {
			
			int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
			int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);			
		}
		
		g2.setColor(Color.WHITE);
		g2.setFont(PK_DS);
	}	
	
	// PAUSE
	private void drawPauseScreen() {
						
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));

		// SUB WINDOW
		int frameX = gp.tileSize * 2;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 12;
		int frameHeight = gp.tileSize * 10;
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch(subState) {
			case 0: options_top(frameX, frameY); break;
			case 1: options_fullscreenNotif(frameX, frameY); break;
			case 2: options_controls(frameX, frameY); break;
			case 3: options_saveGameSlot(frameX, frameY); break;
			case 4: options_saveGameConfirm(frameX, frameY); break;
			case 5: options_loadGameSlot(frameX, frameY); break;
			case 6: options_quitGameConfirm(frameX, frameY); break;
		}
	}
	private void options_top(int frameX, int frameY) {
		
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
			if (gp.keyH.aPressed) {
				if (gp.fullScreenOn) {
					gp.fullScreenOn = false;
				}
				else {
					gp.fullScreenOn = true;
				}
				
				subState = 1;
				gp.keyH.aPressed = false;
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
		
		// TEXT SPEED
		textY += gp.tileSize;
		g2.drawString("Text Speed", textX, textY);
		if (commandNum == 3) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.aPressed) {
				textSpeed++;
				if (textSpeed > 2) textSpeed = 0;
				gp.keyH.aPressed = false;
			}
		}
		
		// CONTROLS
		textY += gp.tileSize;
		g2.drawString("Controls", textX, textY);
		if (commandNum == 4) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.aPressed) {
				subState = 2;
				commandNum = 0;
				gp.keyH.aPressed = false;
			}
		}
				
		// SAVE
		textY += gp.tileSize;
		g2.drawString("Save Progress", textX, textY);
		if (commandNum == 5) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.aPressed) {
				if (Progress.canSave && gp.player.action == Action.IDLE) {
					subState = 3;
					commandNum = 0;					
				}
				else {
					gp.keyH.playErrorSE();
				}
				gp.keyH.aPressed = false;						
			}
		}
		
		// LOAD
		textY += gp.tileSize;
		g2.drawString("Load Previous Save", textX, textY);
		if (commandNum == 6) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.aPressed) {
				if (Progress.canSave && gp.player.action == Action.IDLE) {
					subState = 5;
					commandNum = 0;					
				}
				else {
					gp.keyH.playErrorSE();
				}
				gp.keyH.aPressed = false;						
			}
		}

		// QUIT GAME
		textY += gp.tileSize;
		g2.drawString("Quit to Title Screen", textX, textY);
		if (commandNum == 7) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.aPressed) {
				subState = 6;
				commandNum = 0;
				gp.keyH.aPressed = false;
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
		
		// TEXT SPEED OPTION
		textY += gp.tileSize * 1.5;
		if (textSpeed == 0) g2.drawString("FAST", textX, textY);
		else if (textSpeed == 1) g2.drawString("MEDIUM", textX, textY);
		else if (textSpeed == 2) g2.drawString("SLOW", textX, textY);
		
		gp.config.saveConfig();
	}
	private void options_fullscreenNotif(int frameX, int frameY) {
		
		currentDialogue = "The change will take effect\nafter restarting the game.";
		int textX;
		int textY = frameX + gp.tileSize * 2;				
				
		for (String line : currentDialogue.split("\n")) {
			textX = getXforCenteredText(line);
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
		String text = "BACK";
		textX = getXforCenteredText(text);
		textY += gp.tileSize * 2;
		g2.drawString(text, textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			
			if (gp.keyH.aPressed) {
				commandNum = 0;
				subState = 0;
				gp.keyH.aPressed = false;
			}
		}
	}
	private void options_controls(int frameX, int frameY) {
		
		int textX;
		int textY;
		
		// TITLE
		String text = "CONTROLS";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		// LABELS
		textX = frameX + gp.tileSize;
		textY += gp.tileSize;		
		g2.drawString("ACTION", textX, textY); textY += 35;
		g2.drawString("ATTACK", textX, textY); textY += 35;
		g2.drawString("ITEM", textX, textY); textY += 35;
		g2.drawString("TARGET", textX, textY); textY += 35;
		g2.drawString("SHIELD", textX, textY); textY += 35;
		g2.drawString("CYCLE ITEMS", textX, textY); textY += 35;
		g2.drawString("INVENTORY", textX, textY); textY += 35;	
		g2.drawString("MAP", textX, textY); textY += 35;
		g2.drawString("MINI-MAP", textX, textY); textY += 35;		
		
		// VALUES
		textX = frameX + gp.tileSize * 6;
		textY = frameY + gp.tileSize * 2;
		g2.drawString(KeyEvent.getKeyText(gp.btn_A), textX, textY); textY += 35;
		g2.drawString(KeyEvent.getKeyText(gp.btn_B), textX, textY); textY += 35;
		g2.drawString(KeyEvent.getKeyText(gp.btn_X), textX, textY); textY += 35;
		g2.drawString(KeyEvent.getKeyText(gp.btn_L), textX, textY); textY += 35;
		g2.drawString(KeyEvent.getKeyText(gp.btn_R), textX, textY); textY += 35;
		g2.drawString(KeyEvent.getKeyText(gp.btn_Z), textX, textY); textY += 35;
		g2.drawString(KeyEvent.getKeyText(gp.btn_SELECT), textX, textY); textY += 35;
		g2.drawString(KeyEvent.getKeyText(gp.btn_DUP), textX, textY); textY += 35;
		g2.drawString(KeyEvent.getKeyText(gp.btn_DDOWN), textX, textY); textY += 35;		
		
		// BACK		
		text = "BACK";
		textX = getXforCenteredText(text);
		textY += (gp.tileSize / 2);
		g2.drawString(text, textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			
			if (gp.keyH.aPressed) {
				commandNum = 4;
				subState = 0;
				gp.keyH.aPressed = false;
			}
		}
	}
	private void options_saveGameSlot(int frameX, int frameY) {
		
		// TITLE
		String text = "SAVE GAME SLOT";
		int textX = getXforCenteredText(text);
		int textY = frameX + gp.tileSize * 2;		
		g2.drawString(text, textX, textY);		
		
		textX = gp.tileSize * 3;
		for (int i = 0; i < 3; i++) {
			if (gp.saveLoad.loadFileData(i) == null) text = i + 1 + ")  [EMPTY]";			
			else text = i + 1 + ")  " + gp.saveLoad.loadFileData(i);
			
			textY += gp.tileSize;
			g2.drawString(text, textX, textY);
			if (commandNum == i) {
				g2.drawString(">", textX - 25, textY);
				
				if (gp.keyH.aPressed) {
					subState = 4;
					commandNum = 0;
					gp.saveLoad.save(i);
					gp.fileSlot = i;
					gp.keyH.aPressed = false;
				}
			}		
		}
		
		text = "BACK";
		textX = getXforCenteredText(text);
		textY += gp.tileSize * 2;
		g2.drawString(text, textX, textY);
		if (commandNum == 3) {
			g2.drawString(">", textX - 25, textY);
			
			if (gp.keyH.aPressed) {
				commandNum = 5;
				subState = 0;
				gp.keyH.aPressed = false;
			}
		}
	}
	private void options_loadGameSlot(int frameX, int frameY) {

		// TITLE
		String text = "LOAD GAME SLOT";
		int textX = getXforCenteredText(text);
		int textY = frameX + gp.tileSize * 2;		
		g2.drawString(text, textX, textY);		
		
		textX = gp.tileSize * 3;
		for (int i = 0; i < 3; i++) {
			
			textY += gp.tileSize;
			if (gp.saveLoad.loadFileData(i) == null) {
				text = i + 1 + ")  [EMPTY]";		
				g2.drawString(text, textX, textY);
				if (commandNum == i) {
					g2.drawString(">", textX - 25, textY);
					if (gp.keyH.aPressed) {
						gp.keyH.playErrorSE();
						gp.keyH.aPressed = false;
					}
				}		
			}
			else {
				text = i + 1 + ")  " + gp.saveLoad.loadFileData(i);
				g2.drawString(text, textX, textY);
				if (commandNum == i) {
					g2.drawString(">", textX - 25, textY);
					
					if (gp.keyH.aPressed) {
						gp.stopMusic();
						gp.resetGame();
						commandNum = 0;
						subState = 0;
						gp.fileSlot = i;
						gp.saveLoad.load(i);
						gp.tileM.loadMap();
						gp.gameState = gp.playState;
						gp.setupMusic(true);	
						gp.keyH.aPressed = false;
					}
				}		
			}			
		}
		
		text = "BACK";
		textX = getXforCenteredText(text);
		textY += gp.tileSize * 2;
		g2.drawString(text, textX, textY);
		if (commandNum == 3) {
			g2.drawString(">", textX - 25, textY);
			
			if (gp.keyH.aPressed) {
				commandNum = 6;
				subState = 0;
				gp.keyH.aPressed = false;
			}
		}
	}
	private void options_saveGameConfirm(int frameX, int frameY) {
		
		// TITLE
		String text = "GAME SAVED";
		int textX = getXforCenteredText(text);
		int textY = frameX + gp.tileSize * 2;		
		g2.drawString(text, textX, textY);
		
		text = "Continue playing?";
		textX = getXforCenteredText(text);
		textY += gp.tileSize;		
		g2.drawString(text, textX, textY);
		
		text = "YES";
		textX = getXforCenteredText(text);
		textY += gp.tileSize * 2;
		g2.drawString(text, textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			
			if (gp.keyH.aPressed) {
				subState = 0;		
				commandNum = 0;
				inventoryScreen = 0;
				titleScreenState = 0;
				gp.gameState = gp.playState;
				gp.keyH.aPressed = false;
			}
		}
		
		text = "NO";
		textX = getXforCenteredText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX - 25, textY);
			
			if (gp.keyH.aPressed) {
				gp.stopMusic();				
				subState = 0;		
				commandNum = 0;
				inventoryScreen = 0;
				titleScreenState = 0;
				gp.gameState = gp.titleState;				
				gp.resetGame();
				gp.setupMusic(true);
				gp.keyH.aPressed = false;
			}
		}
	}
	private void options_quitGameConfirm(int frameX, int frameY) {
		
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
			
			if (gp.keyH.aPressed) {
				gp.stopMusic();		
				subState = 0;		
				commandNum = 0;
				inventoryScreen = 0;
				titleScreenState = 0;
				gp.gameState = gp.titleState;		
				gp.resetGame();				
				gp.setupMusic(true);
				gp.keyH.aPressed = false;
			}
		}
		
		// NO
		text = "NO";
		textX = getXforCenteredText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX - 25, textY);
			
			if (gp.keyH.aPressed) {
				commandNum = 7;
				subState = 0;
				gp.keyH.aPressed = false;
			}
		}
	}
	
	// INVENTORY	
	private void drawInventory() {
		
		int x = gp.tileSize * 9;
		int y = 0;
		int width = gp.tileSize * 6;
		int height = gp.tileSize;
		drawSubWindow(x, y, width, height);
		
		x = (gp.tileSize * 7) + 12;
		width = (gp.tileSize * 2) - 15;
		drawSubWindow(x, y, width, height);
		
		x = (gp.tileSize * 8);
		y = 35;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 34F));	
		String text = null;		
		if (inventoryScreen == 0) {
			g2.drawString(KeyEvent.getKeyText(gp.btn_Z) + " >", x, y);
			text = "COLLECTABLES";
		}
		else {
			g2.drawString("< " + KeyEvent.getKeyText(gp.btn_Z), x - 20, y);
			text = "ITEMS";
		}
		
		x = getXforCenteredText(text);
		g2.drawString(text, x + gp.tileSize * 4, y);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 37F));
		
		inventory_equipment();
		
		if (inventoryScreen == 0) {
			inventory_collectables(gp.player, true);
		}
		else {
			inventory_items();			
		}	
	}
	private void inventory_equipment() {

		int frameX = (gp.tileSize * 7) + 15;
		int frameY = gp.tileSize + 5;
		int frameWidth = (gp.tileSize * 2) - 20;
		int frameHeight = (gp.tileSize * 5) - 10;
		
		int slotX = frameX + 16;
		int slotY = frameY + 10;
		int itemX = slotX + 35;
		
		int cursorX = frameX;
		int cursorY = slotY - 6;
		int cursorWidth = frameWidth;
		int cursorHeight = (frameHeight - 4) / 4;
		
		Entity sword = gp.player.currentWeapon;
		Entity shield = gp.player.currentShield;	
		Entity item = gp.player.currentItem;
		
		// DRAW WINDOW
		g2.setColor(Color.WHITE);
		g2.fillRoundRect(frameX, frameY, frameWidth, frameHeight, 15, 15); 
		
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(frameX, frameY, frameWidth, frameHeight, 15, 15);
		
		// DRAW DIVIDERS
		g2.setStroke(new BasicStroke(4));
		for (int i = 0; i < 4; i++) {			
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
			cursorY += cursorHeight;
		}				
		
		// DRAW SWORD
		if (sword != null) {
			g2.drawImage(sword.image, slotX, slotY, null);	
		}		
		
		// DRAW SHIELD
		if (shield != null) {
			slotY = frameY + (gp.tileSize + 15);	
			g2.drawImage(shield.image, slotX, slotY, null);
		}				
		
		// DRAW CURRENT ITEM
		if (item != null) {
			slotY = (frameY + 15) + ((gp.tileSize + 5) * 2);	
			g2.drawImage(item.image, slotX, slotY, null);
			
			// DRAW ARROW COUNT
			if (item.name.equals(ITM_Bow.itmName)) {	
				drawItemCount(1, itemX, slotY, Color.BLACK, 28F);
			}
			// DRAW BOMB COUNT
			else if (item.name.equals(ITM_Bomb.itmName)) {	
				drawItemCount(2, itemX, slotY, Color.BLACK, 28F);
			}		
		}
		
		// DRAW ZORA FLIPPERS
		if (gp.player.canSwim) {
			slotY = (frameY + 15) + ((gp.tileSize + 5) * 3);	
			g2.drawImage(flippers, slotX, slotY, null);
		}
		
	}
	private void inventory_collectables(Entity entity, boolean cursor) {
		
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
						
			g2.drawImage(entity.inventory.get(i).image, slotX, slotY, null);
			
			// STACKABLE ITEMS
			if (entity.inventory.get(i).amount > 1) {
				
				g2.setFont(g2.getFont().deriveFont(28F));				
				int amountX;
				int amountY;	
				
				String s = "" + entity.inventory.get(i).amount;
				amountX = getXforRightAlignText(s, slotX + gp.tileSize);
				amountY = slotY + gp.tileSize;
				
				// SHADOW
				g2.setColor(Color.BLACK);
				g2.drawString(s, amountX, amountY);
				
				// NUMBER
				g2.setColor(Color.WHITE);
				g2.drawString(s, amountX - 3, amountY - 3);
			}
			
			slotX += slotSize;			
			if (slotX == slotXStart + (slotSize * 5)) {
				slotX = slotXStart;
				slotY += gp.tileSize + 3;
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
			
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));;
			
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
	private void inventory_items() {

		int frameX = gp.tileSize * 9;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 6;
		int frameHeight = gp.tileSize * 5;		
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
				
		final int slotXStart = frameX + 20;
		final int slotYStart = frameY + 20;
		int slotX = slotXStart; 
		int slotY = slotYStart;
		int slotCol = playerSlotCol;
		int slotRow = playerSlotRow;
		int slotSize = gp.tileSize + 3;
		
		// DRAW ITEMS
		for (int i = 0; i < gp.player.inventory_item.size(); i++) {
			
			// EQUIPPED CURSOR
			if (gp.player.currentItem != null) {
				if (gp.player.inventory_item.get(i).name.equals(gp.player.currentItem.name)) {				
					g2.setColor(new Color(217,217,217));
					g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
				}	
			}
			
			g2.drawImage(gp.player.inventory_item.get(i).down1, slotX, slotY, null);
			
			// DRAW ARROW COUNT
			if (gp.player.inventory_item.get(i).name.equals(ITM_Bow.itmName)) {						
				drawItemCount(1, slotX + 25, slotY, Color.WHITE, 24F);					
			}
			// DRAW BOMB COUNT
			else if (gp.player.inventory_item.get(i).name.equals(ITM_Bomb.itmName)) {					
				drawItemCount(2, slotX + 25, slotY, Color.WHITE, 24F);
			}
			
			slotX += slotSize;			
			if (slotX == slotXStart + (slotSize * 5)) {
				slotX = slotXStart;
				slotY += gp.tileSize + 3;
			}
		}
		
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
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
		
		int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
		if (itemIndex < gp.player.inventory_item.size()) {
			
			drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
			
			for (String line : gp.player.inventory_item.get(itemIndex).description.split("\n")) {
				g2.drawString(line, textX, textY);	
				textY += 32;
			}
		}
	}
	
	// DIALOGUE	
	public void drawDialogueScreen(boolean skip) {
				
		if (npc.hasCutscene) {
			gp.csManager.scene = gp.csManager.npc;
			gp.gameState = gp.cutsceneState;
		}
		
		int x = gp.tileSize * 2;
		int y = (gp.screenWidth / 2 ) - gp.tileSize;
		int width = gp.screenWidth - (gp.tileSize * 4);
		int height = gp.tileSize * 4;		
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 37F));
		x += gp.tileSize;
		y += gp.tileSize;	
		
		// NPC HAS SOMETHING TO SAY
		if (npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {	
			if (dialogueCounter == textSpeed) {
				char characters[] = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();
							
				if (charIndex < characters.length) {					
					playDialogueSE();
					
					String s = String.valueOf(characters[charIndex]);				
					combinedText += s;
					currentDialogue = combinedText;					
					charIndex++;					
				}
				if (charIndex >= characters.length) {
					canSkip = true;
				}
	
				dialogueCounter = 0;
			}
			else
				dialogueCounter++;
			
		}
		// NPC HAS NO MORE DIALOGUE
		else {			
			npc.dialogueIndex = 0;
			
			// PLAYER HAS DIALOGUE RESPONSE
			if (response) {
				npc.dialogueSet++;
				gp.gameState = gp.tradeState;
			}
			
			else if (gp.gameState == gp.cutsceneState) {
				gp.csManager.phase++;
			}			
			else if (npc != null && npc.hasItemToGive) {
				gp.player.canObtainItem(npc.inventory.get(0));
			}	
			else {
				playDialogueFinishSE();				
				gp.gameState = gp.playState;
			}	
		}		
		
  		for (String line : currentDialogue.split("\n")) { 
			g2.drawString(line, x, y);	
			y += 40;
		} 
  		
  		// DRAW ICON BELOW DIALOGUE BOX
  		int nextIndex = npc.dialogueIndex + 1;
  		if (canSkip && npc.dialogues[npc.dialogueSet][nextIndex] != null) {  		
  			x = (gp.screenWidth / 2) - 24;
			y = gp.tileSize * 10;
	  		g2.drawImage(dialogue_next, x, y + 25, null);
  		}
  		else if (canSkip && npc.dialogues[npc.dialogueSet][nextIndex] == null) {  		
			x = (gp.screenWidth / 2) - 24;
			y = gp.tileSize * 10;
	  		g2.drawImage(dialogue_finish, x, y + 25, null);
  		}
  		
  		if (skip) skipDialogue();
	}
	private void skipDialogue() {
		if (gp.keyH.aPressed && canSkip) {
			canSkip = false;
			charIndex = 0;
			combinedText = "";
			
			if (gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState) {
				npc.dialogueIndex++;
				gp.keyH.aPressed = false;
			}	
		}
	}
			
	// TRADE
	private void drawTradeScreen() {
		
		// PLAYER HAS DIALOGUE RESPONSE
		if (response) {
			dialogue_select();
		}
		else {
			switch (subState) {
				case 0: trade_select(); break;
				case 1: trade_buy(); break;
				case 2: trade_sell(); break;
			}
		}
		
		gp.keyH.aPressed = false;
	}
	private void dialogue_select() {
	
		drawDialogueScreen(false);
		
		int x = gp.tileSize * 8;
		int y = gp.tileSize * 4;
		int width = gp.tileSize * 6;
		int height = gp.tileSize * 3;
		drawSubWindow(x, y, width, height);
				
		x += gp.tileSize;	
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
		
		// LOOP THROUGH EACH DIALOGUE RESPONSE
		for (int i = 0; i < npc.responses[responseSet].length; i++) {
			
			if (npc.responses[responseSet][i] != null) {
				
				y += gp.tileSize - 7;
				g2.drawString(npc.responses[responseSet][i], x, y);
				
				// IF RESPONSE IS SELECTED, DRAW DIALOGUE ANSWER
				if (commandNum == i) {
					g2.drawString(">", x-24, y);
					if (gp.keyH.aPressed && canSkip) {
						commandNum = 0;
						subState = 0;
						responseSet = 0;
						response = false;
						
						canSkip = false;
						charIndex = 0;
						combinedText = "";						
						dialogueCounter = 0;
						
						// CORROSPONDING ANSWER
						npc.dialogueSet = i + offset;	
						
						gp.gameState = gp.dialogueState;
					}
				}		
			}
		}		
	}	
	private void trade_select() {
				
		npc.dialogueSet = 0;
		drawDialogueScreen(false);
		
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
			if (gp.keyH.aPressed) 
				subState = 1;
		}
		
		y += gp.tileSize;
		g2.drawString("Sell", x, y);
		if (commandNum == 1) {
			g2.drawString(">", x-24, y);
			if (gp.keyH.aPressed)
				subState = 2;
		}
		
		y += gp.tileSize;
		g2.drawString("Leave", x, y);
		if (commandNum == 2) {
			g2.drawString(">", x-24, y);
			if (gp.keyH.aPressed) {					
				subState = 0;
				commandNum = 0;
				canSkip = false;
				charIndex = 0;
				combinedText = "";
				npc.startDialogue(npc, 4);
			}	
		}
	}
	private void trade_buy() {
		
		drawRupees();
		
		// DRAW PLAYER INVENTORY
		inventory_collectables(gp.player, false);
		inventory_collectables(npc, true);
				
		// DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
		if (itemIndex < npc.inventory.size()) {

			int x = (int) (gp.tileSize * 5.7);
			int y = (int) (gp.tileSize * 5.5);
			int width = (int) (gp.tileSize * 2.3);
			int height = gp.tileSize;
			drawSubWindow(x, y, width, height);
			g2.drawImage(rupee, x+5, y+8, 32, 32, null);
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 37F));
			int price = npc.inventory.get(itemIndex).price;
			String text = Integer.toString(price);
			x = getXforRightAlignText(text, gp.tileSize * 7);
			g2.drawString(text, x+32, y+35);				
			
			// BUY AN ITEM
			if (gp.keyH.aPressed) {
				
				// NOT ENOUGH RUPEES
				if (npc.inventory.get(itemIndex).price > gp.player.rupees) {
					subState = 0;
					canSkip = false;
					charIndex = 0;
					combinedText = "";
					npc.startDialogue(npc, 1);
				}
				else {			
					
					subState = 0;
					canSkip = false;
					charIndex = 0;
					combinedText = "";
					
					if (gp.player.canObtainItem(npc.inventory.get(itemIndex))) {
						
						gp.player.rupees -= npc.inventory.get(itemIndex).price;	
						rupeeCount = gp.player.rupees;			
						
						// STACKABLE ITEM
						if (npc.inventory.get(itemIndex).amount > 1) {
							npc.inventory.get(itemIndex).amount--;
						}
						// NON-STACKABLE ITEM
						else {
							npc.inventory.remove(itemIndex);
						}									
					}
					else {
						npc.startDialogue(npc, 2);
					}
				}
			} 
		}		
	}
	private void trade_sell() {
		
		drawRupees();
		
		// DRAW PLAYER INVENTORY
		inventory_collectables(gp.player, true);
		inventory_collectables(npc, false);
						
		// DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
		if (itemIndex < gp.player.inventory.size()) {

			int x = (int) (gp.tileSize * 12.7);
			int y = (int) (gp.tileSize * 5.5);
			int width = (int) (gp.tileSize * 2.3);
			int height = gp.tileSize;
			drawSubWindow(x, y, width, height);
			g2.drawImage(rupee, x+5, y+8, 32, 32, null);
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 37F));
			int price = gp.player.inventory.get(itemIndex).price / 2;
			String text = Integer.toString(price);
			x = getXforRightAlignText(text, gp.tileSize * 14);
			g2.drawString(text, x+32, y+35);	
			
			// SELL AN ITEM
			if (gp.keyH.aPressed) {
												
				// CAN SELL
				if (npc.canObtainItem(gp.player.inventory.get(itemIndex)) &&
						(gp.player.inventory.get(itemIndex).type == npc.type_collectable ||
						gp.player.inventory.get(itemIndex).type == npc.type_consumable)) {
					
					rupeeCount = gp.player.rupees + price;	
											
					// STACKABLE ITEM
					if (gp.player.inventory.get(itemIndex).amount > 1) {
						gp.player.inventory.get(itemIndex).amount--;
					}
					// NON-STACKABLE ITEM
					else {
						gp.player.inventory.remove(itemIndex);														
					}	
				}
				// NOT SELLABLE
				else {					
					subState = 0;
					commandNum = 0;		
					canSkip = false;
					charIndex = 0;
					combinedText = "";
					npc.startDialogue(npc, 3);
				}
			}
		}		
	}
		
	// ITEM GET 
	private void drawItemGetScreen() {
	
		int x = gp.tileSize * 2;
		int y = (gp.screenWidth / 2 ) - gp.tileSize;
		int width = gp.screenWidth - (gp.tileSize * 4);
		int height = gp.tileSize * 4;		
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 37F));
		x += gp.tileSize;
		y += gp.tileSize;
		
		for (String line : currentDialogue.split("\n")) { 
			g2.drawString(line, x, y);	
			y += 40;
		} 
		
		x = (gp.screenWidth / 2) - 24;
		y = gp.tileSize * 10;
	  	g2.drawImage(dialogue_finish, x, y + 30, null);
  		
		// DISPLAY ITEM ABOVE PLAYER
		if (newItem != null) {			
									
			BufferedImage playerGet = null;
			
			if (newItem.type == newItem.type_item || newItem.type == newItem.type_equipment) {
				playerGet = gp.player.itemGet_2;
			}
			else {
				playerGet = gp.player.itemGet_1;
			}
			
			gp.player.drawing = false;
			g2.drawImage(newItem.down1, gp.player.screenX, gp.player.screenY - gp.tileSize, null);
			g2.drawImage(playerGet, gp.player.screenX, gp.player.screenY, null);	
		}			
	}
	
	// TRANSITION
	private void drawTransition() {
		
		// DARKEN SCREEN
		counter++;
		g2.setColor(new Color(0,0,0, counter * 5)); 
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// STOP DARKENING SCREEN
		if (counter == 50) {
			counter = 0;			
			gp.gameState = gp.playState;
			
			gp.player.direction = transitionDirection;
			gp.currentMap = gp.eHandler.tempMap;
			
			gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
			gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
			
			gp.player.defaultWorldX = gp.player.worldX;
			gp.player.defaultWorldY = gp.player.worldY;
			
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;
			
			gp.changeArea();
		}		
	}
	
	// SLEEP
	private void drawSleepScreen() {
		
		// DARKEN FOR 2 SECONDS
		counter++;
		if (counter < 120) { 
			gp.eManager.lighting.filterAlpha += 0.01f;
			if (gp.eManager.lighting.filterAlpha > 1f) {
				gp.eManager.lighting.filterAlpha = 1f;
			}
		}
		// BRIGHTEN FOR 2 SECONDS
		else if (counter >= 120) { 
			gp.eManager.lighting.filterAlpha -= 0.01f;
			if (gp.eManager.lighting.filterAlpha <= 0f) {
				gp.eManager.lighting.filterAlpha = 0f;
				counter = 0;
				gp.eManager.lighting.dayState = gp.eManager.lighting.day;
				gp.eManager.lighting.dayCounter = 0;
				
				if (gp.eManager.lighting.bloodMoonCounter < gp.eManager.lighting.bloodMoonMax)
					gp.eManager.lighting.bloodMoonCounter++;
				
				gp.gameState = gp.playState;
			}
		}
	}
	
	// CUT SCENE
	private void drawScene() {
		
		g2.setColor(new Color(0,0,0,200));
		g2.fillRect(263, 543, gp.tileSize * 5, 30);		
		
		g2.setColor(Color.WHITE);
		g2.setFont(gp.ui.PK_DS.deriveFont(30f));
		String text = "[Press " + KeyEvent.getKeyText(gp.btn_START) + " to skip]";
		g2.drawString(text, gp.ui.getXforCenteredText(text), 565);
	}
	
	// GAME OVER
	private void drawGameOverScreen() {
				
		if (deathSprite < 16)
			playerDeathAnimation();		
		else {						
			if (!gp.music.clip.isActive())
				gp.playMusic(0);
			
			// REDUCED OPACITY BLACK
			g2.setColor(new Color(0,0,0,200)); 
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
						
			// TITLE SHADOW
			g2.setColor(Color.BLACK);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
			String text = "Game Over";
			int x = getXforCenteredText(text);
			int y = gp.tileSize * 5;
			g2.drawString(text, x, y);
			
			// TITLE
			g2.setColor(new Color(190,0,25));
			g2.drawString(text, x - 4, y - 4);
			
			// CONTINUE
			g2.setColor(Color.WHITE);
			g2.setFont(g2.getFont().deriveFont(40F));
			text = "Continue From Last Save";
			x = getXforCenteredText(text);
			y += gp.tileSize * 2;
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x - 40, y);
				
				if (gp.keyH.aPressed) {
					gp.stopMusic();
					
					gp.resetGame();
					commandNum = 0;	
					titleScreenState = 0;
					deathSprite = 0;
					deathCounter = 0;
					
					if (gp.saveLoad.loadFileData(gp.fileSlot) != null) {						
						gp.saveLoad.load(gp.fileSlot);					
					}
					
					gp.gameState = gp.playState;
					gp.setupMusic(true);			
				}
			}
			
			// QUIT
			text = "Quit to Title Screen";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				g2.drawString(">", x - 40, y);
				
				if (gp.keyH.aPressed) {
					gp.stopMusic();		
					
					commandNum = 0;	
					titleScreenState = 0;
					deathSprite = 0;
					deathCounter = 0;
					
					gp.resetGame();
					gp.gameState = gp.titleState;
					gp.setupMusic(true);
				}
			}
		}
	}
	private void playerDeathAnimation() {
		
		// CHANGE IMAGE EVERY 6 FRAMES
		deathCounter++;
		if (deathCounter > 6) {
			
			// SPIN PLAYER SPRITE 2 TIMES
			if (deathSprite == 0 || deathSprite == 4) 
				gp.player.image = gp.player.die1;		
			else if (deathSprite == 1 || deathSprite == 5) 
				gp.player.image = gp.player.die2;		
			else if (deathSprite == 2 || deathSprite == 6) 
				gp.player.image = gp.player.die3;
			else if (deathSprite == 3 || deathSprite == 7) 
				gp.player.image = gp.player.die4;
			else if (deathSprite == 8) 
				gp.player.image = gp.player.die5;
			
			// RESET FRAME COUNTER
			deathCounter = 0;
			deathSprite++;
		}
	}
	
	// SOUND EFFECTS
	private void playDialogueSE() {
		gp.playSE(1, 5);
	}
	public void playDialogueFinishSE() {
		gp.playSE(1, 6);
	}
	private void playWalletSE() {
		gp.playSE(2, 0);
	}
	
	// SUB WINDOW
	private void drawSubWindow(int x, int y, int width, int height) {
		
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
	private void drawSubWindow(int x, int y, int width, int height, Color color) {
		
		// BLACK COLOR (RGB, Transparency)
		Color c = new Color(0,0,0,220);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 25, 25); // 25px round corners

		g2.setColor(color);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 15, 15);
		g2.setColor(Color.WHITE);
	}	
	
	// MISC
	private void drawItemCount(int item, int x, int y, Color color, float fontSize) {
		
		String itemCount = "";
		
		y += gp.tileSize - 2;
		
		if (item == 1) {
			itemCount = Integer.toString(gp.player.arrows);				
			if (gp.player.arrows == gp.player.maxArrows) color = new Color(20,190,10);			
		}
		else if (item == 2) {
			itemCount = Integer.toString(gp.player.bombs);				
			if (gp.player.bombs == gp.player.maxBombs) color = new Color(20,190,10);		
		}
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, fontSize));
		
		g2.setColor(Color.BLACK);
		g2.drawString(itemCount, x, y+1);
		
		g2.setColor(color);
		g2.drawString(itemCount, x, y);
	}
	public int getItemIndexOnSlot(int slotCol, int slotRow) {		
		int itemIndex = slotCol + (slotRow * 5);
		return itemIndex;
	}
	public int getXforCenteredText(String text) {
		try {
			int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			int x = (gp.screenWidth / 2) - (length / 2);
			return x;
		}
		catch (Exception e) {
			return gp.screenWidth / 2;
		}		
	}
	public int getXforRightAlignText(String text, int tailX) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
	public <T> int getLength(T[][] arr, int set){
	    int count = 0;
	    
	    for(T el : arr[set])
	        if (el != null)
	            ++count;
	    
	    return count;
	}
	private BufferedImage setup(String imagePath) {	
		
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = GamePanel.utility.scaleImage(image, gp.tileSize, gp.tileSize);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	private BufferedImage setup(String imagePath, int width, int height) {
		
		UtilityTool utility = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = utility.scaleImage(image, width, height);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}	
	public void changeAlpha(Graphics2D g2, float alphaValue) {		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
}