package entity.enemy;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Heart;
import entity.projectile.PRJ_Beam;

public class EMY_Beamos extends Entity {

	public static final String emyName = "Beamos";
	private boolean playerFound = false;
	
	public EMY_Beamos(GamePanel gp, int worldX, int worldY) {
		super(gp);			
		this.worldX = worldX * gp.tileSize;
		this.worldY = worldY * gp.tileSize;	
		direction = "down";
		
		type = type_enemy;
		name = emyName;
		canTarget = false;
		
		maxLife = 12; life = maxLife;
		speed = 0; defaultSpeed = speed;
		animationSpeed = 15;
		attack = 0;
		knockbackPower = 0;
		
		projectile = new PRJ_Beam(gp);
	}
	
	public void getImage() {
		up1 = setup("/enemy/beamos_up_1");
		up2 = setup("/enemy/beamos_up_2");
		down1 = setup("/enemy/beamos_down_1");
		down2 = setup("/enemy/beamos_down_2");
		left1 = setup("/enemy/beamos_left_1");
		left2 = setup("/enemy/beamos_left_2");
		right1 = setup("/enemy/beamos_right_1");
		right2 = setup("/enemy/beamos_right_2");
	}
	
	public void update() {
						
		findPlayer();
		
		// PLAYER FOUND, STOP AND SHOOT PROJECTILE
		if (playerFound) {	
			playerFound = false;
			spriteNum = 1;
			spriteCounter = 0;
			useProjectile(15);			
		}
		else {
			cycleSprites();
		}
		
		manageValues();
	}		
	
	public void cycleSprites() {
		spriteCounter++;
		
		if (spriteCounter == animationSpeed) {		
			spriteNum = 2;
		}
		else if (spriteCounter >= animationSpeed * 2) {
			getDirection();	
			spriteNum = 1;
			spriteCounter = 0;
		}
	}
	
	// FIND NEXT DIRECTION IN ROTATION
	public void getDirection() {
		switch (direction) {			
			case "down":
				direction = "left";
				break;
			case "left":
				direction = "up";
				break;
			case "up":
				direction = "right";
				break;
			case "right":
				direction = "down";
				break;
		}	
	}
	
	private void findPlayer() {
	
		// PLAYER NOT ALREADY FOUND
		if (!playerFound) {		
			
			// PLAYER WITHIN 8 TILES
			isOnPath(gp.player, 8);			
			if (onPath) {			
				
				isOffPath(gp.player, 8);
				
				// FIND IF PLAYER IS IN SIGHTS				
				switch (direction) {
					case "up":
						if (Math.abs(worldX - gp.player.worldX) <= gp.tileSize) {
							if (worldY - gp.player.worldY >= 0) {
								playerFound = true;
							}
						}
						break;
					case "down":
						if (Math.abs(worldX - gp.player.worldX) <= gp.tileSize) {
							if (worldY - gp.player.worldY < 0) {								
								playerFound = true;
							}
						}
						break;
					case "left":
						if (Math.abs(worldY - gp.player.worldY) <= gp.tileSize) {
							if (worldX - gp.player.worldX >= 0) {
								playerFound = true;
							}	
						}		
						break;
					case "right":
						if (Math.abs(worldY - gp.player.worldY) <= gp.tileSize) {
							if (worldX - gp.player.worldX < 0) {								
								playerFound = true;
							}
						}		
						break;
				}
			}
			else {
				playerFound = false;
			}
		}
	}
	
	public void playHurtSE() {
		gp.playSE(3, 0);
	}
	public void playDeathSE() {
		gp.playSE(3, 2);
	}
	
	// DROPPED ITEM
	public void checkDrop() {
		super.checkDrop();		
		dropItem(new COL_Heart(gp));
	}
}