package entity.item;

import entity.Entity;
import main.GamePanel;
import projectile.PRJ_Arrow;

public class ITM_Bow extends Entity {

	GamePanel gp;
	public static final String itmName = "Hylian Bow";
	
	public ITM_Bow(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_item;
		name = itmName;
		description = "[" + name + "]\nEquip to fire an arrow!";
		down1 = setup("/items/ITEM_BOW");
		
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