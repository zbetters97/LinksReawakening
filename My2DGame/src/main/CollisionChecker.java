package main;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gp;
	
	/** CONSTRUCTOR **/
	public CollisionChecker(GamePanel gp) {		
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		
		// COLLISION BOX (left side, right side, top, bottom)
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;
		
		// detect the two tiles player is interacting with
		int tileNum1 = 0, tileNum2 = 0;
		
		// find tile player will interact with, factoring in speed
		switch (entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;	
			
			// tiles at top-left and top-right
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
			
			// tiles at bottom-left and bottom-right
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			
			// tiles at left-top and left-bottom
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
			
			// tiles at right-top and right-bottom
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			
			break;
		case "upleft":
			
			// tiles at top-left and left-top
			entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];	
			
			break;
		case "upright":
			
			// tiles at top-right and right-top
			entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			
			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];	
			
			break;
		case "downleft":
			
			// tiles at bottom-left and left-bottom
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			
			break;
		case "downright":
			
			// tiles at bottom-right and right-bottom
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			
			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			
			break;
		}		

		// if tile 1 or 2 has collision, turn on collision		
		if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
			entity.collisionOn = true;		
	}
	
	// if entity hits object, return index of object
	public int checkObject(Entity entity, boolean player) {
		
		int index = -1;
		
		for (int i  = 0; i < gp.obj.length; i++) {
			
			if (gp.obj[i] != null) {
				
				// get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				// get object's solid area position
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
				
				// find where entity will be after moving in a direction
				// ask if object and entity intersect 
				switch (entity.direction) {
				case "up":					
					entity.solidArea.y -= entity.speed;						
					break;
				case "down":					
					entity.solidArea.y += entity.speed;
					break;
				case "left":					
					entity.solidArea.x -= entity.speed;
					break;
				case "right":					
					entity.solidArea.x += entity.speed;
					break;
				case "upleft":
					entity.solidArea.y -= entity.speed;
					entity.solidArea.x -= entity.speed;
					break;
				case "upright":
					entity.solidArea.y -= entity.speed;
					entity.solidArea.x += entity.speed;
					break;
				case "downleft":					
					entity.solidArea.y += entity.speed;
					entity.solidArea.x -= entity.speed;
					break;
				case "downright":					
					entity.solidArea.y += entity.speed;
					entity.solidArea.x += entity.speed;
					break;
				}
				
				if (entity.solidArea.intersects(gp.obj[i].solidArea)) {						
					if (gp.obj[i].collision) 
						entity.collisionOn = true;					
					if (player) 
						index = i;			
				}
				
				// reset entity solid area
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				
				// reset object solid area
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
			}
		}		
		return index;
	}
	
	// ENTITY COLLISION
	public int checkEntity(Entity entity, Entity[] target) {
		
		int index = -1;
		
		for (int i  = 0; i < target.length; i++) {
			
			if (target[i] != null) {
				
				// get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				// get object's solid area position
				target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
				target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;
				
				// find where entity will be after moving in a direction
				// ask if npc and entity intersect 
				switch (entity.direction) {
				case "up":					
					entity.solidArea.y -= entity.speed;
					break;
				case "down":					
					entity.solidArea.y += entity.speed;
					break;
				case "left":					
					entity.solidArea.x -= entity.speed;
					break;
				case "right":					
					entity.solidArea.x += entity.speed;
					break;					
				case "upleft":
					entity.solidArea.y -= entity.speed;
					entity.solidArea.x -= entity.speed;
					break;
				case "upright":
					entity.solidArea.y -= entity.speed;
					entity.solidArea.x += entity.speed;
					break;
				case "downleft":					
					entity.solidArea.y += entity.speed;
					entity.solidArea.x -= entity.speed;
					break;
				case "downright":					
					entity.solidArea.y += entity.speed;
					entity.solidArea.x += entity.speed;
					break;
				}
				
				if (entity.solidArea.intersects(target[i].solidArea)) {	
					if (target[i] != entity) {
						entity.collisionOn = true;						
						index = i;			
					}
				}
				
				// reset entity solid area
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				
				// reset object solid area
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;
			}
		}		
		return index;
	}
	
	public boolean checkPlayer(Entity entity) {
		
		boolean contactPlayer = false;
		
		// get entity's solid area position
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;
		
		// get object's solid area position
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		
		// find where entity will be after moving in a direction
		// ask if object and entity intersect 
		switch (entity.direction) {
		case "up":
			entity.solidArea.y -= entity.speed;
			break;
		case "down":
			entity.solidArea.y += entity.speed;
			break;
		case "left":
			entity.solidArea.x -= entity.speed;
			break;
		case "right":
			entity.solidArea.x += entity.speed;
			break;
		}
		
		if (entity.solidArea.intersects(gp.player.solidArea)) {						
			entity.collisionOn = true;
			contactPlayer = true;
		}
		
		// reset entity solid area
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		
		// reset object solid area
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		
		return contactPlayer;
	}
}