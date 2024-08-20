package entity.object;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;

public class OBJ_Chest extends Entity {
	
	public static final String objName = "Chest";
	
	public OBJ_Chest(GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.worldX = worldX *= gp.tileSize;
		this.worldY = worldY *= gp.tileSize;
		
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
	public OBJ_Chest(GamePanel gp, int worldX, int worldY, Entity loot) {
		super(gp);
		this.worldX = worldX *= gp.tileSize;
		this.worldY = worldY *= gp.tileSize;
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
		image1 = down1 = setup("/objects/OBJ_CHEST");
		image2 = setup("/objects/OBJ_CHEST_OPENED");
	}
	
	public void setLoot(Entity loot) {
		this.loot = loot;
	}
	
	public void interact() {	
		if (!opened && gp.player.direction.equals("up")) {	
			playOpenSE();
			opened = true;	
			down1 = image2;
			gp.player.getObject(loot);	
		}
	}
	
	public void playOpenSE() {
		gp.playSE(4, 10);
	}
}