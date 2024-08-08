package entity.enemy;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Heart;

public class EMY_Tektite extends Entity {

	public static final String emyName = "Tektite";
	private int sleepCounter = 0;
		
	public EMY_Tektite(GamePanel gp, int worldX, int worldY) {
		super(gp);		
		this.worldX = worldX * gp.tileSize;
		this.worldY = worldY * gp.tileSize;
		worldXStart = this.worldX;
		worldYStart = this.worldY;
		
		type = type_enemy;
		name = emyName;
		canSwim = true;
		capturable = true;
		
		maxLife = 6; life = maxLife;		
		speed = 2; defaultSpeed = speed;
		animationSpeed = 8;
		attack = 2;
		knockbackPower = 0;
		
		hitbox = new Rectangle(2, 18, 44, 30);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {
		up1 = setup("/enemy/tektite_down_1");
		up2 = setup("/enemy/tektite_down_2");
		down1 = up1;
		down2 = up2;
		left1 = up1;
		left2 = up2;
		right1 = up1;
		right2 = up2;
	}
	
	public void setAction() {	
		
		if (!captured) {			
			
			sleepCounter++;
			if (sleepCounter >= 60) {
				sleepCounter = 0;			
				
				if (speed == 0) speed = defaultSpeed;
				else speed = 0;
				
				// STOP/START EVERY 1 SECOND
				getDirection(1);
			}
		}
	}
	public void attacking() {
		attacking = false;
	}
	
	public void damageReaction() {
		actionLockCounter = 0;
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
		dropItem(new COL_Heart(gp));
	}
}