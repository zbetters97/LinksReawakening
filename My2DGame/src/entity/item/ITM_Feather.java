package entity.item;

import application.GamePanel;
import entity.Entity;

public class ITM_Feather extends Entity {

	public static final String itmName = "Rito Feather";
	
	public ITM_Feather(GamePanel gp) {
		super(gp);

		type = type_item;
		name = itmName;		
		description = "[" + name + "]\nEquip to jump over things!";
	}
	
	public void getImage() {
		down1 = setup("/items/ITEM_FEATHER");
	}
	
	public void use() {
		if (gp.player.action != Action.JUMPING) {
			gp.player.onGround = false;
			gp.player.safeWorldX = gp.player.worldX;
			gp.player.safeWorldY = gp.player.worldY;
			gp.player.action = Action.JUMPING;
			gp.player.attackCanceled = true;
			playSE();
		}
	}	
	
	public void playSE() {
		gp.playSE(5, 6);
	}
}