package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class KeyHandler implements KeyListener{

	GamePanel gp;
	public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, shiftPressed;
	
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
			
			if (gp.ui.titleScreenState == 0) {
				
				if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
					gp.ui.commandNum--;
					if (gp.ui.commandNum < 0)
						gp.ui.commandNum = 0;
				}
				if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
					gp.ui.commandNum++;
					if (gp.ui.commandNum > 2)
						gp.ui.commandNum = 2;
				}
				if (code == KeyEvent.VK_SPACE) { 
					
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
			else if (gp.ui.titleScreenState == 1) {
				
				String keyboardLetters = "QWERTYUIOPASDFGHJKLZXCVBNM";			
				Map<Integer, String> keyboard = new LinkedHashMap<>();
				
				for (int i = 0; i < keyboardLetters.length(); i++) 
					keyboard.put(i, String.valueOf(keyboardLetters.charAt(i)));
															
				if (code == KeyEvent.VK_LEFT) {
					gp.ui.commandNum--;
					
					if (gp.ui.commandNum < 0)
						gp.ui.commandNum = 0;
				}
				if (code == KeyEvent.VK_RIGHT) {
					gp.ui.commandNum++;
					
					if (gp.ui.commandNum == 28 && gp.ui.playerName.length() < 1) 
						gp.ui.commandNum = 27;
									
					if (gp.ui.commandNum > 28)
						gp.ui.commandNum = 28;
				}				
				if (code == KeyEvent.VK_UP) {
					if (gp.ui.commandNum >= 10 && gp.ui.commandNum <= 18) 
						gp.ui.commandNum -= 10;					
					else if (gp.ui.commandNum >= 19 && gp.ui.commandNum <= 25) 
						gp.ui.commandNum -= 9;	
					else if (gp.ui.commandNum >= 26)
						gp.ui.commandNum = 19;
				}
				if (code == KeyEvent.VK_DOWN) {					
					if (gp.ui.commandNum >= 0 && gp.ui.commandNum <= 8) 
						gp.ui.commandNum += 10;					
					else if (gp.ui.commandNum >= 9 && gp.ui.commandNum <= 17) 
						gp.ui.commandNum += 9;	
					else if (gp.ui.commandNum == 18)
						gp.ui.commandNum += 8;
					else if (gp.ui.commandNum >= 19 && gp.ui.commandNum <= 26)
						gp.ui.commandNum = 27;
					
				}
				if (code == KeyEvent.VK_SPACE) {
						
					if (gp.ui.commandNum == 26) {
						if (gp.ui.playerName.length() > 0)
							gp.ui.playerName = gp.ui.playerName.substring(
								0, gp.ui.playerName.length() - 1
							); 
					}					
					else if (gp.ui.commandNum == 27) {
						gp.ui.commandNum = 0;
						gp.ui.titleScreenState = 0;
						gp.ui.playerName = "";
					}
					else if (gp.ui.commandNum == 28) {
						gp.gameState = 1;
						gp.playMusic(0);						
					}
					else {
						if (gp.ui.playerName.length() < 11)
							gp.ui.playerName += keyboard.get(gp.ui.commandNum);
					}
				}				
				
			}
		}
		// PLAY STATE
		else {		
			
			if (gp.gameState == gp.playState) {
				
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






