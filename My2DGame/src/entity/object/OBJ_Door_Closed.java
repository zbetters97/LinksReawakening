package entity.object;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;

public class OBJ_Door_Closed extends Entity {
	
	public static final String objName = "Iron Door";
	GamePanel gp;
	
	public OBJ_Door_Closed(GamePanel gp) {
		super(gp);
		this.gp = gp;
					
		type = type_obstacle;
		name = objName;
		direction = "down";
		
		up1 = setup("/objects/door_closed_up");
		down1 = setup("/objects/door_closed_down");
		left1 = setup("/objects/door_closed_left");
		right1 = setup("/objects/door_closed_right");
		
		hitbox = new Rectangle(0, 0, 48, 48);
		collision = true;
	}	
		
	public void playOpenSE() {
		gp.playSE(3, 14);
	}
	public void playCloseSE() {
		gp.playSE(3, 18);
	}
}