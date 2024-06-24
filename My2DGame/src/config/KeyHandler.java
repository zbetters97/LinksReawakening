package config;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;
import java.util.Map;

import application.GamePanel;
import entity.Entity.Action;

public class KeyHandler implements KeyListener {

	private GamePanel gp;
	private boolean lock = true;
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean actionPressed, guardPressed, lockPressed, itemPressed, tabPressed;
	public boolean debug = false;
	private String keyboardLetters;
	public boolean capital = true;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) { } // not used

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode(); // key pressed by user
		
		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			titleState(code);
		}
		// PLAY STATE
		else if (gp.gameState == gp.playState) {
			playState(code);
		}
		// PAUSE STATE
		else if (gp.gameState == gp.pauseState) {
			pauseState(code);
		}
		// MAP STATE
		else if (gp.gameState == gp.mapState) {
			mapState(code);
		}
		// CHARACTER STATE
		else if (gp.gameState == gp.characterState) {
			characterState(code);
		}
		// ITEM STATE
		else if (gp.gameState == gp.itemInventoryState) {
			itemInventoryState(code);
		}
		// DIALOGUE STATE
		else if (gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState) {
			dialogueState(code);
		}		
		// TRADE STATE
		else if (gp.gameState == gp.tradeState) {
			tradeState(code);
		}
		// ITEM GET STATE
		else if (gp.gameState == gp.itemGetState) {
			itemGetState(code);
		}
		// GAME OVER STATE
		else if (gp.gameState == gp.gameOverState) {
			gameOverState(code);
		}
		else if (gp.gameState == gp.endingState) {
			endingState(code);
		}
	}
		
	// TITLE	
	private void titleState(int code) { 
		
		// MAIN MENU
		if (gp.ui.titleScreenState == 0) {			
			mainMenu(code);
		}
		// NEW GAME
		else if (gp.ui.titleScreenState == 1) {
			newGameMenu(code);	
		}
	}
	private void mainMenu(int code) {
		
		if (code == KeyEvent.VK_UP) {
			playCursorSE();
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0)
				gp.ui.commandNum = 0;
		}
		if (code == KeyEvent.VK_DOWN) {
			playCursorSE();
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 2)
				gp.ui.commandNum = 2;
		}
		if (code == KeyEvent.VK_SPACE) { 
			
			// NEW GAME OPTION
			if (gp.ui.commandNum == 0) {
				playSelectSE();
				gp.ui.titleScreenState = 1;
			}
			
			// LOAD GAME OPTION
			else if (gp.ui.commandNum == 1) {
				playSelectSE();		

				gp.stopMusic();
				gp.saveLoad.load();		
				gp.gameState = gp.playState;
				gp.setupMusic();
			}
			
			// QUIT GAME OPTION
			else if (gp.ui.commandNum == 2) {
				System.exit(0);
			}
		}
	}
	public void newGameMenu(int code) {

		// MAP VALUES TO ON-SCREEN KEYBOARD
		Map<Integer, String> keyboard = new LinkedHashMap<>();
		
		if (capital) keyboardLetters = "QWERTYUIOPASDFGHJKLZXCVBNM";	
		else keyboardLetters = "qwertyuiopasdfghjklzxcvbnm";				
		
		for (int i = 0; i < keyboardLetters.length(); i++) 
			keyboard.put(i, String.valueOf(keyboardLetters.charAt(i)));
						
		// NAVIGATE THROUGH ON-SCREEN KEYBOARD
		if (code == KeyEvent.VK_UP) {				
			if (gp.ui.commandNum >= 10) {
				playCursorSE();
				if (gp.ui.commandNum >= 10 && gp.ui.commandNum <= 18) 
					gp.ui.commandNum -= 10;					
				else if (gp.ui.commandNum >= 19 && gp.ui.commandNum <= 25) 
					gp.ui.commandNum -= 9;	
				else if (gp.ui.commandNum == 26)
					gp.ui.commandNum = 17;
				else if (gp.ui.commandNum == 27)
					gp.ui.commandNum = 18;
				else if (gp.ui.commandNum >= 28)
					gp.ui.commandNum = 19;
			}
		}				
		if (code == KeyEvent.VK_DOWN) {
			if (gp.ui.commandNum <= 27) {
				playCursorSE();
				if (gp.ui.commandNum >= 0 && gp.ui.commandNum <= 8) 
					gp.ui.commandNum += 10;					
				else if (gp.ui.commandNum >= 9 && gp.ui.commandNum <= 17) 
					gp.ui.commandNum += 9;	
				else if (gp.ui.commandNum == 18)
					gp.ui.commandNum += 9;
				else if (gp.ui.commandNum >= 19 && gp.ui.commandNum <= 27)
					gp.ui.commandNum = 28;		
			}
		}
		if (code == KeyEvent.VK_LEFT) {
			if (gp.ui.commandNum > 0) {
				playCursorSE();
				gp.ui.commandNum--;
				
				if (gp.ui.commandNum < 0)
					gp.ui.commandNum = 0;
			}
		}
		if (code == KeyEvent.VK_RIGHT) {		
			if (gp.ui.commandNum <= 28) {
				gp.ui.commandNum++;
				
				// STOP PLAYER FROM STARTING IF NO LETTERS
				if (gp.ui.commandNum == 29 && gp.player.name.length() < 1) {
					gp.playSE(1, 2);
					gp.ui.commandNum = 28;
				}
				else 
					playCursorSE();
								
				if (gp.ui.commandNum > 29)
					gp.ui.commandNum = 29;
			}
		}				
		if (code == KeyEvent.VK_SPACE) {
			
			// DEL BUTTON
			if (gp.ui.commandNum == 26) {
				if (gp.player.name.length() > 0) {
					playSelectSE();
					gp.player.name = gp.player.name.substring(
						0, gp.player.name.length() - 1
					);							
				}
				else
					gp.playSE(1, 2);
			}			
			// CAPS BUTTON
			else if (gp.ui.commandNum == 27) {
				playSelectSE();
				if (capital) capital = false;
				else capital = true;
			}
			// BACK BUTTON
			else if (gp.ui.commandNum == 28) {
				playSelectSE();
				gp.ui.commandNum = 0;
				gp.ui.titleScreenState = 0;
				gp.player.name = "Link";
			}
			// ENTER BUTTON
			else if (gp.ui.commandNum == 29) {
				playSelectSE();
				gp.ui.titleScreenState = 0;
				gp.ui.commandNum = 0;					
				gp.gameState = 1;
				gp.resetGame(true);
			}
			// LETTER SELECT				
			else {					
				playSelectSE();	
				
				// 10 CHAR NAME LIMIT
				if (gp.player.name.length() <= 10) 
					gp.player.name += keyboard.get(gp.ui.commandNum);
				
				// if name too long, replace last character with selected letter						
				else {
					gp.player.name = gp.player.name.substring(
							0, gp.player.name.length() - 1);	
					
					// get char in map via corresponding key (EX: 0 -> Q, 10 -> A)
					gp.player.name += keyboard.get(gp.ui.commandNum);
				}
			}
		}		
	}
	
	// PLAY
	private void playState(int code) { 
		
		/* 
		 * ARROWS: MOVEMENT
		 * SPACE: ACTION
		 * Z: GUARD
		 * F: Z-TARGET
		 * Q: ITEM
		 * T: TAB ITEM
		 * ESC: PAUSE MENU
		 * 1: CHARACTER MENU
		 * 2: ITEM MENU
		 * 3: MAP
		 * 4: MINIMAP
		 * 5: RELOAD MAP
		 * SHIFT: DEBUG
		 */		

		if (code == KeyEvent.VK_UP) upPressed = true;
		if (code == KeyEvent.VK_DOWN) downPressed = true;
		if (code == KeyEvent.VK_LEFT) leftPressed = true;
		if (code == KeyEvent.VK_RIGHT) rightPressed = true;
		if (code == KeyEvent.VK_SPACE && lock) { actionPressed = true; lock = false; }
		if (code == KeyEvent.VK_F && lock) { lockPressed = true; lock = false; }
		if (code == KeyEvent.VK_Q && lock) { itemPressed = true; lock = false; }
		if (code == KeyEvent.VK_T && lock) { tabPressed = true; lock = false; }		
		if (code == KeyEvent.VK_Z && lock) { 
			guardPressed = true; 
			lock = false; 
			gp.player.playGuardSE(); 
		}
		
		if (code == KeyEvent.VK_ESCAPE) {
			playMenuOpenSE();
			gp.gameState = gp.pauseState;
		}	
		else if (code == KeyEvent.VK_1) { 
			playMenuOpenSE();
			gp.gameState = gp.characterState;
		}
		else if (code == KeyEvent.VK_2) { 
			playMenuOpenSE();
			gp.gameState = gp.itemInventoryState;
		}
			
		else if (code == KeyEvent.VK_3) {
			playMapOpenSE();
			gp.gameState = gp.mapState;
		}
		else if (code == KeyEvent.VK_4) {
			gp.map.miniMapOn = !gp.map.miniMapOn;
		}
		else if (code == KeyEvent.VK_5) {			
			switch(gp.currentMap) {
				case 0: gp.tileM.loadMap("/maps/worldmap.txt", 0); break;
				case 1: gp.tileM.loadMap("/maps/indoor01.txt", 1); break;
			}			
		}		
		else if (code == KeyEvent.VK_SHIFT) {
			if (debug) debug = false; 
			else debug = true;
		}
	}
	
	// PAUSE
	private void pauseState(int code) { 
		
		int maxCommandNum = 0;
		switch (gp.ui.subState) {
			case 0: maxCommandNum = 6; break;
			case 3: maxCommandNum = 1; break;
		}
		
		if (code == KeyEvent.VK_UP) { 
			if (gp.ui.commandNum != 0) {
				playCursorSE(); 
				gp.ui.commandNum--; 
			}
		}
		if (code == KeyEvent.VK_DOWN) { 
			if (gp.ui.commandNum != maxCommandNum) { 
				playCursorSE(); 
				gp.ui.commandNum++; 
			}
		}
		if (code == KeyEvent.VK_LEFT) {
			
			if (gp.ui.subState == 0) {
				if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
					playCursorSE();
				}
				if (gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
					gp.se.volumeScale--;
					playCursorSE();
				}
			}
		}
		if (code == KeyEvent.VK_RIGHT) {
			if (gp.ui.subState == 0) {
				if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
					playCursorSE();
				}
				if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
					gp.se.volumeScale++;
					playCursorSE();
				}
			}
		}
		
		if (code == KeyEvent.VK_ESCAPE) {
			playMenuCloseSE();
			gp.ui.commandNum = 0;
			gp.ui.subState = 0;
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_SPACE) {
			playSelectSE();
			actionPressed = true;
		}
	}
	
	// MAP
	private void mapState(int code) {
		if (code == KeyEvent.VK_3) {
			gp.gameState = gp.playState;
		}
	}
	
	// CHARACTER
	private void characterState(int code) { 
		
		if (code == KeyEvent.VK_1) {
			playMenuCloseSE();
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_SPACE) {			
			gp.player.selectItem();
		}
		playerInventory(code);
	}
	
	// ITEM
	private void itemInventoryState(int code) { 
		
		if (code == KeyEvent.VK_2) {
			playMenuCloseSE();
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_SPACE) {			
			gp.player.selectItem();
		}
		playerInventory(code);
	}
	
	//INVENTORY	
	private void playerInventory(int code) {
		if (code == KeyEvent.VK_UP) { 
			if (gp.ui.playerSlotRow != 0) {
				playCursorSE(); 
				gp.ui.playerSlotRow--; 
			}
		}
		if (code == KeyEvent.VK_DOWN) { 
			if (gp.ui.playerSlotRow != 3) { 
				playCursorSE(); 
				gp.ui.playerSlotRow++; 
			}
		}
		if (code == KeyEvent.VK_LEFT) { 
			if (gp.ui.playerSlotCol != 0) { 
				playCursorSE();
				gp.ui.playerSlotCol--; 
			}
		}
		if (code == KeyEvent.VK_RIGHT) { 
			if (gp.ui.playerSlotCol != 4) {
				playCursorSE(); 
				gp.ui.playerSlotCol++; 
			}
		}
	}
	
	// DIALOGUE
	private void dialogueState(int code) { 
		if (code == KeyEvent.VK_SPACE && lock) {						
			actionPressed = true;			
			lock = false;
		}
	}
	
	// ITEM GET	
	private void itemGetState(int code) {
		if (code == KeyEvent.VK_SPACE) {
						
			if (gp.ui.npc != null && gp.ui.npc.hasItemToGive) {		
				gp.ui.npc.inventory.remove(gp.ui.newItemIndex);				
				gp.ui.npc = null;
			}			
			
			gp.ui.newItem = null;
			gp.player.attackCanceled = false;
			gp.player.drawing = true;
			gp.gameState = gp.playState;
		}
	}
	
	// TRADE
	private void tradeState(int code) {
						
		if (code == KeyEvent.VK_SPACE && lock) {			
			playSelectSE();
			actionPressed = true;
			lock = false;

			gp.ui.dialogueIndex = 0;
		}
		if (gp.ui.subState == 0) {
			if (code == KeyEvent.VK_UP) { 				
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0)					
					gp.ui.commandNum = 0;
				else
					playCursorSE(); 
			}
			if (code == KeyEvent.VK_DOWN) { 
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 2)					
					gp.ui.commandNum = 2;
				else
					playCursorSE(); 
			}
		}
		if (gp.ui.subState == 1) {
			npcInventory(code);
			if (code == KeyEvent.VK_ESCAPE)
				gp.ui.subState = 0;						
		}
		if (gp.ui.subState == 2) {
			playerInventory(code); 
			if (code == KeyEvent.VK_ESCAPE)
				gp.ui.subState = 0;			
		}
	}
	
	// NPC INVENTORY
	private void npcInventory(int code) {
		if (code == KeyEvent.VK_UP) { 
			if (gp.ui.npcSlotRow != 0) {
				playCursorSE(); 
				gp.ui.npcSlotRow--; 
			}
		}
		if (code == KeyEvent.VK_DOWN) { 
			if (gp.ui.npcSlotRow != 3) { 
				playCursorSE(); 
				gp.ui.npcSlotRow++; 
			}
		}
		if (code == KeyEvent.VK_LEFT) { 
			if (gp.ui.npcSlotCol != 0) { 
				playCursorSE();
				gp.ui.npcSlotCol--; 
			}
		}
		if (code == KeyEvent.VK_RIGHT) { 
			if (gp.ui.npcSlotCol != 4) {
				playCursorSE(); 
				gp.ui.npcSlotCol++; 
			}
		}
	}
	
	// GAME OVER
	private void gameOverState(int code) {
		
		if (code == KeyEvent.VK_UP) { 
			if (gp.ui.commandNum != 0) {
				playCursorSE(); 
				gp.ui.commandNum = 0;
			}
		}
		if (code == KeyEvent.VK_DOWN) { 
			if (gp.ui.commandNum != 1) {
				playCursorSE(); 
				gp.ui.commandNum = 1;
			}
		}
		if (code == KeyEvent.VK_SPACE) {

			gp.ui.deathSprite = 0;
			gp.ui.deathCounter = 0;
			
			if (gp.ui.commandNum == 0) {
				playSelectSE();
				gp.gameState = gp.playState;
				gp.ui.commandNum = 0;
				gp.resetGame(false);
			}
			else if (gp.ui.commandNum == 1){
				playSelectSE();	
				gp.gameState = gp.titleState;
				gp.ui.commandNum = 0;
				gp.resetGame(true);
			}			
		}
	}
	
	// ENDING 
	private void endingState(int code) {
		
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.titleState;
			gp.ui.commandNum = 0;
			gp.resetGame(true);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {	
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_UP) upPressed = false; 
		if (code == KeyEvent.VK_DOWN) downPressed = false;
		if (code == KeyEvent.VK_LEFT) leftPressed = false;
		if (code == KeyEvent.VK_RIGHT) rightPressed = false;
		if (code == KeyEvent.VK_SPACE) { actionPressed = false; lock = true; }
		if (code == KeyEvent.VK_Z) { 
			guardPressed = false; 
			lock = true;
			if (gp.player.action == Action.GUARDING)
				gp.player.action = Action.IDLE;
		}
		if (code == KeyEvent.VK_F) { lockPressed = false; lock = true; }
		if (code == KeyEvent.VK_Q) { 
			itemPressed = false; 
			lock = true; 
			if (gp.player.action == Action.RUNNING) 
				gp.player.action = Action.IDLE;			
		}	
		if (code == KeyEvent.VK_T) { tabPressed = false; lock = true; }
	}
	
	// SOUND EFFECTS
	public void playCursorSE() {
		gp.playSE(1, 0);
	}
	public void playSelectSE() {
		gp.playSE(1, 1);
	}
	public void playMenuOpenSE() {
		gp.playSE(1, 8);
	}
	public void playMenuCloseSE() {
		gp.playSE(1, 9);
	}
	public void playMapOpenSE() {
		gp.playSE(1, 14);
	}

}