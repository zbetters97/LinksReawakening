package object;

import entity.Entity;
import main.GamePanel;

public class ITM_Shovel extends Entity {

	GamePanel gp;
	
	public ITM_Shovel(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_item;
		name = "Shovel";
		description = "[" + name + "]\nEquip to dig for treasure!";
		down1 = setup("/objects/ITEM_SHOVEL");
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