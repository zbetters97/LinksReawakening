package entity.projectile;

import java.awt.Rectangle;

import application.GamePanel;

public class PRJ_Bone extends Projectile {

	public static final String prjName = "PRJ Bone";
	
	public PRJ_Bone(GamePanel gp) {
		super(gp);
						
		type = type_projectile;
		name = prjName;

		maxLife = 45; life = maxLife;			
		speed = 6; 
		animationSpeed = 6;				
		attack = 2;
		knockbackPower = 0;
		alive = false;		
		
		hitbox = new Rectangle(12, 12, 24, 24); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {
		up1 = setup("/projectile/bone_down_1");
		up2 = setup("/projectile/bone_down_2");
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