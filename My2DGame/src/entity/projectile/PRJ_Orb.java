package entity.projectile;

import java.awt.Color;
import java.awt.Rectangle;

import application.GamePanel;

public class PRJ_Orb extends Projectile {

	public static final String prjName = "Magical Orb";
	GamePanel gp;	
	
	public PRJ_Orb(GamePanel gp) {
		super(gp);
		this.gp = gp;
						
		type = type_projectile;
		name = prjName;
		knockbackPower = 0;
		speed = 6; 		
		animationSpeed = 8;			
		maxLife = 40; life = maxLife;			
		alive = false;
		
		hitbox = new Rectangle(12, 12, 24, 24); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/projectile/rod_down_1");
		up2 = setup("/projectile/rod_down_2");
		down1 = up1;
		down2 = up2;
		left1 = up1;
		left2 = up2;
		right1 = up1;
		right2 = up2;
	}
	
	public Color getParticleColor() {
		Color color = new Color(25,132,255); // LIGHT BLUE
		return color;
	}	
	public int getParticleSize() {		
		int size = 6; // 6px
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
		gp.playSE(3, 16);
	}
}