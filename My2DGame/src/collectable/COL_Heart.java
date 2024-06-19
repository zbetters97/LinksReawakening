package collectable;

import entity.Entity;
import main.GamePanel;

public class COL_Heart extends Entity {
	
	public static final String colName = "COL Heart";
	GamePanel gp;
	
	public COL_Heart(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_collectable;		
		name = colName;
		value = 2;
		lifeDuration = 60 * 6; // REMOVE AFTER 6 SECONDS
		
		down1 = setup("/collectables/COL_HEART_FULL", gp.tileSize / 2, gp.tileSize / 2);
		image1 = down1;
		image2 = setup("/collectables/COL_HEART_HALF", gp.tileSize / 2, gp.tileSize / 2);
		image3 = setup("/collectables/COL_HEART_EMPTY", gp.tileSize / 2, gp.tileSize / 2);
	}
	
	public boolean use(Entity user) {
		playSE();
		user.life += value;
		return true;
	}
	public void playSE() {
		gp.playSE(1, 6);	
	}
}