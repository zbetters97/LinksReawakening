package main;

import enemy.*;
import entity.*;
import object.*;
import tile_interactive.IT_DryTree;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setInteractiveTiles() {
		
		int mapNum = 0;
		
		for (int i = 0; i < 8; i++)
			gp.iTile[mapNum][i] = new IT_DryTree(gp, 26 + i, 12);
		
		gp.iTile[mapNum][0] = new IT_DryTree(gp, 31, 21);
	}
	public void setObject() {		
		
		int mapNum = 0;
		
		gp.obj[mapNum][0] = new ITM_Bow(gp);
		gp.obj[mapNum][0].worldX = gp.tileSize * 24;
		gp.obj[mapNum][0].worldY = gp.tileSize * 20;
		
		gp.obj[mapNum][1] = new ITM_Boots(gp);
		gp.obj[mapNum][1].worldX = gp.tileSize * 24;
		gp.obj[mapNum][1].worldY = gp.tileSize * 22;
		
		gp.obj[mapNum][2] = new ITM_Hookshot(gp);
		gp.obj[mapNum][2].worldX = gp.tileSize * 22;
		gp.obj[mapNum][2].worldY = gp.tileSize * 20;
		
		gp.obj[mapNum][3] = new ITM_Axe(gp);
		gp.obj[mapNum][3].worldX = gp.tileSize * 22;
		gp.obj[mapNum][3].worldY = gp.tileSize * 22;
	}	
	public void setNPC() {
		
		int mapNum = 0;
		
		gp.npc[mapNum][0] = new NPC_OldMan(gp);
		gp.npc[mapNum][0].worldX = gp.tileSize * 21;
		gp.npc[mapNum][0].worldY = gp.tileSize * 21;
		
		mapNum = 1;
		
		gp.npc[mapNum][0] = new NPC_OldMan(gp);
		gp.npc[mapNum][0].worldX = gp.tileSize * 12;
		gp.npc[mapNum][0].worldY = gp.tileSize * 9;
	}	
	public void setEnemy() {		
		
		int mapNum = 0;

		gp.enemy[mapNum][1] = new EMY_Goblin(gp);
		gp.enemy[mapNum][1].worldX = gp.tileSize * 24;
		gp.enemy[mapNum][1].worldY = gp.tileSize * 37;
		
		gp.enemy[mapNum][0] = new EMY_GreenSlime(gp);
		gp.enemy[mapNum][0].worldX = gp.tileSize * 23;
		gp.enemy[mapNum][0].worldY = gp.tileSize * 36;
		
		gp.enemy[mapNum][2] = new EMY_Bat(gp);
		gp.enemy[mapNum][2].worldX = gp.tileSize * 25;
		gp.enemy[mapNum][2].worldY = gp.tileSize * 39;
	}
}