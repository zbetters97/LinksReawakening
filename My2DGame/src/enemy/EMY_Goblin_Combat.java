package enemy;

import java.awt.Rectangle;
import java.util.Random;

import collectable.COL_Heart;
import collectable.COL_Rupee_Blue;
import collectable.COL_Rupee_Red;
import entity.Entity;
import main.GamePanel;

public class EMY_Goblin_Combat extends Entity {

	GamePanel gp;
	
	public EMY_Goblin_Combat(GamePanel gp) {
		super(gp);				
		this.gp = gp;
		
		type = type_enemy;
		name = "Combat Goblin";
		speed = 1; defaultSpeed = speed; 
		animationSpeed = 10;
		attack = 4; defense = 1;
		knockbackPower = 1;
		exp = 12;
		maxLife = 8; life = maxLife;
		
		swingSpeed1 = 30;
		swingSpeed2 = 60;
						
		hitbox = new Rectangle(8, 16, 32, 32); 
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		attackArea.width = 36;
		attackArea.height = 36;
		
		getImage();
		getAttackImage();
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
		attackUp1 = setup("/enemy/goblin_attack_up_1", gp.tileSize, gp.tileSize * 2); 
		attackUp2 = setup("/enemy/goblin_attack_up_2", gp.tileSize, gp.tileSize * 2);		
		attackDown1 = setup("/enemy/goblin_attack_down_1", gp.tileSize, gp.tileSize * 2); 
		attackDown2 = setup("/enemy/goblin_attack_down_2", gp.tileSize, gp.tileSize * 2);
		
		attackLeft1 = setup("/enemy/goblin_attack_left_1", gp.tileSize * 2, gp.tileSize); 
		attackLeft2 = setup("/enemy/goblin_attack_left_2", gp.tileSize * 2, gp.tileSize);		
		attackRight1 = setup("/enemy/goblin_attack_right_1", gp.tileSize * 2, gp.tileSize); 
		attackRight2 = setup("/enemy/goblin_attack_right_2", gp.tileSize * 2, gp.tileSize);			
	}
	
	public void setAction() {
		
		if (onPath) {			
			isOffPath(gp.player, 8);									
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player));

			if (!attacking) {
				isAttacking(30, gp.tileSize * 3, gp.tileSize);
			}
			
		}
		else {			
			isOnPath(gp.player, 4);
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
		
		if (i < 50) dropItem(new COL_Heart(gp));
		if (i >= 50 && i < 90) dropItem(new COL_Rupee_Blue(gp));
		if (i >= 90 && i <= 100) dropItem(new COL_Rupee_Red(gp));
	}
}