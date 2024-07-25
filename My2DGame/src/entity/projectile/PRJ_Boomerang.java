package entity.projectile;

import java.awt.Rectangle;

import application.GamePanel;

public class PRJ_Boomerang extends Projectile {

	public static final String prjName = "PRJ Boomerang";
	
	public PRJ_Boomerang(GamePanel gp) {
		super(gp);
						
		type = type_projectile;
		name = prjName;
		canStun = true;

		knockbackPower = 1;
		speed = 8; 		
		animationSpeed = 3;			
		maxLife = 30; life = maxLife;			
		alive = false;
		
		hitbox = new Rectangle(12, 12, 24, 24); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
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
		gp.playSE(5, 2);
	}
}