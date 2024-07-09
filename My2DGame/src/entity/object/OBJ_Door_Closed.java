package entity.object;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;

public class OBJ_Door_Closed extends Entity {
	
	public static final String objName = "Iron Door";
	private int openCounter = 0;
	GamePanel gp;
	
	public OBJ_Door_Closed(GamePanel gp) {
		super(gp);
		this.gp = gp;
					
		type = type_obstacle;
		name = objName;
		direction = "down";
		
		up1 = setup("/objects/door_closed_up_1");
		down1 = setup("/objects/door_closed_down_1");
		left1 = setup("/objects/door_closed_left_1");
		right1 = setup("/objects/door_closed_right_1");
		
		up2 = setup("/objects/door_closed_up_2");
		down2 = setup("/objects/door_closed_down_2");
		left2 = setup("/objects/door_closed_left_2");
		right2 = setup("/objects/door_closed_right_2");
		
		hitbox = new Rectangle(0, 0, 48, 48);
		collision = true;
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
		
	public void playSE() {
		gp.playSE(4, 4);
	}
	public void playCloseSE() {
		gp.playSE(4, 5);
	}
}