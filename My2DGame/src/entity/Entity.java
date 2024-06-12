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

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	GamePanel gp;
	
	// GENERAL ATTRIBUTES
	public int worldX, worldY;		
	public String name;	
		
	// CHARACTER ATTRIBUTES
	public int type;
	public String direction = "down";
	public int life, maxLife; // 1 life = half heart
	public int arrows, maxArrows;
	public int bombs, maxBombs;
	public int speed, defaultSpeed, runSpeed, animationSpeed;
	public int strength, dexterity;
	public int attack, defense;
	public int level, exp, nextLevelEXP;
	public int rupees;
	public Entity currentWeapon, currentShield, currentItem, currentLight;
	public Projectile projectile;
	public boolean collisionOn = false;
	public boolean hasItem = false;
	public boolean hasItemToGive = false;
	
	public boolean onPath = false;
	public boolean pathCompleted = false;
	
	// SPRITES
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage die1, die2, die3, die4;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, 
							attackLeft1, attackLeft2, attackRight1, attackRight2;
	public BufferedImage guardUp1, guardUp2, guardDown1, guardDown2, 
							guardLeft1, guardLeft2, guardRight1, guardRight2;
	public BufferedImage lockedImage;
	
	// DIALOGUE
	public String dialogues[][] = new String[20][20];
	public int dialogueSet = 0;
	public int dialogueIndex = 0;		
	
	// LIFE
	public boolean hpBarOn = false;
	public int hpBarCounter = 0;
	
	public Entity attacker;
	public boolean knockback = false;
	public int knockbackCounter = 0;
	public String knockbackDirection = "";
	
	public boolean lockon = false;
	public Entity lockedTarget;	
	public String lockonDirection;
	public boolean isLocked = false;	
	
	public boolean invincible = false;
	public int invincibleCounter = 0;
	public boolean transparent = false;
	
	public boolean alive = true;
	public boolean dying = false;
	public int dyingCounter = 0;	

	// DEFAULT hitbox
	public Rectangle hitbox = new Rectangle(0, 0, 48, 48);
	public int hitboxDefaultX, hitboxDefaultY, hitboxDefaultWidth, hitboxDefaultHeight;	
	
	// ATTACKING
	public int swingSpeed1;
	public int swingSpeed2;
	public int actionLockCounter = 0;
	public boolean attacking;	
	public int attackCounter = 0;
	public int attackNum = 1;	
	public int shotAvailableCounter;
	public boolean attackCanceled = false;
	
	// WEAPON hitbox
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
		
	// ITEM ATTRIBUTES
	public int value, attackValue, defenseValue;
	public int knockbackPower = 0;
	public String description = "";
	public int price;
	public int useCost;	
	public int amount = 1;
	public int lifeDuration = -1;
	public boolean stackable = false;
	public int lightRadius;
	public boolean hookGrab = false;
	
	// INVENTORY
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	
	// OBJECT ATTRIBUTES
	public BufferedImage image1, image2, image3;	
	public boolean collision = false;
	public boolean diggable = false;
	public boolean canExplode;
	public Entity loot;
	public boolean opened = false;
	
	// CHARACTER TYPES
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_enemy = 2;
	
	// INVENTORY TYPES
	public final int type_sword = 3;
	public final int type_shield = 4;	
	public final int type_item = 5;
	public final int type_collectable = 6;
	public final int type_consumable = 7;
	public final int type_light = 8;
	
	// MAP TYPES
	public final int type_obstacle = 9;
		
	// CONSTRUCTOR
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	// CHILD ONLY	
	public void setAction() { }	
	public void damageReaction() { }	
	public void checkDrop() { }
	public void use() {	}
	public boolean use(Entity user) { return true; }
	public void interact() { }
	public void explode() {	}
	public void setLoot(Entity loot) { }
	public void playSE() { }
	public void playAttackSE() { }
	public void playHurtSE() { }
	public void playDeathSE() { }	
	
	// UPDATER
	public void update() {
		
		if (knockback) { knockbackEntity();	return; }
		if (attacking) { attacking(); }
		
		// CHILD CLASS
		setAction();
		
		checkCollision();
		if (!collisionOn) { 
						
			// move player in direction pressed
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
			
			if (type == type_npc || type == type_enemy) {
			
				// WALKING ANIMATION (only if no collision)
				spriteCounter++;
				if (spriteCounter > animationSpeed && animationSpeed != 0) { // speed of sprite change
					
					if (spriteNum == 1) spriteNum = 2;
					else if (spriteNum == 2) spriteNum = 1;
					
					spriteCounter = 0;
				}
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
		
		// REMOVE ITEMS AFTER X SECONDS
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
	
	// COLLISION CHECKER
	public void checkCollision() {		
		
		collisionOn = false;
		gp.cChecker.checkTile(this);		
		gp.cChecker.checkEntity(this, gp.iTile);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.enemy);
		gp.cChecker.checkObject(this, false);
		
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		if (this.type == type_enemy && contactPlayer) 
			damagePlayer(attack);  	
	}

	// DIALOGUE
	public void speak() { }	
	public void startDialogue(Entity entity, int setNum) {
		dialogueSet = setNum;
		gp.ui.npc = entity;		
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

	// PATH FINDING
	public void getDirection(int rate) {
		
		actionLockCounter++;			
		if (actionLockCounter == rate) {		
						
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
		if (getTileDistance(target) > distance) {
			onPath = false;
		}
	}	
	public int getTileDistance(Entity target) {
		int tileDistance = (getXdistance(target) + getYdistance(target)) / gp.tileSize;
		return tileDistance;
	}
	public int getXdistance(Entity target) { 
		int xDistance = Math.abs(worldX - target.worldX);
		return xDistance;
	}
	public int getYdistance(Entity target) { 
		int yDistance = Math.abs(worldY - target.worldY);
		return yDistance;
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
	public void isAttacking(int rate, int straight, int horizontal) {
				
		boolean targetInRange = false;
		int xDis = getXdistance(gp.player);
		int yDis = getYdistance(gp.player);
		
		// IF PLAYER IS WITHIN ATTACK BOX
		switch (direction) {
			case "up": 
				if (gp.player.worldY < worldY && yDis < straight && xDis < horizontal) {
					targetInRange = true;
				}
				break;
			case "down":
				if (gp.player.worldY > worldY && yDis < straight && xDis < horizontal) {
					targetInRange = true;
				}
				break;
			case "left": 
				if (gp.player.worldX < worldX && xDis < straight && yDis < horizontal) {
					targetInRange = true;
				}
				break;
			case "right": 
				if (gp.player.worldX > worldX && xDis < straight && yDis < horizontal) {
					targetInRange = true;
				}
				break;
		}
		
		// PLAYER IS WITHIN ATTACK BOX
		if (targetInRange) {
			
			// RANDOM CHANCE TO SWING WEAPON
			int i = new Random().nextInt(rate);
			if (i == 0) {
				attacking = true;
				spriteNum = 1;
				spriteCounter = 0;
			}
		} 		
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
		}		
		// ATTACK IMAGE 2
		if (swingSpeed2 >= attackCounter && attackCounter > swingSpeed1) {
			attackNum = 2;
			
			// CHECK IF WEAPON HITS TARGET	
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int hitBoxWidth = hitbox.width;
			int hitBoxHeight = hitbox.height;
			
			// ADJUST PLAYER'S X/Y 
			switch (direction) {
				case "up": worldY -= attackArea.height; break; 
				case "upleft": worldY -= attackArea.height; worldX -= attackArea.width; break; 
				case "upright": worldY -= attackArea.height; worldX += attackArea.width; break; 
				case "down": worldY += attackArea.height; break;
				case "downleft": worldY += attackArea.height; worldX -= attackArea.width; break;
				case "downright": worldY += attackArea.height; worldX += attackArea.width; break;					
				case "left": worldX -= attackArea.width; break;
				case "right": worldX += attackArea.width; break;
			}
			
			// CHANGE SIZE OF HIT BOX 
			hitbox.width = attackArea.width;
			hitbox.height = attackArea.height;
			
			// ENEMY ATTACKING
			if (type == type_enemy) {
				if (gp.cChecker.checkPlayer(this)) 
					damagePlayer(attack);				
			}
			// PLAYER ATTACKING
			else {
				
				// CHECK IF ATTACK LANDS ON ENEMY
				int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
				
				// PREVENT AXE GLITCH
				if(currentWeapon == null) 
					gp.player.damageEnemy(enemyIndex, this, attack, 0);
				else 
					gp.player.damageEnemy(enemyIndex, this, attack, currentWeapon.knockbackPower);
				
				// SHOOT SWORD BEAM
				if (enemyIndex == -1 && gp.keyH.actionPressed) {
					if (projectile.hasResource(this) && !projectile.alive && 
							shotAvailableCounter == 30 ) {
						projectile.playSE();
						
						projectile.set(worldX, worldY, direction, true, this);			
						addProjectile(projectile);					
						projectile.subtractResource(this);
						
						shotAvailableCounter = 0;
					}
				}
				
				// CHECK IF ATTACK LANDS ON PROJECTILE
				int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
				gp.player.damageProjectile(projectileIndex);	
				
				// SWINGING AXE
				if (gp.player.chopping) {				
					currentItem.playSE();
					
					// CHECK INTERACTIVE TILE
					int iTileIndex = gp.cChecker.checkEntity(gp.player, gp.iTile);
					gp.player.damageInteractiveTile(iTileIndex);	
				}
			}
			
			gp.player.chopping = false;
						
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
		
		if (!gp.player.invincible && gp.player.alive) {
									
			int damage = attack - gp.player.defense;
			
			String canGuardDirection = getOppositeDirection(direction);			
			if (gp.player.guarding && gp.player.direction.equals(canGuardDirection)) {
				gp.playSE(3, 13);			
				if (knockbackPower > 0) 
					setKnockback(gp.player, this, 1);
				damage = 0;
			}
			else {
				gp.player.playHurtSE();
				if (knockbackPower > 0) 
					setKnockback(gp.player, this, knockbackPower);

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
		
		if (dyingCounter > 40) 
			alive = false;		
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
	public void setKnockback(Entity target, Entity attacker, int knockbackPower) {			
		this.attacker = attacker;
		target.knockbackDirection = attacker.direction;
		target.speed += knockbackPower;
		target.knockback = true;
	}
	
	// ITEM HANDLING
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
	
	// OBJECT INTERACTION
	public boolean canObtainItem(Entity item) {
		
		Entity newItem = gp.eGenerator.getObject(item.name);
		newItem.amount = 1;	
		
		// IF STACKABLE ITEM
		if (newItem.stackable) {	
			
			// ITEM FOUND IN INVENTORY
			int index = searchItemInventory(newItem.name);
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
				if (this == gp.player) getObject(newItem);				
				else inventory.add(newItem);
				return true;
			}
		}
		
		return false;
	}
	public int searchItemInventory(String itemName) {
		
		// IF PLAYER HAS ITEM
		int itemIndex = -1;		
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).name.equals(itemName)) {
				itemIndex = i;
				break;
			}
		}		
		return itemIndex;
	}
	public void getObject(Entity item) {
		
		// INVENTORY ITEMS
		if (item.type == type_item) {
			hasItem = true;
		}
		else if (item.type == type_sword) {
			currentWeapon = item;
			attack = gp.player.getAttack();
		}
		else if (item.type == type_shield) {
			currentShield = item;
			defense = gp.player.getDefense();
		}
		else if (item.type == type_collectable) {
			item.use(this);
			return;
		}
		else if (item.type == type_consumable) {
			item.use(this);
			return;
		}	
		
		playGetItemSE();
		
		gp.ui.newItem = item;
		inventory.add(item);
		gp.ui.currentDialogue = "You got the " + item.name + "!";		
		gp.gameState = gp.itemGetState;
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
	public int getLeftX() {
		return worldX + hitbox.x;
	}
	public int getRightX() {
		return worldX + hitbox.x + hitbox.width;
	}
	public int getTopY() {
		return worldY + hitbox.y;
	}
	public int getBottomY() {
		return worldY + hitbox.y + hitbox.height;
	}
	public int getCol() {
		return (worldX + hitbox.x) / gp.tileSize;
	}
	public int getRow() {
		return (worldY + hitbox.y) / gp.tileSize;
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
		gp.playSE(3, 1);
	}
	public void playMoveObjectSE() {
		gp.playSE(3, 12);
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
		
		// convert world map coordinates to screen coordinates
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
						
		// only draw tiles within player boundary
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			if (hookGrab) image = this.image1;			
			else {							
				switch (direction) {
					case "up":
					case "upleft":
					case "upright":
						if (attacking) {
							screenY -= gp.tileSize;
							if (attackNum == 1) image = attackUp1;
							if (attackNum == 2) image = attackUp2;
						}		
						else {							
							if (spriteNum == 1) image = up1;
							if (spriteNum == 2) image = up2;	
						}
						break;
					case "down":
					case "downleft":
					case "downright":
						if (attacking) {		
							if (attackNum == 1) image = attackDown1;
							if (attackNum == 2) image = attackDown2;	
						}	
						else {
							if (spriteNum == 1) image = down1;
							if (spriteNum == 2) image = down2;	
						}					
							
						break;
					case "left":					
						if (attacking) {
							screenX -= gp.tileSize;
							if (attackNum == 1)	image = attackLeft1;							
							if (attackNum == 2) image = attackLeft2;	
						}		
						else {
							if (spriteNum == 1) image = left1;
							if (spriteNum == 2) image = left2;	
						}
						break;
					case "right":
						if (attacking) {
							if (attackNum == 1) image = attackRight1;
							if (attackNum == 2) image = attackRight2;
						}	
						else {
							if (spriteNum == 1) image = right1;
							if (spriteNum == 2) image = right2;	
						}								
						break;
				}
			}
			
			// ENEMY HP BAR
			if (type == 2 && hpBarOn) {		
				double oneScale = (double)gp.tileSize / maxLife; // LENGTH OF HALF HEART
				double hpBarValue = oneScale * life; // LENGTH OF ENEMY HEALTH
				
				g2.setColor(new Color(35,35,35)); // DARK GRAY OUTLINE
				g2.fillRect(screenX-1, screenY - 16, gp.tileSize + 2, 10);
				
				g2.setColor(new Color(255,0,30)); // RED BAR
				g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 8);
				
				// REMOVE BAR AFTER 10 SECONDS
				hpBarCounter++;
				if (hpBarCounter > 600) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
			}
			
			// ENEMY IS HIT
			if (invincible) {
				
				// DISPLAY HP
				hpBarOn = true;
				hpBarCounter = 0;
				
				// FLASH OPACITY
				if (invincible) hurtAnimation(g2);
			}			
			
			if (dying) dyingAnimation(g2);		
						
			g2.drawImage(image, screenX, screenY, null);	
			
			if (isLocked) {	
				
				// RESET X AND Y				
				screenX = worldX - gp.player.worldX + gp.player.screenX;
				screenY = worldY - gp.player.worldY + gp.player.screenY;
				lockedImage = setup("/enemy/lockon", gp.tileSize + 20, gp.tileSize + 20);
				
				changeAlpha(g2, 0.8f);
				g2.drawImage(lockedImage, screenX - 10, screenY - 10, null);
			}	
			// DRAW HITBOX
			if (gp.keyH.debug) {
				g2.setColor(Color.RED);
				g2.drawRect(screenX + hitbox.x, screenY + hitbox.y, hitbox.width, hitbox.height);
			}
			
			// RESET OPACITY
			changeAlpha(g2, 1f);			
		}
	}
}