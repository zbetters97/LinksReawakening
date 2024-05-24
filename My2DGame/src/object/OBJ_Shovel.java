package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shovel extends Entity {

	public OBJ_Shovel(GamePanel gp) {
		super(gp);

		type = type_axe;
		name = "Shovel";
		description = "[" + name + "]\nAn iron shovel.";
		down1 = setup("/objects/ITEM_Shovel", gp.tileSize, gp.tileSize);
		
		attackValue = 2;
		attackArea.width = 36;
		attackArea.height = 36;
	}
}