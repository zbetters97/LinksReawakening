package entity.collectable;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;

public class COL_Fairy extends Entity {
	
	public static final String colName = "Fairy";
	
	public COL_Fairy(GamePanel gp) {
		super(gp);
		
		collision = false;
		
		type = type_consumable;		
		name = colName;
		description = "[Fairy]\nHeals all hearts.";
		stackable = true;
		
		hitbox = new Rectangle(9, 9, 30, 30); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {
		down1 = setup("/collectables/fairy");
	}
	
	public boolean use(Entity user) {
		playHealSE();
		user.life = user.maxLife;
		return true;
	}
	public void playSE() {
		gp.playSE(6, 2);
	}
	public void playHealSE() {
		gp.playSE(6, 3);
	}
	
}