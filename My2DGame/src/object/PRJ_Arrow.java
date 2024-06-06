package object;

import java.awt.Rectangle;

import entity.*;
import main.GamePanel;

public class PRJ_Arrow extends Projectile {

	GamePanel gp;
	
	public PRJ_Arrow(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Arrow";
		speed = 12; 		
		attack = 2; 	
		useCost = 1;
		maxLife = 60; life = maxLife;
		alive = false;
		
		hitBox = new Rectangle(12, 16, 32, 24);
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		hitBoxDefaultWidth = hitBox.width;
		hitBoxDefaultHeight = hitBox.height;
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/projectile/arrow_up_1", 35, 35);
		up2 = up1;
		down1 = setup("/projectile/arrow_down_1", 35, 35);
		down2 = down1;
		left1 = setup("/projectile/arrow_left_1", 35, 35);
		left2 = left1;
		right1 = setup("/projectile/arrow_right_1", 35, 35);
		right2 = right1;	
	}
	
	public boolean hasResource(Entity user) {
		
		boolean hasResource = false;
		
		if (user.arrows >= useCost || user.arrows == -1) 
			hasResource = true;		
		
		return hasResource;
	}	
	public void subtractResource(Entity user) {
		if (user.arrows != -1)
			user.arrows -= useCost;
	}
	
	// PICKUP ARROW IF NOT MOVING
	public void interact() {
		if (grabbable) {
			gp.player.arrows++;
			alive = false;
		}
	}
}