package entity.item;

import application.GamePanel;
import entity.Entity;

public class ITM_Boots extends Entity {
	
	public static final String itmName = "Running Boots";
	
	public ITM_Boots(GamePanel gp) {
		super(gp);
		
		type = type_item;
		name = itmName;
		description = "[" + name + "]\nEquip to run fast!";
	}	
	
	public void getImage() {
		down1 = setup("/items/ITEM_BOOTS");
	}
	
	public void use() {
		gp.player.action = Action.RUNNING;
	}
	
	public void playSE() {
		gp.playSE(5, 3);
	}
}