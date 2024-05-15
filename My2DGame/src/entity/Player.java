package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	boolean canRun = false;	
	
	/** CONSTRUCTOR **/
	public Player(GamePanel gp, KeyHandler keyH) {
		
		// pass GamePanel to Entity abstract class
		super(gp);
		
		this.keyH = keyH;		
		
		// PLAYER POSITION LOCKED TO CENTER
		// push by half a tile
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2); 
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
						
		// collision area (x, y, width, height)
		// collision box smaller than player
		solidArea = new Rectangle(8, 16, 32, 32); 
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 3;
		baseSpeed = speed;
		runSpeed = 6;
		animationSpeed = 10;
		direction = "down";
	}
	
	public void getPlayerImage() {		
		up1 = setup("/player/boy_up_1"); up2 = setup("/player/boy_up_2");
		down1 = setup("/player/boy_down_1"); down2 = setup("/player/boy_down_2");
		left1 = setup("/player/boy_left_1"); left2 = setup("/player/boy_left_2");
		right1 = setup("/player/boy_right_1"); right2 = setup("/player/boy_right_2");
		sit = setup("/player/boy_sit"); sing = setup("/npc/girl_sing_1");
	}
	
	public void update() {
		
		// run button (increase sprite cycle)
		if ( keyH.shiftPressed && canRun) { speed = runSpeed; animationSpeed = 6; }
		else { speed = baseSpeed; animationSpeed = 10; }
		
		if (keyH.spacePressed) {	
			
			// CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
		}		
		else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {			
						
			// find direction
			if (keyH.upPressed) direction = "up";
			if (keyH.downPressed) direction = "down";
			if (keyH.leftPressed) direction = "left";
			if (keyH.rightPressed) direction = "right";			
			
			if (keyH.upPressed && keyH.leftPressed) direction = "upleft";
			if (keyH.upPressed && keyH.rightPressed) direction = "upright";
			if (keyH.downPressed && keyH.leftPressed) direction = "downleft";
			if (keyH.downPressed && keyH.rightPressed) direction = "downright";			
			
			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
						
			// CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			// MOVE IF NO COLLISION
			if (!collisionOn) { 
				
				// move player in direction pressed
				switch (direction) {
					case "up": worldY -= speed; break;
					case "upleft": worldY -= speed - 1; worldX -= speed - 1; break;
					case "upright": worldY -= speed - 1; worldX += speed - 1; break;
					
					case "down": worldY += speed; break;
					case "downleft": worldY += speed - 1; worldX -= speed - 1; break;
					case "downright": worldY += speed; worldX += speed - 1; break;
					
					case "left": worldX -= speed; break;
					case "right": worldX += speed; break;
				}
			}
				
			// WALKING ANIMATION
			spriteCounter++;
			if (spriteCounter > animationSpeed) { // speed of sprite change
				
				if (spriteNum == 1) spriteNum = 2;
				else if (spriteNum == 2) spriteNum = 1;
				
				spriteCounter = 0;
			}	
		}		
	}
	
	public void interactNPC(int i) {
		
		// NPC IS NEAR
		if (i != -1) {			
			if (gp.keyH.spacePressed) {
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
		}
	}
	
	public void pickUpObject(int i) {	
		
		// object is interacted with by entity
		if (i != -1) {			
		}
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		// change entity sprite based on which direction and which cycle
		switch (direction) {
			case "up":
			case "upleft":
			case "upright":
				if (spriteNum == 1) image = up1;
				if (spriteNum == 2) image = up2;
				break;
			case "down":
			case "downleft":
			case "downright":
				if (spriteNum == 1) image = down1;
				if (spriteNum == 2) image = down2;
				break;
			case "left":
				if (spriteNum == 1) image = left1;
				if (spriteNum == 2) image = left2;
				break;
			case "right":
				if (spriteNum == 1) image = right1;
				if (spriteNum == 2) image = right2;
				break;
		}
		
		// image, x y position, width, height
		g2.drawImage(image, screenX, screenY, null); 
	}
}














