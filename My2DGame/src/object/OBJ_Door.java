package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {
	
	GamePanel gp;
	
	public OBJ_Door(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Door";
		down1 = setup("/objects/OBJ_DOOR");
		
		collision = true;
		
		hitBox.x = 0;
		hitBox.y = 16;
		hitBox.width = 48;
		hitBox.height = 32;
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
	}	
}