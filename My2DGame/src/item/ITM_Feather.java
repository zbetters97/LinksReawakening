package item;

import entity.Entity;
import main.GamePanel;

public class ITM_Feather extends Entity {

	GamePanel gp;
	public static final String itmName = "Rito Feather";
	
	public ITM_Feather(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_item;
		name = itmName;
		description = "[" + name + "]\nEquip to jump over things!";
		down1 = setup("/items/ITEM_FEATHER");
	}
	
	public void use() {
		if (!gp.player.jumping) {
			gp.player.jumping = true;
			gp.player.attackCanceled = true;
			playSE();
		}
	}	
	
	public void playSE() {
		gp.playSE(3, 11);
	}
}