package tile_interactive;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;
import object.COL_Rupee_Red;

public class IT_DigSpot extends InteractiveTile {

	GamePanel gp;
	int mapNum;
	
	public IT_DigSpot(GamePanel gp, int mapNum, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		destructible = true;
		diggable = true;
		life = 1;
		
		this.mapNum = mapNum;
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		down1 = setup("/tiles/003", gp.tileSize, gp.tileSize);
	}
	
	public boolean isCorrectItem(Entity entity) {		
		
		boolean isCorrectItem = false;				
		if (entity.currentItem.name.equals("Shovel")) {
			isCorrectItem = true;
		}
		
		return isCorrectItem;
	}
	
	public void playSE() {
		gp.playSE(3, 7);
	}
	
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = new IT_Hole(gp, worldX / gp.tileSize, worldY / gp.tileSize);

		gp.obj[mapNum][gp.obj.length + 1] = new COL_Rupee_Red(gp);
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