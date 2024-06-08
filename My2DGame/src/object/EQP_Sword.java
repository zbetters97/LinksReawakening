package object;

import entity.Entity;
import main.GamePanel;

public class EQP_Sword extends Entity {

	GamePanel gp;
	
	public EQP_Sword(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_sword;		
		name = "Old Sword";
		description = "[" + name + "]\nA humble starter sword.\n+1 ATK";
				
		attackValue = 2;
		knockbackPower = 1;
		attackArea.width = 36;
		attackArea.height = 36;
		
		down1 = setup("/objects/ITEM_SWORD");
		image1 = down1;
	}
	
	public void playSE() {
		gp.playSE(3, 0);
	}
}