package entity.collectable;

import application.GamePanel;
import entity.Entity;

public class COL_Arrow extends Entity {

	public static final String colName = "COL Arrow";
	GamePanel gp;
	
	public COL_Arrow(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_collectable;
		name = colName;
		value = 1;
		lifeDuration = 60 * 6; // REMOVE AFTER 6 SECONDS
		
		collision = false;
		
		down1 = setup("/objects/PRJ_ARROWS", gp.tileSize - 15, gp.tileSize - 15);
		image1 = down1;
	}
	
	public boolean use(Entity user) {
		playSE();
		user.arrows += value;
		return true;
	}
	public void playSE() {
		gp.playSE(6, 2);	
	}
}