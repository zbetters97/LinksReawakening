package item;

import entity.Entity;
import main.GamePanel;
import projectile.PRJ_Boomerang;

public class ITM_Boomerang extends Entity {

	GamePanel gp;
	public static final String itmName = "Boomerang";
	
	public ITM_Boomerang(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_item;
		name = itmName;
		description = "[" + name + "]\nEquip to pull in far away\nitems!";
		price = 40;
		down1 = setup("/objects/ITEM_BOOMERANG");
		
		projectile = new PRJ_Boomerang(gp);
	}
	
	public boolean use(Entity user) {
		if (!projectile.alive && user.shotAvailableCounter == 30) { 			
						
			projectile.set(user.worldX, user.worldY, user.direction, true, user);			
			addProjectile(projectile);
						
			user.shotAvailableCounter = 0;	
		}			
		return true;
	}
}