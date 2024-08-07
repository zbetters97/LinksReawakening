package entity;

/** IMPORTS **/
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import application.GamePanel;
import data.Progress;
import entity.collectable.COL_Fairy;
import entity.enemy.EMY_Beetle;
import entity.enemy.EMY_Goblin_Combat_Shield;
import entity.equipment.*;
import entity.item.*;
import entity.projectile.PRJ_Bomb;
import entity.projectile.PRJ_Boomerang;
import entity.projectile.PRJ_Sword;
import entity.projectile.Projectile;
import tile.tile_interactive.IT_Switch;

/** PLAYER CLASS **/
public class Player extends Entity {
	
/** PLAYER VARIABLES **/
		
	// POSITIONING
	public int screenX;
	public int screenY;	
	
	// INVENTORY
	public final int maxItemInventorySize = 10;
	public ArrayList<Entity> inventory_item = new ArrayList<>();
	public int itemIndex = 0;
	public int walletSize;
	public int keys = 0;
	public int boss_key = 0;	
	
	// MISC
	public String aimDirection;	
	
	// COUNTERS
	public int damageNum = 1, rollNum = 1, pullNum = 1, throwNum = 1, 
			digNum = 1, jumpNum = 1, soarNum = 1, rodNum = 1;
	
	public int damageCounter = 0, lowHPCounter = 0, rollCounter = 0, pullCounter = 0, throwCounter = 0, 
			digCounter = 0, jumpCounter = 0, soarCounter = 0, rodCounter = 0, diveCounter = 0;
	
	// IMAGES
	public BufferedImage 	
		swimUp1, swimUp2, swimDown1, swimDown2, 
		swimLeft1, swimLeft2, swimRight1, swimRight2,	
		
		rollUp1, rollUp2, rollUp3, rollUp4, rollDown1, rollDown2, rollDown3, rollDown4,
		rollLeft1, rollLeft2, rollLeft3, rollLeft4, rollRight1, rollRight2, rollRight3, rollRight4,
		
		grabUp1, grabUp2, grabUp3, grabDown1, grabDown2, grabDown3, 
		grabLeft1, grabLeft2, grabLeft3, grabRight1, grabRight2, grabRight3,
		carryUp1, carryUp2, carryDown1, carryDown2, carryLeft1, carryLeft2, carryRight1, carryRight2,
		throwUp1, throwDown1, throwLeft1, throwRight1,
		
		digUp1, digUp2, digDown1, digDown2, digLeft1, digLeft2, digRight1, digRight2,
		
		jumpUp1, jumpUp2, jumpUp3, jumpDown1, jumpDown2, jumpDown3,
		jumpLeft1, jumpLeft2, jumpLeft3, jumpRight1, jumpRight2, jumpRight3,
		soarUp1, soarDown1, soarLeft1, soarRight1,
		rodUp1, rodUp2, rodDown1, rodDown2, rodLeft1, rodLeft2, rodRight1, rodRight2,
								
		titleScreen, sit, sing, itemGet, drown, fall1, fall2, fall3, die1, die2, die3, die4, die5;
	
/** END PLAYER VARIABLES **/		
	
		
/** PLAYER CONSTRUCTOR **/
	
	public Player(GamePanel gp) {
		super(gp);		
		
		// PLAYER POSITION LOCKED TO CENTER
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2); 
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
						
		// HITBOX (x, y, width, height)
		hitbox = new Rectangle(8, 16, 32, 28); 	
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
		
		attackbox.width = 32;
		attackbox.height = 32;
		
		name = "LINK";
	}

