package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity {
	
	public static final String objName = "Tent";
	GamePanel gp;
	
	public OBJ_Tent(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_obstacle;
		name = objName;
		collision = true;
		
		down1 = setup("/objects/OBJ_TENT");
	}	
	
	public void interact() {	
		// PLAY SE
		gp.gameState = gp.sleepState;
	}
}