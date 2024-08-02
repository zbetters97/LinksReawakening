package entity.projectile;

import java.awt.Rectangle;

import application.GamePanel;

public class PRJ_Beam extends Projectile {

	public static final String prjName = "Magic Beam";
	
	public PRJ_Beam(GamePanel gp) {
		super(gp);
		
		type = type_projectile;
		name = prjName;
		maxLife = 120; life = maxLife;
		speed = 12; 
		attack = 2;			
		knockbackPower = 1;
		alive = false;
		
		hitbox = new Rectangle(12, 12, 24, 24); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {
		up1 = setup("/projectiles/beam_down_1");
		up2 = up1;
		down1 = up1;
		down2 = up1;
		left1 = setup("/projectiles/beam_left_1");
		left2 = left1;
		right1 = left1;
		right2 = left1;
	}
	
	public void playSE() {
		gp.playSE(4, 2);
	}
}