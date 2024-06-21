package entity.item;

import entity.Entity;
import main.GamePanel;
import projectile.PRJ_Hookshot;

public class ITM_Hookshot extends Entity {

	GamePanel gp;
	public static final String itmName = "Hookshot";
	
	public ITM_Hookshot(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_item;
		name = itmName;
		description = "[" + name + "]\nEquip to grab things!";
		down1 = setup("/items/ITEM_Hookshot");
		
		projectile = new PRJ_Hookshot(gp);
	}	
	
	public boolean use(Entity user) {
		if (!projectile.alive && user.shotAvailableCounter == 30) { 			
							
			projectile.set(user.worldX, user.worldY, user.direction, true, user);			
			addProjectile(projectile);
						
			user.shotAvailableCounter = 0;	
		}		
		return true;
	}
	public void playSE() {
		gp.playSE(3, 5);
	}
}