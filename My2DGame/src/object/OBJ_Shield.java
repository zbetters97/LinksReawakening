package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield extends Entity {

	public OBJ_Shield(GamePanel gp) {
		super(gp);

		name = "Old Shield";
		description = "[" + name + "]\nA humble starter shield.\n+1 DEF";
		down1 = setup("/objects/ITEM_SHIELD", gp.tileSize, gp.tileSize);
		
		defenseValue = 1;
	}
}