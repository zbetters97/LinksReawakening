package entity.item;

import application.GamePanel;
import entity.Entity;

public class ITM_Cape extends Entity {

	public static final String itmName = "Rito Cape";
	
	public ITM_Cape(GamePanel gp) {
		super(gp);

		type = type_item;
		name = itmName;		
		description = "[" + name + "]\nEquip to soar over things!";
	}
	
	public void getImage() {
		down1 = setup("/items/ITEM_CAPE");
	}
	
	public void use() {
		if (gp.player.action != Action.SOARING) {
			gp.player.onGround = false;
			gp.player.safeWorldX = gp.player.worldX;
			gp.player.safeWorldY = gp.player.worldY;
			gp.player.action = Action.SOARING;
			gp.player.attackCanceled = true;
			gp.playSE(5, 6);
		}
	}	
	
	public void playSE() {
		gp.playSE(5, 9);
	}
}