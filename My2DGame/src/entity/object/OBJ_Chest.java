package entity.object;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;

public class OBJ_Chest extends Entity {
	
	public static final String objName = "Chest";
	
	public OBJ_Chest(GamePanel gp) {
		super(gp);
		
		type = type_obstacle_i;
		name = objName;
		
		hookGrabbable = true;
		collision = true;
		
		hitbox = new Rectangle(4, 16, 40, 32);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}	
	public OBJ_Chest(GamePanel gp, Entity loot) {
		super(gp);
		this.loot = loot;
		
		type = type_obstacle_i;
		name = objName;
		
		hookGrabbable = true;
		collision = true;
		
		hitbox = new Rectangle(4, 16, 40, 32);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}	
	
	public void getImage() {
		image1 = setup("/objects/OBJ_CHEST");
		image2 = setup("/objects/OBJ_CHEST_OPENED");
		down1 = image1;
	}
	
	public void setLoot(Entity loot) {
		this.loot = loot;
	}
	
	public void interact() {		
		if (!opened && gp.player.direction.equals("up")) {	
			gp.player.attackCanceled = true;
			playOpenSE();
			opened = true;	
			down1 = image2;
			gp.player.getObject(loot);	
		}
	}
	
	public void playOpenSE() {
		gp.playSE(4, 3);
	}
}