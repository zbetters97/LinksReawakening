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
		getDescription = "When equiped, press " + KeyEvent.getKeyText(gp.btn_X) + " while moving to"
				+ "\ntoss forward or while standing still to place down!";
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
				gp.player.action = Action.GRABBING;
			}
			
			if (user.bombs != -1) user.bombs--;
		}
		return true;
	}
	
	public void playSE() {
		gp.playSE(5, 4);
	}
}