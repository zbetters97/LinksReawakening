package enemy;

import java.awt.Rectangle;
import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.COL_Heart;
import object.COL_Rupee_Green;

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
		}
		else {
			
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