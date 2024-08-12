package entity.npc;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;
import entity.equipment.EQP_Sword_Old;
import entity.item.ITM_Hookshot;

public class NPC_OldMan extends Entity {
	
	public static final String npcName = "Old Man";
	int itemIndex = 0;
	
	public NPC_OldMan(GamePanel gp, int worldX, int worldY) {		
		super(gp);	
		this.worldX = worldX * gp.tileSize;
		this.worldY = worldY * gp.tileSize;	
		worldXStart = this.worldX;
		worldYStart = this.worldY;
		
		type = type_npc;
		name = npcName;
		direction = "down";
		speed = 0; defaultSpeed = speed;
		animationSpeed = 0; 
		
		hasItemToGive = true;
		
		hitbox = new Rectangle(8, 16, 32, 32); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
		
		setDialogue();
		setItems();		
	}
	
	public void getImage() {		
		up1 = setup("/npc/oldman_up_1"); 
		up2 = setup("/npc/oldman_up_2");
		down1 = setup("/npc/oldman_down_1"); 
		down2 = setup("/npc/oldman_down_2");
		left1 = setup("/npc/oldman_left_1"); 
		left2 = setup("/npc/oldman_left_2");
		right1 = setup("/npc/oldman_right_1"); 
		right2 = setup("/npc/oldman_right_2");
	}
	public void setDialogue() {
		dialogues[0][0] = "Many years ago, a boy just like you came\nup to me. He faced many challenges...";
		dialogues[0][1] = "He sought after the golden triangle...\nWhat people seek after today is far\nmore dangerous.";
		dialogues[0][2] = "You seem like a brave boy.\nUnderstand the importance of what you\nare dealing with.";
		dialogues[0][3] = "Don't be foolish when seeking the\nBlue Heart, " + gp.player.name + ".";
		dialogues[0][4] = "It's dangerous to go alone...\nTake this!";
		
		dialogues[1][0] = "My home lies in the northeast region.\nIs it safe to walk there?";
		
		dialogues[2][0] = "I don't have time to chat right now.";
		
		dialogues[3][0] = "Thank you, kind boy. Here is a gift for you.";
		
		dialogues[4][0] = "It's a secret to everyone.";
	}
	public void setItems() {		
		inventory.add(new EQP_Sword_Old(gp));
		inventory.add(new ITM_Hookshot(gp));
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
		
		direction = getOppositeDirection(gp.player.direction);
		startDialogue(this, dialogueSet);
	}
	
	public void setAction() {
					
		int goalCol = 36;
		int goalRow = 11;
		
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