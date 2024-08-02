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
		getDescription = "Press and hold " + KeyEvent.getKeyText(gp.button_item) + 
				" to run!";
	}	
	
	public void getImage() {
		down1 = setup("/items/boots");
	}
	
	public void use() {
		gp.player.action = Action.RUNNING;
	}
	
	public void playSE() {
		gp.playSE(5, 3);
	}
}