package entity.item;

import java.awt.event.KeyEvent;

import application.GamePanel;
import entity.Entity;

public class ITM_Boots extends Entity {
	
	public static final String itmName = "Running Boots";
	
	public ITM_Boots(GamePanel gp) {
		super(gp);
		
		type = type_item;
		name = itmName;
		description = "[" + name + "]\nEquip to run fast!";
		getDescription = "Press and hold " + KeyEvent.getKeyText(gp.btn_X) + 
				" to run!";
	}	
	
	public void getImage() {
		image = down1 = setup("/items/boots");
	}
	
	public boolean use(Entity user) {
		user.action = Action.RUNNING;
		return true;
	}
	
	public void playSE() {
		gp.playSE(5, 3);
	}
}