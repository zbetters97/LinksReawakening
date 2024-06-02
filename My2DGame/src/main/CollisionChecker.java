package main;

import java.awt.Graphics2D;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gp;
	
	/** CONSTRUCTOR **/
	public CollisionChecker(GamePanel gp) {		
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		
		// COLLISION BOX (left side, right side, top, bottom)
		int entityLeftWorldX = entity.worldX + entity.hitBox.x;
		int entityRightWorldX = entity.worldX + entity.hitBox.x + entity.hitBox.width;
		int entityTopWorldY = entity.worldY + entity.hitBox.y;
		int entityBottomWorldY = entity.worldY + entity.hitBox.y + entity.hitBox.height;
		
		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;
				
		// detect the two tiles player is interacting with
		int tileNum1 = 0, tileNum2 = 0;
				
		// PREVENT COLLISION DETECTION OUT OF BOUNDS
		if (entityTopRow <= 0) return;		
		if (entityBottomRow >= gp.maxWorldRow - 1) return;		
		if (entityLeftCol <= 0) return;		
		if (entityRightCol >= gp.maxWorldCol - 1) return;
		
		// find tile player will interact with, factoring in speed
		switch (entity.direction) {
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
		}		

		// if tile 1 or 2 has collision, turn on collision		
		if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
			entity.collisionOn = true;		
	}
	
	// if entity hits object, return index of object
	public int checkObject(Entity entity, boolean player) {
		
		int index = -1;
		
		for (int i  = 0; i < gp.obj[1].length; i++) {
			
			if (gp.obj[gp.currentMap][i] != null) {
				
				// get entity's solid area position
				entity.hitBox.x = entity.worldX + entity.hitBox.x;
				entity.hitBox.y = entity.worldY + entity.hitBox.y;
				
				// get object's solid area position
				gp.obj[gp.currentMap][i].hitBox.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].hitBox.x;
				gp.obj[gp.currentMap][i].hitBox.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].hitBox.y;
				
				// find where entity will be after moving in a direction
				// ask if object and entity intersect 
				switch (entity.direction) {
				case "up":					
					entity.hitBox.y -= entity.speed;						
					break;
				case "upleft":
					entity.hitBox.y -= entity.speed;
					entity.hitBox.x -= entity.speed;
					break;
				case "upright":
					entity.hitBox.y -= entity.speed;
					entity.hitBox.x += entity.speed;
					break;
				case "down":					
					entity.hitBox.y += entity.speed;
					break;
				case "downleft":					
					entity.hitBox.y += entity.speed;
					entity.hitBox.x -= entity.speed;
					break;
				case "downright":					
					entity.hitBox.y += entity.speed;
					entity.hitBox.x += entity.speed;
					break;
				case "left":					
					entity.hitBox.x -= entity.speed;
					break;
				case "right":					
					entity.hitBox.x += entity.speed;
					break;
				}
				
				if (entity.hitBox.intersects(gp.obj[gp.currentMap][i].hitBox)) {						
					if (gp.obj[gp.currentMap][i].collision) 
						entity.collisionOn = true;	
					if (player) 
						index = i;			
				}
				
				// reset entity solid area
				entity.hitBox.x = entity.hitBoxDefaultX;
				entity.hitBox.y = entity.hitBoxDefaultY;
				
				// reset object solid area
				gp.obj[gp.currentMap][i].hitBox.x = gp.obj[gp.currentMap][i].hitBoxDefaultX;
				gp.obj[gp.currentMap][i].hitBox.y = gp.obj[gp.currentMap][i].hitBoxDefaultY;
			}
		}		
		return index;
	}
	
	// ENTITY COLLISION
	public int checkEntity(Entity entity, Entity[][] target) {
		
		int index = -1;
		
		for (int i  = 0; i < target[1].length; i++) {
			
			if (target[gp.currentMap][i] != null) {			
				
				// get entity's solid area position
				entity.hitBox.x = entity.worldX + entity.hitBox.x;
				entity.hitBox.y = entity.worldY + entity.hitBox.y;
				
				// get object's solid area position
				target[gp.currentMap][i].hitBox.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].hitBox.x;
				target[gp.currentMap][i].hitBox.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].hitBox.y;
				
				// find where entity will be after moving in a direction
				// ask if npc and entity intersect 
				switch (entity.direction) {
					case "up":					
						entity.hitBox.y -= entity.speed;
						break;					
					case "upleft":
						entity.hitBox.y -= entity.speed;
						entity.hitBox.x -= entity.speed;
						break;
					case "upright":
						entity.hitBox.y -= entity.speed;
						entity.hitBox.x += entity.speed;
						break;
					case "down":					
						entity.hitBox.y += entity.speed;
						break;
					case "downleft":					
						entity.hitBox.y += entity.speed;
						entity.hitBox.x -= entity.speed;
						break;
					case "downright":					
						entity.hitBox.y += entity.speed;
						entity.hitBox.x += entity.speed;
						break;
					case "left":					
						entity.hitBox.x -= entity.speed;
						break;
					case "right":					
						entity.hitBox.x += entity.speed;
						break;	
				}
				
				if (entity.hitBox.intersects(target[gp.currentMap][i].hitBox)) {	
					
					if (target[gp.currentMap][i] != entity) {		
						index = i;			
						if (!target[gp.currentMap][i].diggable)
							entity.collisionOn = true;
					}
				}
				
				// reset entity solid area
				entity.hitBox.x = entity.hitBoxDefaultX;
				entity.hitBox.y = entity.hitBoxDefaultY;
				
				// reset object solid area
				target[gp.currentMap][i].hitBox.x = target[gp.currentMap][i].hitBoxDefaultX;
				target[gp.currentMap][i].hitBox.y = target[gp.currentMap][i].hitBoxDefaultY;
			}
		}		
		return index;
	}
	
	// DIGGING COLLISION
	public int checkDigging() {
		
		int index = -1;
		
		for (int i  = 0; i < gp.iTile[1].length; i++) {
			
			if (gp.iTile[gp.currentMap][i] != null) {			
				
				// get player's solid area position
				gp.player.hitBox.x = gp.player.worldX + gp.player.hitBox.x;
				gp.player.hitBox.y = gp.player.worldY + gp.player.hitBox.y;
				
				// get iTile's solid area position
				gp.iTile[gp.currentMap][i].hitBox.x = gp.iTile[gp.currentMap][i].worldX + gp.iTile[gp.currentMap][i].hitBox.x;
				gp.iTile[gp.currentMap][i].hitBox.y = gp.iTile[gp.currentMap][i].worldY + gp.iTile[gp.currentMap][i].hitBox.y;
				
				// FIND iTILE WHERE SHOVEL WILL DIG ON (1 TILE OVER)
				switch (gp.player.direction) {
					case "up":											
					case "upleft":
					case "upright": gp.player.hitBox.y -= gp.tileSize;	break;	
					case "down":						
					case "downleft":	
					case "downright": gp.player.hitBox.y += gp.tileSize; break;
					case "left": gp.player.hitBox.x -= gp.tileSize; break;
					case "right": gp.player.hitBox.x += gp.tileSize; break;	
				}
				
				// IF iTile IS HIT BY PLAYER (1 TILE OVER)
				if (gp.player.hitBox.intersects(gp.iTile[gp.currentMap][i].hitBox))					
					index = i;	
				
				// reset player solid area
				gp.player.hitBox.x = gp.player.hitBoxDefaultX;
				gp.player.hitBox.y = gp.player.hitBoxDefaultY;
				
				// reset iTile solid area
				gp.iTile[gp.currentMap][i].hitBox.x = gp.iTile[gp.currentMap][i].hitBoxDefaultX;
				gp.iTile[gp.currentMap][i].hitBox.y = gp.iTile[gp.currentMap][i].hitBoxDefaultY;
			}
		}		
		return index;
	}
	
	public boolean checkPlayer(Entity entity) {
		
		boolean contactPlayer = false;
		
		// get entity's solid area position
		entity.hitBox.x = entity.worldX + entity.hitBox.x;
		entity.hitBox.y = entity.worldY + entity.hitBox.y;
		
		// get object's solid area position
		gp.player.hitBox.x = gp.player.worldX + gp.player.hitBox.x;
		gp.player.hitBox.y = gp.player.worldY + gp.player.hitBox.y;
		
		// find where entity will be after moving in a direction
		// ask if object and entity intersect 
		switch (entity.direction) {
		case "up":
			entity.hitBox.y -= entity.speed;
			break;
		case "down":
			entity.hitBox.y += entity.speed;
			break;
		case "left":
			entity.hitBox.x -= entity.speed;
			break;
		case "right":
			entity.hitBox.x += entity.speed;
			break;
		}
		
		if (entity.hitBox.intersects(gp.player.hitBox)) {						
			entity.collisionOn = true;
			contactPlayer = true;
		}
		
		// reset entity solid area
		entity.hitBox.x = entity.hitBoxDefaultX;
		entity.hitBox.y = entity.hitBoxDefaultY;
		
		// reset object solid area
		gp.player.hitBox.x = gp.player.hitBoxDefaultX;
		gp.player.hitBox.y = gp.player.hitBoxDefaultY;
		
		return contactPlayer;
	}
}