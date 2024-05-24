package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;
import java.util.Map;

public class KeyHandler implements KeyListener{

	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean spacePressed, shiftPressed;
	public boolean debug = false;
	public String keyboardLetters;
	public boolean isCapital = true;
	
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
		//CHARACTER STATE
		else if (gp.gameState == gp.characterState) {
			characterState(code);
		}
	}
	
	public void titleState(int code) { 
		
		// MAIN MENU
		if (gp.ui.titleScreenState == 0) {
			
			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
				gp.playSE(1, 0);
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0)
					gp.ui.commandNum = 0;
			}
			if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				gp.playSE(1, 0);
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 2)
					gp.ui.commandNum = 2;
			}
			if (code == KeyEvent.VK_SPACE) { 
				gp.playSE(1, 1);
				// NEW GAME OPTION
				if (gp.ui.commandNum == 0) {
					gp.ui.titleScreenState = 1;
				}
				// LOAD GAME OPTION
				else if (gp.ui.commandNum == 1) {
					
				}
				// QUIT GAME OPTION
				else if (gp.ui.commandNum == 2) {
					System.exit(0);
				}
			}
		}
		// NEW GAME
		else if (gp.ui.titleScreenState == 1) {
			
			// MAP VALUES TO ON-SCREEN KEYBOARD
			Map<Integer, String> keyboard = new LinkedHashMap<>();
			
			if (isCapital) keyboardLetters = "QWERTYUIOPASDFGHJKLZXCVBNM";	
			else keyboardLetters = "qwertyuiopasdfghjklzxcvbnm";				
			
			for (int i = 0; i < keyboardLetters.length(); i++) 
				keyboard.put(i, String.valueOf(keyboardLetters.charAt(i)));
							
			// NAVIGATE THROUGH ON-SCREEN KEYBOARD
			if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {				
				if (gp.ui.commandNum >= 10) {
					gp.playSE(1, 0);
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
			if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
				if (gp.ui.commandNum <= 27) {
					gp.playSE(1, 0);
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
			if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
				if (gp.ui.commandNum > 0) {
					gp.playSE(1, 0);
					gp.ui.commandNum--;
					
					if (gp.ui.commandNum < 0)
						gp.ui.commandNum = 0;
				}
			}
			if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {		
				if (gp.ui.commandNum <= 28) {
					gp.ui.commandNum++;
					
					// STOP PLAYER FROM STARTING IF NO LETTERS
					if (gp.ui.commandNum == 29 && gp.player.name.length() < 1) {
						gp.playSE(1, 2);
						gp.ui.commandNum = 28;
					}
					else 
						gp.playSE(1, 0);
									
					if (gp.ui.commandNum > 29)
						gp.ui.commandNum = 29;
				}
			}				
			if (code == KeyEvent.VK_SPACE) {
				
				// DEL BUTTON
				if (gp.ui.commandNum == 26) {
					if (gp.player.name.length() > 0) {
						gp.playSE(1, 1);
						gp.player.name = gp.player.name.substring(
							0, gp.player.name.length() - 1
						);							
					}
					else
						gp.playSE(1, 2);
				}			
				// CAPS BUTTON
				else if (gp.ui.commandNum == 27) {
					gp.playSE(1, 1);
					if (isCapital) isCapital = false;
					else isCapital = true;
				}
				// BACK BUTTON
				else if (gp.ui.commandNum == 28) {
					gp.playSE(1, 1);
					gp.ui.commandNum = 0;
					gp.ui.titleScreenState = 0;
					gp.player.name = "Link";
				}
				// ENTER BUTTON
				else if (gp.ui.commandNum == 29) {
					gp.playSE(1, 1);
					gp.gameState = 1;
					gp.stopMusic();
					gp.playMusic(1);						
				}
				// LETTER SELECT
				// get char in map via corresponding key (EX: 0 -> Q, 10 -> A)
				else {
					
					gp.playSE(1, 1);	
					// name limit is 10 char
					if (gp.player.name.length() <= 10) 
						gp.player.name += keyboard.get(gp.ui.commandNum);
					
					// if name too long, replace last character with selected letter						
					else {
						gp.player.name = gp.player.name.substring(
								0, gp.player.name.length() - 1
							);	
						gp.player.name += keyboard.get(gp.ui.commandNum);
					}
				}
			}				
			
		}
	}
	
	public void playState(int code) { 
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) upPressed = true;
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) downPressed = true;	
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) leftPressed = true;
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) rightPressed = true;
		if (code == KeyEvent.VK_SPACE) spacePressed = true;
		if (code == KeyEvent.VK_SHIFT) shiftPressed = true;	
		if (code == KeyEvent.VK_ESCAPE) gp.gameState = gp.pauseState;
		if (code == KeyEvent.VK_CONTROL) gp.gameState = gp.characterState;
		if (code == KeyEvent.VK_Q) debug = true;
		if (code == KeyEvent.VK_R) gp.tileM.loadMap("/maps/worldmap.txt");
	}
	
	public void pauseState(int code) { 
		if (code == KeyEvent.VK_ESCAPE) gp.gameState = gp.playState;
	}
	
	public void dialogueState(int code) { 
		if (code == KeyEvent.VK_SPACE) gp.gameState = gp.playState;
	}
	
	public void characterState(int code) { 
		if (code == KeyEvent.VK_CONTROL) gp.gameState = gp.playState;
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) { 
			if (gp.ui.slotRow != 0) {
				gp.playSE(1, 1); 
				gp.ui.slotRow--; 
			}
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) { 
			if (gp.ui.slotRow != 3) { 
				gp.playSE(1, 1); 
				gp.ui.slotRow++; 
			}
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) { 
			if (gp.ui.slotCol != 0) { 
				gp.playSE(1, 1);
				gp.ui.slotCol--; 
			}
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) { 
			if (gp.ui.slotCol != 4) {
				gp.playSE(1, 1); 
				gp.ui.slotCol++; 
			}
		}
		if (code == KeyEvent.VK_SPACE) {
			gp.player.selectItem();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) upPressed = false;
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) downPressed = false;	
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) leftPressed = false;
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) rightPressed = false;
		if (code == KeyEvent.VK_SHIFT) shiftPressed = false;
		if (code == KeyEvent.VK_SPACE) spacePressed = false;
	}
}