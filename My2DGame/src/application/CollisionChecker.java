package application;

import java.util.ArrayList;

import entity.Entity;
import entity.Entity.Action;
import entity.enemy.EMY_Octorok;
import entity.projectile.PRJ_Bomb;

public class CollisionChecker {
	
	private GamePanel gp;
	
	/** CONSTRUCTOR **/
	public CollisionChecker(GamePanel gp) {		
		this.gp = gp;
	}

	// TILE COLLISION
	public void checkTile(Entity entity) {
		
		// COLLISION BOX (left side, right side, top, bottom)
		int entityLeftWorldX = entity.worldX + entity.hitbox.x;
		int entityRightWorldX = entity.worldX + entity.hitbox.x + entity.hitbox.width;
		int entityTopWorldY = entity.worldY + entity.hitbox.y;
		int entityBottomWorldY = entity.worldY + entity.hitbox.y + entity.hitbox.height;
		
		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;
		
		// PREVENT COLLISION DETECTION OUT OF BOUNDS
		if (entityTopRow <= 0) return;		
		if (entityBottomRow >= gp.maxWorldRow - 1) return;		
		if (entityLeftCol <= 0) return;		
		if (entityRightCol >= gp.maxWorldCol - 1) return;
		
		// detect the two tiles player is interacting with
		int tileNum1 = 0, tileNum2 = 0;
		
		// KNOCKBACK DIRECTION
		String direction = entity.direction;
		if (entity.lockon) direction = entity.lockonDirection;
		if (entity.knockback) direction = entity.knockbackDirection;
						
		// find tile player will interact with, factoring in speed
		switch (direction) {
			case "up":
				entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;	
				
				// tiles at top-left and top-right
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
				break;
			case "upleft":
				
				// tiles at top-left and left-top
				entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
				
				entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];	
				
				break;
			case "upright":
				
				// tiles at top-right and right-top
				entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
				
				entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];	
				
				break;
			case "down":
				entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
				
				// tiles at bottom-left and bottom-right
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
				
				break;
			case "downleft":
				
				// tiles at bottom-left and left-bottom
				entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
				
				entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
				
				break;
			case "downright":
				
				// tiles at bottom-right and right-bottom
				entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
				
				entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
				
				break;
			case "left":
				entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
				
				// tiles at left-top and left-bottom
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
				
				break;
			case "right":
				entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
				
				// tiles at right-top and right-bottom
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
				
