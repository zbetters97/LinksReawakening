package object;

import entity.Entity;
import main.GamePanel;

public class ITM_Axe extends Entity {

	GamePanel gp;
	
	public ITM_Axe(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_item;		
		name = "Axe";
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