package entity.enemy;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Heart;
import entity.projectile.PRJ_Magic;

public class EMY_Wizzrobe extends Entity {

	public static final String emyName = "Wizzrobe";
	
	private int teleportCounter = 0;
	
	public EMY_Wizzrobe(GamePanel gp, int worldX, int worldY) {
		super(gp);			
		this.worldX = worldX * gp.tileSize;
		this.worldY = worldY * gp.tileSize;
		worldXStart = this.worldX;
		worldYStart = this.worldY;
		
		type = type_enemy;
		name = emyName;
		collision = false;
		capturable = true;		
		
		maxLife = 16; life = maxLife;
		speed = 2; defaultSpeed = speed; 
		animationSpeed = 10;
		
		projectile = new PRJ_Magic(gp);
		
		hitbox = new Rectangle(8, 16, 32, 32); 
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {
		up1 = setup("/enemy/wizzrobe_up_1");
		up2 = down2 = left2 = right2 = setup("/enemy/wizzrobe_down_2");		
		down1 = setup("/enemy/wizzrobe_down_1");
		left1 = setup("/enemy/wizzrobe_left_1");
		right1 = setup("/enemy/wizzrobe_right_1");
	}	
	
	// OVERRIDE ENTITY UPDATE
	public void update() {	
				
		if (sleep) return;		
		if (knockback) { knockbackEntity();	manageValues(); return; }	
		if (captured) { isCaptured(); manageValues(); return; }
		if (teleporting) { teleport(); }		
		else { attack(); }		
		
		manageValues();
	}
	
	public void cycleSprites() {
		
		if (captured) {
			spriteNum = 2;
		}
		else {		
			spriteCounter++;
			if (spriteCounter > animationSpeed && animationSpeed != 0) {
				
				if (spriteNum == 1) spriteNum = 2;
				else if (spriteNum == 2) spriteNum = 1;
				
				spriteCounter = 0;
			}
		}
	}
	
	public void teleport() {
		
		// TELEPORTING ANIMATION
		spriteCounter++;
		if (animationSpeed >= spriteCounter) spriteNum = 2;
		else spriteNum = 4;	
		
		if (spriteNum == 4) {	
			
			invincible = true;
			
			if (locked) {
				gp.player.lockon = false;
				gp.player.lockedTarget = null;				
				locked = false;
			}
			
			// MOVE IN RANDOM DIRECTION
			getDirection(30);			
			checkCollision();				
			if (!collisionOn) { 							
				switch (direction) {
					case "up": worldY -= speed; break;					
					case "down": worldY += speed; break;					
					case "left": worldX -= speed; break;
					case "right": worldX += speed; break;
				}
			}
			
			// RE-APPEAR AFTER 2 SECONDS
			teleportCounter++;
			if (teleportCounter > 120) {
				spriteNum = 4;
				spriteCounter = 0;
				teleporting = false;				
				teleportCounter = 0;
			}
		}	
	}
	
	private void attack() {
		
		// TELEPORTING ANIMATION
		spriteCounter++;
		if (animationSpeed >= spriteCounter) spriteNum = 2;
		else spriteNum = 1;	
		
		if (spriteNum == 1) {
			
			invincible = false;
			
			// FIND PLAYER AND SHOOT PROJECTILE
			if (onPath) {
				approachPlayer(10);
				if (teleportCounter == 45) {
					useProjectile(1);
				}
				isOffPath(gp.player, 12);
			}
			else {
				if (playerWithinBounds()) {
					isOnPath(gp.player, 10);	
				}
				else {
					onPath = false;
				}
			}

			// DISAPEAR AFTER 3 SECONDS
			teleportCounter++;
			if (teleportCounter > 120) {	
				spriteNum = 2;
				spriteCounter = 0;
				teleporting = true;					
				teleportCounter = 0;
			}
		}
	}
	
	public void attacking() {
		attacking = false;
		spriteNum = 1;
		useProjectile(1);
	}
	
	// TELEPORT IF HIT
	public void damageReaction() {
		spriteNum = 2;
		spriteCounter = 0;
		teleporting = true;					
		teleportCounter = 0;	
	}
	
	public void playTeleportSE() {
		gp.playSE(3, 7);
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