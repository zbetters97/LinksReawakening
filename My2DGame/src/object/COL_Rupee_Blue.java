package object;

import entity.Entity;
import main.GamePanel;

public class COL_Rupee_Blue extends Entity {

	public static final String itmName = "Blue Rupee";
	GamePanel gp;
	
	public COL_Rupee_Blue(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_collectable;
		name = itmName;
		value = 5;
		
		down1 = setup("/objects/COL_RUPEE_BLUE");
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