package enemy;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Arrow;
import object.OBJ_Heart;

public class EMY_Bat extends Entity {

	GamePanel gp;
	
	public EMY_Bat(GamePanel gp) {
		super(gp);		
		
		this.gp = gp;
		
		type = type_enemy;
		name = "Bat";
		speed = 2;
		baseSpeed = speed;
		animationSpeed = 5;
		maxLife = 1;
		life = maxLife;
		attack = 2;
		defense = 0;
		exp = 2;
		
		// HIT BOX
		hitBox.x = 2;
		hitBox.y = 18;
		hitBox.width = 44;
		hitBox.height = 30;
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
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

		actionLockCounter++;
		
		if (actionLockCounter == 25) {		
						
			Random random = new Random();
			int i = random.nextInt(100) + 1; // random number 1-100
						
			if (i <= 25) direction = "up";
			if (i > 25 && i <= 50) direction = "down";
			if (i > 50 && i <= 75) direction = "left";
			if (i > 75) direction = "right";
			
			actionLockCounter = 0;
		}
	}
	
	// SLIME RUNS AWAY WHEN HIT
	public void damageReaction() {
		actionLockCounter = 0;
		direction = gp.player.direction; 
	}
	
	// DROPPED ITEM
	public void checkDrop() {
		
		int i = new Random().nextInt(100) + 1;
		
		if (i < 50) 
			dropItem(new OBJ_Heart(gp));
		if (i >= 50)
			dropItem(new OBJ_Arrow(gp));
	}
}