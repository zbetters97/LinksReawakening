package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import application.GamePanel;
import application.UtilityTool;
import data.Progress;
import entity.enemy.EMY_Beetle;
import entity.enemy.EMY_Stalfos;
import entity.enemy.EMY_Zora;
import entity.projectile.PRJ_Seed;
import entity.projectile.Projectile;

public class Entity {
	
	public enum Action {
		IDLE, AIMING, CARRYING, CHARGING, DIGGING, GRABBING, GUARDING, 
		JUMPING, PUSHING, ROLLING, RUNNING, SOARING, SWIMMING, SWINGING, THROWING;
	}
	
	protected List<Action> disabled_actions = Arrays.asList(
			Action.AIMING, Action.CARRYING, Action.CHARGING,
			Action.JUMPING, Action.PUSHING, Action.ROLLING, 
			Action.SOARING, Action.SWIMMING, Action.THROWING
	);
	
	protected GamePanel gp;
	
	// GENERAL ATTRIBUTES
	public int worldX, worldY;	
	public int safeWorldX = 0, safeWorldY = 0;
	protected int worldXStart;
	protected int worldYStart;	
	protected int tempScreenX;
	protected int tempScreenY;
	public int bounds = 999;	
	public String direction = "down";	
	
	public int type;
	public String name;			
	public int life, maxLife; // 1 life = 1/4 heart
	public int speed, defaultSpeed, animationSpeed, runSpeed;	
	public boolean drawing = true;
	public boolean temp = false;
	public boolean sleep = false;	
	public boolean collision = true;
	public boolean collisionOn = false;		
	public boolean onGround = true;	
	
	// SPRITE HANDLING
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public BufferedImage image, image1, image2, image3,
							up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3,							
							attackUp1, attackUp2, attackUp3, attackDown1, attackDown2, attackDown3,
							attackLeft1, attackLeft2, attackLeft3, attackRight1, attackRight2, attackRight3,							
							guardUp1, guardUp2, guardDown1, guardDown2, 
							guardLeft1, guardLeft2, guardRight1, guardRight2,							
							grabUp1, grabDown1, grabLeft1, grabRight1,							
							buzzUp1, buzzUp2,
							lockedImage = setup("/enemy/lockon", 48 + 20, 48 + 20);
		
	// CHARACTER ATTRIBUTES	
	public Action action;	
	public int attack;
	public int rupees;
	public int arrows, maxArrows;
	public int bombs, maxBombs;	
	public Entity currentWeapon, currentShield, currentItem;
	public Projectile projectile;		
	public boolean hasItemToGive = false;	
	public boolean hasCutscene = false;	
	public boolean canMove = true;
	public boolean canSwim = false;	
	public boolean diving = false;
	public boolean moving = false;
	public boolean onPath = false;
	public boolean pathCompleted = false;		
	public boolean guarded = false;
	public boolean captured = false;
	public boolean capturable = false;	
	public boolean attacking = false;
	public boolean teleporting = false;
	public boolean buzzing = false;		
	public Entity capturedTarget;
		
	// DIALOGUE
	public String dialogues[][] = new String[20][20];
	public int dialogueSet = 0;
	public int dialogueIndex = 0;		
	public String responses[][] = new String[10][3];
				
	// LIFE	
	public boolean alive = true;
	public boolean dying = false;
	public int dyingCounter = 0;	
	public boolean hpBarOn = false;
	public int hpBarCounter = 0;
	
	// DEFAULT HITBOX
	public Rectangle hitbox = new Rectangle(0, 0, 48, 48);
	public int hitboxDefaultX = hitbox.x;
	public int hitboxDefaultY = hitbox.y;
	public int hitboxDefaultWidth = hitbox.width;
	public int hitboxDefaultHeight = hitbox.height;
	
	// WEAPON HITBOX
	public Rectangle attackbox = new Rectangle(0, 0, 0, 0);
	
	// LOCKON
	public boolean lockon = false;
	public boolean locked = false;	
	public Entity lockedTarget;	
	public String lockonDirection = "";
	
	// ATTACK	
	public int attackNum = 1;
	public int attackCounter = 0;
	public int actionLockCounter = 0;	
	public boolean attackCanceled = false;
	public int swingSpeed1;
	public int swingSpeed2;	
	
	// SPIN ATTACK
	public boolean spinning = false;
	public int spinNum = 0;
	public int spinCounter = 0;
	public int charge = 0;

