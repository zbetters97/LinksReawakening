package entity.collectable;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;

public class COL_Heart extends Entity {
	
	public static final String colName = "COL Heart";
	GamePanel gp;
	
	public COL_Heart(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_collectable;		
		name = colName;
		value = 2;
		lifeDuration = 60 * 6; // REMOVE AFTER 6 SECONDS
		collision = false;
		
		hitbox = new Rectangle(9, 9, 30, 30); 		
		hitboxDefaultX = hitbox.x;
	    hitboxDefaultY = hitbox.y;
		
		image1 = setup("/collectables/COL_HEART_FULL", gp.tileSize / 2, gp.tileSize / 2);
		image2 = setup("/collectables/COL_HEART_HALF", gp.tileSize / 2, gp.tileSize / 2);
		image3 = setup("/collectables/COL_HEART_EMPTY", gp.tileSize / 2, gp.tileSize / 2);
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