package entity.enemy;

import java.awt.Rectangle;
import java.util.Random;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Heart;
import entity.collectable.COL_Rupee_Blue;
import entity.projectile.PRJ_Fireball;

public class EMY_Slime_Red extends Entity {

	public static final String emyName = "Red Slime";
	GamePanel gp;
	
	public EMY_Slime_Red(GamePanel gp) {
		super(gp);			
		this.gp = gp;
		
		type = type_enemy;
		name = emyName;
		
		speed = 1; defaultSpeed = speed;
		animationSpeed = 15;
		attack = 2; 
		knockbackPower = 1;
		maxLife = 6; life = maxLife;
		
		hitbox = new Rectangle(2, 18, 44, 30);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;

		projectile = new PRJ_Fireball(gp);
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/enemy/redslime_down_1");
		up2 = setup("/enemy/redslime_down_2");
		down1 = setup("/enemy/redslime_down_1");
		down2 = setup("/enemy/redslime_down_2");
		left1 = setup("/enemy/redslime_down_1");
		left2 = setup("/enemy/redslime_down_2");
		right1 = setup("/enemy/redslime_down_1");
		right2 = setup("/enemy/redslime_down_2");
	}
	
	public void setAction() {
		if (onPath) {			
			isOffPath(gp.player, 8);				
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
			useProjectile(90);
		}
		else {	
			isOnPath(gp.player, 6);
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