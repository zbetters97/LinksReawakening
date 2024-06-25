package entity.object;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;

public class OBJ_Chest extends Entity {
	
	public static final String objName = "Chest";
	GamePanel gp;
	
	public OBJ_Chest(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_obstacle_i;
		name = objName;
		image1 = setup("/objects/OBJ_CHEST");
		image2 = setup("/objects/OBJ_CHEST_OPENED");
		down1 = image1;
		
		grabbable = true;
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
		if (!opened && gp.player.direction.equals("up")) {	
			gp.player.attackCanceled = true;
			playOpenSE();
			opened = true;	
			down1 = image2;
			gp.player.getObject(loot);	
		}
	}
	
	public void playOpenSE() {
		gp.playSE(3, 17);
	}
	
	public void playCloseSE() {
		gp.playSE(3, 18);
	}
}