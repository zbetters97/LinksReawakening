package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	GamePanel gp;
	
	public int worldX, worldY;	
	
	// OBJECT ATTRIBUTES
	public BufferedImage image, image2, image3;
	public String name;
	public boolean collision = false;
	public boolean diggable = false;
	
	// CHARACTER ATTRIBUTES
	public int type;
	public int life, maxLife; // 1 life = half heart
	public int arrows, maxArrows;
	public int bombs, maxBombs;
	public int speed, baseSpeed, runSpeed, animationSpeed;
	public int level;
	public int strength, dexterity;
	public int attack, defense;
	public int exp, nextLevelEXP;
	public int rupees;
	public Entity currentWeapon, currentShield, currentItem;
	public Projectile projectile;
	public boolean hasItem;
	public String direction = "down";
	public boolean onPath = false;
	public boolean collisionOn = false;
	
	// SPRITES
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage die1, die2, die3, die4;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, 
							attackLeft1, attackLeft2, attackRight1, attackRight2;

	// DIALOGUE
	public String dialogues[] = new String[20];
	int dialogueIndex = 0;
	
	// LIFE
	boolean hpBarOn = false;
	int hpBarCounter = 0;
	public boolean invincible = false;
	public int invincibleCounter = 0;
	public boolean alive = true;
	public boolean dying = false;
	public int dyingCounter = 0;		

	// DEFAULT HITBOX
	public Rectangle hitBox = new Rectangle(0, 0, 48, 48);
	public int hitBoxDefaultX, hitBoxDefaultY, hitBoxDefaultWidth, hitBoxDefaultHeight;	
	
	// ATTACKING
	public int actionLockCounter = 0;
	public int shotAvailableCounter;
	public boolean attacking;	
	public int attackCounter = 0;
	public int attackNum = 1;
	
	// WEAPON HITBOX
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
		
	// ITEM ATTRIBUTES
	public int value, attackValue, defenseValue;
	public String description = "";
	public int price;
	public int useCost;	
	public boolean hookGrab = false;	
	
	// INVENTORY
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	
	// CHARACTER TYPES
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_enemy = 2;
	
	// INVENTORY TYPES
	public final int type_sword = 3;
	public final int type_shield = 4;	
	public final int type_item = 5;
	public final int type_collectable = 6;
	public final int type_consumable = 7;
	
	boolean pathCompleted = false;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	// CHILD ONLY	
	public void use(Entity entity) { }	
	public void setAction() { }	
	public void damageReaction() { }	
	public void checkDrop() { }
	
	public void update() { 
		
		// calls child class method
		setAction();
		
		// if no collision detected
		checkCollision();
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
			
			if (type == type_npc || type == type_enemy) {
			
				// WALKING ANIMATION (only if no collision)
				spriteCounter++;
				if (spriteCounter > animationSpeed && animationSpeed != 0) { // speed of sprite change
					
					if (spriteNum == 1) spriteNum = 2;
					else if (spriteNum == 2) spriteNum = 1;
					
					spriteCounter = 0;
				}
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
		
		// PROJECTILE REFRESH TIME
		if (shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}
	}
	
	public void checkCollision() {		
		collisionOn = false;
		gp.cChecker.checkTile(this);		
		gp.cChecker.checkEntity(this, gp.iTile);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.enemy);
		gp.cChecker.checkObject(this, false);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		if (this.type == type_enemy && contactPlayer) 
			damagePlayer(attack);  	
	}
	
	public void speak() { 
		
		gp.keyH.spacePressed = false;
		
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
	
	public void searchPath(int goalCol, int goalRow) {
		
		int startCol = (worldX + hitBox.x) / gp.tileSize;
		int startRow = (worldY + hitBox.y) / gp.tileSize;
		
		// SET PATH
		gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
		
		// PATH FOUND
		if (gp.pFinder.search()) {
			
			// NEXT WORLDX & WORLDY
			int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
			int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
						
			// ENTITY HITBOX
			int eLeftX = worldX + hitBox.x;
			int eRightX = worldX + hitBox.x + hitBox.width;
			int eTopY = worldY + hitBox.y;
			int eBottomY = worldY + hitBox.y + hitBox.height;
			
			// FIND DIRECTION TO NEXT NODE
			// UP OR DOWN
			if (eTopY > nextY && eLeftX >= nextX && eRightX < nextX + gp.tileSize) 
				direction = "up";			
			else if (eTopY < nextY && eLeftX >= nextX && eRightX < nextX + gp.tileSize)
				direction = "down";			
			// LEFT OR RIGHT
			else if (eTopY >= nextY && eBottomY < nextY + gp.tileSize) {				
				if (eLeftX > nextX) direction = "left";
				if (eLeftX < nextX) direction = "right";
			}
			// UP OR LEFT
			else if (eTopY > nextY && eLeftX > nextX) {				
				direction = "up";				
				checkCollision();
				if (collisionOn) direction = "left";			
			}
			// UP OR RIGHT
			else if (eTopY > nextY && eLeftX < nextX) {
				direction = "up";
				checkCollision();
				if (collisionOn) direction = "right";		
			}
			// DOWN OR LEFT
			else if (eTopY < nextY && eLeftX > nextX) {
				direction = "down";
				checkCollision();
				if (collisionOn) direction = "left";
			}
			// DOWN OR RIGHT
			else if (eTopY < nextY && eLeftX < nextX) {
				direction = "down";
				checkCollision();
				if (collisionOn) direction = "right";
			}
		}
		// NO PATH FOUND
		else {
			onPath = false;
		}
		
		// GOAL REACHED
		if (gp.pFinder.pathList.size() > 0) {		
			int nextCol = gp.pFinder.pathList.get(0).col;
			int nextRow = gp.pFinder.pathList.get(0).row;
			if (nextCol == goalCol && nextRow == goalRow)
				pathCompleted = true;
		}
	}
	public boolean findPath(int goalCol, int goalRow) {
		
		boolean pathFound = false;
		
		int startCol = (worldX + hitBox.x) / gp.tileSize;
		int startRow = (worldY + hitBox.y) / gp.tileSize;
		
		// SET PATH
		gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
		
		if (gp.pFinder.search()) 
			pathFound = true;
			
		return pathFound;
	}
	
	public void damagePlayer(int attack) {
				
		if (!gp.player.invincible && gp.player.alive) {
			gp.playSE(2, 0);
			
			int damage = attack - gp.player.defense;
			if (damage < 0) damage = 0;	
			gp.player.life -= damage;
			
			gp.player.invincible = true;
		}
	}
			
	public void hurtAnimation(Graphics2D g2) {
		
		invincibleCounter++;	
		
		if (invincibleCounter % 5 == 0) 
			changeAlpha(g2, 0.2f);
	}
	
	public void dyingAnimation(Graphics2D g2) {
		
		dyingCounter++;	
		
		if (dyingCounter % 5 == 0) 
			changeAlpha(g2, 0.2f);
		
		if (dyingCounter > 40) 
			alive = false;		
	}
	
	public void dropItem(Entity droppedItem) { 
		
		for (int i = 0; i < gp.obj[1].length; i++) {			
			if (gp.obj[gp.currentMap][i] == null) {
				gp.obj[gp.currentMap][i] = droppedItem;
				gp.obj[gp.currentMap][i].worldX = worldX;
				gp.obj[gp.currentMap][i].worldY = worldY;
				break;
			}
		}
	}
	
	public void generateParticle(Entity generator, Entity target) {

		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxLife = generator.getParticleMaxLife();
		
		Particle p1 = new Particle(gp, generator, color, size, speed, maxLife, -2, -1);
		Particle p2 = new Particle(gp, generator, color, size, speed, maxLife, -2, 1);
		Particle p3 = new Particle(gp, generator, color, size, speed, maxLife, 2, -1);
		Particle p4 = new Particle(gp, generator, color, size, speed, maxLife, 2, 1);
		gp.particleList.addAll(Arrays.asList(p1, p2, p3, p4));
	}
	public Color getParticleColor() {
		Color color = new Color(0,0,0);
		return color;
	}	
	public int getParticleSize() {		
		int size = 0; // 0px
		return size;
	}	
	public int getParticleSpeed() {
		int speed = 0;
		return speed;		
	}	
	public int getParticleMaxLife() {
		int maxLife = 0;
		return maxLife;
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
			
			if (hookGrab) 
				image = this.image;			
			else {			
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
			}
			
			// ENEMY HP BAR
			if (type == 2 && hpBarOn) {		
				double oneScale = (double)gp.tileSize / maxLife; // LENGTH OF HALF HEART
				double hpBarValue = oneScale * life; // LENGTH OF ENEMY HEALTH
				
				g2.setColor(new Color(35,35,35)); // DARK GRAY OUTLINE
				g2.fillRect(screenX-1, screenY - 16, gp.tileSize + 2, 10);
				
				g2.setColor(new Color(255,0,30)); // RED BAR
				g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 8);
				
				// REMOVE BAR AFTER 10 SECONDS
				hpBarCounter++;
				if (hpBarCounter > 600) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
			}
			
			// ENEMY IS HIT
			if (invincible) {
				
				// DISPLAY HP
				hpBarOn = true;
				hpBarCounter = 0;
				
				// FLASH OPACITY
				if (invincible) 
					hurtAnimation(g2);
			}	
			if (dying) 				
				dyingAnimation(g2);		
			
			g2.drawImage(image, screenX, screenY, null);			

			// DRAW HITBOX
			if (gp.keyH.debug) {
				g2.setColor(Color.RED);
				g2.drawRect(screenX + hitBox.x, screenY + hitBox.y, hitBox.width, hitBox.height);
			}
			
			// RESET OPACITY
			changeAlpha(g2, 1f);			
		}
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
	
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
}