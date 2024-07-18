package entity.item;

import application.GamePanel;
import entity.Entity;
import entity.projectile.PRJ_Orb;

public class ITM_Rod extends Entity {

	public static final String itmName = "Magical Rod";
	
	public ITM_Rod(GamePanel gp) {
		super(gp);

		type = type_item;
		name = itmName;
		description = "[" + name + "]\nEquip to control enemies!";
		
		swingSpeed1 = 3;
		swingSpeed2 = 15;
		
		attackbox.width = 36;
		attackbox.height = 36;
		
		projectile = new PRJ_Orb(gp);
	}
	
	public void getImage() {
		down1 = setup("/items/ITEM_ROD");
	}
	
	public void use() {		

		gp.player.action = Action.SWINGING;
				
		if (!projectile.alive && gp.player.shotAvailableCounter == 30 && 
				gp.player.capturedTarget == null) {					
			playSE();
			
			projectile.set(gp.player.worldX, gp.player.worldY, gp.player.direction, true, gp.player);			
			addProjectile(projectile);	
			
			gp.player.shotAvailableCounter = 0;	
		}	
		else if (gp.player.capturedTarget != null) {
			projectile.playSE();
			
			gp.player.capturedTarget.captured = false;
			gp.player.capturedTarget = null;
		}
	}	
	
	public void playSE() {
		gp.playSE(5, 10);
	}
}