package collectables;

import entity.Entity;
import main.GamePanel;

public class COL_Rupee_Green extends Entity {

	public static final String itmName = "Collectable Green Rupee";
	GamePanel gp;
	
	public COL_Rupee_Green(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_collectable;
		name = itmName;
		value = 1;
		lifeDuration = 60 * 6; // REMOVE AFTER 6 SECONDS
		
		down1 = setup("/objects/COL_RUPEE_GREEN");
	}
	
	public boolean use(Entity user) {		
		//playSE();
		gp.ui.rupeeCount = user.rupees + value;
		return true;
	}
	public void playSE() {
		gp.playSE(1, 5);
	}
}