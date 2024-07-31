package entity.enemy;

import java.awt.Rectangle;
import java.util.Random;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Heart;
import entity.collectable.COL_Rupee_Green;

public class EMY_ChuChu_Green extends Entity {

	public static final String emyName = "Green ChuChu";
	
	public EMY_ChuChu_Green(GamePanel gp, int worldX, int worldY) {
		super(gp);		
		this.worldX = worldX * gp.tileSize;
		this.worldY = worldY * gp.tileSize;	
		worldXStart = this.worldX;
		worldYStart = this.worldY;
		
		type = type_enemy;
		name = emyName;
		capturable = true;
		
		maxLife = 6; life = maxLife;
		speed = 0; defaultSpeed = speed;
		animationSpeed = 13;
		attack = 2; 
		knockbackPower = 0;
		
		hitbox = new Rectangle(2, 18, 44, 30);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {	
		up1 = setup("/enemy/chuchu_green_down_1");
		up2 = setup("/enemy/chuchu_green_down_2");
		up3 = setup("/enemy/chuchu_green_down_3");
		down1 = up1;
		down2 = up2;
		down3 = up3;
		left1 = up1;
		left2 = up2;
		left3 = up3;
		right1 = up1;
		right2 = up2;
		right3 = up3;
	}
	
	public void cycleSprites() {
				
		spriteCounter++;
		if (spriteCounter > animationSpeed && animationSpeed != 0) {
			
			if (onPath || captured) {
				speed = 1;
				if (spriteNum == 1 || spriteNum == 2) spriteNum = 3;
				else if (spriteNum == 3) spriteNum = 2;	
			}
			else {			
				spriteNum = 1;
				speed = defaultSpeed;
			}
			
			spriteCounter = 0;
		}		
	}
	
	public void setAction() {
				
		if (!captured) {
			
			isOffPath(gp.player, 6);
			if (onPath && playerWithinBounds()) {	
				searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
			}
			else {				
				
				// SEARCH FOR PLAYER IF WITHIN BOUNDS
				if (playerWithinBounds()) {
					isOnPath(gp.player, 4);
				}
				else {
					onPath = false;
				}
			}
		}
	}
	
	public void attacking() {
		attacking = false;
	}
	
	// ONLY FOLLOW PLAYER WHEN HIT
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
		if (i < 70) dropItem(new COL_Heart(gp));
		else dropItem(new COL_Rupee_Green(gp));
	}
}