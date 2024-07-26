package entity.item;

import application.GamePanel;
import entity.Entity;
import entity.projectile.PRJ_Boomerang;

public class ITM_Boomerang extends Entity {

	public static final String itmName = "Hylian Boomerang";
	
	public ITM_Boomerang(GamePanel gp) {
		super(gp);

		type = type_item;
		name = itmName;
		description = "[" + name + "]\nEquip to pull in far away\nitems!";
		getDescription = "Use it to stun enemies or grab far away items!";
		price = 40;
		
		projectile = new PRJ_Boomerang(gp);
	}
	
	public void getImage() {
		down1 = setup("/items/ITEM_BOOMERANG");
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