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
		
		knockbackPower = 1;
		speed = 8; 		
		animationSpeed = 8;			
		maxLife = 30; life = maxLife;			
		alive = false;
		
		hitBox = new Rectangle(12, 12, 24, 24); 		
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/projectile/boomerang_down_1");
		up2 = setup("/projectile/boomerang_down_2");
		down1 = up1;
		down2 = up2;
		left1 = up1;
		left2 = up2;
		right1 = up1;
		right2 = up2;
	}
	
	public void playSE() {
		gp.playSE(3, 9);
	}
}