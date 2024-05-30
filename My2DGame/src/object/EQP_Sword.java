package object;

import entity.Entity;
import main.GamePanel;

public class EQP_Sword extends Entity {

	public EQP_Sword(GamePanel gp) {
		super(gp);
		
		type = type_sword;		
		name = "Old Sword";
		description = "[" + name + "]\nA humble starter sword.\n+1 ATK";
		down1 = setup("/objects/ITEM_SWORD", gp.tileSize, gp.tileSize);
		
		attackValue = 1;
		attackArea.width = 36;
		attackArea.height = 36;
	}
}