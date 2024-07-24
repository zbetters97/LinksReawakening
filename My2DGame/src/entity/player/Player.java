package entity.player;

/** IMPORTS **/
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import application.GamePanel;
import application.KeyHandler;
import entity.Entity;
import entity.equipment.*;
import entity.item.*;
import entity.projectile.PRJ_Bomb;
import entity.projectile.PRJ_Sword;
import entity.projectile.Projectile;
import tile.tile_interactive.IT_Switch;

/** PLAYER CLASS **/
public class Player extends Entity {
	
/** PLAYER VARIABLES **/
	
	// KEY INPUT
	KeyHandler keyH;
	
	// POSITIONING
	public int screenX;
	public int screenY;	
	public int safeWorldX = 0;
	public int safeWorldY = 0;
	
	// INVENTORY
	public final int maxItemInventorySize = 20;
	public ArrayList<Entity> inventory_item = new ArrayList<>();
	public int itemIndex = 0;
	public int walletSize;
	public int keys = 0;
	public int boss_key = 0;	
	
	// MISC
	public String aimDirection;	
	public Entity capturedTarget;
	
	// COUNTERS
	public int damageNum = 1, rollNum = 1, pullNum = 1, throwNum = 1, 
			digNum = 1, jumpNum = 1, soarNum = 1, rodNum = 1;
	
	public int damageCounter = 0, lowHPCounter = 0, rollCounter = 0, pullCounter = 0, throwCounter = 0, 
			digCounter = 0, jumpCounter = 0, soarCounter = 0, rodCounter = 0;
	
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
								
		titleScreen, sit, sing, itemGet, drown, fall1, fall2, fall3;	
	
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
		hitbox = new Rectangle(8, 16, 32, 28); 	
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
		
		attackbox.width = 32;
		attackbox.height = 32;
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
		
//		currentWeapon = null;
		currentWeapon = new EQP_Sword_Old(gp);
		currentShield = new EQP_Shield(gp);
		
//		projectile = new PRJ_Sword(gp);
		
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
/*	
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;		
		gp.currentMap = 0;
		gp.currentArea = gp.outside;
*/
		worldX = gp.tileSize * 40;
		worldY = gp.tileSize * 92;		
		gp.currentMap = 2;
		gp.currentArea = gp.dungeon;
		
		direction = "up";
	}
	public void setDefaultItems() {		
		inventory_item.add(new ITM_Shovel(gp));
		inventory_item.add(new ITM_Boomerang(gp));
		inventory_item.add(new ITM_Boots(gp));				
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
		
		resetValues();
	}	
	public void resetValues() {	
				
		action = Action.IDLE;
		onGround = true;
		knockback = false;
		invincible = false;
		transparent = false;	
		attackCanceled = false;
		
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
	
	// ATTACK, DEFENSE
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
	}
