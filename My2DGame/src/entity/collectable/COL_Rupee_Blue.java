package entity.collectable;

import application.GamePanel;
import entity.Entity;

public class COL_Rupee_Blue extends Entity {

	public static final String colName = "Blue Rupee";
	
	public COL_Rupee_Blue(GamePanel gp) {
		super(gp);
		
		collision = false;
		
		type = type_collectable;
		name = colName;
		value = 5;
		lifeDuration = 60 * 6; // REMOVE AFTER 6 SECONDS
	}
	
	public void getImage() {
		down1 = setup("/collectables/rupee_blue");
	}
	
	public boolean use(Entity user) {	
		gp.ui.rupeeCount = user.rupees + value;
		return true;
	}
}