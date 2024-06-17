package main;

import java.util.ArrayList;

import entity.Entity;
import tile_interactive.IT_Plate_Metal;

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
				
		// detect the two tiles player is interacting with
		int tileNum1 = 0, tileNum2 = 0;
		
		// KNOCKBACK DIRECTION
		String direction = entity.direction;
		if (entity.knockback) {
			direction = entity.knockbackDirection;
		}
		if (entity.lockon) {
			direction = entity.lockonDirection;
		}
				
		// PREVENT COLLISION DETECTION OUT OF BOUNDS
		if (entityTopRow <= 0) return;		
		if (entityBottomRow >= gp.maxWorldRow - 1) return;		
		if (entityLeftCol <= 0) return;		
		if (entityRightCol >= gp.maxWorldCol - 1) return;
		
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
		}		

		// if tile 1 or 2 has collision, turn on collision		
		if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) 
			entity.collisionOn = true;				
	}
	
	// OBJECT COLLISION
	public int checkObject(Entity entity, boolean player) {
		
		int index = -1;
		
		// KNOCKBACK DIRECTION
		String direction = entity.direction;
		if (entity.knockback) {
			direction = entity.knockbackDirection;
		}
		// LOCKON DIRECTION
		if (entity.lockon) {
			direction = entity.lockonDirection;
		}
		
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
				}
				
				if (entity.hitbox.intersects(gp.obj[gp.currentMap][i].hitbox)) {						
					if (gp.obj[gp.currentMap][i].collision) 
						entity.collisionOn = true;	
					if (player) 
						index = i;			
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
	
	// ENTITY COLLISION
	public int checkEntity(Entity entity, Entity[][] target) {
		
		int index = -1;
		
		// KNOCKBACK DIRECTION
		String direction = entity.direction;
		if (entity.knockback) {
			direction = entity.knockbackDirection;
		}
		
		// LOCKON DIRECTION
		if (entity.lockon) {
			direction = entity.lockonDirection;
		}
				
		for (int i  = 0; i < target[1].length; i++) {
			
			if (target[gp.currentMap][i] != null) {			
				
				// get entity's solid area position
				entity.hitbox.x = entity.worldX + entity.hitbox.x;
				entity.hitbox.y = entity.worldY + entity.hitbox.y;
				
				// get object's solid area position
				target[gp.currentMap][i].hitbox.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].hitbox.x;
				target[gp.currentMap][i].hitbox.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].hitbox.y;
				
				// find where entity will be after moving in a direction
				// ask if npc and entity intersect 
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
				}
				
				if (entity.hitbox.intersects(target[gp.currentMap][i].hitbox)) {	
					
					if (target[gp.currentMap][i] != entity) {		
						index = i;	
												
						// ONLY PLAYER CAN PUSH BOMBS
						if (!target[gp.currentMap][i].diggable && !target[gp.currentMap][i].canExplode &&
								!target[gp.currentMap][i].name.equals(IT_Plate_Metal.itName))
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
				
				if (gp.iTile[gp.currentMap][i].bombable)
				
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
					case "upright": gp.player.hitbox.y -= gp.tileSize;	break;	
					case "down":						
					case "downleft":	
					case "downright": gp.player.hitbox.y += gp.tileSize; break;
					case "left": gp.player.hitbox.x -= gp.tileSize; break;
					case "right": gp.player.hitbox.x += gp.tileSize; break;	
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
	
	public boolean checkPlayer(Entity entity) {
		
		boolean contactPlayer = false;
		
		// get entity's solid area position
		entity.hitbox.x = entity.worldX + entity.hitbox.x;
		entity.hitbox.y = entity.worldY + entity.hitbox.y;
		
		// get object's solid area position
		gp.player.hitbox.x = gp.player.worldX + gp.player.hitbox.x;
		gp.player.hitbox.y = gp.player.worldY + gp.player.hitbox.y;
		
		// find where entity will be after moving in a direction
		// ask if object and entity intersect 
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
}