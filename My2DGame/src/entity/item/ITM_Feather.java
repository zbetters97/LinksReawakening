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
		getDescription = "Use it to jump over things!";
	}
	
	public void getImage() {
		down1 = setup("/items/feather");
	}
	
	public boolean use(Entity user) {
		if (user.action != Action.JUMPING) {
			playSE();
			gp.player.playGruntSE_1();
			
			user.safeWorldX = user.worldX;
			user.safeWorldY = user.worldY;
			user.onGround = false;			
			user.action = Action.JUMPING;			
			user.attackCanceled = true;			
		}
		
		return true;
	}	
	
	public void playSE() {
		gp.playSE(5, 6);
	}
}