package entity.item;

import java.awt.event.KeyEvent;

import application.GamePanel;
import entity.Entity;
import entity.projectile.PRJ_Arrow;

public class ITM_Bow extends Entity {

	public static final String itmName = "Hylian Bow";
	
	public ITM_Bow(GamePanel gp) {
		super(gp);

		type = type_item;
		name = itmName;		
		description = "[" + name + "]\nEquip to fire an arrow!";
		getDescription = "Press and hold " + KeyEvent.getKeyText(gp.btn_X) + 
				" to charge its power!\nRelease " + KeyEvent.getKeyText(gp.btn_X) + " to fire an arrow!";	
	}
	
	public void getImage() {
		image = down1 = setup("/items/bow");
	}
	
	public boolean setCharge(Entity user) {
		
		if (user.arrows > 0) {	
			
			if (user.charge < 120) user.charge++;
			
			user.action = Action.AIMING; 
			
			return true;
		}
		else {
			return false;
		}		
	}
	
	public boolean use(Entity user) {	

		if (user.arrows > 0) {			
			playSE();
			
			projectile = new PRJ_Arrow(gp);		
			
			if (80 > user.charge && user.charge >= 40) {
				projectile.attack++;
				projectile.speed += 2;
			}
			else if (120 > user.charge && user.charge >= 80) {
				projectile.attack += 2;
				projectile.speed += 4;
			}
			else if (user.charge >= 120) {
				projectile.attack += 3;
				projectile.speed += 6;
			}				
			
			projectile.set(user.worldX, user.worldY, user.direction, true, user);		
			addProjectile(projectile);	
			
			if (user.arrows != -1) user.arrows--;	
			
			user.action = Action.IDLE; 				
			user.charge = 0;
		}
		
		return true;
	}	
	
	public void playChargeSE() {
		gp.playSE(4, 1);
	}	
	public void playSE() {
		gp.playSE(5, 7);
	}
}