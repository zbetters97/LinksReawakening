package tile.tile_interactive;

import java.awt.Color;

import application.GamePanel;
import entity.Entity;
import entity.item.ITM_Shovel;

public class IT_DigSpot extends InteractiveTile {

	public static final String itName = "Dig Spot";
	int mapNum;
	
	public IT_DigSpot(GamePanel gp) {
		super(gp);
		
		name = itName;		
		destructible = true;
		collision = false;
		life = 1;
	}	
	public IT_DigSpot(GamePanel gp, int col, int row, Entity loot) {
		super(gp, col, row);
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		this.loot = loot;
		
		name = itName;		
		destructible = true;
		collision = false;
		life = 1;
	}
	
	public void getImage() {
		down1 = setup("/tiles/017");
	}
	
	public boolean correctItem(Entity entity) {		
		
		boolean correctItem = false;				
		if (entity.name.equals(ITM_Shovel.itmName)) {
			correctItem = true;
		}
		
		return correctItem;
	}
	
	public InteractiveTile getDestroyedForm() {
		
		InteractiveTile tile = new IT_Hole(gp, worldX / gp.tileSize, worldY / gp.tileSize);
		
		for (int i = 0; i < gp.obj[1].length; i++) {
			if (gp.obj[gp.currentMap][i] == null) {
				
				gp.obj[mapNum][i] = loot;
				gp.obj[mapNum][i].worldX = worldX;
				gp.obj[mapNum][i].worldY = worldY;
				
				break;
			}
		}
		
		return tile;
	}	
	public Color getParticleColor() {
		Color color = new Color(65,50,30); // BROWN
		return color;
	}	
	public int getParticleSize() {		
		int size = 6; // 6px
		return size;
	}	
	public int getParticleSpeed() {
		int speed = 1;
		return speed;		
	}	
	public int getParticleMaxLife() {
		int maxLife = 20; // 20 frames
		return maxLife;
	}
}