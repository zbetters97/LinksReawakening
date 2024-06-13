package projectile;

import java.awt.Color;
import java.awt.Rectangle;

import entity.Entity;
import entity.Particle;
import entity.Projectile;
import main.GamePanel;

public class PRJ_Hookshot extends Projectile {

	GamePanel gp;
	
	public PRJ_Hookshot(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Hookshot";
		speed = 10;
		maxLife = 30; life = maxLife;	
		alive = false;

		hitbox = new Rectangle(16, 16, 16, 16); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		getImage();
	}
	
	public void getImage() {
		image1 = setup("/projectile/hookshot_grab_1", gp.tileSize, gp.tileSize);
		up1 = setup("/projectile/hookshot_down_1", gp.tileSize, gp.tileSize);
		up2 = up1;
		down1 = up1;
		down2 = up1;
		left1 = up1;
		left2 = up1;
		right1 = up1;
		right2 = up1;
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
		gp.playSE(3, 5);
	}
}