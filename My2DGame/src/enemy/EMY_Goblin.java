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

public class EMY_Goblin extends Entity {

	GamePanel gp;
	Entity item;
	
	public EMY_Goblin(GamePanel gp) {
		super(gp);		
		
		this.gp = gp;
		
		type = type_enemy;
		name = "Goblin";
		speed = 2; defaultSpeed = speed; 
		animationSpeed = 10;
		attack = 4; defense = 0;
		knockbackPower = 1;
		exp = 8;
		maxLife = 6; life = maxLife;
						
		hitbox = new Rectangle(8, 16, 32, 32); 
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		arrows = -1;
		item = new ITM_Bow(gp);
		
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
	public void getAttackImage() {		
		attackUp1 = setup("/player/goblin_attack_up_1", gp.tileSize * 2, gp.tileSize * 2); 
		attackUp2 = setup("/player/goblin_attack_up_2", gp.tileSize, gp.tileSize * 2);		
		attackDown1 = setup("/player/goblin_attack_down_1", gp.tileSize * 2, gp.tileSize * 2); 
		attackDown2 = setup("/player/goblin_attack_down_2", gp.tileSize, gp.tileSize * 2);
		
		attackLeft1 = setup("/player/goblin_attack_left_1", gp.tileSize * 2, gp.tileSize * 2); 
		attackLeft2 = setup("/player/goblin_attack_left_2", gp.tileSize * 2, gp.tileSize);
		
		attackRight1 = setup("/player/goblin_attack_right_1", gp.tileSize * 2, gp.tileSize * 2); 
		attackRight2 = setup("/player/goblin_attack_right_2", gp.tileSize * 2, gp.tileSize);		
	}
	
	public void update() {
		
		super.update();
		
		// DISTANCE TO PLAYER (IN TILES)
		int xDistance = Math.abs(worldX - gp.player.worldX);
		int yDistance = Math.abs(worldY - gp.player.worldY);
		int tileDistance = (xDistance + yDistance) / gp.tileSize;
		
		// FOLLOW PLAYER IF CLOSE		
		if (!onPath && tileDistance < 5)			
			onPath = true;		
		
		// STOP FOLLOWING IF TOO FAR
		if (onPath && tileDistance > 10)
			onPath = false;
	}
	
	public void setAction() {
		
		if (onPath) {
			
			int goalCol = (gp.player.worldX + gp.player.hitbox.x) / gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.hitbox.y) / gp.tileSize;
			
			searchPath(goalCol, goalRow);
			
			// 1 SHOT/2 SECONDS (120 frames)
			int i = new Random().nextInt(120) + 1;
			if (i > 119) 
				item.use(this);			
		}
		else {

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