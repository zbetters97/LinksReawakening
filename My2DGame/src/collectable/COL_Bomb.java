package collectable;

import entity.Entity;
import main.GamePanel;

public class COL_Bomb extends Entity {

	public static final String colName = "Bomb";
	GamePanel gp;
	
	public COL_Bomb(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_collectable;
		name = colName;
		value = 1;
		lifeDuration = 60 * 6; // REMOVE AFTER 6 SECONDS
		
		down1 = setup("/projectile/bomb_down_1");
	}
	
	public boolean use(Entity user) {
		gp.playSE(1, 6);
		gp.ui.addMessage("Bombs +" + value + "!");
		user.bombs += value;
		return true;
	}
}