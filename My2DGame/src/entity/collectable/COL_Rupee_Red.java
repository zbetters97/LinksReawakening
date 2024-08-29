package entity.collectable;

import application.GamePanel;
import entity.Entity;

public class COL_Rupee_Red extends Entity {

	public static final String colName = "Red Rupee";
	
	public COL_Rupee_Red(GamePanel gp) {
		super(gp);
		
		collision = false;
		
		type = type_collectable;
		name = colName;
		value = 20;
		lifeDuration = 60 * 6; // REMOVE AFTER 6 SECONDS
	}
	
	public void getImage() {
		image = down1 = setup("/collectables/rupee_red");
	}
	
	public boolean use(Entity user) {		
		gp.ui.rupeeCount = user.rupees + value;
		return true;
	}
}