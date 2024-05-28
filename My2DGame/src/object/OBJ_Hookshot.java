package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Hookshot extends Entity {

	GamePanel gp;
	
	public OBJ_Hookshot(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_item;
		name = "Hookshot";
		description = "[" + name + "]\nPress G to fire the claw!";
		down1 = setup("/objects/ITEM_Hookshot");
	}
}