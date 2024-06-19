package item;

import entity.Entity;
import main.GamePanel;
import projectile.PRJ_Orb;

public class ITM_Rod extends Entity {

	GamePanel gp;
	public static final String itmName = "Magical Rod";
	
	public ITM_Rod(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_item;
		name = itmName;
		description = "[" + name + "]\nEquip to control enemies!";
		down1 = setup("/items/ITEM_ROD");
		
		swingSpeed1 = 3;
		swingSpeed2 = 15;
		
		attackbox.width = 36;
		attackbox.height = 36;
		
		projectile = new PRJ_Orb(gp);
	}
	
	public boolean use(Entity user) {		

		gp.player.swinging = true;
		
		if (!projectile.alive && user.shotAvailableCounter == 30 && 
				gp.player.capturedTarget == null) {				
			playSE();
			
			projectile.set(user.worldX, user.worldY, user.direction, true, user);			
			addProjectile(projectile);	
			
			user.shotAvailableCounter = 0;	
		}	
		else if (gp.player.capturedTarget != null) {
			gp.player.capturedTarget.captured = false;
			gp.player.capturedTarget = null;
		}
		return true;
	}	
	
	public void playSE() {
		gp.playSE(3, 15);
	}
}