package entity;

import java.awt.Rectangle;

import equipment.EQP_Sword_Old;
import item.ITM_Bomb;
import main.GamePanel;

public class NPC_OldMan extends Entity {
	
	GamePanel gp;
	int itemIndex = 0;
	
	public NPC_OldMan(GamePanel gp) {		
		super(gp);
		this.gp = gp;		
		
		hasItemToGive = true;
		type = type_npc;
		name = "Old Man";
		direction = "down";
		speed = 0; defaultSpeed = speed;
		animationSpeed = 0; 
		
		hitbox = new Rectangle(8, 16, 32, 32); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		getImage();
		setItems();
		setDialogue();
	}
	
	public void getImage() {		
		up1 = setup("/npc/oldman_up_1"); up2 = setup("/npc/oldman_up_2");
		down1 = setup("/npc/oldman_down_1"); down2 = setup("/npc/oldman_down_2");
		left1 = setup("/npc/oldman_left_1"); left2 = setup("/npc/oldman_left_2");
		right1 = setup("/npc/oldman_right_1"); right2 = setup("/npc/oldman_right_2");
	}
	public void setItems() {		
		inventory.add(new EQP_Sword_Old(gp));
		inventory.add(new ITM_Bomb(gp));
	}
	
	public void setDialogue() {
		dialogues[0][0] = "Many years ago, a boy just like you came\nup to me. He faced many challenges...";
		dialogues[0][1] = "It's dangerous to go alone...\nTake this!";
		
		dialogues[1][0] = "My home lies in the northeast region.\nIs it safe to walk there?";
		
		dialogues[2][0] = "I don't have time to chat right now.";
		
		dialogues[3][0] = "Thank you, kind boy. Here is a gift for you.";
		
		dialogues[4][0] = "It's a secret to everyone.";
	}
	
	public void speak() {		
		
		hasItemToGive = false;
		
		if (inventory.size() == 2) {
			hasItemToGive = true;
			gp.ui.npc = this;
			gp.ui.newItemIndex = itemIndex;
			dialogueSet = 0;
		}				
		else if (inventory.size() == 1 && !pathCompleted) {	
			dialogueSet = 1;
			onPath = true;
		}			
		else if (inventory.size() == 1 && pathCompleted) {
			hasItemToGive = true;
			gp.ui.npc = this;
			gp.ui.newItemIndex = itemIndex;	
			dialogueSet = 3;
		}		
		else if (inventory.size() == 0) {
			dialogueSet = 4;			
		}
		
		facePlayer();
		startDialogue(this, dialogueSet);
	}
	
	public void setAction() {
					
		int goalCol = 37;
		int goalRow = 9;
		
		// PATH NOT OPEN
		if (onPath && !findPath(goalCol, goalRow)) {
			speed = defaultSpeed;
			animationSpeed = 0;	
			dialogueSet = 1;
			onPath = false;
		}
		// PATH OPEN
		if (onPath && findPath(goalCol, goalRow)) {						
			speed = 2;
			animationSpeed = 15;
			dialogueSet = 2;			
			searchPath(goalCol, goalRow);
		}		
		// GOAL REACHED
		if (pathCompleted) {
			speed = defaultSpeed;		
			animationSpeed = 0;
			onPath = false;
		}
	}
}