package entity.npc;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;

public class NPC_Dialogue_Exchange extends Entity {
	
	GamePanel gp;
	
	public NPC_Dialogue_Exchange(GamePanel gp) {		
		super(gp);
		this.gp = gp;		
		
		type = type_npc;
		name = "Old Man";
		direction = "down";
		speed = 0; defaultSpeed = speed;
		animationSpeed = 0; 
		
		hitbox = new Rectangle(8, 16, 32, 32); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		getImage();
		setDialogue();
		setDialogueResponses();
	}
	
	public void getImage() {		
		up1 = setup("/npc/oldman_up_1"); up2 = setup("/npc/oldman_up_2");
		down1 = setup("/npc/oldman_down_1"); down2 = setup("/npc/oldman_down_2");
		left1 = setup("/npc/oldman_left_1"); left2 = setup("/npc/oldman_left_2");
		right1 = setup("/npc/oldman_right_1"); right2 = setup("/npc/oldman_right_2");
	}
	
	public void setDialogue() {
		dialogues[0][0] = "You look confused.";
		dialogues[1][0] = "How can I help you?";
		
		dialogues[2][0] = "You're in Hyrule!";		
		dialogues[2][1] = "I know, crazy, right!";
		dialogues[3][0] = "Your name is " + gp.player.name + ".";
		dialogues[4][0] = "My name is Maximus!";
	}
	public void setDialogueResponses() {
		responses[0][0] = "Where am I?";
		responses[0][1] = "What is my name?";
		responses[0][2] = "Who are you?";
	}
	
	public void speak() {		
		
		gp.keyH.actionPressed = false;		
		gp.ui.npc = this;
		dialogueSet = 0;		
		
		facePlayer();
		startDialogue(this, dialogueSet, 0, 2);
	}
}