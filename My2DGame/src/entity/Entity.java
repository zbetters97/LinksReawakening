package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;

import application.GamePanel;
import application.UtilityTool;
import entity.enemy.EMY_Zora;
import entity.projectile.PRJ_Seed;
import entity.projectile.Projectile;

public class Entity {
	
	public enum Action {
		IDLE, CARRYING, GUARDING, GRABBING, RUNNING, AIMING, DIGGING, JUMPING, SOARING, SWINGING, SWIMMING, THROWING;
	}
	
	protected GamePanel gp;
	
	// GENERAL ATTRIBUTES
	public int worldXStart;
	public int worldYStart;
	public int worldX, worldY;		
	protected int tempScreenX;

	protected int tempScreenY;
	public String name;		
	public Action action;
	public boolean collision = true;
	public boolean collisionOn = false;
	public boolean onGround = true;
	public boolean sleep = false;
	public boolean temp = false;
	public boolean drawing = true;
	public boolean opening = false;
	public boolean closing = false;
	public int bounds = 999;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public BufferedImage image1, image2, image3,
							up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3,
							attackUp1, attackUp2, attackUp3, attackDown1, attackDown2, attackDown3,
							attackLeft1, attackLeft2, attackLeft3, attackRight1, attackRight2, attackRight3,
							guardUp1, guardUp2, guardDown1, guardDown2, 
							guardLeft1, guardLeft2, guardRight1, guardRight2,
							buzzUp1, buzzUp2, lockedImage, die1, die2, die3, die4;
		
	// CHARACTER ATTRIBUTES
	public int type;
	public String direction = "down";
	public int life, maxLife; // 1 life = half heart
	public int arrows, maxArrows;
	public int bombs, maxBombs;
	public int speed, defaultSpeed, runSpeed, animationSpeed;
	public int attack;
	public int rupees;
	public Entity currentWeapon, currentShield, currentItem;
	public Projectile projectile;	
	public boolean hasItemToGive = false;	
	public boolean hasCutscene = false;	
	public boolean attacking = false;
	public boolean moving = false;
	public boolean onPath = false;
	public boolean pathCompleted = false;
	public boolean teleporting = false;
	public boolean capturable = false;
	public boolean captured = false;
	public boolean canSwim = false;
	public boolean buzzing = false;
	public boolean guarded = false;
	public Entity grabbedObject;
	
		
	// DIALOGUE
	public String dialogues[][] = new String[20][20];
	public int dialogueSet = 0;
	public int dialogueIndex = 0;		
	public String responses[][] = new String[10][3];
		
	// LOCKON
	public boolean lockon = false;
	public boolean locked = false;	
	public Entity lockedTarget;	
	public String lockonDirection;
	
	// KNOCKBACK
	public boolean knockback = false;
	public int knockbackCounter = 0;
	public String knockbackDirection = "";
	
	// STUNNED
	public boolean stunned = false;
	public int stunnedCounter = 0;
	
	// INVINCIBILITY
	public boolean invincible = false;
	public int invincibleCounter = 0;
	public boolean transparent = false;
	
	// LIFE
	public boolean hpBarOn = false;
	public int hpBarCounter = 0;
	public boolean alive = true;
	public boolean dying = false;
	public int dyingCounter = 0;	
	
	protected int throwCounter = 0;

	// DEFAULT HITBOX
	public Rectangle hitbox = new Rectangle(0, 0, 48, 48);
	public int hitboxDefaultX, hitboxDefaultY, hitboxDefaultWidth, hitboxDefaultHeight;	
	
	// WEAPON HITBOX
	public Rectangle attackbox = new Rectangle(0, 0, 0, 0);
	
	// ATTACKING
	public int swingSpeed1;
	public int swingSpeed2;
	public int actionLockCounter = 0;
	public int attackCounter = 0;
	public int attackNum = 1;	
	public boolean attackCanceled = false;
	public int shotAvailableCounter;
	
	// BOSS VALUES
	public int currentBossPhase = 0;
	public final int bossPhase_1 = 1;
	public final int bossPhase_2 = 2;
	public final int bossPhase_3 = 3;
	
	// OBJECT ATTRIBUTES
	public Entity loot;	
	public Entity linkedEntity;
	public int pushAvailableCounter = 0;	
	public boolean destructible = false;
	public boolean bombable = false;
	public boolean diggable = false;
	public boolean grabbable = false;
	public boolean grabbed = false;
	public boolean hookGrabbable = false;
	public boolean hookGrabbed = false;
	public boolean opened = false;
	public boolean pressable = false;
	public boolean active = false;	
	public boolean switchedOn = false;
	public boolean thrown = false;
	
	// THROWING VARIABLES
	public double tTime = 0;
	public double xT = 0;
	public double yT = 0;
	public double tSpeed = 0.25;
	public double tG = 0.00065;
	public double tAnimationSpeed = 25;
	
