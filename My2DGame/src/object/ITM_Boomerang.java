package object;

import entity.Entity;
import main.GamePanel;

public class ITM_Boomerang extends Entity {

	GamePanel gp;
	
	public ITM_Boomerang(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_item;
		name = "Boomerang";
		description = "[" + name + "]\nEquip to pull in far away\nitems!";
		price = 40;
		down1 = setup("/objects/ITEM_BOOMERANG");
	}
}