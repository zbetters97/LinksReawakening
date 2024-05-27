package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {

	public OBJ_Axe(GamePanel gp) {
		super(gp);
		
		type = type_axe;		
		name = "Iron Axe";
		description = "[" + name + "]\nAn iron axe that can chop down trees\n+1 ATK";
		down1 = setup("/objects/ITEM_AXE", gp.tileSize, gp.tileSize);
		
		attackValue = 1;
		attackArea.width = 36;
		attackArea.height = 36;
	}
}