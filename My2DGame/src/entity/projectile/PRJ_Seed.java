package entity.projectile;

import java.awt.Rectangle;

import application.GamePanel;

public class PRJ_Seed extends Projectile {

	GamePanel gp;
	public static final String prjName = "Seed";
	
	public PRJ_Seed(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_projectile;
		name = prjName;
		attack = 2;	
		speed = 7; 
		maxLife = 45; life = maxLife;
		alive = false;
		
		hitbox = new Rectangle(12, 12, 24, 24); 		
		hitboxDefaultX = hitbox.x;
	    hitboxDefaultY = hitbox.y;
		
		getImage();
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