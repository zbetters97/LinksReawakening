package main;

import collectables.COL_Rupee_Red;
import enemy.*;
import entity.*;
import items.ITM_Axe;
import items.ITM_Boots;
import items.ITM_Bow;
import items.ITM_Feather;
import items.ITM_Hookshot;
import items.ITM_Lantern;
import items.ITM_Shovel;
import object.*;
import tile_interactive.*;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
		
	public void setObject() {		
		
		int mapNum = 0;
		int i = 0;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new ITM_Shovel(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 38;
		gp.obj[mapNum][i].worldY = gp.tileSize * 41;	
		i++;				
		gp.obj[mapNum][i] = new ITM_Axe(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 16;
		gp.obj[mapNum][i].worldY = gp.tileSize * 21;	
		i++;	
		gp.obj[mapNum][i] = new ITM_Boots(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 18;
		gp.obj[mapNum][i].worldY = gp.tileSize * 16;
		i++;	
		gp.obj[mapNum][i] = new ITM_Hookshot(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 31;
		gp.obj[mapNum][i].worldY = gp.tileSize * 21;		
		i++;	
		gp.obj[mapNum][i] = new ITM_Feather(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 12;
		gp.obj[mapNum][i].worldY = gp.tileSize * 32;
		i++;	
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new ITM_Bow(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 34;
		gp.obj[mapNum][i].worldY = gp.tileSize * 8;		
		i++;	
		gp.obj[mapNum][i] = new OBJ_Tent(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 40;
		gp.obj[mapNum][i].worldY = gp.tileSize * 11;
		i++;	
		
		mapNum = 2;
		i = 0;
		
		gp.obj[mapNum][i] = new OBJ_Door_Iron(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 18;
		gp.obj[mapNum][i].worldY = gp.tileSize * 23;	
		i++;
	}	
	public void setNPC() {
		
		int mapNum = 0;
		int i = 0;
		
		gp.npc[mapNum][i] = new NPC_OldMan(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 30;
		gp.npc[mapNum][i].worldY = gp.tileSize * 29;
		i++;
		
		mapNum = 1;
		i = 0;
		
		gp.npc[mapNum][i] = new NPC_Merchant(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 12;
		gp.npc[mapNum][i].worldY = gp.tileSize * 7;
		i++;
		
		mapNum = 2;
		i = 0;
		
		gp.npc[mapNum][i] = new NPC_Boulder(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 34;
		gp.npc[mapNum][i].worldY = gp.tileSize * 26;
		i++;
		gp.npc[mapNum][i] = new NPC_Boulder(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 11;
		gp.npc[mapNum][i].worldY = gp.tileSize * 18;
		i++;
		gp.npc[mapNum][i] = new NPC_Boulder(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 23;
		gp.npc[mapNum][i].worldY = gp.tileSize * 14;
		i++;
	}	
	public void setEnemy() {		
		
		int mapNum = 0;
		int i = 0;
		
		gp.enemy[mapNum][i] = new EMY_Bat(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 23;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 40;
		i++;
		gp.enemy[mapNum][i] = new EMY_Bat(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 21;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 37;
		i++;
		gp.enemy[mapNum][i] = new EMY_Slime_Red(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 36;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 31;
		i++;
		gp.enemy[mapNum][i] = new EMY_Slime_Green(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 38;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 40;
		i++;
		gp.enemy[mapNum][i] = new EMY_Goblin_Archer(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 36;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 27;
		i++;
		gp.enemy[mapNum][i] = new EMY_Goblin_Archer(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 12;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 31;
		i++;
		gp.enemy[mapNum][i] = new EMY_Goblin_Combat(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 12;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 29;
		i++;
		
		mapNum = 3;
		i = 0;
		gp.enemy[mapNum][i] = new BOS_Skeleton(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 23;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 16;
	}
	public void setInteractiveTiles() {
		
		int mapNum = 0;
		int i = 0;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 13, 21); i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 36, 30); i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 21, 16); i++;
		gp.iTile[mapNum][i] = new IT_DigSpot(gp, mapNum, 30, 36, new COL_Rupee_Red(gp)); i++;
		gp.iTile[mapNum][i] = new IT_DigSpot(gp, mapNum, 27, 16, new COL_Rupee_Red(gp)); i++;
		gp.iTile[mapNum][i] = new IT_DigSpot(gp, mapNum, 31, 25, new ITM_Lantern(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Wall(gp, 30, 21); i++;
		gp.iTile[mapNum][i] = new IT_Wall(gp, 32, 21); i++;		
		
		mapNum = 2;
		i = 0;
		
		gp.iTile[mapNum][i] = new IT_Wall(gp, 10, 22); i++;
		gp.iTile[mapNum][i] = new IT_Plate_Metal(gp, 20, 22); i++;
		gp.iTile[mapNum][i] = new IT_Plate_Metal(gp, 8, 17); i++;
		gp.iTile[mapNum][i] = new IT_Plate_Metal(gp, 39, 31); i++;
	}
}