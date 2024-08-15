package entity.object;

import application.GamePanel;
import entity.Entity;

public class OBJ_Door_Closed extends Entity {
	
	public static final String objName = "Closed Door";
	private int openCounter = 0;
	
	public OBJ_Door_Closed(GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.worldX = worldX *= gp.tileSize;
		this.worldY = worldY *= gp.tileSize;
					
		type = type_obstacle_i;
		name = objName;
		direction = "down";
		collision = true;
	}	
	
	public void getImage() {
		up1 = setup("/objects/door_closed_up_1");
		up2 = setup("/objects/door_closed_up_2");
		down1 = setup("/objects/door_closed_down_1");
		down2 = setup("/objects/door_closed_down_2");
		left1 = setup("/objects/door_closed_left_1");
		left2 = setup("/objects/door_closed_left_2");
		right1 = setup("/objects/door_closed_right_1");
		right2 = setup("/objects/door_closed_right_2");
	}
	
	public void update() {
		if (opening) {
			spriteNum = 2;
			open();
		}
		if (closing) {
			spriteNum = 2;
			close();
		}
	}
	
	public void open() {
		openCounter++;
		if (openCounter == 15) {
			openCounter = 0;			
			spriteNum = 1;
			opening = false;
			this.alive = false;
		}
	}
	
	public void close() {
		openCounter++;
		if (openCounter == 15) {
			openCounter = 0;
			spriteNum = 1;
			closing = false;
		}
	}
		
	public void playOpenSE() {
		gp.playSE(4, 11);
	}
	public void playCloseSE() {
		gp.playSE(4, 12);
	}
}