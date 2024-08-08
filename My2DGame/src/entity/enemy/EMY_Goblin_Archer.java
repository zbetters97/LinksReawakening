package entity.enemy;

import java.awt.Rectangle;
import java.util.Random;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Arrow;
import entity.collectable.COL_Heart;
import entity.collectable.COL_Rupee_Blue;
import entity.item.ITM_Bow;
import entity.projectile.PRJ_Spear;

public class EMY_Goblin_Archer extends Entity {

	public static final String emyName = "Archer Goblin";
	
	public EMY_Goblin_Archer(GamePanel gp, int worldX, int worldY) {
		super(gp);	
		this.worldX = worldX * gp.tileSize;
		this.worldY = worldY * gp.tileSize;	
		worldXStart = this.worldX;
		worldYStart = this.worldY;
		
		type = type_enemy;
		name = emyName;
		capturable = true;
		
		speed = 1; defaultSpeed = speed; 
		animationSpeed = 10;
		maxLife = 8; life = maxLife;
		attack = 2;
		knockbackPower = 0;		
		
		projectile = new PRJ_Spear(gp);
		
		hitbox = new Rectangle(8, 16, 32, 32); 
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {
		up1 = setup("/enemy/goblin_up_1");
		up2 = setup("/enemy/goblin_up_2");
		down1 = setup("/enemy/goblin_down_1");
		down2 = setup("/enemy/goblin_down_2");
		left1 = setup("/enemy/goblin_left_1");
		left2 = setup("/enemy/goblin_left_2");
		right1 = setup("/enemy/goblin_right_1");
		right2 = setup("/enemy/goblin_right_2");
	}	
	
	public void setAction() {
		
		if (!captured) {
			
			if (onPath) {					
				isOffPath(gp.player, 10);	
				
				if (onPath && playerWithinBounds()) {					
					searchPath(getGoalCol(gp.player), getGoalRow(gp.player));		
					useProjectile(150);
				}
				else if (onPath) {
					useProjectile(150);
				}
			}
			else {					
				getDirection(60);
				
				// SEARCH FOR PLAYER IF WITHIN BOUNDS
				if (playerWithinBounds()) {
					isOnPath(gp.player, 7);
				}
				else {
					onPath = false;
				}	
			}
		}
	}
	
	public void attacking() {
		useProjectile(1);
		attacking = false;
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
		
		if (gp.player.inventory.contains(new ITM_Bow(gp))) {
			dropItem(new COL_Arrow(gp));
		}
		else {
			if (i < 50) dropItem(new COL_Heart(gp));
			if (i >= 50 && i < 90) dropItem(new COL_Rupee_Blue(gp));
			if (i >= 90 && i <= 100) dropItem(new COL_Rupee_Blue(gp));
		}
	}
}