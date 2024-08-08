package entity.projectile;

import java.awt.Rectangle;

import application.GamePanel;

public class PRJ_Spear extends Projectile {

	public static final String prjName = "Spear";
	
	public PRJ_Spear(GamePanel gp) {
		super(gp);
		
		type = type_projectile;
		name = prjName;
		speed = 6; 		
		attack = 4; 	
		knockbackPower = 0;
		useCost = 1;		
		maxLife = 90; life = maxLife;
		alive = false;
		
		hitbox = new Rectangle(12, 16, 24, 24);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {
		up1 = setup("/projectiles/spear_up_1");
		up2 = up1;
		down1 = setup("/projectiles/spear_down_1");
		down2 = down1;
		left1 = setup("/projectiles/spear_left_1");
		left2 = left1;
		right1 = setup("/projectiles/spear_right_1");
		right2 = right1;	
	}
	
	public void playSE() {
		gp.playSE(5, 7);
	}
}