package entity.npc;

import application.GamePanel;
import entity.item.ITM_Shovel;

public class NPC_Traveler_2 extends NPC_Traveler_Template {
	
	public static final String npcName = "Traveler 2";
	
	public NPC_Traveler_2(GamePanel gp, int worldX, int worldY) {		
		super(gp, worldX, worldY);	
		
		name = npcName;
	}
	
	public void setDialogue() {
		dialogues[0][0] = "Hello, lad.";
		
		dialogues[1][0] = "Titus, there you are!";
		dialogues[1][1] = "Cyprius, where have you been?";
		dialogues[1][2] = "No time for that, Titus.\nI found something!";
		dialogues[1][3] = "Found what? Spit it out, lad!";
		dialogues[1][4] = "The Blue Heart, Titus! I found it!";
		dialogues[1][5] = "By the saints!\nWell, Cyprius, show me! Show me!\nNo time to waste!";
		
		dialogues[2][0] = "Well... I thought for sure the Blue Heart\nwas here.";
		dialogues[2][1] = "I'm never trusting that shopowner again!"; 
		dialogues[2][2] = "All he's good for is that fancy\nBoomerang he sells!";
		dialogues[2][3] = "I really wish I had the money for it.\nOr, at least a Shovel to find the money...";
		
		dialogues[3][0] = "Hey! You have a shovel!\nCan it really dig up treasure?";
		dialogues[3][1] = "I bet it won't be that easy to find\nthe Blue Heart...";
	}
	
	public void speak() {	
		
		if (gp.player.searchItemInventory(ITM_Shovel.itmName) != -1)
			dialogueSet = 3;
		
		if (!onPath) {
			facePlayer();
			startDialogue(this, dialogueSet);	
		}	
		else
			gp.player.attackCanceled = false;
	}
}