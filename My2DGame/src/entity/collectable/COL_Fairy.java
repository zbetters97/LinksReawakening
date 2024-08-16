package entity.collectable;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;

public class COL_Fairy extends Entity {
	
	public static final String colName = "Fairy";
	private int moveCounter = 0;
	private int aliveCounter = -1;
	
	public COL_Fairy(GamePanel gp) {
		super(gp);
				
		collision = false;
		onGround = false;
		reviving = false;
		
		type = type_consumable;		
		name = colName;
		description = "[Fairy]\nHeals all hearts.";
		stackable = true;
		speed = 1;
		
		hitbox = new Rectangle(4, 4, 20, 20); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {
		image = setup("/collectables/fairy");
		up1 = down1 = left1 = right1 = setup("/collectables/fairy", 30, 30);
	}	
	
	public boolean use(Entity user) {		
		
		user.life = user.maxLife;
		
		if (amount > 1) amount--;
		else user.inventory.remove(this);
		
		aliveCounter = 180;			
		canPickup = false;
		
		user.dropItem(this);
		worldY -= gp.tileSize / 2;
				
		return true;
	}
	
	public void update() {
		
		// PLAYER REVIVED
		if (aliveCounter != -1) {			
			
			if (aliveCounter % 60 == 0 && aliveCounter != 0) 
				playHealSE();	
			
			aliveCounter--;
						
			if (aliveCounter == 0) 
				alive = false;			
			else { 
				getDirection(15); 
				move(); 
			}					
		}
		else {
			getDirection(15);
			move();	
		}		
	}
	
	public void move() {
		
		moveCounter++;		
		if (moveCounter >= 10) {
			
			checkCollision();
			if (!collisionOn) {
				switch (direction) {
					case "up": worldY--; break;
					case "down": worldY++; break;
					case "left": worldX--; break;
					case "right": worldX++; break;
				}
			}
			
			moveCounter = 0;
		}
	}
	
	public void playSE() {
		gp.playSE(6, 2);
	}
	public void playHealSE() {
		gp.playSE(6, 3);
	}	
}