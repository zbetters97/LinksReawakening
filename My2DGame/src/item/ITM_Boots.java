package object;

import entity.Entity;
import main.GamePanel;

public class ITM_Boots extends Entity {
	
	GamePanel gp;
	
	public ITM_Boots(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_item;
		name = "Boots";
		description = "[" + name + "]\nEquip to run fast!";
		down1 = setup("/objects/ITEM_BOOTS");
	}	
	
	public void use() {
		gp.player.running = true;
	}
	
	public void playSE() {
		gp.playSE(3, 6);
	}
}