package collectable;

import entity.Entity;
import main.GamePanel;

public class COL_Rupee_Red extends Entity {

	public static final String colName = "Red Rupee";
	GamePanel gp;
	
	public COL_Rupee_Red(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_collectable;
		name = colName;
		value = 20;
		
		down1 = setup("/objects/COL_RUPEE_RED");
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