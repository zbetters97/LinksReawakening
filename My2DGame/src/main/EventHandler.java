package main;
import java.util.Arrays;
import java.util.List;

import data.Progress;
import entity.Entity;
import entity.enemy.*;
import entity.object.OBJ_Door_Closed;

public class EventHandler {
	
	private GamePanel gp;
	private EventRect eventRect[][][];
	private Entity dekuTree;
	
	protected int previousEventX, previousEventY;
	private boolean canTouchEvent = true;
	protected int tempMap, tempCol, tempRow;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		
		// MYSTERIOUS VOICE DIALOGUE
		dekuTree = new Entity(gp);
		
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
		
		setDialogue();
	}
	
	private void setDialogue() {
		dekuTree.dialogues[0][0] = "Ah... The water is pure and heals you.";
		dekuTree.dialogues[1][0] = "YOU ARE WINNER";
	}
	
	public void checkEvent() {
		
		gp.ui.showHint = false;
		
		// CHECK IF PLAYER IS >1 TILE AWAY FROM PREVIOUS EVENT
		int xDistance = Math.abs(gp.player.worldX - previousEventX); 
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance); // FIND GREATER OF THE TWO
		
		if (distance > gp.tileSize) 
			canTouchEvent = true;
		
		// IF EVENT CAN HAPPEN AT X/Y FACING DIRECTION
		if (canTouchEvent) {

			// FAIRY POOL
			if (hit(0, 23, 11, Arrays.asList("up"), false)) healingPool();
			if (hit(3, 29, 40, Arrays.asList("right"), false)) healingPool();
			
			// SHOP KEEPER
			else if (hit(1, 10, 9, Arrays.asList("up","upleft","upright"), true)) speak(gp.npc[1][0]);	
			else if (hit(1, 11, 9, Arrays.asList("up","upleft","upright"), true)) speak(gp.npc[1][0]);	
			else if (hit(1, 12, 9, Arrays.asList("up","upleft","upright"), true)) speak(gp.npc[1][0]);	
			else if (hit(1, 13, 9, Arrays.asList("up","upleft","upright"), true)) speak(gp.npc[1][0]);	
			else if (hit(1, 14, 9, Arrays.asList("up","upleft","upright"), true)) speak(gp.npc[1][0]);	
			
			// TELEPORT SPOTS
			else if (hit(0, 10, 39, true)) teleport(1, 12, 13, gp.inside, 1); // SHOP ENTRANCE
			else if (hit(1, 12, 13, true)) teleport(0, 10, 39, gp.outside, 2); // SHOP EXIT
			else if (hit(0, 12, 9, true)) teleport(2, 10, 37, gp.dungeon, 2); // DUNGEON ENTRANCE
			else if (hit(2, 10, 37, true)) teleport(0, 12, 9, gp.outside, 1); // DUNGEON EXIT
			else if (hit(2, 9, 8, true)) teleport(3, 18, 40, gp.dungeon, 3); // DUNGEON F2
			else if (hit(3, 18, 40, true)) teleport(2, 9, 8, gp.dungeon, 2); // DUNEGOEN F1
						
			// ENEMY SPAWN
			if (!Progress.enemy_room_1_1) {
				if (hit(2, 29, 39, true)) {
					Progress.enemy_room_1_1 = true;
					spawnEnemies(
							new int[]{27,38}, new int[]{39,39}, new String[]{"right","left"},
							Arrays.asList(new EMY_Goblin_Combat(gp), new EMY_Bat(gp)), 
							new int[]{34,35}, new int[]{40,39}
					);
				}
			}

			// BOSS CUTSCENES
			if (!Progress.bossDefeated_1) { // SKELETON
				if (hit(3, 25, 27, false)) boss(1); 
			}
		}
	}
	
	private boolean hit(int map, int col, int row, List<String> reqDirection, boolean fullTile) {
		
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
	private boolean hit(int map, int col, int row, boolean fullTile) {
		
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
	
	private void healingPool() {
		
		gp.ui.hint = "[Press SPACE to interact]";
		gp.ui.showHint = true;
		
		if (gp.keyH.actionPressed) {
			gp.playSE(1, 4);
			gp.ui.showHint = false;
			
			gp.player.attackCanceled = true;			
			
			gp.player.life = gp.player.maxLife;	
			gp.player.arrows = gp.player.maxArrows;
			gp.player.bombs = gp.player.maxBombs;
			
			dekuTree.startDialogue(dekuTree, 0);
		}
	}
	
	private void speak(Entity npc) {		
		if (gp.keyH.actionPressed) {
			gp.gameState = gp.dialogueState;
			gp.player.attackCanceled = true;
			npc.speak();
		}
	}
	
	private void teleport(int map, int col, int row, int area, int level) {		
		
		if (level == 1 || level == 3) playStairsUpSE();
		else if (level == 2) playStairsDownSE();
		
		tempMap = map;
		tempCol = col;
		tempRow = row;
		
		canTouchEvent = false;
		
		gp.nextArea = area;		
		gp.gameState = gp.transitionState;
	}	
	
	private void spawnEnemies(int[] dX, int[] dY, String[] dir, List<Entity> enemyList, int[] worldX, int[] worldY) {
		
		// LOOP THROUGH ALL PASSED DOORS
		for (int i = 0; i < dir.length; i++) {
			
			// FIND OPEN SLOT IN GP OBJ LIST
			for (int c = 0; c < gp.obj[1].length; c++) {
				
				// CREATE NEW DOOR
				if (gp.obj[gp.currentMap][c] == null) {					
					gp.obj[gp.currentMap][c] = new OBJ_Door_Closed(gp);
					gp.obj[gp.currentMap][c].worldX = dX[i] * gp.tileSize;
					gp.obj[gp.currentMap][c].worldY = dY[i] * gp.tileSize;
					gp.obj[gp.currentMap][c].direction = dir[i];
					gp.obj[gp.currentMap][c].temp = true;
					
					break;
				}
			}
		}	
		
		// LOOP THROUGH ALL PASSED ENEMIES
		for (int i = 0; i < enemyList.size(); i++) {
			
			// FIND OPEN SLOT IN GP ENEMY LIST
			for (int c = 0; c < gp.enemy_r[1].length; c++) {
				
				// CREATE NEW ENEMY
				if (gp.enemy_r[gp.currentMap][c] == null) {
					gp.enemy_r[gp.currentMap][c] = enemyList.get(i);
					gp.enemy_r[gp.currentMap][c].worldX = worldX[i] * gp.tileSize;
					gp.enemy_r[gp.currentMap][c].worldY = worldY[i] * gp.tileSize;
					
					break;
				}
			}
		}
		
		canTouchEvent = false;		
		
		gp.csManager.scene = gp.csManager.enemy_spawn;
		gp.gameState = gp.cutsceneState;	
	}
	
	private void boss(int num) {	
		
		if (!gp.bossBattleOn) {			
			if (num == 1) gp.csManager.scene = gp.csManager.boss_1;	
			
			gp.gameState = gp.cutsceneState;			
		}
	}
	
	private void playStairsUpSE() {
		gp.playSE(1, 10);
	}
	private void playStairsDownSE() {
		gp.playSE(1, 17);
	}
}