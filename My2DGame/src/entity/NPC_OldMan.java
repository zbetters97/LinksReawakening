package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity{
	
	GamePanel gp;
	
	public NPC_OldMan(GamePanel gp) {		
		super(gp);
		this.gp = gp;		
		
		type = type_npc;
		name = "Old Man";
		direction = "down";
		speed = 1;
		animationSpeed = 15; 
		
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
		dialogues[0] = "It's dangerous to go alone!\nTake this!";
		dialogues[1] = "I took an arrow to the knee.";
		dialogues[2] = "You are winner";
	}
	
	public void speak() {
		super.speak();		
	}
	
	public void setAction() {
		
		actionLockCounter++;
		
		if (actionLockCounter == 120) {		
						
			Random random = new Random();
			int i = random.nextInt(100) + 1; // random number 1-100
						
			if (i <= 25) direction = "up";
			if (i > 25 && i <= 50) direction = "down";
			if (i > 50 && i <= 75) direction = "left";
			if (i > 75) direction = "right";
			
			actionLockCounter = 0;
		}
	}
}