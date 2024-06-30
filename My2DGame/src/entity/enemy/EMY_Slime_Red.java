package entity.enemy;

import java.awt.Rectangle;
import java.util.Random;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Heart;
import entity.collectable.COL_Rupee_Blue;

public class EMY_Slime_Red extends Entity {

	public static final String emyName = "Red Slime";
	GamePanel gp;
	
	public EMY_Slime_Red(GamePanel gp) {
		super(gp);			
		this.gp = gp;
		
		type = type_enemy;
		name = emyName;
		
		speed = 0; defaultSpeed = speed;
		animationSpeed = 12;
		attack = 2; 
		knockbackPower = 1;
		maxLife = 8; life = maxLife;
		
		hitbox = new Rectangle(2, 18, 44, 30);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/enemy/slime_red_down_1");
		up2 = setup("/enemy/slime_red_down_2");
		up3 = setup("/enemy/slime_red_down_3");
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
			isOffPath(gp.player, 6);
			if (onPath) {
				searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
			}
		}
		else {				
			isOnPath(gp.player, 4);
			if (onPath) {
				searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
				isOffPath(gp.player, 8);
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
		
		if (i < 50) dropItem(new COL_Heart(gp));
		else dropItem(new COL_Rupee_Blue(gp));
	}
}