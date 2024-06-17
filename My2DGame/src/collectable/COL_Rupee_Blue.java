package collectable;

import entity.Entity;
import main.GamePanel;

public class COL_Rupee_Blue extends Entity {

	public static final String colName = "Blue Rupee";
	GamePanel gp;
	
	public COL_Rupee_Blue(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_collectable;
		name = colName;
		value = 5;
		lifeDuration = 60 * 6; // REMOVE AFTER 6 SECONDS
		
		down1 = setup("/objects/COL_RUPEE_BLUE");
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