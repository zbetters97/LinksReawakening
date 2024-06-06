package object;

import java.awt.Rectangle;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {
	
	GamePanel gp;
	Entity loot;
	boolean opened = false;
	
	public OBJ_Chest(GamePanel gp, Entity loot) {
		super(gp);
		this.gp = gp;
		this.loot = loot;
		
		type = type_obstacle;
		name = "Chest";
		image1 = setup("/objects/chest");
		image2 = setup("/objects/chest_opened");
		down1 = image1;
		collision = true;
		
		hitBox = new Rectangle(4, 16, 40, 32);
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
	}	
	
	public void interact() {		
		if (!opened) {		
			opened = true;	
			down1 = image2;
			gp.player.getObject(loot);	
		}
		else {
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "It's empty...";
		}
	}
}