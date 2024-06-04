package object;

import java.awt.Rectangle;

import entity.Projectile;
import main.GamePanel;

public class PRJ_Boomerang extends Projectile {

	GamePanel gp;
	
	public PRJ_Boomerang(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Boomerang";
		speed = 8; 		
		animationSpeed = 8;	
		knockbackPower = 1;
		maxLife = 30; // length of life (1/2 second)
		life = maxLife;			
		alive = false;
		
		hitBox = new Rectangle(12, 12, 24, 24); 		
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/projectile/boomerang_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/boomerang_down_2", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/boomerang_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/boomerang_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/boomerang_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/boomerang_down_2", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/boomerang_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/boomerang_down_2", gp.tileSize, gp.tileSize);
	}
}