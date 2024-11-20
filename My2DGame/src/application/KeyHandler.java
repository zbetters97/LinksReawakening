package application;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;
import java.util.Map;

import data.Progress;
import entity.Entity.Action;

public class KeyHandler implements KeyListener {

	private GamePanel gp;
	private boolean lock = true;
	public boolean upPressed, downPressed, leftPressed, rightPressed, 
					dupPressed, ddownPressed, 
					aPressed, bPressed, xPressed, yPressed, 
					lPressed, rPressed, zPressed,
					startPressed, selectPressed;
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
		// DIALOGUE STATE
		else if (gp.gameState == gp.dialogueState) {
			dialogueState(code);
		}		
		// TRADE STATE
		else if (gp.gameState == gp.tradeState) {
			tradeState(code);
		}
		// ACHIEVEMENT STATE
		else if (gp.gameState == gp.achievmentState) {
			achievmentState(code);
		}
		// MUSIC STATE
		else if (gp.gameState == gp.musicState) {
			musicState(code);
		}
		// DIALOGUE STATE
		else if (gp.gameState == gp.cutsceneState) {
			sceneState(code);
		}		
		// GAME OVER STATE
		else if (gp.gameState == gp.gameOverState) {
			gameOverState(code);
		}
		// ENDING STATE
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
		
