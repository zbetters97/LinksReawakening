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
		
		for (int i = 0; i < 7; i++)
			gp.iTile[i] = new IT_DryTree(gp, 27 + i, 12);
	}
	
	public void setObject() {	
		gp.obj[0] = new OBJ_Axe(gp);
		gp.obj[0].worldX = gp.tileSize * 23;
		gp.obj[0].worldY = gp.tileSize * 17;
		
		gp.obj[1] = new OBJ_Bow(gp);
		gp.obj[1].worldX = gp.tileSize * 20;
		gp.obj[1].worldY = gp.tileSize * 21;
		
		gp.obj[2] = new OBJ_Boots(gp);
		gp.obj[2].worldX = gp.tileSize * 22;
		gp.obj[2].worldY = gp.tileSize * 20;
		
		gp.obj[3] = new OBJ_Hookshot(gp);
		gp.obj[3].worldX = gp.tileSize * 22;
		gp.obj[3].worldY = gp.tileSize * 21;
	}	
	public void setNPC() {
		gp.npc[0] = new NPC_OldMan(gp);
		gp.npc[0].worldX = gp.tileSize * 21;
		gp.npc[0].worldY = gp.tileSize * 21;
	}	
	public void setEnemy() {
		
		gp.enemy[0] = new EMY_GreenSlime(gp);
		gp.enemy[0].worldX = gp.tileSize * 23;
		gp.enemy[0].worldY = gp.tileSize * 36;
		
		gp.enemy[1] = new EMY_GreenSlime(gp);
		gp.enemy[1].worldX = gp.tileSize * 24;
		gp.enemy[1].worldY = gp.tileSize * 37;
		
		gp.enemy[2] = new EMY_Bat(gp);
		gp.enemy[2].worldX = gp.tileSize * 25;
		gp.enemy[2].worldY = gp.tileSize * 39;
	}
}