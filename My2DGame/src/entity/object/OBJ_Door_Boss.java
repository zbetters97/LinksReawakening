package entity.object;

import application.GamePanel;
import entity.Entity;

public class OBJ_Door_Boss extends Entity {
	
	public static final String objName = "Boss Door";
	private int openCounter = 0;
	
	public OBJ_Door_Boss(GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.worldX = worldX *= gp.tileSize;
		this.worldY = worldY *= gp.tileSize;
		
		type = type_obstacle_i;
		name = objName;
		direction = "down";
		collision = true;
	}	
	
	public void getImage() {
		up1 = down1 = left1 = right1 = setup("/objects/door_boss_down_1");
		up2 = down2 = left2 = right2 = setup("/objects/door_boss_down_2");
	}
	
	public void update() {
		if (opening) {
			spriteNum = 2;
			open();
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
	
	public void interact() {
		if (gp.player.boss_key > 0) {
			playSE();
			gp.player.boss_key--;
			opening = true;
		}		
	}
	
	public void playSE() {
		gp.playSE(4, 4);
	}
	public void playCloseSE() {
		gp.playSE(4, 5);
	}
}