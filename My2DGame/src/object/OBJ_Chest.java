package object;

import java.awt.Rectangle;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {
	
	public static final String objName = "Chest";
	GamePanel gp;
	
	public OBJ_Chest(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_obstacle;
		name = objName;
		image1 = setup("/objects/OBJ_CHEST");
		image2 = setup("/objects/OBJ_CHEST_OPENED");
		down1 = image1;
		collision = true;
		
		hitbox = new Rectangle(4, 16, 40, 32);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
	}	
	
	public void setLoot(Entity loot) {
		this.loot = loot;
		setDialogue();
	}
	
	public void setDialogue() {
		dialogues[0][0] = "It's empty...";
	}
	
	public void interact() {		
		if (!opened) {	
			playOpenSE();
			opened = true;	
			down1 = image2;
			gp.player.getObject(loot);	
		}
		else
			gp.player.attackCanceled = false;
	}
	
	public void playOpenSE() {
		gp.playSE(3, 17);
	}
	
	public void playCloseSE() {
		gp.playSE(3, 18);
	}
}