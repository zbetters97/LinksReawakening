package tile.tile_interactive;

import java.awt.Color;

import application.GamePanel;
import entity.Entity;
import entity.item.ITM_Axe;

public class IT_DryTree extends InteractiveTile {

	public static final String itName = "Dry Tree";
	GamePanel gp;
	
	public IT_DryTree(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		name = itName;
		destructible = true;
		grabbale = true;
		life = 3;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		down1 = setup("/tiles_interactive/drytree");
	}
	
	public boolean correctItem(Entity entity) {		
		
		boolean correctItem = false;				
		if (entity.currentItem.name.equals(ITM_Axe.itmName))
			correctItem = true;		
		
		return correctItem;
	}
	
	public void playSE() {
		gp.playSE(1, 7);
	}
	
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = new IT_Trunk(gp, worldX / gp.tileSize, worldY / gp.tileSize);
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