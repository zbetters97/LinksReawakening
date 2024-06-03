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
		speed = 12; // speed of travel
		maxLife = 60; // length of life (1 second)
		life = maxLife;
		attack = 2; // damage dealt		
		useCost = 1; // 1 arrow to shoot 1 arrow
		alive = false;
		
		hitBox = new Rectangle(12, 16, 40, 32);
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/projectile/arrow_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/arrow_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/arrow_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/arrow_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/arrow_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/arrow_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/arrow_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/arrow_right_2", gp.tileSize, gp.tileSize);		
	}
	
	public boolean hasResource(Entity user) {
		
		boolean hasResource = false;
		
		if (user.arrows >= useCost) 
			hasResource = true;		
		
		return hasResource;
	}
	
	public void subtractResource(Entity user) {
		user.arrows -= useCost;
	}
}