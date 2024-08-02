package entity.equipment;

import application.GamePanel;
import entity.Entity;

public class EQP_Shield extends Entity {

	public static final String eqpName = "Old Shield";
	
	public EQP_Shield(GamePanel gp) {
		super(gp);

		type = type_equipment;
		name = eqpName;
		description = "[" + name + "]\nA humble starter shield.";
	}
	
	public void getImage() {
		down1 = setup("/equipment/shield");
	}
}