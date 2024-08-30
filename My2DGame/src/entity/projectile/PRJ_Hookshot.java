package entity.projectile;

import java.awt.Color;
import java.awt.Rectangle;

import application.GamePanel;
import entity.Entity;
import entity.Particle;

public class PRJ_Hookshot extends Projectile {

	public static final String prjName = "Hookshot Projectile";
	
	public PRJ_Hookshot(GamePanel gp) {
		super(gp);
		
		type = type_projectile;
		name = prjName;
		canStun = true;
		
		maxLife = 30; life = maxLife;		
		speed = 10;
		knockbackPower = 0;		
		alive = false;

		hitbox = new Rectangle(16, 16, 16, 16); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
		
		getGrabImage();
	}
	
	public void getImage() {		
		up1 = up2 = setup("/projectiles/hookshot_up_1");
		down1 = down2 = setup("/projectiles/hookshot_down_1");
		left1 = left2 = setup("/projectiles/hookshot_left_1");
		right1 = right2 = setup("/projectiles/hookshot_right_1");
	}
	public void getGrabImage() {
		grabUp1 = setup("/projectiles/hookshot_grab_up_1");
		grabDown1 = setup("/projectiles/hookshot_grab_down_1");
		grabLeft1 = setup("/projectiles/hookshot_grab_left_1");
		grabRight1 = setup("/projectiles/hookshot_grab_right_1");
	}
	
	public void generateParticle(Entity generator) {
		
		// BLACK CHAIN
		Color color = new Color(0,0,0); 
		int size = 9; // 9px
		
		// CHAIN (does not move)
		Particle p1 = new Particle(gp, generator, color, size, 0, 90, 0, 0);
		gp.particleList.add(p1);
	}
	
	public void playSE() {
		gp.playSE(5, 8);
	}
}