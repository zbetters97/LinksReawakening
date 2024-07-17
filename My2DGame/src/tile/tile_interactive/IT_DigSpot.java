package tile.tile_interactive;

import java.awt.Color;

import application.GamePanel;
import entity.Entity;
import entity.item.ITM_Shovel;

public class IT_DigSpot extends InteractiveTile {

	public static final String itName = "Dig Spot";
	int mapNum;
	Entity item;
	GamePanel gp;	
	
	public IT_DigSpot(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = itName;		
		destructible = true;
		diggable = true;
		collision = false;
		life = 1;
		
		down1 = setup("/tiles/017");
	}	
	public IT_DigSpot(GamePanel gp, int col, int row, Entity item) {
		super(gp, col, row);
		this.gp = gp;
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		this.item = item;
		
		name = itName;		
		destructible = true;
		diggable = true;
		collision = false;
		life = 1;
		
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