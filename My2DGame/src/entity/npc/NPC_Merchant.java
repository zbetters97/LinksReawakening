package entity.npc;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;
import entity.item.ITM_Boomerang;

public class NPC_Merchant extends Entity {
	
	public static final String npcName = "Merchant 1";
	
	public NPC_Merchant(GamePanel gp, int worldX, int worldY) {		
		super(gp);	
		this.worldX = worldX * gp.tileSize;
		this.worldY = worldY * gp.tileSize;	
		worldXStart = this.worldX;
		worldYStart = this.worldY;
		
		type = type_npc;
		name = npcName;
		direction = "down";
		speed = 0; defaultSpeed = speed;
		animationSpeed = 25; 
		
		hitbox = new Rectangle(8, 16, 32, 32); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
		
		setDialogue();
		setItems();		
	}
	
	public void getImage() {		
		down1 = setup("/npc/merchant_down_1"); 
		down2 = setup("/npc/merchant_down_2");
	}	
	public void setDialogue() {
		dialogues[0][0] = "Buy somethin', will ya!";		
		dialogues[1][0] = "Hey! You don't have enough rupees!";
		dialogues[2][0] = "Looks like yer all outa room, kid!";
		dialogues[3][0] = "I think you should hold onto that!";
		dialogues[4][0] = "Scram, kid!";
	}		
	
	public void setItems() {		
		inventory.add(new ITM_Boomerang(gp));
	}	
	
	public void speak() {
		gp.keyH.actionPressed = false;
		gp.ui.npc = this;
		gp.gameState = gp.tradeState;
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