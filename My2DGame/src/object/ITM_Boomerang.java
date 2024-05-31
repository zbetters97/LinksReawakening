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
		description = "[" + name + "]\nIt'll always return to you!";
		price = 100;
		down1 = setup("/objects/ITEM_Boomerang");
	}
}