package entity.projectile;

import java.awt.Rectangle;

import application.GamePanel;

public class PRJ_Seed extends Projectile {

	public static final String prjName = "Seed";
	
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
		up1 = down1 = left1 = right1 = setup("/projectiles/seed_down_1");
		up2 = down2 = left2 = right2 = setup("/projectiles/seed_down_1");
	}
	
	public void playSE() {
		gp.playSE(4, 2);
	}
}