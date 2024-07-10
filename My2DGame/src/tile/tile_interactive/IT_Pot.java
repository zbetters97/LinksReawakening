package tile.tile_interactive;

import java.awt.Rectangle;
import java.util.Random;

import application.GamePanel;
import entity.Entity;
import entity.item.ITM_Shovel;
import entity.projectile.PRJ_Arrow;
import entity.projectile.PRJ_Hookshot;

public class IT_Pot extends InteractiveTile {

	public static final String itName = "Pot";
	GamePanel gp;
	Entity lootOne, lootTwo;
	
	public IT_Pot(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		name = itName;
		life = 1;
		destructible = true;
		bombable = true;
		
		direction = "down";
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		hitbox = new Rectangle(0, 0, 48, 48);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		down1 = setup("/tiles_interactive/pot");		
	}
	public IT_Pot(GamePanel gp, int col, int row, Entity loot) {
		super(gp, col, row);
		this.gp = gp;
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		this.lootOne = loot;
		this.lootTwo = loot;
		
		name = itName;
		life = 1;
		destructible = true;
		bombable = true;
		
		direction = "down";
		
		hitbox = new Rectangle(0, 0, 48, 48);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		down1 = setup("/tiles_interactive/pot");		
	}
	
	public void setLoot(Entity lootOne, Entity lootTwo) {
		this.lootOne = lootOne;
		this.lootTwo = lootTwo;
	}
	public void setLoot(Entity lootOne) {
		this.lootOne = lootOne;
		this.lootTwo = lootOne;
	}
		
	public void interact() {
		playSE();
		checkDrop();
		alive = false;
	}
	
	public boolean correctItem(Entity entity) {		
		
		boolean isCorrectItem = true;
		
		if (entity.name.equals(PRJ_Hookshot.prjName) || 
				entity.name.equals(PRJ_Arrow.prjName) ||
				entity.name.equals(ITM_Shovel.itmName))
			isCorrectItem = false;
		
		return isCorrectItem;
	}
	
	// DROPPED ITEM
	public void checkDrop() {
		int i = new Random().nextInt(100) + 1;
		
		if (i <= 50) dropItem(lootOne);
		else dropItem(lootTwo);
	}
		
	public void playSE() {
		gp.playSE(4, 9);
	}
}