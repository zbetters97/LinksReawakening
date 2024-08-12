package entity.projectile;

import java.awt.Rectangle;

import application.GamePanel;

public class PRJ_Arrow extends Projectile {

	public static final String prjName = "Arrow Projectile";
	
	public PRJ_Arrow(GamePanel gp) {
		super(gp);
		
		type = type_projectile;
		name = prjName;
		speed = 6; 		
		attack = 3; 	
		knockbackPower = 0;
		useCost = 1;		
		maxLife = 120; life = maxLife;
		alive = false;
		
		hitbox = new Rectangle(12, 16, 24, 24);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {
		up1 = up2 = setup("/projectiles/arrow_up_1", 35, 35);
		down1 = down2 = setup("/projectiles/arrow_down_1", 35, 35);
		left1 = left2 = setup("/projectiles/arrow_left_1", 35, 35);
		right1 = right2 = setup("/projectiles/arrow_right_1", 35, 35);
	}
	
	// PICKUP ARROW IF NOT MOVING
	public void interact() {
		if (canPickup) {
			gp.player.arrows++;
			alive = false;
			canPickup = false;
		}
	}
	
	public void resetValues() {
		attack = 2;
		speed = 6; 
		alive = false;
		canPickup = false;	
	}
}