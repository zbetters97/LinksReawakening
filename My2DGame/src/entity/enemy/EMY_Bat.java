package entity.enemy;

import java.awt.Rectangle;
import java.util.Random;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Heart;
import entity.collectable.COL_Rupee_Green;

public class EMY_Bat extends Entity {

	public static final String emyName = "Bat";
	GamePanel gp;
	
	public EMY_Bat(GamePanel gp) {
		super(gp);				
		this.gp = gp;
		
		type = type_enemy;
		name = emyName;
		onGround = false;
		
		speed = 2; defaultSpeed = speed;
		animationSpeed = 5;
		attack = 1;
		knockbackPower = 1;
		maxLife = 4; life = maxLife;
		
		hitbox = new Rectangle(2, 18, 44, 30);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/enemy/bat_down_1");
		up2 = setup("/enemy/bat_down_2");
		down1 = up1;
		down2 = up2;
		left1 = up1;
		left2 = up2;
		right1 = up1;
		right2 = up2;
	}
	
	public void setAction() {	
		getDirection(25);
	}
	
	// BAT CHASES PLAYER WHEN HIT
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
		else dropItem(new COL_Rupee_Green(gp));
	}
}