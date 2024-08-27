package entity.enemy;

import java.awt.Rectangle;
import java.util.Random;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Heart;
import entity.collectable.COL_Rupee_Blue;

public class EMY_Buzzblob extends Entity {

	public static final String emyName = "Buzz Blob";
	private int cycle = 0;
	
	public EMY_Buzzblob(GamePanel gp, int worldX, int worldY) {
		super(gp);		
		this.worldX = worldX * gp.tileSize;
		this.worldY = worldY * gp.tileSize;	
		worldXStart = this.worldX;
		worldYStart = this.worldY;
		
		type = type_enemy;
		name = emyName;
		capturable = true;
		
		maxLife = 6; life = maxLife;
		speed = 1; defaultSpeed = speed;
		animationSpeed = 10;
		attack = 4; 
		knockbackPower = 0;
		
		hitbox = new Rectangle(8, 16, 32, 32); 
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	
		getBuzzImage();
	}
	
	public void getImage() {
		up1 = down1 = left1 = right1 = setup("/enemy/buzzblob_down_1");
		up2 = down2 = left2 = right2 = setup("/enemy/buzzblob_down_2");
		up3 = down3 = left3 = right3 = setup("/enemy/buzzblob_down_3");
		
	}
	public void getBuzzImage() {		
		attackUp1 = attackDown1 = attackLeft1 = attackRight1 = setup("/enemy/buzzblob_attack_down_1"); 
		attackUp2 = attackDown2 = attackLeft2 = attackRight2 = setup("/enemy/buzzblob_attack_down_2");	
	}
	
	public void cycleSprites() {
		
		spriteCounter++;
		if (spriteCounter > animationSpeed && animationSpeed != 0) {
			
			// 1 -> 2
			if (attacking) {
				if (attackNum == 1) attackNum = 2;
				else if (attackNum == 2) attackNum = 1;
			}
			// 1 -> 2 -> 3 -> 2 -> 1
			else {
				if (spriteNum == 1) spriteNum = 2;
				else if (spriteNum == 2 && cycle == 0) {
					spriteNum = 3;
					cycle++;
				}
				else if (spriteNum == 2 && cycle == 1) {
					spriteNum = 1;
					cycle = 0;
				}
				else if (spriteNum == 3) {
					spriteNum = 2;				
				}
			}
			
			spriteCounter = 0;
		}		
	}
	
	public void attacking() {
		
		// BUZZ FOR 2 SECONDS
		attackCounter++;
		if (attackCounter > 120) {
			attacking = false;
			buzzing = false;
			attackCounter = 0;
			attack = 2;
		}		
		else {
			buzzing = true;
		}
	}
	
	public void setAction() {
		
		if (!captured) {
			isOffPath(gp.player, 6);	
			if (onPath && playerWithinBounds()) {														
				searchPath(getGoalCol(gp.player), getGoalRow(gp.player));	
				if (!buzzing) {
					if (isAttacking(180, gp.tileSize * 3, gp.tileSize * 3)) {
						playShockSE();
						attacking = true;					
						attack *= 2;
					}
				}
			}
			else {		
				getDirection(60);		
				
				// SEARCH FOR PLAYER IF WITHIN BOUNDS
				if (playerWithinBounds()) {
					isOnPath(gp.player, 3);
				}
				else {
					onPath = false;
				}
				
				attack = 2;			
			}
		}
	}
	
	public void damageReaction() {
		actionLockCounter = 0;
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
		else dropItem(new COL_Rupee_Blue(gp));
	}
}