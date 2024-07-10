package entity.enemy;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Heart;
import entity.projectile.PRJ_Seed;

public class EMY_Octorok extends Entity {

	public static final String emyName = "Octorok";
	GamePanel gp;
	
	public EMY_Octorok(GamePanel gp, String direction) {
		super(gp);			
		this.gp = gp;
		this.direction = direction;
		
		type = type_enemy;
		name = emyName;
		canSwim = true;
		
		speed = 1; defaultSpeed = speed;
		animationSpeed = 15;
		attack = 1;
		knockbackPower = 0;
		maxLife = 6; life = maxLife;
		
		hitbox = new Rectangle(0, 0, 48, 48);
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
	
	// UPDATER
	public void update() {
		
		if (sleep) return;		
		if (knockback) { knockbackEntity();	return; }
		if (stunned) { manageValues(); return; }
		if (captured) { isCaptured(); return; }
		if (attacking) { attacking(); }
		
		setAction();
		
		if (onPath) {
			
			switch(direction) {
				case "up":
				case "down":
					if (gp.player.worldX > worldX) {
						lockonDirection = "right";
						checkCollision();
						if (!collisionOn) worldX += speed;
					}
					else if (gp.player.worldX < worldX) {
						lockonDirection = "left";
						checkCollision();
						if (!collisionOn) worldX -= speed;
					}
					break;
				case "left":
				case "right":
					if (gp.player.worldY > worldY) {
						lockonDirection = "down";
						checkCollision();
						if (!collisionOn) worldY += speed;
					}
					else if (gp.player.worldY < worldY) {
						lockonDirection = "up";
						checkCollision();
						if (!collisionOn) worldY -= speed;
					}
					break;
			}		
		}
		cycleSprites();
		
		manageValues();
	}
	
	public void setAction() {
		if (onPath) {			
			isOffPath(gp.player, 8);
			useProjectile(90);
		}
		else {	
			isOnPath(gp.player, 5);
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
		dropItem(new COL_Heart(gp));
	}
}