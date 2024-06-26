package entity.equipment;

import application.GamePanel;
import entity.Entity;

public class EQP_Sword_Master extends Entity {

	public static final String eqpName = "Master Sword";
	
	public EQP_Sword_Master(GamePanel gp) {
		super(gp);
		
		type = type_equipment;	
		name = eqpName;
		description = "[" + name + "]\nThe sword of a true hero.";
				
		attackValue = 4;
		knockbackPower = 1;
		
		swingSpeed1 = 3;
		swingSpeed2 = 12;
		
		attackbox.width = 36;
		attackbox.height = 36;
		
		down1 = setup("/items/EQP_SWORD_MASTER");
		image1 = down1;
	}
	
	public void playSE() {
		gp.playSE(4, 0);
	}
}