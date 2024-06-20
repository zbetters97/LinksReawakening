package entity;

import main.GamePanel;
import projectile.*;

public class Projectile extends Entity {

	public Entity user;
	public boolean active = false;
	public boolean grabbable = false;
	
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
		
		// PREVENT GLITCH WHEN RECEIVING AN ITEM
		if (gp.gameState == gp.itemGetState) {
			alive = false;
			return;
		}
		
		// PREVENT GLITCHED DEATH WHEN ITEM IS IN AIR
		if (gp.gameState != gp.gameOverState) {
			
			if (name.equals(PRJ_Arrow.prjName)) useArrow();
			else if (name.equals(PRJ_Bomb.prjName)) useBomb();
			else if (name.equals(PRJ_Boomerang.prjName)) useBoomerang();
			else if (name.equals(PRJ_Hookshot.prjName)) useHookshot();
			else if (name.equals(PRJ_Orb.prjName)) useRod();
			else useProjectile();
		}
	}	
	
	public void useArrow() {
		
		// CHECK TILE COLLISION
		collisionOn = false;		
		gp.cChecker.checkTile(this);		
		gp.cChecker.checkEntity(this, gp.iTile);		
		
		// SHOT BY PLAYER
		if (user == gp.player) {
			
			int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
			if (enemyIndex != -1) {
				gp.player.damageEnemy(enemyIndex, this, attack, knockbackPower);
				alive = false;
			}
									
			int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
			if (projectileIndex != -1) {
				Entity projectile = gp.projectile[gp.currentMap][projectileIndex];	
				
				if (projectile.name.equals(PRJ_Bomb.prjName))
					projectile.explode();
				
				alive = false;
			}	
		}
		// SHOT BY ENEMEY
		else {
			boolean contactPlayer = gp.cChecker.checkPlayer(this);
			if (contactPlayer && !gp.player.invincible) {
				damagePlayer(attack);
				alive = false;
			}
		}
		
		if (!collisionOn) {
			
			grabbable = false;		
			
			// MOVE IN DIRECTION SHOT
			switch (direction) {
				case "up": 
				case "upleft": 
				case "upright": worldY -= speed; break;			
				case "down":
				case "downleft": 
				case "downright": worldY += speed; break;			
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
			}
		}
		// KILL PROJECTILE IF COLLISION
		else {
			alive = false;
		}
		
		life--;
		if (life <= 0) { // REMOVE AFTER X FRAMES
			alive = false;
		}
		
		// MOVING ANIMATION
		spriteCounter++;
		if (spriteCounter > animationSpeed) { // speed of sprite change
			
			if (spriteNum == 1) spriteNum = 2;
			else if (spriteNum == 2) spriteNum = 1;
			
			spriteCounter = 0;
		}		
	}
	
	public void useBomb() {
		
		if (!active) {
						
			// CHECK TILE COLLISION
			collisionOn = false;		
			gp.cChecker.checkTile(this);		
			gp.cChecker.checkEntity(this, gp.iTile);
			
			if (!collisionOn) {
				
				// PLACED IN DIRECTION FACING
				switch (direction) {
					case "up": 
					case "upleft": 
					case "upright": worldY -= gp.tileSize / 1.5; break;			
					case "down":
					case "downleft": 
					case "downright": worldY += gp.tileSize / 1.5; break;			
					case "left": worldX -= gp.tileSize / 1.5; break;
					case "right": worldX += gp.tileSize / 1.5; break;
				}
			}
			else {
				worldX = gp.player.worldX;
				worldY = gp.player.worldY;
			}
			
			active = true;
		}
		else {
			
			// FUSE ANIMATION
			spriteCounter++;
			if (spriteCounter > animationSpeed) { // speed of sprite change
				
				if (spriteNum == 1) spriteNum = 2;
				else if (spriteNum == 2) spriteNum = 1;
				
				spriteCounter = 0;
				animationSpeed -= 3; // INCREASING SWAP SPEED
			}
			
			// REMOVE AFTER X FRAMES
			life--;
			if (life <= 0) { 
				explode();
				animationSpeed = 30;
				active = false;
				alive = false;				
			}
		}
	}
	
	public void useBoomerang() {
		
		// PAUSE PLAYER INPUT
		gp.gameState = gp.objectState;
		
		// CHECK TILE/NPC/ENEMY/OBJECT COLLISION
		collisionOn = false;		
		gp.cChecker.checkTile(this);		
		gp.cChecker.checkEntity(this, gp.iTile);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.enemy);
		gp.cChecker.checkObject(this, false);	
						
		int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
		if (enemyIndex != -1) 
			gp.player.damageEnemy(enemyIndex, this, attack, knockbackPower);
		
		// OBJECT IS NOT GRABBABLE, RETURN
		int objectIndex = gp.cChecker.checkObject(this, true);
		if (collisionOn) 
			life = 0;
		
		int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
		if (projectileIndex != -1) {
			Entity projectile = gp.projectile[gp.currentMap][projectileIndex];
			
			if (projectile.name.equals(PRJ_Bomb.prjName))
				projectile.explode();
			
			life = 0;
		}
		
		// MAX LENGTH REACHED OR OBJECT RECIEVED
		if (life <= 0 || objectIndex != -1) {	
			
			// PULL OBJECT TOWARDS PLAYER
			if (objectIndex != -1) {				
				gp.obj[gp.currentMap][objectIndex].worldX = worldX;
				gp.obj[gp.currentMap][objectIndex].worldY = worldY;
			}		
			
			switch (direction) {
				case "up":
				case "upleft": 
				case "upright":	
					if (worldY + gp.tileSize / 2 <= gp.player.worldY) 				
							worldY += 5;				
					else {
						alive = false;
						gp.gameState = gp.playState;
					}					
					break;			
				case "down": 
				case "downleft": 
				case "downright": 	
					if (worldY - gp.tileSize / 2 >= gp.player.worldY) 
						worldY -= 5;						
					else {
						alive = false;
						gp.gameState = gp.playState;
					}						
					break;	
				case "left": 
					if (worldX + gp.tileSize / 2 <= gp.player.worldX) 				
						worldX += 5;			
					else {						
						alive = false;	
						gp.gameState = gp.playState;
					}						
					break;	
				case "right": 
					if (worldX - gp.tileSize / 2 >= gp.player.worldX) 				
						worldX -= 5;				
					else {
						alive = false;
						gp.gameState = gp.playState;
					}						
					break;				
			}		
		}
		else {
			// MOVE IN DIRECTION SHOT
			switch (direction) {
				case "up": 
				case "upleft": 
				case "upright": worldY -= speed; break;			
				case "down":
				case "downleft": 
				case "downright": worldY += speed; break;			
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
			}	

			life--;				
		}			

		// MOVING ANIMATION
		spriteCounter++;
		if (spriteCounter > animationSpeed) { // speed of sprite change
			playSE();
			
			if (spriteNum == 1) spriteNum = 2;
			else if (spriteNum == 2) spriteNum = 1;
			
			spriteCounter = 0;
		}	
	}
	
	public void useHookshot() {
				
		// PAUSE PLAYER INPUT
		gp.gameState = gp.objectState;
						
		// CHECK TILE/NPC/ENEMY/OBJECT COLLISION
		collisionOn = false;		
		gp.cChecker.checkTile(this);		
		gp.cChecker.checkEntity(this, gp.iTile);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.enemy);
		gp.cChecker.checkObject(this, false);		
		
		int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
		if (enemyIndex != -1) 
			gp.player.damageEnemy(enemyIndex, this, attack, knockbackPower);
		
		int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);	
		int objectIndex = gp.cChecker.checkObject(this, true);
				
		// OBJECT IS NOT GRABBABLE, RETURN
		if (collisionOn && iTileIndex == -1) 
			life = 0;
				
		// PULL PLAYER TOWARDS GRABBALE TILE
		if (iTileIndex != -1 && gp.iTile[gp.currentMap][iTileIndex].grabbale) {
			
			// PREVENT COLLISION WITH PLAYER
			int playeriTileIndex = gp.cChecker.checkEntity(gp.player, gp.iTile);
			if (playeriTileIndex != -1) {
				alive = false;	
				gp.gameState = gp.playState;	
				return;
			}
			
			switch (direction) {
				case "up": 
				case "upleft": 
				case "upright": 
					for (int i = 0; i < gp.particleList.size(); i++) {
						if (gp.particleList.get(i).worldY >= gp.player.worldY) 
							gp.particleList.get(i).alive = false;
					}
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
					for (int i = 0; i < gp.particleList.size(); i++) {
						if (gp.particleList.get(i).worldY <= gp.player.worldY) 
							gp.particleList.get(i).alive = false;
					}					
					if (gp.player.worldY <= worldY) {		
						 gp.player.worldY += 5;														
					}
					else {
						gp.player.worldX = worldX;
						gp.player.worldY = worldY;
						alive = false; 
						gp.gameState = gp.playState;
					}					
					break;			
				case "left": 					
					for (int i = 0; i < gp.particleList.size(); i++) {
						if (gp.particleList.get(i).worldX >= gp.player.worldX) 
							gp.particleList.get(i).alive = false;
					}
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
					for (int i = 0; i < gp.particleList.size(); i++) {
						if (gp.particleList.get(i).worldX <= gp.player.worldX) 
							gp.particleList.get(i).alive = false;
					}
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
		// MAX LENGTH REACHED OR OBJECT GRABBED
		else if (life <= 0 || objectIndex != -1) {	
			
			// PULL OBJECT TOWARDS PLAYER
			if (objectIndex != -1) {
				hookGrab = true;					
				gp.obj[gp.currentMap][objectIndex].worldX = worldX;
				gp.obj[gp.currentMap][objectIndex].worldY = worldY;
				
				// STOP COLLISION WITH PLAYER
				int playerObjIndex = gp.cChecker.checkObject(gp.player, true);
				if (playerObjIndex != -1) {
					alive = false;			
					hookGrab = false;
					gp.gameState = gp.playState;	
					return;
				}
			}			
						
			switch (direction) {
				case "up":
				case "upleft": 
				case "upright":					
					for (int i = 0; i < gp.particleList.size(); i++) {
						if (gp.particleList.get(i).worldY <= worldY) 
							gp.particleList.get(i).alive = false;
					}
					if (worldY + gp.tileSize / 2 <= gp.player.worldY) 				
							worldY += 5;				
					else {
						alive = false;
						hookGrab = false;
						gp.gameState = gp.playState;
					}					
					break;			
				case "down": 
				case "downleft": 
				case "downright": 					
					for (int i = 0; i < gp.particleList.size(); i++) {
						if (gp.particleList.get(i).worldY >= worldY) 
							gp.particleList.get(i).alive = false;
					}
					if (worldY - gp.tileSize / 2 >= gp.player.worldY) 
						worldY -= 5;						
					else {
						alive = false;
						hookGrab = false;
						gp.gameState = gp.playState;
					}						
					break;	
				case "left": 										
					for (int i = 0; i < gp.particleList.size(); i++) {
						if (gp.particleList.get(i).worldX <= worldX) 
							gp.particleList.get(i).alive = false;
					}
					if (worldX + gp.tileSize / 2 <= gp.player.worldX) 				
						worldX += 5;			
					else {						
						alive = false;	
						hookGrab = false;
						gp.gameState = gp.playState;
					}						
					break;	
				case "right": 
					for (int i = 0; i < gp.particleList.size(); i++) {
						if (gp.particleList.get(i).worldX >= worldX)
							gp.particleList.get(i).alive = false;
					}
					if (worldX - gp.tileSize / 2 >= gp.player.worldX) 				
						worldX -= 5;				
					else {
						alive = false;
						hookGrab = false;
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
				case "upright": worldY -= speed; break;			
				case "down":
				case "downleft": 
				case "downright": worldY += speed; break;			
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
			}	
			
			life--;
			
			// DRAW CHAIN AND PLAY SOUND
			if (life % 5 == 0) {
				generateParticle(this, this);
				playSE();
			}
		}
	}
	
	public void useRod() {
		
		// CHECK TILE COLLISION
		collisionOn = false;		
		gp.cChecker.checkTile(this);		
		gp.cChecker.checkEntity(this, gp.iTile);		

		int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
		if (enemyIndex != -1) {
			if (gp.enemy[gp.currentMap][enemyIndex].isCapturable && 
					!gp.enemy[gp.currentMap][enemyIndex].captured) {
				gp.enemy[gp.currentMap][enemyIndex].captured = true;
				gp.player.capturedTarget = gp.enemy[gp.currentMap][enemyIndex];
			}
			playSE();
			alive = false;
		}
		
		if (!collisionOn) {		
			
			// MOVE IN DIRECTION SHOT
			switch (direction) {
				case "up": 
				case "upleft": 
				case "upright": worldY -= speed; break;			
				case "down":
				case "downleft": 
				case "downright": worldY += speed; break;			
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
			}
		}
		// KILL PROJECTILE IF COLLISION
		else {
			alive = false;
		}
		
		life--;
		if (life <= 0) { // REMOVE AFTER X FRAMES
			alive = false;
		}
		
		// MOVING ANIMATION
		spriteCounter++;
		if (spriteCounter > animationSpeed) { // speed of sprite change
			
			if (spriteNum == 1) spriteNum = 2;
			else if (spriteNum == 2) spriteNum = 1;
			
			spriteCounter = 0;
		}		
	}
	
	public void useProjectile() {
		
		// CHECK TILE COLLISION
		collisionOn = false;		
		gp.cChecker.checkTile(this);		
		gp.cChecker.checkEntity(this, gp.iTile);		
		
		// SHOT BY PLAYER
		if (user == gp.player) {
			
			int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
			if (enemyIndex != -1) {
				gp.player.damageEnemy(enemyIndex, this, attack, knockbackPower);
				alive = false;
			}
			
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);			
			if (iTileIndex != -1 && gp.iTile[gp.currentMap][iTileIndex].pressable && 
					gp.iTile[gp.currentMap][iTileIndex].isCorrectItem(this)) {		
				
				gp.iTile[gp.currentMap][10] = null;
				gp.iTile[gp.currentMap][11] = null;
			}
			
			int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
			if (projectileIndex != -1) {
				Entity projectile = gp.projectile[gp.currentMap][projectileIndex];
				
				if (projectile.name.equals(PRJ_Bomb.prjName))
					projectile.explode();
				
				alive = false;
			}
			
			// NO COLLISION FOR SWORD BEAM
			if (name.equals(PRJ_Sword.prjName)) 			
				collisionOn = false;	
		}
		// SHOT BY ENEMEY
		else {
			boolean contactPlayer = gp.cChecker.checkPlayer(this);
			if (contactPlayer && !gp.player.invincible) {
				damagePlayer(attack);
				alive = false;
			}
		}
		
		if (!collisionOn) {
			
			grabbable = false;		
			
			// MOVE IN DIRECTION SHOT
			switch (direction) {
				case "up": 
				case "upleft": 
				case "upright": worldY -= speed; break;			
				case "down":
				case "downleft": 
				case "downright": worldY += speed; break;			
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
			}
		}
		// KILL PROJECTILE IF COLLISION
		else {	
			alive = false;
		}
		
		life--;
		if (life <= 0) { // REMOVE AFTER X FRAMES
			grabbable = false;
			alive = false;
		}
		
		// MOVING ANIMATION
		spriteCounter++;
		if (spriteCounter > animationSpeed) { // speed of sprite change
			
			if (spriteNum == 1) spriteNum = 2;
			else if (spriteNum == 2) spriteNum = 1;
			
			spriteCounter = 0;
		}		
	}
	
	// NOT CALLED
	public boolean hasResource(Entity user) {		
		boolean hasResource = false;		
		return hasResource;
	}
	public void subtractResource(Entity user) { }
	public void interact() { }
}