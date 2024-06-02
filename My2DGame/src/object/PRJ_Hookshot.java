package object;

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
		speed = 10; // speed of travel
		maxLife = 35; // length of life (half length of screen)
		life = maxLife;	
		alive = false;

		// SMALLER HITBOX
		hitBox = new Rectangle(16, 16, 16, 16); 		
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		getImage();
	}
	
	public void getImage() {
		image = setup("/projectile/hookshot_grab_1", gp.tileSize, gp.tileSize);
		up1 = setup("/projectile/hookshot_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/hookshot_down_1", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/hookshot_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/hookshot_down_1", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/hookshot_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/hookshot_down_1", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/hookshot_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/hookshot_down_1", gp.tileSize, gp.tileSize);
	}
	
	public void generateParticle(Entity generator, Entity target) {

		Color color = new Color(0,0,0); // BLACK CHAIN
		int size = 8; // 8px
		int speed = 0;
		
		// CHAIN (does not move)
		Particle p1 = new Particle(gp, generator, color, size, speed, 90, 0, 0);
		gp.particleList.add(p1);
	}
}