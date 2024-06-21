package tile_interactive;

import java.awt.Color;
import java.util.Random;

import entity.collectable.COL_Bomb;
import entity.collectable.COL_Rupee_Blue;
import main.GamePanel;

public class IT_Wall extends InteractiveTile {

	public static final String itName = "Destructible Wall";
	GamePanel gp;
	
	public IT_Wall(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		name = itName;		
		destructible = true;
		bombable = true;
		life = 1;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		down1 = setup("/tiles_interactive/destructiblewall");
	}
	
	public void playSE() {
		gp.playSE(1, 7);
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
		
		if (i < 50) dropItem(new COL_Rupee_Blue(gp));
		else dropItem(new COL_Bomb(gp));
	}
}