	// KNOCKBACK
	public boolean knockback = false;
	public int knockbackCounter = 0;
	public String knockbackDirection = "";
	
	// STUNNED
	public boolean canStun = false;
	public boolean stunned = false;
	public int stunnedCounter = 0;
	public int guardCounter = 0;
	public boolean critical = false;
	
	// INVINCIBILITY
	public boolean invincible = false;
	public int invincibleCounter = 0;
	public boolean transparent = false;
	
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
	public boolean grabbable = false;
	public boolean grabbed = false;
	public boolean hookGrabbable = false;
	public boolean hookGrabbed = false;
	public boolean pressable = false;
	public boolean active = false;	
	public boolean switchedOn = false;
	public boolean opened = false;
	public boolean opening = false;
	public boolean closing = false;
	public boolean turning = false;	
	
	// THROWING 
	public boolean thrown = false;
	public Entity grabbedObject;
	protected int throwCounter = 0;	
	public double tTime = 0;
	public double xT = 0;
	public double yT = 0;
	public double tSpeed = 0.25;
	public double tG = 0.00065;
	public double tAnimationSpeed = 25;
	public int tWorldY = 0;
	
	// PROJECTILE ATTRIBUTES
	public Entity user;
	public int shotAvailableCounter;
	public boolean canPickup = false;
	
	// ITEM ATTRIBUTES
	public String description = "";
	public String getDescription = "";
	public int price, value;
	public int attackValue;	
	public int knockbackPower = 0;
	public int amount = 1;
	public int useCost;		
	public int lifeDuration = -1;
	public boolean stackable = false;		
	
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
	public final int type_consumable = 5;
	public final int type_collectable = 6;
	public final int type_item = 7;
	public final int type_key = 8;
	public final int type_boss_key = 9;
	
	// OBJECT TYPES
	public final int type_projectile = 10;	
	public final int type_obstacle = 11;
	public final int type_obstacle_i = 12;
	public final int type_pickupOnly = 13;
	
	// CONSTRUCTOR
	public Entity(GamePanel gp) {
		this.gp = gp;
		getImage();
		getAttackImage();
	}
	
	// CHILD ONLY		
	public void getImage() { }
	public void getAttackImage() { }
	public void setAction() { }	
	public void move(String direction) { }
	public void setPath(int c, int r) { }	
	public void use() {	}
	public boolean use(Entity user) { return true; }
	public void setLoot(Entity loot) { }
	public boolean setCharge(Entity user) { return true; }	
	public void interact() { }
	public void speak() { }	
	public void explode() {	}
	public void damageReaction() { }	
	public void checkDrop() { checkEnemyRoom(); }
	public void resetValues() { }	
	public void playSE() { }
	public void playOpenSE() { }
	public void playCloseSE() { }
	public void playAttackSE() { }
	public void playChargeSE() { }
	public void playHurtSE() { }
	public void playDeathSE() { }	
	
	// UPDATER
	public void update() {
		
		if (sleep || stunned) { manageValues(); return; }	
		if (captured) { isCaptured(); manageValues(); return; }
		if (knockback) { knockbackEntity();	manageValues(); return; }	
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
	
	// COLLISION CHECKER
	protected void checkCollision() {		
		
		collisionOn = false;
		gp.cChecker.checkTile(this);	
		gp.cChecker.checkPit(this, false);	
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.enemy);
		gp.cChecker.checkEntity(this, gp.obj);
		gp.cChecker.checkEntity(this, gp.obj_i);
		gp.cChecker.checkEntity(this, gp.iTile);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkObject_I(this, false);
		
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		if ((type == type_enemy || type == type_boss) && contactPlayer) 
			damagePlayer(attack);  	
	}
	private void checkThrownCollision() {
		gp.cChecker.checkTile(this);
		gp.cChecker.checkEntity(this, gp.iTile);
		gp.cChecker.checkEntity(this, gp.obj);
		gp.cChecker.checkEntity(this, gp.obj_i);
	}

