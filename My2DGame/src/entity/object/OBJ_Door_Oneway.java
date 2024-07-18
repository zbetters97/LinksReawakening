package entity.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import application.GamePanel;
import entity.Entity;

public class OBJ_Door_Oneway extends Entity {
	
	public static final String objName = "Oneway Door";
	public BufferedImage turnUp1, turnUp2, turnUp3, turnDown1, turnDown2, turnDown3;
	
	public OBJ_Door_Oneway(GamePanel gp) {
		super(gp);
		
		type = type_obstacle;
		name = objName;
		direction = "down";
		collision = true;
		switchedOn = true;
		
		hitbox = new Rectangle(0, 16, 48, 40);
		
		getTurnImage();
	}	
	public OBJ_Door_Oneway(GamePanel gp, String direction, boolean switchedOn) {
		super(gp);
		
		type = type_obstacle;
		name = objName;
		this.direction = direction;
		this.switchedOn = switchedOn;
		collision = true;
		
		hitbox = new Rectangle(0, 16, 48, 40);
		
		getTurnImage();
	}	
	public void getImage() {
		up1 = setup("/objects/oneway_open_up");
		up2 = setup("/objects/oneway_closed_up");
		down1 = setup("/objects/oneway_open_down");
		down2 = setup("/objects/oneway_closed_down");
	}
	
	public void getTurnImage() {
		turnUp1 = setup("/objects/oneway_open_up_1");
		turnUp2 = setup("/objects/oneway_open_up_2");
		turnUp3 = setup("/objects/oneway_open_up_3");
		turnDown1 = setup("/objects/oneway_open_down_1");
		turnDown2 = setup("/objects/oneway_open_down_2");
		turnDown3 = setup("/objects/oneway_open_down_3");
	}
	
	public void interact() {	
		
		if (switchedOn && !turning) {	
			
			// PLAYER MUST BE FACING DOOR
			if (gp.player.direction.equals(getOppositeDirection(direction))) {
				playOpenSE();
				turning = true;
				gp.player.drawing = false;
				gp.gameState = gp.cutsceneState;
			}
		}
	}
	
	public void update() {
		cycleSprites();
	}
	
	public void cycleSprites() {
		
		if (turning || opening) {
			
			spriteCounter++;
			
			if (15 >= spriteCounter) spriteNum = 1;		
			else if (30 >= spriteCounter && spriteCounter > 15) spriteNum = 2;		
			else if (45 >= spriteCounter && spriteCounter > 30) spriteNum = 3;
			else if (spriteCounter > 45) {
				spriteNum = 1;
				spriteCounter = 0;		
				if (opening) {
					opening = false;
					open();
				}
				else {
					turning = false;
					turn();							
				}
			}
		}
	}
	
	private void turn() {
		
		switch (direction) {
			case "up": 
				gp.player.worldY += gp.tileSize * 2; 
				
				for (int i = 0; i < gp.obj[1].length; i++) {
					if (gp.obj[gp.currentMap][i] != null && 
							gp.obj[gp.currentMap][i].worldY == worldY + gp.tileSize) {

						gp.obj[gp.currentMap][i].opening = true;									
						break;
					}
				}
				
				break;
			case "down": 
				gp.player.worldY -= gp.tileSize * 2; 
				
				for (int i = 0; i < gp.obj[1].length; i++) {
					if (gp.obj[gp.currentMap][i] != null && 
							gp.obj[gp.currentMap][i].worldY == worldY - gp.tileSize) {

						gp.obj[gp.currentMap][i].opening = true;									
						break;
					}
				}
				
				break;						
		}
	}
	private void open() {
		
		switch (direction) {
			case "up": gp.player.worldY = worldY - gp.tileSize; break;
			case "down": gp.player.worldY = worldY + gp.tileSize; break;						
		}
		
		gp.player.drawing = true;
		gp.gameState = gp.playState;
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
								
		if (inFrame()) {		
			
			switch (direction) {
			
				case "up":								
					if (turning) {
						if (spriteNum == 1) image = turnUp1;
						else if (spriteNum == 2) image = turnUp2;	
						else if (spriteNum == 3) image = turnUp3;		
					}
					else if (opening) {
						if (spriteNum == 1) image = turnUp3;
						else if (spriteNum == 2) image = turnUp2;	
						else if (spriteNum == 3) image = turnUp1;		
					}
					else if (switchedOn) image = up1;
					else image = up2;
					
					break;
					
				case "down":						
					if (turning) {
						if (spriteNum == 1) image = turnDown1;
						else if (spriteNum == 2) image = turnDown2;	
						else if (spriteNum == 3) image = turnDown3;		
					}
					else if (opening) {
						if (spriteNum == 1) image = turnDown3;
						else if (spriteNum == 2) image = turnDown2;	
						else if (spriteNum == 3) image = turnDown1;		
					}
					else if (switchedOn) image = down1;
					else image = down2;					
					
					break;
			}
			
			g2.drawImage(image, tempScreenX, tempScreenY, null);	
			
			// DRAW HITBOX
			if (gp.keyH.debug) {
				g2.setColor(Color.RED);
				g2.drawRect(tempScreenX + hitbox.x, tempScreenY + hitbox.y, hitbox.width, hitbox.height);
			}		
		}
	}
	
	public void playOpenSE() {
		gp.playSE(4, 4);
	}
	public void playCloseSE() {
		gp.playSE(4, 5);
	}
}