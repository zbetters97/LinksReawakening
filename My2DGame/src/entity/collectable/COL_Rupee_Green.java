package entity.collectable;

import application.GamePanel;
import entity.Entity;

public class COL_Rupee_Green extends Entity {

	public static final String colName = "COL Green Rupee";
	
	public COL_Rupee_Green(GamePanel gp) {
		super(gp);
		
		collision = false;
		
		type = type_collectable;
		name = colName;
		value = 1;
		lifeDuration = 60 * 6; // REMOVE AFTER 6 SECONDS
	}
	
	public void getImage() {
		down1 = setup("/collectables/COL_RUPEE_GREEN");
	}
	
	public boolean use(Entity user) {		
		gp.ui.rupeeCount = user.rupees + value;
		return true;
	}
}