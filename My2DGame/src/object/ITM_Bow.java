package object;

import entity.Entity;
import main.GamePanel;

public class ITM_Bow extends Entity {

	GamePanel gp;
	
	public ITM_Bow(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_item;
		name = "Hylian Bow";
		description = "[" + name + "]\nEquip to fire an arrow!";
		price = 15;
		down1 = setup("/objects/ITEM_BOW");
	}
}