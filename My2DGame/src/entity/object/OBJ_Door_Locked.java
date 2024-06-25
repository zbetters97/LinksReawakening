package entity.object;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;

public class OBJ_Door_Locked extends Entity {
	
	public static final String objName = "Locked Door";
	GamePanel gp;
	
	public OBJ_Door_Locked(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_obstacle;
		name = objName;
		direction = "down";
		
		up1 = setup("/objects/door_locked_up");
		down1 = setup("/objects/door_locked_down");
		left1 = setup("/objects/door_locked_left");
		right1 = setup("/objects/door_locked_right");
		
		hitbox = new Rectangle(0, 16, 48, 40);
		collision = true;
	}	
	
	public void interact() {	
				
		boolean respond = false;
		
		// PLAYER MUST BE FACING DOOR
		switch (direction) {
			case "up": 
				if (gp.player.direction.equals("down") || 
						gp.player.direction.equals("downleft") ||
						gp.player.direction.equals("downright")) respond = true;
				break;
			case "down": 
				if (gp.player.direction.equals("up") ||
						gp.player.direction.equals("upleft") ||
						gp.player.direction.equals("upright")) respond = true;			
				break;
			case "left": 
				if (gp.player.direction.equals("right")) respond = true;			
				break;
			case "right": 
				if (gp.player.direction.equals("left")) respond = true;			
				break;
		}
		
		if (respond) {		
			if (gp.player.keys > 0) {
				playOpenSE();
				gp.player.keys--;
				this.alive = false;
			}
		}
	}
	
	public void playOpenSE() {
		gp.playSE(4, 4);
	}
	public void playCloseSE() {
		gp.playSE(4, 5);
	}
}