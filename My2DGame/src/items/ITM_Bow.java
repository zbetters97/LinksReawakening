package object;

import entity.Entity;
import main.GamePanel;

public class ITM_Bow extends Entity {

	GamePanel gp;
	
	public ITM_Bow(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_item;
		name = "Bow";
		description = "[" + name + "]\nEquip to fire an arrow!";
		down1 = setup("/objects/ITEM_BOW");
		
		projectile = new PRJ_Arrow(gp);
	}
	
	public boolean use(Entity user) {				
		if (!projectile.alive && user.shotAvailableCounter == 30 && 
				projectile.hasResource(user)) {							
			
			playSE();
			
			projectile.set(user.worldX, user.worldY, user.direction, true, user);			
			addProjectile(projectile);	
			
			projectile.subtractResource(user);
			
			user.shotAvailableCounter = 0;	
		}	
		return true;
	}	
	
	public void playSE() {
		gp.playSE(3, 2);
	}
}