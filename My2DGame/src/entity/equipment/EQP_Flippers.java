package entity.equipment;

import application.GamePanel;
import entity.Entity;

public class EQP_Flippers extends Entity {

	public static final String eqpName = "Zora Flippers";
	
	public EQP_Flippers(GamePanel gp) {
		super(gp);

		type = type_equipment;
		name = eqpName;
		description = "[" + name + "]\nSwim through water!";
	}
	
	public void getImage() {
		image = down1 = setup("/equipment/flippers");
	}
}