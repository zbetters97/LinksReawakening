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
		image = down1 = setup("/items/shovel");
	}
	
	public boolean use(Entity user) {
		if (user.action != Action.DIGGING) {
			playSE();
			user.action = Action.DIGGING;
		}
		
		return true;
	}
	
	public void playSE() {
		gp.playSE(5, 1);
	}
}