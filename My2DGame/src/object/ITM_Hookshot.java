package object;

import entity.Entity;
import main.GamePanel;

public class ITM_Hookshot extends Entity {

	GamePanel gp;
	
	public ITM_Hookshot(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_item;
		name = "Hookshot";
		description = "[" + name + "]\nEquip to grab things!";
		down1 = setup("/objects/ITEM_Hookshot");
	}
}