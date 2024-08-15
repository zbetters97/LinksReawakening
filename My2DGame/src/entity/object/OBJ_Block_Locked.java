package entity.object;

import application.GamePanel;
import entity.Entity;

public class OBJ_Block_Locked extends Entity {
	
	public static final String objName = "Locked Block";
	
	public OBJ_Block_Locked(GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.worldX = worldX *= gp.tileSize;
		this.worldY = worldY *= gp.tileSize;
		
		type = type_obstacle_i;
		name = objName;
		direction = "down";
		collision = true;		
	}	
	
	public void getImage() {
		up1 = down1 = left1 = right1 = setup("/objects/OBJ_BLOCK_LOCK");
	}
	
	public void interact() {		
		if (gp.player.keys > 0) {
			playOpenSE();
			gp.player.keys--;
			this.alive = false;
		}		
	}
	
	public void playOpenSE() {
		gp.playSE(4, 11);
	}
}