package entity.object;

import application.GamePanel;
import entity.Entity;

public class OBJ_Block_Locked extends Entity {
	
	public static final String objName = "Locked Block";
	
	public OBJ_Block_Locked(GamePanel gp) {
		super(gp);
		
		type = type_obstacle;
		name = objName;
		direction = "down";
		collision = true;		
	}	
	
	public void getImage() {
		up1 = setup("/objects/OBJ_BLOCK_LOCK");
		down1 = up1;
		left1 = up1;
		right1 = up1;
	}
	
	public void interact() {		
		if (gp.player.keys > 0) {
			playOpenSE();
			gp.player.keys--;
			this.alive = false;
		}		
	}
	
	public void playOpenSE() {
		gp.playSE(4, 4);
	}
}