/** END PLAYER CONSTRUCTOR **/
		
	
/** DEFAULT HANDLERS **/
	
	// DEFAULT VALUES
	public void setDefaultValues() {
			
		action = Action.IDLE;	
		grabbedObject = null;
		onGround = true;		
		canSwim = false;		
		
		speed = 3; defaultSpeed = speed;
		runSpeed = 6; animationSpeed = 10;
		
		// PLAYER ATTRIBUTES
		maxLife = 20; life = maxLife;
		walletSize = 99; rupees = 0;
		
		maxArrows = 10; arrows = maxArrows;
		maxBombs = 10; bombs = maxBombs;
		shotAvailableCounter = 30;
		
//		currentWeapon = null;
		currentWeapon = new EQP_Sword_Old(gp);
		currentShield = new EQP_Shield(gp);
		
		attack = getAttack();
		
		setDefaultPosition();
		setDefaultItems();	
		setDialogue();

		getAttackImage();
		getGuardImage();
		getRollImage();
		getSwimImage();			
		getGrabImage();
		getCarryImage();		
		getThrowImage();		
		getDigImage();
		getJumpImage();
		getSoarImage();
		getRodImage();
		getMiscImage();
	}	
	public void setDefaultPosition() {	

		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 12;		
		gp.currentMap = 0;
		gp.currentArea = gp.outside;
/*
		worldX = gp.tileSize * 40;
		worldY = gp.tileSize * 92;		
		gp.currentArea = gp.dungeon;		
		gp.currentMap = 2;		
		direction = "up";	
		*/
	}
	public void setDefaultItems() {		
		inventory_item.add(new ITM_Shovel(gp));
		inventory_item.add(new ITM_Boomerang(gp));		
		inventory_item.add(new ITM_Bomb(gp));
		inventory_item.add(new ITM_Feather(gp));
		inventory_item.add(new ITM_Bow(gp));		
		inventory_item.add(new ITM_Hookshot(gp));
		inventory_item.add(new ITM_Cape(gp));		
		inventory_item.add(new ITM_Rod(gp));
	}
	public void restoreStatus() {
		alive = true;		
		canMove = true;
		currentItem = null;
		life = maxLife;
		speed = defaultSpeed;
		Progress.canSave = true;
		
		resetValues();
	}	
	public void resetValues() {	
				
		gp.keyH.actionPressed = false;
		action = Action.IDLE;
		onGround = true;
		knockback = false;
		invincible = false;
		transparent = false;	
		attacking = false;
		spinning = false;
		lockon = false;
		attackCanceled = false;		
		
		attackNum = 1;
		attackCounter = 0;
		actionLockCounter = 0;	
		charge = 0;
		spinNum = 0;			
		invincibleCounter = 0;
		damageNum = 1;
		damageCounter = 0;			
		lowHPCounter = 0;
		rollNum = 1;
		rollCounter = 0;		
		pullNum = 1;
		pullCounter = 0;		
		throwNum = 1;
		throwCounter = 0;		
		digNum = 1;
		digCounter = 0;		
		jumpNum = 1;
		jumpCounter = 0;
		soarNum = 1;
		soarCounter = 0; 		
		rodNum = 1;
		rodCounter = 0;
				
		if (grabbedObject != null) {
			if (grabbedObject.name.equals(PRJ_Bomb.prjName)) {
				grabbedObject.resetValues();
			}
			else {
				grabbedObject.alive = false;	
			}									
			grabbedObject = null;
		}
	}

	// DIALOGUE
	public void setDialogue() {
		dialogues[0][0] = "\"I need to find a sword! But where?...\"";
		dialogues[1][0] = "\"I need to find an item! But where?...\"";
		dialogues[2][0] = "\"I should equip an item first...\"";
	}
	
	// ATTACK
	public int getAttack() {
		if (currentWeapon == null)
			return 1;
		else {
			attackbox = currentWeapon.attackbox;
			swingSpeed1 = currentWeapon.swingSpeed1;
			swingSpeed2 = currentWeapon.swingSpeed2;
			return currentWeapon.attackValue;
		}
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
		if (currentWeapon != null && currentWeapon.name.equals(EQP_Sword_Old.eqpName)) {	
			attackUp1 = setup("/player/boy_attack_old_up_1", gp.tileSize * 2, gp.tileSize * 2); 
			attackUp2 = setup("/player/boy_attack_old_up_2", gp.tileSize, gp.tileSize * 2);		
			attackDown1 = setup("/player/boy_attack_old_down_1", gp.tileSize * 2, gp.tileSize * 2); 
			attackDown2 = setup("/player/boy_attack_old_down_2", gp.tileSize, gp.tileSize * 2);		
			attackLeft1 = setup("/player/boy_attack_old_left_1", gp.tileSize * 2, gp.tileSize * 2); 
			attackLeft2 = setup("/player/boy_attack_old_left_2", gp.tileSize * 2, gp.tileSize);		
			attackRight1 = setup("/player/boy_attack_old_right_1", gp.tileSize * 2, gp.tileSize * 2); 
			attackRight2 = setup("/player/boy_attack_old_right_2", gp.tileSize * 2, gp.tileSize);		
		}
		else {
			attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize * 2, gp.tileSize * 2); 
			attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);		
			attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize * 2, gp.tileSize * 2); 
			attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);		
			attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize * 2); 
			attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);		
			attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize * 2); 
			attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);		
		}
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
	public void getRollImage() {
		rollUp1 = setup("/player/boy_roll_up_1");
		rollUp2 = setup("/player/boy_roll_up_2");
		rollUp3 = setup("/player/boy_roll_up_3");			
		rollUp4 = setup("/player/boy_roll_up_4");			
		rollDown1 = setup("/player/boy_roll_down_1");
		rollDown2 = setup("/player/boy_roll_down_2");
		rollDown3 = setup("/player/boy_roll_down_3");		
		rollDown4 = setup("/player/boy_roll_down_4");		
		rollLeft1 = setup("/player/boy_roll_left_1");
		rollLeft2 = setup("/player/boy_roll_left_2");
		rollLeft3 = setup("/player/boy_roll_left_3");	
		rollLeft4 = setup("/player/boy_roll_left_4");	
		rollRight1 = setup("/player/boy_roll_right_1");
		rollRight2 = setup("/player/boy_roll_right_2");
		rollRight3 = setup("/player/boy_roll_right_3");
		rollRight4 = setup("/player/boy_roll_right_4");
	}
	public void getSwimImage() {			
		swimUp1 = setup("/player/boy_swim_up_1"); 
		swimUp2 = setup("/player/boy_swim_up_2");			
		swimDown1 = setup("/player/boy_swim_down_1"); 
		swimDown2 = setup("/player/boy_swim_down_2");		
		swimLeft1 = setup("/player/boy_swim_left_1"); 
		swimLeft2 = setup("/player/boy_swim_left_2");		
		swimRight1 = setup("/player/boy_swim_right_1"); 
		swimRight2 = setup("/player/boy_swim_right_2");		
	}
	public void getGrabImage() {
		grabUp1 = setup("/player/boy_grab_up_1"); 		
		grabUp2 = setup("/player/boy_grab_up_2"); 		
		grabUp3 = setup("/player/boy_grab_up_3"); 		
		grabDown1 = setup("/player/boy_grab_down_1");
		grabDown2 = setup("/player/boy_grab_down_2");
		grabDown3 = setup("/player/boy_grab_down_3");
		grabLeft1 = setup("/player/boy_grab_left_1"); 	
		grabLeft2 = setup("/player/boy_grab_left_2"); 	
		grabLeft3 = setup("/player/boy_grab_left_3"); 	
		grabRight1 = setup("/player/boy_grab_right_1"); 
		grabRight2 = setup("/player/boy_grab_right_2"); 
		grabRight3 = setup("/player/boy_grab_right_3"); 
	}
	public void getCarryImage() {
		carryUp1 = setup("/player/boy_carry_up_1"); 
		carryUp2 = setup("/player/boy_carry_up_2");			
		carryDown1 = setup("/player/boy_carry_down_1"); 
		carryDown2 = setup("/player/boy_carry_down_2");		
		carryLeft1 = setup("/player/boy_carry_left_1"); 
		carryLeft2 = setup("/player/boy_carry_left_2");		
		carryRight1 = setup("/player/boy_carry_right_1"); 
		carryRight2 = setup("/player/boy_carry_right_2");		
	}
	public void getThrowImage() {
		throwUp1 = setup("/player/boy_throw_up_1"); 		
		throwDown1 = setup("/player/boy_throw_down_1"); 	
		throwLeft1 = setup("/player/boy_throw_left_1"); 	
		throwRight1 = setup("/player/boy_throw_right_1"); 	
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
	public void getSoarImage() {
		soarUp1 = setup("/player/boy_soar_up_1");
		soarDown1 = setup("/player/boy_soar_down_1");
		soarLeft1 = setup("/player/boy_soar_left_1");
		soarRight1 = setup("/player/boy_soar_right_1");		
	}
	public void getRodImage() {		
		rodUp1 = setup("/player/boy_rod_up_1", gp.tileSize * 2, gp.tileSize * 2); 
		rodUp2 = setup("/player/boy_rod_up_2", gp.tileSize, gp.tileSize * 2);		
		rodDown1 = setup("/player/boy_rod_down_1", gp.tileSize * 2, gp.tileSize * 2); 
		rodDown2 = setup("/player/boy_rod_down_2", gp.tileSize, gp.tileSize * 2);		
		rodLeft1 = setup("/player/boy_rod_left_1", gp.tileSize * 2, gp.tileSize * 2); 
		rodLeft2 = setup("/player/boy_rod_left_2", gp.tileSize * 2, gp.tileSize);		
		rodRight1 = setup("/player/boy_rod_right_1", gp.tileSize * 2, gp.tileSize * 2); 
		rodRight2 = setup("/player/boy_rod_right_2", gp.tileSize * 2, gp.tileSize);		
	}	
	public void getMiscImage() {		
		drown = setup("/player/boy_drown");
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
		die5 = setup("/player/boy_die_5");	
	}
/** END DEFAULT HANDLERS **/
	
	
/** UPDATER **/

	public void update() {
		
		checkCollision();
				
		if (action == Action.IDLE) { onGround = true; guardCounter = 0; }
		
		if (knockback) { knockbackPlayer(); manageValues(); checkDeath(); return; }
		
		if (gp.keyH.ztargetPressed) { lockon = !lockon; gp.keyH.ztargetPressed = false; }		
		if (lockon && action != Action.AIMING) { zTarget(); }
		else {
			if (lockedTarget != null) {
				lockedTarget.locked = false;
				lockedTarget = null;
			}
		}		
		
		if (getAction()) { manageValues(); checkDeath(); return; }

		// DISABLED BUTTONS DURING SPECIFIC ACTIONS
		if (!disabled_actions.contains(action)) {			
			if (gp.keyH.actionPressed) { interact(); }	
			if (spinning) { spinAttacking(); manageValues(); checkDeath(); return; }	
			if (attacking) { attacking(); manageValues(); checkDeath(); return; }					
			if (gp.keyH.grabPressed) { grabbing(); }			
			if (gp.keyH.itemPressed) { useItem(); }					
			if (gp.keyH.tabPressed) { cycleItems(); }
			if (gp.keyH.guardPressed) { 
				action = Action.GUARDING; 
				if (guardCounter < 12) 
					guardCounter++; 
			}	
		}	
		
		if ((gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.leftPressed || gp.keyH.rightPressed) && 
				action != Action.AIMING && action != Action.GRABBING) { 
			walking(); 					
		}
				
		manageValues();		
		checkDeath();
	}
	public boolean getAction() {
		
		boolean stop = false;

		if (action == Action.AIMING) { aiming(); }	
		else if (action == Action.CARRYING) { if (carrying()) stop = true; }
		else if (action == Action.CHARGING) { charging(); }
		else if (action == Action.DIGGING) { digging(); stop = true; }
		else if (action == Action.GRABBING) { grabbing(); }	
		else if (action == Action.JUMPING) { jumping(); }	
		else if (action == Action.ROLLING) { rolling(); }
		else if (action == Action.SOARING) { jumping(); }
		else if (action == Action.SWIMMING) { swimming(); }
		else if (action == Action.SWINGING) { swinging(); stop = true; }	
		else if (action == Action.THROWING) { throwing(); stop = true; }		
		
		return stop;
	}

/** END UPDATER **/
	
	
/** PLAYER METHODS **/
	
	// MOVEMENT
	public void walking() {
		
		if (gp.keyH.guardPressed && action != Action.SWIMMING) {
			getDirection(); return;
		}
		
		// DONT CHANGE DIRECTIONS WHILE ROLLING AND NOT LOCKON
		if (action != Action.ROLLING || lockon) getDirection();
		
		if (!gp.keyH.debug) checkCollision();
		if (!collisionOn) { 
			
			if (lockon) move(lockonDirection);			
			else move(direction);
			
			cycleSprites();	
			
			// ONLY ROLL WHILE MOVING
			if (gp.keyH.rollPressed && action == Action.IDLE) { 
				grunting();				
				action = Action.ROLLING; 
				gp.keyH.rollPressed = false; 
			}	
		}
	}
	public void getDirection() {
		
		String tempDirection = "";
		
		if (gp.keyH.upPressed) tempDirection = "up";
		if (gp.keyH.downPressed) tempDirection = "down";
		if (gp.keyH.leftPressed) tempDirection = "left";
		if (gp.keyH.rightPressed) tempDirection = "right";			
		
		if (gp.keyH.upPressed && gp.keyH.leftPressed) tempDirection = "upleft";
		if (gp.keyH.upPressed && gp.keyH.rightPressed) tempDirection = "upright";
		if (gp.keyH.downPressed && gp.keyH.leftPressed) tempDirection = "downleft";
		if (gp.keyH.downPressed && gp.keyH.rightPressed) tempDirection = "downright";	
		
		if (lockon) lockonDirection = tempDirection;
		else direction = tempDirection;
	}
	public void move(String direction) {
		
		if (canMove) {
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
	public void cycleSprites() {

		spriteCounter++;
		if (spriteCounter > animationSpeed) {
							
			// CYLCE WALKING/SWIMMING SPRITES
			if (spriteNum == 1) {
				if (action == Action.SWIMMING && !diving) playSwimSE();					
				spriteNum = 2;
			}
			else if (spriteNum == 2) { 
				spriteNum = 1;
			}
			
			// RUNNING ANIMATION
			if (action == Action.RUNNING) {
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
	public void grunting() {		
		int grunt = 1 + (int)(Math.random() * 3);
		if (grunt == 1) playGruntSE_1();
		else if (grunt == 2) playGruntSE_2();
		else if (grunt == 3) playGruntSE_3();
	}
	
	// INTERACTIONS
	public void interact() {
		if (!attackCanceled) swingSword();
	}
	public void checkCollision() {
				
		collisionOn = false;
		
		if (gp.keyH.debug) return;
		
		// CHECK EVENTS
		gp.eHandler.checkEvent();
		
		// CHECK TILE COLLISION
		gp.cChecker.checkTile(this);
		
		// CHECK INTERACTIVE TILE COLLISION
		gp.cChecker.checkEntity(this, gp.iTile);
								
		// DON'T CHECK PITS WHEN JUMPING
		if (action != Action.JUMPING && action != Action.SOARING) gp.cChecker.checkPit(this, true);
		
		if (diving) {
			if (collisionOn) {
				diving = false;
			}
			else {
				return;
			}
		}		
		
		// CHECK NPC COLLISION
		gp.cChecker.checkEntity(this, gp.npc);		
		int npcIndex = gp.cChecker.checkNPC();
		interactNPC(npcIndex);
		
		// CHECK ENEMY COLLISION
		int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
		contactEnemy(enemyIndex, gp.enemy);
		
		// CHECK INTERACTIVE OBJECTS COLLISION
		int objTIndex = gp.cChecker.checkObject_I(this, true);
		interactObject(objTIndex);	
		
		// CHECK OBJECT COLLISION
		int objIndex = gp.cChecker.checkObject(this, true);
		pickUpObject(objIndex);	
		
		// CHECK PROJECTILE COLLISION
		int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
		pickUpProjectile(projectileIndex);
	}
	public void interactNPC(int i) {		
		if (i != -1) {
			if (gp.keyH.actionPressed) {
				resetValues();
				attackCanceled = true;
				gp.npc[gp.currentMap][i].speak();				
			}			
		}				
	}	
	public void interactObject(int i) {
		
		// OBJECT INTERACTION
		if (i != -1) {
			
			if (gp.obj_i[gp.currentMap][i].type == type_obstacle) {
				if (!gp.obj_i[gp.currentMap][i].moving) {
					gp.obj_i[gp.currentMap][i].move(direction);	
				}
			}					
		}
	}	
	public void pickUpObject(int i) {
		
		// OBJECT INTERACTION
		if (i != -1) {
			
			// OBSTACLE ITEMS
			if (gp.obj[gp.currentMap][i].type == type_obstacle) {
				gp.obj[gp.currentMap][i].interact(); 
			}
			else if (gp.obj[gp.currentMap][i].type == type_obstacle_i) {
				if (gp.keyH.actionPressed) {
					gp.obj[gp.currentMap][i].interact();
				}
			}
			// PICKUP ONLY ITEMS
			else if (gp.obj[gp.currentMap][i].type == type_pickupOnly) {
				attackCanceled = true;
				gp.obj[gp.currentMap][i].use(this);
				gp.obj[gp.currentMap][i] = null;
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
	public void getObject(Entity item) {
						
		if (item.type == type_collectable) {
			item.use(this);
			return;
		}
		else if (item.type == type_item) {
			inventory_item.add(item);	
			
			gp.ui.newItem = item;
			gp.ui.currentDialogue = "You got the " + item.name + "!\n" + item.getDescription;
		}
		else if (item.type == type_equipment) {			
			if (item.name.equals(EQP_Sword_Old.eqpName) || 
					item.name.equals(EQP_Sword_Master.eqpName)) {
				currentWeapon = item;
				attack = getAttack();
				getAttackImage();
			}			
			else if (item.name.equals(EQP_Flippers.eqpName)) {
				canSwim = true;
			}
			
			gp.ui.newItem = item;
			gp.ui.currentDialogue = "You got the " + item.name + "!";
		}
		else if (item.type == type_key) {
			playGetItemSE();
			keys++;
			
			gp.ui.newItem = item;
			gp.ui.currentDialogue = "You found a " + item.name + "!";		
		}
		else if (item.type == type_boss_key) {
			playGetItemSE();
			boss_key++;
			
			gp.ui.newItem = item;
			gp.ui.currentDialogue = "You found the " + item.name + "!";	
		}					
		else {
			inventory.add(item);
		}
		
		playGetItemSE();		
		gp.ui.subState = 0;
		gp.gameState = gp.itemGetState;
	}
		
	// SWORD
	public void swingSword() {			
		if (currentWeapon == null) {		
			gp.keyH.actionPressed = false;
			gp.gameState = gp.dialogueState;
			startDialogue(this, 0);
			return;
		}			
		// SWING SWORD IF NOT ALREADY
		else if (currentWeapon != null && !attackCanceled) {								
			currentWeapon.playSE();
			grunting();
			
			attacking = true;
			attackCanceled = true;
			spriteCounter = 0;
		}	
	}
	public void charging() {
		if (gp.keyH.actionPressed) {
			
			if (charge < 119) charge += 2;
			
			lockon = true;
			lockonDirection = direction;
			speed = 2;
		}
		else if (charge >= 120) {
			
			// RELEASE SPIN ATTACK
			if (charge >= 120) {
				playGruntSE_4();
				playSpinSwordSE();
				
				charge = 0;
				action = Action.IDLE;
				spinning = true;
				attackCounter = 0;
				gp.keyH.actionPressed = false;
				
				if (lockedTarget == null) {
					lockon = false;
				}				
				
				speed = defaultSpeed;
			}
		}
		else {		
			charge = 0;
			action = Action.IDLE;
			attackNum = 1;
			attackCounter = 0;				
			attacking = false;			
			attackCanceled = false;

			if (lockedTarget == null) {
				lockon = false;
			}				
		}
	}
	
	// Z-TARGETING
	public void zTarget() {
		
		if (action == Action.CHARGING || spinning) return;
		
		// FIND TARGET IF NOT ALREADY
		if (lockedTarget == null) {
			lockedTarget = findTarget();			
		}
		
		// TARGET FOUND WITHIN 10 TILES
		if (lockedTarget != null && getTileDistance(lockedTarget) < 10) {
			if (lockedTarget.alive) {
				lockedTarget.locked = true;
				direction = findTargetDirection(lockedTarget);
				lockonDirection = direction;
			}
			// TARGET DEFEATED
			else {				
				lockedTarget.locked = false;
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
			
			if (gp.enemy[gp.currentMap][i] != null && gp.enemy[gp.currentMap][i].type != type_boss &&
					!gp.enemy[gp.currentMap][i].teleporting) {
				int enemyDistance = getTileDistance(gp.enemy[gp.currentMap][i]);
				if (enemyDistance < currentDistance) {
					currentDistance = enemyDistance;
					target = gp.enemy[gp.currentMap][i];
				}
			}
		}
		if (target != null) {
			playZTargetSE();
		}
		
		return target;
	}	
	public String findTargetDirection(Entity target) {
		
		String eDirection = direction;
		
		int px = (worldX + (hitbox.width / 2)) / gp.tileSize;
		int py = (worldY + (hitbox.width / 2)) / gp.tileSize;
		
		int ex = (target.worldX + (target.hitbox.width / 2)) / gp.tileSize;
		int ey = (target.worldY + (target.hitbox.height / 2)) / gp.tileSize;	
		
		if (py == ey && px == ex) 
			eDirection = direction;		
		else if (py > ey && Math.abs(px-ex) < Math.abs(py-ey)) 
			eDirection = "up";
		else if (py > ey && px-ex > Math.abs(py-ey)) 
			eDirection = "left";
		else if (py > ey && px-ex < Math.abs(py-ey)) 
			eDirection = "right";
		else if (py < ey && Math.abs(px-ex) < Math.abs(py-ey)) 
			eDirection = "down";
		else if (py < ey && px-ex > Math.abs(py-ey)) 
			eDirection = "left";
		else if (py < ey && px-ex < Math.abs(py-ey)) 
			eDirection = "right";		
		else if (py == ey && px > ex) 
			eDirection = "left";
		else if (py == ey && px < ex) 
			eDirection = "right";
						
		return eDirection;
	}
	
	// ENEMY DAMAGE
	public void contactEnemy(int i, Entity enemyList[][]) {
		
		// PLAYER HURT BY ENEMY
		if (i != -1 && !invincible &&
				enemyList[gp.currentMap][i].collision &&
				!enemyList[gp.currentMap][i].dying && 		
				!enemyList[gp.currentMap][i].captured) {

			Entity enemy = enemyList[gp.currentMap][i];
			
			if (enemy.knockbackPower > 0) setKnockback(this, enemy, enemy.knockbackPower);			
			
			String guardDirection = getOppositeDirection(enemy.direction);
			if (action == Action.GUARDING && direction.equals(guardDirection)) {
				gp.playSE(4, 8);	
				
				if (enemy.name.equals(EMY_Beetle.emyName)) {
					enemy.attacking = true;
					setKnockback(enemy, this, 1);
				}
				
				if (enemy.knockbackPower == 0) setKnockback(enemy, this, 1);
			}
			else {					
				playHurtSE();
				
				int damage = enemy.attack;								
				if (damage < 0) damage = 0;				
				this.life -= damage;
				
				invincible = true;
				transparent = true;	
			}
		}
	}		
	public void knockbackPlayer() {
		
		collisionOn = false;
		gp.cChecker.checkTile(this);		
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.enemy);
		gp.cChecker.checkEntity(this, gp.obj);
		gp.cChecker.checkEntity(this, gp.obj_i);
		gp.cChecker.checkEntity(this, gp.iTile);
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
	public void selectInventory() {
		
		int inventoryIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
		if (inventoryIndex < inventory.size()) {			
			gp.keyH.playSelectSE();
			
			if (action != Action.SWIMMING) 
				action = Action.IDLE;
			
			itemIndex = inventoryIndex;										
									
			Entity selectedItem = inventory.get(inventoryIndex);
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
		}
	}
	public void selectItem() {
		
		int inventoryIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
		if (inventoryIndex < inventory_item.size()) {		
			
			if (action != Action.IDLE && action != Action.SWIMMING) {
				return;
			}
			
			gp.keyH.playSelectSE();
			itemIndex = inventoryIndex;										
									
			Entity selectedItem = inventory_item.get(inventoryIndex);
			
			if (selectedItem == currentItem) {
				currentItem = null;
			}
			else {
				currentItem = selectedItem;	
			}
		}
	}
	public void useItem() {
		
		// HAS ITEM EQUIPPED
		if (currentItem != null) {							
			switch (currentItem.name) {
				case ITM_Bomb.itmName:
				case ITM_Boots.itmName:
				case ITM_Cape.itmName:
				case ITM_Feather.itmName:
				case ITM_Rod.itmName:
				case ITM_Shovel.itmName:				
					gp.keyH.itemPressed = false;
					currentItem.use(this);
					break;
				case ITM_Bow.itmName:
					if (currentItem.setCharge(this)) {
						lockon = true;
						lockonDirection = direction;	
					}					
					break;
				case ITM_Boomerang.itmName: 
				case ITM_Hookshot.itmName:	
					
					// STOP MOVEMENT
					gp.keyH.itemPressed = false;
					gp.keyH.upPressed = false; gp.keyH.downPressed  = false;
					gp.keyH.leftPressed  = false; gp.keyH.rightPressed  = false;	
					currentItem.use(this);
					break;	
			}	
		}
		// NO ITEM EQUIPPED
		else {
			gp.keyH.itemPressed = false;	
			startDialogue(this, 2);					
			gp.gameState = gp.dialogueState;
		}			
	}
	public void cycleItems() {
		
		if (currentItem != null) {
			gp.keyH.playCursorSE();
			gp.keyH.tabPressed = false;
								
			itemIndex++;
			if (itemIndex >= inventory_item.size())
				itemIndex = 0;
			
			currentItem = inventory_item.get(itemIndex);		
		}
	}	

	// ANIMATIONS	
	public void rolling() {
		
		speed = 5;		
		
		rollCounter++;				
		if (5 >= rollCounter) rollNum = 1;		
		else if (10 > rollCounter && rollCounter > 5) rollNum = 2;		
		else if (15 > rollCounter && rollCounter > 10) rollNum = 3;		
		else if (20 > rollCounter && rollCounter > 15) rollNum = 4;		
		else if (rollCounter > 20) {
			rollNum = 1;
			rollCounter = 0;
			attackCanceled = false;
			action = Action.IDLE;	
			speed = defaultSpeed;
		}
	}
	public void aiming() {
		
		switch (direction) {			
			case "up":
			case "upleft":
			case "upright":
			case "down":
			case "downleft":
			case "downright":
				gp.keyH.upPressed = false; gp.keyH.downPressed = false;
				if ((gp.keyH.leftPressed || gp.keyH.rightPressed)) { walking(); }
				break;
			case "left":
			case "right":			
				gp.keyH.leftPressed = false; gp.keyH.rightPressed = false;
				if ((gp.keyH.upPressed || gp.keyH.downPressed)) { walking(); }
				break;
		}
		
		if (gp.keyH.itemPressed) {
			currentItem.setCharge(this);
		}
		else {
			lockon = false;
			currentItem.use(this);
		}
	}
	public void grabbing() {
		
		int i = gp.cChecker.checkEntity(this, gp.iTile);
		if (i != -1) {
			if (gp.iTile[gp.currentMap][i].grabbable) {
				grabEntity(gp.iTile[gp.currentMap][i]);
 				return;
			}
		}		
		else {
			i = gp.cChecker.checkEntity(this, gp.projectile);
			if (i != -1 && gp.projectile[gp.currentMap][i].grabbable) {
				grabEntity(gp.projectile[gp.currentMap][i]);	
				return;
			}
		}
		
		action = Action.IDLE;
	}
	public void grabEntity(Entity entity) {
		
		action = Action.GRABBING;
		
		// PLAYER AUTOMATICALLY GRABS BOMB
		if (entity.name.equals(PRJ_Bomb.prjName)) {
			pull(entity);
			return;
		}
		
		String pullDirection = getOppositeDirection(direction);
		switch (pullDirection) {
			case "up":
			case "upleft":
			case "upright":
				if (gp.keyH.upPressed) {
					pull(entity);
				}
				else {
					pullNum = 1;
					pullCounter = 0;
					action = Action.IDLE;
				}
				break;
			case "down":
			case "downleft":
			case "downright":
				if (gp.keyH.downPressed) {					
					pull(entity);
				}
				else {
					pullNum = 1;
					pullCounter = 0;
					action = Action.IDLE;
				}
				break;
			case "left":
				if (gp.keyH.leftPressed) {
					pull(entity);
				}
				else {
					pullNum = 1;
					pullCounter = 0;
					action = Action.IDLE;
				}
				break;
			case "right":
				if (gp.keyH.rightPressed) {
					pull(entity);
				}
				else {
					pullNum = 1;
					pullCounter = 0;
					action = Action.IDLE;
				}
				break;
		}
	}
	public void pull(Entity entity) {
		pullCounter++;
		
		if (6 >= pullCounter) pullNum = 1; 
		else if (18 > pullCounter && 12 >= pullCounter) pullNum = 2;		
		else if (27 > pullCounter && pullCounter > 12) pullNum = 3;	
		else if (pullCounter >= 28) {	
			playLiftSE();
			pullNum = 1;
			pullCounter = 0;
			action = Action.CARRYING;
			grabbedObject = entity;
			entity.grabbed = true;
			gp.keyH.grabPressed = false;	
		}
	}
	public boolean carrying() {
		if (gp.keyH.grabPressed) {
			playThrowSE();
			
			action = Action.THROWING;
			grabbedObject.thrown = true;
			grabbedObject.grabbed = false;
			grabbedObject.tWorldY = worldY;
			
			switch (direction) {
				case "up":
				case "upleft":
				case "upright": grabbedObject.direction = "up"; break;
				case "down":
				case "downleft":
				case "downright": grabbedObject.direction = "down"; break;
				default: grabbedObject.direction = direction; break;
			}
			
			return true;
		}
		else if (gp.keyH.itemPressed) {
			if (grabbedObject.name.equals(PRJ_Bomb.prjName)) {
				
				action = Action.THROWING;
				grabbedObject.grabbed = false;
				grabbedObject.worldX = worldX;
				grabbedObject.worldY = worldY;
			}
		}
		
		return false;
	}
	public void throwing() {		
		throwCounter++;		
		if (throwCounter > 30 && gp.gameState == gp.playState) {
			action = Action.IDLE;
			throwCounter = 0;
		}
	}
	public void digging() {
		
		digCounter++;
				
		if (12 >= digCounter) digNum = 1;		
		else if (24 > digCounter && digCounter > 12) digNum = 2;		
		else if (digCounter > 24) {
			
			// CHECK INTERACTIVE TILE
			int iTileIndex = gp.cChecker.checkDigging();
			damageInteractiveTile(iTileIndex, currentItem);

			digNum = 1;
			digCounter = 0;
			attackCanceled = false;
			action = Action.IDLE;			
		}
	}
	public void jumping() {
		
		jumpCounter++;
				
		if (6 >= jumpCounter) jumpNum = 1; 
		else if (18 > jumpCounter && 12 >= jumpCounter) jumpNum = 2;		
		else if (27 > jumpCounter && jumpCounter > 12) jumpNum = 3;	
		else if (jumpCounter >= 28) {	
			
			if (action == Action.SOARING) {		
				
				if (jumpCounter == 30) {
					currentItem.playSE();
				}
				
				jumpNum = 4;
				soaring();
			}
			else {			
				jumpNum = 1;
				jumpCounter = 0;
				attackCanceled = false;
				action = Action.IDLE;	
			}
		}
	}
	public void soaring() {

		if (60 > jumpCounter) {		
			
			if (!collisionOn) {	
				if (lockon) { 
					switch (lockonDirection) {
						case "up": worldY--; break;
						case "upleft": worldY -= 0.5; worldX -= 0.5; break;
						case "upright": worldY -= 0.5; worldX += 0.5; break;
						
						case "down": worldY++; break;
						case "downleft": worldY += 0.5; worldX -= 0.5; break;
						case "downright": worldY += 0.5; worldX += 0.5; break;
						
						case "left": worldX--; break;
						case "right": worldX++; break;
					}
				}
				else {
					switch (direction) {
						case "up": worldY--; break;
						case "upleft": worldY -= 0.5; worldX -= 0.5; break;
						case "upright": worldY -= 0.5; worldX += 0.5; break;
						
						case "down": worldY++; break;
						case "downleft": worldY += 0.5; worldX -= 0.5; break;
						case "downright": worldY += 0.5; worldX += 0.5; break;
						
						case "left": worldX--; break;
						case "right": worldX++; break;
					}
				}
			}
		}
		else if (jumpCounter >= 70) {
			jumpNum = 1;
			jumpCounter = 0;			
			attackCanceled = false;
			action = Action.IDLE;
			gp.gameState = gp.playState;
		}
	}	
	public void swimming() { 
		if (gp.keyH.actionPressed) { 
			diving = true; guarded = true;
		} 
	}
	public void swinging() {

		rodCounter++;
				
		// ATTACK IMAGE 1
		if (currentItem.swingSpeed1 >= rodCounter) {			
			rodNum = 1;
		}		
		// ATTACK IMAGE 2
		if (currentItem.swingSpeed2 >= rodCounter && rodCounter > currentItem.swingSpeed1) {
			rodNum = 2;
			
			// CHECK IF WEAPON HITS TARGET	
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			
			// ADJUST PLAYER'S X/Y 
			switch (direction) {
				case "up": worldY -= attackbox.height; break; 
				case "upleft": worldY -= attackbox.height; worldX -= attackbox.width; break; 
				case "upright": worldY -= attackbox.height; worldX += attackbox.width; break; 
				case "down": worldY += attackbox.height; break;
				case "downleft": worldY += attackbox.height; worldX -= attackbox.width; break;
				case "downright": worldY += attackbox.height; worldX += attackbox.width; break;					
				case "left": worldX -= attackbox.width; break;
				case "right": worldX += attackbox.width; break;
			}
			
			// CHANGE SIZE OF HIT BOX 
			hitbox.width = attackbox.width;
			hitbox.height = attackbox.height;
						
			// RESTORE PLAYER HITBOX
			worldX = currentWorldX;
			worldY = currentWorldY;
			hitbox.width = hitboxDefaultWidth;
			hitbox.height = hitboxDefaultHeight;
		}

		// RESET IMAGE
		if (rodCounter > currentItem.swingSpeed2) {
			rodNum = 1;
			rodCounter = 0;
			attackCanceled = false;
			action = Action.IDLE;
		}
	}			
	
	// DAMAGE
	public void damageEnemy(Entity target, Entity attacker, int attack, int knockbackPower) {
				
		if (target != null) {
						
			if (!target.guarded) {				
			
				// BUZZING ENEMY
				if (target.buzzing && attacker == this) {
					if (!attacker.invincible) {
						target.playShockSE();
					}
					damagePlayer(target.attack);
				}			
				else if (!target.invincible && !target.captured) {		
					
					// SHIELD GOBLIN BLOCKS ATTACK
					if (target.name.equals(EMY_Goblin_Combat_Shield.emyName) &&
							direction == getOppositeDirection(target.direction) && 
							!target.stunned) {
						playBlockSE();	
						setKnockback(attacker, target, target.knockbackPower);
					}
					else {
						target.playHurtSE();
						
						// HIT BY STUN WEAPON
						if (attacker.canStun && !target.stunned) {
							target.stunned = true;
							target.spriteCounter = -30;
						}
										
						int damage = attack;
						if (damage < 0) damage = 1;
						
						target.life -= damage;					
						target.transparent = true;
					}
					
					target.invincible = true;
					target.damageReaction();
					
					if (knockbackPower > 0 && target.type != type_boss) {
						setKnockback(target, attacker, knockbackPower);
					}				
					
					// ENEMY DEFEATED
					if (target.life <= 0) {
						target.playDeathSE();
						target.dying = true;
					}				
				}		
			}		
			// BEETLE ENEMY PROJECTILE DAMAGE
			else if (target.name.equals(EMY_Beetle.emyName) && 
					(attacker.name.equals(PRJ_Boomerang.prjName) || 
					attacker.name.equals(PRJ_Bomb.prjName))) {
				setKnockback(target, attacker, 1);
				target.attacking = true;			
			}
		}
	}
	public void damageProjectile(int i) {
		
		if (i != -1) {
			Entity projectile = gp.projectile[gp.currentMap][i];
			
			if (projectile.name.equals(PRJ_Sword.prjName))
				return;
			else if (projectile.name.equals(PRJ_Bomb.prjName)) {
				projectile.explode();
				projectile.animationSpeed = 30;
				projectile.active = false;
				projectile.alive = false;		
			}
			else {
				gp.projectile[gp.currentMap][i].playSE();
				projectile.alive = false;
				generateParticle(projectile);
			}
		}
	}		
	public void damageInteractiveTile(int i, Entity entity) {
		
		if (i != -1) {
		
			if (gp.iTile[gp.currentMap][i].name.equals(IT_Switch.itName) && !gp.iTile[gp.currentMap][i].invincible) {
				gp.iTile[gp.currentMap][i].invincible = true;
				gp.iTile[gp.currentMap][i].interact();
			}
		
			else if (i != -1 && !gp.iTile[gp.currentMap][i].invincible &&
					gp.iTile[gp.currentMap][i].correctItem(entity)) {
				
				gp.iTile[gp.currentMap][i].playSE();
				
				gp.iTile[gp.currentMap][i].life--;
				gp.iTile[gp.currentMap][i].invincible = true;
						
				generateParticle(gp.iTile[gp.currentMap][i]);
				
				if (gp.iTile[gp.currentMap][i].life == 0) {				
					gp.iTile[gp.currentMap][i].checkDrop();
					gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
				}
			}
		}
	}
	public void takingDamage() {
		
		damageCounter++;
				
		if (6 >= damageCounter) damageNum = 1; 
		else if (18 > damageCounter && 12 >= damageCounter) damageNum = 2;		
		else if (24 > damageCounter && damageCounter > 12) damageNum = 3;	
		else if (60 > damageCounter && damageCounter >= 24) damageNum = 4;		
		else if (damageCounter >= 80) {
			
			life -= 2;
			damageNum = 1;
			damageCounter = 0;			
			attackCanceled = false;
			transparent = true;
			
			if (action == Action.CARRYING) {
				grabbedObject.alive = false;				
			}
			action = Action.IDLE;
			
			// MOVE PLAYER TO SAFE SPOT
			worldX = safeWorldX;
			worldY = safeWorldY;
			safeWorldX = 0;
			safeWorldY = 0;
			
			gp.gameState = gp.playState;
		}		
	}		
	
	// CHECKERS
	public void manageValues() {
		
		if (gp.keyH.debug) life = maxLife;
		
		if (life <= 4) {
			lowHPCounter++;
			if (lowHPCounter == 90) {
				playLowHPSE();
				lowHPCounter = 0;
			}
		}
		else {
			lowHPCounter = 0;
		}
						
		// KEEP ARROWS WITHIN MAX
		if (arrows > maxArrows)	
			arrows = maxArrows;	
		
		// KEEP BOMBS WITHIN MAX
		if (bombs > maxBombs)	
			bombs = maxBombs;	
				
		// KEEP HEARTS WITHIN MAX
		if (life > maxLife) 
			life = maxLife;
		
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
		
		// DIVE FOR 1.5 SECONDS
		if (diving) {
			diveCounter++;
			if (diveCounter >= 90) {
				
				diving = false;
				diveCounter = 0;
			}
		}
	}
	public void checkDeath() {
		
		if (life <= 0 && alive) {
			
			for (Entity e : inventory) {
				if (e.name.equals(COL_Fairy.colName)) {
					e.use(this);
					if (e.amount > 1) e.amount--;
					else inventory.remove(e);
					return;
				}
			}
			
			gp.stopMusic();
			playDeathSE();
			alive = false;
			attacking = false;
			lockon = false;
			lockedTarget = null;
			gp.gameState = gp.gameOverState;
			gp.ui.commandNum = -1;							
		}
	}
	
	// SOUND EFFECTS
	public void playGruntSE_1() {
		gp.playSE(2, 8);
	}
	public void playGruntSE_2() {
		gp.playSE(2, 9);
	}
	public void playGruntSE_3() {
		gp.playSE(2, 10);
	}
	public void playGruntSE_4() {
		gp.playSE(2, 11);
	}
	public void playGetItemSE() {
		gp.playSE(5, 0);
	}
	public void playGuardSE() {
		gp.playSE(2, 1);
	}
	public void playBlockSE() {
		gp.playSE(4, 8);
	}
	public void playSpinSwordSE() {
		gp.playSE(4, 13);
	}
	public void playZTargetSE() {
		gp.playSE(2, 1);
	}	
	private void playLiftSE() {
		gp.playSE(4, 10);
	}
	public void playThrowSE() {
		gp.playSE(4, 11);
	}
	public void playSwimSE() {
		gp.playSE(2, 2);
	}
	public void playDrownSE() {
		gp.playSE(2, 3);
	}	
	public void playFallSE() {
		gp.playSE(2, 4);
	}
	public void playHurtSE() {
		gp.playSE(2, 5);
	}
	public void playLowHPSE() {
		gp.playSE(2, 7);
	}
	public void playDeathSE() {
		gp.playSE(2, 6);
	}

	// IMAGE MANAGER
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}

	// DRAW HANDLER
	public void draw(Graphics2D g2) {
		
		if (!drawing) return;
		
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		if (screenX > worldX) {
			tempScreenX = worldX;
		}
		if (screenY > worldY) {
			tempScreenY = worldY;
		}
		
		// FROM PLAYER TO RIGHT-EDGE OF SCREEN
		int rightOffset = gp.screenWidth - screenX;		
		
		// FROM PLAYER TO RIGHT-EDGE OF WORLD
		if (rightOffset > gp.worldWidth - worldX) {
			tempScreenX = gp.screenWidth - (gp.worldWidth - worldX);
		}			
		
		// FROM PLAYER TO BOTTOM-EDGE OF SCREEN
		int bottomOffSet = gp.screenHeight - screenY;
		
		// FROM PLAYER TO BOTTOM-EDGE OF WORLD
		if (bottomOffSet > gp.worldHeight - worldY) {
			tempScreenY = gp.screenHeight - (gp.worldHeight - worldY);
		}
		
		if (alive) {					
			switch (direction) {
				case "up":
				case "upleft":
				case "upright":	
					if (attacking) {
						tempScreenY -= gp.tileSize;
						if (attackNum == 1) {
							tempScreenX -= gp.tileSize;
							image = attackUp1;
						}
						else if (attackNum == 2) image = attackUp2;
					}
					else {
						switch (action) {
						case CARRYING:
							if (spriteNum == 1) image = carryUp1;
							else if (spriteNum == 2) image = carryUp2;
							break;
						case GUARDING:
							image = guardUp1;
							break;
						case GRABBING:
							if (pullNum == 1) image = grabUp1;
							else if (pullNum == 2) image = grabUp2;
							else if (pullNum == 3) image = grabUp3;
							break;
						case DIGGING:
							if (digNum == 1) image = digUp1;
							else if (digNum == 2) image = digUp2;
							break;
						case JUMPING:
						case SOARING:
							tempScreenY -= 30;
							if (jumpNum == 1) image = jumpUp1;
							else if (jumpNum == 2) image = jumpUp2; 
							else if (jumpNum == 3) image = jumpUp3; 
							else if (jumpNum == 4) image = soarUp1; 
							
							g2.setColor(Color.BLACK);
							g2.fillOval(tempScreenX + 10, tempScreenY + 70, 30, 10);
							break;
						case ROLLING:
							if (rollNum == 1) image = rollUp1;
							else if (rollNum == 2) image = rollUp2; 
							else if (rollNum == 3) image = rollUp3; 
							else if (rollNum == 4) image = rollUp4; 
							break;
						case SWINGING:
							tempScreenY -= gp.tileSize;
							if (rodNum == 1) {
								tempScreenX -= gp.tileSize;
								image = rodUp1;
							}
							else if (rodNum == 2) image = rodUp2;	
							break;
						case SWIMMING:
							if (spriteNum == 1) image = swimUp1;
							else if (spriteNum == 2) image = swimUp2;
							break;
						case AIMING:
						case THROWING:
							image = throwUp1;
							break;
						default:
							if (spriteNum == 1) image = up1;
							else if (spriteNum == 2) image = up2;	
							break;
						}
					}
					break;
				case "down":
				case "downleft":
				case "downright":
					if (attacking) {
						if (attackNum == 1) image = attackDown1;
						else if (attackNum == 2) image = attackDown2;	
					}
					else {
						switch (action) {
						case CARRYING:
							if (spriteNum == 1) image = carryDown1;
							else if (spriteNum == 2) image = carryDown2;
							break;
						case GUARDING:
							image = guardDown1;
							break;
						case GRABBING:
							if (pullNum == 1) image = grabDown1;
							else if (pullNum == 2) image = grabDown2;
							else if (pullNum == 3) image = grabDown3;
							break;
						case DIGGING:
							if (digNum == 1) image = digDown1;
							else if (digNum == 2) image = digDown2;
							break;
						case JUMPING:
						case SOARING:
							tempScreenY -= 30;
							if (jumpNum == 1) image = jumpDown1;
							else if (jumpNum == 2) image = jumpDown2; 
							else if (jumpNum == 3) image = jumpDown3; 
							else if (jumpNum == 4) image = soarDown1; 
							
							g2.setColor(Color.BLACK);
							g2.fillOval(tempScreenX + 10, tempScreenY + 70, 30, 10);
							break;
						case ROLLING:
							if (rollNum == 1) image = rollDown1;
							else if (rollNum == 2) image = rollDown2; 
							else if (rollNum == 3) image = rollDown3; 
							else if (rollNum == 4) image = rollDown4; 
							break;
						case SWINGING:
							if (rodNum == 1) image = rodDown1;
							else if (rodNum == 2) image = rodDown2;
							break;
						case SWIMMING:
							if (spriteNum == 1) image = swimDown1;
							else if (spriteNum == 2) image = swimDown2;
							break;
						case AIMING:
						case THROWING:
							image = throwDown1;
							break;
						default:
							if (spriteNum == 1) image = down1;
							else if (spriteNum == 2) image = down2;	
							break;
						}
					}
					break;
				case "left":
					if (attacking) {
						tempScreenX -= gp.tileSize;
						if (attackNum == 1) {
							tempScreenY -= gp.tileSize;
							image = attackLeft1;
						}
						else if (attackNum == 2) image = attackLeft2;	
					}
					else {
						switch (action) {
						case CARRYING:
							if (spriteNum == 1) image = carryLeft1;
							else if (spriteNum == 2) image = carryLeft2;
							break;
						case GUARDING:
							image = guardLeft1;
							break;
						case GRABBING:
							if (pullNum == 1) image = grabLeft1;
							else if (pullNum == 2) image = grabLeft2;
							else if (pullNum == 3) image = grabLeft3;
							break;
						case DIGGING:
							if (digNum == 1) image = digLeft1;
							else if (digNum == 2) image = digLeft2;
							break;
						case JUMPING:
						case SOARING:
							tempScreenY -= 30;
							if (jumpNum == 1) image = jumpLeft1;
							else if (jumpNum == 2) image = jumpLeft2; 
							else if (jumpNum == 3) image = jumpLeft3; 
							else if (jumpNum == 4) image = soarLeft1; 
							
							g2.setColor(Color.BLACK);
							g2.fillOval(tempScreenX + 10, tempScreenY + 70, 30, 10);
							break;
						case ROLLING:
							if (rollNum == 1) image = rollLeft1;
							else if (rollNum == 2) image = rollLeft2; 
							else if (rollNum == 3) image = rollLeft3; 
							else if (rollNum == 4) image = rollLeft4; 
							break;
						case SWINGING:
							tempScreenX -= gp.tileSize;
							if (rodNum == 1) {
								tempScreenY -= gp.tileSize;
								image = rodLeft1;
							}
							else if (rodNum == 2) image = rodLeft2;	
							break;
						case SWIMMING:
							if (spriteNum == 1) image = swimLeft1;
							else if (spriteNum == 2) image = swimLeft2;
							break;						
						case AIMING:
						case THROWING:						
							image = throwLeft1;
							break;
						default:
							if (spriteNum == 1) image = left1;
							else if (spriteNum == 2) image = left2;	
							break;
						}
					}
					break;
				case "right":
					if (attacking) {
						if (attackNum == 1) {
							tempScreenY -= gp.tileSize;
							image = attackRight1;
						}
						else if (attackNum == 2) image = attackRight2;
					}
					else {
						switch (action) {
						case CARRYING:
							if (spriteNum == 1) image = carryRight1;
							else if (spriteNum == 2) image = carryRight2;
							break;
						case GUARDING:						
							image = guardRight1;						
							break;
						case GRABBING:
							if (pullNum == 1) image = grabRight1;
							else if (pullNum == 2) image = grabRight2;
							else if (pullNum == 3) image = grabRight3;
							break;
						case DIGGING:
							if (digNum == 1) image = digRight1;
							else if (digNum == 2) image = digRight2;
							break;
						case JUMPING:	
						case SOARING:
							tempScreenY -= 30;
							if (jumpNum == 1) image = jumpRight1;
							else if (jumpNum == 2) image = jumpRight2; 
							else if (jumpNum == 3) image = jumpRight3; 
							else if (jumpNum == 4) image = soarRight1; 
							
							g2.setColor(Color.BLACK);
							g2.fillOval(tempScreenX + 10, tempScreenY + 70, 30, 10);
							break;
						case ROLLING:
							if (rollNum == 1) image = rollRight1;
							else if (rollNum == 2) image = rollRight2; 
							else if (rollNum == 3) image = rollRight3; 
							else if (rollNum == 4) image = rollRight4; 
							break;
						case SWINGING:			
							if (rodNum == 1) {
								tempScreenY -= gp.tileSize;
								image = rodRight1;
							}
							else if (rodNum == 2) image = rodRight2;
							break;
						case SWIMMING:
							if (spriteNum == 1) image = swimRight1;
							else if (spriteNum == 2) image = swimRight2;
							break;
						case AIMING:
						case THROWING:
							image = throwRight1;
							break;
						default:
							if (spriteNum == 1) image = right1;
							else if (spriteNum == 2) image = right2;	
							break;
						}							
					}
					break;
			}
			
			
			// PLAYER HIT, FLASH OPACITY
			if (transparent) {		
				if (invincibleCounter % 5 == 0)	
					changeAlpha(g2, 0.2f);
			}				
		}	
				
		if (gp.gameState == gp.fallingState) {
			if (damageNum == 1) image = fall1;
			else if (damageNum == 2) image = fall2;
			else if (damageNum == 3) image = fall3;
			else if (damageNum == 4) image = null;
			
			tempScreenX = screenX;
			tempScreenY = screenY;
		}	
		else if (gp.gameState == gp.drowningState || diving) {
			image = drown;			
			
			tempScreenX = screenX;
			tempScreenY = screenY;
		}				
		
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		// RESET OPACITY
		changeAlpha(g2, 1f);
	}	
}
/** END PLAYER METHODS **/

/** END PLAYER CLASS **/