package entity.equipment;

import application.GamePanel;
import entity.Entity;

public class EQP_Sword_Old extends Entity {

	public static final String eqpName = "Kokiri Sword";
	
	public EQP_Sword_Old(GamePanel gp) {
		super(gp);
		
		type = type_equipment;	
		name = eqpName;
		description = "[" + name + "]\nA humble starter sword.";	
		attackValue = 4;
		knockbackPower = 0;
		
		swingSpeed1 = 4;
		swingSpeed2 = 13;
		
		attackbox.width = 44;
		attackbox.height = 42;
	}
	
	public void getImage() {
		image = down1 = setup("/equipment/sword_old");
	}
	
	public void playSE() {
		gp.playSE(4, 0);
	}
	public void playChargeSE() {
		gp.playSE(4, 12);
	}
}