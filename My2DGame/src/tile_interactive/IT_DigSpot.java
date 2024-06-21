package tile_interactive;

import java.awt.Color;

import entity.Entity;
import entity.item.ITM_Shovel;
import main.GamePanel;

public class IT_DigSpot extends InteractiveTile {

	public static final String itName = "Dig Spot";
	int mapNum;
	Entity item;
	GamePanel gp;	
	
	public IT_DigSpot(GamePanel gp, int mapNum, int col, int row, Entity item) {
		super(gp, col, row);
		this.gp = gp;
		this.item = item;
		
		name = itName;		
		destructible = true;
		diggable = true;
		life = 1;
		
		this.mapNum = mapNum;
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		down1 = setup("/tiles/017");
	}
	
	public boolean correctItem(Entity entity) {		
		
		boolean correctItem = false;				
		if (entity.currentItem.name.equals(ITM_Shovel.itmName)) {
			correctItem = true;
		}
		
		return correctItem;
	}
	
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = new IT_Hole(gp, worldX / gp.tileSize, worldY / gp.tileSize);

		gp.obj[mapNum][gp.obj.length + 1] = item;
		gp.obj[mapNum][gp.obj.length + 1].worldX = worldX;
		gp.obj[mapNum][gp.obj.length + 1].worldY = worldY;
		
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