package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
			
			// PLAYER INVENTORY
			for (Entity item : gp.player.inventory) {				
				ds.itemNames.add(item.name);
				ds.itemAmounts.add(item.amount);				
			}
			
			// PLAYER EQUIPMENT
			ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
			ds.currentShieldSlot = gp.player.getCurrentShieldSlot();
			
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
		catch(Exception e) { }
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
			
			// PLAYER ITEMS
			gp.player.inventory.clear();			
			for (int i = 0; i < ds.itemNames.size(); i++) {				
				gp.player.inventory.add(gp.eGenerator.getObject(ds.itemNames.get(i)));
				gp.player.inventory.get(i).amount = ds.itemAmounts.get(i);
			}
			
			// PLAYER EQUIPMENT
			gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
			gp.player.currentShield = gp.player.inventory.get(ds.currentShieldSlot);
			
			gp.player.getAttack();
			
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
		catch(Exception e) { }
	}
}