package tile.tile_interactive;

import java.awt.Color;
import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;
import entity.item.ITM_Shovel;
import entity.projectile.PRJ_Hookshot;

public class IT_Pot extends InteractiveTile {

	public static final String itName = "Pot";
	GamePanel gp;
	
	public IT_Pot(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_obstacle;
		name = itName;
		life = 1;
		destructible = true;
		bombable = true;
		grabbable = true;
		
		hitbox = new Rectangle(8, 16, 32, 28); 	
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		direction = "down";
		
		getImage();	
	}
	public IT_Pot(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		type = type_obstacle;
		name = itName;
		life = 1;
		destructible = true;
		bombable = true;
		grabbable = true;
		
		hitbox = new Rectangle(8, 16, 32, 28); 	
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		direction = "down";
		
		getImage();	
	}
	public IT_Pot(GamePanel gp, int col, int row, Entity loot) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		this.loot = loot;
		
		type = type_obstacle;
		name = itName;
		life = 1;
		destructible = true;
		bombable = true;
		grabbable = true;
		
		hitbox = new Rectangle(8, 16, 32, 28); 	
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		direction = "down";
		
		getImage();
	}
	
	public void getImage() {
		down1 = setup("/tiles_interactive/pot");
		up1 = down1;
		left1 = down1;
		right1 = down1;
	}
	
	public void setLoot(Entity loot) {
		this.loot = loot;		
	}
		
	public void interact() {
		playSE();
		checkDrop();
		alive = false;
	}
	
	public boolean correctItem(Entity entity) {		
		
		boolean isCorrectItem = false;
		
		if (!entity.name.equals(PRJ_Hookshot.prjName) && 
				!entity.name.equals(ITM_Shovel.itmName)) {
			isCorrectItem = true;
		}
		
		return isCorrectItem;
	}
	public Color getParticleColor() {
		Color color = new Color(150,83,23); // BROWN
		return color;
	}	
	public int getParticleSize() {		
		int size = 9; // 9px
		return size;
	}	
	public int getParticleSpeed() {
		int speed = 1;
		return speed;		
	}	
	public int getParticleMaxLife() {
		int maxLife = 16; // 16 frames
		return maxLife;
	}
	
	// DROPPED ITEM
	public void checkDrop() {
		dropItem(loot);
	}
		
	public void playSE() {
		gp.playSE(4, 9);
	}
}