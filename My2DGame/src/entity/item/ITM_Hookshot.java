package entity.item;

import application.GamePanel;
import entity.Entity;
import entity.projectile.PRJ_Hookshot;

public class ITM_Hookshot extends Entity {

	public static final String itmName = "Hookshot";
	
	public ITM_Hookshot(GamePanel gp) {
		super(gp);

		type = type_item;
		name = itmName;		
		description = "[" + name + "]\nEquip to grab things!";
		getDescription = "Use it to pull yourself towards certain\nobjects or grab far away items!";
		
		projectile = new PRJ_Hookshot(gp);
	}	
	
	public void getImage() {
		down1 = setup("/items/ITEM_Hookshot");
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
	public void playSE() {
		gp.playSE(5, 8);
	}
}