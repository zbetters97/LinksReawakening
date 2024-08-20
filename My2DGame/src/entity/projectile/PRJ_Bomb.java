package entity.projectile;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

import application.GamePanel;
import entity.Entity;
import tile.tile_interactive.IT_Switch;

public class PRJ_Bomb extends Projectile {

	public static final String prjName = "Bomb Projectile";
	
	public PRJ_Bomb(GamePanel gp) {
		super(gp);
		
		type = type_projectile;
		name = prjName;
		canStun = true;
		grabbable = true;
		capturable = true;
		collision = false;
		
		animationSpeed = 30;
		defaultSpeed = (int)(gp.tileSize / 2); speed = defaultSpeed; //COLLISION DETECTING 
		attack = 6;
		knockbackPower = 2;
		useCost = 1;
		maxLife = 240; life = maxLife;
		alive = false;
		
		hitbox = new Rectangle(12, 15, 24, 27); 		
		hitboxDefaultX = hitbox.x;
	    hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {
		up1 = setup("/projectiles/bomb_down_1");
		up2 = setup("/projectiles/bomb_down_2");
		down1 = up1;
		down2 = up2;
		left1 = up1;
		left2 = up2;
		right1 = up1;
		right2 = up2;
	}
	
	public void update() {
		super.update();
				
		if (grabbed) grabbed();		
		else if (thrown) thrown();
	}
	
	private void grabbed() {
		worldX = gp.player.worldX;
		worldY = gp.player.worldY - gp.tileSize + 10;
		collision = false;
		xT = worldX;
		yT = worldY;
	}
	private void thrown() {
		speed = 0;
		if (tossEntity()) {									
			
			gp.cChecker.checkHazard(this, false);
			
			thrown = false;
			throwCounter = 0;
			tTime = 0;
			collisionOn = false;

			gp.player.action = Action.IDLE;
			gp.player.grabbedObject = null;			
			gp.player.throwCounter = 0;
			gp.player.throwNum = 1;
			
			if (alive) {	
				Entity enemy = getEnemy(this);		
				if (enemy != null) { explode(); }	
			}
		}
	}
	
	public void explode() {		
		gp.playSE(5, 5);
		
		generateRectParticle(this);
		
		// DAMAGE SURROUNDING ENEMIES
		ArrayList<Entity> enemyIndexes = gp.cChecker.checkExplosion(this, gp.enemy);
		if (enemyIndexes.size() > 0) {
			for (Entity e : enemyIndexes) 
				gp.player.damageEnemy(e, this, attack, knockbackPower);						
		}
		// DAMAGE SURROUNDING BOMBS
		ArrayList<Entity> bombIndexes = gp.cChecker.checkExplosion(this, gp.projectile);
		if (bombIndexes.size() > 0) {
			for (Entity e : bombIndexes) {
				if (e.name.equals(name) && e != this) {
					e.life = 0;
				}
			}									
		}
		// DAMAGE SURROUNDING iTILES
		ArrayList<Integer> iTileIndexes = gp.cChecker.checkiTileExplosion(this);
		if (iTileIndexes.size() > 0) {
			for (Integer i : iTileIndexes) {
				
				generateRectParticle(gp.iTile[gp.currentMap][i]);
				
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
		boolean contactPlayer = gp.cChecker.checkPlayerExplosion(this);
		if (contactPlayer && !gp.player.invincible) 
			damagePlayer(attack);				
		
		if (gp.player.grabbedObject == this && gp.player.action != Action.IDLE) {
			gp.player.action = Action.IDLE;
			gp.player.grabbedObject = null;
		}
		
		resetValues();
	}
	
	public void attacking() {
		attacking = false;
		explode();
	}
	
	public void resetValues() {		
		alive = false;	
		active = false;
		captured = false;
		grabbed = false;
		thrown = false;
		
		speed = defaultSpeed;
		animationSpeed = 30;
		spriteNum = 1;
		throwCounter = 0;
		tTime = 0;		
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