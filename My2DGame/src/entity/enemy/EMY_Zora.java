package entity.enemy;

import java.util.Random;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Heart;
import entity.projectile.PRJ_Fireball;

public class EMY_Zora extends Entity {

	public static final String emyName = "Octorok";
	
	public EMY_Zora(GamePanel gp, int worldX, int worldY) {
		super(gp);			
		this.worldX = worldX * gp.tileSize;
		this.worldY = worldY * gp.tileSize;	
		worldXStart = this.worldX;
		worldYStart = this.worldY;
		direction = "down";
		
		type = type_enemy;
		name = emyName;
		canSwim = true;		
		
		maxLife = 12; life = maxLife;
		speed = 0; defaultSpeed = speed;
		animationSpeed = 12;				
		attack = 2; 
		knockbackPower = 0;
		
		projectile = new PRJ_Fireball(gp);
		
		getAttackImage();
	}
	
	public void getImage() {
		down1 = setup("/enemy/zora_down_1");
		down2 = setup("/enemy/zora_down_2");
		up1 = down1;
		up2 = down2;
		left1 = down1;
		left2 = down2;
		right1 = down1;
		right2 = down2;
	}
	public void getAttackImage() {
		attackDown1 = setup("/enemy/zora_attack_down_1");
		attackDown2 = attackDown1;
		attackUp1 = attackDown1;
		attackUp2 = attackDown1;
		attackLeft1 = attackDown1;
		attackLeft2 = attackDown1;
		attackRight1 = attackDown1;
		attackRight2 = attackDown1;
	}
	
	// UPDATER
	public void update() {
		
		if (sleep) return;		
		if (knockback) { knockbackEntity();	return; }
		if (stunned) { manageValues(); return; }
		
		setAction();
		
		if (onPath) {			
			spriteNum = 2;
			
			if (!attacking) {				
				prepareAttack(120);
			}
			else {
				spriteCounter++;
				if (spriteCounter == 30) {
					useProjectile();
				}
				else if (spriteCounter >= 60) {
					attacking = false;
					spriteCounter = 0;
				}
			}
		}
		else {			
			attacking = false;
			spriteNum = 1;
			spriteCounter = 0;			
		}
		
		manageValues();
	}
	
	public void setAction() {
		if (onPath) {			
			isOffPath(gp.player, 7);
			approachPlayer(10);
		}
		else {	
			isOnPath(gp.player, 6);
		}
	}
	
	private void prepareAttack(int rate) {		
		int i = new Random().nextInt(rate);
		if (i == 0 && !projectile.alive && shotAvailableCounter == 30) {	
			attacking = true;
		}		
	}
	
	public void useProjectile() {		
		if (!projectile.alive && shotAvailableCounter == 30) {	
			
			projectile.set(worldX, worldY, direction, true, this);
			addProjectile(projectile);
			
			shotAvailableCounter = 0;
			
			projectile.playSE();
		}
	}
	
	public void damageReaction() {
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