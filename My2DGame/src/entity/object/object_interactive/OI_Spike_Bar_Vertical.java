package entity.object.object_interactive;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;

public class OI_Spike_Bar_Vertical extends Entity {
	
	public static final String obj_iName = "Spike Bar";
	public int pushCounter = 0;
	
	public OI_Spike_Bar_Vertical(GamePanel gp, int worldX, int worldY) {		
		super(gp);
		this.worldX = worldX *= gp.tileSize;
		this.worldY = worldY *= gp.tileSize;
		
		type = type_obstacle_i;
		name = obj_iName;
		direction = "left";
		collision = true;	
		animationSpeed = 10;
		speed = 1; defaultSpeed = speed;
		
		hitbox = new Rectangle(1, 3, 46, 138); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {		
		up1 = down1 = left1 = right1 = setup("/objects_interactive/spike_bar_left_1", gp.tileSize, gp.tileSize * 3); 
		up2 = down2 = left2 = right2 = setup("/objects_interactive/spike_bar_left_2", gp.tileSize, gp.tileSize * 3); 
	}
	
	public void move(String dir) {	
		
		if (!moving) {
						
			// INCREASE SPEED TO DETECT COLLISION
			direction = dir;
			speed = gp.tileSize;			
			
			checkCollision();
							
			if (isCorrectTile() && !collisionOn) {
				playSE();
				gp.player.action = Action.PUSHING;
				moving = true;
				speed = 1;
			}
		}
	}
	
	public void checkCollision() {
		collisionOn = false;
		gp.cChecker.checkPlayer(this);
		gp.cChecker.checkTile(this);		
		gp.cChecker.checkEntity(this, gp.iTile);
		gp.cChecker.checkEntity(this, gp.obj);	
		gp.cChecker.checkEntity(this, gp.obj_i);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.enemy);		
	}
	
	public void update() { 
		
		if (moving) {
			pushCounter++;
			if (pushCounter <= gp.tileSize) {	
				push();
			}
			else {
				moving = false;
				pushCounter = 0;
			}
		}
	}
	
	private void push() {
		
		// MOVE IN DIRECTION PUSHED
		switch (direction) {		
			case "left": worldX -= speed; break;
			case "right": worldX += speed; break;
		}		

		cycleSprites();
	}
	
	private boolean isCorrectTile() {
		
		boolean correctTile = false;
	
		correctTile = true;
		
		return correctTile;
	}
		
	public void playSE() {
		gp.playSE(4, 5);
	}
}