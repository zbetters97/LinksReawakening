package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GamePanel;
import main.KeyHandler;
import object.*;

public class Player extends Entity {

	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	
	public boolean attackCanceled = false;
	
	public boolean canRun = false;
	public boolean canShoot = false;
	public boolean canGrab = false;
		
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
	}
	
	public void setDefaultValues() {
						
		setDefaultPosition();
		
		speed = 3; baseSpeed = speed;
		runSpeed = 6; animationSpeed = 10;
		
		// PLAYER ATTRIBUTES
		level = 1;
		maxLife = 6; life = maxLife;
		strength = 1; dexterity = 1; // helps attack, defense
		exp = 0; nextLevelEXP = 5;
		rupee = 0;
		
		currentWeapon = new OBJ_Sword(gp);
		currentShield = new OBJ_Shield(gp);		
		projectile = new PRJ_Arrow(gp);
		projectile_item = new PRJ_Hookshot(gp);
		projectile_sword = new PRJ_Sword_Beam(gp);
		
		maxArrows = 5; arrows = maxArrows;
		
		attack = getAttack();
		defense = getDefense();
	}	
	public int getAttack() {
		attackArea = currentWeapon.attackArea;
		return attack = strength * currentWeapon.attackValue;
	}
	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
	}
	public void setItems() {
		inventory.add(currentWeapon);
		inventory.add(currentShield);
	}
	public void setDefaultPosition() {		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		direction = "down";
		speed = baseSpeed;
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
		
		itemGet = setup("/player/boy_item_get");
		
		sit = setup("/player/boy_sit"); 
		sing = setup("/npc/girl_sing_1");
		
		die1 = setup("/player/boy_die_1"); 
		die2 = setup("/player/boy_die_2");
		die3 = setup("/player/boy_die_3"); 
		die4 = setup("/player/boy_die_4");
	}
	
	public void getPlayerAttackImage() {
		
		if (currentWeapon.type == type_sword || currentWeapon.type == type_axe) {
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
	
	public void update() {
		
		// RUN BUTTON
		if (keyH.shiftPressed && canRun) { 
			speed = runSpeed; 
			animationSpeed = 6; 
		}
		else { 
			speed = baseSpeed; 
			animationSpeed = 10; 
		}
		
		if (attacking)
			attacking();
						
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
			
			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			// CHECK ENEMY COLLISION
			int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
			contactEnemy(enemyIndex);
			
			// CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
							
			// CHECK INTERACTIVE TILE COLLISION
			gp.cChecker.checkEntity(this, gp.iTile);
			
			// CHECK EVENT
			gp.eHandler.checkEvent();
			
			// MOVE IF NO COLLISION
			if (!collisionOn && !keyH.spacePressed) { 
				
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
			
			// SWING SWORD VALID
			if (keyH.spacePressed && !attackCanceled) {
				
				gp.playSE(3, 0);
				attacking = true;
				spriteCounter = 0;
				
				// SHOOT SWORD BEAM
				if (currentWeapon.type == type_sword && !projectile_sword.alive && 
						shotAvailableCounter == 30 && projectile_sword.hasResource(this)) { 	
											
					projectile_sword.set(worldX, worldY, direction, true, this);			
					gp.projectileList.add(projectile_sword);			
					
					projectile_sword.subtractResource(this);
					
					shotAvailableCounter = 0;		
					
					gp.playSE(3, 4);
				}
			}
			
			attackCanceled = false;			
			gp.keyH.spacePressed = false;
			
			// WALKING ANIMATION
			spriteCounter++;
			if (spriteCounter > animationSpeed) { // speed of sprite change

				if (spriteNum == 1) spriteNum = 2;
				else if (spriteNum == 2) spriteNum = 1;
				
				spriteCounter = 0;
			}	
		}		
		// SHOOT HOOKSHOT (STOP PLAYER MOVEMENT)
		if (gp.keyH.hookPressed && !projectile_item.alive && 
				shotAvailableCounter == 30 && canGrab) { 			
									
			keyH.upPressed = false; keyH.downPressed  = false;
			keyH.leftPressed  = false; keyH.rightPressed  = false;
			
			projectile_item.set(worldX, worldY, direction, true, this);			
			gp.projectileList.add(projectile_item);	
						
			shotAvailableCounter = 0;	
		}
		
		// SHOOT ARROW (CAN SHOOT WHILE MOVING)
		if (gp.keyH.itemPressed && !projectile.alive && shotAvailableCounter == 30 
				&& projectile.hasResource(this) && canShoot) { 			
									
			projectile.set(worldX, worldY, direction, true, this);			
			gp.projectileList.add(projectile);			
			
			projectile.subtractResource(this);
			
			shotAvailableCounter = 0;		
			
			gp.playSE(3, 2);
		}
				
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
				
		// KEEP HEARTS WITHIN MAX
		if (life > maxLife) 
			life = maxLife;
		
		// KEEP ARROWS WITHIN MAX
		if (arrows > maxArrows)	
			arrows = maxArrows;
		
		// PLAYER DIES
		if (life <= 0) {
			gp.stopMusic();
			gp.playSE(2, 1);
			alive = false;
			gp.gameState = gp.gameOverState;		
		}
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
			damageEnemy(enemyIndex, attack);			
			
			// CHECK INTERACTIVE TILE
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
			damageInteractiveTile(iTileIndex);
			
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
		}
	}
	
	public void contactEnemy(int i) {
		
		// PLAYER HIT BY ENEMY
		if (i != -1) {					
			if (!invincible && !gp.enemy[gp.currentMap][i].dying) {
				gp.playSE(2, 0);
				
				int damage = gp.enemy[gp.currentMap][i].attack - defense;
				if (damage < 0) damage = 0;				
				this.life -= damage;
				
				invincible = true;
			}
		}
	}
	
	public void damageEnemy(int i, int attack) {
		
		// ATTACK LANDS
		if (i != -1) {
			
			// HURT ENEMY
			if (!gp.enemy[gp.currentMap][i].invincible) {
				gp.playSE(4, 1);
				
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
	
	public void damageInteractiveTile(int i) {
		
		if (i != -1 && gp.iTile[gp.currentMap][i].destructible && !gp.iTile[gp.currentMap][i].invincible &&
				gp.iTile[gp.currentMap][i].isCorrectItem(this)) {
			
			gp.iTile[gp.currentMap][i].playSE();
			gp.iTile[gp.currentMap][i].life--;
			gp.iTile[gp.currentMap][i].invincible = true;
					
			generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);
			
			if (gp.iTile[gp.currentMap][i].life == 0)
				gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
		}
	}
	
	public void checkLevelUp() {
		
		if (exp >= nextLevelEXP) {
			gp.playSE(1, 3);
			level++;
			nextLevelEXP *= 2;
			maxLife += 2;
			life = maxLife;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			
			gp.ui.addMessage("Leveled up to level " + level + "!");
		}
	}
	
	public void interactNPC(int i) {
		
		if (gp.keyH.spacePressed) {
			
			// TALKING TO NPC
			if (i != -1) {		
				attackCanceled = true;
				gp.gameState = gp.dialogueState;
				gp.npc[gp.currentMap][i].speak();
			}
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
			else {
				String text;
				
				if (inventory.size() != maxInventorySize) {		
					gp.playSE(3, 1);
					inventory.add(gp.obj[gp.currentMap][i]);				
					text = "You found the " + gp.obj[gp.currentMap][i].name + "!";
					
					if (gp.obj[gp.currentMap][i].name.equals("Running Shoes")) 
						canRun = true;
					
					if (gp.obj[gp.currentMap][i].name.equals("Hylian Bow")) 
						canShoot = true;
					
					if (gp.obj[gp.currentMap][i].name.equals("Hookshot")) 
						canGrab = true;
				}
				else
					text = "You cannot carry any more items!";
				
				gp.ui.addMessage(text);
				gp.obj[gp.currentMap][i] = null;
			}
		}
	}
	
	public void selectItem() {
		
		int itemIndex = gp.ui.getItemIndexOnSlot();
		if (itemIndex < inventory.size()) { // selecting an item
									
			Entity selectedItem = inventory.get(itemIndex);
			if (selectedItem.type == type_sword || selectedItem.type == type_axe) {
				currentWeapon = selectedItem;
				attack = getAttack();
			}
			if (selectedItem.type == type_shield) {
				currentShield = selectedItem;
				defense = getDefense();
			}
			if (selectedItem.type == type_consumable) {
				selectedItem.use(this);
				inventory.remove(itemIndex);
			}
			
			getPlayerAttackImage();
		}
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
					if (!attacking) {
						if (spriteNum == 1) image = up1;
						if (spriteNum == 2) image = up2;	
					}
					else {
						if (attackNum == 1) {
							tempScreenX -= gp.tileSize;
							tempScreenY -= gp.tileSize;
							image = attackUp1;
						}
						if (attackNum == 2) {
							tempScreenY -= gp.tileSize;
							image = attackUp2;	
						}
					}				
					break;
				case "down":
				case "downleft":
				case "downright":
					if (!attacking) {
						if (spriteNum == 1) image = down1;
						if (spriteNum == 2) image = down2;	
					}
					else {		
						if (attackNum == 1) image = attackDown1;
						if (attackNum == 2) image = attackDown2;	
					}		
					break;
				case "left":
					if (!attacking) {
						if (spriteNum == 1) image = left1;
						if (spriteNum == 2) image = left2;	
					}
					else {
						tempScreenX -= gp.tileSize;
						if (attackNum == 1) {
							tempScreenY -= gp.tileSize;
							image = attackLeft1;
						}
						if (attackNum == 2) image = attackLeft2;	
					}		
					break;
				case "right":
					if (!attacking) {
						if (spriteNum == 1) image = right1;
						if (spriteNum == 2) image = right2;	
					}
					else {
						if (attackNum == 1) {
							tempScreenY -= gp.tileSize;
							image = attackRight1;
						}
						if (attackNum == 2) image = attackRight2;
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
						
		g2.drawImage(image, tempScreenX, tempScreenY, null); 
		
		// RESET OPACITY
		changeAlpha(g2, 1f);
	}
	
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
}