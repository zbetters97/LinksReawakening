package object;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

import entity.*;
import main.GamePanel;

public class PRJ_Bomb extends Projectile {

	GamePanel gp;
	
	public PRJ_Bomb(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Bomb";
		animationSpeed = 15;
		speed = (int)(gp.tileSize / 1.5); // for collision calculation (1 tile over)
		attack = 2;
		knockbackPower = 5;
		useCost = 1; // 1 bomb to throw 1 bomb
		maxLife = 180; // length of life (3 seconds)
		life = maxLife;
		alive = false;
		
		// SMALLER HITBOX
		hitBox = new Rectangle(12, 16, 24, 24); 		
		hitBoxDefaultX = hitBox.x;
	    hitBoxDefaultY = hitBox.y;
		hitBoxDefaultWidth = hitBox.width;
		hitBoxDefaultHeight = hitBox.height;
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/projectile/bomb_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/bomb_down_2", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/bomb_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/bomb_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/bomb_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/bomb_down_2", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/bomb_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/bomb_down_2", gp.tileSize, gp.tileSize);		
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
	
	public void explode() {

		gp.playSE(3, 8);
		generateParticle(this, this);
		
		// DAMAGE SURROUNDING ENEMIES
		ArrayList<Integer> enemyIndexes = gp.cChecker.checkExplosion(this, gp.enemy);
		if (enemyIndexes.size() > 0) {
			for (Integer e : enemyIndexes) 
				gp.player.damageEnemy(e, attack, knockbackPower);						
		}
		
		// DAMAGE SURROUNDING iTILES
		ArrayList<Integer> iTileIndexes = gp.cChecker.checkiTileExplosion(this);
		if (iTileIndexes.size() > 0) {
			for (Integer i : iTileIndexes) {

				gp.iTile[gp.currentMap][i].playSE();
				gp.iTile[gp.currentMap][i].life--;
				gp.iTile[gp.currentMap][i].invincible = true;
						
				generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);
				
				if (gp.iTile[gp.currentMap][i].life == 0)
					gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();				
			}
		}			
		
		// DAMAGE PLAYER
		boolean contactPlayer = gp.cChecker.checkExplosion(this);
		if (contactPlayer && !gp.player.invincible) 
			damagePlayer(attack);				
		
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