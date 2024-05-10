package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity {

	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	boolean canRun = false;
	
	// # of keys player holds
	public int hasKey = 0;
	
	
	/** CONSTRUCTOR **/
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;		
		
		// PLAYER POSITION LOCKED TO CENTER
		// push by half a tile
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2); 
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
						
		// collision area (x, y, width, height)
		// collision box smaller than entity
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
		animationSpeed = 10;
		direction = "down";
	}
	
	public void getPlayerImage() {		
		up1 = setup("boy_up_1"); up2 = setup("boy_up_2");
		down1 = setup("boy_down_1"); down2 = setup("boy_down_2");
		left1 = setup("boy_left_1"); left2 = setup("boy_left_2");
		right1 = setup("boy_right_1"); right2 = setup("boy_right_2");
	}
	
	public BufferedImage setup(String imageName) {
		
		UtilityTool utility = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/"+imageName+".png"));
			image = utility.scaleImage(image, gp.tileSize, gp.tileSize);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public void update() {
		
		// run button (increase sprite cycle)
		if (keyH.shiftPressed && canRun) { speed = 5; animationSpeed = 6; }
		else { speed = 10; animationSpeed = 10; }
		
		if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {			
						
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
			
			// CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			// if no collision detected
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
			
				// WALKING ANIMATION (only if no collision)
				spriteCounter++;
				if (spriteCounter > animationSpeed) { // speed of sprite change
					
					if (spriteNum == 1) spriteNum = 2;
					else if (spriteNum == 2) spriteNum = 1;
					
					spriteCounter = 0;
				}	
			}
			else // set walking sprite to idle if collisionOn
				spriteNum = 1;
		}		
	}
	
	public void pickUpObject(int i) {
		
		// object is interacted with by entity
		if (i != -1) {
			
			String objectName = gp.obj[i].name;
			switch (objectName) {
			case "Key":
				
				gp.playSE(1);
				hasKey++;
				gp.obj[i] = null;
				gp.ui.displayMessage("You found a key!");
				
				break;
			case "Boots":
				
				gp.playSE(2);
				canRun = true;
				gp.obj[i] = null;	
				
				gp.ui.displayMessage("You found the Boots! Hold SHIFT to run fast!");
				
				break;			
			case "Door":	
				
				if (hasKey > 0 ) {
					gp.playSE(3);
					hasKey--;
					gp.obj[i] = null;
				}	
				else
					gp.ui.displayMessage("The door is locked...");
				
				break;
			case "Chest":				
				
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSE(4);
				
				break;
			}
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














