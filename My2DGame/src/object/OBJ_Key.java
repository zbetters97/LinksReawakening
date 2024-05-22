package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
	
	GamePanel gp;

	public OBJ_Key(GamePanel gp) {		
		super(gp);	
		
		name = "Key";
		description = "[" + name + "]\nThis can unlock a door.\nSingle-use only.";
		down1 = setup("/objects/ITEM_KEY");
	}	
}