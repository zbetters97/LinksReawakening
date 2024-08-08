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
		
		swingSpeed1 = 4;
		swingSpeed2 = 13;
		
		attackbox.width = 40;
		attackbox.height = 50;
	}
	
	public void getImage() {
		down1 = setup("/equipment/sword_master");
		image1 = down1;
	}
	
	public void playSE() {
		gp.playSE(4, 0);
	}
	public void playChargeSE() {
		gp.playSE(4, 12);
	}
}