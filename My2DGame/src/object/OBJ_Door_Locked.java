package object;

import java.awt.Rectangle;

import entity.Entity;
import main.GamePanel;

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
	
	public void playOpenSe() {
		gp.playSE(3, 14);
	}
	public void playCloseSE() {
		gp.playSE(3, 18);
	}
}