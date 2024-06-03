package object;

import entity.Entity;
import main.GamePanel;

public class ITM_Bomb extends Entity {
	
	GamePanel gp;
	
	public ITM_Bomb(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_item;
		name = "Bomb";
		description = "[" + name + "]\nEquip to blow things up!";
		down1 = setup("/objects/ITEM_BOMB");
	}	
}