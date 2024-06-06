package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;
import object.*;

public class Player extends Entity {

	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	public int safeWorldX = 0;
	public int safeWorldY = 0;
	
	public final int maxItemInventorySize = 10;
	public boolean hasItem;
	public int itemIndex = 0;

	public String enemyDirection;
			
	public boolean attackCanceled = false;
	public boolean running = false;
	
	public int digNum;
	public int digCounter = 0;
	public boolean digging = false;
	
	public int jumpNum;
	public int jumpCounter = 0;
	public boolean jumping = false;
	
	public int fallNum;
	public int fallCounter = 0;
	public boolean falling = false;
	
	public BufferedImage titleScreen, sit, sing, itemGet, fall1, fall2, fall3;	
	public BufferedImage digUp1, digUp2, digDown1, digDown2, 
							digLeft1, digLeft2, digRight1, digRight2;
	public BufferedImage jumpUp1, jumpUp2, jumpUp3, jumpDown1, jumpDown2, jumpDown3,
							jumpLeft1, jumpLeft2, jumpLeft3, jumpRight1, jumpRight2, jumpRight3;
	
			
	/** CONSTRUCTOR **/
	public Player(GamePanel gp, KeyHandler keyH) {
		
		// pass GamePanel to Entity abstract class
		super(gp);		
		this.keyH = keyH;
		
		// PLAYER POSITION LOCKED TO CENTER
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2); 
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
						
		// HITBOX (x, y, width, height)
		hitBox = new Rectangle(8, 16, 32, 32); 		
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		attackArea.width = 36;
		attackArea.height = 36;
		
