package entity.item;

import application.GamePanel;
import entity.Entity;
import entity.projectile.PRJ_Arrow;

public class ITM_Bow extends Entity {

	GamePanel gp;
	public static final String itmName = "Hylian Bow";
	
	public ITM_Bow(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_item;
		name = itmName;
		
		charge = 0;
		
		description = "[" + name + "]\nEquip to fire an arrow!";
		down1 = setup("/items/ITEM_BOW");
		
		projectile = new PRJ_Arrow(gp);
	}
	
	public void setCharge(Entity user) {
		
		if (!projectile.alive && user.shotAvailableCounter == 30 && 
				projectile.hasResource(user)) {	
			
			if (charge < 120) charge++;
									
			gp.player.action = Action.AIMING; 
		}
	}
	
	public boolean use(Entity user) {	
		
		if (!projectile.alive && user.shotAvailableCounter == 30 && 
				projectile.hasResource(user)) {			
			playSE();
			
			if (user == gp.player) {	
				
				gp.player.action = Action.IDLE; 	
				
				if (80 > charge && charge >= 40) {
					projectile.attack++;
					projectile.speed += 2;
				}
				else if (120 > charge && charge >= 80) {
					projectile.attack += 2;
					projectile.speed += 4;
				}
				else if (charge >= 120) {
					projectile.attack += 3;
					projectile.speed += 6;
				}				
				
				charge = 0;	
			}
			else {
				projectile.attack = 2;
				projectile.speed = 10;
			}
			
			projectile.set(user.worldX, user.worldY, user.direction, true, user);		
			addProjectile(projectile);	
			
			projectile.subtractResource(user);
			
			user.shotAvailableCounter = 0;					
		}
		
		return true;
	}	
	
	public void playSE() {
		gp.playSE(5, 7);
	}
}