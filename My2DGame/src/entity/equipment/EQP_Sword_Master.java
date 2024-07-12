package entity.equipment;

import application.GamePanel;
import entity.Entity;

public class EQP_Sword_Master extends Entity {

	public static final String eqpName = "Master Sword";
	
	public EQP_Sword_Master(GamePanel gp) {
		super(gp);
		
		type = type_equipment;	
		name = eqpName;
		description = "[" + name + "]\nThe sword of the hero of legends.";
				
		attackValue = 3;
		knockbackPower = 1;
		
		swingSpeed1 = 3;
		swingSpeed2 = 12;
		
		attackbox.width = 40;
		attackbox.height = 32;
		
		down1 = setup("/items/EQP_SWORD_MASTER");
		image1 = down1;
	}
	
	public void playSE() {
		gp.playSE(4, 0);
	}
}