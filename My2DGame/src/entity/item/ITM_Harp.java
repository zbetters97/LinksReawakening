package entity.item;

import application.GamePanel;
import entity.Entity;

public class ITM_Harp extends Entity {
	
	public static final String itmName = "Harp";
	
	public ITM_Harp(GamePanel gp) {
		super(gp);
		
		type = type_item;
		name = itmName;
		description = "[" + name + "]\nEquip to play music!";
		getDescription = "";
	}	
	
	public void getImage() {
		image = down1 = setup("/items/harp");
	}
	
	public boolean use(Entity user) {
		user.action = Action.MUSIC;
		gp.gameState = gp.musicState;
		return true;
	}
}