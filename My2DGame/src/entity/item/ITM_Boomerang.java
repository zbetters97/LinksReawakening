package entity.item;

import application.GamePanel;
import entity.Entity;
import entity.projectile.PRJ_Boomerang;

public class ITM_Boomerang extends Entity {

	GamePanel gp;
	public static final String itmName = "Hylian Boomerang";
	
	public ITM_Boomerang(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_item;
		name = itmName;
		description = "[" + name + "]\nEquip to pull in far away\nitems!";
		price = 40;
		down1 = setup("/items/ITEM_BOOMERANG");
		
		projectile = new PRJ_Boomerang(gp);
	}
	
	public boolean use(Entity user) {
		if (!projectile.alive && user.shotAvailableCounter == 30) { 
			
			projectile.set(user.worldX, user.worldY, user.direction, true, user);			
			addProjectile(projectile);
						
			user.shotAvailableCounter = 0;	
			
			user.action = Action.THROWING;
		}			
		return true;
	}
}