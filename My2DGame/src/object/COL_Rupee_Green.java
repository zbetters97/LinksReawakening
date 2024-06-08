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
		
		down1 = setup("/objects/COL_RUPEE_GREEN");
	}
	
	public boolean use(Entity user) {		
		playSE();
		user.rupees += value;
		return true;
	}
	public void playSE() {
		gp.playSE(1, 5);
	}
}