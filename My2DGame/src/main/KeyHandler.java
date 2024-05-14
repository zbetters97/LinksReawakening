package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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






