package item;

import entity.Entity;
import main.GamePanel;

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
		if (!gp.player.digging) {
			gp.player.digging = true;
			gp.player.attackCanceled = true;
			playSE();
		}
	}
	
	public void playSE() {
		gp.playSE(3, 7);
	}
}