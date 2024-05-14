package main;

import entity.NPC_OldMan;

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
}