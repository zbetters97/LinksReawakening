package object;

import java.awt.Rectangle;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {
	
	public static final String itmName = "Locked Door";
	GamePanel gp;
	
	public OBJ_Door(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_obstacle;
		name = itmName;
		down1 = setup("/objects/OBJ_DOOR");
		hitbox = new Rectangle(0, 16, 48, 32);
		collision = true;
		
		setDialogue();
	}	
	
	public void setDialogue() {
		dialogues[0][0] = "It's locked...";	
	}
	
	public void interact() {		
		startDialogue(this, 0);
	}
}