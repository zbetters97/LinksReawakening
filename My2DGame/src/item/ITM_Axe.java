package item;

import entity.Entity;
import main.GamePanel;

public class ITM_Axe extends Entity {

	GamePanel gp;
	public static final String itmName = "Axe";
	
	public ITM_Axe(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_item;		
		name = itmName;
		description = "[" + name + "]\nEquip to chop down trees!";
		
		attack = 1;
		knockbackPower = 0;
		
		down1 = setup("/objects/ITEM_AXE");
	}
	
	public void use() {				
		gp.player.attacking = true;
		gp.player.chopping = true;
	}
	public void playSE() {
		gp.playSE(3, 0);
	}
}