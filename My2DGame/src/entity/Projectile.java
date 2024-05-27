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
	
	// NOT CALLED
	public boolean haveResource(Entity user) {		
		boolean hasResource = false;		
		return hasResource;
	}
	// NOT CALLED
	public void subtractResource(Entity user) {
	}
}