package entity.enemy;

import java.awt.Rectangle;
import java.util.Random;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Heart;
import entity.collectable.COL_Rupee_Blue;

public class EMY_Slime_Green extends Entity {

	public static final String emyName = "Green Slime";
	GamePanel gp;
	
	public EMY_Slime_Green(GamePanel gp) {
		super(gp);			
		this.gp = gp;
		
		type = type_enemy;
		name = emyName;
		
		speed = 0; defaultSpeed = speed;
		animationSpeed = 13;
		attack = 1; 
		knockbackPower = 1;
		maxLife = 6; life = maxLife;
		
		hitbox = new Rectangle(2, 18, 44, 30);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		getImage();
	}
	
	public void getImage() {	
		up1 = setup("/enemy/greenslime_down_1");
		up2 = setup("/enemy/greenslime_down_2");
		up3 = setup("/enemy/greenslime_down_3");
		down1 = setup("/enemy/greenslime_down_1");
		down2 = setup("/enemy/greenslime_down_2");
		down3 = setup("/enemy/greenslime_down_3");
		left1 = setup("/enemy/greenslime_down_1");
		left2 = setup("/enemy/greenslime_down_2");
		left3 = setup("/enemy/greenslime_down_3");
		right1 = setup("/enemy/greenslime_down_1");
		right2 = setup("/enemy/greenslime_down_2");
		right3 = setup("/enemy/greenslime_down_3");
	}
	
	public void cycleSprites() {
		spriteCounter++;
		if (spriteCounter > animationSpeed && animationSpeed != 0) {
			
			if (onPath) {
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
		
		if (onPath) {
			isOffPath(gp.player, 5);
			if (onPath) {
				searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
			}
		}
		else {				
			isOnPath(gp.player, 3);
			if (onPath) {
				searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
				isOffPath(gp.player, 8);
			}		
		}
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
		
		if (i < 50) dropItem(new COL_Heart(gp));
		else dropItem(new COL_Rupee_Blue(gp));
	}
}