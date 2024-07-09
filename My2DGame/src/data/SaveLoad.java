package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import application.GamePanel;
import entity.Entity;

public class SaveLoad {

	GamePanel gp;
	
	public SaveLoad(GamePanel gp) {
		this.gp = gp;
	}
	
	public void save() {		
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));

			// SAVE DATA TO DS
			DataStorage ds = new DataStorage();
			
			// DAY DATA
			ds.dayState = gp.eManager.lighting.dayState;
			ds.dayCounter = gp.eManager.lighting.dayCounter;
			ds.bloodMoonCounter = gp.eManager.lighting.bloodMoonCounter;
			
			// PROGRESS DATA
			ds.enemy_room_1_1 = Progress.enemy_room_1_1;
			ds.enemy_room_1_2 = Progress.enemy_room_1_2;
			ds.enemy_room_1_3 = Progress.enemy_room_1_3;
			ds.puzzle_1_1 = Progress.puzzle_1_1;
			ds.bossDefeated_1 = Progress.bossDefeated_1;
			
			// PLAYER DATA
			ds.cMap = gp.currentMap;
			ds.pWorldX = gp.player.worldX;
			ds.pWorldY = gp.player.worldY;
			ds.cArea = gp.currentArea;
			
			ds.name = gp.player.name;
			ds.maxLife = gp.player.maxLife;
			ds.life = gp.player.life;
			ds.attack = gp.player.attack;
						
			ds.rupees = gp.player.rupees;
			ds.maxArrows = gp.player.maxArrows;
			ds.arrows = gp.player.arrows;
			ds.maxBombs = gp.player.maxBombs;
			ds.bombs = gp.player.bombs;
			
			// PLAYER COLLECTABLES
			for (Entity collectable : gp.player.inventory) {				
				ds.colNames.add(collectable.name);
				ds.colAmounts.add(collectable.amount);				
			}
			
			// PLAYER ITEMS
			for (Entity item : gp.player.inventory_item) {				
				ds.itemNames.add(item.name);	
			}
			
			// PLAYER EQUIPMENT			
			if (gp.player.currentWeapon != null) 
				ds.sword = gp.player.currentWeapon.name;
			if (gp.player.currentShield != null)
				ds.shield = gp.player.currentShield.name;
			
			ds.canSwim = gp.player.canSwim;
			
			// NPCs
			ds.npcNames = new String[gp.maxMap][gp.npc[1].length];
			ds.npcWorldX = new int[gp.maxMap][gp.npc[1].length];
			ds.npcWorldY = new int[gp.maxMap][gp.npc[1].length];
			ds.npcDialogueSet = new int[gp.maxMap][gp.npc[1].length];
			ds.npcHasCutscene = new boolean[gp.maxMap][gp.npc[1].length];
			ds.npcHasItem = new boolean[gp.maxMap][gp.npc[1].length];
			
			for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
				
				for (int i = 0; i < gp.npc[1].length; i++) {
					
					if (gp.npc[mapNum][i] == null) {
						ds.npcNames[mapNum][i] = "NULL";
					}
					else {
						ds.npcNames[mapNum][i] = gp.npc[mapNum][i].name;
						ds.npcWorldX[mapNum][i] = gp.npc[mapNum][i].worldX;
						ds.npcWorldY[mapNum][i] = gp.npc[mapNum][i].worldY;
						ds.npcDialogueSet[mapNum][i] = gp.npc[mapNum][i].dialogueSet;
						ds.npcHasCutscene[mapNum][i] = gp.npc[mapNum][i].hasCutscene;
						ds.npcHasItem[mapNum][i] = gp.npc[mapNum][i].hasItemToGive;
						
						// IF NPC HAS ITEMS
						if (gp.npc[mapNum][i].inventory.size() > 0) {
							
							List<String> items = new ArrayList<>();
							
							// LOOP THROUGH INVENTORY
							for (int c = 0; c < gp.npc[mapNum][i].inventory.size(); c++) {	
								if (gp.npc[mapNum][i].inventory.get(c) != null) {									
									items.add(gp.npc[mapNum][i].inventory.get(c).name);
								}
							}
							
							// KEY: NPC NAME, VALUE: ITEMS LIST
							ds.npcInventory.put(gp.npc[mapNum][i].name, items);
						}
					}					
				}				
			}
			
			// ENEMIES
			ds.emyAlive = new boolean[gp.maxMap][gp.enemy[1].length];
			for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {				
				for (int i = 0; i < gp.enemy[1].length; i++) {
					if (gp.enemy[mapNum][i] == null) {
						ds.emyAlive[mapNum][i] = false;
					}
				}
			}			
			
			// MAP OBJECTS
			ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
			ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
			ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
			ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[1].length];
			ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[1].length];
			
			for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
				
				for (int i = 0; i < gp.obj[1].length; i++) {
					
					if (gp.obj[mapNum][i] == null) {
						ds.mapObjectNames[mapNum][i] = "NULL";
					}
					else {
						ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
						ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
						ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
						
						if (gp.obj[mapNum][i].loot != null) {
							ds.mapObjectLootNames[mapNum][i] = gp.obj[mapNum][i].loot.name;
						}
						
						ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
					}					
				}				
			}
			
			// WRITE THE DS OBJECT
			oos.writeObject(ds);
		}
		catch(Exception e) { 
			System.out.println(e);
		}
	}
	
	public void load() {
		
		try {			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
			
			// LOAD DATA FROM DS
			DataStorage ds = (DataStorage)ois.readObject();
						
			// DAY DATA
			gp.eManager.lighting.dayState = ds.dayState;
			gp.eManager.lighting.dayCounter = ds.dayCounter;
			gp.eManager.lighting.bloodMoonCounter = ds.bloodMoonCounter;
			
			// PROGRESS DATA
			Progress.enemy_room_1_1 = ds.enemy_room_1_1;
			Progress.enemy_room_1_2 = ds.enemy_room_1_2;
			Progress.enemy_room_1_3 = ds.enemy_room_1_3;
			Progress.puzzle_1_1 = ds.puzzle_1_1;
			Progress.bossDefeated_1 = ds.bossDefeated_1;
			
			// PLAYER DATA
			gp.currentMap = ds.cMap;
			gp.currentArea = ds.cArea;
			gp.player.worldX = ds.pWorldX;
			gp.player.worldY = ds.pWorldY;
			
			gp.player.name = ds.name;
			gp.player.maxLife = ds.maxLife;
			gp.player.life = ds.life;
			gp.player.attack = ds.attack;
						
			gp.player.rupees = ds.rupees;
			gp.player.maxArrows = ds.maxArrows;
			gp.player.arrows = ds.arrows;
			gp.player.maxBombs = ds.maxBombs;
			gp.player.bombs = ds.bombs;
			
			// PLAYER COLLECTABLES
			gp.player.inventory.clear();			
			for (int i = 0; i < ds.colNames.size(); i++) {				
				gp.player.inventory.add(gp.eGenerator.getObject(ds.colNames.get(i)));
				gp.player.inventory.get(i).amount = ds.colAmounts.get(i);
			}
			
			// PLAYER ITEMS
			gp.player.inventory_item.clear();			
			for (int i = 0; i < ds.itemNames.size(); i++) {				
				gp.player.inventory_item.add(gp.eGenerator.getObject(ds.itemNames.get(i)));
			}
			
			// PLAYER EQUIPMENT
			gp.player.currentWeapon = gp.eGenerator.getObject(ds.sword);
			gp.player.currentShield = gp.eGenerator.getObject(ds.shield);
			gp.player.canSwim = ds.canSwim;
			
			gp.player.getAttack();
			
			// NPCs
			for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
				
				for (int i = 0; i < gp.npc[1].length; i++) {
					
					if (ds.npcNames[mapNum][i].equals("NULL")) {
						gp.npc[mapNum][i] = null;
					}
					else {
						gp.npc[mapNum][i].worldX = ds.npcWorldX[mapNum][i];
						gp.npc[mapNum][i].worldY = ds.npcWorldY[mapNum][i];		
						gp.npc[mapNum][i].dialogueSet = ds.npcDialogueSet[mapNum][i];
						gp.npc[mapNum][i].hasCutscene = ds.npcHasCutscene[mapNum][i];
						gp.npc[mapNum][i].hasItemToGive = ds.npcHasItem[mapNum][i];
						
						// LOOP THROUGH NPC NAMES IN NPC INVENTORY
						for (String name : ds.npcInventory.keySet()) {
							
							// IF NAME FOUND IN KEY-VALUE PAIR
							if (gp.npc[mapNum][i].name.equals(name)) {
								
								// RESET INVENTORY
								gp.npc[mapNum][i].inventory.clear();
								
								// GET ITEMS FROM KEY-VALUE AND ADD TO INVENTORY
								List<String> items = ds.npcInventory.get(name);								
								for (String item : items) {
									gp.npc[mapNum][i].inventory.add(gp.eGenerator.getObject(item));
								}
							}
						}
					}			
				}				
			}
			
			// ENEMIES	
			for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {				
				for (int i = 0; i < gp.enemy[1].length; i++) {
					if (!ds.emyAlive[mapNum][i]) {
						gp.enemy[mapNum][i] = null;
					}
				}
			}		
			
			// MAP OBJECTS
			for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
				
				for (int i = 0; i < gp.obj[1].length; i++) {
					
					if (ds.mapObjectNames[mapNum][i].equals("NULL")) {
						gp.obj[mapNum][i] = null;
					}
					else {						
						gp.obj[mapNum][i] = gp.eGenerator.getObject(ds.mapObjectNames[mapNum][i]);
						gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
						gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
					
						if (ds.mapObjectLootNames[mapNum][i] != null) {
							gp.obj[mapNum][i].setLoot(gp.eGenerator.getObject(ds.mapObjectLootNames[mapNum][i]));
						}
						
						gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];
						if (gp.obj[mapNum][i].opened) {
							gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2; 
						}		
						
					}			
				}				
			}
		}
		catch(Exception e) { 
			System.out.println(e);
		}
	}
}