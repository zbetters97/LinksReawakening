package object;

import java.awt.Rectangle;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {
	
	GamePanel gp;
	
	public OBJ_Door(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Door";
		hitBox = new Rectangle(0, 16, 48, 32);
		down1 = setup("/objects/OBJ_DOOR");
	}	
}