package entity.enemy;

import java.util.Random;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Heart;
import entity.collectable.COL_Rupee_Blue;
import entity.collectable.COL_Rupee_Green;
import entity.projectile.PRJ_Magic;

public class EMY_Wizzrobe extends Entity {

	public static final String emyName = "Wizzrobe";
	GamePanel gp;
	
	private int teleportCounter = 0;
	
	public EMY_Wizzrobe(GamePanel gp) {
		super(gp);				
		this.gp = gp;
		
		type = type_enemy;
		name = emyName;
		
		collision = false;
		
		speed = 2; defaultSpeed = speed; 
		animationSpeed = 10;
		maxLife = 8; life = maxLife;
		
		projectile = new PRJ_Magic(gp);
		
		getImage();
	}
	public EMY_Wizzrobe(GamePanel gp, int worldX, int worldY) {
		super(gp);				
		this.gp = gp;
		this.worldX = worldX * 48;
		this.worldY = worldY * 48;
		worldXStart = this.worldX;
		worldYStart = this.worldY;
		bounds = 6;
		
		type = type_enemy;
		name = emyName;
		
		collision = false;
		
		speed = 2; defaultSpeed = speed; 
		animationSpeed = 10;
		maxLife = 8; life = maxLife;
		
		projectile = new PRJ_Magic(gp);
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/enemy/wizzrobe_up_1");
		up2 = setup("/enemy/wizzrobe_down_2");		
		down1 = setup("/enemy/wizzrobe_down_1");
		down2 = up2;		
		left1 = setup("/enemy/wizzrobe_left_1");
		left2 = up2;
		right1 = setup("/enemy/wizzrobe_right_1");
		right2 = up2;
	}	
	
	// OVERRIDE ENTITY UPDATE
	public void update() {	
				
		if (sleep) return;		
		if (knockback) { knockbackEntity();	return; }		
		if (teleporting) { teleport(); }		
		else { attack(); }		
		
		manageValues();
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
				isOffPath(gp.player, 12);
				approachPlayer(10);
				if (teleportCounter == 45) {
					useProjectile(1);
				}
			}
			else {
				isOnPath(gp.player, 10);	
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
	
	// TELEPORT IF HIT
	public void damageReaction() {
		spriteNum = 2;
		spriteCounter = 0;
		teleporting = true;					
		teleportCounter = 0;		
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
		
		int i = new Random().nextInt(100) + 1;

		if (i < 50) dropItem(new COL_Heart(gp));
		if (i >= 50 && i < 90) dropItem(new COL_Rupee_Green(gp));
		if (i >= 90 && i <= 100) dropItem(new COL_Rupee_Blue(gp));		
	}
}