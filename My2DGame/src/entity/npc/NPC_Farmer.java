package entity.npc;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;
import entity.item.ITM_Bow;

public class NPC_Farmer extends Entity {
	
	public static final String npcName = "Farmer";	
	public int itemIndex = 0;
	
	public NPC_Farmer(GamePanel gp, int worldX, int worldY) {		
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
		up1 = setup("/npc/farmer_up_1"); 
		up2 = setup("/npc/farmer_up_2");
		down1 = setup("/npc/farmer_down_1"); 
		down2 = setup("/npc/farmer_down_2");
		left1 = setup("/npc/farmer_left_1"); 
		left2 = setup("/npc/farmer_left_2");
		right1 = setup("/npc/farmer_right_1"); 
		right2 = setup("/npc/farmer_right_2");
	}
	public void setDialogue() {
		dialogues[0][0] = "My Cucco! My Cucco!";
		dialogues[0][1] = "Oh, young lad, have you seen my precious\nCucco?";
		dialogues[0][2] = "With all the monsters running around\nlooking for the Blue Heart, I'm scared\nthat Spladoodles is in great danger!";
		
		dialogues[1][0] = "SPLADOODLES! You are ok!";
		dialogues[1][1] = "Thank you, " + gp.player.name + "! You're a true hero!";
		dialogues[1][2] = "Here is a great reward for you!";
		
		dialogues[2][0] = "Why do you think everyone wants the\nBlue Heart, anyways?";
	}
	public void setItems() {		
		inventory.add(new ITM_Bow(gp));
	}
	
	public void speak() {		
		
		hasItemToGive = false;
		
		if (inventory.size() == 1 && !foundCucco()) {
			gp.ui.npc = this;
			dialogueSet = 0;
		}
		else if (inventory.size() == 1) {
			hasItemToGive = true;
			gp.ui.npc = this;
			gp.ui.newItemIndex = itemIndex;
			dialogueSet = 1;
		}		
		else if (inventory.size() == 0) {
			dialogueSet = 2;
		}
		
		direction = getOppositeDirection(gp.player.direction);
		startDialogue(this, dialogueSet);
	}
	
	public void setAction() {
		
		// LOOK AROUND IF CUCCO NOT FOUND
		if (inventory.size() != 0) {
			speed = defaultSpeed;	
			getDirection(60);		
		}
	}
	
	public boolean foundCucco() {
		
		boolean cuccoFound = false;
		int cuccoIndex = -1;
		int cuccoTileDistance = 99;
		
		// FIND INDEX OF CUCCO
		for (int i = 0; i < gp.npc[1].length; i++) {
			if (gp.npc[gp.currentMap][i] != null && 
					gp.npc[gp.currentMap][i].name.equals(NPC_Cucco.npcName)) {
				cuccoIndex = i;
				break;
			}
		}
		
		// CHECK IF CUCCO IS CLOSEBY
		cuccoTileDistance = getTileDistance(gp.npc[gp.currentMap][cuccoIndex]);
		
		// CUCCO WITHIN 3 TILES
		if (cuccoTileDistance < 3) {
			cuccoFound = true;
		}
		
		return cuccoFound;
	}
}