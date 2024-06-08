package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class NPC_Traveler extends Entity{
	
	GamePanel gp;
	int itemIndex = 0;
	
	public NPC_Traveler(GamePanel gp) {		
		super(gp);
		this.gp = gp;		
		
		type = type_npc;
		name = "Traveler";
		direction = "down";
		speed = 1;
		animationSpeed = 15; 		

		hitbox = new Rectangle(8, 16, 32, 32); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		getImage();
		setDialogue();
	}
	
	public void getImage() {		
		up1 = setup("/npc/oldman_up_1"); up2 = setup("/npc/oldman_up_2");
		down1 = setup("/npc/oldman_down_1"); down2 = setup("/npc/oldman_down_2");
		left1 = setup("/npc/oldman_left_1"); left2 = setup("/npc/oldman_left_2");
		right1 = setup("/npc/oldman_right_1"); right2 = setup("/npc/oldman_right_2");
	}
	
	public void setDialogue() {
		dialogues[0] = "Follow me, my boy!";
	}
	
	public void speak() {		
		super.speak();
		
		// NPC GOES TO LOCATION
		onPath = true;
	}
	
	public void setAction() {

		// NPC IS MOVING TO LOCATION
		if (onPath) {
			
			int goalCol = 26;
			int goalRow = 39;	
			
			searchPath(goalCol, goalRow);
		}
		else {
			// MOVE EVERY 3 SECONDS
			actionLockCounter++;
			if (actionLockCounter == 180) {		
							
				Random random = new Random();
				int i = random.nextInt(100) + 1;
							
				if (i <= 25) direction = "up";
				if (i > 25 && i <= 50) direction = "down";
				if (i > 50 && i <= 75) direction = "left";
				if (i > 75) direction = "right";
				
				actionLockCounter = 0;
			}	
		}
	}
}