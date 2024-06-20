package main;

import collectable.COL_Rupee_Red;
import data.Progress;
import enemy.*;
import entity.*;
import item.ITM_Axe;
import item.ITM_Boots;
import item.ITM_Bow;
import item.ITM_Feather;
import item.ITM_Hookshot;
import item.ITM_Lantern;
import item.ITM_Shovel;
import object.*;
import object_interactive.OT_Block_Pushable;
import tile_interactive.*;

public class AssetSetter {
	
	private GamePanel gp;
	
	protected AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
		
	protected void setObject() {		
		
		int mapNum = 0;
		int i = 0;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new ITM_Shovel(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 38;
		gp.obj[mapNum][i].worldY = gp.tileSize * 41;	
		i++;				
		gp.obj[mapNum][i] = new ITM_Axe(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 15;
		gp.obj[mapNum][i].worldY = gp.tileSize * 21;	
		i++;	
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new ITM_Boots(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 17;
		gp.obj[mapNum][i].worldY = gp.tileSize * 15;
		i++;	
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new ITM_Hookshot(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 30;
		gp.obj[mapNum][i].worldY = gp.tileSize * 21;		
		i++;	
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new ITM_Feather(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 12;
		gp.obj[mapNum][i].worldY = gp.tileSize * 32;
		i++;	
		gp.obj[mapNum][i] = new OBJ_Tent(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 40;
		gp.obj[mapNum][i].worldY = gp.tileSize * 11;
		i++;	
		gp.obj[mapNum][i] = new OBJ_Chest(gp);
		gp.obj[mapNum][i].setLoot(new ITM_Bow(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 38;
		gp.obj[mapNum][i].worldY = gp.tileSize * 7;		
		i++;	
		
		mapNum = 2;
		i = 0;
		
		gp.obj[mapNum][i] = new OBJ_Door_Closed(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 18;
		gp.obj[mapNum][i].worldY = gp.tileSize * 23;	
		i++;		
		
		mapNum = 3;
		i = 0;
		
		gp.obj[mapNum][i] = new OBJ_Door_Closed(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 25;
		gp.obj[mapNum][i].worldY = gp.tileSize * 15;	
		i++;			
		gp.obj[mapNum][i] = new OBJ_BlueHeart(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 25;
		gp.obj[mapNum][i].worldY = gp.tileSize * 8;	
		i++;	
	}	
	protected void setNPC() {
		
		int mapNum = 0;
		int i = 0;
		
		gp.npc[mapNum][i] = new NPC_OldMan(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 30;
		gp.npc[mapNum][i].worldY = gp.tileSize * 30;
		i++;
		
		gp.npc[mapNum][i] = new NPC_Traveler_1(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 20;
		gp.npc[mapNum][i].worldY = gp.tileSize * 21;
		i++;
		gp.npc[mapNum][i] = new NPC_Traveler_2(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 26;
		gp.npc[mapNum][i].worldY = gp.tileSize * 11;
		i++;
		
		mapNum = 1;
		i = 0;
		
		gp.npc[mapNum][i] = new NPC_Merchant(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 12;
		gp.npc[mapNum][i].worldY = gp.tileSize * 7;
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
		
		if (!Progress.bossDefeated) {
			gp.enemy[mapNum][i] = new BOS_Skeleton(gp);
			gp.enemy[mapNum][i].worldX = gp.tileSize * 23;
			gp.enemy[mapNum][i].worldY = gp.tileSize * 16;
		}
	}
	protected void setInteractiveObjects() {
		
		int mapNum = 2;
		int i = 0;
		
		gp.obj_t[mapNum][i] = new OT_Block_Pushable(gp);
		gp.obj_t[mapNum][i].worldX = gp.tileSize * 34;
		gp.obj_t[mapNum][i].worldY = gp.tileSize * 26;
		i++;
		gp.obj_t[mapNum][i] = new OT_Block_Pushable(gp);
		gp.obj_t[mapNum][i].worldX = gp.tileSize * 11;
		gp.obj_t[mapNum][i].worldY = gp.tileSize * 18;
		i++;
		gp.obj_t[mapNum][i] = new OT_Block_Pushable(gp);
		gp.obj_t[mapNum][i].worldX = gp.tileSize * 23;
		gp.obj_t[mapNum][i].worldY = gp.tileSize * 14;
		i++;
	}
	protected void setInteractiveTiles() {
		
		int mapNum = 0;
		int i = 0;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 13, 21); i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 21, 16); i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 35, 30); i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 36, 30); i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 37, 30); i++;		
		gp.iTile[mapNum][i] = new IT_DigSpot(gp, mapNum, 30, 36, new COL_Rupee_Red(gp)); i++;
		gp.iTile[mapNum][i] = new IT_DigSpot(gp, mapNum, 27, 16, new COL_Rupee_Red(gp)); i++;
		gp.iTile[mapNum][i] = new IT_DigSpot(gp, mapNum, 31, 25, new ITM_Lantern(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Wall(gp, 28, 20); i++;
		gp.iTile[mapNum][i] = new IT_Wall(gp, 28, 21); i++;
		gp.iTile[mapNum][i] = new IT_Wall(gp, 28, 22); i++;
		gp.iTile[mapNum][i] = new IT_Wall(gp, 32, 20); i++;
		gp.iTile[mapNum][i] = new IT_Wall(gp, 32, 21); i++;
		gp.iTile[mapNum][i] = new IT_Wall(gp, 32, 22); i++;
		
		mapNum = 2;
		i = 0;
		
		gp.iTile[mapNum][i] = new IT_Wall(gp, 10, 22); i++;
		gp.iTile[mapNum][i] = new IT_Button_Metal(gp, 20, 22); i++;
		gp.iTile[mapNum][i] = new IT_Button_Metal(gp, 8, 17); i++;
		gp.iTile[mapNum][i] = new IT_Button_Metal(gp, 39, 31); i++;
	}
}