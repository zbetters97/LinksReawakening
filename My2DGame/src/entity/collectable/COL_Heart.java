package entity.collectable;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;

public class COL_Heart extends Entity {
	
	public static final String colName = "COL Heart";
	
	public COL_Heart(GamePanel gp) {
		super(gp);
		
		collision = false;
		
		type = type_collectable;		
		name = colName;
		value = 4;
		lifeDuration = 60 * 6; // REMOVE AFTER 6 SECONDS
		
		hitbox = new Rectangle(9, 9, 30, 30); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {
		down1 = setup("/collectables/COL_HEART");
	}
	
	public boolean use(Entity user) {
		playSE();
		user.life += value;
		if (user.life > user.maxLife) {
			user.life = user.maxLife;
		}
		return true;
	}
	public void playSE() {
		gp.playSE(6, 2);	
	}
}