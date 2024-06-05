package object;

import entity.Entity;
import main.GamePanel;

public class COL_Rupee_Green extends Entity {

	GamePanel gp;
	
	public COL_Rupee_Green(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_collectable;
		name = "Green Rupee";
		value = 1;
		
		down1 = setup("/objects/COL_RUPEE_GREEN", gp.tileSize, gp.tileSize);
	}
	
	public void use(Entity user) {		
		gp.playSE(1, 5);
		gp.ui.addMessage("Rupees +" + value + "!");
		user.rupees += value;
	}
}