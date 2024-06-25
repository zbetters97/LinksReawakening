package entity.item;

import application.GamePanel;
import entity.Entity;

public class ITM_Shovel extends Entity {

	GamePanel gp;
	public static final String itmName = "Wooden Shovel";
	
	public ITM_Shovel(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_item;
		name = itmName;
		description = "[" + name + "]\nEquip to dig for treasure!";
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