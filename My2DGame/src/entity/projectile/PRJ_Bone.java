package entity.projectile;

import java.awt.Rectangle;

import application.GamePanel;

public class PRJ_Bone extends Projectile {

	public static final String prjName = "Bone";
	
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
		up1 = down1 = left1 = right1 = setup("/projectiles/bone_down_1");
		up2 = down2 = left2 = right2 = setup("/projectiles/bone_down_2");
	}
	
	public void playSE() {
		gp.playSE(5, 2);
	}
}