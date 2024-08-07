package entity.item;

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
		getDescription = "Use it to soar through the air for a short time!";
		
		swingSpeed1 = 3;
		swingSpeed2 = 15;
		
		attackbox.width = 36;
		attackbox.height = 36;
		
		projectile = new PRJ_Orb(gp);
	}
	
	public void getImage() {
		down1 = setup("/items/rod");
	}
	
	public void use() {		

		gp.player.action = Action.SWINGING;
				
		if (!projectile.alive && gp.player.shotAvailableCounter == 30 && 
				gp.player.capturedTarget == null) {					
			playSE();
			
			int worldX = gp.player.worldX;
			int worldY = gp.player.worldY;
			
			// SHIFT ORB 
			switch (gp.player.direction) {
				case "up":
				case "upleft":
				case "upright": worldY -= 20; break;
				case "down":
				case "downleft":
				case "downright": worldY += 20; break;
				case "left": worldY += 8; worldX -= 20; break;
				case "right": worldY += 8; worldX += 20; break;
			}
			
			projectile.set(worldX, worldY, gp.player.direction, true, gp.player);			
			addProjectile(projectile);	
			
			gp.player.shotAvailableCounter = 0;	
		}	
		else if (gp.player.capturedTarget != null) {
			projectile.playSE();
			
			gp.player.capturedTarget.speed = gp.player.capturedTarget.defaultSpeed; 
			gp.player.capturedTarget.captured = false;
			gp.player.capturedTarget = null;
		}
	}	
	
	public void playSE() {
		gp.playSE(5, 10);
	}
}