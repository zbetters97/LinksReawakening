package entity.object;

import application.GamePanel;
import entity.Entity;

public class OBJ_Tent extends Entity {
	
	public static final String objName = "Tent";
	
	public OBJ_Tent(GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.worldX = worldX *= gp.tileSize;
		this.worldY = worldY *= gp.tileSize;
		
		type = type_obstacle_i;
		name = objName;
		collision = true;
	}	
	
	public void getImage() {
		image = down1 = setup("/objects/OBJ_TENT");
	}
	
	public void interact() {
		gp.player.attackCanceled = true;
		gp.gameState = gp.sleepState;
	}
}