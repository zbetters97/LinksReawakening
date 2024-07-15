package entity.collectable;

import application.GamePanel;
import entity.Entity;

public class COL_Bomb extends Entity {

	public static final String colName = "COL Bomb";
	GamePanel gp;
	
	public COL_Bomb(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_collectable;
		name = colName;
		value = 1;
		lifeDuration = 60 * 6; // REMOVE AFTER 6 SECONDS
		
		collision = false;
		
		down1 = setup("/projectile/bomb_down_1");
	}
	
	public boolean use(Entity user) {
		playSE();
		user.bombs += value;
		return true;
	}
	public void playSE() {
		gp.playSE(6, 2);	
	}
}