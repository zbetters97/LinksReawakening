package enemy;

import java.awt.Rectangle;
import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.COL_Heart;
import object.COL_Rupee_Blue;
import object.PRJ_Fireball;

public class EMY_Slime_Green extends Entity {

	GamePanel gp;
	
	public EMY_Slime_Green(GamePanel gp) {
		super(gp);		
		
		this.gp = gp;
		
		type = type_enemy;
		name = "Green Slime";
		speed = 1; baseSpeed = speed;
		animationSpeed = 15;
		maxLife = 2; life = maxLife;
		attack = 3; defense = 0;
		exp = 4;
		
		projectile = new PRJ_Fireball(gp);
		
		// HIT BOX
		hitBox = new Rectangle(2, 18, 44, 30);
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/enemy/greenslime_down_1");
		up2 = setup("/enemy/greenslime_down_2");
		down1 = setup("/enemy/greenslime_down_1");
		down2 = setup("/enemy/greenslime_down_2");
		left1 = setup("/enemy/greenslime_down_1");
		left2 = setup("/enemy/greenslime_down_2");
		right1 = setup("/enemy/greenslime_down_1");
		right2 = setup("/enemy/greenslime_down_2");
	}
	
	public void setAction() {

		actionLockCounter++;
		
		if (actionLockCounter == 120) {		
						
			Random random = new Random();
			int i = random.nextInt(100) + 1; // random number 1-100
						
			if (i <= 25) direction = "up";
			if (i > 25 && i <= 50) direction = "down";
			if (i > 50 && i <= 75) direction = "left";
			if (i > 75) direction = "right";
			
			actionLockCounter = 0;
		}		

		// ~1 SHOT/2 SECONDS (120 frames)
		int i = new Random().nextInt(120) + 1;
		if (i > 119 && !projectile.alive && shotAvailableCounter == 30) {
			
			projectile.set(worldX, worldY, direction, true, this);
			gp.projectileList.add(projectile);
			
			shotAvailableCounter = 0;
			
			gp.playSE(3, 3);
		}
	}
	
	// RUN AWAY WHEN HIT
	public void damageReaction() {
		actionLockCounter = 0;
		direction = gp.player.direction; 
	}
	
	// DROPPED ITEM
	public void checkDrop() {
		
		int i = new Random().nextInt(100) + 1;
		
		if (i < 50) dropItem(new COL_Heart(gp));
		else dropItem(new COL_Rupee_Blue(gp));
	}
}