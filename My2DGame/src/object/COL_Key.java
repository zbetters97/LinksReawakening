package object;

import entity.Entity;
import main.GamePanel;

public class COL_Key extends Entity {
	
	GamePanel gp;

	public COL_Key(GamePanel gp) {		
		super(gp);	
		this.gp = gp;
		
		name = "Key";
		description = "[" + name + "]\nThis can unlock a door.\nSingle-use only.";
		
		down1 = setup("/objects/ITEM_KEY");
	}	
}