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
		
		maxLife = 1; life = maxLife;
		speed = 4; defaultSpeed = speed;
		animationSpeed = 0;		
		attack = 4;
		knockbackPower = 1;		
		
		hitbox = new Rectangle(8, 8, 32, 32);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
	}
	
	public void getImage() {
		up1 = setup("/enemy/blade_down_1");
		down1 = up1;
		left1 = up1;
		right1 = up1;
	}
	
	// UPDATER
	public void update() {
				
		if (returning) {	
			
			// RETURN TO STARTING POINT
			if (worldX != worldXStart || worldY != worldYStart) {
				
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
					if (worldY - gp.player.worldY >= 0) {
						direction = "up";
						playerFound = true;
					}	
					else if (worldY - gp.player.worldY < 0) {
						direction = "down";
						playerFound = true;
					}
				}
				else if (Math.abs(worldY - gp.player.worldY) <= gp.tileSize) {
					if (worldX - gp.player.worldX >= 0) {
						direction = "left";
						playerFound = true;
					}	
					else if (worldX - gp.player.worldX < 0) {
						direction = "right";
						playerFound = true;
					}
				}		
				else {
					playerFound = false;
				}
			}
			else {
				playerFound = false;
			}
		}
	}
}