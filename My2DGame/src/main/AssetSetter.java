package main;

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
import tile_interactive.*;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
		
	public void setObject() {		
		
		int mapNum = 0;
				
		gp.obj[mapNum][0] = new OBJ_Chest(gp);
		gp.obj[mapNum][0].setLoot(new ITM_Shovel(gp));
		gp.obj[mapNum][0].worldX = gp.tileSize * 38;
		gp.obj[mapNum][0].worldY = gp.tileSize * 41;	
						
		gp.obj[mapNum][1] = new ITM_Axe(gp);
		gp.obj[mapNum][1].worldX = gp.tileSize * 16;
		gp.obj[mapNum][1].worldY = gp.tileSize * 21;	
				
		gp.obj[mapNum][2] = new ITM_Boots(gp);
		gp.obj[mapNum][2].worldX = gp.tileSize * 18;
		gp.obj[mapNum][2].worldY = gp.tileSize * 16;
		
		gp.obj[mapNum][3] = new ITM_Hookshot(gp);
		gp.obj[mapNum][3].worldX = gp.tileSize * 31;
		gp.obj[mapNum][3].worldY = gp.tileSize * 21;		
		
		gp.obj[mapNum][4] = new ITM_Feather(gp);
		gp.obj[mapNum][4].worldX = gp.tileSize * 12;
		gp.obj[mapNum][4].worldY = gp.tileSize * 32;
		
		gp.obj[mapNum][5] = new OBJ_Chest(gp);
		gp.obj[mapNum][5].setLoot(new ITM_Bow(gp));
		gp.obj[mapNum][5].worldX = gp.tileSize * 34;
		gp.obj[mapNum][5].worldY = gp.tileSize * 8;		

		gp.obj[mapNum][6] = new OBJ_Tent(gp);
		gp.obj[mapNum][6].worldX = gp.tileSize * 40;
		gp.obj[mapNum][6].worldY = gp.tileSize * 11;
	}	
	public void setNPC() {
		
		int mapNum = 0;
		
		gp.npc[mapNum][0] = new NPC_OldMan(gp);
		gp.npc[mapNum][0].worldX = gp.tileSize * 30;
		gp.npc[mapNum][0].worldY = gp.tileSize * 29;
		
		mapNum = 1;
		
		gp.npc[mapNum][0] = new NPC_Merchant(gp);
		gp.npc[mapNum][0].worldX = gp.tileSize * 12;
		gp.npc[mapNum][0].worldY = gp.tileSize * 7;
	}	
	public void setEnemy() {		
		
		int mapNum = 0;
		
		gp.enemy[mapNum][0] = new EMY_Bat(gp);
		gp.enemy[mapNum][0].worldX = gp.tileSize * 23;
		gp.enemy[mapNum][0].worldY = gp.tileSize * 40;
		
		gp.enemy[mapNum][1] = new EMY_Bat(gp);
		gp.enemy[mapNum][1].worldX = gp.tileSize * 21;
		gp.enemy[mapNum][1].worldY = gp.tileSize * 37;
		
		gp.enemy[mapNum][2] = new EMY_Slime_Green(gp);
		gp.enemy[mapNum][2].worldX = gp.tileSize * 36;
		gp.enemy[mapNum][2].worldY = gp.tileSize * 31;
		
		gp.enemy[mapNum][3] = new EMY_Slime_Green(gp);
		gp.enemy[mapNum][3].worldX = gp.tileSize * 38;
		gp.enemy[mapNum][3].worldY = gp.tileSize * 40;
		
		gp.enemy[mapNum][4] = new EMY_Goblin_Archer(gp);
		gp.enemy[mapNum][4].worldX = gp.tileSize * 36;
		gp.enemy[mapNum][4].worldY = gp.tileSize * 27;
		
		gp.enemy[mapNum][5] = new EMY_Goblin_Archer(gp);
		gp.enemy[mapNum][5].worldX = gp.tileSize * 12;
		gp.enemy[mapNum][5].worldY = gp.tileSize * 31;
		
		gp.enemy[mapNum][6] = new EMY_Goblin_Combat(gp);
		gp.enemy[mapNum][6].worldX = gp.tileSize * 12;
		gp.enemy[mapNum][6].worldY = gp.tileSize * 29;
	}
	public void setInteractiveTiles() {
		
		int mapNum = 0;
		
		gp.iTile[mapNum][0] = new IT_DryTree(gp, 13, 21);
		gp.iTile[mapNum][1] = new IT_DryTree(gp, 36, 30);
		gp.iTile[mapNum][2] = new IT_DryTree(gp, 21, 16);
		gp.iTile[mapNum][4] = new IT_DigSpot(gp, mapNum, 30, 36, new COL_Rupee_Red(gp));
		gp.iTile[mapNum][5] = new IT_DigSpot(gp, mapNum, 27, 16, new COL_Rupee_Red(gp));
		gp.iTile[mapNum][6] = new IT_DigSpot(gp, mapNum, 31, 25, new ITM_Lantern(gp));
		
		gp.iTile[mapNum][7] = new IT_Wall(gp, 30, 21);
		gp.iTile[mapNum][8] = new IT_Wall(gp, 32, 21);
		
		gp.iTile[mapNum][9] = new IT_Button(gp, 10, 17);
		gp.iTile[mapNum][9].pressable = true;
		
		gp.iTile[mapNum][10] = new IT_Button(gp, 11, 20);
		gp.iTile[mapNum][11] = new IT_Button(gp, 12, 20);
	}
}