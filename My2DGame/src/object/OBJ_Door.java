package object;

import java.awt.Rectangle;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {
	
	GamePanel gp;
	
	public OBJ_Door(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_obstacle;
		name = "Door";
		down1 = setup("/objects/OBJ_DOOR");
		hitbox = new Rectangle(0, 16, 48, 32);
		collision = true;
	}	
	
	public void interact() {		
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "It's locked...";		
	}
}