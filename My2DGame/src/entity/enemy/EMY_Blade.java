package entity.enemy;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;

public class EMY_Blade extends Entity {

	public static final String emyName = "Blade";
	private boolean playerFound = false;
	private boolean returning = false;
	
	public EMY_Blade(GamePanel gp, int worldX, int worldY) {
		super(gp);			
		this.worldX = worldX * gp.tileSize;
		this.worldY = worldY * gp.tileSize;	
		worldXStart = this.worldX;
		worldYStart = this.worldY;
		direction = "down";
		
		type = type_enemy;
		name = emyName;
		guarded = true;
		canTarget = false;
		
		maxLife = 1; life = maxLife;
		speed = 5; defaultSpeed = speed;
		animationSpeed = 0;		
		attack = 4;
		knockbackPower = 1;		
		
		hitbox = new Rectangle(8, 8, 32, 32);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {
		up1 = down1 = left1 = right1 = setup("/enemy/blade_down_1");
	}
	
	// UPDATER
	public void update() {
		
		if (returning) {	
						
			// RETURNING TO STARTING POINT 
			if ((!(worldXStart - 3 <= worldX && worldX <= worldXStart + 3)) ||
					(!(worldYStart - 3 <= worldY && worldY <= worldYStart + 3))) {
				
				checkCollision();
				if (!collisionOn) { 	
					move();
				}
			}
			// RETURNED TO STARTING POINT
			else {
				returning = false;
				speed = defaultSpeed;
			}
		}
	
		else {				
			findPlayer();
			
			// PLAYER IN RANGE
			if (playerFound) {
				
				// MOVE TOWARDS PLAYER
				checkCollision();
				if (!collisionOn) { 											
					move();
				}			
				// COLLISION DETECTED, MOVE BACKWARDS
				else {			
					playSE();
					returning = true;
					playerFound = false;
					speed = 2;
					direction = getOppositeDirection(direction);					
				}
			}
		}		
	}
	
	private void findPlayer() {
	
		// PLAYER NOT ALREADY FOUND
		if (!playerFound) {		
			
			// PLAYER WITHIN 6 TILES
			isOnPath(gp.player, 6);			
			if (onPath) {			
				
				isOffPath(gp.player, 6);
				
				// DIRECTION OF PLAYER RELATIVE TO BLADE
				if (Math.abs(worldX - gp.player.worldX) <= gp.tileSize) {
					
					// PLAYER ABOVE
					if (worldY - gp.player.worldY >= 0) {
						direction = "up";
						if (pathOpen(direction)) playerFound = true;
					}	
					// PLAYER BELOW
					else if (worldY - gp.player.worldY < 0) {
						direction = "down";
						if (pathOpen(direction)) playerFound = true;
					}
				}
				else if (Math.abs(worldY - gp.player.worldY) <= gp.tileSize) {
					
					// PLAYER LEFT
					if (worldX - gp.player.worldX >= 0) {
						direction = "left";
						if (pathOpen(direction)) playerFound = true;
					}						
					// PLAYER RIGHT
					else if (worldX - gp.player.worldX < 0) {
						direction = "right";
						if (pathOpen(direction)) playerFound = true;
					}
				}		
				// PLAYER NOT CLOSE TO BLADE
				else {
					playerFound = false;
				}
			}
			// PLAYER NOT WITHIN 6 TILES
			else {
				playerFound = false;
			}
		}
	}
	
	// RETURN TRUE IF PATH TO PLAYER IS OPEN
	private boolean pathOpen(String direction) {		
		
		switch(direction) {
			case "up":
				for (int i = 0; i <= getTileDistance(gp.player); i++) {					
					int wX = worldX / gp.tileSize;
					int wY = (worldY - gp.tileSize * i) / gp.tileSize;
					if (tileHasCollision(wX, wY))
						return false;
				}
				break;
			case "down":
				for (int i = 0; i <= getTileDistance(gp.player); i++) {					
					int wX = worldX / gp.tileSize;
					int wY = (worldY + gp.tileSize * i) / gp.tileSize;
					if (tileHasCollision(wX, wY))
						return false;
				}
				break;
			case "left":				
				for (int i = 0; i <= getTileDistance(gp.player); i++) {					
					int wX = (worldX - gp.tileSize * i) / gp.tileSize;
					int wY = worldY / gp.tileSize;
					if (tileHasCollision(wX, wY))
						return false;
				}				
				break;
			case "right":
				for (int i = 0; i <= getTileDistance(gp.player); i++) {					
					int wX = (worldX + gp.tileSize * i) / gp.tileSize;
					int wY = worldY / gp.tileSize;
					if (tileHasCollision(wX, wY))
						return false;
				}
				break;
		}		
		
		return true;
	}
	
	// RETURN TRUE IF GIVEN TILE HAS COLLISION
	private boolean tileHasCollision(int wX, int wY) {
		
		boolean tileCollision = false;
		
		int tileNum = gp.tileM.mapTileNum[gp.currentMap][wX][wY];
		if (gp.tileM.tile[tileNum].collision || 
				gp.tileM.tile[tileNum].pit || 
				gp.tileM.tile[tileNum].water) {
			tileCollision = true;
		}
		
		return tileCollision;
	}
	
	public void playSE() {
		gp.playSE(4, 4);
	}
}