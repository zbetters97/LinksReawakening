package main;

import java.util.Arrays;
import java.util.List;

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
			eventRect[map][col][row].eventRectDefaultWidth = eventRect[map][col][row].width;
			eventRect[map][col][row].eventRectDefaultHeight = eventRect[map][col][row].height;
			
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
		
		gp.ui.showHint = false;
		
		// CHECK IF PLAYER IS >1 TILE AWAY FROM PREVIOUS EVENT
		int xDistance = Math.abs(gp.player.worldX - previousEventX); // convert - to +
		int yDistance = Math.abs(gp.player.worldY - previousEventY); // convert - to +
		int distance = Math.max(xDistance, yDistance); // find greater of two
		
		if (distance > gp.tileSize) 
			canTouchEvent = true;
		
		// IF EVENT CAN HAPPEN AT X/Y FACING DIRECTION
		if (canTouchEvent) {
			if (hit(0, 23, 12, Arrays.asList("up"), false)) healingPool(gp.dialogueState);
			
			else if (hit(0, 10, 39, false)) teleport(1, 12, 13);			
			else if (hit(1, 12, 9, Arrays.asList("up","upleft","upright"), true)) speak(gp.npc[1][0]);
			else if (hit(1, 12, 13, false)) teleport(0, 10, 39);	
			
			else if (hit(0, 18, 20, Arrays.asList("left","upleft","downleft"), true)) fall(20, 20);
			else if (hit(0, 18, 21, Arrays.asList("left","upleft","downleft"), true)) fall(20, 21);
			else if (hit(0, 18, 22, Arrays.asList("left","upleft","downleft"), true)) fall(20, 22);
			
			else if (hit(0, 18, 20, Arrays.asList("right","upright","downright"), true)) fall(16, 20);
			else if (hit(0, 18, 21, Arrays.asList("right","upright","downright"), true)) fall(16, 21);
			else if (hit(0, 18, 22, Arrays.asList("right","upright","downright"), true)) fall(16, 22);
			
			else if (hit(0, 37, 9, Arrays.asList("up", "upleft", "upright"), true)) fall(37, 10);
			else if (hit(0, 38, 9, Arrays.asList("up", "upleft", "upright"), true)) fall(38, 10);
			
			else if (hit(0, 10, 22, Arrays.asList("up", "upleft", "upright"), true)) fall(10, 23);
			else if (hit(0, 10, 21, Arrays.asList("up", "upleft", "upright"), true)) fall(10, 23);
			
			else if (hit(0, 12, 9, false)) win();	
		}
	}
	
	public boolean hit(int map, int col, int row, List<String> reqDirection, boolean fullTile) {
		
		boolean hit = false;
		
		if (map == gp.currentMap) {

			if (fullTile) {
				eventRect[map][col][row].x = 16;
				eventRect[map][col][row].y = 16;
				eventRect[map][col][row].width = 16;
				eventRect[map][col][row].height = 16;
			}
			
			// PLAYER hitbox
			gp.player.hitbox.x += gp.player.worldX;		
			gp.player.hitbox.y += gp.player.worldY;
			
			// EVENT hitbox
			eventRect[map][col][row].x += col * gp.tileSize;
			eventRect[map][col][row].y += row * gp.tileSize;
			
			// PLAYER INTERACTS WITH EVENT AND EVENT CAN HAPPEN
			if (gp.player.hitbox.intersects(eventRect[map][col][row]) && 
					!eventRect[map][col][row].eventDone) {
				
				for (String dir : reqDirection) {
					if (gp.player.direction.equals(dir)) {
						hit = true;
						
						// RECORD PLAYER X/Y
						previousEventX = gp.player.worldX;
						previousEventY = gp.player.worldY;
						
						break;
					}
				}
			}
			
			// RESET hitbox
			gp.player.hitbox.x = gp.player.hitboxDefaultX;
			gp.player.hitbox.y = gp.player.hitboxDefaultY;
			eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
			eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;	
			eventRect[map][col][row].width = eventRect[map][col][row].eventRectDefaultWidth;
			eventRect[map][col][row].height = eventRect[map][col][row].eventRectDefaultHeight;
		}		
		
		return hit;		
	}
	
	public boolean hit(int map, int col, int row, boolean fullTile) {
		
		boolean hit = false;
		
		if (map == gp.currentMap) {

			if (fullTile) {
				eventRect[map][col][row].x = 16;
				eventRect[map][col][row].y = 16;
				eventRect[map][col][row].width = 16;
				eventRect[map][col][row].height = 16;
			}
			
			// PLAYER hitbox
			gp.player.hitbox.x += gp.player.worldX;		
			gp.player.hitbox.y += gp.player.worldY;
			
			// EVENT hitbox
			eventRect[map][col][row].x += col * gp.tileSize;
			eventRect[map][col][row].y += row * gp.tileSize;
			
			// PLAYER INTERACTS WITH EVENT AND EVENT CAN HAPPEN
			if (gp.player.hitbox.intersects(eventRect[map][col][row]) && 
					!eventRect[map][col][row].eventDone) {
				
				hit = true;
				
				// RECORD PLAYER X/Y
				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;
			}
			
			// RESET hitbox
			gp.player.hitbox.x = gp.player.hitboxDefaultX;
			gp.player.hitbox.y = gp.player.hitboxDefaultY;
			eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
			eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;	
		}		
		
		return hit;		
	}
	
	public void win() {
		gp.ui.currentDialogue = "You win!";
		gp.gameState = gp.dialogueState;
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
		
		tempMap = map;
		tempCol = col;
		tempRow = row;
		
		canTouchEvent = false;
		
		gp.gameState = gp.transitionState;
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
		
		gp.ui.currentDialogue = "Ouch! You got stung by a bee!";
		gp.player.life--;
		canTouchEvent = false;
		gp.gameState = gameState;
	}
	
	public void healingPool(int gameState) {
		
		gp.ui.hint = "[Press SPACE to interact]";
		gp.ui.showHint = true;
		
		if (gp.keyH.spacePressed) {
			gp.ui.showHint = false;
			gp.playSE(1, 4);
			
			gp.player.attackCanceled = true;			
			gp.ui.currentDialogue = "Ah... The water is pure and heals you.";		
			
			gp.player.life = gp.player.maxLife;	
			gp.player.arrows = gp.player.maxArrows;
			gp.player.bombs = gp.player.maxBombs;
			gp.aSetter.setEnemy();
			
			gp.gameState = gameState;
		}
	}
}