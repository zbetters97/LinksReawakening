package entity.projectile;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

import application.GamePanel;
import entity.*;
import tile.tile_interactive.IT_Switch;

public class PRJ_Bomb extends Projectile {

	GamePanel gp;
	public static final String prjName = "Bomb";
	
	public PRJ_Bomb(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_projectile;
		name = prjName;

		capturable = true;
		collision = false;
		
		animationSpeed = 30;
		defaultSpeed = (int)(gp.tileSize / 2); speed = defaultSpeed; //COLLISION DETECTING 
		attack = 2;
		useCost = 1;
		maxLife = 240; life = maxLife;
		alive = false;
		
		hitbox = new Rectangle(12, 16, 24, 24); 		
		hitboxDefaultX = hitbox.x;
	    hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/projectile/bomb_down_1");
		up2 = setup("/projectile/bomb_down_2");
		down1 = up1;
		down2 = up2;
		left1 = up1;
		left2 = up2;
		right1 = up1;
		right2 = up2;
	}

	public boolean hasResource(Entity user) {
		
		boolean hasResource = false;
		
		if (user.bombs >= useCost) 
			hasResource = true;		
		
		return hasResource;
	}	
	public void subtractResource(Entity user) {
		user.bombs -= useCost;
	}
	
	// ONLY PLAYER CAN PUSH BOMB
	public void interact() {
		
		// CHANGE BOMB ATTRIBUTES FOR COLLISION DETECTION
		collisionOn = false;
		direction = gp.player.direction;
		speed = 15;				
		
		// IF PLAYER IS NOT TOUCHING BOMB				
		boolean contactPlayer = gp.cChecker.checkPlayer(this);				
		if (!contactPlayer) {					
			
			gp.cChecker.checkTile(this);				
			gp.cChecker.checkPit(this, false);
			gp.cChecker.checkEntity(this, gp.iTile);				
			gp.cChecker.checkEntity(this, gp.npc);
			gp.cChecker.checkEntity(this, gp.enemy);
			gp.cChecker.checkEntity(this, gp.enemy_r);
			
			// PUSH BOMB IF NO COLLISION
			if (!collisionOn) {
				playMoveObjectSE();
				switch (gp.player.direction) {
					case "up": 
					case "upleft":
					case "upright": worldY -= gp.tileSize / 3; break;
					case "down": 
					case "downleft":
					case "downright": worldY += gp.tileSize / 3; break;
					case "left": worldX -= gp.tileSize / 3; break;
					case "right": worldX += gp.tileSize / 3; break;
				}
			}
		}
	}
	
	public void explode() {
		
		gp.playSE(5, 5);
		generateParticle(this, this);
		
		// DAMAGE SURROUNDING ENEMIES
		ArrayList<Integer> enemyIndexes = gp.cChecker.checkExplosion(this, gp.enemy);
		if (enemyIndexes.size() > 0) {
			for (Integer e : enemyIndexes) 
				gp.player.damageEnemy(e, this, attack, knockbackPower);						
		}
		enemyIndexes = gp.cChecker.checkExplosion(this, gp.enemy_r);
		if (enemyIndexes.size() > 0) {
			for (Integer e : enemyIndexes) 
				gp.player.damageEnemy(e, this, attack, knockbackPower);						
		}
		
		// DAMAGE SURROUNDING iTILES
		ArrayList<Integer> iTileIndexes = gp.cChecker.checkiTileExplosion(this);
		if (iTileIndexes.size() > 0) {
			for (Integer i : iTileIndexes) {
				
				generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);
				
				if (gp.iTile[gp.currentMap][i].name.equals(IT_Switch.itName) && !gp.iTile[gp.currentMap][i].invincible) {
					gp.iTile[gp.currentMap][i].invincible = true;
					gp.iTile[gp.currentMap][i].interact();
				}
				else {
					gp.iTile[gp.currentMap][i].playSE();
					gp.iTile[gp.currentMap][i].life--;
					gp.iTile[gp.currentMap][i].invincible = true;
					
					if (gp.iTile[gp.currentMap][i].life == 0) {
						gp.iTile[gp.currentMap][i].checkDrop();
						gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();	
					}			
				}
			}
		}			
		
		// DAMAGE PLAYER
		boolean contactPlayer = gp.cChecker.checkExplosion(this);
		if (contactPlayer && !gp.player.invincible) 
			damagePlayer(attack);				
		
		speed = defaultSpeed;
		active = false;
		alive = false;	
	}

	public Color getParticleColor() {
		Color color = new Color(0,0,0); // BLACK
		return color;
	}	
	public int getParticleSize() {		
		int size = 7; // 7px
		return size;
	}	
	public int getParticleSpeed() {
		int speed = 1;
		return speed;		
	}	
	public int getParticleMaxLife() {
		int maxLife = 20; // 20 frames
		return maxLife;
	}
}