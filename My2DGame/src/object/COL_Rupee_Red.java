package object;

import entity.Entity;
import main.GamePanel;

public class COL_Rupee_Red extends Entity {

	GamePanel gp;
	
	public COL_Rupee_Red(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_collectable;
		name = "Red Rupee";
		value = 20;
		down1 = setup("/objects/COL_RUPEE_RED", gp.tileSize, gp.tileSize);
	}
	
	public void use(Entity entity) {		
		gp.playSE(1, 5);
		gp.ui.addMessage("Rupees +" + value + "!");
		entity.rupees += value;
	}
}