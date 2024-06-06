package entity;

import java.awt.Rectangle;

import main.GamePanel;
import object.EQP_Sword;
import object.ITM_Bomb;

public class NPC_OldMan extends Entity{
	
	GamePanel gp;
	int itemIndex = 0;
	
	public NPC_OldMan(GamePanel gp) {		
		super(gp);
		this.gp = gp;		
		
		hasItem = true;
		type = type_npc;
		name = "Old Man";
		direction = "down";
		speed = 0; defaultSpeed = speed;
		animationSpeed = 0; 
		
		hitBox = new Rectangle(8, 16, 32, 32); 		
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
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
		inventory.add(new EQP_Sword(gp));
		inventory.add(new ITM_Bomb(gp));
	}
	
	public void setDialogue() {
		dialogues[0] = "It's dangerous to go alone!\nTake this!";
		dialogues[1] = "My home lies in the northeast region.\nIs it safe to walk there?";
		dialogues[2] = "I don't have time to chat right now.";
		dialogues[3] = "Thank you, kind boy. Here is a gift for you.";
		dialogues[4] = "It's a secret to everyone.";
	}
	
	public void speak() {		
		
		if (inventory.size() == 2) {
			gp.ui.npc = this;
			gp.ui.newItemIndex = itemIndex;
			dialogueIndex = 0;
		}				
		else if (inventory.size() == 1 && !pathCompleted) {	
			onPath = true;
		}			
		else if (inventory.size() == 1 && pathCompleted) {
			gp.ui.npc = this;
			gp.ui.newItemIndex = itemIndex;	
			dialogueIndex = 3;
		}		
		else if (inventory.size() == 0) 
			dialogueIndex = 4;			
		
//		dialogues = Arrays.copyOfRange(dialogues, 1, dialogues.length);
		
		super.speak();			
	}
	
	public void setAction() {
					
		int goalCol = 37;
		int goalRow = 9;
		
		// PATH NOT OPEN
		if (onPath && !findPath(goalCol, goalRow)) {
			speed = defaultSpeed;
			animationSpeed = 0;	
			dialogueIndex = 1;
			onPath = false;
		}
		// PATH OPEN
		if (onPath && findPath(goalCol, goalRow)) {						
			speed = 2;
			animationSpeed = 15;
			dialogueIndex = 2;			
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