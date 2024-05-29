package enemy;

import java.awt.Rectangle;
import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.*;

public class EMY_Goblin extends Entity {

	GamePanel gp;
	
	public EMY_Goblin(GamePanel gp) {
		super(gp);		
		
		this.gp = gp;
		
		type = type_enemy;
		name = "Goblin";
		speed = 2;
		baseSpeed = speed;
		animationSpeed = 10;
		maxLife = 4;
		life = maxLife;
		attack = 4;
		defense = 0;
		exp = 8;
		projectile = new PRJ_Arrow(gp);
		
		// HIT BOX
		hitBox = new Rectangle(8, 16, 32, 32); 
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
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
	
	public void getGoblinAttackImage() {		
		attackUp1 = setup("/player/goblin_attack_up_1", gp.tileSize * 2, gp.tileSize * 2); 
		attackUp2 = setup("/player/goblin_attack_up_2", gp.tileSize, gp.tileSize * 2);		
		attackDown1 = setup("/player/goblin_attack_down_1", gp.tileSize * 2, gp.tileSize * 2); 
		attackDown2 = setup("/player/goblin_attack_down_2", gp.tileSize, gp.tileSize * 2);
		
		attackLeft1 = setup("/player/goblin_attack_left_1", gp.tileSize * 2, gp.tileSize * 2); 
		attackLeft2 = setup("/player/goblin_attack_left_2", gp.tileSize * 2, gp.tileSize);
		
		attackRight1 = setup("/player/goblin_attack_right_1", gp.tileSize * 2, gp.tileSize * 2); 
		attackRight2 = setup("/player/goblin_attack_right_2", gp.tileSize * 2, gp.tileSize);		
	}
	
	public void setAction() {

		actionLockCounter++;
		
		if (actionLockCounter == 60) {		
						
			Random random = new Random();
			int i = random.nextInt(100) + 1; // random number 1-100
						
			if (i <= 25) direction = "up";
			if (i > 25 && i <= 50) direction = "down";
			if (i > 50 && i <= 75) direction = "left";
			if (i > 75) direction = "right";
			
			actionLockCounter = 0;
		}		

		// 1 SHOT/2 SECONDS (120 frames)
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
		
		if (i < 50) 
			dropItem(new OBJ_Heart(gp));
		if (i >= 50 && i < 75)
			dropItem(new OBJ_Arrow(gp));
		if (i >= 75 && i <= 100)
			dropItem(new OBJ_Rupee_Blue(gp));
	}
}