package entity.projectile;

import application.GamePanel;
import entity.Entity;

public class Projectile extends Entity {

	GamePanel gp;
	
	public Projectile(GamePanel gp) {
		super(gp);
		this.gp = gp;
	}
	
	public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {		
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.user = user;
		this.life = this.maxLife;
	}
	
	public void update() {
		
		// PREVENT GLITCH
		if (gp.gameState == gp.itemGetState || 
				gp.gameState == gp.cutsceneState || 
				gp.gameState == gp.gameOverState) {
			alive = false;
			return;
		}
		
		if (name.equals(PRJ_Arrow.prjName)) useArrow();
		else if (name.equals(PRJ_Bomb.prjName)) {
			if (!captured) useBomb(); 
			else { speed = 2; isCaptured(); }
		}
		else if (name.equals(PRJ_Boomerang.prjName)) useBoomerang();
		else if (name.equals(PRJ_Hookshot.prjName)) useHookshot();
		else if (name.equals(PRJ_Orb.prjName)) useRod();
		else useProjectile();		
	}	
	
	public void useArrow() {
						
		// CHECK TILE COLLISION
		collisionOn = false;		
		gp.cChecker.checkTile(this);		
		gp.cChecker.checkEntity(this, gp.npc);				
		gp.cChecker.checkObject(this, false);
		
		int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
		int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
		int objectIIndex = gp.cChecker.checkObject_I(this, false);		
		
		// SHOT BY PLAYER
		if (user == gp.player) {
						
			gp.player.damageInteractiveTile(iTileIndex, this);
			if (iTileIndex != -1) resetValues();
			if (objectIIndex != -1) resetValues();
			
			int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
			if (enemyIndex != -1) {
				gp.player.damageEnemy(enemyIndex, this, attack, knockbackPower);
				resetValues();
			}									
			
			if (projectileIndex != -1) {
				Entity projectile = gp.projectile[gp.currentMap][projectileIndex];	
				
				if (projectile.name.equals(PRJ_Bomb.prjName))
					projectile.explode();
				
				resetValues();			
			}			
		}
		
		// SHOT BY ENEMEY AND PLAYER ON GROUND
		else if (gp.player.onGround) {
			boolean contactPlayer = gp.cChecker.checkPlayer(this);
			
			if (contactPlayer && !gp.player.invincible) {
				damagePlayer(attack);
				resetValues();
			}			
		}
		// MOVE IN DIRECTION SHOT
		if (!canPickup) {
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
		if (collisionOn && alive) {
			attack = 2;
			speed = 6;
			canPickup = true;	
		}
		
		life--;
		if (life <= 0) { // REMOVE AFTER X FRAMES
			resetValues();
		}
	}
	
	public void useBomb() {
		
		if (!active) {
						
			// CHECK TILE COLLISION
			collisionOn = false;		
			gp.cChecker.checkTile(this);		
			gp.cChecker.checkEntity(this, gp.iTile);
			gp.cChecker.checkObject_I(this, false);
			
			if (!collisionOn) {
				
				// PLACED IN DIRECTION FACING
				switch (direction) {
					case "up": 
					case "upleft": 
					case "upright": worldY -= gp.tileSize / 2; break;			
					case "down":
					case "downleft": 
					case "downright": worldY += gp.tileSize / 2; break;			
					case "left": worldX -= gp.tileSize / 2; break;
					case "right": worldX += gp.tileSize / 2; break;
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
		gp.cChecker.checkObject_I(this, false);
		
		int objectIndex = gp.cChecker.checkObject(this, true);
		int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
		if (enemyIndex != -1) 
			gp.player.damageEnemy(enemyIndex, this, attack, knockbackPower);
				
		int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
		if (projectileIndex != -1) {
			Entity projectile = gp.projectile[gp.currentMap][projectileIndex];
			
			if (projectile.name.equals(PRJ_Bomb.prjName))
				projectile.explode();
			
			life = 0;
		}
		
		int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
		gp.player.damageInteractiveTile(iTileIndex, this);
		
		// OBJECT IS NOT canPickup, RETURN
		if (collisionOn) 
			life = 0;
		
		// MAX LENGTH REACHED OR OBJECT RECIEVED
		if (life <= 0 || objectIndex != -1) {	
			
			// PULL OBJECT TOWARDS PLAYER
			if (objectIndex != -1 && 
					gp.obj[gp.currentMap][objectIndex].type != type_obstacle &&
					gp.obj[gp.currentMap][objectIndex].type != type_obstacle_i) {				
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
						
		// CHECK TILE/iTILE/NPC/ENEMY/OBJECT/iOBJECT
		collisionOn = false;		
		gp.cChecker.checkTile(this);		
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.enemy);
		
		int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
		if (enemyIndex != -1) gp.player.damageEnemy(enemyIndex, this, attack, knockbackPower);
		
		int objectIndex = gp.cChecker.checkObject(this, true);
		int objectiIndex = gp.cChecker.checkObject_I(this, true);		
		int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);		
		gp.player.damageInteractiveTile(iTileIndex, this);
						
		// COLLISION DETECTED
		if (collisionOn)
			life = 0;
				
		// PULL PLAYER TOWARDS GRABBALE ENTITY
		if ((objectIndex != -1 && gp.obj[gp.currentMap][objectIndex].hookGrabbable) ||
				(objectiIndex != -1 && gp.obj_i[gp.currentMap][objectiIndex].hookGrabbable) ||
				(iTileIndex != -1 && gp.iTile[gp.currentMap][iTileIndex].hookGrabbable)) {

			gp.player.onGround = false;
			
			gp.cChecker.checkObject(gp.player, true);
			gp.cChecker.checkObject_I(gp.player, true);
			gp.cChecker.checkEntity(gp.player, gp.iTile);			
			if (gp.player.collisionOn) {
				alive = false;	
				gp.player.onGround = true;
				gp.gameState = gp.playState;	
				return;
			}		
			
			// PREVENT COLLISION WITH PLAYER
			int playeriTileIndex = gp.cChecker.checkEntity(gp.player, gp.iTile);
			if (playeriTileIndex != -1) {
				alive = false;	
				gp.player.onGround = true;
				gp.gameState = gp.playState;	
				return;
			}			
			int playerObjIndex = gp.cChecker.checkEntity(gp.player, gp.obj);
			if (playerObjIndex != -1) {
				alive = false;	
				gp.player.onGround = true;
				gp.gameState = gp.playState;	
				return;
			}
			int playerObjiIndex = gp.cChecker.checkEntity(gp.player, gp.obj_i);
			if (playerObjiIndex != -1) {
				alive = false;	
				gp.player.onGround = true;
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
						gp.player.onGround = true;
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
						gp.player.onGround = true;
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
						gp.player.onGround = true;
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
						gp.player.onGround = true;
						alive = false;							
						gp.gameState = gp.playState;
					}						
					break;
			}				
		}
		// MAX LENGTH REACHED OR OBJECT GRABBED
		else if (life <= 0 || objectIndex != -1) {	
			
			// PULL OBJECT TOWARDS PLAYER
			if (objectIndex != -1 && 
					gp.obj[gp.currentMap][objectIndex].type != type_obstacle &&
					gp.obj[gp.currentMap][objectIndex].type != type_obstacle_i) {
				hookGrabbed = true;					
				gp.obj[gp.currentMap][objectIndex].worldX = worldX;
				gp.obj[gp.currentMap][objectIndex].worldY = worldY;
				
				// STOP COLLISION WITH PLAYER
				int playerObjIndex = gp.cChecker.checkObject(gp.player, true);
				if (playerObjIndex != -1) {
					alive = false;			
					hookGrabbed = false;
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
						hookGrabbed = false;
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
						hookGrabbed = false;
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
						hookGrabbed = false;
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
						hookGrabbed = false;
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
		gp.cChecker.checkObject_I(this, false);

		int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
		if (projectileIndex != -1) {
			Entity projectile = gp.projectile[gp.currentMap][projectileIndex];
			
			if (projectile.name.equals(PRJ_Bomb.prjName)) {
				gp.projectile[gp.currentMap][projectileIndex].captured = true;
				gp.player.capturedTarget = gp.projectile[gp.currentMap][projectileIndex];
			}
			
			life = 0;
		}
		
		int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
		if (enemyIndex != -1) {
			if (gp.enemy[gp.currentMap][enemyIndex].capturable && 
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
		gp.cChecker.checkObject_I(this, false);
		
		// SHOT BY PLAYER
		if (user == gp.player) {
			
			int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
			if (enemyIndex != -1) {
				gp.player.damageEnemy(enemyIndex, this, attack, knockbackPower);
				alive = false;
			}
			
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);	
			gp.player.damageInteractiveTile(iTileIndex, this);
			
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
		else if (gp.player.onGround) {
			boolean contactPlayer = gp.cChecker.checkPlayer(this);
			if (contactPlayer && !gp.player.invincible) {
								
				// BOUNCE BACK PROJECTILE
				if (name.equals(PRJ_Seed.prjName)) {
					damagePlayer(attack);
				}
				else {
					damagePlayer(attack);
					alive = false;
				}
			}
		}
		
		if (!collisionOn) {
			
			canPickup = false;		
			
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
			canPickup = false;
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