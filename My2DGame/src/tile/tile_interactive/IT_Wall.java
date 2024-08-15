package tile.tile_interactive;

import java.awt.Color;
import java.util.Random;

import application.GamePanel;
import entity.Entity;
import entity.collectable.COL_Bomb;
import entity.projectile.PRJ_Bomb;

public class IT_Wall extends InteractiveTile {

	public static final String itName = "Destructible Wall";
	
	public IT_Wall(GamePanel gp) {
		super(gp);
		
		name = itName;		
		destructible = true;
		life = 1;
	}
	public IT_Wall(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		name = itName;		
		destructible = true;
		life = 1;
	}
	
	public void getImage() {
		down1 = setup("/tiles_interactive/destructiblewall");
	}
	
	public boolean correctItem(Entity entity) {		
		
		boolean isCorrectItem = false;
		
		if (entity.name.equals(PRJ_Bomb.prjName)) {
			isCorrectItem = true;
		}
		
		return isCorrectItem;
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
	
	// DROPPED ITEM
	public void checkDrop() {
		int i = new Random().nextInt(100) + 1;
		if (i >= 75) dropItem(new COL_Bomb(gp));
	}
}