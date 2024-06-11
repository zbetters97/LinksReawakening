package item;

import entity.Entity;
import main.GamePanel;

public class ITM_Lantern extends Entity {

	GamePanel gp;
	public static final String itmName = "Lantern";
	
	public ITM_Lantern(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_light;
		name = itmName;
		description = "[" + name + "]\nEquip to light up the\ndarkness!";
		lightRadius = 300;		
		
		down1 = setup("/objects/ITEM_LANTERN", gp.tileSize, gp.tileSize);
	}
	
	public void use() {
		
	}
	
	public void playSE() {
		
	}
}