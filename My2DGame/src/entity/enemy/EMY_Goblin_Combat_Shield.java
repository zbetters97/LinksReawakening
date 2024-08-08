package entity.enemy;

import java.awt.Rectangle;
import java.util.Random;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Heart;
import entity.collectable.COL_Rupee_Blue;
import entity.collectable.COL_Rupee_Red;

public class EMY_Goblin_Combat_Shield extends Entity {

	public static final String emyName = "Combat Shield Goblin";
	
	public EMY_Goblin_Combat_Shield(GamePanel gp, int worldX, int worldY) {
		super(gp);		
		this.worldX = worldX * gp.tileSize;
		this.worldY = worldY * gp.tileSize;
		worldXStart = this.worldX;
		worldYStart = this.worldY;
		
		type = type_enemy;
		name = emyName;
		capturable = true;
		
		maxLife = 12; life = maxLife;
		speed = 1; defaultSpeed = speed; 
		animationSpeed = 10;
		attack = 6; 
		knockbackPower = 1;		
		
		hitbox = new Rectangle(8, 16, 32, 32); 
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
		
		swingSpeed1 = 30;
		swingSpeed2 = 60;		
		
		attackbox.width = 42;
		attackbox.height = 42;
	}
	
	public void getImage() {
		up1 = setup("/enemy/goblin_shield_up_1");
		up2 = setup("/enemy/goblin_shield_up_2");
		down1 = setup("/enemy/goblin_shield_down_1");
		down2 = setup("/enemy/goblin_shield_down_2");
		left1 = setup("/enemy/goblin_shield_left_1");
		left2 = setup("/enemy/goblin_shield_left_2");
		right1 = setup("/enemy/goblin_shield_right_1");
		right2 = setup("/enemy/goblin_shield_right_2");
	}	
	public void getAttackImage() {		
		attackUp1 = setup("/enemy/goblin_shield_attack_up_1", gp.tileSize, gp.tileSize * 2); 
		attackUp2 = setup("/enemy/goblin_shield_attack_up_2", gp.tileSize, gp.tileSize * 2);		
		attackDown1 = setup("/enemy/goblin_shield_attack_down_1", gp.tileSize, gp.tileSize * 2); 
		attackDown2 = setup("/enemy/goblin_shield_attack_down_2", gp.tileSize, gp.tileSize * 2);
		
		attackLeft1 = setup("/enemy/goblin_shield_attack_left_1", gp.tileSize * 2, gp.tileSize); 
		attackLeft2 = setup("/enemy/goblin_shield_attack_left_2", gp.tileSize * 2, gp.tileSize);		
		attackRight1 = setup("/enemy/goblin_shield_attack_right_1", gp.tileSize * 2, gp.tileSize); 
		attackRight2 = setup("/enemy/goblin_shield_attack_right_2", gp.tileSize * 2, gp.tileSize);			
	}
	
	public void setAction() {
				
		if (!captured) {
			
			if (onPath) {					
				isOffPath(gp.player, 8);				
				if (onPath && playerWithinBounds()) {					
					searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
					if (!attacking) {
						if (isAttacking(60, gp.tileSize * 3, gp.tileSize))
							attacking = true;
					}	
				}
			}
			else {				
				getDirection(60);
				
				// SEARCH FOR PLAYER IF WITHIN BOUNDS
				if (playerWithinBounds()) {
					isOnPath(gp.player, 5);
				}
				else {
					onPath = false;
				}
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
		
		if (i < 60) dropItem(new COL_Heart(gp));
		if (i >= 60 && i < 80) dropItem(new COL_Rupee_Blue(gp));
		if (i >= 80 && i <= 100) dropItem(new COL_Rupee_Red(gp));
	}
}