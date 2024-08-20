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
		getDescription = "Use it to soar through the air for a short time!";
	}
	
	public void getImage() {
		image = down1 = setup("/items/cape");
	}
	
	public boolean use(Entity user) {
		if (user.action != Action.SOARING) {
			gp.playSE(5, 6);
			gp.player.playGruntSE_1();

			user.safeWorldX = user.worldX;
			user.safeWorldY = user.worldY;
			user.onGround = false;			
			user.action = Action.SOARING;
		}
		
		return true;
	}	
	
	public void playSE() {
		gp.playSE(5, 9);
	}
}