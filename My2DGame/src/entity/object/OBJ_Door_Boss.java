package entity.object;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;

public class OBJ_Door_Boss extends Entity {
	
	public static final String objName = "Boss Door";
	GamePanel gp;
	
	public OBJ_Door_Boss(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_obstacle;
		name = objName;
		direction = "down";
		
		up1 = setup("/objects/door_boss_down");
		down1 = up1;
		left1 = up1;
		right1 = up1;
		
		hitbox = new Rectangle(0, 16, 48, 40);
		collision = true;
	}	
	
	public void interact() {		
		if (gp.player.boss_key > 0) {
			playOpenSE();
			gp.player.boss_key--;
			this.alive = false;
		}		
	}
	
	public void playOpenSE() {
		gp.playSE(3, 14);
	}
	public void playCloseSE() {
		gp.playSE(3, 18);
	}
}