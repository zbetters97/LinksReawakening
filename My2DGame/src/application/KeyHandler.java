package application;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;
import java.util.Map;

import entity.Entity.Action;

public class KeyHandler implements KeyListener {

	private GamePanel gp;
	private boolean lock = true;
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean actionPressed, selectPressed, rollPressed, guardPressed, grabPressed, lockPressed, itemPressed, tabPressed;
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
		// INVENTORY STATE
		else if (gp.gameState == gp.inventoryState) {
			inventoryState(code);
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
		// LOAD GAME
		else if (gp.ui.titleScreenState == 2) {
			loadGameMenu(code);	
		}
	}
	private void mainMenu(int code) {
		
		if (code == KeyEvent.VK_UP) {
			if (gp.ui.commandNum > 0) {
				playCursorSE();
				gp.ui.commandNum--;
				
				if (gp.ui.commandNum < 0)
					gp.ui.commandNum = 0;
			}
		}
		if (code == KeyEvent.VK_DOWN) {
			if (gp.ui.commandNum < 2) {
				playCursorSE();
				gp.ui.commandNum++;
				
				if (gp.ui.commandNum > 2)
					gp.ui.commandNum = 2;
			}
		}
		if (code == KeyEvent.VK_SPACE) { 
			
			// NEW GAME OPTION
			if (gp.ui.commandNum == 0) {
				playSelectSE();
				gp.ui.commandNum = 0;
				gp.ui.titleScreenState = 1;
				gp.player.name = "LINK";
			}
			
			// LOAD GAME OPTION
			else if (gp.ui.commandNum == 1) {
				playSelectSE();		
				gp.ui.commandNum = 0;
				gp.ui.titleScreenState = 2;
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
					playErrorSE();
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
							0, gp.player.name.length() - 1);							
				}
				else
					playErrorSE();
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
				gp.player.name = "LINK";
			}
			// ENTER BUTTON
			else if (gp.ui.commandNum == 29) {
				playSelectSE();
				gp.ui.commandNum = 0;			
				gp.fileSlot = 0;						
				gp.gameState = gp.playState;
				gp.resetGame();		
				gp.setupMusic(true);
				gp.ui.titleScreenState = 0;
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
	private void loadGameMenu(int code) {
		
		if (code == KeyEvent.VK_UP) {
			if (gp.ui.commandNum > 0) {
				playCursorSE();
				gp.ui.commandNum--;
				
				if (gp.ui.commandNum < 0)
					gp.ui.commandNum = 0;
			}
		}
		if (code == KeyEvent.VK_DOWN) {
			if (gp.ui.commandNum < 3) {
				playCursorSE();
				gp.ui.commandNum++;
				
				if (gp.ui.commandNum > 3)
					gp.ui.commandNum = 3;
			}
		}
		if (code == KeyEvent.VK_SPACE) { 
									
			// LOAD GAME OPTION 1
			if (gp.ui.commandNum == 0) {
				if (gp.saveLoad.loadFileData(0) != null) {
					playSelectSE();		

					gp.stopMusic();
					gp.saveLoad.load(0);	
					gp.fileSlot = 0;
					gp.gameState = gp.playState;
					gp.setupMusic(true);						
				}
				else {
					playErrorSE();
				}
			}
			// LOAD GAME OPTION 2
			else if (gp.ui.commandNum == 1) {
				
				if (gp.saveLoad.loadFileData(1) != null) {
					playSelectSE();		

					gp.stopMusic();
					gp.saveLoad.load(1);	
					gp.fileSlot = 1;
					gp.gameState = gp.playState;
					gp.setupMusic(true);						
				}
				else {
					playErrorSE();
				}
			}
			// LOAD GAME OPTION 3
			else if (gp.ui.commandNum == 2) {
				if (gp.saveLoad.loadFileData(2) != null) {
					playSelectSE();		

					gp.stopMusic();
					gp.saveLoad.load(2);
					gp.fileSlot = 2;
					gp.gameState = gp.playState;
					gp.setupMusic(true);						
				}
				else {
					playErrorSE();
				}
			}
			// BACK OPTION
			else if (gp.ui.commandNum == 3) {
				playSelectSE();
				gp.ui.commandNum = 1;
				gp.ui.titleScreenState = 0;
			}
		}
	}
	
	// PLAY
	private void playState(int code) { 
		
		/* 
		 * ARROWS: MOVEMENT
		 * SPACE: ACTION
		 * Z: GUARD
		 * G: GRAB
		 * R: ROLL
		 * F: Z-TARGET
		 * Q: ITEM
		 * T: TAB ITEM
		 * ESC: PAUSE MENU
		 * E: INVENTORY
		 * M: MAP
		 * N: MINIMAP
		 * 1: RELOAD MAP
		 * SHIFT: DEBUG
		 */		

		if (code == KeyEvent.VK_UP) upPressed = true;
		if (code == KeyEvent.VK_DOWN) downPressed = true;
		if (code == KeyEvent.VK_LEFT) leftPressed = true;
		if (code == KeyEvent.VK_RIGHT) rightPressed = true;
		if (code == KeyEvent.VK_SPACE && lock) { actionPressed = true; lock = false; }
		if (code == KeyEvent.VK_F && lock) { lockPressed = true; lock = false; }
		if (code == KeyEvent.VK_G && lock) { grabPressed = true; lock = false; }
		if (code == KeyEvent.VK_R && lock) { rollPressed = true; lock = false; }
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
		else if (code == KeyEvent.VK_E) { 
			playMenuOpenSE();
			gp.gameState = gp.inventoryState;
		}			
		else if (code == KeyEvent.VK_M) {
			playMapOpenSE();
			gp.gameState = gp.mapState;
		}
		else if (code == KeyEvent.VK_N) {
			gp.map.miniMapOn = !gp.map.miniMapOn;
		}
		else if (code == KeyEvent.VK_1) {			
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
			case 0: maxCommandNum = 7; break;
			case 3:
			case 4: 
			case 5: maxCommandNum = 3; break;
			case 6: maxCommandNum = 1; break;
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
					playCursorSE();
					gp.music.volumeScale--;
					gp.music.checkVolume();					
				}
				if (gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
					playCursorSE();
					gp.se.volumeScale--;					
				}
			}
		}
		if (code == KeyEvent.VK_RIGHT) {
			if (gp.ui.subState == 0) {
				if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
					playCursorSE();
					gp.music.volumeScale++;
					gp.music.checkVolume();					
				}
				if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {					
					playCursorSE();
					gp.se.volumeScale++;					
				}
			}
		}
		
		if (code == KeyEvent.VK_ESCAPE) {
			playMenuCloseSE();
			gp.ui.commandNum = 0;
			gp.ui.subState = 0;
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_SPACE && lock) {
			playSelectSE();
			actionPressed = true;
			lock = false;
		}
	}
	
	// INVENTORY
	private void inventoryState(int code) { 
		
		if (code == KeyEvent.VK_E) {
			playMenuCloseSE();
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_SPACE) {	
			
			if (gp.ui.inventoryScreen == 0) {
				gp.player.selectInventory();
			}
			else {
				gp.player.selectItem();	
			}
		}
		if (code == KeyEvent.VK_T) {
			playCursorSE();
			
			if (gp.ui.inventoryScreen == 0)
				gp.ui.inventoryScreen = 1;
			else
				gp.ui.inventoryScreen = 0;
		}
		playerInventory(code);
	}
	
	// PLAYER INVENTORY	
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
	
	// MAP
	private void mapState(int code) {
		if (code == KeyEvent.VK_M) {
			gp.gameState = gp.playState;
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
		
		if (gp.ui.response) {
			if (code == KeyEvent.VK_UP) { 				
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0)					
					gp.ui.commandNum = 0;
				else
					playCursorSE(); 
			}
			if (code == KeyEvent.VK_DOWN) { 
				gp.ui.commandNum++;
				if (gp.ui.commandNum > gp.ui.getLength(gp.ui.npc.responses, gp.ui.responseSet) - 1)					
					gp.ui.commandNum = gp.ui.getLength(gp.ui.npc.responses, gp.ui.responseSet) - 1;
				else
					playCursorSE(); 
			}
		}		
		else {		
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
			
			if (gp.ui.commandNum == 0) {
				playSelectSE();
												
				if (gp.saveLoad.loadFileData(gp.fileSlot) != null) {					
					gp.stopMusic();
					
					gp.ui.commandNum = 0;					
					gp.saveLoad.ready = false;
					gp.resetGame();
					gp.saveLoad.load(gp.fileSlot);						
					if (gp.saveLoad.ready) {
						gp.ui.deathSprite = 0;
						gp.ui.deathCounter = 0;
						
						gp.gameState = gp.playState;	
						gp.setupMusic(true);	
					}
				}
				else {
					gp.ui.commandNum = 0;						
					gp.ui.titleScreenState = 0;
					gp.resetGame();
					gp.setupMusic(true);
					gp.gameState = gp.playState;
				}
				
			}
			else if (gp.ui.commandNum == 1) {
				playSelectSE();		
				
				gp.ui.deathSprite = 0;
				gp.ui.deathCounter = 0;
				
				gp.ui.commandNum = 0;
				gp.gameState = gp.titleState;			
				
				gp.resetGame();
				gp.setupMusic(true);
			}			
		}
	}
	
	// ENDING 
	private void endingState(int code) {
		
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.titleState;
			gp.ui.commandNum = 0;
			gp.resetGame();
			gp.setupMusic(true);
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
		if (code == KeyEvent.VK_R) { rollPressed = false; lock = true; }
		if (code == KeyEvent.VK_Z) { 
			guardPressed = false; 
			lock = true;
			if (gp.player.action == Action.GUARDING)
				gp.player.action = Action.IDLE;
		}
		if (code == KeyEvent.VK_G) { 
			grabPressed = false; 
			lock = true;
			if (gp.player.action == Action.GRABBING)
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
	public void playErrorSE() {
		gp.playSE(1, 2);
	}
	public void playMenuOpenSE() {
		gp.playSE(1, 3);
	}
	public void playMenuCloseSE() {
		gp.playSE(1, 4);
	}
	public void playMapOpenSE() {
		gp.playSE(1, 7);
	}
}