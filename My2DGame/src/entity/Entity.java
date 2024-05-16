package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	GamePanel gp;
	
	public int worldX, worldY;
	public int type; // 0 = PLAYER, 1 = NPC, 2 = ENEMY
	public int speed, baseSpeed, runSpeed, animationSpeed;
	
	// SPRITES
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, 
							attackLeft1, attackLeft2, attackRight1, attackRight2;
	public BufferedImage sit, sing;
	
	public String direction = "down";

	public int spriteCounter = 0;
	public int spriteNum = 1;	
	public int actionLockCounter = 0;
	
	public boolean attacking;	
	public int attackCounter = 0;
	public int attackNum = 1;
	
	public boolean invincible = false;
	public int invincibleCounter = 0;
	
	public boolean alive = true;
	public boolean dying = false;
	public int dyingCounter = 0;
	
	// ENTITY WEAPON AREA
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	
	// DEFAULT HITBOX
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX, solidAreaDefaultY;		
	public boolean collisionOn = false;
	
	// CHARACTER STATUS
	public int maxLife; // 1 life = half heart
	public int life;
	
	String dialogues[] = new String[20];
	int dialogueIndex = 0;
	
	// OBJECT ATTRIBUTES
	public BufferedImage image, image2, image3;
	public String name;
	public boolean collision = false;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public void speak() { 
		
		if (dialogues[dialogueIndex] == null) 
			dialogueIndex = 0;
		
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;	
		
		switch (gp.player.direction) {
		
		case "up":
		case "upleft":
		case "upright":
			direction = "down";
			break;
		case "down":
		case "downleft":
		case "downright":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;		
		}		
	}
	
	public void setAction() { }
	
	public void update() { 
		
		// calls child class method
		setAction();
		
		collisionOn = false;
		gp.cChecker.checkTile(this);		
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.enemy);
		gp.cChecker.checkObject(this, false);		
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		if (this.type == 2 && contactPlayer) {
			if (!gp.player.invincible) {
				gp.player.life--;
				gp.player.invincible = true;
			}
		}
		
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
		// ENTITY SHIELD AFTER HIT
		if (invincible) {
			invincibleCounter++;
			
			// 1 SECOND REFRESH TIME 
			if (invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		// convert world map coordinates to screen coordinates
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
					
		// only draw tiles within player boundary
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			// change entity sprite based on which direction and which cycle
			switch (direction) {
			case "up":
				if (spriteNum == 1) image = up1;
				if (spriteNum == 2) image = up2;
				break;
			case "down":
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
			
			// ENTITY IS HIT
			if (invincible) {
				
				// FLASH OPACITY
				if (invincibleCounter % 5 == 0) 
					changeAlpha(g2, 0.3f);
				else 
					changeAlpha(g2, 1f);
			}	
			if (dying) {
				dyingAnimation(g2);
			}
			
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			
			// RESET OPACITY
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
	}
	
	public void dyingAnimation(Graphics2D g2) {
		
		dyingCounter++;
		
		if (dyingCounter % 5 == 0 && dyingCounter <= 40) 
			changeAlpha(g2, 0f);		
		else
			changeAlpha(g2, 1f);
		
		if (dyingCounter > 40) {
			dying = false;
			alive = false;
		}
	}
	
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
	
	public BufferedImage setup(String imagePath) {	
		
		UtilityTool utility = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = utility.scaleImage(image, gp.tileSize, gp.tileSize);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	public BufferedImage setup(String imagePath, int width, int height) {
		
		UtilityTool utility = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = utility.scaleImage(image, width, height);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
}