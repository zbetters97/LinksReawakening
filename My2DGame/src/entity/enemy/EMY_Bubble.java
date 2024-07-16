package entity.enemy;

import java.awt.Rectangle;
import java.util.Random;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Heart;
import entity.collectable.COL_Rupee_Green;

public class EMY_Bubble extends Entity {

	public static final String emyName = "Buble";
	GamePanel gp;
	
	public EMY_Bubble(GamePanel gp, int worldX, int worldY) {
		super(gp);				
		this.gp = gp;
		this.worldX = worldX * gp.tileSize;
		this.worldY = worldY * gp.tileSize;	
		worldXStart = this.worldX;
		worldYStart = this.worldY;
		direction = "upleft";
		
		type = type_enemy;
		name = emyName;
		onGround = false;
		
		speed = 2; defaultSpeed = speed;
		animationSpeed = 5;
		maxLife = 8; life = maxLife;
		attack = 2;
		knockbackPower = 0;		
		
		hitbox = new Rectangle(8, 8, 32, 32);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/enemy/bubble_down_1");
		up2 = up1;
		down1 = up1;
		down2 = up2;
		left1 = up1;
		left2 = up2;
		right1 = up1;
		right2 = up2;
	}
	
	public void update() {
		move();
		manageValues();
	}
	
	public void move() {
						
		gp.cChecker.detectBounce(this);
		gp.cChecker.detectBounce(this, gp.iTile);	
		gp.cChecker.detectBounce(this, gp.obj);	
		gp.cChecker.detectBounce(this, gp.obj_i);
		gp.cChecker.detectBounce(this, gp.npc);
		
		switch (direction) {
			case "upleft": worldY -= speed - 1; worldX -= speed - 1; break;
			case "upright": worldY -= speed - 1; worldX += speed - 1; break;				
			case "downleft": worldY += speed - 1; worldX -= speed - 1; break;
			case "downright": worldY += speed; worldX += speed - 1; break;
		}		
	}
	
	public void damageReaction() {
	}
	public void playHurtSE() {
		gp.playSE(3, 0);
	}
	public void playDeathSE() {
		gp.playSE(3, 2);
	}
	public void checkDrop() {
		super.checkDrop();
		
		int i = new Random().nextInt(100) + 1;
		
		if (i < 50) dropItem(new COL_Heart(gp));
		else dropItem(new COL_Rupee_Green(gp));
	}
}