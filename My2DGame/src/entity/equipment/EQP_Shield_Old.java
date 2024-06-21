package entity.equipment;

import entity.Entity;
import main.GamePanel;

public class EQP_Shield_Old extends Entity {

	public static final String eqpName = "Old Shield";
	
	public EQP_Shield_Old(GamePanel gp) {
		super(gp);

		type = type_shield;
		name = eqpName;
		description = "[" + name + "]\nA humble starter shield.\n+1 DEF";
		defenseValue = 1;
		
		down1 = setup("/items/ITEM_SHIELD");
	}
}