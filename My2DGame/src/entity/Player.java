package entity;

/** IMPORTS **/
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import equipment.EQP_Shield_Old;
import equipment.EQP_Sword_Old;
import main.GamePanel;
import main.KeyHandler;
import projectile.PRJ_Sword_Beam;


/** PLAYER CLASS **/
public class Player extends Entity {

	
	/** PLAYER VARIABLES **/
	
	// KEY INPUT
	KeyHandler keyH;
	
	// INVENTORY
	public final int maxItemInventorySize = 10;
	public int itemIndex = 0;	
	public int walletSize;
		
	// POSITIONING
	public final int screenX;
	public final int screenY;
	public int safeWorldX = 0;
	public int safeWorldY = 0;

	// LIGHTING
	public boolean lightUpdated = false;
	
	// TRIGGERS
	public boolean guarding = false;
	public boolean running = false;
	public boolean chopping = false;	
	
	// DIGGING
	public boolean digging = false;
	public int digNum;
	public int digCounter = 0;	

	// JUMPING
	public boolean jumping = false;
	public int jumpNum;
	public int jumpCounter = 0;

	// FALLING
	public boolean falling = false;
	public int fallNum;
	public int fallCounter = 0;
	
	// IMAGES
	public BufferedImage titleScreen, sit, sing, itemGet, fall1, fall2, fall3;	
	public BufferedImage digUp1, digUp2, digDown1, digDown2, 
							digLeft1, digLeft2, digRight1, digRight2;
	public BufferedImage jumpUp1, jumpUp2, jumpUp3, jumpDown1, jumpDown2, jumpDown3,
							jumpLeft1, jumpLeft2, jumpLeft3, jumpRight1, jumpRight2, jumpRight3;
	
	/** END PLAYER VARIABLES **/		
	
	
	
	/** PLAYER CONSTRUCTOR **/
	public Player(GamePanel gp, KeyHandler keyH) {
		
		// pass GamePanel to Entity abstract class
		super(gp);		
		this.keyH = keyH;
		
		// PLAYER POSITION LOCKED TO CENTER
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2); 
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
						
		// HITBOX (x, y, width, height)
		hitbox = new Rectangle(8, 16, 32, 32); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		attackArea.width = 36;
		attackArea.height = 36;
		
