package entity.npc;

import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;

public class NPC_Cucco extends Entity {
	
	public static final String npcName = "Cucco";
	private int squakTimer = 0;
	private int squakTimerMax = 120;
	
	public NPC_Cucco(GamePanel gp, int worldX, int worldY) {		
		super(gp);	
		this.worldX = worldX * gp.tileSize;
		this.worldY = worldY * gp.tileSize;	
		worldXStart = this.worldX;
		worldYStart = this.worldY;
		
		collision = false;
		grabbable = true;
		
		type = type_npc;
		name = npcName;
		direction = "down";
		speed = 1; defaultSpeed = speed;
		animationSpeed = 10; defaultAnimationSpeed = animationSpeed;
		
		hitbox = new Rectangle(8, 20, 32, 28); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {		
		up1 = setup("/npc/cucco_up_1"); 
		up2 = setup("/npc/cucco_up_2");
		down1 = setup("/npc/cucco_down_1"); 
		down2 = setup("/npc/cucco_down_2");
		left1 = setup("/npc/cucco_left_1"); 
		left2 = setup("/npc/cucco_left_2");
		right1 = setup("/npc/cucco_right_1"); 
		right2 = setup("/npc/cucco_right_2");
	}
	
	public void update() {
		super.update();
		
		if (grabbed) grabbed();		
		else if (thrown) thrown();
	}
	
	private void grabbed() {
		worldX = gp.player.worldX;
		worldY = gp.player.worldY - gp.tileSize + 10;
		collisionOn = false;
		xT = worldX;
		yT = worldY;
	}
	private void thrown() {
		speed = 0;
		if (tossEntity()) {									
			
			gp.cChecker.checkPit(this, false);
			
			thrown = false;
			throwCounter = 0;
			tTime = 0;
			speed = defaultSpeed;

			gp.player.action = Action.IDLE;
			gp.player.grabbedObject = null;			
			gp.player.throwCounter = 0;
			gp.player.throwNum = 1;
		}
	}
	
	public void setAction() {				
		
		if (!thrown && !grabbed) {	
			animationSpeed = defaultAnimationSpeed;
			getDirection(60);			
		}		
		// CUCCO PANICS WHEN GRABBED
		else if (grabbed) {
			animationSpeed = 5;
			squakTimerMax = 30;
			direction = getPlayerDirection();
		}
		
		// PLAY SQUAK IF IN FRAME
		if (inFrame()) {
			squakTimer++;
			if (squakTimer >= squakTimerMax) {				
				playSE();
				squakTimer = 0;
				getSquakTimerMax();	
			}
		}		
	}
	
	// RANDOM SQUAK INTERVALS (1, 2, 3, or 4 seconds)
	private void getSquakTimerMax() {		
		int squak = 1 + (int)(Math.random() * 3);
		if (squak == 1) squakTimerMax = 60;
		else if (squak == 2) squakTimerMax = 120;
		else if (squak == 3) squakTimerMax = 180;
		else if (squak == 4) squakTimerMax = 240;		
	}
	
	public void resetValues() {
		
		// CANNOT DIE, RESET TO STARTING POINT
		alive = true;
		worldX = worldXStart;
		worldY = worldYStart;
		
		thrown = false;		
		gp.player.action = Action.IDLE;
		gp.player.grabbedObject = null;			
		gp.player.throwCounter = 0;
		gp.player.throwNum = 1;
	}
	
	public void playSE() {
		gp.playSE(8, 0);
	}
}