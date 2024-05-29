package object;

import entity.*;
import main.GamePanel;

public class PRJ_Sword_Beam extends Projectile {

	GamePanel gp;
	
	public PRJ_Sword_Beam(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Sword Beam";
		speed = 8; // speed of travel
		animationSpeed = 8;
		maxLife = 60; // length of life (1 second)		
		life = maxLife;
		attack = 1; // damage dealt
		alive = false;
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/projectile/sword_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/sword_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/sword_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/sword_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/sword_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/sword_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/sword_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/sword_right_2", gp.tileSize, gp.tileSize);		
	}
	
	public boolean hasResource(Entity user) {
		
		boolean hasResource = false;
		if (user.life == user.maxLife)
				hasResource = true;
		
		return hasResource;
	}
	
	public void subtractResource(Entity user) {
		user.arrows -= useCost;
	}
}