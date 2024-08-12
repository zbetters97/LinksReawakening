package entity.npc;

import application.GamePanel;

public class NPC_Traveler_1 extends NPC_Traveler_Template {
	
	public static final String npcName = "Traveler 1";
	
	public NPC_Traveler_1(GamePanel gp, int worldX, int worldY) {		
		super(gp, worldX, worldY);
		
		name = npcName;
		hasCutscene = true;	
	}
	
	public void setDialogue() {
		dialogues[0][0] = "I don't have time to chat right now.\nI'm waiting for a friend.";
		dialogues[1][0] = "I knew Cyprius was lying to me...";
		dialogues[1][1] = "I've always heard that the Blue Heart\nrests in the dungeon to the northwest.";
		dialogues[1][2] = "But of course the only way to get there\nis with a Hookshot!";
	}
}