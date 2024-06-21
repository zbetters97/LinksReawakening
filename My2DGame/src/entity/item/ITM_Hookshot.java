package entity.item;

import entity.Entity;
import entity.projectile.PRJ_Hookshot;
import main.GamePanel;

public class ITM_Hookshot extends Entity {

	GamePanel gp;
	public static final String itmName = "Hookshot";
	
	public ITM_Hookshot(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_item;
		name = itmName;
		description = "[" + name + "]\nEquip to grab things!";
		down1 = setup("/items/ITEM_Hookshot");
		
		projectile = new PRJ_Hookshot(gp);
	}	
	
	public void use() {
		if (!projectile.alive && gp.player.shotAvailableCounter == 30) { 			
							
			projectile.set(gp.player.worldX, gp.player.worldY, gp.player.direction, true, gp.player);			
			addProjectile(projectile);
						
			gp.player.shotAvailableCounter = 0;	
		}		
	}
	public void playSE() {
		gp.playSE(3, 5);
	}
}