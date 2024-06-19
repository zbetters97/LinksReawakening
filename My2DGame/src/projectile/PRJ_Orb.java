package projectile;

import java.awt.Rectangle;

import entity.Projectile;
import main.GamePanel;

public class PRJ_Orb extends Projectile {

	GamePanel gp;
	public static final String prjName = "Magical Orb";
	
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
	
	public void playSE() {
		gp.playSE(3, 16);
	}
}