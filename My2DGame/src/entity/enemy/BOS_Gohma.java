package entity.enemy;

import java.awt.Rectangle;
import java.util.Random;

import application.GamePanel;
import entity.Entity;
import entity.projectile.PRJ_Fireball;

public class BOS_Gohma extends Entity {

	public static final String emyName = "Queen Gohma";
	GamePanel gp;
	private int cycle = 0;
	
	public BOS_Gohma(GamePanel gp, int worldX, int worldY) {
		super(gp);			
		this.gp = gp;
		this.worldX = worldX * gp.tileSize;
		this.worldY = worldY * gp.tileSize;	
		this.worldXStart = this.worldX;
		this.worldYStart = this.worldY;
		
		type = type_boss;
		name = emyName;
		sleep = true;
		lockon = true;
		direction = "down";			
		lockonDirection = "right";
		currentBossPhase = bossPhase_1;
		bounds = 2;
		
		speed = 1; defaultSpeed = speed; 
		animationSpeed = 10;
		attack = 4;
		knockbackPower = 1;
		maxLife = 12; life = maxLife;
		currentBossPhase = bossPhase_1;
								
		hitbox = new Rectangle(51, 8, 42, 33); 
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		projectile = new PRJ_Fireball(gp);
		
		getImage();
		getAttackImage();
	}
	
	public void getImage() {
		
		int scale = gp.tileSize * 3;
		
		down1 = setup("/boss/gohma_down_1", scale, gp.tileSize);
		down2 = setup("/boss/gohma_down_2", scale, gp.tileSize);
		down3 = setup("/boss/gohma_down_3", scale, gp.tileSize);
	}
	public void getAttackImage() {		
		
		int scale = gp.tileSize * 3;
		
		attackDown1 = setup("/boss/gohma_attack_down_1", scale, gp.tileSize); 
		attackDown2 = setup("/boss/gohma_attack_down_2", scale, gp.tileSize);	
		attackDown3 = setup("/boss/gohma_attack_down_3", scale, gp.tileSize);	
	}
	
	public void update() {

		if (sleep) return;		
		
		setAction();		
		move();		
		
		cycleSprites();		
		manageValues();
	}
	
	public void move() {
		
		checkCollision();
		if (!collisionOn && withinBounds()) { 						
			switch (lockonDirection) {
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
			}
		}		
	}
	
	public void cycleSprites() {
		spriteCounter++;
		if (spriteCounter > animationSpeed && animationSpeed != 0) {
			
			// 1 -> 2
			if (attacking) {
				if (attackNum == 1) attackNum = 2;
				else if (attackNum == 2 && cycle == 0) {
					attackNum = 3;
					cycle++;
				}
				else if (attackNum == 2 && cycle == 1) {
					attackNum = 1;
					cycle = 0;
				}
				else if (attackNum == 3) {
					attackNum = 2;				
				}
			}
			// 1 -> 2 -> 3 -> 2 -> 1
			else {
				if (spriteNum == 1) spriteNum = 2;
				else if (spriteNum == 2 && cycle == 0) {
					spriteNum = 3;
					cycle++;
				}
				else if (spriteNum == 2 && cycle == 1) {
					spriteNum = 1;
					cycle = 0;
				}
				else if (spriteNum == 3) {
					spriteNum = 2;				
				}
			}
			
			spriteCounter = 0;
		}		
	}
	
	public void setAction() {	
		
		int directionRate = 45;
		int projectileRate = 60;
		
		if (currentBossPhase == bossPhase_1) {
			directionRate = 45;
			projectileRate = 60;
			
			if (life <= maxLife / 2) {
				currentBossPhase = 2;
				speed++;
			}
		}
		else if (currentBossPhase == bossPhase_2) {
			directionRate = 30;
			projectileRate = 45;
		}
		
		getDirection(directionRate);
		if (!attacking) {
			guarded = true;
			int i = new Random().nextInt(100) + 1;
			if (i == 1) {
				attacking = true;
			}
		}
		else {
			guarded = false;
			useProjectile(projectileRate, gp.tileSize);
			attackCounter++;
			if (attackCounter == 180) {
				attackCounter = 0;
				attacking = false;			
			}
		}		
	}
	public void getDirection(int rate) {
		
		actionLockCounter++;			
		if (actionLockCounter >= rate) {		
						
			Random random = new Random();
			int i = random.nextInt(100) + 1;
						
			if (i <= 50) lockonDirection = "left";
			else lockonDirection = "right";
			
			actionLockCounter = 0;
		}
	}
	
	public void damageReaction() {
		actionLockCounter = 0;
		guarded = true;
		attacking = false;
		attackCounter = 0;
	}	
	
	public void playHurtSE() {
		gp.playSE(3, 4);
	}
	public void playDeathSE() {
		gp.playSE(3, 5);
	}
	public void checkDrop() {	
		super.checkDrop();
		
		gp.stopMusic();
		gp.playMusic(5);		
	}
}