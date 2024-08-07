package entity.item;

import java.awt.event.KeyEvent;

import application.GamePanel;
import entity.Entity;
import entity.projectile.PRJ_Bomb;

public class ITM_Bomb extends Entity {
	
	public static final String itmName = "Bomb Item";
	
	public ITM_Bomb(GamePanel gp) {
		super(gp);
		
		type = type_item;
		name = itmName;
		description = "[" + name + "]\nEquip to blow things up!";
		getDescription = "When equipped, press " + KeyEvent.getKeyText(gp.button_grab) + 
				" to throw a bomb\nor " + KeyEvent.getKeyText(gp.button_item) + " to set it down!";
	}	
	
	public void getImage() {
		down1 = setup("/items/bomb");
	}
	
	public boolean use(Entity user) {
				
		if (user.bombs > 0) {			
			playSE();
			
			projectile = new PRJ_Bomb(gp);				
			projectile.set(user.worldX, user.worldY, user.direction, true, user);		
			addProjectile(projectile);	
			
			if (user == gp.player) gp.player.grabEntity(projectile);			
			
			if (user.bombs != -1) user.bombs--;
		}
		return true;
	}
	
	public void playSE() {
		gp.playSE(5, 4);
	}
}