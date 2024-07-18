package entity.projectile;

import java.awt.Color;
import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;
import entity.Particle;

public class PRJ_Hookshot extends Projectile {

	public static final String prjName = "PRJ Claw";
	
	public PRJ_Hookshot(GamePanel gp) {
		super(gp);
		
		type = type_projectile;
		name = prjName;
		maxLife = 30; life = maxLife;		
		speed = 10;
		knockbackPower = 0;		
		alive = false;

		hitbox = new Rectangle(16, 16, 16, 16); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		getGrabImage();
	}
	
	public void getImage() {		
		up1 = setup("/projectile/hookshot_up_1");
		up2 = up1;
		down1 = setup("/projectile/hookshot_down_1");
		down2 = down1;
		left1 = setup("/projectile/hookshot_left_1");
		left2 = left1;
		right1 = setup("/projectile/hookshot_right_1");
		right2 = right1;
	}
	public void getGrabImage() {
		grabUp1 = setup("/projectile/hookshot_grab_up_1");
		grabDown1 = setup("/projectile/hookshot_grab_down_1");
		grabLeft1 = setup("/projectile/hookshot_grab_left_1");
		grabRight1 = setup("/projectile/hookshot_grab_right_1");
	}
	
	public void generateParticle(Entity generator, Entity target) {

		Color color = new Color(0,0,0); // BLACK CHAIN
		int size = 8; // 8px
		int speed = 0;
		
		// CHAIN (does not move)
		Particle p1 = new Particle(gp, generator, color, size, speed, 90, 0, 0);
		gp.particleList.add(p1);
	}
	
	public void playSE() {
		gp.playSE(5, 8);
	}
}