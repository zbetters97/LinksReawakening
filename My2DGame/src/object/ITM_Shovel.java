package object;

import entity.Entity;
import main.GamePanel;

public class ITM_Shovel extends Entity {

	GamePanel gp;
	
	public ITM_Shovel(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_item;
		name = "Shovel";
		description = "[" + name + "]\nEquip to dig for treasure!";
		down1 = setup("/objects/ITEM_Shovel", gp.tileSize, gp.tileSize);
	}
}