	// CAPTURED
	public void isCaptured() {		
		
		worldXStart = worldX;
		worldYStart = worldY;
		
		if (gp.keyH.actionPressed) { attacking = true; }
		if (attacking) { attacking(); return; }		
		if (gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.leftPressed || gp.keyH.rightPressed) {
			walking();
		}	
	}
	public void walking() {
		getDirection();
		
		checkCollision();
		if (!collisionOn) {
			if (lockon) {
				switch (lockonDirection) {
					case "up": worldY -= speed; break;		
					case "down": worldY += speed; break;		
					case "left": worldX -= speed; break;
					case "right": worldX += speed; break;
				}	
			}
			else {
				switch (direction) {
					case "up": worldY -= speed; break;		
					case "down": worldY += speed; break;		
					case "left": worldX -= speed; break;
					case "right": worldX += speed; break;
				}	
			}
		}

		cycleSprites();
	}
	public void getDirection() {
		if (lockon) {
			if (gp.keyH.upPressed) lockonDirection = "up";
			if (gp.keyH.downPressed) lockonDirection = "down";
			if (gp.keyH.leftPressed) lockonDirection = "left";
			if (gp.keyH.rightPressed) lockonDirection = "right";				
		}
		else {
			if (gp.keyH.upPressed) direction = "up";
			if (gp.keyH.downPressed) direction = "down";
			if (gp.keyH.leftPressed) direction = "left";
			if (gp.keyH.rightPressed) direction = "right";				
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
	
	// DIALOGUE
	public void startDialogue(Entity entity, int setNum) {
		dialogueSet = setNum;
		gp.ui.npc = entity;		
		gp.gameState = gp.dialogueState;
	}
	public void startDialogue(Entity entity, int setNum, int responseSet, int offset) {
		gp.ui.npc = entity;	
		dialogueSet = setNum;
		gp.ui.responseSet = responseSet;	
		gp.ui.offset = offset;		
		gp.ui.response = true;
		gp.gameState = gp.dialogueState;
	}	
	
	// PATH FINDING
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
	public boolean playerWithinBounds() {
		
		boolean playerWithinBounds = true;
		
		int tileDistance = (Math.abs(worldXStart - gp.player.worldX) + Math.abs(worldYStart - gp.player.worldY)) / gp.tileSize;

		if (tileDistance > bounds)
			playerWithinBounds = false;
		
		return playerWithinBounds;		
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
	public int getGoalCol(Entity target) {
		int goalCol = (target.worldX + target.hitbox.x) / gp.tileSize;
		return goalCol;
	}
	public int getGoalRow(Entity target) {
		int goalRow = (target.worldY + target.hitbox.y) / gp.tileSize;
		return goalRow;
	}
	
	// ATTACKING
	public Entity getEnemy(Entity entity) {
		
		Entity enemy = null;
		
		int enemyIndex = gp.cChecker.checkEntity(entity, gp.enemy);
		if (enemyIndex != -1) {
			enemy = gp.enemy[gp.currentMap][enemyIndex];
		}
		
		return enemy;
	}
	protected boolean isAttacking(int rate, int straight, int horizontal) {
				
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
	protected void attacking() {
		
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
			
			// SAVE X/Y
			int currentWorldX = worldX;
			int currentWorldY = worldY;				
			
			// ENEMY ATTACKING
			if ((type == type_enemy || type == type_boss) && !captured) {
								
				// ADJUST X/Y 
				switch (direction) {
					case "up": 						
					case "upleft":				
					case "upright":
						worldY -= attackbox.height + hitbox.y; 
						hitbox.height = attackbox.height;
						break; 
					case "down": 
					case "downleft":
					case "downright": 
						worldY += attackbox.height - hitbox.y; 
						hitbox.height = attackbox.height;
						break;				
					case "left": 
						worldX -= attackbox.width; 
						hitbox.width = attackbox.width;
						break;					
					case "right": 
						worldX += attackbox.width - hitbox.y; 
						hitbox.width = attackbox.width;
						break;
				}	
				
				if (gp.cChecker.checkPlayer(this)) 
					damagePlayer(attack);				
			}
			// PLAYER ATTACKING
			else {				
				
				// ADJUST X/Y 
				switch (direction) {
					case "up": 						
					case "upleft":				
					case "upright":
						worldY -= attackbox.height + hitbox.y; 
						hitbox.height = attackbox.height;
						break; 
					case "down": 
					case "downleft":
					case "downright": 
						worldY += attackbox.height - hitbox.y; 
						hitbox.height = attackbox.height;
						break;				
					case "left": 
						worldX -= attackbox.width; 
						hitbox.width = attackbox.width;
						break;					
					case "right": 
						worldX += attackbox.width - hitbox.y; 
						hitbox.width = attackbox.width;
						break;
				}	
				
				// CHECK IF ATTACK LANDS ON ENEMY
				Entity enemy = getEnemy(this);		
				if (enemy != null) {				
					if (currentWeapon == null) 			
						gp.player.damageEnemy(enemy, this, attack, 0);
					else
						gp.player.damageEnemy(enemy, this, attack, currentWeapon.knockbackPower);
				}
				
				// CHECK INTERACTIVE TILE
				int iTileIndex = gp.cChecker.checkEntity(gp.player, gp.iTile);
				gp.player.damageInteractiveTile(iTileIndex, this);				
				
				// CHECK IF ATTACK LANDS ON PROJECTILE
				int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
				gp.player.damageProjectile(projectileIndex);				
			}
						
			// RESTORE HITBOX			
			worldX = currentWorldX;
			worldY = currentWorldY;
			hitbox.width = hitboxDefaultWidth;
			hitbox.height = hitboxDefaultHeight;
		}
		
		if (attackCounter > swingSpeed2) {
			
			if (this == gp.player && currentWeapon != null) { 
				
				// CHARGE SPIN ATTACK
				if (gp.keyH.actionPressed) {
					currentWeapon.playChargeSE();
					action = Action.CHARGING;
				}
				// RESET IMAGE/VALUES
				else {
					attackNum = 1;
					attackCounter = 0;				
					attacking = false;
					attackCanceled = false;
					gp.keyH.actionPressed = false;
				}
			}			
			// RESET IMAGE/VALUES
			else {
				attackNum = 1;
				attackCounter = 0;				
				attacking = false;
				attackCanceled = false;
			}
		}
	}		
	protected void spinAttacking() {
		
		attackCounter++;
		if (attackCounter >= 5) {
			attackCounter = 0;
			
			// ROTATE PLAYER CLOCKWISE
			if (spinNum != 4) {
				switch (direction) {
					case "up":
					case "upleft": 
					case "upright": direction = "right"; break;
					case "right": direction = "down"; break;
					case "down": 
					case "downleft": 
					case "downright": direction = "left"; break;
					case "left": direction = "up"; break;
				}		
			}
			
			// SAVE HITBOX
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			
			// ADJUST X/Y AND HITBOX
			// SWING DETECTION AS FOLLOWS (P = PLAYER):
			/* L U U
			 * L P R
			 * D D R */
			switch (direction) {
				case "up": 						
				case "upleft":				
				case "upright":
					worldY -= attackbox.height + hitbox.y; 
					hitbox.height = attackbox.height;
					hitbox.width *= 2; 
					break; 
				case "down": 
				case "downleft":
				case "downright": 					
					worldX -= attackbox.width; 
					worldY += attackbox.height - hitbox.y; 
					hitbox.height = attackbox.height;
					hitbox.width *= 2; 
					break;				
				case "left": 
					worldX -= attackbox.width; 
					worldY -= attackbox.height; 
					hitbox.width = attackbox.width;
					hitbox.height *= 2;  
					break;					
				case "right": 
					worldX += attackbox.width - hitbox.y; 
					hitbox.width = attackbox.width;
					hitbox.height *= 2;  
					break;
			}	
							
			// CHECK IF ATTACK LANDS ON ENEMY
			Entity enemy = getEnemy(this);		
			if (enemy != null) {			
				int damage = attack++;
				gp.player.damageEnemy(enemy, this, damage, currentWeapon.knockbackPower);
			}
			
			// CHECK INTERACTIVE TILE
			int iTileIndex = gp.cChecker.checkEntity(gp.player, gp.iTile);
			gp.player.damageInteractiveTile(iTileIndex, this);				
			
			// CHECK IF ATTACK LANDS ON PROJECTILE
			int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
			gp.player.damageProjectile(projectileIndex);			
						
			// RESET X/Y AND HITBOX
			worldX = currentWorldX;
			worldY = currentWorldY;
			hitbox.width = hitboxDefaultWidth;
			hitbox.height = hitboxDefaultHeight;
			
			// REPEAT 5 TIMES
			spinNum++;
			if (spinNum == 5) {
				spinNum = 0;
				spinning = false;
				attacking = false;
				attackCanceled = false;				
			}
		}
	}
	
	// DAMAGE
	protected void damagePlayer(int attack) {
		
		if (!gp.player.invincible && gp.player.alive && !teleporting) {
						
			int damage = attack;
			
			if (knockbackPower > 0) setKnockback(gp.player, this, knockbackPower);
			
			String guardDirection = getOppositeDirection(direction);
			if (gp.player.action == Action.GUARDING && gp.player.direction.equals(guardDirection)) {
				gp.player.playBlockSE();
												
				if (name.equals(PRJ_Seed.prjName)) {
					direction = gp.player.direction;
					collisionOn = false;
					user = gp.player;
					life = maxLife;
				}
				else if (name.equals(EMY_Beetle.emyName)) {
					attacking = true;
					setKnockback(this, gp.player, 1);
				}
				else {				
					if (gp.player.guardCounter < 10) {
						stunned = true;
						critical = true;
						attacking = false;
						attackCounter = 0;
					}
					
					if (knockbackPower == 0) setKnockback(this, gp.player, 1);
					damage = 0;
				}
			}
			else {
				gp.player.playHurt();
				
				if (damage < 0) damage = 0;	
				gp.player.life -= damage;
				gp.player.transparent = true;
			}			

			gp.player.invincible = true;
		}
	}
	protected String getOppositeDirection(String direction) {
		
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
	protected String getPlayerDirection() {
		
		String playerDirection = "";
		
		switch (gp.player.direction) {
			case "up": 
			case "upleft": 
			case "upright": playerDirection = "up"; break;
			case "down": 
			case "downleft": 
			case "downright": playerDirection = "down"; break;
			case "left": playerDirection = "left"; break;
			case "right": playerDirection = "right"; break;
		}
		
		return playerDirection;
	}	
	protected void hurtAnimation(Graphics2D g2) {	
		if (invincibleCounter % 5 == 0) 
			changeAlpha(g2, 0.2f);
	}	
	protected void dyingAnimation(Graphics2D g2) {
		
		transparent = false;
		
		dyingCounter++;			
		if (dyingCounter % 5 == 0) 
			changeAlpha(g2, 0.2f);
		
		// LONGER DYING ANIMATION FOR BOSSES
		if (type == type_boss) {
			if (dyingCounter > 180) {
				alive = false;
				gp.pFinder.pathList.clear();
			}
		}
		else {
			if (dyingCounter > 40) {
				alive = false;		
				gp.pFinder.pathList.clear();
			}
		}		
	}
	protected void dropItem(Entity droppedItem) { 								
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
	protected void knockbackEntity() {
		
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
	
	// ENEMY ROOM CHECK
	private void checkEnemyRoom() {							
		if (isRoomClear()) {
			gp.removeTempEntity(false);
			Progress.canSave = true;
		}
	}
	private boolean isRoomClear() {		
		for (Entity e : gp.enemy[gp.currentMap]) {
			if (e != null && e.alive && e.temp)
				return false;
		}		
		return true;
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
	public void addProjectile(Projectile projectile) {
		for (int i = 0; i < gp.projectile[1].length; i++) {
			if (gp.projectile[gp.currentMap][i] == null) {
				gp.projectile[gp.currentMap][i] = projectile;
				break;
			}
		}
	}
	
	// ITEM RETRIEVAL
	public boolean canObtainItem(Entity item) {
		
		Entity newItem = gp.eGenerator.getItem(item.name);
		newItem.amount = 1;	
		
		// STACKABLE
		if (newItem.stackable) {	
			
			// ITEM FOUND IN INVENTORY
			int index = searchInventory(newItem.name);
			if (index != -1) {		
				
				// NOT TOO MANY
				if (inventory.get(index).amount != 999) {
					inventory.get(index).amount++;
					newItem.playSE();
					return true;
				}
			}
			// NEW ITEM
			else {
				if (inventory.size() != maxInventorySize) {						
					inventory.add(newItem);	
					newItem.playSE();
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
	
	// THROWING
	protected boolean tossEntity() {
		
		boolean hit = false;
		
		throwCounter++;			
		if (throwCounter <= 29) {
			
			tTime += tAnimationSpeed;				
			switch (direction) {
				case "up":
				case "upleft":
				case "upright":			
					checkThrownCollision();
					if (!collisionOn) worldY -= 3;	
					else hit = true;					
					break;
				case "down": 
				case "downleft":
				case "downright":
					if (throwCounter == 1) worldY += 64;
					else {								
						checkThrownCollision();
						if (!collisionOn) worldY += 4;							
						else hit = true;						
					}
					break;
				case "left": 			
					checkThrownCollision();
					if (!collisionOn) getTrajectory(-135);	
					else { worldY = tWorldY; hit = true; }						
					break;
				case "right": 		
					checkThrownCollision();
					if (!collisionOn) getTrajectory(-45);
					else { worldY = tWorldY; hit = true; }
					break;
			}	
		}
		else {		
			hit = true;
		}
		
		return hit;
	}
	private void getTrajectory(double angle) {
		worldX = (int) (tSpeed * Math.cos(angle * Math.PI / 180.0) * tTime + xT);
		worldY = (int) (0.5 * tG * tTime * tTime + tSpeed * Math.sin(angle * Math.PI / 180.0) * tTime + yT);
	}
	
	// PARTICLES
	public void generateParticle(Entity generator) {

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
	
	// MANAGE VALUES
	public void manageValues() {
		 
		// STUNNED TIME (1 SECOND)
		if (stunned) {
			attacking = false;
			stunnedCounter++;
			if (stunnedCounter > 60) {				
				if (critical) {
					if (stunnedCounter > 120) {
						stunned = false;
						critical = false;
						stunnedCounter = 0;				
					}
				}
				else {
					stunned = false;
					stunnedCounter = 0;			
				}
			}
		}
		
		// SHIELD AFTER HIT
		if (invincible) {
			invincibleCounter++;
			
			// REFRESH TIME (1 SECOND)
			if (invincibleCounter > 60) {
				invincibleCounter = 0;
				invincible = false;
				transparent = false;
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
	
	// DRAW
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
								
		// DRAW TILES WITHIN SCREEN BOUNDARY
		if (inFrame() && drawing) {
			
			if (hookGrabbed) {
				switch (direction) {
					case "up": image = grabUp1; break;
					case "down": image = grabDown1; break;
					case "left": image = grabLeft1; break;
					case "right": image = grabRight1; break;				
				}
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
			
			// AVOIDS BUG WITH ATTACKING SPRITE
			if (name.equals(EMY_Beetle.emyName) || name.equals(EMY_Stalfos.emyName) ||
					name.equals(EMY_Zora.emyName)) {
				offCenter();
			}
						
			// ENEMY HIT, FLASH OPACITY
			if (transparent) hurtAnimation(g2);	
			
			if (action == Action.JUMPING) drawShadow(g2, 5, 50, tempScreenY + 40);
			if (thrown) drawShadow(g2, 5, 70, tWorldY - gp.player.worldY + gp.player.screenY + 40);						
			if (captured) changeAlpha(g2, 0.7f);	
			if (dying) dyingAnimation(g2);	

			g2.drawImage(image, tempScreenX, tempScreenY, null);
					
			// LOCKON IMAGE
			if (locked) {						
				g2.drawImage(lockedImage, getScreenX() - 10, getScreenY() - 10, null);
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
	private void drawShadow(Graphics2D g2, int offsetX, int offsetYUD, int offsetYLR) {
		
		g2.setColor(Color.BLACK);
		switch (direction) {
			case "up":
			case "upleft":
			case "upright":
				g2.fillOval(tempScreenX + offsetX, tempScreenY + offsetYUD - throwCounter, 38, 10);
				break;
			case "down": 	
			case "downleft":
			case "downright":
				g2.fillOval(tempScreenX + offsetX, tempScreenY + offsetYUD - throwCounter, 38, 10);
				break;
			case "left": 	
				g2.fillOval(tempScreenX + offsetX, offsetYLR, 38, 10);
				break;
			case "right": 
				g2.fillOval(tempScreenX + offsetX, offsetYLR, 38, 10);
				break;
		}	
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
	
	// GET X,Y
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
	private int getLeftX() { return worldX + hitbox.x; }
	private int getRightX() { return worldX + hitbox.x + hitbox.width;}
	private int getTopY() { return worldY + hitbox.y; }
	private int getBottomY() { return worldY + hitbox.y + hitbox.height; }
	private int getCol() { return (worldX + hitbox.x) / gp.tileSize; }
	private int getRow() { return (worldY + hitbox.y) / gp.tileSize; }
	
	// SOUND EFFECTS
	public void playMoveObjectSE() {
		gp.playSE(4, 7);
	}
	public void playShockSE() {
		gp.playSE(3, 6);
	}
}