package entity.enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import application.GamePanel;
import entity.Entity;

public class BOS_Stalfos extends Entity {

	public static final String emyName = "Stalfos King";
	
	public BOS_Stalfos(GamePanel gp, int worldX, int worldY) {
		super(gp);				
		this.worldX = worldX * gp.tileSize;
		this.worldY = worldY * gp.tileSize;	
		worldXStart = this.worldX;
		worldYStart = this.worldY;
		direction = "down";
		lockonDirection = "right";
		
		type = type_boss;
		name = emyName;	
		lockon = true;
		
		speed = 2; defaultSpeed = speed; 
		animationSpeed = 14;
		maxLife = 50; life = maxLife;
		attack = 8;
		knockbackPower = 3;		
		currentBossPhase = bossPhase_1;
		
		swingSpeed1 = 30;
		swingSpeed2 = 80;
						
		hitbox = new Rectangle(4, 32, 120, 128); 
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
		
		attackbox.width = 128;
		attackbox.height = 100;
				
		setDialogue();
	}
	
	public void getImage() {		
		down1 = setup("/boss/stalfos_down_1", 40 * 4, 40 * 4);
		down2 = setup("/boss/stalfos_down_2", 40 * 4, 40 * 4);
		up1 = down1;
		up2 = down2;
		down1 = up1;
		down2 = up2;
		left1 = up1;
		left2 = up2;
		right1 = up1;
		right2 = up2;		
	}	
	public void getAttackImage() {			
		attackLeft1 = setup("/boss/stalfos_attack_left_1", 40 * 4, gp.tileSize * 4);
		attackLeft2 = setup("/boss/stalfos_attack_left_2", 40 * 4, gp.tileSize * 4);
		attackLeft3 = setup("/boss/stalfos_attack_left_3", 40 * 4, gp.tileSize * 4);
		attackRight1 = setup("/boss/stalfos_attack_right_1", 40 * 4, gp.tileSize * 4);
		attackRight2 = setup("/boss/stalfos_attack_right_2", 40 * 4, gp.tileSize * 4);
		attackRight3 = setup("/boss/stalfos_attack_right_3", 40 * 4, gp.tileSize * 4);
	}
	
	public void setDialogue() {
		dialogues[0][0] = "No one may enter the tressure room!";
		dialogues[0][1] = "Taste the blade of my sword!";
	}
	
	public void setAction() {
		
		if (!attacking) {			
			getDirection(30);
		}						
		
		if (!attacking) {
			if (isAttacking(45, gp.tileSize * 7, gp.tileSize * 5)) {
				attacking = true;
			}				
			speed = defaultSpeed;
		}
		else {
			speed = 0;
		}			
	}
	
	public void getDirection(int rate) {
		
		actionLockCounter++;			
		if (actionLockCounter >= rate) {		
						
			Random random = new Random();
			int i = random.nextInt(100) + 1;
						
			if (i <= 50) {
				lockonDirection = "left";
				direction = lockonDirection;
			}
			else {
				lockonDirection = "right";
				direction = lockonDirection;
			}
			
			actionLockCounter = 0;
		}
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
	
	public void attacking() {

		attackCounter++;
				
		// ATTACK IMAGE 1
		if (attackCounter <= swingSpeed1) {			
			attackNum = 1;
			if (swingSpeed1 == attackCounter)
				playAttackSE();
		}		
		
		else if (attackCounter > swingSpeed1 && attackCounter <= 35) {
			attackNum = 2;
		}
		
		// ATTACK IMAGE 2
		else if (attackCounter <= swingSpeed2 && attackCounter > swingSpeed1) {
			attackNum = 3;
			
			// CHECK IF WEAPON HITS TARGET	
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			
			// ADJUST X/Y 
			worldY += attackbox.height; 			
			
			// CHANGE SIZE OF HIT BOX 
			hitbox.width = attackbox.width;
			hitbox.height = attackbox.height;
			
			if (gp.cChecker.checkPlayer(this)) 
				damagePlayer(attack);				
			
			// RESTORE HITBOX
			worldX = currentWorldX;
			worldY = currentWorldY;
			hitbox.width = hitboxDefaultWidth;
			hitbox.height = hitboxDefaultHeight;
		}
		
		// RESET IMAGE
		if (attackCounter > swingSpeed2) {
			attackNum = 1;
			attackCounter = 0;
			attacking = false;
		}
	}		
	
	public void damageReaction() {
		attacking = true;
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
								
		// DRAW TILES WITHIN SCREEN BOUNDARY
		if (inFrame()) {			
			
			if (attacking) {
				switch(lockonDirection) {
					case "left":
						if (attackNum == 1) {
							image = attackLeft1;
							tempScreenY -= 8 * 4;
						}
						else if (attackNum == 2) {
							image = attackLeft2;	
							tempScreenY += 8 * 4;
						}
						else if (attackNum == 3) {
							image = attackLeft3;	
							tempScreenY += 8 * 4;
						}
						break;
					case "right":
						if (attackNum == 1) {
							image = attackRight1;
							tempScreenY -= 8 * 4;
						}
						else if (attackNum == 2) {
							image = attackRight2;	
							tempScreenY += 8 * 4;
						}
						else if (attackNum == 3) {
							image = attackRight3;	
							tempScreenY += 8 * 4;
						}						
						break;			
				}
			}
			else {
				if (spriteNum == 1) image = down1;
				else if (spriteNum == 2) image = down2;	
			}						
		}
					
		// ENEMY IS HIT
		if (invincible) {
			
			// DISPLAY HP
			hpBarOn = true;
			hpBarCounter = 0;	
			
			// FLASH OPACITY
			hurtAnimation(g2);
		}		
		
		if (dying) dyingAnimation(g2);	
		
		g2.drawImage(image, tempScreenX, tempScreenY, null);	
		
		// DRAW HITBOX
		if (gp.keyH.debug) {
			g2.setColor(Color.RED);
			g2.drawRect(tempScreenX + hitbox.x, tempScreenY + hitbox.y, hitbox.width, hitbox.height);
		}
		
		// RESET OPACITY
		changeAlpha(g2, 1f);		
	}
	
	public void resetValues() {
		speed = 2; defaultSpeed = speed; 
		animationSpeed = 14;
		maxLife = 50; life = maxLife;
		attack = 8;
		knockbackPower = 3;		
		currentBossPhase = bossPhase_1;
		attacking = false;
		attackCounter = 0;
	}
	
	public void playAttackSE() {
		gp.playSE(3, 3);
	}
	public void playHurtSE() {
		gp.playSE(3, 4);
	}
	public void playDeathSE() {
		gp.playSE(3, 5);
	}
	
	public void checkDrop() {			
	}
}