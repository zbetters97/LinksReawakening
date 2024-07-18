package entity.item;

import application.GamePanel;
import entity.Entity;

public class ITM_Lantern extends Entity {

	public static final String itmName = "Old Lantern";
	
	public ITM_Lantern(GamePanel gp) {
		super(gp);

		name = itmName;
		description = "[" + name + "]\nEquip to light up the\ndarkness!";
//		lightRadius = 300;		
	}
	
	public void getImage() {
		down1 = setup("/items/ITEM_LANTERN");
	}
	
	public void use() {
		
	}
	
	public void playSE() {
		
	}
}