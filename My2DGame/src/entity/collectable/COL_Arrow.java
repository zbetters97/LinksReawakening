package entity.collectable;

import application.GamePanel;
import entity.Entity;

public class COL_Arrow extends Entity {

	public static final String colName = "Arrow Collectable";
	
	public COL_Arrow(GamePanel gp) {
		super(gp);

		collision = false;
		
		type = type_collectable;
		name = colName;
		value = 1;
		lifeDuration = 60 * 6; // REMOVE AFTER 6 SECONDS
	}
	
	public void getImage() {
		image = down1 = setup("/collectables/arrow", 35, 35);
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