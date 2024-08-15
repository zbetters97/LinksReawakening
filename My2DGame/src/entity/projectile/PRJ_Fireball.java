package entity.projectile;

import java.awt.Color;
import java.awt.Rectangle;

import application.GamePanel;

public class PRJ_Fireball extends Projectile {

	public static final String prjName = "Fireball";
	
	public PRJ_Fireball(GamePanel gp) {
		super(gp);
		
		type = type_projectile;
		name = prjName;
		maxLife = 60; life = maxLife;
		speed = 7; 
		attack = 2;		
		alive = false;
		
		hitbox = new Rectangle(12, 12, 24, 24); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {
		up1 = down1 = left1 = right1 = setup("/projectiles/fireball_down_1");
		up2 = down2 = left2 = right2 = setup("/projectiles/fireball_down_2");
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
	
	public void playSE() {
		gp.playSE(4, 8);
	}
}