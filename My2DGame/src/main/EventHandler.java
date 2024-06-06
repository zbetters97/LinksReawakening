package main;

import entity.Entity;

public class EventHandler {
	
	GamePanel gp;
	EventRect eventRect[][][];
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	int tempMap, tempCol, tempRow;
	
	public EventHandler(GamePanel gp) {
		
		this.gp = gp;
		
		// EVENT RECTANGLE ON EVERY WORLD MAP TILE
		eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		int map = 0;
		int col = 0;
		int row = 0;
		while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
			// DRAW HIT BOX ON EACH EVENT
			eventRect[map][col][row] = new EventRect();			
			eventRect[map][col][row].x = 23;
			eventRect[map][col][row].y = 23;
			eventRect[map][col][row].width = 2;
			eventRect[map][col][row].height = 2;
			eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
			
			col++;
			if (col == gp.maxWorldCol) {
				col = 0;
				row++;
				
				if (row == gp.maxWorldRow) {
					row = 0;
					map++;
				}
			}
		}
	}
	
	public void checkEvent() {
		
		// CHECK IF PLAYER IS >1 TILE AWAY FROM PREVIOUS EVENT
		int xDistance = Math.abs(gp.player.worldX - previousEventX); // convert - to +
		int yDistance = Math.abs(gp.player.worldY - previousEventY); // convert - to +
		int distance = Math.max(xDistance, yDistance); // find greater of two
		
		if (distance > gp.tileSize) 
			canTouchEvent = true;
		
		// IF EVENT CAN HAPPEN AT X/Y FACING DIRECTION
		if (canTouchEvent) {
			if (hit(0, 23, 12, "up", false)) healingPool(gp.dialogueState);
			else if (hit(0, 18, 20, "any", true)) fall(20, 20);
			else if (hit(0, 18, 21, "any", true)) fall(20, 21);
			else if (hit(0, 18, 22, "any", true)) fall(20, 22);
			else if (hit(0, 10, 22, "any", true)) fall(10, 23);
			else if (hit(0, 10, 21, "any", true)) fall(10, 23);
			else if (hit(0, 10, 39, "any", false)) teleport(1, 12, 13);			
			else if (hit(1, 12, 13, "any", false)) teleport(0, 10, 39);
			else if (hit(1, 12, 9, "up", true)) speak(gp.npc[1][0]);
			else if (hit(0, 12, 9, "any", false)) win();	
		}
	}
	
	public boolean hit(int map, int col, int row, String reqDirection, boolean fullTile) {
		
		boolean hit = false;
		
		if (map == gp.currentMap) {

			if (fullTile) {
				eventRect[map][col][row].x = 16;
				eventRect[map][col][row].y = 16;
				eventRect[map][col][row].width = 16;
				eventRect[map][col][row].height = 16;
			}
			
			// PLAYER HITBOX
			gp.player.hitBox.x += gp.player.worldX;		
			gp.player.hitBox.y += gp.player.worldY;
			
			// EVENT HITBOX
			eventRect[map][col][row].x += col * gp.tileSize;
			eventRect[map][col][row].y += row * gp.tileSize;
			
			// PLAYER INTERACTS WITH EVENT AND EVENT CAN HAPPEN
			if (gp.player.hitBox.intersects(eventRect[map][col][row]) && 
					!eventRect[map][col][row].eventDone) {
				
				// EVENT OCCURS ONLY ON DIRECTION
				if (gp.player.direction.equals(reqDirection) || 
						reqDirection.equals("any")) {
					hit = true;
					
					// RECORD PLAYER X/Y
					previousEventX = gp.player.worldX;
					previousEventY = gp.player.worldY;
				}
			}
			
			// RESET HITBOX
			gp.player.hitBox.x = gp.player.hitBoxDefaultX;
			gp.player.hitBox.y = gp.player.hitBoxDefaultY;
			eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
			eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;	
		}		
		
		return hit;		
	}
	
	public void win() {
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "You win!";
	}
	
	public void speak(Entity npc) {		
		if (gp.keyH.spacePressed) {
			gp.gameState = gp.dialogueState;
			gp.player.attackCanceled = true;
			npc.speak();
		}
	}
	public void teleport(int map, int col, int row) {	
				
		gp.stopMusic();		
		gp.playSE(1,10);		
		
		gp.gameState = gp.transitionState;
		
		tempMap = map;
		tempCol = col;
		tempRow = row;
		
		canTouchEvent = false;
	}
	public void fall(int x, int y) {			
		gp.playSE(2, 2);
		gp.player.falling = true;
		gp.player.invincible = true;
		gp.player.safeWorldX = x * gp.tileSize;
		gp.player.safeWorldY = y * gp.tileSize;
	}
	
	public void damagePit(int gameState) {		
		
		gp.playSE(2, 0);
		gp.gameState = gameState;
		
		gp.ui.currentDialogue = "Ouch! You got stung by a bee!";
		gp.player.life--;
		canTouchEvent = false;
	}
	
	public void healingPool(int gameState) {
		if (gp.keyH.spacePressed) {
			gp.player.attackCanceled = true;
			gp.gameState = gameState;
			gp.ui.currentDialogue = "Ah... The water is pure and heals you.";
			gp.playSE(1, 4);
			gp.player.life = gp.player.maxLife;	
			gp.aSetter.setEnemy();
		}
	}
}