	// ITEM ATTRIBUTES
	public int value, attackValue;
	public int charge = 0;
	public int knockbackPower = 0;
	public String description = "";
	public int price;
	public int useCost;	
	public int amount = 1;
	public int lifeDuration = -1;
	public boolean stackable = false;	
	
	// PROJECTILE ATTRIBUTES
	public Entity user;
	public boolean canPickup = false;
	
	// INVENTORY
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	
	// CHARACTER TYPES
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_enemy = 2;
	public final int type_boss = 3;
	
	// INVENTORY TYPES
	public final int type_equipment = 4;
	public final int type_item = 5;
	public final int type_collectable = 6;
	public final int type_consumable = 7;
	public final int type_quest = 8;
	public final int type_key = 9;
	public final int type_boss_key = 10;
	
	// OBJECT TYPES
	public final int type_projectile = 11;	
	public final int type_obstacle = 12;
	public final int type_obstacle_i = 13;
	public final int type_pickupOnly = 14;
	
	// CONSTRUCTOR
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	// CHILD ONLY	
	public void setAction() { }	
	public void setPath(int c, int r) { }	
	public void damageReaction() { }	
	public void checkDrop() { checkEnemyRoom(); }
	public void use() {	}
	public boolean use(Entity user) { return true; }
	public void setCharge(Entity user) { }
	public void resetValues() { }
	public void interact() { }
	public void move(String direction) { }	
	public void explode() {	}
	public void setLoot(Entity loot) { }
	public void setLoot(Entity lootOne, Entity lootTwo) { }
	public void playSE() { }
	public void playOpenSE() { }
	public void playCloseSE() { }
	public void playAttackSE() { }
	public void playHurtSE() { }
	public void playDeathSE() { }	
	
	// UPDATER
	public void update() {
		
		if (sleep) return;		
		if (knockback) { knockbackEntity();	return; }
		if (stunned) { manageValues(); return; }
		if (captured) { isCaptured(); return; }
		if (attacking) { attacking(); }
		
		// CHILD CLASS
		setAction();
		
		move();
		
		// ANIMATE ENEMY ALWAYS
		if (type == type_enemy || type == type_boss) {			
			cycleSprites();
		}
		
		manageValues();
	}
	
	public void move() {
		
		checkCollision();
		if (!collisionOn && withinBounds()) { 						
			switch (direction) {
				case "up": worldY -= speed; break;
				case "upleft": worldY -= speed - 1; worldX -= speed - 1; break;
				case "upright": worldY -= speed - 1; worldX += speed - 1; break;
				
				case "down": worldY += speed; break;
				case "downleft": worldY += speed - 1; worldX -= speed - 1; break;
				case "downright": worldY += speed; worldX += speed - 1; break;
				
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
			}
		}		
		
		// ANIMATE NPC IF MOVING
		if (type == type_npc) {
			cycleSprites();
		}
	}
	
	public void cycleSprites() {
		spriteCounter++;
		if (spriteCounter > animationSpeed && animationSpeed != 0) {
			
			if (spriteNum == 1) spriteNum = 2;
			else if (spriteNum == 2) spriteNum = 1;
			
			spriteCounter = 0;
		}
	}
	
	public void walking() {
		getDirection();
		
		checkCollision();
		if (!collisionOn) {
			switch (direction) {
				case "up": worldY -= speed; break;		
				case "down": worldY += speed; break;		
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
			}		
		}

		spriteCounter++;
		if (spriteCounter > animationSpeed && animationSpeed != 0) {
			
			if (spriteNum == 1) spriteNum = 2;
			else if (spriteNum == 2) spriteNum = 1;
			
			spriteCounter = 0;
		}
	}
	public void getDirection() {
		if (gp.keyH.upPressed) direction = "up";
		if (gp.keyH.downPressed) direction = "down";
		if (gp.keyH.leftPressed) direction = "left";
		if (gp.keyH.rightPressed) direction = "right";				
	}
	public void isCaptured() {
		
		if (attacking) { attacking(); return; }
		if (gp.keyH.actionPressed) { attacking = true; }
		if (gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.leftPressed || gp.keyH.rightPressed) {
			walking();
		}	
	}
	