				break;
			default: 
				entity.collisionOn = false; 
				return;
		}		

		// NPC AND EMNEMIES
		if (entity.type == entity.type_npc || entity.type == entity.type_enemy || entity.type == entity.type_boss) {
			
			// PIT
			if (gp.tileM.tile[tileNum1].pit || gp.tileM.tile[tileNum2].pit) {
												
				// ENEMY CAN FALL IN PIT IF HIT
				if (!entity.knockback && entity.onGround) {
					entity.collisionOn = true;	
				}
			}
			// WATER
			else if (gp.tileM.tile[tileNum1].water || gp.tileM.tile[tileNum2].water) {
				
				// ENEMY CAN FALL IN WATER IF HIT
				if (!entity.canSwim && !entity.knockback) {
					entity.collisionOn = true;
				}
			}
			// SPIKES
			else if (tileNum1 == gp.tileM.spikeTile || tileNum2 == gp.tileM.spikeTile) {				
				entity.collisionOn = true;
			}
			else if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
			}			
			if (entity.name.equals(EMY_Octorok.emyName)) {
				if (tileNum1 != gp.tileM.waterTile || tileNum2 != gp.tileM.waterTile) {
					entity.collisionOn = true;
				}
			}
		}
		// PROJECTILES
		else if (entity.type == entity.type_projectile) {
			
			if (gp.tileM.tile[tileNum1].pit || gp.tileM.tile[tileNum2].pit ||
					gp.tileM.tile[tileNum1].water || gp.tileM.tile[tileNum2].water) {
				entity.collisionOn = false;
			}
			// NO COLLISION FOR BOUNDARY WATER
			else if (tileNum1 == gp.tileM.oceanTile1 || tileNum2 == gp.tileM.oceanTile1
					|| tileNum1 == gp.tileM.oceanTile2 || tileNum2 == gp.tileM.oceanTile2) {
				entity.collisionOn = false;
			}
			else if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {				
				entity.collisionOn = true;
			}				
		}
		// PLAYER
		else if (entity == gp.player){
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
			}
			// SPIKES
			else if (tileNum1 == gp.tileM.spikeTile || tileNum2 == gp.tileM.spikeTile) {	
												
				if (!gp.player.invincible) {
					
					String kbDirection = "down";
					
					switch (entity.direction) {
						case "up": kbDirection = "down"; break;	
						case "down": kbDirection = "up"; break;	
						case "left": kbDirection = "right"; break;	
						case "right": kbDirection = "left"; break;	
					}
					
					gp.player.setKnockback(gp.player, kbDirection, 1);
					
					gp.player.life--;
					gp.player.transparent = true;
					gp.player.invincible = true;	
				}				
			}
		}
		// OTHER
		else {
			if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
				entity.collisionOn = true;
			}
		}
	}
	
	// DAMAGE PIT COLLISION
	public void checkPit(Entity entity, boolean player) {
		
		// COLLISION BOX (left side, right side, top, bottom)
		int entityWorldX = entity.worldX + entity.hitbox.x + (entity.hitbox.width / 2);
		int entityWorldY = entity.worldY + entity.hitbox.y + (entity.hitbox.height / 2);
		
		int entityLeftCol = entityWorldX / gp.tileSize;
		int entityRightCol = entityWorldX / gp.tileSize;
		int entityTopRow = entityWorldY / gp.tileSize;
		int entityBottomRow = entityWorldY / gp.tileSize;
		
		// PREVENT COLLISION DETECTION OUT OF BOUNDS
		if (entityTopRow <= 0) return;		
		if (entityBottomRow >= gp.maxWorldRow - 1) return;		
		if (entityLeftCol <= 0) return;		
		if (entityRightCol >= gp.maxWorldCol - 1) return;
		
		// detect the two tiles player is interacting with
		int tileNum1 = 0, tileNum2 = 0;
		
		// KNOCKBACK DIRECTION
		String direction = entity.direction;
		if (entity.lockon) direction = entity.lockonDirection;		
		if (entity.knockback) direction = entity.knockbackDirection;
				
		switch (direction) {
			case "up":				
				
				entityTopRow = entityWorldY / gp.tileSize;	
					
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
				
				if (player) {
					if (gp.player.onGround) {
						gp.player.safeWorldX = gp.player.worldX;
						gp.player.safeWorldY = gp.player.worldY + 35;
					}
				}
				
				break;
			case "upleft":
				
				entityTopRow = entityWorldY / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
				
				entityLeftCol = entityWorldX / gp.tileSize;
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];	
				
				if (player) {
					if (gp.player.onGround) {
						gp.player.safeWorldX = gp.player.worldX + 35;
						gp.player.safeWorldY = gp.player.worldY + 35;
					}
				}
				
				break;
			case "upright":
				
				entityTopRow = entityWorldY / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
				
				entityRightCol = entityWorldX / gp.tileSize;
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];	
				
				if (player) {
					if (gp.player.onGround) {
						gp.player.safeWorldX = gp.player.worldX - 35;
						gp.player.safeWorldY = gp.player.worldY + 35;
					}
				}
				
				break;
			case "down":
				
				entityBottomRow = entityWorldY / gp.tileSize;
				
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];				

				if (player) {
					if (gp.player.onGround) {
						gp.player.safeWorldX = gp.player.worldX;
						gp.player.safeWorldY = gp.player.worldY - 35;
					}
				}
				
				break;
			case "downleft":
				
				entityBottomRow = entityWorldY / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
				
				entityLeftCol = entityWorldX / gp.tileSize;
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
				
				if (player) {
					if (gp.player.onGround) {
						gp.player.safeWorldX = gp.player.worldX + 35;
						gp.player.safeWorldY = gp.player.worldY - 35;
					}
				}
				
				break;
			case "downright":
				
				entityBottomRow = entityWorldY / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
				
				entityRightCol = entityWorldX / gp.tileSize;
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
				
				if (player) {
					if (gp.player.onGround) {
						gp.player.safeWorldX = gp.player.worldX - 35;
						gp.player.safeWorldY = gp.player.worldY - 35;
					}
				}
				break;	
			case "left":
				
				entityLeftCol = entityWorldX / gp.tileSize;
				
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
				
				if (player) {
					if (gp.player.onGround) {
						gp.player.safeWorldX = gp.player.worldX + 35;
						gp.player.safeWorldY = gp.player.worldY;
					}
				}
				
				break;				
			case "right":
				
				entityRightCol = entityWorldX / gp.tileSize;
				
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
				
				if (player) {
					if (gp.player.onGround) {
						gp.player.safeWorldX = gp.player.worldX - 35;
						gp.player.safeWorldY = gp.player.worldY;
					}
				}
				
				break;
			default: 
				return;
		}		

		// PIT
		if (gp.tileM.tile[tileNum1].pit || gp.tileM.tile[tileNum2].pit) {
			if (entity.action != Action.JUMPING && entity.action != Action.SOARING &&
					gp.gameState == gp.playState) {
				
				if (player) {
					gp.playSE(2, 4);			
					gp.player.invincible = true;
					gp.gameState = gp.fallingState;
				}
				else {
					if (entity.name.equals(PRJ_Bomb.prjName)) {
						entity.animationSpeed = 30;
						entity.active = false;	
					}
					if (entity.onGround) {
						entity.alive = false;
					}
				}				
			}
		}
		// WATER
		else if (gp.tileM.tile[tileNum1].water || gp.tileM.tile[tileNum2].water) {			
			
			if (player) {
				gp.player.action = Action.SWIMMING;
				
				if (!gp.player.canSwim && gp.gameState == gp.playState) {
					gp.player.playDrownSE();
					gp.player.playHurtSE();	
					gp.player.invincible = true;
					gp.gameState = gp.drowningState;
				}			
			}
			else {
				if (entity.name.equals(PRJ_Bomb.prjName)) {
					entity.animationSpeed = 30;
					entity.active = false;	
				}
				if (!entity.canSwim) {
					entity.alive = false;
				}
			}
		}		
		else {
			if (entity.action == Action.SWIMMING)
				entity.action = Action.IDLE;
		}
	}	
	
	// NPC COLLISION
	public int checkNPC() {
		
		int index = -1;
		int speed = 30;
		
		String direction = gp.player.direction;
		if (gp.player.lockon) direction = gp.player.lockonDirection;
		if (gp.player.knockback) direction = gp.player.knockbackDirection;
			
		for (int i  = 0; i < gp.npc[1].length; i++) {
			
			if (gp.npc[gp.currentMap][i] != null) {			
				
				// get gp.player's solid area position
				gp.player.hitbox.x = gp.player.worldX + gp.player.hitbox.x;
				gp.player.hitbox.y = gp.player.worldY + gp.player.hitbox.y;
				
				// get object's solid area position
				gp.npc[gp.currentMap][i].hitbox.x = gp.npc[gp.currentMap][i].worldX + gp.npc[gp.currentMap][i].hitbox.x;
				gp.npc[gp.currentMap][i].hitbox.y = gp.npc[gp.currentMap][i].worldY + gp.npc[gp.currentMap][i].hitbox.y;
				
				// find where gp.player will be after moving in a direction
				// ask if gp.npc and gp.player intersect 
				switch (direction) {
					case "up":					
						gp.player.hitbox.y -= speed;
						break;					
					case "upleft":
						gp.player.hitbox.y -= speed;
						gp.player.hitbox.x -= speed;
						break;
					case "upright":
						gp.player.hitbox.y -= speed;
						gp.player.hitbox.x += speed;
						break;
					case "down":					
						gp.player.hitbox.y += speed;
						break;
					case "downleft":					
						gp.player.hitbox.y += speed;
						gp.player.hitbox.x -= speed;
						break;
					case "downright":					
						gp.player.hitbox.y += speed;
						gp.player.hitbox.x += speed;
						break;
					case "left":					
						gp.player.hitbox.x -= speed;
						break;
					case "right":					
						gp.player.hitbox.x += speed;
						break;	
					default: 
						return index;
				}
				
				if (gp.player.hitbox.intersects(gp.npc[gp.currentMap][i].hitbox)) {	
					
					if (gp.npc[gp.currentMap][i] != gp.player) {		
						index = i;	
					}
				}
				
				// reset gp.player solid area
				gp.player.hitbox.x = gp.player.hitboxDefaultX;
				gp.player.hitbox.y = gp.player.hitboxDefaultY;
				
				// reset object solid area
				gp.npc[gp.currentMap][i].hitbox.x = gp.npc[gp.currentMap][i].hitboxDefaultX;
				gp.npc[gp.currentMap][i].hitbox.y = gp.npc[gp.currentMap][i].hitboxDefaultY;
			}
		}		
		
		return index;
	}
	
	// ENTITY COLLISION
	public int checkEntity(Entity entity, Entity[][] target) {
		
		int index = -1;
		
		String direction = entity.direction;
		if (entity.lockon) direction = entity.lockonDirection;
		if (entity.knockback) direction = entity.knockbackDirection;
				
		for (int i  = 0; i < target[1].length; i++) {
			
			if (target[gp.currentMap][i] != null) {			
				
				entity.hitbox.x = entity.worldX + entity.hitbox.x;
				entity.hitbox.y = entity.worldY + entity.hitbox.y;
				
				target[gp.currentMap][i].hitbox.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].hitbox.x;
				target[gp.currentMap][i].hitbox.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].hitbox.y;
			
				switch (direction) {
					case "up":					
						entity.hitbox.y -= entity.speed;
						break;					
					case "upleft":
						entity.hitbox.y -= entity.speed;
						entity.hitbox.x -= entity.speed;
						break;
					case "upright":
						entity.hitbox.y -= entity.speed;
						entity.hitbox.x += entity.speed;
						break;
					case "down":					
						entity.hitbox.y += entity.speed;
						break;
					case "downleft":					
						entity.hitbox.y += entity.speed;
						entity.hitbox.x -= entity.speed;
						break;
					case "downright":					
						entity.hitbox.y += entity.speed;
						entity.hitbox.x += entity.speed;
						break;
					case "left":					
						entity.hitbox.x -= entity.speed;
						break;
					case "right":					
						entity.hitbox.x += entity.speed;
						break;	
					default: 
						entity.collision = true; 
						return index;
				}
				
				if (entity.hitbox.intersects(target[gp.currentMap][i].hitbox)) {	
										
					if (target[gp.currentMap][i] != entity) {		
						index = i;	
											
						if (target[gp.currentMap][i].collision) 
							entity.collisionOn = true;							
					}
				}
				
				// reset entity solid area
				entity.hitbox.x = entity.hitboxDefaultX;
				entity.hitbox.y = entity.hitboxDefaultY;
				
				// reset object solid area
				target[gp.currentMap][i].hitbox.x = target[gp.currentMap][i].hitboxDefaultX;
				target[gp.currentMap][i].hitbox.y = target[gp.currentMap][i].hitboxDefaultY;
			}
		}		
		return index;
	}
	
	// OBJECT COLLISION
	public int checkObject(Entity entity, boolean player) {
		
		int index = -1;
		
		String direction = entity.direction;
		if (entity.lockon) direction = entity.lockonDirection;
		if (entity.knockback) direction = entity.knockbackDirection;
		
		for (int i  = 0; i < gp.obj[1].length; i++) {
			
			if (gp.obj[gp.currentMap][i] != null) {
				
				// get entity's solid area position
				entity.hitbox.x = entity.worldX + entity.hitbox.x;
				entity.hitbox.y = entity.worldY + entity.hitbox.y;
				
				// get object's solid area position
				gp.obj[gp.currentMap][i].hitbox.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].hitbox.x;
				gp.obj[gp.currentMap][i].hitbox.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].hitbox.y;
				
				// find where entity will be after moving in a direction
				// ask if object and entity intersect 
				switch (direction) {
					case "up":					
						entity.hitbox.y -= entity.speed;						
						break;
					case "upleft":
						entity.hitbox.y -= entity.speed;
						entity.hitbox.x -= entity.speed;
						break;
					case "upright":
						entity.hitbox.y -= entity.speed;
						entity.hitbox.x += entity.speed;
						break;
					case "down":					
						entity.hitbox.y += entity.speed;
						break;
					case "downleft":					
						entity.hitbox.y += entity.speed;
						entity.hitbox.x -= entity.speed;
						break;
					case "downright":					
						entity.hitbox.y += entity.speed;
						entity.hitbox.x += entity.speed;
						break;
					case "left":					
						entity.hitbox.x -= entity.speed;
						break;
					case "right":					
						entity.hitbox.x += entity.speed;
						break;
					default: 
						entity.collision = true; 
						return index;
				}
				
				if (entity.hitbox.intersects(gp.obj[gp.currentMap][i].hitbox)) {
					
					if (gp.obj[gp.currentMap][i].collision) {
						entity.collisionOn = true;	
					}
					if (player) {
						index = i;
					}
					else if (gp.obj[gp.currentMap][i].type == gp.obj[gp.currentMap][i].type_collectable) {
						entity.collisionOn = false;
					}
				}
				
				// reset entity solid area
				entity.hitbox.x = entity.hitboxDefaultX;
				entity.hitbox.y = entity.hitboxDefaultY;
				
				// reset object solid area
				gp.obj[gp.currentMap][i].hitbox.x = gp.obj[gp.currentMap][i].hitboxDefaultX;
				gp.obj[gp.currentMap][i].hitbox.y = gp.obj[gp.currentMap][i].hitboxDefaultY;
			}
		}		
		return index;
	}
	
	// OBJECT COLLISION
	public int checkObject_I(Entity entity, boolean player) {
		
		int index = -1;
		
		String direction = entity.direction;
		if (entity.lockon) direction = entity.lockonDirection;		
		if (entity.knockback) direction = entity.knockbackDirection;
		
		for (int i  = 0; i < gp.obj_i[1].length; i++) {
			
			if (gp.obj_i[gp.currentMap][i] != null) {
				
				// get entity's solid area position
				entity.hitbox.x = entity.worldX + entity.hitbox.x;
				entity.hitbox.y = entity.worldY + entity.hitbox.y;
				
				// get obj_iect's solid area position
				gp.obj_i[gp.currentMap][i].hitbox.x = gp.obj_i[gp.currentMap][i].worldX + gp.obj_i[gp.currentMap][i].hitbox.x;
				gp.obj_i[gp.currentMap][i].hitbox.y = gp.obj_i[gp.currentMap][i].worldY + gp.obj_i[gp.currentMap][i].hitbox.y;
				
				// find where entity will be after moving in a direction
				// ask if obj_iect and entity intersect 
				switch (direction) {
					case "up":					
						entity.hitbox.y -= entity.speed;						
						break;
					case "upleft":
						entity.hitbox.y -= entity.speed;
						entity.hitbox.x -= entity.speed;
						break;
					case "upright":
						entity.hitbox.y -= entity.speed;
						entity.hitbox.x += entity.speed;
						break;
					case "down":					
						entity.hitbox.y += entity.speed;
						break;
					case "downleft":					
						entity.hitbox.y += entity.speed;
						entity.hitbox.x -= entity.speed;
						break;
					case "downright":					
						entity.hitbox.y += entity.speed;
						entity.hitbox.x += entity.speed;
						break;
					case "left":					
						entity.hitbox.x -= entity.speed;
						break;
					case "right":					
						entity.hitbox.x += entity.speed;
						break;
					default: 
						entity.collision = true; 
						return index;
				}
								
				if (entity.hitbox.intersects(gp.obj_i[gp.currentMap][i].hitbox)) {						
					if (gp.obj_i[gp.currentMap][i].collision) 
						entity.collisionOn = true;	
					if (player) 
						index = i;			
				}
				
				// reset entity solid area
				entity.hitbox.x = entity.hitboxDefaultX;
				entity.hitbox.y = entity.hitboxDefaultY;
				
				// reset obj_iect solid area
				gp.obj_i[gp.currentMap][i].hitbox.x = gp.obj_i[gp.currentMap][i].hitboxDefaultX;
				gp.obj_i[gp.currentMap][i].hitbox.y = gp.obj_i[gp.currentMap][i].hitboxDefaultY;
			}
		}		
		return index;
	}
	
	// CONTACT PLAYER COLLISION
	public boolean checkPlayer(Entity entity) {
		
		boolean contactPlayer = false;
		
		entity.hitbox.x = entity.worldX + entity.hitbox.x;
		entity.hitbox.y = entity.worldY + entity.hitbox.y;
		
		gp.player.hitbox.x = gp.player.worldX + gp.player.hitbox.x;
		gp.player.hitbox.y = gp.player.worldY + gp.player.hitbox.y;
		
		switch (entity.direction) {
			case "up":
				entity.hitbox.y -= entity.speed;
				break;
			case "down":
				entity.hitbox.y += entity.speed;
				break;
			case "left":
				entity.hitbox.x -= entity.speed;
				break;
			case "right":
				entity.hitbox.x += entity.speed;
				break;
			default:
				return false;
		}
		
		if (entity.hitbox.intersects(gp.player.hitbox)) {						
			entity.collisionOn = true;
			contactPlayer = true;
		}
		
		// reset entity solid area
		entity.hitbox.x = entity.hitboxDefaultX;
		entity.hitbox.y = entity.hitboxDefaultY;
		
		// reset object solid area
		gp.player.hitbox.x = gp.player.hitboxDefaultX;
		gp.player.hitbox.y = gp.player.hitboxDefaultY;
		
		return contactPlayer;
	}
	
	// EXPLOSION PLAYER COLLISION
	public boolean checkExplosion(Entity entity) {
		
		boolean contactPlayer = false;
		
		// get bomb's solid area position
		entity.hitbox.x = entity.worldX - gp.tileSize;
		entity.hitbox.y = entity.worldY - gp.tileSize;
		entity.hitbox.width = gp.tileSize * 3;
		entity.hitbox.height = gp.tileSize * 3;
		
		// get iTile's solid area position
		gp.player.hitbox.x = gp.player.worldX + gp.player.hitbox.x;
		gp.player.hitbox.y = gp.player.worldY + gp.player.hitbox.y;
		
		// IF iTile IS HIT BY PLAYER (1 TILE OVER)
		if (entity.hitbox.intersects(gp.player.hitbox))					
			contactPlayer = true;
		
		// reset bomb solid area
		entity.hitbox.x = entity.hitboxDefaultX;
		entity.hitbox.y = entity.hitboxDefaultY;
		entity.hitbox.width = entity.hitboxDefaultWidth;
		entity.hitbox.height = entity.hitboxDefaultHeight;				
		
		// reset iTile solid area
		gp.player.hitbox.x = gp.player.hitboxDefaultX;
		gp.player.hitbox.y = gp.player.hitboxDefaultY;
				
		return contactPlayer;
	}
	
	// EXPLOSION iTILE COLLISION
	public ArrayList<Integer> checkiTileExplosion(Entity entity) {
		
		ArrayList<Integer> tiles = new ArrayList<Integer>();
		
		for (int i  = 0; i < gp.iTile[1].length; i++) {
			
			if (gp.iTile[gp.currentMap][i] != null) {	
				
				// bomb hitbox 3x3 radius
				entity.hitbox.x = entity.worldX - gp.tileSize;
				entity.hitbox.y = entity.worldY - gp.tileSize;
				entity.hitbox.width = gp.tileSize * 3;
				entity.hitbox.height = gp.tileSize * 3;
				
				// get iTile's hitbox position
				gp.iTile[gp.currentMap][i].hitbox.x = gp.iTile[gp.currentMap][i].worldX + gp.iTile[gp.currentMap][i].hitbox.x;
				gp.iTile[gp.currentMap][i].hitbox.y = gp.iTile[gp.currentMap][i].worldY + gp.iTile[gp.currentMap][i].hitbox.y;
				
				// IF iTile IS HIT BY BOMB
				if (entity.hitbox.intersects(gp.iTile[gp.currentMap][i].hitbox) && 
						gp.iTile[gp.currentMap][i].bombable) 			
					tiles.add(i);				
				
				// reset bomb hitbox
				entity.hitbox.x = entity.hitboxDefaultX;
				entity.hitbox.y = entity.hitboxDefaultY;
				entity.hitbox.width = entity.hitboxDefaultWidth;
				entity.hitbox.height = entity.hitboxDefaultHeight;				
				
				// reset iTile hitbox
				gp.iTile[gp.currentMap][i].hitbox.x = gp.iTile[gp.currentMap][i].hitboxDefaultX;
				gp.iTile[gp.currentMap][i].hitbox.y = gp.iTile[gp.currentMap][i].hitboxDefaultY;
			}
		}		
		return tiles;
	}
	
	// EXPLOSION COLLISION
	public ArrayList<Integer> checkExplosion(Entity entity, Entity[][] target) {
		
		ArrayList<Integer> tiles = new ArrayList<Integer>();
		
		for (int i  = 0; i < target[1].length; i++) {
			
			if (target[gp.currentMap][i] != null) {	
				
				// bomb hitbox 3x3 radius
				entity.hitbox.x = entity.worldX - gp.tileSize;
				entity.hitbox.y = entity.worldY - gp.tileSize;
				entity.hitbox.width = gp.tileSize * 3;
				entity.hitbox.height = gp.tileSize * 3;
				
				// get iTile's hitbox position
				target[gp.currentMap][i].hitbox.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].hitbox.x;
				target[gp.currentMap][i].hitbox.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].hitbox.y;
				
				// IF iTile IS HIT BY BOMB
				if (entity.hitbox.intersects(target[gp.currentMap][i].hitbox)) 			
					tiles.add(i);				
				
				// reset bomb hitbox
				entity.hitbox.x = entity.hitboxDefaultX;
				entity.hitbox.y = entity.hitboxDefaultY;
				entity.hitbox.width = entity.hitboxDefaultWidth;
				entity.hitbox.height = entity.hitboxDefaultHeight;				
				
				// reset iTile hitbox
				target[gp.currentMap][i].hitbox.x = target[gp.currentMap][i].hitboxDefaultX;
				target[gp.currentMap][i].hitbox.y = target[gp.currentMap][i].hitboxDefaultY;
			}
		}		
		return tiles;
	}
	
	// DIGGING COLLISION
	public int checkDigging() {
		
		int index = -1;
		
		for (int i  = 0; i < gp.iTile[1].length; i++) {
			
			if (gp.iTile[gp.currentMap][i] != null) {			
				
				// get player's solid area position
				gp.player.hitbox.x = gp.player.worldX + gp.player.hitbox.x;
				gp.player.hitbox.y = gp.player.worldY + gp.player.hitbox.y;
				
				// get iTile's solid area position
				gp.iTile[gp.currentMap][i].hitbox.x = gp.iTile[gp.currentMap][i].worldX + gp.iTile[gp.currentMap][i].hitbox.x;
				gp.iTile[gp.currentMap][i].hitbox.y = gp.iTile[gp.currentMap][i].worldY + gp.iTile[gp.currentMap][i].hitbox.y;
				
				// FIND iTILE WHERE SHOVEL WILL DIG ON (1 TILE OVER)
				switch (gp.player.direction) {
					case "up":											
					case "upleft":
					case "upright": 
						gp.player.hitbox.y -= gp.tileSize;	
						break;	
					case "down":						
					case "downleft":	
					case "downright": 
						gp.player.hitbox.y += gp.tileSize; 
						break;
					case "left": 
						gp.player.hitbox.x -= gp.tileSize; 
						break;
					case "right": 
						gp.player.hitbox.x += gp.tileSize; 
						break;	
					default: 
						return index;
				}
				
				// IF iTile IS HIT BY PLAYER (1 TILE OVER)
				if (gp.player.hitbox.intersects(gp.iTile[gp.currentMap][i].hitbox))					
					index = i;	
				
				// reset player solid area
				gp.player.hitbox.x = gp.player.hitboxDefaultX;
				gp.player.hitbox.y = gp.player.hitboxDefaultY;
				
				// reset iTile solid area
				gp.iTile[gp.currentMap][i].hitbox.x = gp.iTile[gp.currentMap][i].hitboxDefaultX;
				gp.iTile[gp.currentMap][i].hitbox.y = gp.iTile[gp.currentMap][i].hitboxDefaultY;
			}
		}		
		return index;
	}
}