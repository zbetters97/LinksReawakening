package main;

import enemy.*;
import entity.*;
import object.*;
import tile_interactive.IT_DigSpot;
import tile_interactive.IT_DryTree;
import tile_interactive.IT_Wall;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setInteractiveTiles() {
		
		int mapNum = 0;
		
		gp.iTile[mapNum][0] = new IT_DryTree(gp, 13, 21);
		gp.iTile[mapNum][1] = new IT_DryTree(gp, 36, 30);
		gp.iTile[mapNum][2] = new IT_DryTree(gp, 21, 16);
		gp.iTile[mapNum][3] = new IT_DryTree(gp, 10, 22);
		gp.iTile[mapNum][4] = new IT_DigSpot(gp, mapNum, 30, 36);
		gp.iTile[mapNum][5] = new IT_DigSpot(gp, mapNum, 27, 16);
		
		gp.iTile[mapNum][6] = new IT_Wall(gp, 30, 21);
		gp.iTile[mapNum][7] = new IT_Wall(gp, 32, 21);
	}
	public void setObject() {		
		
		int mapNum = 0;
		
		gp.obj[mapNum][0] = new ITM_Shovel(gp);
		gp.obj[mapNum][0].worldX = gp.tileSize * 35;
		gp.obj[mapNum][0].worldY = gp.tileSize * 38;
		
		gp.obj[mapNum][1] = new ITM_Axe(gp);
		gp.obj[mapNum][1].worldX = gp.tileSize * 16;
		gp.obj[mapNum][1].worldY = gp.tileSize * 21;
		
		gp.obj[mapNum][2] = new ITM_Hookshot(gp);
		gp.obj[mapNum][2].worldX = gp.tileSize * 31;
		gp.obj[mapNum][2].worldY = gp.tileSize * 21;
		
		gp.obj[mapNum][3] = new ITM_Boots(gp);
		gp.obj[mapNum][3].worldX = gp.tileSize * 18;
		gp.obj[mapNum][3].worldY = gp.tileSize * 16;
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
		
		gp.enemy[mapNum][3] = new EMY_Goblin(gp);
		gp.enemy[mapNum][3].worldX = gp.tileSize * 36;
		gp.enemy[mapNum][3].worldY = gp.tileSize * 27;
		
		gp.enemy[mapNum][4] = new EMY_Goblin(gp);
		gp.enemy[mapNum][4].worldX = gp.tileSize * 12;
		gp.enemy[mapNum][4].worldY = gp.tileSize * 31;
	}
}