package entity.object;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;

public class OBJ_Block extends Entity {
	
	public static final String objName = "Block";
	public int pushCounter = 0;
	public int pushMax = 48;
	GamePanel gp;
	
	public OBJ_Block(GamePanel gp) {		
		super(gp);
		this.gp = gp;		
		
		type = type_obstacle;
		name = objName;
		direction = "down";
		speed = 0; defaultSpeed = speed;
		
		grabbable = true;
		collision = true;		
		
		hitbox = new Rectangle(0, 0, 48, 48); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		getImage();
	}
			
	public void getImage() {		
		down1 = setup("/objects/OBJ_BLOCK"); 
	}
	
	public void interact() {
	}
}