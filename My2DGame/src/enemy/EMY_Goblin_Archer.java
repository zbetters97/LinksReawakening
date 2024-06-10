package enemy;

import java.awt.Rectangle;
import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.COL_Arrow;
import object.COL_Heart;
import object.COL_Rupee_Blue;
import object.COL_Rupee_Red;
import object.ITM_Bow;

public class EMY_Goblin_Archer extends Entity {

	GamePanel gp;
	
	public EMY_Goblin_Archer(GamePanel gp) {
		super(gp);				
		this.gp = gp;
		
		type = type_enemy;
		name = "Archer Goblin";
		speed = 1; defaultSpeed = speed; 
		animationSpeed = 10;
		attack = 2; defense = 0;
		knockbackPower = 0;
		exp = 8;
		maxLife = 9; life = maxLife;
						
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
	
	// RUN AWAY WHEN HIT
	public void damageReaction() {
		actionLockCounter = 0;
		direction = gp.player.direction; 
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