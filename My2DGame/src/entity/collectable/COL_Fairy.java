package entity.collectable;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;

public class COL_Fairy extends Entity {
	
	public static final String colName = "Fairy";
	private int moveCounter = 0;
	
	public COL_Fairy(GamePanel gp) {
		super(gp);
				
		collision = false;
		onGround = false;
		
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
		playHealSE();
		user.life = user.maxLife;
		return true;
	}
	
	public void update() {
		getDirection(15);
		move();		
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