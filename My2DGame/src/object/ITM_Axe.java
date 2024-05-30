package object;

import entity.Entity;
import main.GamePanel;

public class ITM_Axe extends Entity {

	public ITM_Axe(GamePanel gp) {
		super(gp);
		
		type = type_item;		
		name = "Iron Axe";
		description = "[" + name + "]\nEquip to chop down trees!";
		down1 = setup("/objects/ITEM_AXE", gp.tileSize, gp.tileSize);
	}
}