		if (code == gp.btn_UP) {
			if (gp.ui.commandNum > 0) {
				playCursorSE();
				gp.ui.commandNum--;
				
				if (gp.ui.commandNum < 0)
					gp.ui.commandNum = 0;
			}
		}
		if (code == gp.btn_DOWN) {
			if (gp.ui.commandNum < 2) {
				playCursorSE();
				gp.ui.commandNum++;
				
				if (gp.ui.commandNum > 2)
					gp.ui.commandNum = 2;
			}
		}
		if (code == gp.btn_A) { 
			
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
		if (code == gp.btn_UP) {				
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
		if (code == gp.btn_DOWN) {
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
		if (code == gp.btn_LEFT) {
			if (gp.ui.commandNum > 0) {
				playCursorSE();
				gp.ui.commandNum--;
				
				if (gp.ui.commandNum < 0)
					gp.ui.commandNum = 0;
			}
		}
		if (code == gp.btn_RIGHT) {		
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
		if (code == gp.btn_A) {
			
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
				gp.getMapTitle();
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
		
		if (code == gp.btn_UP) {
			if (gp.ui.commandNum > 0) {
				playCursorSE();
				gp.ui.commandNum--;
				
				if (gp.ui.commandNum < 0)
					gp.ui.commandNum = 0;
			}
		}
		if (code == gp.btn_DOWN) {
			if (gp.ui.commandNum < 3) {
				playCursorSE();
				gp.ui.commandNum++;
				
				if (gp.ui.commandNum > 3)
					gp.ui.commandNum = 3;
			}
		}		
		if (code == gp.btn_A) { 
									
			// LOAD GAME OPTION 1
			if (gp.ui.commandNum == 0) {
				if (gp.saveLoad.loadFileData(0) != null) {
					playSelectSE();		

					gp.stopMusic();
					gp.saveLoad.load(0);	
					gp.fileSlot = 0;
					gp.tileM.loadMap();
					gp.gameState = gp.playState;
					gp.setupMusic(true);	
					gp.getMapTitle();
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
					gp.tileM.loadMap();
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
					gp.tileM.loadMap();
					gp.gameState = gp.playState;
					gp.setupMusic(true);	
					gp.getMapTitle();
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
		
		/* DIR: 	MOVEMENT
		 * A:		ACTION
		 * B:		ATTACK
		 * X:		ITEM
		 * Y:		ITEM
		 * L:		TARGET
		 * R:		SHIELD
		 * Z:		TAB
		 * DUP:		MAP
		 * DDOWN:	MINI MAP
		 * START:	PAUSE
		 * SELECT:	INVENTORY */		

		if (code == gp.btn_UP) upPressed = true;
		if (code == gp.btn_DOWN) downPressed = true;
		if (code == gp.btn_LEFT) leftPressed = true;
		if (code == gp.btn_RIGHT) rightPressed = true;
						
		if (code == gp.btn_A && lock) { aPressed = true; lock = false; }
		if (code == gp.btn_B && lock) { bPressed = true; lock = false; }
		if (code == gp.btn_X && lock) { xPressed = true; lock = false; }
		if (code == gp.btn_Y && lock) { yPressed = true; lock = false; }
		
		if (code == gp.btn_L && lock) { lPressed = true; lock = false; }
		if (code == gp.btn_R && lock) { rPressed = true; lock = false; }
		if (code == gp.btn_Z && lock) { zPressed = true; lock = false; }

		if (code == gp.btn_DDOWN && lock) {			
			if (!gp.map.miniMapOn) playMapOpenSE();
			gp.map.miniMapOn = !gp.map.miniMapOn;
			lock = false;
		}
		
		if (code == gp.btn_START && lock) { 
			if (Progress.canSave && gp.player.action == Action.IDLE) {
				playMenuOpenSE(); 
				gp.gameState = gp.pauseState; 
				lock = false;
			}
			else {
				gp.keyH.playErrorSE();
				lock = false;
			}
		}		
		
		if (code == gp.btn_DEBUG) { if (debug) debug = false; else debug = true; }
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
		
		if (gp.ui.pauseState == 1) {
			
			if (gp.ui.inventoryScreen == 0) {		
				if (code == gp.btn_UP) { 
					if (gp.ui.playerSlotRow != 0) {
						playCursorSE(); 
						gp.ui.playerSlotRow--; 
					}
				}
				if (code == gp.btn_DOWN) { 
					if (gp.ui.playerSlotRow != 4) { 
						playCursorSE(); 
						gp.ui.playerSlotRow++; 
					}
				}
				if (code == gp.btn_LEFT) { 
					if (gp.ui.playerSlotCol != 0) { 
						playCursorSE();
						gp.ui.playerSlotCol--; 
					}
				}
				if (code == gp.btn_RIGHT) { 
					if (gp.ui.playerSlotCol != 4) {
						playCursorSE(); 
						gp.ui.playerSlotCol++; 
					}
					else {
						playCursorSE(); 
						gp.ui.playerSlotCol = 0;
						gp.ui.playerSlotRow = 0;
						gp.ui.inventoryScreen = 1;						
					}
				}
				if (code == gp.btn_A && lock) {					
					gp.player.selectCollectable();		
					lock = false;
				}
			}
			else if (gp.ui.inventoryScreen == 1) {				
				if (code == gp.btn_UP) { 
					if (gp.ui.playerSlotRow != 0) {
						playCursorSE(); 
						gp.ui.playerSlotRow--; 
					}					
				}
				if (code == gp.btn_DOWN) { 
					if (gp.ui.playerSlotRow != 2) { 
						playCursorSE(); 
						gp.ui.playerSlotRow++; 
					}
				}
				if (code == gp.btn_LEFT) { 
					if (gp.ui.playerSlotCol != 0) { 
						playCursorSE();
						gp.ui.playerSlotCol--; 
					}
					else {
						playCursorSE(); 
						gp.ui.playerSlotCol = 4;
						gp.ui.playerSlotRow = 0;
						gp.ui.inventoryScreen = 0;
					}
				}
				if (code == gp.btn_RIGHT) { 
					if (gp.ui.playerSlotCol != 2) {
						playCursorSE(); 
						gp.ui.playerSlotCol++; 
					}
				}
				if (code == gp.btn_A && lock) {					
					gp.player.selectItem();		
					lock = false;
				}
			}			
		}
		else if (gp.ui.pauseState == 2) {
			if (code == gp.btn_UP) { 
				if (gp.ui.commandNum != 0) {
					playCursorSE(); 
					gp.ui.commandNum--; 
				}
			}
			if (code == gp.btn_DOWN) { 
				if (gp.ui.commandNum != maxCommandNum) { 
					playCursorSE(); 
					gp.ui.commandNum++; 
				}
			}
			if (code == gp.btn_LEFT) {				
				if (gp.ui.subState == 0) {
					if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
						playCursorSE();
						gp.music.volumeScale--;
						gp.music.checkVolume();		
					}
					if (gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
						playCursorSE();
						gp.se.volumeScale--;	
						gp.music.checkVolume();		
					}
					if (gp.ui.commandNum == 3) {
						playCursorSE();
						gp.ui.textSpeed++;	
						if (gp.ui.textSpeed > 2) {
							gp.ui.textSpeed = 0;
						}
					}
				}
			}
			if (code == gp.btn_RIGHT) {
				if (gp.ui.subState == 0) {
					if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
						playCursorSE();
						gp.music.volumeScale++;
						gp.music.checkVolume();					
					}
					if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {					
						playCursorSE();
						gp.se.volumeScale++;	
						gp.music.checkVolume();		
					}
					if (gp.ui.commandNum == 3) {
						playCursorSE();
						gp.ui.textSpeed--;	
						if (gp.ui.textSpeed < 0) {
							gp.ui.textSpeed = 2;
						}
					}
				}
			}
		}
		
		if (code == gp.btn_L && lock) {			
			if (gp.ui.pauseState > 0) {
				playCursorSE();
				gp.ui.pauseState--;
				lock = false;
			}
		}
		if (code == gp.btn_R && lock) {
			if (gp.ui.pauseState < 2) {
				playCursorSE();
				gp.ui.pauseState++;
				lock = false;
			}
		}
		
		if ((code == gp.btn_START || code == gp.btn_B) && lock) {
			playMenuCloseSE();
			gp.ui.playerSlotRow = 0;
			gp.ui.playerSlotCol = 0;
			gp.ui.commandNum = 0;
			gp.ui.subState = 0;
			gp.ui.inventoryScreen = 1;
			gp.ui.pauseState = 1;
			gp.gameState = gp.playState;
			lock = false;
		}
		if (code == gp.btn_A && lock) {
			playSelectSE();
			aPressed = true;
			lock = false;
		}
	}
			
	// PLAYER INVENTORY	
	private void playerInventory(int code) {
		if (code == gp.btn_UP) { 
			if (gp.ui.playerSlotRow != 0) {
				playCursorSE(); 
				gp.ui.playerSlotRow--; 
			}
		}
		if (code == gp.btn_DOWN) { 
			if (gp.ui.playerSlotRow != 3) { 
				playCursorSE(); 
				gp.ui.playerSlotRow++; 
			}
		}
		if (code == gp.btn_LEFT) { 
			if (gp.ui.playerSlotCol != 0) { 
				playCursorSE();
				gp.ui.playerSlotCol--; 
			}
		}
		if (code == gp.btn_RIGHT) { 
			if (gp.ui.playerSlotCol != 4) {
				playCursorSE(); 
				gp.ui.playerSlotCol++; 
			}
		}
	}
		
	// DIALOGUE
	private void dialogueState(int code) { 
		if (code == gp.btn_A && lock) {	aPressed = true; lock = false; }
	}
	
	// Achievement	
	private void achievmentState(int code) {
		if (code == gp.btn_A) {
						
			if (gp.ui.npc != null && gp.ui.npc.hasItemToGive) {		
				gp.ui.npc.inventory.remove(gp.ui.newItemIndex);				
				gp.ui.npc = null;
			}			
			
			gp.ui.newItem = null;
			gp.ui.songPlayed = null;
			gp.player.drawing = true;
			gp.gameState = gp.playState;
		}
	}
	
	// MUSIC
	private void musicState(int code) { 
		if (code == gp.btn_UP && lock) { upPressed = true; lock = false; }
		if (code == gp.btn_DOWN && lock) { downPressed = true; lock = false; }
		if (code == gp.btn_LEFT && lock) { leftPressed = true; lock = false; }
		if (code == gp.btn_RIGHT && lock) { rightPressed = true; lock = false; }					
		if (code == gp.btn_A && lock) { aPressed = true; lock = false; }
		if (code == gp.btn_X && lock) { xPressed = true; lock = false; }
	}	
	
	// TRADE
	private void tradeState(int code) {
						
		if (code == gp.btn_A && lock) {			
			playSelectSE();
			aPressed = true;
			lock = false;

			gp.ui.dialogueIndex = 0;
		}
		if (code == gp.btn_B && lock) {						
			bPressed = true;
			lock = false;
		}
		
		if (gp.ui.response) {
			if (code == gp.btn_UP) { 				
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0)					
					gp.ui.commandNum = 0;
				else
					playCursorSE(); 
			}
			if (code == gp.btn_DOWN) { 
				gp.ui.commandNum++;
				if (gp.ui.commandNum > gp.ui.getLength(gp.ui.npc.responses, gp.ui.responseSet) - 1)					
					gp.ui.commandNum = gp.ui.getLength(gp.ui.npc.responses, gp.ui.responseSet) - 1;
				else
					playCursorSE(); 
			}
		}		
		else {		
			if (gp.ui.subState == 0) {
				if (code == gp.btn_UP) { 				
					gp.ui.commandNum--;
					if (gp.ui.commandNum < 0)					
						gp.ui.commandNum = 0;
					else
						playCursorSE(); 
				}
				if (code == gp.btn_DOWN) { 
					gp.ui.commandNum++;
					if (gp.ui.commandNum > 2)					
						gp.ui.commandNum = 2;
					else
						playCursorSE(); 
				}
			}
			if (gp.ui.subState == 1) {
				npcInventory(code);
				if (code == gp.btn_START || code == gp.btn_B) {
					gp.ui.subState = 0;						
					bPressed = false;
				}
			}
			if (gp.ui.subState == 2) {
				playerInventory(code); 
				if (code == gp.btn_START || code == gp.btn_B) {
					gp.ui.subState = 0;			
					bPressed = false;
				}
			}
		}
	}
	
	// NPC INVENTORY
	private void npcInventory(int code) {
		if (code == gp.btn_UP) { 
			if (gp.ui.npcSlotRow != 0) {
				playCursorSE(); 
				gp.ui.npcSlotRow--; 
			}
		}
		if (code == gp.btn_DOWN) { 
			if (gp.ui.npcSlotRow != 3) { 
				playCursorSE(); 
				gp.ui.npcSlotRow++; 
			}
		}
		if (code == gp.btn_LEFT) { 
			if (gp.ui.npcSlotCol != 0) { 
				playCursorSE();
				gp.ui.npcSlotCol--; 
			}
		}
		if (code == gp.btn_RIGHT) { 
			if (gp.ui.npcSlotCol != 4) {
				playCursorSE(); 
				gp.ui.npcSlotCol++; 
			}
		}
	}
	
	// MAP
	private void sceneState(int code) {
		if (code == gp.btn_A && lock) {						
			aPressed = true;			
			lock = false;
		}
		if (code == gp.btn_START && lock) {
			startPressed = true;
			lock = false;
		}
	}
	
	// GAME OVER
	private void gameOverState(int code) {
		
		if (code == gp.btn_UP) { 
			if (gp.ui.commandNum != 0) {
				playCursorSE(); 
				gp.ui.commandNum = 0;
			}
		}
		if (code == gp.btn_DOWN) { 
			if (gp.ui.commandNum != 1) {
				playCursorSE(); 
				gp.ui.commandNum = 1;
			}
		}
		if (code == gp.btn_A && lock) {			
			playSelectSE();
			aPressed = true;
			lock = false;
		}
	}
	
	// ENDING 
	private void endingState(int code) {
		
		if (code == gp.btn_START) {
			gp.gameState = gp.titleState;
			gp.ui.commandNum = 0;
			gp.resetGame();
			gp.setupMusic(true);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {	
		int code = e.getKeyCode();		
		
		if (gp.gameState == gp.musicState) {
			if (code == gp.btn_UP) { upPressed = false; lock = true; }
			if (code == gp.btn_DOWN) { downPressed = false; lock = true; }
			if (code == gp.btn_LEFT) { leftPressed = false; lock = true; }
			if (code == gp.btn_RIGHT) { rightPressed = false; lock = true; }		
		}
		else {
			if (code == gp.btn_UP) upPressed = false; 
			if (code == gp.btn_DOWN) downPressed = false;
			if (code == gp.btn_LEFT) leftPressed = false;
			if (code == gp.btn_RIGHT) rightPressed = false;
		}
		if (code == gp.btn_A) { aPressed = false; lock = true; }
		if (code == gp.btn_B) { bPressed = false; lock = true; }
		if (code == gp.btn_X) { xPressed = false; lock = true; }
		if (code == gp.btn_Y) { yPressed = false; lock = true; }
		
		if (code == gp.btn_L) { lPressed = false; lock = true; }
		if (code == gp.btn_R) { 
			rPressed = false; lock = true; 
			if (gp.player.action == Action.GUARDING)
				gp.player.action = Action.IDLE;
		}
		if (code == gp.btn_Z) { zPressed = false; lock = true; }
		
		if (code == gp.btn_DUP) { lock = true; }
		if (code == gp.btn_DDOWN) { lock = true; }
		
		if (code == gp.btn_START) { startPressed = false; lock = true; }	
		if (code == gp.btn_SELECT) { selectPressed = false; lock = true; }			
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
	public void playMenuReturnSE() {
		gp.playSE(1, 8);
	}
}