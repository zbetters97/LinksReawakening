package enemy;

import java.awt.Rectangle;
import java.util.Random;

import collectable.COL_Heart;
import collectable.COL_Rupee_Green;
import entity.Entity;
import main.GamePanel;

public class EMY_Bat extends Entity {

	GamePanel gp;
	
	public EMY_Bat(GamePanel gp) {
		super(gp);				
		this.gp = gp;
		
		type = type_enemy;
		name = "Bat";
		speed = 2; defaultSpeed = speed;
		animationSpeed = 5;
		attack = 2; defense = 0;
		knockbackPower = 1;
		exp = 2;
		maxLife = 6; life = maxLife;
		
		hitbox = new Rectangle(2, 18, 44, 30);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/enemy/bat_down_1");
		up2 = setup("/enemy/bat_down_2");
		down1 = setup("/enemy/bat_down_1");
		down2 = setup("/enemy/bat_down_2");
		left1 = setup("/enemy/bat_down_1");
		left2 = setup("/enemy/bat_down_2");
		right1 = setup("/enemy/bat_down_1");
		right2 = setup("/enemy/bat_down_2");
	}
	
	public void setAction() {
		
		if (onPath) {			
			isOffPath(gp.player, 10);				
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
		}
		else {				
			isOnPath(gp.player, 5);
			getDirection(25);
		}
	}
	
	// BAT CHASES PLAYER WHEN HIT
	public void damageReaction() {
		actionLockCounter = 0;
		onPath = true;
	}
	
	public void playHurtSE() {
		gp.playSE(4, 0);
	}
	public void playDeathSE() {
		gp.playSE(4, 2);
	}
	
	// DROPPED ITEM
	public void checkDrop() {
		
		int i = new Random().nextInt(100) + 1;
		
		if (i < 50) dropItem(new COL_Heart(gp));
		else dropItem(new COL_Rupee_Green(gp));
	}
}