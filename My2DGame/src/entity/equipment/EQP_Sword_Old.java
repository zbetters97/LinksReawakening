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
		
		swingSpeed1 = 3;
		swingSpeed2 = 12;
		
		attackbox.width = 40;
		attackbox.height = 50;
	}
	
	public void getImage() {
		down1 = setup("/equipment/sword_old");
		image1 = down1;
	}
	
	public void playSE() {
		gp.playSE(4, 0);
	}
}