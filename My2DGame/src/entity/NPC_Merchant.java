package entity;

import item.ITM_Boomerang;
import main.GamePanel;

public class NPC_Merchant extends Entity {
	
	GamePanel gp;
	
	public NPC_Merchant(GamePanel gp) {		
		super(gp);
		this.gp = gp;		
		
		type = type_npc;
		name = "Shopkeeper";
		direction = "down";
		speed = 0;
		animationSpeed = 25; 
		
		getImage();
		setItems();
		setDialogue();
	}
	
	public void getImage() {		
		down1 = setup("/npc/merchant_down_1"); 
		down2 = setup("/npc/merchant_down_2");
	}	
	
	public void setItems() {		
		inventory.add(new ITM_Boomerang(gp));
	}
	public void setDialogue() {
		dialogues[0][0] = "Buy somethin', will ya!";		
		dialogues[1][0] = "Hey! You don't have enough rupees!";
		dialogues[2][0] = "Looks like yer all outa room, kid!";
		dialogues[3][0] = "I think you should hold onto that!";
		dialogues[4][0] = "Scram, kid!";
	}	
	
	public void speak() {				
		facePlayer();
		gp.keyH.actionPressed = false;
		gp.gameState = gp.tradeState;
		gp.ui.npc = this;
	}
	
	public void update() {
		
		spriteCounter++;
		if (spriteCounter > animationSpeed) { // speed of sprite change
			
			if (spriteNum == 1) spriteNum = 2;
			else if (spriteNum == 2) spriteNum = 1;
			
			spriteCounter = 0;
		}
	}
}