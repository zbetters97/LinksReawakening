package entity.item;

import application.GamePanel;
import entity.Entity;

public class ITM_Shovel extends Entity {

	public static final String itmName = "Wooden Shovel";
	
	public ITM_Shovel(GamePanel gp) {
		super(gp);

		type = type_item;
		name = itmName;
		description = "[" + name + "]\nEquip to dig for treasure!";
		getDescription = "Use it on dirt patches to dig up burried\ntreasure!";
	}
	
	public void getImage() {
		down1 = setup("/items/ITEM_SHOVEL");
	}
	
	public void use() {
		if (gp.player.action != Action.DIGGING) {
			gp.player.action = Action.DIGGING;
			gp.player.attackCanceled = true;
			playSE();
		}
	}
	
	public void playSE() {
		gp.playSE(5, 1);
	}
}