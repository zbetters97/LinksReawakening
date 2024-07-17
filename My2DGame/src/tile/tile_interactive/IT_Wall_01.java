package tile.tile_interactive;

import java.awt.Color;
import java.util.Random;

import application.GamePanel;
import entity.collectable.COL_Bomb;
import entity.collectable.COL_Rupee_Green;

public class IT_Wall_01 extends InteractiveTile {

	public static final String itName = "Destructible Wall 01";
	GamePanel gp;
	
	public IT_Wall_01(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = itName;		
		destructible = true;
		bombable = true;
		life = 1;
		
		getImage();
	}
	public IT_Wall_01(GamePanel gp, int col, int row, String direction) {
		super(gp, col, row);
		this.gp = gp;
		this.direction = direction;
		
		name = itName;		
		destructible = true;
		bombable = true;
		life = 1;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		getImage();
	}
	public void getImage() {
		up1 = setup("/tiles_interactive/destructiblewall_01_up");
		down1 = setup("/tiles_interactive/destructiblewall_01_down");
		left1 = setup("/tiles_interactive/destructiblewall_01_left");
		right1 = setup("/tiles_interactive/destructiblewall_01_right");
	}
	
	public void playSE() {
		gp.playSE(6, 6);
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
		
		if (75 > i && i >= 50) dropItem(new COL_Rupee_Green(gp));
		else if (i >= 75) dropItem(new COL_Bomb(gp));
	}
}