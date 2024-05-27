package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shovel extends Entity {

	GamePanel gp;
	
	public OBJ_Shovel(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_axe;
		name = "Shovel";
		description = "[" + name + "]\nAn iron shovel.";
		down1 = setup("/objects/ITEM_Shovel", gp.tileSize, gp.tileSize);
		
		attackValue = 1;
		attackArea.width = 36;
		attackArea.height = 36;
	}
}