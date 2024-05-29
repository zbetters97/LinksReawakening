package object;

import java.awt.Color;

import entity.Projectile;
import main.GamePanel;

public class PRJ_Fireball extends Projectile {

	GamePanel gp;
	
	public PRJ_Fireball(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Fireball";
		speed = 8; // speed of travel
		maxLife = 60; // length of life (1 second)
		life = maxLife;
		attack = 2; // damage dealt		
		useCost = 1; // 1 arrow to shoot 1 arrow
		alive = false;
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/projectile/fireball_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/fireball_down_2", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/fireball_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/fireball_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/fireball_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/fireball_down_2", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/fireball_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/fireball_down_2", gp.tileSize, gp.tileSize);
	}
	
	public Color getParticleColor() {
		Color color = new Color(240,50,0); // RED
		return color;
	}
	
	public int getParticleSize() {		
		int size = 10; // 10px
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