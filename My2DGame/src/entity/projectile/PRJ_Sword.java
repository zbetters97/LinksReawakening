package entity.projectile;

import application.GamePanel;
import entity.*;

public class PRJ_Sword extends Projectile {
	
	public final static String prjName = "PRJ Sword Beam";
	
	public PRJ_Sword(GamePanel gp) {
		super(gp);
		
		type = type_projectile;
		name = prjName;		 
		
		maxLife = 60; life = maxLife;		
		speed = 8; 
		animationSpeed = 8;
		attack = 2;		
		alive = false;
	}
	
	public void getImage() {
		up1 = setup("/projectile/sword_up_1");
		up2 = setup("/projectile/sword_up_2");
		down1 = setup("/projectile/sword_down_1");
		down2 = setup("/projectile/sword_down_2");
		left1 = setup("/projectile/sword_left_1");
		left2 = setup("/projectile/sword_left_2");
		right1 = setup("/projectile/sword_right_1");
		right2 = setup("/projectile/sword_right_2");		
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
	
	public void playSE() {
		gp.playSE(4, 1);
	}
}