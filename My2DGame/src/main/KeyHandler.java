package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class KeyHandler implements KeyListener{

	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean spacePressed, shiftPressed;
	
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
						
			// MAIN MENU
			if (gp.ui.titleScreenState == 0) {
				
				if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
					gp.playSE(2);
					gp.ui.commandNum--;
					if (gp.ui.commandNum < 0)
						gp.ui.commandNum = 0;
				}
				if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
					gp.playSE(2);
					gp.ui.commandNum++;
					if (gp.ui.commandNum > 2)
						gp.ui.commandNum = 2;
				}
				if (code == KeyEvent.VK_SPACE) { 
					gp.playSE(3);
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
				String keyboardLetters = "QWERTYUIOPASDFGHJKLZXCVBNM";			
				Map<Integer, String> keyboard = new LinkedHashMap<>();
				
				for (int i = 0; i < keyboardLetters.length(); i++) 
					keyboard.put(i, String.valueOf(keyboardLetters.charAt(i)));
								
				// NAVIGATE THROUGH ON-SCREEN KEYBOARD
				if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
					gp.playSE(2);
					if (gp.ui.commandNum >= 10 && gp.ui.commandNum <= 18) 
						gp.ui.commandNum -= 10;					
					else if (gp.ui.commandNum >= 19 && gp.ui.commandNum <= 25) 
						gp.ui.commandNum -= 9;	
					else if (gp.ui.commandNum == 26)
						gp.ui.commandNum = 18;
					else if (gp.ui.commandNum >= 27)
						gp.ui.commandNum = 19;
				}
				if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
					gp.playSE(2);
					if (gp.ui.commandNum >= 0 && gp.ui.commandNum <= 8) 
						gp.ui.commandNum += 10;					
					else if (gp.ui.commandNum >= 9 && gp.ui.commandNum <= 17) 
						gp.ui.commandNum += 9;	
					else if (gp.ui.commandNum == 18)
						gp.ui.commandNum += 8;
					else if (gp.ui.commandNum >= 19 && gp.ui.commandNum <= 26)
						gp.ui.commandNum = 27;					
				}
				if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
					gp.playSE(2);
					gp.ui.commandNum--;
					
					if (gp.ui.commandNum < 0)
						gp.ui.commandNum = 0;
				}
				if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {					
					gp.ui.commandNum++;
					
					// STOP PLAYER FROM STARTING IF NO LETTERS
					if (gp.ui.commandNum == 28 && gp.playerName.length() < 1) {
						gp.playSE(4);
						gp.ui.commandNum = 27;
					}
					else 
						gp.playSE(2);
									
					if (gp.ui.commandNum > 28)
						gp.ui.commandNum = 28;
				}				
				if (code == KeyEvent.VK_SPACE) {
					// DEL BUTTON
					if (gp.ui.commandNum == 26) {
						if (gp.playerName.length() > 0) {
							gp.playSE(3);
							gp.playerName = gp.playerName.substring(
								0, gp.playerName.length() - 1
							);							
						}
						else
							gp.playSE(4);
					}					
					// BACK BUTTON
					else if (gp.ui.commandNum == 27) {
						gp.playSE(3);
						gp.ui.commandNum = 0;
						gp.ui.titleScreenState = 0;
						gp.playerName = "";
					}
					// ENTER BUTTON
					else if (gp.ui.commandNum == 28) {
						gp.playSE(3);
						gp.gameState = 1;
						gp.stopMusic();
						gp.playMusic(1);						
					}
					// LETTER SELECT
					// get char in map via corresponding key (EX: 0 -> Q, 10 -> A)
					else {
						gp.playSE(3);	
						// name limit is 10 char
						if (gp.playerName.length() <= 10) 
							gp.playerName += keyboard.get(gp.ui.commandNum);
						
						// if name too long, replace last character with selected letter						
						else {
							gp.playerName = gp.playerName.substring(
									0, gp.playerName.length() - 1
								);	
							gp.playerName += keyboard.get(gp.ui.commandNum);
						}
					}
				}				
				
			}
		}
		// PLAY STATE
		else if (gp.gameState == gp.playState) {
				
				if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) upPressed = true;
				if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) downPressed = true;	
				if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) leftPressed = true;
				if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) rightPressed = true;
				if (code == KeyEvent.VK_SHIFT) shiftPressed = true;	
				if (code == KeyEvent.VK_SPACE) spacePressed = true;
				if (code == KeyEvent.VK_ESCAPE) gp.gameState = gp.pauseState;			
			}
		else if (gp.gameState == gp.pauseState) {
			if (code == KeyEvent.VK_ESCAPE) gp.gameState = gp.playState;
		}
		else if (gp.gameState == gp.dialogueState) {
			if (code == KeyEvent.VK_SPACE) gp.gameState = gp.playState;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode(); // key released by user
		
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) upPressed = false;
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) downPressed = false;	
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) leftPressed = false;
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) rightPressed = false;
		if (code == KeyEvent.VK_SHIFT) shiftPressed = false;
		if (code == KeyEvent.VK_SPACE) spacePressed = false;
	}
}






