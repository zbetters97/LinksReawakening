package entity.item;

import java.awt.event.KeyEvent;

import application.GamePanel;
import entity.Entity;

public class ITM_Cape extends Entity {

	public static final String itmName = "Rito Cape";
	
	public ITM_Cape(GamePanel gp) {
		super(gp);

		type = type_item;
		name = itmName;		
		description = "[" + name + "]\nEquip to soar over things!";
		getDescription = "Press " + KeyEvent.getKeyText(gp.button_item) + " to shoot a magical orb"
				+ " at enemies!\nWhen an enemy is controlled, press " + KeyEvent.getKeyText(gp.button_item)
				+ " again to release control!";
	}
	
	public void getImage() {
		down1 = setup("/items/cape");
	}
	
	public void use() {
		if (gp.player.action != Action.SOARING) {
			gp.playSE(5, 6);
			gp.player.playGruntSE_1();
			
			gp.player.onGround = false;
			gp.player.safeWorldX = gp.player.worldX;
			gp.player.safeWorldY = gp.player.worldY;
			gp.player.action = Action.SOARING;
			gp.player.attackCanceled = true;
		}
	}	
	
	public void playSE() {
		gp.playSE(5, 9);
	}
}