package object;

import entity.Entity;
import main.GamePanel;

public class COL_Rupee_Red extends Entity {

	GamePanel gp;
	
	public COL_Rupee_Red(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_collectable;
		name = "Red Rupee";
		value = 20;
		
		down1 = setup("/objects/COL_RUPEE_RED");
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