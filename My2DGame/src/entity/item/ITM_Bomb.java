package entity.item;

import application.GamePanel;
import entity.Entity;
import entity.projectile.PRJ_Bomb;

public class ITM_Bomb extends Entity {
	
	public static final String itmName = "Bombs";
	
	public ITM_Bomb(GamePanel gp) {
		super(gp);
		
		type = type_item;
		name = itmName;
		description = "[" + name + "]\nEquip to blow things up!";
		
		projectile = new PRJ_Bomb(gp);
	}	
	
	public void getImage() {
		down1 = setup("/items/ITEM_BOMB");
	}
	
	public boolean use(Entity user) {
		
		if (!projectile.alive && user.shotAvailableCounter == 30 && 
				projectile.hasResource(user)) {
			
			playSE();
			
			projectile.set(user.worldX, user.worldY, user.direction, true, user);
			
			if (user == gp.player) {
				gp.player.grabEntity(projectile);
			}
			
			addProjectile(projectile);	
			
			projectile.subtractResource(user);
			
			user.shotAvailableCounter = 0;
		}
		return true;
	}
	
	public void playSE() {
		gp.playSE(5, 4);
	}
}