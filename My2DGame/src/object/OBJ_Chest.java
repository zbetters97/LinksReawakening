package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {
	
	GamePanel gp;
	
	public OBJ_Chest(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Chest";
		down1 = setup("/objects/OBJ_CHEST");
	}	
}