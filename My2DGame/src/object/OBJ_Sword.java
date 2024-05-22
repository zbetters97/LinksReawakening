package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword extends Entity {

	public OBJ_Sword(GamePanel gp) {
		super(gp);
		
		name = "Old Sword";
		description = "[" + name + "]\nA humble starter sword.\n+1 ATK";
		down1 = setup("/objects/ITEM_SWORD", gp.tileSize, gp.tileSize);
		
		attackValue = 1;
	}
}