package entity.enemy;

import java.awt.Rectangle;
import java.util.Random;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Heart;
import entity.collectable.COL_Rupee_Green;

public class EMY_Keese extends Entity {

	public static final String emyName = "Keese";
		
	public EMY_Keese(GamePanel gp, int worldX, int worldY) {
		super(gp);		
		this.worldX = worldX * gp.tileSize;
		this.worldY = worldY * gp.tileSize;
		worldXStart = this.worldX;
		worldYStart = this.worldY;
		bounds = 5;
		
		type = type_enemy;
		name = emyName;
		onGround = false;
		
		maxLife = 4; life = maxLife;		
		speed = 2; defaultSpeed = speed;
		animationSpeed = 5;
		attack = 1;
		knockbackPower = 0;
		
		hitbox = new Rectangle(2, 18, 44, 30);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
	}
	
	public void getImage() {
		up1 = setup("/enemy/keese_down_1");
		up2 = setup("/enemy/keese_down_2");
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