	// COLLISION CHECKER
	public void checkCollision() {		
		
		collisionOn = false;
		gp.cChecker.checkTile(this);	
		gp.cChecker.checkPit(this, false);	
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.enemy);
		gp.cChecker.checkEntity(this, gp.enemy_r);
		gp.cChecker.checkEntity(this, gp.obj);
		gp.cChecker.checkEntity(this, gp.obj_i);
		gp.cChecker.checkEntity(this, gp.iTile);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkObject_I(this, false);
		
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		if ((type == type_enemy || type == type_boss) && contactPlayer) 
			damagePlayer(attack);  	
	}

	// DIALOGUE
	public void speak() { }	
	public void startDialogue(Entity entity, int setNum) {
		dialogueSet = setNum;
		gp.ui.npc = entity;		
		gp.gameState = gp.dialogueState;
	}
	public void startDialogue(Entity entity, int setNum, int responseSet, int offset) {
		dialogueSet = setNum;
		gp.ui.responseSet = responseSet;	
		gp.ui.offset = offset;
		gp.ui.npc = entity;		
		gp.ui.response = true;
		gp.gameState = gp.dialogueState;
	}
	public void facePlayer() {
		switch (gp.player.direction) {		
			case "up":
			case "upleft":
			case "upright": direction = "down"; break;
			case "down":
			case "downleft":
			case "downright": direction = "up"; break;
			case "left": direction = "right"; break;
			case "right": direction = "left"; break;		
		}	
	}
	
	public boolean withinBounds() {
		
		boolean withinBounds = true;
		
		String tempDirection;
		int tempWorldX = worldX;
		int tempWorldY = worldY;		
		
		if (lockon) tempDirection = lockonDirection;
		else tempDirection = direction;
		
		switch (tempDirection) {
			case "up": tempWorldY -= speed; break;
			case "upleft": tempWorldY -= speed - 1; tempWorldX -= speed - 1; break;
			case "upright": tempWorldY -= speed - 1; tempWorldX += speed - 1; break;
			
			case "down": tempWorldY += speed; break;
			case "downleft": tempWorldY += speed - 1; tempWorldX -= speed - 1; break;
			case "downright": tempWorldY += speed; tempWorldX += speed - 1; break;
			
			case "left": tempWorldX -= speed; break;
			case "right": tempWorldX += speed; break;
		}
		
		int tileDistance = (Math.abs(worldXStart - tempWorldX) + Math.abs(worldYStart - tempWorldY)) / gp.tileSize;

		if (tileDistance > bounds)
			withinBounds = false;
		
		return withinBounds;
	}
	
	// PATH FINDING
	public void approachPlayer(int rate) {
		
		actionLockCounter++;
		if (actionLockCounter >= rate) {
			
			if (getXdistance(gp.player) >= getYdistance(gp.player)) {
				if (gp.player.getCenterX() < getCenterX()) {
					direction = "left";
				}
				
				else {
					direction = "right";
				}
			}
			else if (getXdistance(gp.player) < getYdistance(gp.player)) {
				if (gp.player.getCenterY() < getCenterY()) {
					direction = "up";
				}
				else {
					direction = "down";
				}
			}
			
			actionLockCounter = 0;
		}
	}
	public void getDirection(int rate) {
		
		actionLockCounter++;			
		if (actionLockCounter >= rate) {		
						
			Random random = new Random();
			int i = random.nextInt(100) + 1;
						
			if (i <= 25) direction = "up";
			if (i > 25 && i <= 50) direction = "down";
			if (i > 50 && i <= 75) direction = "left";
			if (i > 75) direction = "right";
			
			actionLockCounter = 0;
		}
	}
	public void isOnPath(Entity target, int distance) {
		if (getTileDistance(target) < distance) {
			onPath = true;
		}
	}
	public void isOffPath(Entity target, int distance) {
		if (getTileDistance(target) > distance || !withinBounds()) {
			onPath = false;			
		}
	}	
	public int getTileDistance(Entity target) {
		int tileDistance = (getXdistance(target) + getYdistance(target)) / gp.tileSize;
		return tileDistance;
	}
	public int getXdistance(Entity target) { 
		int xDistance = Math.abs(getCenterX() - target.getCenterX());
		return xDistance;
	}
	public int getYdistance(Entity target) { 
		int yDistance = Math.abs(getCenterY() - target.getCenterY());
		return yDistance;
	}
	public int getCenterX() {
		int centerX = worldX + left1.getWidth() / 2;
		return centerX;
	}
	public int getCenterY() {
		int centerY = worldY + up1.getHeight() / 2;
		return centerY;
	}
	public int getScreenX() {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		return screenX;
	}
	public int getScreenY() {
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		return screenY;
	}	
	public boolean findPath(int goalCol, int goalRow) {
		
		boolean pathFound = false;
		
		int startCol = (worldX + hitbox.x) / gp.tileSize;
		int startRow = (worldY + hitbox.y) / gp.tileSize;
		
		// SET PATH
		gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
		
		if (gp.pFinder.search()) 
			pathFound = true;
			
		return pathFound;
	}	
	public void searchPath(int goalCol, int goalRow) {
		
		int startCol = (worldX + hitbox.x) / gp.tileSize;
		int startRow = (worldY + hitbox.y) / gp.tileSize;
		
		// SET PATH
		gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
		
		// PATH FOUND
		if (gp.pFinder.search()) {
			
			// NEXT WORLDX & WORLDY
			int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
			int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
						
			// ENTITY hitbox
			int eLeftX = worldX + hitbox.x;
			int eRightX = worldX + hitbox.x + hitbox.width;
			int eTopY = worldY + hitbox.y;
			int eBottomY = worldY + hitbox.y + hitbox.height;
			
			// FIND DIRECTION TO NEXT NODE
			// UP OR DOWN
			if (eTopY > nextY && eLeftX >= nextX && eRightX < nextX + gp.tileSize) 
				direction = "up";			
			else if (eTopY < nextY && eLeftX >= nextX && eRightX < nextX + gp.tileSize)
				direction = "down";			
			// LEFT OR RIGHT
			else if (eTopY >= nextY && eBottomY < nextY + gp.tileSize) {				
				if (eLeftX > nextX) direction = "left";
				if (eLeftX < nextX) direction = "right";
			}
			// UP OR LEFT
			else if (eTopY > nextY && eLeftX > nextX) {				
				direction = "up";				
				checkCollision();
				if (collisionOn) direction = "left";			
			}
			// UP OR RIGHT
			else if (eTopY > nextY && eLeftX < nextX) {
				direction = "up";
				checkCollision();
				if (collisionOn) direction = "right";		
			}
			// DOWN OR LEFT
			else if (eTopY < nextY && eLeftX > nextX) {
				direction = "down";
				checkCollision();
				if (collisionOn) direction = "left";
			}
			// DOWN OR RIGHT
			else if (eTopY < nextY && eLeftX < nextX) {
				direction = "down";
				checkCollision();
				if (collisionOn) direction = "right";
			}
		}
		// NO PATH FOUND
		else {
			onPath = false;
		}
		
		// GOAL REACHED
		if (gp.pFinder.pathList.size() > 0) {		
			int nextCol = gp.pFinder.pathList.get(0).col;
			int nextRow = gp.pFinder.pathList.get(0).row;
			if (nextCol == goalCol && nextRow == goalRow)
				pathCompleted = true;
		}
	}
	public int getGoalCol(Entity target) {
		int goalCol = (target.worldX + target.hitbox.x) / gp.tileSize;
		return goalCol;
	}
	public int getGoalRow(Entity target) {
		int goalRow = (target.worldY + target.hitbox.y) / gp.tileSize;
		return goalRow;
	}
	
	// ATTACKING / DAMAGE
	public boolean isAttacking(int rate, int straight, int horizontal) {
				
		boolean targetInRange = false;
		int xDis = getXdistance(gp.player);
		int yDis = getYdistance(gp.player);
		
		// IF PLAYER IS WITHIN ATTACK BOX
		switch (direction) {
			case "up": 
			case "upleft":
			case "upright":
				if (gp.player.getCenterY() < getCenterY() && yDis < straight && xDis < horizontal) {
					targetInRange = true;
				}
				break;
			case "down":
			case "downleft":
			case "downright":
				if (gp.player.getCenterY() > getCenterY() && yDis < straight && xDis < horizontal) {
					targetInRange = true;
				}
				break;
			case "left": 
				if (gp.player.getCenterX() < getCenterX() && xDis < straight && yDis < horizontal) {
					targetInRange = true;
				}
				break;
			case "right": 
				if (gp.player.getCenterX() > getCenterX() && xDis < straight && yDis < horizontal) {
					targetInRange = true;
				}
				break;
		}
		
		// PLAYER IS WITHIN ATTACK BOX
		if (targetInRange) {
			
			// RANDOM CHANCE TO SWING WEAPON
			int i = new Random().nextInt(rate);
			if (i == 0) {
				spriteNum = 1;
				spriteCounter = 0;
				return true;				
			}
		} 	
			
		return false;
	}
	public void attacking() {

		attackCounter++;
		
		// PREVENT SWING SPEED GLITCH
		if (swingSpeed1 == 0 && swingSpeed2 == 0) {
			swingSpeed1 = 3;
			swingSpeed2 = 15;
		}
				
		// ATTACK IMAGE 1
		if (swingSpeed1 >= attackCounter) {			
			attackNum = 1;
			if (swingSpeed1 == attackCounter)
				playAttackSE();
		}		
		// ATTACK IMAGE 2
		if (swingSpeed2 >= attackCounter && attackCounter > swingSpeed1) {
			
			attackNum = 2;
			
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int hitBoxWidth = hitbox.width;
			int hitBoxHeight = hitbox.height;
			
			// ADJUST PLAYER'S X/Y 
			switch (direction) {
				case "up":
				case "upleft": 
				case "upright":  worldY -= attackbox.height; break; 
				case "down": 
				case "downleft": 
				case "downright": worldY += (attackbox.height / 2); break;					
				case "left": worldX -= attackbox.width; break;
				case "right": worldX += attackbox.width; break;
			}
			
			// CHANGE SIZE OF HIT BOX 
			hitbox.width = attackbox.width;
			hitbox.height = attackbox.height;
			
			// ENEMY ATTACKING
			if ((type == type_enemy || type == type_boss) && !captured) {
				if (gp.cChecker.checkPlayer(this)) 
					damagePlayer(attack);				
			}
			// PLAYER ATTACKING
			else {
				
				// CHECK IF ATTACK LANDS ON ENEMY
				int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
				if (enemyIndex == -1) enemyIndex = gp.cChecker.checkEntity(this, gp.enemy_r); 
				
				if (currentWeapon == null) 					
					gp.player.damageEnemy(enemyIndex, this, attack, 0);
				else
					gp.player.damageEnemy(enemyIndex, this, attack, currentWeapon.knockbackPower);
				
				// CHECK INTERACTIVE TILE
				int iTileIndex = gp.cChecker.checkEntity(gp.player, gp.iTile);
				gp.player.damageInteractiveTile(iTileIndex, this);				
				
				// CHECK IF ATTACK LANDS ON PROJECTILE
				int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
				gp.player.damageProjectile(projectileIndex);	
			}
						
			// RESTORE PLAYER HITBOX
			worldX = currentWorldX;
			worldY = currentWorldY;
			hitbox.width = hitBoxWidth;
			hitbox.height = hitBoxHeight;
		}
		
		// RESET IMAGE
		if (attackCounter > swingSpeed2) {
			attackNum = 1;
			attackCounter = 0;
			attacking = false;
			attackCanceled = false;
			gp.keyH.actionPressed = false;
		}
	}		
	public void damagePlayer(int attack) {
		
		if (!gp.player.invincible && gp.player.alive && !teleporting) {
						
			int damage = attack;
			String guardDirection = getOppositeDirection(direction);
			if (gp.player.action == Action.GUARDING && gp.player.direction.equals(guardDirection)) {
				gp.player.playBlockSE();
								
				if (name.equals(PRJ_Seed.prjName)) {
					direction = gp.player.direction;
					collisionOn = false;
					user = gp.player;
					life = maxLife;
				}
				else {				
					if (knockbackPower > 0) setKnockback(gp.player, this, 1);
					damage = 0;
				}
			}
			else {
				gp.player.playHurtSE();
				
				if (knockbackPower > 0) setKnockback(gp.player, this, knockbackPower);
				if (damage < 0) damage = 0;	
				gp.player.life -= damage;
				gp.player.transparent = true;
			}			

			gp.player.invincible = true;
		}
	}
	public String getOppositeDirection(String direction) {
		
		String oppositeDirection = "";
		
		switch(direction) {
			case "up": 
			case "upleft": 
			case "upright": oppositeDirection = "down"; break;
			case "down": 
			case "downleft": 
			case "downright": oppositeDirection = "up"; break;
			case "left": oppositeDirection = "right"; break;
			case "right": oppositeDirection = "left"; break;
		}
		
		return oppositeDirection;
	}
	public void hurtAnimation(Graphics2D g2) {
		
		invincibleCounter++;	
		
		if (invincibleCounter % 5 == 0) 
			changeAlpha(g2, 0.2f);
	}	
	public void dyingAnimation(Graphics2D g2) {
		
		dyingCounter++;	
		
		if (dyingCounter % 5 == 0) 
			changeAlpha(g2, 0.2f);
		
		// LONGER DYING ANIMATION FOR BOSSES
		if (type == type_boss) {
			if (dyingCounter > 180) {
				alive = false;		
			}
		}
		else {
			if (dyingCounter > 40) 
				alive = false;			
		}		
	}
	public void dropItem(Entity droppedItem) { 								
		for (int i = 0; i < gp.obj[1].length; i++) {			
			if (gp.obj[gp.currentMap][i] == null) {
				gp.obj[gp.currentMap][i] = droppedItem;
				gp.obj[gp.currentMap][i].worldX = worldX;
				gp.obj[gp.currentMap][i].worldY = worldY;
				break;
			}
		}
	}
	
	// KNOCKBACK
	public void knockbackEntity() {
		
		// CANCEL IF TILE COLLISION
		checkCollision();			
		if (collisionOn) {
			knockbackCounter = 0;
			knockback = false;
			speed = defaultSpeed;
		}
		else {
			switch(knockbackDirection) {
				case "up": 
				case "upleft": 
				case "upright": worldY -= speed; break;				
				case "down": 
				case "downleft": 
				case "downright": worldY += speed; break;				
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
			}
		}
		
		knockbackCounter++;
		if (knockbackCounter == 10) {
			knockbackCounter = 0;
			knockback = false;
			speed = defaultSpeed;						
		}		
	}	
	public void setKnockback(Entity target, Entity attacker, double knockbackPower) {			
		target.knockbackDirection = attacker.direction;
		target.speed += knockbackPower;
		target.knockback = true;
	}
	public void setKnockback(Entity target, String direction, double knockbackPower) {
		target.knockbackDirection = direction;
		target.speed += knockbackPower;
		target.knockback = true;
	}
	
	// USE ITEM/PROJECTILE
	public void useItem(int rate) {
		int i = new Random().nextInt(rate);
		if (i == 0) currentItem.use(this);		
	}
	public void useProjectile(int rate) {
		
		int i = new Random().nextInt(rate);
		if (i == 0 && !projectile.alive && shotAvailableCounter == 30) {

			projectile.set(worldX, worldY, direction, true, this);
			addProjectile(projectile);
			
			shotAvailableCounter = 0;
			
			projectile.playSE();
		}
	}
	public void useProjectile(int rate, int scale) {
		
		int i = new Random().nextInt(rate);
		if (i == 0 && !projectile.alive && shotAvailableCounter == 30) {

			projectile.set(worldX + scale, worldY, direction, true, this);
			addProjectile(projectile);
			
			shotAvailableCounter = 0;
			
			projectile.playSE();
		}
	}
	
	// ITEM RETRIEVAL
	public boolean canObtainItem(Entity item) {
		
		Entity newItem = gp.eGenerator.getObject(item.name);
		newItem.amount = 1;	
		
		// STACKABLE
		if (newItem.stackable) {	
			
			// ITEM FOUND IN INVENTORY
			int index = searchInventory(newItem.name);
			if (index != -1) {		
				
				// NOT TOO MANY
				if (inventory.get(index).amount != 999) {
					inventory.get(index).amount++;
					return true;
				}
			}
			// NEW ITEM
			else {
				if (inventory.size() != maxInventorySize) {						
					inventory.add(newItem);					
					return true;
				}
			}	
		}
		// NOT STACKABLE
		else {
			if (inventory.size() != maxInventorySize) {
				if (this == gp.player) gp.player.getObject(newItem);				
				else inventory.add(newItem);
				return true;
			}
		}
		
		return false;
	}
	public int searchInventory(String item) {
		
		int itemIndex = -1;		
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).name.equals(item)) {
				itemIndex = i;
				break;
			}
		}		
		return itemIndex;
	}
	public int searchItemInventory(String item) {
		
		int itemIndex = -1;		
		for (int i = 0; i < gp.player.inventory_item.size(); i++) {
			if (gp.player.inventory_item.get(i).name.equals(item)) {
				itemIndex = i;
				break;
			}
		}		
		return itemIndex;
	}
	
	// ITEM-OBJECT INTERACTION
	public int getDetected(Entity user, Entity target[][], String targetName) {
		
		int index = -1;
		
		int nextWorldX = user.getLeftX();
		int nextWorldY = user.getTopY();
		
		// CHECK SURROUNDING OBJECTS
		switch(user.direction) {
			case "up":
			case "upleft":
			case "upright": nextWorldY = user.getTopY() - gp.player.speed; break;
			case "down":
			case "downleft":
			case "downright": nextWorldY = user.getBottomY() + gp.player.speed; break;
			case "left": nextWorldX = user.getLeftX() - gp.player.speed; break;
			case "right": nextWorldX = user.getRightX() + gp.player.speed; break;
		}
		
		// CHECK IF FOUND OBJECT IS TARGET
		int col = nextWorldX / gp.tileSize;
		int row = nextWorldY / gp.tileSize;		
		
		for (int i = 0; i < target[1].length; i++) {
			if (target[gp.currentMap][i] != null && 
				target[gp.currentMap][i].getCol() == col && 
				target[gp.currentMap][i].getRow() == row &&
				target[gp.currentMap][i].name.equals(targetName)) {
					index = i;
					break;					
			}
		}
		
		return index;
	}

	// PROJECTILE
	public void addProjectile(Projectile projectile) {
		for (int i = 0; i < gp.projectile[1].length; i++) {
			if (gp.projectile[gp.currentMap][i] == null) {
				gp.projectile[gp.currentMap][i] = projectile;
				break;
			}
		}
	}
	public boolean tossEntity() {
		
		boolean hit = false;
		
		throwCounter++;			
		if (throwCounter <= 28) {
			
//			gp.cChecker.checkTile(this);
//			if (!collisionOn) {
				tTime += tAnimationSpeed;				
				switch (direction) {
					case "up": 	
						worldY -= 3;
						break;
					case "down": 		
						if (throwCounter == 1) worldY += 60;
						else worldY += 4;						
						break;
					case "left": 						
						getTrajectory(-135);
						break;
					case "right": 
						getTrajectory(-45);						
						hitbox.y += gp.tileSize;						
						break;
				}	
//			}
//			else {
//				hit = true;
//			}
		}
		else {		
			hit = true;
			hitbox.x = hitboxDefaultX;
			hitbox.y = hitboxDefaultY;
		}
		
		return hit;
	}
	public void getTrajectory(double angle) {
		worldX = (int) (tSpeed * Math.cos(angle * Math.PI / 180.0) * tTime + xT);
		worldY = (int) (0.5 * tG * tTime * tTime + tSpeed * Math.sin(angle * Math.PI / 180.0) * tTime + yT);
	}
	
	// PARTICLES
	public void generateParticle(Entity generator, Entity target) {

		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxLife = generator.getParticleMaxLife();
		
		Particle p1 = new Particle(gp, generator, color, size, speed, maxLife, -2, -1);
		Particle p2 = new Particle(gp, generator, color, size, speed, maxLife, -2, 1);
		Particle p3 = new Particle(gp, generator, color, size, speed, maxLife, 2, -1);
		Particle p4 = new Particle(gp, generator, color, size, speed, maxLife, 2, 1);
		gp.particleList.addAll(Arrays.asList(p1, p2, p3, p4));
	}
	public Color getParticleColor() {
		Color color = new Color(0,0,0);
		return color;
	}	
	public int getParticleSize() {		
		int size = 0; // 0px
		return size;
	}	
	public int getParticleSpeed() {
		int speed = 0;
		return speed;		
	}	
	public int getParticleMaxLife() {
		int maxLife = 0;
		return maxLife;
	}	
	
	// SOUND EFFECTS
	public void playGetItemSE() {
		gp.playSE(5, 0);
	}
	public void playMoveObjectSE() {
		gp.playSE(4, 7);
	}
	public void playShockSE() {
		gp.playSE(3, 6);
	}
	public void playLiftSE() {
		gp.playSE(4, 10);
	}
	public void playThrowSE() {
		gp.playSE(4, 11);
	}
	
	// GET X,Y
	public int getLeftX() { return worldX + hitbox.x; }
	public int getRightX() { return worldX + hitbox.x + hitbox.width;}
	public int getTopY() { return worldY + hitbox.y; }
	public int getBottomY() { return worldY + hitbox.y + hitbox.height; }
	public int getCol() { return (worldX + hitbox.x) / gp.tileSize; }
	public int getRow() { return (worldY + hitbox.y) / gp.tileSize; }
	
	// ENEMY ROOM CHECK
	protected void checkEnemyRoom() {							
		if (isRoomClear()) {
			gp.removeTempEntity();
		}
	}
	private boolean isRoomClear() {		
		for (Entity e : gp.enemy_r[gp.currentMap]) {
			if (e != null && e.alive)
				return false;
		}		
		return true;
	}
	
	// MANAGE VALUES
	public void manageValues() {
		 
		// STUNNED TIME (1 SECOND)
		if (stunned) {
			stunnedCounter++;
			if (stunnedCounter > 60) {				
				stunned = false;
				stunnedCounter = 0;				
			}
		}
		
		// ENTITY SHIELD AFTER HIT
		if (invincible) {
			invincibleCounter++;
			
			// REFRESH TIME (1 SECOND)
			if (invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		
		// PROJECTILE REFRESH TIME (1/2 SECOND)
		if (shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}
		
		// REMOVE ITEM AFTER X SECONDS
		if (lifeDuration != -1) {
			lifeDuration--;
			if (lifeDuration == 0)
				dying = true;
		}
	}
	public void resetCounter() {
		spriteCounter = 0;
		actionLockCounter = 0;		
		shotAvailableCounter = 0;		
		hpBarCounter = 0;
		invincibleCounter = 0;
		knockbackCounter = 0;
		dyingCounter = 0;
	}
	
	// IMAGE MANAGERS
	public BufferedImage setup(String imagePath) {	
		
		UtilityTool utility = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = utility.scaleImage(image, gp.tileSize, gp.tileSize);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	public BufferedImage setup(String imagePath, int width, int height) {
		
		UtilityTool utility = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = utility.scaleImage(image, width, height);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}	
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
	
	public boolean inFrame() {
		
		boolean inFrame = false;
		
		// PLAYER AWAY FROM CENTER OF CAMERA
		if (offCenter()) {
			inFrame = true;
		}
		else {
			// WITHIN SCREEN BOUNDARY
			if (worldX + gp.tileSize * 5 > gp.player.worldX - gp.player.screenX &&
					worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
					worldY + gp.tileSize * 5 > gp.player.worldY - gp.player.screenY &&
					worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				inFrame = true;
			}	
		}
		
		return inFrame;
	}
	
	// DRAW
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
								
		// DRAW TILES WITHIN SCREEN BOUNDARY
		if (inFrame()) {
			
			if (hookGrabbed) {
				image = this.image1;			
			}
			else if (buzzing) {
				if (spriteNum == 1) image = buzzUp1;
				else if (spriteNum == 2) image = buzzUp2;
			}
			else {							
				switch (direction) {
					case "up":
					case "upleft":
					case "upright":
						if (attacking) {
							tempScreenY -= up1.getHeight();
							if (attackNum == 1) image = attackUp1;
							else if (attackNum == 2) image = attackUp2;
							else if (attackNum == 3) image = attackUp3;	
						}		
						else {							
							if (spriteNum == 1) image = up1;
							else if (spriteNum == 2) image = up2;	
							else if (spriteNum == 3) image = up3;	
						}
						break;
					case "down":
					case "downleft":
					case "downright":
						if (attacking) {		
							if (attackNum == 1) image = attackDown1;
							else if (attackNum == 2) image = attackDown2;	
							else if (attackNum == 3) image = attackDown3;	
						}	
						else {
							if (spriteNum == 1) image = down1;
							else if (spriteNum == 2) image = down2;	
							else if (spriteNum == 3) image = down3;	
						}					
							
						break;
					case "left":					
						if (attacking) {
							tempScreenX -= left1.getWidth();
							if (attackNum == 1)	image = attackLeft1;							
							else if (attackNum == 2) image = attackLeft2;
							else if (attackNum == 3) image = attackLeft3;	
						}		
						else {
							if (spriteNum == 1) image = left1;
							else if (spriteNum == 2) image = left2;	
							else if (spriteNum == 3) image = left3;	
						}
						break;
					case "right":
						if (attacking) {
							if (attackNum == 1) image = attackRight1;
							else if (attackNum == 2) image = attackRight2;
							else if (attackNum == 3) image = attackRight3;	
						}	
						else {
							if (spriteNum == 1) image = right1;
							else if (spriteNum == 2) image = right2;	
							else if (spriteNum == 3) image = right3;	
						}								
						break;
				}
			}
			
			// AVOIDS BUG WITH ZORA ATTACKING SPRITE
			if (name.equals(EMY_Zora.emyName)) {
				offCenter();
			}
						
			// ENEMY IS HIT
			if (invincible) {
				
				// DISPLAY HP
				hpBarOn = true;
				hpBarCounter = 0;	
				
				// FLASH OPACITY
				hurtAnimation(g2);
			}		
			
			if (captured) changeAlpha(g2, 0.7f);
			
			if (dying) dyingAnimation(g2);	
			
			if (thrown) {
				g2.setColor(Color.BLACK);
				switch (direction) {
					case "up": 	
						g2.fillOval(tempScreenX + 5, tempScreenY + 70 - throwCounter, 38, 10);
						break;
					case "down": 			
						g2.fillOval(tempScreenX + 5, tempScreenY + 70 - throwCounter, 38, 10);
						break;
					case "left": 				
						break;
					case "right": 
						break;
				}	
			}
			
			g2.drawImage(image, tempScreenX, tempScreenY, null);	
			
			if (locked) {	
				
				// LOCKON IMAGE X, Y				
				tempScreenX = getScreenX() - 10;
				tempScreenY = getScreenY() - 10;								
				
				lockedImage = setup("/enemy/lockon", gp.tileSize + 20, gp.tileSize + 20);				
				changeAlpha(g2, 0.8f);
				g2.drawImage(lockedImage, tempScreenX, tempScreenY, null);
			}
			
			// DRAW HITBOX
			if (gp.keyH.debug) {
				g2.setColor(Color.RED);
				g2.drawRect(tempScreenX + hitbox.x, tempScreenY + hitbox.y, hitbox.width, hitbox.height);
			}
			
			// RESET OPACITY
			changeAlpha(g2, 1f);			
		}
	}
	
	private boolean offCenter() {
		
		boolean offCenter = false;
		
		tempScreenX = getScreenX();
		tempScreenY = getScreenY();
		
		if (gp.player.worldX < gp.player.screenX) {
			tempScreenX = worldX;
			offCenter = true;
		}
		if (gp.player.worldY < gp.player.screenY) {
			tempScreenY = worldY;
			offCenter = true;
		}
		
		// FROM PLAYER TO RIGHT-EDGE OF SCREEN
		int rightOffset = gp.screenWidth - gp.player.screenX;		
		
		// FROM PLAYER TO RIGHT-EDGE OF WORLD
		if (rightOffset > gp.worldWidth - gp.player.worldX) {
			tempScreenX = gp.screenWidth - (gp.worldWidth - worldX);
			offCenter = true;
		}			
		
		// FROM PLAYER TO BOTTOM-EDGE OF SCREEN
		int bottomOffSet = gp.screenHeight - gp.player.screenY;
		
		// FROM PLAYER TO BOTTOM-EDGE OF WORLD
		if (bottomOffSet > gp.worldHeight - gp.player.worldY) {			
			tempScreenY = gp.screenHeight - (gp.worldHeight - worldY);
			offCenter = true;
		}
		
		return offCenter;
	}
}