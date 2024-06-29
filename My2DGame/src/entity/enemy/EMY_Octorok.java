package entity.enemy;

import java.awt.Rectangle;
import java.util.Random;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Heart;
import entity.collectable.COL_Rupee_Blue;
import entity.projectile.PRJ_Seed;

public class EMY_Octorok extends Entity {

	public static final String emyName = "Octorok";
	GamePanel gp;
	
	public EMY_Octorok(GamePanel gp) {
		super(gp);			
		this.gp = gp;
		
		type = type_enemy;
		name = emyName;
		canSwim = true;
		
		speed = 0; defaultSpeed = speed;
		animationSpeed = 15;
		attack = 1; 
		knockbackPower = 0;
		maxLife = 6; life = maxLife;
		
		hitbox = new Rectangle(2, 18, 44, 30);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;

		projectile = new PRJ_Seed(gp);
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/enemy/octo_up_1");
		up2 = setup("/enemy/octo_up_2");
		down1 = setup("/enemy/octo_down_1");
		down2 = setup("/enemy/octo_down_2");
		left1 = setup("/enemy/octo_left_1");
		left2 = setup("/enemy/octo_left_2");
		right1 = setup("/enemy/octo_right_1");
		right2 = setup("/enemy/octo_right_2");
	}
	
	public void setAction() {
		if (onPath) {			
			isOffPath(gp.player, 10);			
			approachPlayer(10);
			useProjectile(60);
		}
		else {	
			isOnPath(gp.player, 8);
			getDirection(120);
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