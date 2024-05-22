package main;

import enemy.*;
import entity.*;
import object.*;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {		
		// CREATE NEW OBJECT
		// ASSIGN X, Y COORD (n * tileSize, n = # of tiles to location)
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