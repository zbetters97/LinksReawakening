package tile.tile_interactive;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import application.GamePanel;
import entity.Entity;

public class InteractiveTile extends Entity {
	
	GamePanel gp;
	
	public InteractiveTile(GamePanel gp, int col, int row) {
		super(gp);
		this.gp = gp;
		collision = true;		
	}
	
	public void update() {
		
		if (grabbed) {
			worldX = gp.player.worldX;
			worldY = gp.player.worldY - gp.tileSize + 5;
			collision = false;
			xT = worldX;
			yT = worldY;
		}		
		if (thrown) {		
			
			if (tossEntity()) {
				int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
				if (enemyIndex == -1) enemyIndex = gp.cChecker.checkEntity(this, gp.enemy_r); 
				gp.player.damageEnemy(enemyIndex, this, 2, 2);
				
				playSE();				
				checkDrop();
				generateParticle(this, this);
				throwCounter = 0;
				tTime = 0;
				alive = false;
			}
		}
		
		// SHIELD AFTER HIT
		if (invincible) {
			invincibleCounter++;
			
			// 1 SECOND REFRESH TIME 
			if (invincibleCounter > 40) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	
	public boolean correctItem(Entity entity) {		
		boolean correctItem = false;		
		return correctItem;
	}
	
	public void playSE() {
		
	}
	
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = null;
		return tile;
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		boolean offCenter = false;
		
		if (getScreenX() > worldX) {
			screenX = worldX;
			offCenter = true;
		}
		if (getScreenY() > worldY) {
			screenY = worldY;
			offCenter = true;
		}
		
		// FROM PLAYER TO RIGHT-EDGE OF SCREEN
		int rightOffset = gp.screenWidth - getScreenX();		
		
		// FROM PLAYER TO RIGHT-EDGE OF WORLD
		if (rightOffset > gp.worldWidth - worldX) {
			screenX = gp.screenWidth - (gp.worldWidth - worldX);
			offCenter = true;
		}			
		
		// FROM PLAYER TO BOTTOM-EDGE OF SCREEN
		int bottomOffSet = gp.screenHeight - getScreenY();
		
		// FROM PLAYER TO BOTTOM-EDGE OF WORLD
		if (bottomOffSet > gp.worldHeight - worldY) {
			screenY = gp.screenHeight - (gp.worldHeight - worldY);
			offCenter = true;
		}
		
		if (thrown) {
			g2.setColor(Color.BLACK);
			g2.fillOval(screenX + 10, screenY + 40, 30, 10);
		}
		
		if (direction.equals("up")) image = up1;
		else if (direction.equals("down")) image = down1;
		else if (direction.equals("left")) image = left1;
		else if (direction.equals("right")) image = right1;
		
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {				
			
			if (thrown) {
				
				g2.setColor(Color.BLACK);
				switch (direction) {
					case "up": 	
						g2.fillOval(screenX + 5, screenY + 70 - throwCounter, 38, 10);
						break;
					case "down": 			
						g2.fillOval(screenX + 5, screenY + 70 - throwCounter, 38, 10);
						break;
					case "left": 				
						break;
					case "right": 
						break;
				}	
			}
			
			g2.drawImage(image, screenX, screenY, null);
		}
		else if (offCenter) {
			g2.drawImage(image, screenX, screenY, null);
		}
	}
}