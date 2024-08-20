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
import entity.projectile.PRJ_Bone;

public class EMY_Stalfos extends Entity {

	public static final String emyName = "Stalfos";
	
	public EMY_Stalfos(GamePanel gp, int worldX, int worldY) {
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
		maxLife = 12; life = maxLife;
		attack = 2;
		knockbackPower = 0;		
		
		projectile = new PRJ_Bone(gp);	
		
		hitbox = new Rectangle(8, 16, 32, 32); 
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {
		up1 = down1 = left1 = right1 = setup("/enemy/stalfos_down_1");
		up2 = down2 = left2 = right2 = setup("/enemy/stalfos_down_2");
	}	
	
	public void getAttackImage() {
		attackUp1 = setup("/enemy/stalfos_attack_down_1");
		attackUp2 = attackUp1;
		attackDown1 = attackUp1;
		attackDown2 = attackUp1;
		attackLeft1 = attackUp1;
		attackLeft2 = attackUp1;
		attackRight1 = attackUp1;
		attackRight2 = attackUp1;
	}
	
	public void setAction() {
		
		if (!captured) {	
			
			// PLAYER SWINGS SWORD IN RANGE, NOT ALREADY JUMPING
			if (gp.keyH.bPressed && getTileDistance(gp.player) < 2 &&
					direction.equals(getOppositeDirection(gp.player.direction))) {
				
				action = Action.JUMPING;
				attacking = true;
				speed = 3;
				direction = getPlayerDirection();				
			}		
			else if (!attacking) {		
				speed = defaultSpeed;
				
				if (onPath) {	
					isOffPath(gp.player, 7);				
					if (onPath && playerWithinBounds()) {					
						searchPath(getGoalCol(gp.player), getGoalRow(gp.player));	
						useProjectile(60);
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
		else {
			attackCounter = 0;
			guarded = false;
			action = Action.IDLE;
		}
	}
	
	public void attacking() {		
		
		if (captured) {
			useProjectile(1);
			attackCounter = 0;
			attacking = false;
			guarded = false;
			action = Action.IDLE;
		}
		else {
			guarded = true;
		
			attackCounter++;		
			if (attackCounter >= 15) {
				attackCounter = 0;
				attacking = false;
				guarded = false;
				action = Action.IDLE;
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