package event;
import java.util.Arrays;
import java.util.List;

import application.GamePanel;
import data.Progress;
import entity.Entity;
import entity.enemy.*;
import entity.object.OBJ_Door_Closed;

public class EventHandler {
	
	private GamePanel gp;
	private EventRect eventRect[][][];
	private Entity dekuTree;
	
	public int previousEventX;
	public int previousEventY;
	public boolean canTouchEvent = true;
	public int tempMap, tempCol, tempRow;
	
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
			else if (hit(0, 10, 39, true)) teleport(1, 12, 13, gp.shop, 1, "up"); // SHOP ENTRANCE
			else if (hit(1, 12, 13, true)) teleport(0, 10, 39, gp.outside, 2, "down"); // SHOP EXIT
			else if (hit(0, 12, 12, true)) teleport(2, 40, 93, gp.dungeon, 1, "up"); // DUNGEON ENTRANCE
			else if (hit(2, 40, 93, true)) teleport(0, 12, 12, gp.outside, 2, "down"); // DUNGEON EXIT
			else if (hit(2, 70, 49, true)) teleport(3, 18, 40, gp.dungeon, 3, "right"); // DUNGEON F2
			else if (hit(3, 18, 40, true)) teleport(2, 70, 49, gp.dungeon, 2, "down"); // DUNEGOEN F1
						
			// ENEMY ROOMS
			if (!Progress.enemy_room_1_1) {
				if (hit(2, 33, 80, true)) {
					Progress.enemy_room_1_1 = true;
					spawnEnemies(
							Arrays.asList(new EMY_ChuChu_Red(gp,29,81), new EMY_ChuChu_Green(gp,29,78)),
							new int[]{35,29}, new int[]{80,76}, new String[]{"left","down"}, new Boolean[]{true, true}
							
					);
				}
			}
			if (!Progress.enemy_room_1_2) {
				if (hit(2, 23, 64, true)) {
					Progress.enemy_room_1_2 = true;
					spawnEnemies(
							Arrays.asList(new EMY_ChuChu_Red(gp,16,64), new EMY_ChuChu_Red(gp,18,64)),
							new int[]{25}, new int[]{64}, new String[]{"left"}, new Boolean[]{true}
							
					);
				}
			}
			if (!Progress.enemy_room_1_3) {
				if (hit(2, 59, 65, true)) {
					Progress.enemy_room_1_3 = true;
					spawnEnemies(							
							Arrays.asList(new EMY_Wizzrobe(gp,57,62), new EMY_Buzzblob(gp,62,62)),
							new int[]{59,59}, new int[]{67,60}, new String[]{"up","down"}, new Boolean[]{true, true}
					);
				}
			}

			// BOSS CUTSCENES
			// QUEEN GOMA
			if (!Progress.bossDefeated_1_1) { 
				if (hit(2, 61, 89, true)) {		
					Progress.bossDefeated_1_1 = true;
					miniBoss(
							BOS_Gohma.emyName, 
							new int[]{60}, new int[]{85}, new String[]{"right"}, new Boolean[]{true}
					);
				}
			}
			// SKELETON KING
			if (!Progress.bossDefeated_1_2) { 
				if (hit(3, 25, 27, false)) {
					Progress.bossDefeated_1_2 = true;
					boss(1); 
				}
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
			playFairySE();	
			
			gp.ui.showHint = false;
			gp.keyH.actionPressed = false;
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
	
	private void teleport(int map, int col, int row, int area, int level, String direction) {		
		
		if (level == 1 || level == 3) playStairsUpSE();
		else if (level == 2) playStairsDownSE();
		
		tempMap = map;
		tempCol = col;
		tempRow = row;
		
		canTouchEvent = false;
		gp.removeProjectiles();
		
		gp.ui.transitionDirection = direction;
		gp.nextArea = area;		
		gp.gameState = gp.transitionState;
	}	
	
	private void spawnEnemies(List<Entity> enemyList, int[] dX, int[] dY, String[] dir, Boolean[] temp) {
		
		canTouchEvent = false;	
		Progress.canSave = false;
		
		if (enemyList != null) {
			
			// LOOP THROUGH ALL PASSED ENEMIES
			for (int i = 0; i < enemyList.size(); i++) {
				
				// FIND OPEN SLOT IN GP ENEMY LIST
				for (int c = 0; c < gp.enemy[1].length; c++) {
					
					// CREATE NEW ENEMY
					if (gp.enemy[gp.currentMap][c] == null) {
						gp.enemy[gp.currentMap][c] = enemyList.get(i);
						gp.enemy[gp.currentMap][c].temp = true;
						break;
					}
				}
			}
		}
		
		if (dir != null) {		
			
			// LOOP THROUGH ALL PASSED DOORS
			for (int i = 0; i < dir.length; i++) {				
				spawnDoors(
						new int[]{dX[i]}, new int[]{dY[i]}, 
						new String[]{dir[i]}, new Boolean[]{temp[i]}
				);				
			}	
		}
		
		gp.csManager.scene = gp.csManager.enemy_spawn;
		gp.gameState = gp.cutsceneState;	
	}
	
	private void spawnDoors(int[] dX, int[] dY, String[] dir, Boolean[] temp) {
		
		if (dir != null) {		
			
			// LOOP THROUGH ALL PASSED DOORS
			for (int i = 0; i < dir.length; i++) {
				
				// FIND OPEN SLOT IN GP OBJ LIST
				for (int c = 0; c < gp.obj[1].length; c++) {
					
					// CREATE NEW DOOR
					if (gp.obj[gp.currentMap][c] == null) {	
						
						gp.obj[gp.currentMap][c] = new OBJ_Door_Closed(gp, dX[i], dY[i]);
						gp.obj[gp.currentMap][c].closing = true;
						gp.obj[gp.currentMap][c].direction = dir[i];
						gp.obj[gp.currentMap][c].temp = temp[i];
						
						break;
					}
				}
			}	
		}
		
		canTouchEvent = false;		
		
		gp.csManager.scene = gp.csManager.enemy_spawn;
		gp.gameState = gp.cutsceneState;	
	}
	
	private void miniBoss(String miniBoss, int[] dX, int[] dY, String[] dir, Boolean[] temp) {
		
		gp.stopMusic();
		gp.playMusic(6);
		
		canTouchEvent = false;
		Progress.canSave = false;	
		
		// SEARCH FOR BOSS
		for (int i = 0; i < gp.enemy[1].length; i++) {
			if (gp.enemy[gp.currentMap][i] != null && 
					gp.enemy[gp.currentMap][i].name.equals(miniBoss)) {
				
				gp.enemy[gp.currentMap][i].sleep = false;							
				break;
			}
		}	
		
		if (dir != null) {		
			
			// LOOP THROUGH ALL PASSED DOORS
			for (int i = 0; i < dir.length; i++) {				
				spawnDoors(
						new int[]{dX[i]}, new int[]{dY[i]}, 
						new String[]{dir[i]}, new Boolean[]{temp[i]}
				);				
			}	
		}
		
		gp.csManager.scene = gp.csManager.enemy_spawn;
		gp.gameState = gp.cutsceneState;	
	}
	
	
	private void boss(int num) {	
		
		if (!gp.bossBattleOn) {			
			if (num == 1) gp.csManager.scene = gp.csManager.boss_1;	
			
			Progress.canSave = false;
			gp.gameState = gp.cutsceneState;			
		}
	}
	
	private void playFairySE() {
		gp.playSE(6, 3);
	}
	private void playStairsUpSE() {
		gp.playSE(6, 4);
	}
	private void playStairsDownSE() {
		gp.playSE(6, 5);
	}
}