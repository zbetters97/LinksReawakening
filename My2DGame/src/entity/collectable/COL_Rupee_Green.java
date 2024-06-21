package entity.collectable;

import entity.Entity;
import main.GamePanel;

public class COL_Rupee_Green extends Entity {

	public static final String colName = "COL Green Rupee";
	GamePanel gp;
	
	public COL_Rupee_Green(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_collectable;
		name = colName;
		value = 1;
		lifeDuration = 60 * 6; // REMOVE AFTER 6 SECONDS
		
		down1 = setup("/collectables/COL_RUPEE_GREEN");
	}
	
	public boolean use(Entity user) {		
		gp.ui.rupeeCount = user.rupees + value;
		return true;
	}
}