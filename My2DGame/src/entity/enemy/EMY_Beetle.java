package entity.enemy;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Heart;

public class EMY_Beetle extends Entity {

	public static final String emyName = "Beetle";
	private int attackCounter = 0;
		
	public EMY_Beetle(GamePanel gp, int worldX, int worldY) {
		super(gp);		
		this.worldX = worldX * gp.tileSize;
		this.worldY = worldY * gp.tileSize;
		worldXStart = this.worldX;
		worldYStart = this.worldY;
		
		type = type_enemy;
		name = emyName;
		guarded = true;
		capturable = true;
		
		maxLife = 16; life = maxLife;		
		speed = 1; defaultSpeed = speed;
		animationSpeed = 15;
		attack = 2;
		knockbackPower = 1;
		
		hitbox = new Rectangle(8, 16, 32, 32); 
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {
		up1 = down1 = left1 = right1 = setup("/enemy/beetle_down_1");
		up2 = down2 = left2 = right2 = setup("/enemy/beetle_down_2");
	}
	public void getAttackImage() {
		attackUp1 = attackDown1 = attackLeft1 = attackRight1 = setup("/enemy/beetle_attack_down_1");
		attackUp2 = attackDown2 = attackLeft2 = attackRight2 = setup("/enemy/beetle_attack_down_2");
	}
	
	public void setAction() {		
		
		if (!captured && !attacking) {
			
			isOffPath(gp.player, 7);	
			if (onPath && playerWithinBounds()) {														
				searchPath(getGoalCol(gp.player), getGoalRow(gp.player));					
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
	public void attacking() {
		if (!captured) {
			guarded = false;
			attack = 0;
			speed = 0;
			
			attackCounter++;
			if (120 > attackCounter && attackCounter >= 60) {
				attackNum = 2;
			}
			else if (attackCounter >= 120) {
				guarded = true;
				attacking = false;
				attack = 2;
				speed = 1;
				attackNum = 1;
				attackCounter = 0;
			}
		}
		else {
			attacking = false;
		}		
	}
	
	// BAT CHASES PLAYER WHEN HIT
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
		dropItem(new COL_Heart(gp));
	}
}