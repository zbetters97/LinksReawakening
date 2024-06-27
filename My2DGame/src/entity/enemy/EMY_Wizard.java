package entity.enemy;

import java.awt.Rectangle;
import java.util.Random;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Arrow;
import entity.collectable.COL_Heart;
import entity.collectable.COL_Rupee_Blue;
import entity.collectable.COL_Rupee_Red;
import entity.item.ITM_Bow;
import entity.projectile.PRJ_Magic;

public class EMY_Wizard extends Entity {

	public static final String emyName = "Wizard";
	GamePanel gp;
	private boolean teleporting = false;
	
	public EMY_Wizard(GamePanel gp) {
		super(gp);				
		this.gp = gp;
		
		type = type_enemy;
		name = emyName;
		
		speed = 1; defaultSpeed = speed; 
		animationSpeed = 0;
		attack = 0;
		knockbackPower = 0;
		maxLife = 6; life = maxLife;
						
		hitbox = new Rectangle(8, 16, 32, 32); 
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		projectile = new PRJ_Magic(gp);
		
		getImage();
	}
	
	public void getImage() {
		down1 = setup("/enemy/wizard_down_1");
		down2 = setup("/enemy/wizard_down_2");
		up1 = setup("/enemy/wizard_up_1");
		up2 = down2;		
		left1 = setup("/enemy/wizard_left_1");
		left2 = down2;
		right1 = setup("/enemy/wizard_right_1");
		right2 = down2;
	}	
	
	public void update() {	
		
		setAction();
		
		if (teleporting) {
			
			checkCollision();
			if (!collisionOn) { 
							
				switch (direction) {
					case "up": worldY -= speed; break;
					case "upleft": worldY -= speed - 1; worldX -= speed - 1; break;
					case "upright": worldY -= speed - 1; worldX += speed - 1; break;
					
					case "down": worldY += speed; break;
					case "downleft": worldY += speed - 1; worldX -= speed - 1; break;
					case "downright": worldY += speed; worldX += speed - 1; break;
					
					case "left": worldX -= speed; break;
					case "right": worldX += speed; break;
				}
			}	
		}
		
		manageValues();
	}
	
	public void setAction() {
		
		if (teleporting) {
			
			// SEARCH FOR PLAYER
			if (onPath) {			
				isOffPath(gp.player, 12);									
				searchPath(getGoalCol(gp.player), getGoalRow(gp.player));		
			}
			// PLAYER NOT FOUND
			else {				
				isOnPath(gp.player, 8);
				getDirection(60);
			}
			
			// STOP TELEPORTING
			int i = new Random().nextInt(200) + 1;
			if (i == 1) {
				teleporting = false;
				collision = true;
				spriteNum = 1;
				spriteCounter = 0;
			}
		}		
		// WIZARD NOT MOVING
		else {
			
			// LOOK AT PLAYER AND SHOOT
			approachPlayer(60);
			useProjectile(120);
			
			// TELEPORT
			int i = new Random().nextInt(300) + 1;
			if (i == 1 || spriteNum == 2) {
				teleport();
			}
		}
	}
	
	public void teleport() {
		
		spriteCounter++;
		if (spriteCounter > animationSpeed && !teleporting) {
			
			if (spriteNum == 1) spriteNum = 2;
			else if (spriteNum == 2) {
				spriteNum = 3;
				teleporting = true;
			}			
		}
	}
	
	public void damageReaction() {
		actionLockCounter = 0;
		onPath = true;
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
		
		if (gp.player.inventory.contains(new ITM_Bow(gp))) {
			if (i < 50) dropItem(new COL_Heart(gp));
			if (i >= 50 && i < 90) dropItem(new COL_Arrow(gp));
			if (i >= 90 && i <= 100) dropItem(new COL_Rupee_Red(gp));	
		}
		else {
			if (i < 50) dropItem(new COL_Heart(gp));
			if (i >= 50 && i < 90) dropItem(new COL_Rupee_Blue(gp));
			if (i >= 90 && i <= 100) dropItem(new COL_Rupee_Red(gp));
		}
	}
}