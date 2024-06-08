package entity;

import main.GamePanel;
import object.COL_Potion_Red;
import object.ITM_Boomerang;

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
		dialogues[0] = "Buy somethin', will ya!";
	}	
	
	public void speak() {		
		gp.gameState = gp.tradeState;
		super.speak();		
		
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