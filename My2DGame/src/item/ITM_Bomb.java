package object;

import entity.Entity;
import main.GamePanel;

public class ITM_Bomb extends Entity {
	
	GamePanel gp;
	
	public ITM_Bomb(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_item;
		name = "Bomb";
		description = "[" + name + "]\nEquip to blow things up!";
		down1 = setup("/objects/ITEM_BOMB");
		
		projectile = new PRJ_Bomb(gp);
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
		gp.playSE(3, 8);
	}
}