		setDefaultValues();  
	}
	/** END PLAYER CONSTRUCTOR **/
		
	
	/** DEFAULT HANDLERS **/
	
	// DEFAULT VALUES
	public void setDefaultValues() {
						
		speed = 3; defaultSpeed = speed;
		runSpeed = 6; animationSpeed = 10;
		
		// PLAYER ATTRIBUTES
		level = 1;
		maxLife = 8; life = maxLife;
		strength = 1; dexterity = 1;
		exp = 0; nextLevelEXP = 10;
		walletSize = 99; rupees = 0;
		
		maxArrows = 5; arrows = maxArrows;
		maxBombs = 5; bombs = maxBombs;
		
		currentShield = new EQP_Shield_Old(gp);
		
		currentWeapon = null;
//		currentWeapon = new EQP_Sword_Old(gp);
		
		currentLight = null;
		projectile = new PRJ_Sword_Beam(gp);		
		
		attack = getAttack();
		defense = getDefense();
		
		setDefaultPosition();
		setDefaultItems();	
		setDialogue();

		getImage();
		getAttackImage();	
		getGuardImage();
		getDigImage();
		getJumpImage();
		getMiscImage();
	}	
	public void setDefaultPosition() {	
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 20;
//		worldX = gp.tileSize * 15;
//		worldY = gp.tileSize * 27;
		
		gp.currentMap = 0;
//		gp.currentMap = 2;
		
		direction = "down";
	}
	public void setDefaultItems() {		
		inventory.add(currentShield);
	}
	public void restoreStatus() {
		life = maxLife;
		speed = defaultSpeed;
		attacking = false;
		guarding = false;
		running = false;
		jumping = false;
		falling = false;
		chopping = false;
		digging = false;
		knockback = false;
		invincible = false;
		transparent = false;
		lightUpdated = true;
	}	

	// DIALOGUE
	public void setDialogue() {
		dialogues[0][0] = "\"I need to find a sword!\nBut where?...\"";
		dialogues[1][0] = "\"I need to find an item!\nBut where?...\"";
		dialogues[2][0] = "\"I should equip an item first...\"";
	}
	
	// ATTACK, DEFENSE
	public int getAttack() {
		if (currentWeapon == null)
			return 1;
		else {
			attackArea = currentWeapon.attackArea;
			swingSpeed1 = currentWeapon.swingSpeed1;
			swingSpeed2 = currentWeapon.swingSpeed2;
			return strength * currentWeapon.attackValue;
		}
	}
	public int getDefense() {
		return dexterity * currentShield.defenseValue;
	}
	
	// EQUIPMENT SLOT
	public int getCurrentWeaponSlot() {
		
		int currentWeaponSlot = 0;
		
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) == currentWeapon) {
				currentWeaponSlot = i;
			}
		}
		
		return currentWeaponSlot;
	}
	public int getCurrentShieldSlot() {
		
		int currentShieldSlot = 0;
		
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) == currentShield) {
				currentShieldSlot = i;
			}
		}
		
		return currentShieldSlot;
	}
	
	// PLAYER IMAGES
	public void getImage() {			
		up1 = setup("/player/boy_up_1"); 
		up2 = setup("/player/boy_up_2"); 
		down1 = setup("/player/boy_down_1"); 
		down2 = setup("/player/boy_down_2"); 
		left1 = setup("/player/boy_left_1"); 
		left2 = setup("/player/boy_left_2"); 
		right1 = setup("/player/boy_right_1"); 
		right2 = setup("/player/boy_right_2"); 
	}	
	public void getAttackImage() {		
		attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize * 2, gp.tileSize * 2); 
		attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);		
		attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize * 2, gp.tileSize * 2); 
		attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);		
		attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize * 2); 
		attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);		
		attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize * 2); 
		attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);		
	}	
	public void getGuardImage() {			
		guardUp1 = setup("/player/boy_guard_up_1"); 
		guardUp2 = guardUp1;
		guardDown1 = setup("/player/boy_guard_down_1"); 
		guardDown2 = guardDown1;
		guardLeft1 = setup("/player/boy_guard_left_1"); 
		guardLeft2 = guardLeft1;
		guardRight1 = setup("/player/boy_guard_right_1"); 
		guardRight2 = guardRight1;
	}	
	public void getDigImage() {
		digUp1 = setup("/player/boy_dig_up_1"); 
		digUp2 = setup("/player/boy_dig_up_2");			
		digDown1 = setup("/player/boy_dig_down_1"); 
		digDown2 = setup("/player/boy_dig_down_2");		
		digLeft1 = setup("/player/boy_dig_left_1"); 
		digLeft2 = setup("/player/boy_dig_left_2");		
		digRight1 = setup("/player/boy_dig_right_1"); 
		digRight2 = setup("/player/boy_dig_right_2");		
	}
	public void getJumpImage() {
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
	public void getMiscImage() {		
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
	
	/** END DEFAULT HANDLERS **/
	
	
	/** UPDATER **/
	public void update() {	

		guarding = false;
				
		if (keyH.actionPressed) { action(); }
		if (attacking) { attacking(); return; }	
		if (knockback) { knockbackPlayer(); return;	}
		if (digging) { digging(); return; }		
		if (jumping) { jumping(); }			
		if (lockon) { lockTarget(); }
		else {
			if (lockedTarget != null) {
				lockedTarget.isLocked = false;
				lockedTarget = null;
			}
		}
		if (falling) { falling(); return; } 
		if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed ||
				keyH.guardPressed) {
			walking();
		}	
		if (keyH.lockPressed) { lockon = !lockon; keyH.lockPressed = false; }
		if (keyH.itemPressed) { useItem(); }				
		if (keyH.tabPressed) { cycleItems(); }	
		manageValues();		
		checkDeath();
		
	}
	/** END UPDATER **/
	
	
	/** PLAYER METHODS **/
	
	// MOVEMENT
	public void walking() {
		
		// FIND DIRECTION
		getDirection();
		
		// CHECK COLLISION (NOT ON DEBUG)
		if(!keyH.debug) checkCollision();
		
		// STOP MOVEMENT WHEN GUARDING
		if (keyH.guardPressed) {
			guarding = true;
		}
		else {
			guarding = false;
		
			// MOVE PLAYER
			if (!collisionOn) { 				
				if (lockon) {
					switch (lockonDirection) {
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
				else {				
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
			}
			
			// WALKING ANIMATION
			spriteCounter++;
			if (spriteCounter > animationSpeed) {
								
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
	}
	public void getDirection() {
		
		if (lockedTarget == null && !lockon) {
			if (keyH.upPressed) direction = "up";
			if (keyH.downPressed) direction = "down";
			if (keyH.leftPressed) direction = "left";
			if (keyH.rightPressed) direction = "right";			
			
			if (keyH.upPressed && keyH.leftPressed) direction = "upleft";
			if (keyH.upPressed && keyH.rightPressed) direction = "upright";
			if (keyH.downPressed && keyH.leftPressed) direction = "downleft";
			if (keyH.downPressed && keyH.rightPressed) direction = "downright";	
		}
		// KEEP PLAYER FACING ENEMY
		else {			
			if (keyH.upPressed) lockonDirection = "up";
			if (keyH.downPressed) lockonDirection = "down";
			if (keyH.leftPressed) lockonDirection = "left";
			if (keyH.rightPressed) lockonDirection = "right";			
			
			if (keyH.upPressed && keyH.leftPressed) lockonDirection = "upleft";
			if (keyH.upPressed && keyH.rightPressed) lockonDirection = "upright";
			if (keyH.downPressed && keyH.leftPressed) lockonDirection = "downleft";
			if (keyH.downPressed && keyH.rightPressed) lockonDirection = "downright";	
		}
	}
	
	// ACTION BUTTON
	public void action() {
		checkCollision();
		
		if (!attackCanceled) 
			swingSword();
	}
	public void checkCollision() {
		
		collisionOn = false;
		
		// CHECK TILE COLLISION
		gp.cChecker.checkTile(this);

		// CHECK INTERACTIVE TILE COLLISION
		gp.cChecker.checkEntity(this, gp.iTile);
		
		// CHECK EVENT HANDLER (ONLY ON GROUND)
		if (!jumping) gp.eHandler.checkEvent();
					
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
	public void interactNPC(int i) {		
		if (i != -1) {
			if (keyH.actionPressed) {
				keyH.actionPressed = false;
				attackCanceled = true;			
				gp.npc[gp.currentMap][i].speak();
			}			
						
			if (!gp.npc[gp.currentMap][i].isPushed)
				gp.npc[gp.currentMap][i].move(direction);
		}				
	}
	public void contactEnemy(int i) {
		
		// PLAYER HIT BY ENEMY
		if (i != -1 && !invincible && !gp.enemy[gp.currentMap][i].dying) {
			playHurtSE();
			
			if (gp.enemy[gp.currentMap][i].knockbackPower > 0) 
				setKnockback(gp.player, gp.enemy[gp.currentMap][i], gp.enemy[gp.currentMap][i].knockbackPower);
			
			int damage = gp.enemy[gp.currentMap][i].attack - defense;
			if (damage < 0) damage = 0;				
			this.life -= damage;
			
			invincible = true;
			transparent = true;
			
		}
	}		
	public void pickUpObject(int i) {
		
		// OBJECT INTERACTION
		if (i != -1) {
			
			// OBSTACLE ITEMS
			if (gp.obj[gp.currentMap][i].type == type_obstacle) {
				if (keyH.actionPressed) {
					attackCanceled = true;
					gp.obj[gp.currentMap][i].interact();
				}
			}
			// REGULAR ITEMS
			else if (canObtainItem(gp.obj[gp.currentMap][i])) {
				gp.obj[gp.currentMap][i] = null;
			}			
			
		}
	}	
	public void pickUpProjectile(int i) {
		
		if (i != -1) {
			Projectile projectile = (Projectile) gp.projectile[gp.currentMap][i];					
			projectile.interact();
		}
	}
	public void swingSword() {
				
		if (currentWeapon == null) {		
			keyH.actionPressed = false;
			gp.gameState = gp.dialogueState;
			startDialogue(this, 0);
			return;
		}			
		// SWING SWORD IF NOT ALREADY
		else if (currentWeapon != null && !attackCanceled) {								
			currentWeapon.playSE();
			
			attacking = true;
			attackCanceled = true;
			spriteCounter = 0;
		}			
	}
	
	// Z-TARGETING
	public void lockTarget() {
		
		// FIND TARGET IF NOT ALREADY
		if (lockedTarget == null) {
			lockedTarget = findTarget();			
		}
		
		// TARGET FOUND WITHIN 10 TILES
		if (lockedTarget != null && getTileDistance(lockedTarget) < 8) {
			if (lockedTarget.alive) {
				lockedTarget.isLocked = true;
				direction = findTargetDirection(lockedTarget);
			}
			// TARGET DEFEATED
			else {				
				lockedTarget.isLocked = false;
				lockedTarget = null;
				lockon = false;
			}
		}
		// NO TARGET FOUND, TURN OFF LOCKON
		else 
			lockon = false;	
			
	}	
	public Entity findTarget() {
		
		Entity target = null;
		
		int currentDistance = 6;
		for (int i = 0; i < gp.enemy[1].length; i++) {
			
			if (gp.enemy[gp.currentMap][i] != null) {
				int enemyDistance = getTileDistance(gp.enemy[gp.currentMap][i]);
				if (enemyDistance < currentDistance) {
					currentDistance = enemyDistance;
					target = gp.enemy[gp.currentMap][i];
				}
			}
		}
		
		if (target != null)
			playLockOnSE();
		
		return target;
	}	
	public String findTargetDirection(Entity enemy) {
		
		String eDirection = "";
		
		int px = worldX / gp.tileSize;
		int py = worldY / gp.tileSize;
		
		int ex = enemy.worldX / gp.tileSize;
		int ey = enemy.worldY / gp.tileSize;	

		if (py >= ey && Math.abs(px-ex) < Math.abs(py-ey))
			eDirection = "up";
		else if (py >= ey && px-ex >= Math.abs(py-ey))
			eDirection = "left";
		else if (py >= ey && px-ex <= Math.abs(py-ey))
			eDirection = "right";
		
		else if (py < ey && Math.abs(px-ex) < Math.abs(py-ey))
			eDirection = "down";
		else if (py < ey && px-ex >= Math.abs(py-ey))
			eDirection = "left";
		else if (py < ey && px-ex <= Math.abs(py-ey))
			eDirection = "right";
		
		return eDirection;
	}
	
	// KNOCKBACK
	public void knockbackPlayer() {
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkEntity(this, gp.iTile);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.enemy);
		gp.eHandler.checkEvent();
		
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
	
	// ITEM HANDLING
	public void useItem() {
		keyH.itemPressed = false;
		
		if (hasItem && currentItem != null) {							
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
					keyH.upPressed = false; keyH.downPressed  = false;
					keyH.leftPressed  = false; keyH.rightPressed  = false;			
					currentItem.use(this);		
					break;	
			}	
		}
		else if (!hasItem) {
			gp.gameState = gp.dialogueState;
			startDialogue(this, 1);
		}
		else if (currentItem == null) {
			gp.gameState = gp.dialogueState;
			startDialogue(this, 2);
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
			else if (selectedItem.type == type_shield) {
				currentShield = selectedItem;
				defense = getDefense();
			}
			else if (selectedItem.type == type_item) {
				currentItem = selectedItem;
			}			
			else if (selectedItem.type == type_consumable) {
				if (selectedItem.use(this)) {
					
					if (selectedItem.amount > 1) {
						selectedItem.amount--;
					}
					else {
						inventory.remove(selectedItem);
					}
				}
			}
			else if (selectedItem.type == type_light) {
				if (currentLight == selectedItem) currentLight = null;				
				else currentLight = selectedItem;				
				lightUpdated = true;
			}
			
			getAttackImage();
		}
	}
	public void cycleItems() {
		
		if (currentItem != null) {
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
	}	

	// ANIMATIONS
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
			transparent = true;
			
			// MOVE PLAYER TO SAFE SPOT
			worldX = safeWorldX;
			worldY = safeWorldY;
			safeWorldX = 0;
			safeWorldY = 0;
		}		
	}	
			
	// DAMAGE
	public void damageEnemy(int i, Entity attacker, int attack, int knockbackPower) {
		
		// ATTACK HITS ENEMY
		if (i != -1) {
			
			// HURT ENEMY
			if (!gp.enemy[gp.currentMap][i].invincible) {
				gp.enemy[gp.currentMap][i].playHurtSE();
				
				if (knockbackPower > 0) {
					setKnockback(gp.enemy[gp.currentMap][i], attacker, knockbackPower);
				}
				
				// HIT BY PROJECTILE (NOT SWORD BEAM)
				if (attacker.type == type_projectile && 
						!attacker.name.equals(PRJ_Sword_Beam.prjName)) {
					gp.enemy[gp.currentMap][i].stunned = true;
					gp.enemy[gp.currentMap][i].spriteCounter = -30;
				}
								
				int damage = attack - gp.enemy[gp.currentMap][i].defense;
				if (damage < 0) damage = 0;				
				
				gp.enemy[gp.currentMap][i].life -= damage;					
				gp.enemy[gp.currentMap][i].invincible = true;
				gp.enemy[gp.currentMap][i].damageReaction();
				
				// KILL ENEMY
				if (gp.enemy[gp.currentMap][i].life <= 0) {
					gp.playSE(4, 2);
					gp.enemy[gp.currentMap][i].dying = true;
				}
			}
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
				gp.projectile[gp.currentMap][i].playSE();
				projectile.alive = false;
				generateParticle(projectile, projectile);
			}
		}
	}		
	public void damageInteractiveTile(int i) {
				
		if (i != -1 && gp.iTile[gp.currentMap][i].destructible && !gp.iTile[gp.currentMap][i].invincible &&
				gp.iTile[gp.currentMap][i].isCorrectItem(this)) {
			
			gp.iTile[gp.currentMap][i].playSE();
			
			gp.iTile[gp.currentMap][i].life--;
			gp.iTile[gp.currentMap][i].invincible = true;
					
			generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);
			
			if (gp.iTile[gp.currentMap][i].life == 0) {				
				gp.iTile[gp.currentMap][i].checkDrop();
				gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
			}
		}
	}
	
	// CHECKERS
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
		
		// PROJECTILE REFRESH TIME
		if (shotAvailableCounter < 30) 
			shotAvailableCounter++;	
		
		// PLAYER SHIELD AFTER DAMAGE
		if (invincible) {
			invincibleCounter++;
			
			// 1 SECOND REFRESH TIME 
			if (invincibleCounter > 60) {
				invincible = false;
				transparent = false;
				invincibleCounter = 0;
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
	public void checkDeath() {
		
		if (keyH.debug) {
			return;
		}
		
		if (life <= 0 && alive) {
			gp.stopMusic();
			playDeathSE();
			alive = false;
			lockon = false;
			lockedTarget = null;
			gp.gameState = gp.gameOverState;
			gp.ui.commandNum = -1;			
		}
	}
	
	// SOUND EFFECTS	
	public void playGuardSE() {
		gp.playSE(2, 3);
	}
	public void playLockOnSE() {
		gp.playSE(2, 3);
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

	// IMAGE MANAGER
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
	
	/** END PLAYER METHODS **/
	
	
	/** DRAW HANDLER **/
	public void draw(Graphics2D g2) {
						
		// DON'T DRAW PLAYER IN ITEMGET STATE
		if (gp.gameState == gp.itemGetState) 
			return;
		
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
					else if (guarding) {
						image1 = guardUp1;
					}
					else if (attacking) {
						tempScreenY -= gp.tileSize;
						if (attackNum == 1) {
							tempScreenX -= gp.tileSize;
							image1 = attackUp1;
						}
						if (attackNum == 2) image1 = attackUp2;							
					}	
					else {
						if (spriteNum == 1) image1 = up1;
						if (spriteNum == 2) image1 = up2;	
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
					else if (attacking) {		
						if (attackNum == 1) image1 = attackDown1;
						if (attackNum == 2) image1 = attackDown2;	
					}		
					else if (guarding) {
						image1 = guardDown1;
					}
					else {
						if (spriteNum == 1) image1 = down1;
						if (spriteNum == 2) image1 = down2;	
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
					else if (attacking) {
						tempScreenX -= gp.tileSize;
						if (attackNum == 1) {
							tempScreenY -= gp.tileSize;
							image1 = attackLeft1;
						}
						if (attackNum == 2) image1 = attackLeft2;	
					}	
					else if (guarding) {
						image1 = guardLeft1;
					}
					else {
						if (spriteNum == 1) image1 = left1;
						if (spriteNum == 2) image1 = left2;	
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
					else if (attacking) {
						if (attackNum == 1) {
							tempScreenY -= gp.tileSize;
							image1 = attackRight1;
						}
						if (attackNum == 2) image1 = attackRight2;
					}	
					else if (guarding) {
						image1 = guardRight1;
					}
					else {
						if (spriteNum == 1) image1 = right1;
						if (spriteNum == 2) image1 = right2;	
					}							
					break;
			}
							
			// PLAYER IS HIT
			if (transparent) {
				
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
		if (keyH.debug) {			
			g2.setColor(Color.RED);
			g2.drawRect(screenX + hitbox.x, screenY + hitbox.y, hitbox.width, hitbox.height);
		}
		
		// RESET OPACITY
		changeAlpha(g2, 1f);
	}
	/** END DRAW HANDLER **/
	
}
/** END PLAYER CLASS **/