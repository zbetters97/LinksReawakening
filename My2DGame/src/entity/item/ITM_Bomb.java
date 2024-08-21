package entity.item;

import java.awt.event.KeyEvent;

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
		getDescription = "Press + " + KeyEvent.getKeyText(gp.btn_X) + " to place it down and\n"
				+ KeyEvent.getKeyText(gp.btn_A) + " to pick it up!";
	}	
	
	public void getImage() {		
		image = down1 = setup("/items/bomb");
	}
	
	public boolean use(Entity user) {
				
		if (user.bombs > 0) {			
			playSE();
			
			projectile = new PRJ_Bomb(gp);				
			projectile.set(user.worldX, user.worldY, user.direction, true, user);		
			addProjectile(projectile);	
			
			if (user == gp.player) {
				gp.player.grabbedObject = projectile;
			}
			
			if (user.bombs != -1) user.bombs--;
		}
		return true;
	}
	
	public void playSE() {
		gp.playSE(5, 4);
	}
}