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
		attackValue = 6;
		knockbackPower = 1;
		
		swingSpeed1 = 3;
		swingSpeed2 = 6;
		swingSpeed3 = 13;
		
		attackbox.width = 44;
		attackbox.height = 42;
	}
	
	public void getImage() {
		image = down1 = setup("/equipment/sword_master");
	}
	
	public void playSE() {
		gp.playSE(4, 0);
	}
	public void playChargeSE() {
		gp.playSE(4, 12);
	}
}