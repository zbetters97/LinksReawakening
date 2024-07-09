package entity.object;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;

public class OBJ_Oneway extends Entity {
	
	public static final String objName = "Oneway Door";
	GamePanel gp;
	
	public OBJ_Oneway(GamePanel gp, String direction, boolean switchedOn) {
		super(gp);
		this.gp = gp;
		
		type = type_obstacle;
		name = objName;
		this.direction = direction;
		this.switchedOn = switchedOn;
		if (switchedOn) spriteNum = 1;
		else spriteNum = 2;	
		
		up1 = setup("/objects/oneway_open_up");
		up2 = setup("/objects/oneway_closed_up");
		down1 = setup("/objects/oneway_open_down");
		down2 = setup("/objects/oneway_closed_down");
		
		hitbox = new Rectangle(0, 16, 48, 40);
		collision = true;
	}	
		
	public void interact() {	
				
		if (switchedOn) {		
			// PLAYER MUST BE FACING DOOR
			switch (direction) {
				case "up": 
					if (gp.player.direction.equals("down") || 
							gp.player.direction.equals("downleft") ||
							gp.player.direction.equals("downright")) {
						playOpenSE();
						gp.player.worldY += (gp.tileSize * 3);
					}
					break;
				case "down": 
					if (gp.player.direction.equals("up") ||
							gp.player.direction.equals("upleft") ||
							gp.player.direction.equals("upright")) {
						playOpenSE();
						gp.player.worldY -= (gp.tileSize * 3);
					}
					break;
			}
		}
	}
	
	public void playSE() {
		gp.playSE(4, 4);
	}
	public void playCloseSE() {
		gp.playSE(4, 5);
	}
}