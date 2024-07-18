package entity.object;

import application.GamePanel;
import entity.Entity;

public class OBJ_Tent extends Entity {
	
	public static final String objName = "Tent";
	
	public OBJ_Tent(GamePanel gp) {
		super(gp);
		
		type = type_obstacle_i;
		name = objName;
		collision = true;
	}	
	
	public void getImage() {
		down1 = setup("/objects/OBJ_TENT");
	}
	
	public void interact() {
		gp.player.attackCanceled = true;
		gp.gameState = gp.sleepState;
	}
}