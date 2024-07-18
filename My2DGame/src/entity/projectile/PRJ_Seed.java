package entity.projectile;

import java.awt.Rectangle;

import application.GamePanel;

public class PRJ_Seed extends Projectile {

	public static final String prjName = "PRJ Seed";
	
	public PRJ_Seed(GamePanel gp) {
		super(gp);
		
		type = type_projectile;
		name = prjName;
		maxLife = 45; life = maxLife;
		speed = 7; 
		attack = 2;			
		alive = false;
		
		hitbox = new Rectangle(12, 12, 24, 24); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {
		up1 = setup("/projectile/seed_down_1");
		up2 = up1;
		down1 = up1;
		down2 = up1;
		left1 = up1;
		left2 = up1;
		right1 = up1;
		right2 = up1;
	}
	
	public void playSE() {
		gp.playSE(4, 2);
	}
}