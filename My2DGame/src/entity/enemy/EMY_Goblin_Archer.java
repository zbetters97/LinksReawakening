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

public class EMY_Goblin_Archer extends Entity {

	public static final String emyName = "Archer Goblin";
	GamePanel gp;
	
	public EMY_Goblin_Archer(GamePanel gp) {
		super(gp);				
		this.gp = gp;
		
		type = type_enemy;
		name = emyName;
		
		speed = 1; defaultSpeed = speed; 
		animationSpeed = 10;
		attack = 1;
		knockbackPower = 0;
		maxLife = 6; life = maxLife;
						
		hitbox = new Rectangle(8, 16, 32, 32); 
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		arrows = -1;
		currentItem = new ITM_Bow(gp);
		
		getImage();
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
		
		if (onPath) {			
			isOffPath(gp.player, 10);									
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
			useItem(120);
		}
		else {				
			isOnPath(gp.player, 6);
			getDirection(60);
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