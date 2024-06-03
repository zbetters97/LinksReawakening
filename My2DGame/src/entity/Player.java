package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
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
	
	public ArrayList<Entity> items = new ArrayList<>();
	public final int maxItemInventorySize = 10;
	public int itemIndex = 0;

	public boolean attackCanceled = false;
	public boolean running = false;
	
	public int digNum;
	public int digCounter = 0;
	public boolean digging = false;
	
	public BufferedImage digUp1, digUp2, digDown1, digDown2, digLeft1, digLeft2, digRight1, digRight2;
	public BufferedImage titleScreen;
	
	public Projectile projectile_sword;
	public Projectile projectile_arrow;
	public Projectile projectile_bomb;
	public Projectile projectile_hookshot;
	public Projectile projectile_boomerang;
			
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
		rupees = 0;
		
		currentShield = new EQP_Shield(gp);
		projectile_sword = new PRJ_Sword_Beam(gp);
		projectile_arrow = new PRJ_Arrow(gp);
		projectile_bomb = new PRJ_Bomb(gp);
		projectile_hookshot = new PRJ_Hookshot(gp);
		projectile_boomerang = new PRJ_Boomerang(gp);
		
		maxArrows = 5; arrows = maxArrows;
		maxBombs = 5; bombs = maxBombs;
		
		attack = getAttack();
		defense = getDefense();
	}	
	public void setDefaultPosition() {	
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 22;
		direction = "down";
		speed = baseSpeed;
	}
	public void setItems() {
		inventory.add(currentShield);	
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
		
		itemGet = setup("/player/boy_item_get");
		
		sit = setup("/player/boy_sit"); 
		sing = setup("/npc/girl_sing_1");
		
		die1 = setup("/player/boy_die_1"); 
		die2 = setup("/player/boy_die_2");
		die3 = setup("/player/boy_die_3"); 
		die4 = setup("/player/boy_die_4");
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
		digUp1 = setup("/player/boy_dig_up_1", gp.tileSize, gp.tileSize); 
		digUp2 = setup("/player/boy_dig_up_2", gp.tileSize, gp.tileSize);		
		digDown1 = setup("/player/boy_dig_down_1", gp.tileSize, gp.tileSize); 
		digDown2 = setup("/player/boy_dig_down_2", gp.tileSize, gp.tileSize);
		
		digLeft1 = setup("/player/boy_dig_left_1", gp.tileSize, gp.tileSize); 
		digLeft2 = setup("/player/boy_dig_left_2", gp.tileSize, gp.tileSize);
		
		digRight1 = setup("/player/boy_dig_right_1", gp.tileSize, gp.tileSize); 
		digRight2 = setup("/player/boy_dig_right_2", gp.tileSize, gp.tileSize);		
	}
	
	public void update() {
				
		if (attacking) attacking();		
		if (digging) digging();			
		
		else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || 
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
			
			// CHECK EVENT
			gp.eHandler.checkEvent();
			
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
								
				gp.playSE(3, 0);
				attacking = true;
				spriteCounter = 0;
				
				// SHOOT SWORD BEAM
				if (projectile_sword.hasResource(this) && !projectile_sword.alive && 
						shotAvailableCounter == 30 ) { 	
											
					projectile_sword.set(worldX, worldY, direction, true, this);			
					gp.projectileList.add(projectile_sword);			
					
					projectile_sword.subtractResource(this);
					
					shotAvailableCounter = 0;		
					
					gp.playSE(3, 4);
				}
			}
			if (currentWeapon == null && keyH.spacePressed) {
				gp.gameState = gp.dialogueState;
				gp.ui.currentDialogue = "\"I need to find a sword!\nBut where?...\"";
			}
			
			attackCanceled = false;
			
			// WALKING ANIMATION
			spriteCounter++;
			if (spriteCounter > animationSpeed) { // speed of sprite change
								
				// CYLCE WALKING SPRITES
				if (spriteNum == 1) spriteNum = 2;
				else if (spriteNum == 2) spriteNum = 1;
				
				// RUNNING ANIMATION
				if (running) {
					gp.playSE(3, 6);
					speed = runSpeed;
					animationSpeed = 6;
				}
				else {
					speed = baseSpeed; 
					animationSpeed = 10; 
				}					
				spriteCounter = 0;
			}					
		}		
		
		// USE ITEM
		if (keyH.itemPressed && currentItem != null) {		
			useItem();
		}			
		if (keyH.itemPressed && currentItem == null) {
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "\"I need to find an item!\nBut where?...\"";
		}
		
		// CYCLES ITEMS
		if (keyH.tabPressed && items.size() > 0 && currentItem != null) {
			cycleItems();
		}		
				
		// PROJECTILE REFRESH TIME
		if (shotAvailableCounter < 30) 
			shotAvailableCounter++;	
		
		// KEEP ARROWS WITHIN MAX
		if (arrows > maxArrows)	
			arrows = maxArrows;	
				
		// KEEP HEARTS WITHIN MAX
		if (life > maxLife) 
			life = maxLife;
		
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
			gp.gameState = gp.gameOverState;
			gp.ui.commandNum = -1;
			gp.stopMusic();
			gp.playSE(2, 1);
			alive = false;	
		}
	}
	
	public void useItem() {
		
		switch (currentItem.name) {
		
			case "Hylian Bow":					
				if (!projectile_arrow.alive && shotAvailableCounter == 30 && 
						projectile_arrow.hasResource(this)) {							
					
					projectile_arrow.set(worldX, worldY, direction, true, this);			
					gp.projectileList.add(projectile_arrow);			
					
					projectile_arrow.subtractResource(this);
					
					shotAvailableCounter = 0;		
					
					gp.playSE(3, 2);
				}
				break;			
			case "Hookshot": 					
				if (!projectile_hookshot.alive && shotAvailableCounter == 30) { 			
								
					// STOP MOVEMENT
					keyH.upPressed = false; keyH.downPressed  = false;
					keyH.leftPressed  = false; keyH.rightPressed  = false;
					
					projectile_hookshot.set(worldX, worldY, direction, true, this);			
					gp.projectileList.add(projectile_hookshot);	
								
					shotAvailableCounter = 0;	
				}					
				break;	
			case "Boomerang": 					
				if (!projectile_boomerang.alive && shotAvailableCounter == 30) { 			
												
					// STOP MOVEMENT
					keyH.upPressed = false; keyH.downPressed  = false;
					keyH.leftPressed  = false; keyH.rightPressed  = false;
					
					projectile_boomerang.set(worldX, worldY, direction, true, this);			
					gp.projectileList.add(projectile_boomerang);	
								
					shotAvailableCounter = 0;	
				}					
				break;	
			case "Running Shoes":
				running = true;
				break;	
			case "Iron Axe":
				if (!attackCanceled && !attacking) {
					gp.playSE(3, 0);
					attacking = true;
				}
				break;		
			case "Shovel":				
				digging = true;
				attackCanceled = true;
				break;
			case "Bomb":
				if (!projectile_bomb.alive && shotAvailableCounter == 30 && 
						projectile_bomb.hasResource(this)) {
					
					projectile_bomb.set(worldX, worldY, direction, true, this);			
					gp.projectileList.add(projectile_bomb);			
					
					projectile_bomb.subtractResource(this);
					
					shotAvailableCounter = 0;
				}
				break;
		}	
	}
	
	public void cycleItems() {
		gp.playSE(1, 0);
		running = false;
		keyH.tabPressed = false;
		
		itemIndex++;			
		if (itemIndex >= items.size())
			itemIndex = 0;	
		
		currentItem = items.get(itemIndex);
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
		}
	}
	
	public void digging() {
		
		digCounter++;
				
		if (12 >= digCounter) 
			digNum = 1;
		
		if (24 > digCounter && digCounter > 12)
			digNum = 2;
		
		if (digCounter > 24) {
			
			// CHECK INTERACTIVE TILE
			int iTileIndex = gp.cChecker.checkDigging();
			damageInteractiveTile(iTileIndex);

			digNum = 1;
			digCounter = 0;
			digging = false;
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
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			
			gp.ui.addMessage("Leveled up to level " + level + "!");
		}
	}
	
	public void interactNPC(int i) {
		
		if (keyH.spacePressed) {
			
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
			else if (gp.obj[gp.currentMap][i].type == type_item) {
									
				gp.playSE(3, 1);																							
				gp.ui.currentDialogue = "You got the " + gp.obj[gp.currentMap][i].name + "!";
					
				items.add(gp.obj[gp.currentMap][i]);					
				inventory.add(gp.obj[gp.currentMap][i]);		
					
				gp.gameState = gp.itemGetState;
				gp.ui.newItem = gp.obj[gp.currentMap][i];
				gp.obj[gp.currentMap][i] = null;
			}			
			else {
				String text;
				
				if (inventory.size() != maxInventorySize) {	
					
					gp.playSE(3, 1);
					
					Entity object = gp.obj[gp.currentMap][i];
																		
					text = "You found the " + object.name + "!";
					
					inventory.add(object);
				}
				else
					text = "You cannot carry any more items!";
				
				gp.ui.addMessage(text);
				gp.obj[gp.currentMap][i] = null;
			}
		}
	}
	
	public void getObject(Entity item) {
		
		if (item != null) {
			
			gp.playSE(3, 1);																							
			gp.ui.currentDialogue = "You got the " + item.name + "!";				
			inventory.add(item);	
			
			// INVENTORY ITEMS
			if (item.type == type_item) 
				items.add(item);
			if (item.type == type_sword) {
				currentWeapon = item;
				attack = getAttack();
			}
					
			gp.gameState = gp.itemGetState;
			gp.ui.newItem = item;
		}
	}
	
	public void selectItem() {
		
		int inventoryIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
		if (inventoryIndex < inventory.size()) {
			
			gp.playSE(1, 1); 
			running = false;
			itemIndex = inventoryIndex;										
									
			Entity selectedItem = inventory.get(inventoryIndex);
			if (selectedItem.type == type_sword || selectedItem.type == type_axe) {
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
				selectedItem.use(this);
				inventory.remove(selectedItem);
				selectedItem = null;
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
					if (digging) {
						if (digNum == 1) image = digUp1;
						if (digNum == 2) image = digUp2;
					}
					else if (!attacking) {
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
					if (digging) {
						if (digNum == 1) image = digDown1;
						if (digNum == 2) image = digDown2;
					}
					else if (!attacking) {
						if (spriteNum == 1) image = down1;
						if (spriteNum == 2) image = down2;	
					}					
					else {		
						if (attackNum == 1) image = attackDown1;
						if (attackNum == 2) image = attackDown2;	
					}		
					break;
				case "left":
					if (digging) {
						if (digNum == 1) image = digLeft1;
						if (digNum == 2) image = digLeft2;
					}
					else if (!attacking) {
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
					if (digging) {
						if (digNum == 1) image = digRight1;
						if (digNum == 2) image = digRight2;
					}
					else if (!attacking) {
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