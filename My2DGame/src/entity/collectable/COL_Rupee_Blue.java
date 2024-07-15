package entity.collectable;

import application.GamePanel;
import entity.Entity;

public class COL_Rupee_Blue extends Entity {

	public static final String colName = "COL Blue Rupee";
	GamePanel gp;
	
	public COL_Rupee_Blue(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_collectable;
		name = colName;
		value = 5;
		lifeDuration = 60 * 6; // REMOVE AFTER 6 SECONDS
		
		collision = false;
		
		down1 = setup("/collectables/COL_RUPEE_BLUE");
	}
	
	public boolean use(Entity user) {	
		gp.ui.rupeeCount = user.rupees + value;
		return true;
	}
}