package entity;

import main.GamePanel;

public class Projectile extends Entity {

	Entity user;
	
	public Projectile(GamePanel gp) {
		super(gp);
	}
	
	public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
		
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.user = user;
		this.life = this.maxLife; // RESET LIFE WHEN USED		
	}
	
	public void update() {		
		
		// NON-HOOKSHOT PROJECTILES
		if (!name.equals("Hookshot")) {
			
			// SHOT BY PLAYER
			if (user == gp.player) {
				
				int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
				if (enemyIndex != -1) {
					gp.player.damageEnemy(enemyIndex, attack);
					generateParticle(user.projectile, gp.enemy[enemyIndex]);
					alive = false;
				}
			}
			// SHOT BY NON-PLAYER
			else {
				boolean contactPlayer = gp.cChecker.checkPlayer(this);
				if (!gp.player.invincible && contactPlayer) {
					damagePlayer(attack);
					generateParticle(user.projectile, gp.player);
					alive = false;
				}
			}
			
			// MOVE IN DIRECTION SHOT
			switch (direction) {
				case "up": 
				case "upleft": 
				case "upright": 
					worldY -= speed; break;			
				case "down":
				case "downleft": 
				case "downright":
					 worldY += speed; break;			
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
			}
			
			life--;
			if (life <= 0) alive = false; // DISSAPEARS AFTER X FRAMES
			
			// MOVING ANIMATION
			spriteCounter++;
			if (spriteCounter > animationSpeed) { // speed of sprite change
				
				if (spriteNum == 1) spriteNum = 2;
				else if (spriteNum == 2) spriteNum = 1;
				
				spriteCounter = 0;
			}		
		}
		// HOOKSHOT PROJECTILE
		else if (name.equals("Hookshot")) {

			gp.gameState = gp.itemState;

			if (life % 5 == 0 && life != 0) generateParticle(this, this);
			
			collisionOn = false;			
			int objectIndex = gp.cChecker.checkObject(this, true);	
			gp.cChecker.checkTile(this);	
			gp.cChecker.checkEntity(this, gp.iTile);
			
			// PULL ITEM TOWARDS PLAYER
			if (objectIndex != -1) {
				
				hookGrab = true;
				
				worldX = gp.obj[objectIndex].worldX;
				worldY = gp.obj[objectIndex].worldY;
				
				switch (direction) {
					case "down": 
					case "downleft": 
					case "downright": 
						if (gp.obj[objectIndex].worldY >= gp.player.worldY) 
							gp.obj[objectIndex].worldY -= 5;						
						else {
							alive = false;
							gp.gameState = gp.playState;
						}	
						break;			
					case "up":
					case "upLeft": 
					case "upright":
						 if (gp.obj[objectIndex].worldY <= gp.player.worldY) 				
								gp.obj[objectIndex].worldY += 5;				
						else {
							alive = false;
							gp.gameState = gp.playState;
						}	
						 break;			
					case "right": 
						if (gp.obj[objectIndex].worldX >= gp.player.worldX) 				
							gp.obj[objectIndex].worldX -= 5;				
						else {
							alive = false;
							gp.gameState = gp.playState;
						}	
						break;
					case "left": 
						if (gp.obj[objectIndex].worldX <= gp.player.worldX) 				
							gp.obj[objectIndex].worldX += 5;				
						else {
							alive = false;							
							gp.gameState = gp.playState;
						}	
						break;
				}						
			}
			// PULL PLAYER TOWARDS TILE
			else if (collisionOn) {
				
				hookGrab = true;
				
				switch (direction) {
					case "up": 
					case "upleft": 
					case "upright": 
						if (gp.player.worldY >= worldY) 		
							gp.player.worldY -= 5;							
						else {
							gp.player.worldX = worldX;
							gp.player.worldY = worldY;
							alive = false;
							gp.gameState = gp.playState;
						}	
						break;			
					case "down":
					case "downleft": 
					case "downright":
						 if (gp.player.worldY <= worldY) 				
								gp.player.worldY += 5;				
						else {
							gp.player.worldX = worldX;
							gp.player.worldY = worldY;
							alive = false;
							gp.gameState = gp.playState;
						}	
						 break;			
					case "left": 
						if (gp.player.worldX >= worldX) 				
							gp.player.worldX -= 5;				
						else {
							gp.player.worldX = worldX;
							gp.player.worldY = worldY;
							alive = false;
							gp.gameState = gp.playState;
						}	
						break;
					case "right": 
						if (gp.player.worldX <= worldX) 				
							gp.player.worldX += 5;				
						else {
							gp.player.worldX = worldX;
							gp.player.worldY = worldY;
							alive = false;							
							gp.gameState = gp.playState;
						}	
						break;
				}	
			}
			// HOOKSHOT REACHES MAX LENGTH
			else if (life <= 0) {
				switch (direction) {
					case "down": 
					case "downleft": 
					case "downright": 
						if (worldY >= gp.player.worldY) 
							worldY -= 5;						
						else {
							alive = false;
							gp.gameState = gp.playState;
						}	
						break;			
					case "up":
					case "upLeft": 
					case "upright":
						 if (worldY <= gp.player.worldY) 				
								worldY += 5;				
						else {
							alive = false;
							gp.gameState = gp.playState;
						}	
						 break;			
					case "right": 
						if (worldX >= gp.player.worldX) 				
							worldX -= 5;				
						else {
							alive = false;
							gp.gameState = gp.playState;
						}	
						break;
					case "left": 
						if (worldX <= gp.player.worldX) 				
							worldX += 5;				
						else {
							alive = false;							
							gp.gameState = gp.playState;
						}	
						break;
				}		
			}
			// NO OBJECT HIT
			else {
				// MOVE IN DIRECTION SHOT
				switch (direction) {
					case "up": 
					case "upleft": 
					case "upright": 
						worldY -= speed; break;			
					case "down":
					case "downleft": 
					case "downright":
						 worldY += speed; break;			
					case "left": worldX -= speed; break;
					case "right": worldX += speed; break;
				}
				
				life--;
			}
		}
		
	}
	
	// NOT CALLED
	public boolean haveResource(Entity user) {		
		boolean hasResource = false;		
		return hasResource;
	}
	// NOT CALLED
	public void subtractResource(Entity user) {
	}
}