package entity.item;

import java.awt.event.KeyEvent;

import application.GamePanel;
import entity.Entity;
import entity.projectile.PRJ_Orb;

public class ITM_Rod extends Entity {

	public static final String itmName = "Magical Rod";
	
	public ITM_Rod(GamePanel gp) {
		super(gp);

		type = type_item;
		name = itmName;
		description = "[" + name + "]\nEquip to control enemies!";
		getDescription = "Press " + KeyEvent.getKeyText(gp.button_item) + " to shoot a magical orb"
				+ " at enemies!\nWhen an enemy is controlled, press " + KeyEvent.getKeyText(gp.button_item)
				+ " again to release control!";
		
		swingSpeed1 = 6;
		swingSpeed2 = 15;
		
		attackbox.width = 36;
		attackbox.height = 36;
		
		projectile = new PRJ_Orb(gp);
	}
	
	public void getImage() {
		down1 = setup("/items/rod");
	}
	
	public boolean use(Entity user) {		

		user.action = Action.SWINGING;
				
		if (!projectile.alive && user.capturedTarget == null) {					
			playSE();
			
			int worldX = user.worldX;
			int worldY = user.worldY;
			
			// SHIFT ORB 
			switch (user.direction) {
				case "up":
				case "upleft":
				case "upright": worldY -= 20; break;
				case "down":
				case "downleft":
				case "downright": worldY += 20; break;
				case "left": worldY += 8; worldX -= 20; break;
				case "right": worldY += 8; worldX += 20; break;
			}
			
			projectile.set(worldX, worldY, user.direction, true, user);			
			addProjectile(projectile);	
		}	
		else if (user.capturedTarget != null) {
			projectile.playSE();
			
			user.capturedTarget.speed = user.capturedTarget.defaultSpeed; 
			user.capturedTarget.captured = false;
			user.capturedTarget = null;
		}
		
		return true;
	}	
	
	public void playSE() {
		gp.playSE(5, 10);
	}
}