/** END DEFAULT HANDLERS **/
	
	
/** UPDATER **/

	public void update() {	
				
		if (!keyH.debug) checkCollision();
		if (action == Action.IDLE) { onGround = true; }
		if (knockback) { knockbackPlayer(); manageValues(); checkDeath(); return;	}
		
		if (keyH.lockPressed) { lockon = !lockon; keyH.lockPressed = false; }
		if (lockon && action != Action.AIMING) { lockTarget(); }
		else {
			if (lockedTarget != null) {
				lockedTarget.locked = false;
				lockedTarget = null;
			}
		}	
		
		if (action == Action.DIGGING) { digging(); manageValues(); checkDeath(); return; }
		else if (action == Action.SWINGING) { swinging(); manageValues(); checkDeath(); return; }
		else if (action == Action.THROWING) { throwing(); manageValues(); checkDeath(); return; }		
		else if (action == Action.AIMING) {
			
			switch (direction) {			
				case "up":
				case "upleft":
				case "upright":
				case "down":
				case "downleft":
				case "downright":
					keyH.upPressed = false; keyH.downPressed = false;
					if ((keyH.leftPressed || keyH.rightPressed)) { walking(); }
					break;
				case "left":
				case "right":			
					keyH.leftPressed = false; keyH.rightPressed = false;
					if ((keyH.upPressed || keyH.downPressed)) { walking(); }
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
		else if (action == Action.GRABBING) { grabbing(); }		
		else if (action == Action.JUMPING) { jumping(); }	
		else if (action == Action.SOARING) { jumping(); }
		else if (action == Action.ROLLING) { rolling(); }
		else if (action == Action.CARRYING) {
			if (keyH.grabPressed) {
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
				
				return;
			}
			if (keyH.itemPressed) {
				if (grabbedObject.name.equals(PRJ_Bomb.prjName)) {
					
					action = Action.THROWING;
					grabbedObject.grabbed = false;
					grabbedObject.worldX = gp.player.worldX;
					grabbedObject.worldY = gp.player.worldY;
				}
			}
		}
		
		// DISABLED ACTIONS WHILE SWIMMING, AIMING, OR CARRYING
		if (action != Action.SWIMMING && action != Action.AIMING && action != Action.JUMPING
				&& action != Action.CARRYING && action != Action.SOARING && action != Action.ROLLING) {			
			if (keyH.actionPressed) { action(); }
			if (attacking) { attacking(); manageValues(); checkDeath(); return; }	
			if (keyH.guardPressed) { action = Action.GUARDING; }	
			if (keyH.grabPressed) { grabbing(); }			
			if (keyH.itemPressed) { useItem(); }					
			if (keyH.tabPressed) { cycleItems(); }
		}		
				
		if ((keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) && 
				action != Action.AIMING && action != Action.GRABBING) { 
			walking(); 					
		}
				
		manageValues();		
		checkDeath();
	}

/** END UPDATER **/
	
	
/** PLAYER METHODS **/
	
	// MOVEMENT
	public void walking() {
		
		if (keyH.guardPressed && action != Action.SWIMMING) {
			getDirection(); return;
		}
		
		// DONT CHANGE DIRECTIONS WHILE ROLLING
		if (action != Action.ROLLING) getDirection();
		
		if (!keyH.debug) checkCollision();
		if (!collisionOn) { 				
			if (lockon) move(lockonDirection);			
			else move(direction);		
			cycleSprites();	
			
			// ONLY ROLL WHILE MOVING
			if (keyH.rollPressed && action == Action.IDLE) { action = Action.ROLLING; }	
		}
	}
	public void getDirection() {
		
		// KEEP PLAYER FACING ENEMY
		if (lockon) {
			if (keyH.upPressed) lockonDirection = "up";
			if (keyH.downPressed) lockonDirection = "down";
			if (keyH.leftPressed) lockonDirection = "left";
			if (keyH.rightPressed) lockonDirection = "right";			
			
			if (keyH.upPressed && keyH.leftPressed) lockonDirection = "upleft";
			if (keyH.upPressed && keyH.rightPressed) lockonDirection = "upright";
			if (keyH.downPressed && keyH.leftPressed) lockonDirection = "downleft";
			if (keyH.downPressed && keyH.rightPressed) lockonDirection = "downright";	
		}		
		else {			
			if (keyH.upPressed) direction = "up";
			if (keyH.downPressed) direction = "down";
			if (keyH.leftPressed) direction = "left";
			if (keyH.rightPressed) direction = "right";			
			
			if (keyH.upPressed && keyH.leftPressed) direction = "upleft";
			if (keyH.upPressed && keyH.rightPressed) direction = "upright";
			if (keyH.downPressed && keyH.leftPressed) direction = "downleft";
			if (keyH.downPressed && keyH.rightPressed) direction = "downright";	
		}
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
				if (action == Action.SWIMMING) playSwimSE();					
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
	
	// INTERACTIONS
	public void action() {		
		if (!attackCanceled) swingSword();
	}
	public void checkCollision() {
		
		collisionOn = false;
		
		// CHECK EVENTS
		gp.eHandler.checkEvent();
		
		// CHECK TILE COLLISION
		gp.cChecker.checkTile(this);
		
		// CHECK INTERACTIVE TILE COLLISION
		gp.cChecker.checkEntity(this, gp.iTile);
		
		// DON'T CHECK PITS WHEN JUMPING
		if (action != Action.JUMPING && action != Action.SOARING) gp.cChecker.checkPit(this, true);
					
		// CHECK NPC COLLISION
		gp.cChecker.checkEntity(this, gp.npc);		
		int npcIndex = gp.cChecker.checkNPC();
		interactNPC(npcIndex);
		
		// CHECK ENEMY COLLISION
		int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
		contactEnemy(enemyIndex, gp.enemy);
		
		enemyIndex = gp.cChecker.checkEntity(this, gp.enemy_r);
		contactEnemy(enemyIndex, gp.enemy_r);
		
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
			if (keyH.actionPressed) {
				keyH.actionPressed = false;
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
				if (keyH.actionPressed) {
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

		if (item.type == type_equipment) {			
			if (item.name.equals(EQP_Sword_Old.eqpName) || 
					item.name.equals(EQP_Sword_Master.eqpName)) {
				currentWeapon = item;
				attack = gp.player.getAttack();
				getAttackImage();
			}			
			else if (item.name.equals(EQP_Flippers.eqpName)) {
				canSwim = true;
			}
			
			gp.ui.newItem = item;
			gp.ui.currentDialogue = "You got the " + item.name + "!";
		}
		else if (item.type == type_collectable) {
			item.use(this);
			return;
		}
		else if (item.type == type_key) {
			playGetItemSE();
			gp.player.keys++;
			
			gp.ui.newItem = item;
			gp.ui.currentDialogue = "You found a " + item.name + "!";		
		}
		else if (item.type == type_boss_key) {
			playGetItemSE();
			gp.player.boss_key++;
			
			gp.ui.newItem = item;
			gp.ui.currentDialogue = "You found the " + item.name + "!";	
		}	
		
		else if (item.type == type_item) {
			gp.player.inventory_item.add(item);	
			
			gp.ui.newItem = item;
			gp.ui.currentDialogue = "You got the " + item.name + "!";
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
			
			if (gp.enemy[gp.currentMap][i] != null && gp.enemy[gp.currentMap][i].type != type_boss) {
				int enemyDistance = getTileDistance(gp.enemy[gp.currentMap][i]);
				if (enemyDistance < currentDistance) {
					currentDistance = enemyDistance;
					target = gp.enemy[gp.currentMap][i];
				}
			}
		}
		if (target != null) {
			playLockOnSE();
		}
		else {
			for (int i = 0; i < gp.enemy_r[1].length; i++) {
				
				if (gp.enemy_r[gp.currentMap][i] != null && gp.enemy_r[gp.currentMap][i].type != type_boss) {
					int enemyDistance = getTileDistance(gp.enemy_r[gp.currentMap][i]);
					if (enemyDistance < currentDistance) {
						currentDistance = enemyDistance;
						target = gp.enemy_r[gp.currentMap][i];
					}
				}
			}
			if (target != null)
				playLockOnSE();
		}
		
		return target;
	}	
	public String findTargetDirection(Entity target) {
		
		String eDirection = direction;
		
		int px = (worldX + (hitbox.width / 2)) / gp.tileSize;
		int py = (worldY + (hitbox.width / 2)) / gp.tileSize;
		
		int ex = (target.worldX + (target.hitbox.width / 2)) / gp.tileSize;
		int ey = (target.worldY + (target.hitbox.height / 2)) / gp.tileSize;	
		
		if (py > ey && Math.abs(px-ex) <= Math.abs(py-ey)) 
			eDirection = "up";
		else if (py > ey && px-ex > Math.abs(py-ey)) 
			eDirection = "left";
		else if (py > ey && px-ex < Math.abs(py-ey)) 
			eDirection = "right";
		else if (py < ey && Math.abs(px-ex) <= Math.abs(py-ey)) 
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
			
			if (enemy.knockbackPower > 0) {
				setKnockback(gp.player, enemy, enemy.knockbackPower);
			}
			
			String guardDirection = getOppositeDirection(enemy.direction);
			if (action == Action.GUARDING && direction.equals(guardDirection)) {
				gp.playSE(4, 8);			
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
		gp.cChecker.checkEntity(this, gp.enemy_r);
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
			keyH.playSelectSE();
			
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
			
			keyH.playSelectSE();
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
		
		if (currentItem != null) {							
			switch (currentItem.name) {
				case ITM_Boots.itmName:
				case ITM_Cape.itmName:
				case ITM_Feather.itmName:
				case ITM_Rod.itmName:
				case ITM_Shovel.itmName:
					keyH.itemPressed = false;
					currentItem.use();
					break;		
				case ITM_Bomb.itmName:
					keyH.itemPressed = false;
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
					keyH.itemPressed = false;
					keyH.upPressed = false; keyH.downPressed  = false;
					keyH.leftPressed  = false; keyH.rightPressed  = false;	
					currentItem.use(this);
					break;	
			}	
		}
		else if (currentItem == null) {
			keyH.itemPressed = false;
			gp.gameState = gp.dialogueState;
			startDialogue(this, 2);
		}			
	}
	public void cycleItems() {
		
		if (currentItem != null) {
			keyH.playCursorSE();
			keyH.tabPressed = false;
								
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
				if (keyH.upPressed) {
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
				if (keyH.downPressed) {					
					pull(entity);
				}
				else {
					pullNum = 1;
					pullCounter = 0;
					action = Action.IDLE;
				}
				break;
			case "left":
				if (keyH.leftPressed) {
					pull(entity);
				}
				else {
					pullNum = 1;
					pullCounter = 0;
					action = Action.IDLE;
				}
				break;
			case "right":
				if (keyH.rightPressed) {
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
			keyH.grabPressed = false;	
		}
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
			int hitBoxWidth = hitbox.width;
			int hitBoxHeight = hitbox.height;
			
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
			hitbox.width = hitBoxWidth;
			hitbox.height = hitBoxHeight;
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
				
		if (target != null && !target.guarded) {
						
			// BUZZING ENEMY
			if (target.buzzing && attacker == gp.player) {
				if (!attacker.invincible) {
					target.playShockSE();
				}
				damagePlayer(target.attack);
			}			
			else if (!target.invincible && !target.captured) {
				target.playHurtSE();
				
				if (knockbackPower > 0 && target.type != type_boss) {
					setKnockback(target, attacker, knockbackPower);
				}
				
				// HIT BY PROJECTILE (NOT PROJECTILE BEAM)
				if (attacker.type == type_projectile) {
					target.stunned = true;
					target.spriteCounter = -30;
				}
								
				int damage = attack;
				if (damage < 0) damage = 1;		
				
				target.life -= damage;					
				target.invincible = true;
				target.damageReaction();
				
				// KILL ENEMY
				if (target.life <= 0) {
					target.playDeathSE();
					target.dying = true;
				}
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
				generateParticle(projectile, projectile);
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
						
				generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);
				
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
		else if (damageCounter >= 60) {
			
			life-= 2;
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
	private void playLiftSE() {
		gp.playSE(4, 10);
	}
	public void playThrowSE() {
		gp.playSE(4, 11);
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
	public void playLockOnSE() {
		gp.playSE(2, 1);
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
							image1 = attackUp1;
						}
						else if (attackNum == 2) image1 = attackUp2;
					}
					else {
						switch (action) {
						case CARRYING:
							if (spriteNum == 1) image1 = carryUp1;
							else if (spriteNum == 2) image1 = carryUp2;
							break;
						case GUARDING:
							image1 = guardUp1;
							break;
						case GRABBING:
							if (pullNum == 1) image1 = grabUp1;
							else if (pullNum == 2) image1 = grabUp2;
							else if (pullNum == 3) image1 = grabUp3;
							break;
						case DIGGING:
							if (digNum == 1) image1 = digUp1;
							else if (digNum == 2) image1 = digUp2;
							break;
						case JUMPING:
						case SOARING:
							tempScreenY -= 30;
							if (jumpNum == 1) image1 = jumpUp1;
							else if (jumpNum == 2) image1 = jumpUp2; 
							else if (jumpNum == 3) image1 = jumpUp3; 
							else if (jumpNum == 4) image1 = soarUp1; 
							
							g2.setColor(Color.BLACK);
							g2.fillOval(screenX + 10, screenY + 40, 30, 10);
							break;
						case ROLLING:
							if (rollNum == 1) image1 = rollUp1;
							else if (rollNum == 2) image1 = rollUp2; 
							else if (rollNum == 3) image1 = rollUp3; 
							else if (rollNum == 4) image1 = rollUp4; 
							break;
						case SWINGING:
							tempScreenY -= gp.tileSize;
							if (rodNum == 1) {
								tempScreenX -= gp.tileSize;
								image1 = rodUp1;
							}
							else if (rodNum == 2) image1 = rodUp2;	
							break;
						case SWIMMING:
							if (spriteNum == 1) image1 = swimUp1;
							else if (spriteNum == 2) image1 = swimUp2;
							break;
						case AIMING:
						case THROWING:
							image1 = throwUp1;
							break;
						default:
							if (spriteNum == 1) image1 = up1;
							else if (spriteNum == 2) image1 = up2;	
							break;
						}
					}
					break;
				case "down":
				case "downleft":
				case "downright":
					if (attacking) {
						if (attackNum == 1) image1 = attackDown1;
						else if (attackNum == 2) image1 = attackDown2;	
					}
					else {
						switch (action) {
						case CARRYING:
							if (spriteNum == 1) image1 = carryDown1;
							else if (spriteNum == 2) image1 = carryDown2;
							break;
						case GUARDING:
							image1 = guardDown1;
							break;
						case GRABBING:
							if (pullNum == 1) image1 = grabDown1;
							else if (pullNum == 2) image1 = grabDown2;
							else if (pullNum == 3) image1 = grabDown3;
							break;
						case DIGGING:
							if (digNum == 1) image1 = digDown1;
							else if (digNum == 2) image1 = digDown2;
							break;
						case JUMPING:
						case SOARING:
							tempScreenY -= 30;
							if (jumpNum == 1) image1 = jumpDown1;
							else if (jumpNum == 2) image1 = jumpDown2; 
							else if (jumpNum == 3) image1 = jumpDown3; 
							else if (jumpNum == 4) image1 = soarDown1; 
							
							g2.setColor(Color.BLACK);
							g2.fillOval(screenX + 10, screenY + 40, 30, 10);
							break;
						case ROLLING:
							if (rollNum == 1) image1 = rollDown1;
							else if (rollNum == 2) image1 = rollDown2; 
							else if (rollNum == 3) image1 = rollDown3; 
							else if (rollNum == 4) image1 = rollDown4; 
							break;
						case SWINGING:
							if (rodNum == 1) image1 = rodDown1;
							else if (rodNum == 2) image1 = rodDown2;
							break;
						case SWIMMING:
							if (spriteNum == 1) image1 = swimDown1;
							else if (spriteNum == 2) image1 = swimDown2;
							break;
						case AIMING:
						case THROWING:
							image1 = throwDown1;
							break;
						default:
							if (spriteNum == 1) image1 = down1;
							else if (spriteNum == 2) image1 = down2;	
							break;
						}
					}
					break;
				case "left":
					if (attacking) {
						tempScreenX -= gp.tileSize;
						if (attackNum == 1) {
							tempScreenY -= gp.tileSize;
							image1 = attackLeft1;
						}
						else if (attackNum == 2) image1 = attackLeft2;	
					}
					else {
						switch (action) {
						case CARRYING:
							if (spriteNum == 1) image1 = carryLeft1;
							else if (spriteNum == 2) image1 = carryLeft2;
							break;
						case GUARDING:
							image1 = guardLeft1;
							break;
						case GRABBING:
							if (pullNum == 1) image1 = grabLeft1;
							else if (pullNum == 2) image1 = grabLeft2;
							else if (pullNum == 3) image1 = grabLeft3;
							break;
						case DIGGING:
							if (digNum == 1) image1 = digLeft1;
							else if (digNum == 2) image1 = digLeft2;
							break;
						case JUMPING:
						case SOARING:
							tempScreenY -= 30;
							if (jumpNum == 1) image1 = jumpLeft1;
							else if (jumpNum == 2) image1 = jumpLeft2; 
							else if (jumpNum == 3) image1 = jumpLeft3; 
							else if (jumpNum == 4) image1 = soarLeft1; 
							
							g2.setColor(Color.BLACK);
							g2.fillOval(screenX + 10, screenY + 40, 30, 10);
							break;
						case ROLLING:
							if (rollNum == 1) image1 = rollLeft1;
							else if (rollNum == 2) image1 = rollLeft2; 
							else if (rollNum == 3) image1 = rollLeft3; 
							else if (rollNum == 4) image1 = rollLeft4; 
							break;
						case SWINGING:
							tempScreenX -= gp.tileSize;
							if (rodNum == 1) {
								tempScreenY -= gp.tileSize;
								image1 = rodLeft1;
							}
							else if (rodNum == 2) image1 = rodLeft2;	
							break;
						case SWIMMING:
							if (spriteNum == 1) image1 = swimLeft1;
							else if (spriteNum == 2) image1 = swimLeft2;
							break;						
						case AIMING:
						case THROWING:						
							image1 = throwLeft1;
							break;
						default:
							if (spriteNum == 1) image1 = left1;
							else if (spriteNum == 2) image1 = left2;	
							break;
						}
					}
					break;
				case "right":
					if (attacking) {
						if (attackNum == 1) {
							tempScreenY -= gp.tileSize;
							image1 = attackRight1;
						}
						else if (attackNum == 2) image1 = attackRight2;
					}
					else {
						switch (action) {
						case CARRYING:
							if (spriteNum == 1) image1 = carryRight1;
							else if (spriteNum == 2) image1 = carryRight2;
							break;
						case GUARDING:						
							image1 = guardRight1;						
							break;
						case GRABBING:
							if (pullNum == 1) image1 = grabRight1;
							else if (pullNum == 2) image1 = grabRight2;
							else if (pullNum == 3) image1 = grabRight3;
							break;
						case DIGGING:
							if (digNum == 1) image1 = digRight1;
							else if (digNum == 2) image1 = digRight2;
							break;
						case JUMPING:	
						case SOARING:
							tempScreenY -= 30;
							if (jumpNum == 1) image1 = jumpRight1;
							else if (jumpNum == 2) image1 = jumpRight2; 
							else if (jumpNum == 3) image1 = jumpRight3; 
							else if (jumpNum == 4) image1 = soarRight1; 
							
							g2.setColor(Color.BLACK);
							g2.fillOval(screenX + 10, screenY + 40, 30, 10);
							break;
						case ROLLING:
							if (rollNum == 1) image1 = rollRight1;
							else if (rollNum == 2) image1 = rollRight2; 
							else if (rollNum == 3) image1 = rollRight3; 
							else if (rollNum == 4) image1 = rollRight4; 
							break;
						case SWINGING:			
							if (rodNum == 1) {
								tempScreenY -= gp.tileSize;
								image1 = rodRight1;
							}
							else if (rodNum == 2) image1 = rodRight2;
							break;
						case SWIMMING:
							if (spriteNum == 1) image1 = swimRight1;
							else if (spriteNum == 2) image1 = swimRight2;
							break;
						case AIMING:
						case THROWING:
							image1 = throwRight1;
							break;
						default:
							if (spriteNum == 1) image1 = right1;
							else if (spriteNum == 2) image1 = right2;	
							break;
						}							
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
		
		if (gp.gameState == gp.fallingState) {
			if (damageNum == 1) image1 = fall1;
			else if (damageNum == 2) image1 = fall2;
			else if (damageNum == 3) image1 = fall3;
			else if (damageNum == 4) image1 = null;
		}	
		else if (gp.gameState == gp.drowningState) {
			image1 = drown;			
		}		
		
		g2.drawImage(image1, tempScreenX, tempScreenY, null);
		
		// RESET OPACITY
		changeAlpha(g2, 1f);
	}	
}
/** END PLAYER METHODS **/

/** END PLAYER CLASS **/