		setDefaultValues();  
		getPlayerImage();
		setItems();
		getPlayerAttackImage();	
		getPlayerDigImage();
		getPlayerJumpImage();
		getPlayerMiscImage();
	}
	
	public void setDefaultValues() {
						
		setDefaultPosition();
		
		speed = 3; defaultSpeed = speed;
		runSpeed = 6; animationSpeed = 10;
		
		// PLAYER ATTRIBUTES
		level = 1;
		maxLife = 8; life = maxLife;
		strength = 1; dexterity = 1; // helps attack, defense
		exp = 0; nextLevelEXP = 5;
		rupees = 0;
		
		maxArrows = 5; arrows = maxArrows;
		maxBombs = 5; bombs = maxBombs;
		
		currentWeapon = new EQP_Sword(gp);		
		currentShield = new EQP_Shield(gp);
		projectile = new PRJ_Sword_Beam(gp);		
		
		attack = getAttack();
		defense = getDefense();
	}	
	public void setDefaultPosition() {	
		worldX = gp.tileSize * 10;
		worldY = gp.tileSize * 40;
		direction = "down";
	}
	public void setItems() {		
		inventory.add(currentShield);	
		inventory.add(currentWeapon);
		inventory.add(new ITM_Feather(gp));
		inventory.add(new ITM_Shovel(gp));
		inventory.add(new ITM_Bomb(gp));
		inventory.add(new ITM_Boomerang(gp));
		inventory.add(new ITM_Hookshot(gp));
		inventory.add(new ITM_Boots(gp));
		inventory.add(new ITM_Axe(gp));
		inventory.add(new ITM_Bow(gp));
		hasItem = true;
	}
	
	public int getAttack() {
		if (currentWeapon == null)
			return 1;
		else {
			attackArea = currentWeapon.attackArea;
			return attack = strength * currentWeapon.attackValue;
		}
	}
	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
	}
	
	public void restoreHearts() {
		life = maxLife;
		invincible = false;
	}
	
	public void getPlayerImage() {			
		up1 = setup("/player/boy_up_1"); 
		up2 = setup("/player/boy_up_2");
		down1 = setup("/player/boy_down_1"); 
		down2 = setup("/player/boy_down_2");
		left1 = setup("/player/boy_left_1"); 
		left2 = setup("/player/boy_left_2");
		right1 = setup("/player/boy_right_1"); 
		right2 = setup("/player/boy_right_2");
	}	
	public void getPlayerAttackImage() {		
		attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize * 2, gp.tileSize * 2); 
		attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);		
		attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize * 2, gp.tileSize * 2); 
		attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
		
		attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize * 2); 
		attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
		
		attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize * 2); 
		attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);		
	}	
	public void getPlayerDigImage() {
		digUp1 = setup("/player/boy_dig_up_1"); 
		digUp2 = setup("/player/boy_dig_up_2");	
		
		digDown1 = setup("/player/boy_dig_down_1"); 
		digDown2 = setup("/player/boy_dig_down_2");
		
		digLeft1 = setup("/player/boy_dig_left_1"); 
		digLeft2 = setup("/player/boy_dig_left_2");
		
		digRight1 = setup("/player/boy_dig_right_1"); 
		digRight2 = setup("/player/boy_dig_right_2");		
	}
	public void getPlayerJumpImage() {
		jumpUp1 = setup("/player/boy_jump_up_1");
		jumpUp2 = setup("/player/boy_jump_up_2");
		jumpUp3 = setup("/player/boy_jump_up_3");		
		
		jumpDown1 = setup("/player/boy_jump_down_1");
		jumpDown2 = setup("/player/boy_jump_down_2");
		jumpDown3 = setup("/player/boy_jump_down_3");
		
		jumpLeft1 = setup("/player/boy_jump_left_1");
		jumpLeft2 = setup("/player/boy_jump_left_2");
		jumpLeft3 = setup("/player/boy_jump_left_3");
		
		jumpRight1 = setup("/player/boy_jump_right_1");
		jumpRight2 = setup("/player/boy_jump_right_2");
		jumpRight3 = setup("/player/boy_jump_right_3");
	}
	public void getPlayerMiscImage() {		
		fall1 = setup("/player/boy_fall_1");
		fall2 = setup("/player/boy_fall_2");
		fall3 = setup("/player/boy_fall_3");
		
		itemGet = setup("/player/boy_item_get");		
		sit = setup("/player/boy_sit"); 
		sing = setup("/npc/girl_sing_1");
		
		die1 = setup("/player/boy_die_1"); 
		die2 = setup("/player/boy_die_2");
		die3 = setup("/player/boy_die_3"); 
		die4 = setup("/player/boy_die_4");		
	}
	
	public void update() {
		
		if (attacking) { 
			attacking(); 
			return; 
		}
		if (digging) {
			digging();	
			return; 
		}
		if (falling) { 
			falling(); 
			return; 
		} 
		if (knockback) {
			knockbackPlayer();
			return;
		}
		if (jumping) jumping();
		
		if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || 
			keyH.spacePressed) {	
		
			// find direction
			if (keyH.upPressed) direction = "up";
			if (keyH.downPressed) direction = "down";
			if (keyH.leftPressed) direction = "left";
			if (keyH.rightPressed) direction = "right";			
			
			if (keyH.upPressed && keyH.leftPressed) direction = "upleft";
			if (keyH.upPressed && keyH.rightPressed) direction = "upright";
			if (keyH.downPressed && keyH.leftPressed) direction = "downleft";
			if (keyH.downPressed && keyH.rightPressed) direction = "downright";			
			
			// CHECK COLLISION
			checkCollision();
						
			// CHECK EVENT
			if (!jumping) gp.eHandler.checkEvent();
			
			// MOVE IF NO COLLISION AND NOT ATTAKING
			if (!collisionOn && !attacking && !keyH.spacePressed) { 
				
				switch (direction) {
					case "up": worldY -= speed; break;
					case "upleft": worldY -= speed - 0.5; worldX -= speed - 0.5; break;
					case "upright": worldY -= speed - 0.5; worldX += speed - 0.5; break;
					
					case "down": worldY += speed; break;
					case "downleft": worldY += speed - 0.5; worldX -= speed - 0.5; break;
					case "downright": worldY += speed; worldX += speed - 0.5; break;
					
					case "left": worldX -= speed; break;
					case "right": worldX += speed; break;
				}
			}
			
			// SWING SWORD IF VALID
			if (currentWeapon != null && keyH.spacePressed && !attackCanceled && !attacking) {
								
				playAttackSE();
				attacking = true;
				spriteCounter = 0;
				
				// SHOOT SWORD BEAM
				if (projectile.hasResource(this) && !projectile.alive && 
						shotAvailableCounter == 30 ) { 	
					
					playBeamSE();
					
					projectile.set(worldX, worldY, direction, true, this);			
					addProjectile(projectile);					
					projectile.subtractResource(this);
					
					shotAvailableCounter = 0;
				}
			}
			if (currentWeapon == null && keyH.spacePressed) {
				gp.gameState = gp.dialogueState;
				gp.ui.currentDialogue = "\"I need to find a sword!\nBut where?...\"";
			}
							
			// WALKING ANIMATION
			spriteCounter++;
			if (spriteCounter > animationSpeed) { // speed of sprite change
								
				// CYLCE WALKING SPRITES
				if (spriteNum == 1) spriteNum = 2;
				else if (spriteNum == 2) spriteNum = 1;
				
				// RUNNING ANIMATION
				if (running) {
					currentItem.playSE();
					speed = runSpeed;
					animationSpeed = 6;
				}
				else {
					speed = defaultSpeed; 
					animationSpeed = 10; 
				}					
				spriteCounter = 0;
			}					
		}		
		
		// USE ITEM
		if (keyH.itemPressed && currentItem != null && hasItem) {		
			useItem();
		}			
		if (keyH.itemPressed && currentItem == null) {
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "\"I need to find an item!\nBut where?...\"";
		}
		
		// CYCLES ITEMS
		if (keyH.tabPressed && currentItem != null) {
			cycleItems();
		}		
		
		manageValues();
				
		// PROJECTILE REFRESH TIME
		if (shotAvailableCounter < 30) 
			shotAvailableCounter++;	
		
		// PLAYER SHIELD AFTER DAMAGE
		if (invincible) {
			invincibleCounter++;
			
			// 1 SECOND REFRESH TIME 
			if (invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}	
		
		// PLAYER DIES
		if (life <= 0 && alive) {
			gp.stopMusic();
			playDeathSE();
			gp.gameState = gp.gameOverState;
			gp.ui.commandNum = -1;
			alive = false;	
		}
	}
	
	public void checkCollision() {
		
		collisionOn = false;
		
		// CHECK TILE COLLISION
		gp.cChecker.checkTile(this);

		// CHECK INTERACTIVE TILE COLLISION
		gp.cChecker.checkEntity(this, gp.iTile);
					
		// CHECK NPC COLLISION
		int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
		interactNPC(npcIndex);
		
		// CHECK ENEMY COLLISION
		int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
		contactEnemy(enemyIndex);
		
		// CHECK OBJECT COLLISION
		int objIndex = gp.cChecker.checkObject(this, true);
		pickUpObject(objIndex);	
		
		// CHECK PROJECTILE COLLISION
		int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
		pickUpProjectile(projectileIndex);
	}
	
	public void useItem() {
		
		gp.keyH.itemPressed = false;
		
		switch (currentItem.name) {
			case "Axe":
			case "Boots":
			case "Feather":
			case "Shovel":
				currentItem.use();
				break;		
			case "Bomb":
			case "Bow":
				currentItem.use(this);
				break;
			case "Boomerang": 
			case "Hookshot":				
				// STOP MOVEMENT
				gp.keyH.upPressed = false; gp.keyH.downPressed  = false;
				gp.keyH.leftPressed  = false; gp.keyH.rightPressed  = false;			
				currentItem.use(this);		
				break;	
		}	
	}
	
	public void selectItem() {
		
		int inventoryIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
		if (inventoryIndex < inventory.size()) {
			
			keyH.playSelectSE();
			running = false;
			itemIndex = inventoryIndex;										
									
			Entity selectedItem = inventory.get(inventoryIndex);
			if (selectedItem.type == type_sword) {
				currentWeapon = selectedItem;
				attack = getAttack();
			}
			if (selectedItem.type == type_shield) {
				currentShield = selectedItem;
				defense = getDefense();
			}
			if (selectedItem.type == type_item) {
				currentItem = selectedItem;
			}			
			if (selectedItem.type == type_consumable) {
				if (selectedItem.use(this)) {
					
					if (selectedItem.amount > 1) {
						selectedItem.amount--;
					}
					else {
						inventory.remove(selectedItem);
					}
				}
			}
			
			getPlayerAttackImage();
		}
	}
	
	public void cycleItems() {
		
		keyH.playCursorSE();
		running = false;
		keyH.tabPressed = false;
			
		do {						
			itemIndex++;
			if (itemIndex >= inventory.size())
				itemIndex = 0;
		}
		while (inventory.get(itemIndex).type != type_item);		
		
		currentItem = inventory.get(itemIndex);			
	}	
	
	public void attacking() {
		
		attackCounter++;
		
		// 3 FRAMES: ATTACK IMAGE 1
		if (3 >= attackCounter) {			
			attackNum = 1;
		}		
		// 12 FRAMES: ATTACK IMAGE 2
		if (15 >= attackCounter && attackCounter > 3) {
			attackNum = 2;
			
			// CHECK IF WEAPON HITS TARGET	
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int hitBoxWidth = hitBox.width;
			int hitBoxHeight = hitBox.height;
			
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
			hitBox.width = attackArea.width;
			hitBox.height = attackArea.height;
			
			// CHECK IF ATTACK LANDS ON ENEMY
			int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
			
			// PREVENT GLITCH WITH AXE
			if (currentWeapon != null) 				
				damageEnemy(enemyIndex, attack, currentWeapon.knockbackPower);			
			else 
				damageEnemy(enemyIndex, attack, 0);
			
			// CHECK IF ATTACK LANDS ON PROJECTILE
			int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
			damageProjectile(projectileIndex);
			
			// ONLY ITEMS CAN DAMAGE INTERACTIVE TILES
			if (keyH.itemPressed) {		

				keyH.itemPressed = false;	
				
				// CHECK INTERACTIVE TILE
				int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
				damageInteractiveTile(iTileIndex);
			}
			
			// RESTORE PLAYER HITBOX
			worldX = currentWorldX;
			worldY = currentWorldY;
			hitBox.width = hitBoxWidth;
			hitBox.height = hitBoxHeight;
		}
		
		// RESET IMAGE
		if (attackCounter > 15) {
			attackNum = 1;
			attackCounter = 0;
			attacking = false;
			gp.keyH.spacePressed = false;
		}
	}
	
	public void digging() {
		
		digCounter++;
				
		if (12 >= digCounter) digNum = 1;		
		else if (24 > digCounter && digCounter > 12) digNum = 2;		
		else if (digCounter > 24) {
			
			// CHECK INTERACTIVE TILE
			int iTileIndex = gp.cChecker.checkDigging();
			damageInteractiveTile(iTileIndex);

			digNum = 1;
			digCounter = 0;
			digging = false;
			attackCanceled = false;
		}
	}
	
	public void jumping() {
		
		jumpCounter++;
				
		if (6 >= jumpCounter) jumpNum = 1; 
		else if (18 > jumpCounter && 12 >= jumpCounter) jumpNum = 2;		
		else if (24 > jumpCounter && jumpCounter > 12) jumpNum = 3;	
		else if (jumpCounter >= 24) {	
			jumpNum = 1;
			jumpCounter = 0;
			jumping = false;
			attackCanceled = false;
		}
	}
	
	public void falling() {
		
		fallCounter++;
				
		if (6 >= fallCounter) fallNum = 1; 
		else if (18 > fallCounter && 12 >= fallCounter) fallNum = 2;		
		else if (24 > fallCounter && fallCounter > 12) fallNum = 3;	
		else if (60 > fallCounter && fallCounter >= 24) fallNum = 4;		
		else if (fallCounter >= 60) {
			life--;
			fallNum = 1;
			fallCounter = 0;
			falling = false;
			attackCanceled = false;
			
			// MOVE PLAYER TO SAFE SPOT
			worldX = safeWorldX;
			worldY = safeWorldY;
			safeWorldX = 0;
			safeWorldY = 0;
		}		
	}	
				
	public void interactNPC(int i) {		
		if (i != -1 && keyH.spacePressed) {
			attackCanceled = true;
			gp.gameState = gp.dialogueState;
			gp.npc[gp.currentMap][i].speak();		
		}				
	}
	
	public void knockbackPlayer() {
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkEntity(this, gp.iTile);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.enemy);
		gp.eHandler.checkEvent();
		
		int objIndex = gp.cChecker.checkObject(this, true);
		pickUpObject(objIndex);	
		
		if (collisionOn) {
			knockbackCounter = 0;
			knockback = false;
			speed = defaultSpeed;
		}
		else {
			switch(direction) {
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
			direction = knockbackDirection;						
		}		
	}
	
	public void contactEnemy(int i) {
		
		// PLAYER HIT BY ENEMY
		if (i != -1 && !invincible && !gp.enemy[gp.currentMap][i].dying) {
			playHurtSE();
			
			if (gp.enemy[gp.currentMap][i].knockbackPower > 0) 
				knockback(gp.player, gp.enemy[gp.currentMap][i].direction, gp.enemy[gp.currentMap][i].knockbackPower);
			
			int damage = gp.enemy[gp.currentMap][i].attack - defense;
			if (damage < 0) damage = 0;				
			this.life -= damage;
			
			invincible = true;
			
		}
	}
	
	public void damageEnemy(int i, int attack, int knockbackPower) {
		
		// ATTACK HITS ENEMY
		if (i != -1) {
			
			// HURT ENEMY
			if (!gp.enemy[gp.currentMap][i].invincible) {
				gp.enemy[gp.currentMap][i].playHurtSE();
				
				if (knockbackPower > 0) 
					knockback(gp.enemy[gp.currentMap][i], direction, knockbackPower);				
				
				int damage = attack - gp.enemy[gp.currentMap][i].defense;
				if (damage < 0) damage = 0;				
				gp.enemy[gp.currentMap][i].life -= damage;			
				
				gp.enemy[gp.currentMap][i].invincible = true;
				gp.enemy[gp.currentMap][i].damageReaction();
				
				// KILL ENEMY
				if (gp.enemy[gp.currentMap][i].life <= 0) {
					gp.enemy[gp.currentMap][i].dying = true;
					
					gp.playSE(4, 2);
													
					exp += gp.enemy[gp.currentMap][i].exp;
					gp.ui.addMessage("+" + gp.enemy[gp.currentMap][i].exp + " EXP!");	
					
					checkLevelUp();
				}
			}
		}
	}
	
	public void checkLevelUp() {
		
		if (exp >= nextLevelEXP) {
			
			level++;
			nextLevelEXP *= 2;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			
			gp.ui.addMessage("Leveled up to level " + level + "!");
		}
	}
	
	public void pickUpProjectile(int i) {
		
		if (i != -1) {
			Projectile projectile = (Projectile) gp.projectile[gp.currentMap][i];					
			projectile.interact();
		}
	}
	
	public void damageProjectile(int i) {
		
		if (i != -1) {
			Entity projectile = gp.projectile[gp.currentMap][i];
			
			if (projectile.name.equals("Sword Beam"))
				return;
			else if (projectile.name.equals("Bomb"))
				projectile.explode();
			else {
				projectile.alive = false;
				generateParticle(projectile, projectile);
			}
		}
	}	
	
	public void damageInteractiveTile(int i) {
		
		if (i != -1 && gp.iTile[gp.currentMap][i].destructible && !gp.iTile[gp.currentMap][i].invincible &&
				gp.iTile[gp.currentMap][i].isCorrectItem(this)) {
			
			gp.iTile[gp.currentMap][i].life--;
			gp.iTile[gp.currentMap][i].invincible = true;
					
			generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);
			
			if (gp.iTile[gp.currentMap][i].life == 0)
				gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
		}
	}
	
	public void pickUpObject(int i) {
		
		// OBJECT INTERACTION
		if (i != -1) {
			
			// COLLECTABLES
			if (gp.obj[gp.currentMap][i].type == type_collectable) {				
				gp.obj[gp.currentMap][i].use(this);
				gp.obj[gp.currentMap][i] = null;
			}			
			// INVENTORY ITEMS
			else if (gp.obj[gp.currentMap][i].type == type_item) {
									
				playGetItemSE();																							
				gp.ui.currentDialogue = "You got the " + gp.obj[gp.currentMap][i].name + "!";
					
				hasItem = true;
				inventory.add(gp.obj[gp.currentMap][i]);		
					
				gp.gameState = gp.itemGetState;
				gp.ui.newItem = gp.obj[gp.currentMap][i];
				gp.obj[gp.currentMap][i] = null;
			}			
			// OBSTACLE ITEMS
			else if (gp.obj[gp.currentMap][i].type == type_obstacle) {
				if (keyH.spacePressed) {
					attackCanceled = true;
					gp.obj[gp.currentMap][i].interact();
				}
			}
			else {
				String text;
				
				if (canObtainItem(gp.obj[gp.currentMap][i])) {						
					playGetItemSE();
					
					Entity object = gp.obj[gp.currentMap][i];																		
					text = "You found the " + object.name + "!";	
				}
				else
					text = "You cannot carry any more items!";
				
				gp.ui.addMessage(text);
				gp.obj[gp.currentMap][i] = null;
			}
		}
	}
	
	public void getObject(Entity item) {
		
		if (item != null && canObtainItem(item)) {			
			playGetItemSE();
			gp.ui.currentDialogue = "You got the " + item.name + "!";
			
			// INVENTORY ITEMS
			if (item.type == type_item) 
				hasItem = true;
			if (item.type == type_sword) {
				currentWeapon = item;
				attack = getAttack();
			}
					
			gp.gameState = gp.itemGetState;
			gp.ui.newItem = item;
		}
	}
	
	public boolean canObtainItem(Entity item) {
		
		boolean canObtain = false;
		
		// IF STACKABLE ITEM
		if (item.stackable) {
			int index = searchItemInventory(item.name);
			
			// ITEM FOUND IN INVENTORY
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
					inventory.add(item);
					return true;
				}
			}			
		}
		// NOT STACKABLE
		else {
			if (inventory.size() != maxInventorySize) {
				inventory.add(item);
				return true;
			}
		}
		
		return canObtain;
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
	
	public void playAttackSE() {
		gp.playSE(3, 0);
	}
	public void playBeamSE() {
		gp.playSE(3, 4);
	}
	public void playLevelUpSE() {
		gp.playSE(1, 3);
	}
	public void playHurtSE() {
		gp.playSE(2, 0);
	}
	public void playDeathSE() {
		gp.playSE(2, 1);
	}
	
	public void manageValues() {
		
		// KEEP ARROWS WITHIN MAX
		if (arrows > maxArrows)	
			arrows = maxArrows;	
		
		// KEEP BOMBS WITHIN MAX
		if (bombs > maxBombs)	
			bombs = maxBombs;	
				
		// KEEP HEARTS WITHIN MAX
		if (life > maxLife) 
			life = maxLife;
	}

	public void draw(Graphics2D g2) {
						
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		if (alive) {
			
			// change entity sprite based on which direction and which cycle
			switch (direction) {
				case "up":
				case "upleft":
				case "upright":
					if (digging) {
						if (digNum == 1) image1 = digUp1;
						if (digNum == 2) image1 = digUp2;
					}
					else if (jumping) {					
						tempScreenY -= 15;
						if (jumpNum == 1) image1 = jumpUp1;
						if (jumpNum == 2) image1 = jumpUp2; 
						if (jumpNum == 3) image1 = jumpUp3; 
					}
					else if (!attacking) {
						if (spriteNum == 1) image1 = up1;
						if (spriteNum == 2) image1 = up2;	
					}					
					else {
						if (attackNum == 1) {
							tempScreenX -= gp.tileSize;
							tempScreenY -= gp.tileSize;
							image1 = attackUp1;
						}
						if (attackNum == 2) {
							tempScreenY -= gp.tileSize;
							image1 = attackUp2;	
						}
					}				
					break;
				case "down":
				case "downleft":
				case "downright":
					if (digging) {
						if (digNum == 1) image1 = digDown1;
						if (digNum == 2) image1 = digDown2;
					}
					else if (jumping) {					
						tempScreenY -= 15;
						if (jumpNum == 1) image1 = jumpDown1;
						if (jumpNum == 2) image1 = jumpDown2; 
						if (jumpNum == 3) image1 = jumpDown3; 
					}
					else if (!attacking) {
						if (spriteNum == 1) image1 = down1;
						if (spriteNum == 2) image1 = down2;	
					}					
					else {		
						if (attackNum == 1) image1 = attackDown1;
						if (attackNum == 2) image1 = attackDown2;	
					}		
					break;
				case "left":
					if (digging) {
						if (digNum == 1) image1 = digLeft1;
						if (digNum == 2) image1 = digLeft2;
					}
					else if (jumping) {					
						tempScreenY -= 15;
						if (jumpNum == 1) image1 = jumpLeft1;
						if (jumpNum == 2) image1 = jumpLeft2; 
						if (jumpNum == 3) image1 = jumpLeft3; 
					}
					else if (!attacking) {
						if (spriteNum == 1) image1 = left1;
						if (spriteNum == 2) image1 = left2;	
					}
					else {
						tempScreenX -= gp.tileSize;
						if (attackNum == 1) {
							tempScreenY -= gp.tileSize;
							image1 = attackLeft1;
						}
						if (attackNum == 2) image1 = attackLeft2;	
					}		
					break;
				case "right":
					if (digging) {
						if (digNum == 1) image1 = digRight1;
						if (digNum == 2) image1 = digRight2;
					}
					else if (jumping) {					
						tempScreenY -= 15;
						if (jumpNum == 1) image1 = jumpRight1;
						if (jumpNum == 2) image1 = jumpRight2; 
						if (jumpNum == 3) image1 = jumpRight3; 
					}					
					else if (!attacking) {
						if (spriteNum == 1) image1 = right1;
						if (spriteNum == 2) image1 = right2;	
					}					
					else {
						if (attackNum == 1) {
							tempScreenY -= gp.tileSize;
							image1 = attackRight1;
						}
						if (attackNum == 2) image1 = attackRight2;
					}		
					break;
			}
							
			// PLAYER IS HIT
			if (invincible) {
				
				// FLASH OPACITY
				if (invincibleCounter % 5 == 0)
					changeAlpha(g2, 0.2f);
				else
					changeAlpha(g2, 1f);
			}	
		}	
		
		if (falling) {
			if (fallNum == 1) image1 = fall1;
			if (fallNum == 2) image1 = fall2;
			if (fallNum == 3) image1 = fall3;
			if (fallNum == 4) image1 = null;
		}
						
		g2.drawImage(image1, tempScreenX, tempScreenY, null); 
		
		// DRAW SHADOW UNDER PLAYER
		if (jumping) {
			g2.setColor(Color.BLACK);
			g2.fillOval(screenX + 10, screenY + 40, 30, 10);
		}

		// DRAW HITBOX
		if (gp.keyH.debug) {			
			g2.setColor(Color.RED);
			g2.drawRect(screenX + hitBox.x, screenY + hitBox.y, hitBox.width, hitBox.height);
		}
		
		// RESET OPACITY
		changeAlpha(g2, 1f);
	}
	
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
}