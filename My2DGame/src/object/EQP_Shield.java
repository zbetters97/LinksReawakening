package object;

import entity.Entity;
import main.GamePanel;

public class EQP_Shield extends Entity {

	public EQP_Shield(GamePanel gp) {
		super(gp);

		type = type_shield;
		name = "Old Shield";
		description = "[" + name + "]\nA humble starter shield.\n+1 DEF";
		down1 = setup("/objects/ITEM_SHIELD", gp.tileSize, gp.tileSize);
		
		defenseValue = 1;
	}
}