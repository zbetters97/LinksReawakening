package application;

import java.util.ArrayList;

import entity.Entity;
import entity.Entity.Action;
import entity.enemy.EMY_Octorok;
import entity.enemy.EMY_Tektite;
import entity.npc.NPC_Cucco;

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
		
		// SHIFT ENTITY COLLISION BOX DOWN TO AVOID WALLS
		if (entity.thrown) {			
			switch (entity.direction) {
				case "right":
				case "left":
					entityTopWorldY = entity.tWorldY + entity.hitbox.y;					
					entityBottomWorldY = entity.tWorldY + entity.hitbox.y + entity.hitbox.height;
					break;
				}
		}
		
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
		
		// PIT
		if (gp.tileM.tile[tileNum1].pit || gp.tileM.tile[tileNum2].pit) {
			
			// NPC AND EMNEMIES
			if (entity.type == entity.type_npc || entity.type == entity.type_enemy) {	

				// ENEMY CAN FALL IN PIT IF HIT OR CAPTURED
				if (entity.onGround && !entity.knockback && !entity.captured && !entity.thrown) {
					entity.collisionOn = true;	
				}
			}
			// BOSSES
			else if (entity.type == entity.type_boss) {
				if (entity.onGround) {
					entity.collisionOn = true;	
				}
			}
			// PROJECTILES
			else if (entity.type == entity.type_projectile) {
				entity.collisionOn = false;
			}
			// EVERYTHING ELSE
			else if (entity != gp.player) {
				entity.collisionOn = true;
			}
		}
		// WATER
		else if (gp.tileM.tile[tileNum1].water || gp.tileM.tile[tileNum2].water) {
			
			// NPC AND EMNEMIES
			if (entity.type == entity.type_npc || entity.type == entity.type_enemy) {	
			
				// ENEMY CAN FALL IN WATER IF HIT
				if (!entity.canSwim && !entity.knockback && !entity.thrown) {
					entity.collisionOn = true;
				}
			}
			// BOSSES
			else if (entity.type == entity.type_boss) {
				if (!entity.canSwim) {
					entity.collisionOn = true;
				} 
			}		
			// PROJECTILES
			else if (entity.type == entity.type_projectile) {
				entity.collisionOn = false;
			}
			// EVERYTHING ELSE
			else if (entity != gp.player) {
				entity.collisionOn = true;
			}
		}
		// SPIKES
		else if (tileNum1 == gp.tileM.spikeTile || tileNum2 == gp.tileM.spikeTile) {	
			
			// PLAYER
			if (entity == gp.player) {		
												
				if (!gp.player.invincible) {
					
					gp.player.playHurt();					
					gp.player.life -= 2;
					gp.player.transparent = true;
					gp.player.invincible = true;	
					
					String kbDirection = "down";
					
					switch (entity.direction) {
						case "up": kbDirection = "down"; break;	
						case "down": kbDirection = "up"; break;	
						case "left": kbDirection = "right"; break;	
						case "right": kbDirection = "left"; break;	
					}
							
					gp.player.setKnockback(gp.player, kbDirection, 1);
				}
			}	
			// NPC AND EMNEMIES
			else if (entity.type == entity.type_npc || entity.type == entity.type_enemy) {			
				entity.collisionOn = true;
			}
		}
		// OCEAN
		else if (tileNum1 == gp.tileM.oceanTile1 || tileNum2 == gp.tileM.oceanTile1
				|| tileNum1 == gp.tileM.oceanTile2 || tileNum2 == gp.tileM.oceanTile2) {
			
			// PROJECTILES
			if (entity.type == entity.type_projectile) {
				entity.collisionOn = false;
			}
			// OTHER
			else {
				entity.collisionOn = true;
			}
		}
		// NORMAL COLLISION
		else if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {			
			entity.collisionOn = true;			
		}
		else {
			// PLAYER
			if (entity == gp.player) {		
				gp.player.diving = false;
				gp.player.diveCounter = 0;				
			}
		}
		
		if (entity.type == entity.type_enemy && !entity.captured &&
				(entity.name.equals(EMY_Octorok.emyName) || 
				entity.name.equals(EMY_Tektite.emyName))) {
			
			if (tileNum1 != gp.tileM.waterTile || tileNum2 != gp.tileM.waterTile) {
				entity.collisionOn = true;
			}		
		}
	}
			
	// DAMAGE PIT COLLISION
	public void checkHazard(Entity entity, boolean player) {
		
		// COLLISION BOX
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

				if (player && gp.player.onGround) checkSafeworld(tileNum1, tileNum2);
				
				break;
			case "upleft":
				
				entityTopRow = entityWorldY / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
				
				entityLeftCol = entityWorldX / gp.tileSize;
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];	
				
				if (player && gp.player.onGround) checkSafeworld(tileNum1, tileNum2);
				
				break;
			case "upright":
				
				entityTopRow = entityWorldY / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
				
				entityRightCol = entityWorldX / gp.tileSize;
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];	
				
				if (player && gp.player.onGround) checkSafeworld(tileNum1, tileNum2);
				
				break;
			case "down":
				
				entityBottomRow = entityWorldY / gp.tileSize;
				
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];				

				if (player && gp.player.onGround) checkSafeworld(tileNum1, tileNum2);
				
				break;
			case "downleft":
				
				entityBottomRow = entityWorldY / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
				
				entityLeftCol = entityWorldX / gp.tileSize;
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
				
				if (player && gp.player.onGround) checkSafeworld(tileNum1, tileNum2);
				
				break;
			case "downright":
				
				entityBottomRow = entityWorldY / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
				
				entityRightCol = entityWorldX / gp.tileSize;
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
				
				if (player && gp.player.onGround) checkSafeworld(tileNum1, tileNum2);
				
				break;	
			case "left":
				
				entityLeftCol = entityWorldX / gp.tileSize;
				
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
				
				if (player && gp.player.onGround) checkSafeworld(tileNum1, tileNum2);
				
				break;				
			case "right":
				
				entityRightCol = entityWorldX / gp.tileSize;
				
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];

				if (player && gp.player.onGround) checkSafeworld(tileNum1, tileNum2);
				
				break;
			default: 
				return;
		}		
		
		// PIT
		if (gp.tileM.tile[tileNum1].pit || gp.tileM.tile[tileNum2].pit) {
			
			// ENTITY NOT JUMPING OR SOARING
			if (gp.gameState == gp.playState &&
					entity.action != Action.JUMPING && 
					entity.action != Action.SOARING	) {
				
				// PLAYER
				if (player) {
					gp.player.playFallSE();		
					gp.player.resetValues();
					gp.player.invincible = true;	
					gp.player.canMove = false;
					gp.player.action = Action.FALLING;
					gp.gameState = gp.waitState;
				}
				// THROWN ENTITY
				else if (entity.thrown) {
					entity.resetValues();	
					
					gp.player.action = Action.IDLE;
					gp.player.grabbedObject = null;			
					gp.player.throwNum = 1;
					gp.player.throwCounter = 0;	
				}
				// ON GROUND ENTITY
				else if (entity.onGround) {															
					if (entity.captured) gp.player.capturedTarget = null;
					entity.alive = false;	
				}			
			}
		}
		// WATER
		else if (gp.tileM.tile[tileNum1].water || gp.tileM.tile[tileNum2].water) {			
			
			// PLAYER
			if (player) {
				if (gp.gameState == gp.playState) {
					gp.player.action = Action.SWIMMING;
					
					// PLAYER CAN SWIM
					if (gp.player.canSwim) {
						gp.player.speed = 2;
						
						if (gp.player.grabbedObject != null) {
							gp.player.grabbedObject.resetValues();																
							gp.player.grabbedObject = null;
						}
					}		
					// PLAYER CANNOT SWIM
					else {					
						playDrownSE();
						gp.player.playHurt();	
						gp.player.resetValues();
						gp.player.invincible = true;
						gp.player.canMove = false;
						gp.player.action = Action.DROWNING;
						gp.gameState = gp.waitState;
					}
				}
			}
			// NON-PLAYER
			else {
				// ENTITY CANNOT SWIM
				if (!entity.canSwim) {
					playDrownSE();
					entity.generateWaterParticle(entity);
					entity.alive = false;
				}
				// RESET THROWN ENTITY VALUES
				if (entity.thrown) {						
					entity.resetValues();	
					
					gp.player.action = Action.IDLE;
					gp.player.grabbedObject = null;			
					gp.player.throwNum = 1;
					gp.player.throwCounter = 0;					
				}				
			}
		}		
		// OTHER
		else {
			// NON-WATER TILE
			if (entity.action == Action.SWIMMING) {
				entity.action = Action.IDLE;
			}
		}
	}	
	private void checkSafeworld(int tile1, int tile2) {
		if (!gp.tileM.tile[tile1].pit && !gp.tileM.tile[tile2].pit &&
				!gp.tileM.tile[tile1].water && !gp.tileM.tile[tile2].water) {					
			gp.player.safeWorldX = gp.player.worldX;
			gp.player.safeWorldY = gp.player.worldY;						
		}		
	}
	
	// BOUNCE COLLISION
	public void detectBounce(Entity entity) {
		
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
		
		// detect the two tiles entity is interacting with
		int tileNum1 = 0, tileNum2 = 0;
		
		String direction = entity.direction;
						
		switch (direction) {
			case "upleft":
				
				entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
				
				entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];	
				
				if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum1].water) entity.direction = "downleft";
				else if (gp.tileM.tile[tileNum2].collision || gp.tileM.tile[tileNum2].water) entity.direction = "upright";
				
				break;
			case "upright":
				
				entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
				
				entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];	
				
				if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum1].water) entity.direction = "downright";
				else if (gp.tileM.tile[tileNum2].collision || gp.tileM.tile[tileNum2].water) entity.direction = "upleft";
				
				break;
			case "downleft":
				
				entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
				
				entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
				
				if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum1].water) entity.direction = "upleft";
				else if (gp.tileM.tile[tileNum2].collision || gp.tileM.tile[tileNum2].water) entity.direction = "downright";
				
				break;
			case "downright":
				
				entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
				tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
				
				entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
				tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
				
				if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum1].water) entity.direction = "upright";
				else if (gp.tileM.tile[tileNum2].collision || gp.tileM.tile[tileNum2].water) entity.direction = "downleft";
				
				break;
		}		
	}	
	public void detectBounce(Entity entity, Entity[][] target) {
		
		String direction = entity.direction;
				
		for (int i  = 0; i < target[1].length; i++) {
			
			if (target[gp.currentMap][i] != null) {			
				
				entity.hitbox.x = entity.worldX + entity.hitbox.x;
				entity.hitbox.y = entity.worldY + entity.hitbox.y;
				
				target[gp.currentMap][i].hitbox.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].hitbox.x;
				target[gp.currentMap][i].hitbox.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].hitbox.y;
			
				switch (direction) {			
					case "upleft":
						entity.hitbox.y -= entity.speed;
						getBounce(entity, target[gp.currentMap][i], "up");
						
						entity.hitbox.x -= entity.speed;
						getBounce(entity, target[gp.currentMap][i], "left");						
						
						break;
					case "upright":
						entity.hitbox.y -= entity.speed;
						getBounce(entity, target[gp.currentMap][i], "up");
						
						entity.hitbox.x += entity.speed;					
						getBounce(entity, target[gp.currentMap][i], "right");
						
						break;
					case "downleft":					
						entity.hitbox.y += entity.speed;
						getBounce(entity, target[gp.currentMap][i], "down");
						
						entity.hitbox.x -= entity.speed;	
						getBounce(entity, target[gp.currentMap][i], "left");
						
						break;
					case "downright":					
						entity.hitbox.y += entity.speed;
						getBounce(entity, target[gp.currentMap][i], "down");
						
						entity.hitbox.x += entity.speed;
						getBounce(entity, target[gp.currentMap][i], "right");
						
						break;
				}
				
				// reset entity solid area
				entity.hitbox.x = entity.hitboxDefaultX;
				entity.hitbox.y = entity.hitboxDefaultY;
				
				// reset object solid area
				target[gp.currentMap][i].hitbox.x = target[gp.currentMap][i].hitboxDefaultX;
				target[gp.currentMap][i].hitbox.y = target[gp.currentMap][i].hitboxDefaultY;
			}
		}		
	}
	private void getBounce(Entity entity, Entity target, String direction) {
		
		if (entity.hitbox.intersects(target.hitbox)) {	
			
			if (target.collision) {
				
				switch(direction) {
					case "up": 
						switch(entity.direction) {
							case "upleft": entity.direction = "downleft";
							case "upright": entity.direction = "downright";
						}
						break;
					case "down":
						switch(entity.direction) {
							case "downleft": entity.direction = "upleft";
							case "downright": entity.direction = "upright";
						}
						break;
					case "left": 
						switch(entity.direction) {
							case "upleft": entity.direction = "upright";
							case "downleft": entity.direction = "downright";
						}
						break;
					case "right":
						switch(entity.direction) {
							case "upright": entity.direction = "upleft";
							case "downright": entity.direction = "downleft";
						}
						break;
				}
			}	
		}
	}
	
	// CONTACT PLAYER COLLISION
	public boolean checkPlayer(Entity entity) {
				
		boolean contactPlayer = false;
		
		if (gp.player.diving) return false;
		
		String direction = entity.direction;
				
		entity.hitbox.x = entity.worldX + entity.hitbox.x;
		entity.hitbox.y = entity.worldY + entity.hitbox.y;
				
		gp.player.hitbox.x = gp.player.worldX + gp.player.hitbox.x;
		gp.player.hitbox.y = gp.player.worldY + gp.player.hitbox.y;
			
		switch (direction) {
			case "up": entity.hitbox.y -= entity.speed; break;		
			case "down": entity.hitbox.y += entity.speed; break;
			case "left": entity.hitbox.x -= entity.speed; break;
			case "right": entity.hitbox.x += entity.speed; break;	
			default: entity.collision = true; return false;
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
				
				// SHIFT ENTITY COLLISION BOX DOWN TO AVOID WALLS
				if (entity.thrown) {			
					switch (entity.direction) {
						case "right":
						case "left":
							entity.hitbox.y = entity.tWorldY + entity.hitbox.y;
							break;		
						default:
							entity.hitbox.y = entity.worldY + entity.hitbox.y;
							break;
					}
				}
				else {
					entity.hitbox.y = entity.worldY + entity.hitbox.y;
				}
				
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
						
						// THROWN OBJECTS ONLY STOP AT WALLS
						if (entity.thrown) {
							
							if (target[gp.currentMap][i].name.contains("Wall")) {
								
								index = i;	
								
								if (target[gp.currentMap][i].collision) {
									entity.collisionOn = true;								
								}	
							}								
						}
						else {
							index = i;	
							
							if (target[gp.currentMap][i].collision) {
								entity.collisionOn = true;								
							}		
						}						
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
					if (player && gp.obj[gp.currentMap][i].canPickup) {
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
	
	// INTERACTIVE OBJECT COLLISION
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
	
	// EXPLOSION COLLISION
	public boolean checkPlayerExplosion(Entity entity) {
		
		boolean contactPlayer = false;
		
		// get bomb's hitbox position
		entity.hitbox.x = entity.worldX - gp.tileSize;
		entity.hitbox.y = entity.worldY - gp.tileSize;
		entity.hitbox.width = gp.tileSize * 3;
		entity.hitbox.height = gp.tileSize * 3;
		
		// get player's hitbox position
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
	public ArrayList<Entity> checkExplosion(Entity entity, Entity[][] target) {
		
		ArrayList<Entity> impacted = new ArrayList<Entity>();
		
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
				
				// IF ENTITY IS HIT BY BOMB
				if (entity.hitbox.intersects(target[gp.currentMap][i].hitbox)) {	
					if (target[gp.currentMap][i].type == entity.type_npc) { 
						if (target[gp.currentMap][i].name.equals(NPC_Cucco.npcName)) 
							impacted.add(target[gp.currentMap][i]);							
					}
					else {
						impacted.add(target[gp.currentMap][i]);
					}									
				}
				
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
		return impacted;
	}
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
						gp.iTile[gp.currentMap][i].correctItem(entity)) 			
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
	
	public void playDrownSE() {
		gp.playSE(2, 14);
	}
}