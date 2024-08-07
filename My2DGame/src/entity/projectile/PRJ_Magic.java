package entity.projectile;

import java.awt.Rectangle;

import application.GamePanel;

public class PRJ_Magic extends Projectile {

	public static final String prjName = "Magic";
	
	public PRJ_Magic(GamePanel gp) {
		super(gp);
		
		type = type_projectile;
		name = prjName;
		maxLife = 120; life = maxLife;
		speed = 8; 
		attack = 2;			
		alive = false;
		
		hitbox = new Rectangle(12, 12, 24, 24); 		
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {
		up1 = setup("/projectiles/magic_up_1");
		up2 = up1;
		down1 = setup("/projectiles/magic_down_1");
		down2 = down1;
		left1 = setup("/projectiles/magic_left_1");
		left2 = left1;
		right1 = setup("/projectiles/magic_right_1");
		right2 = right1;
	}
	
	public void playSE() {
		gp.playSE(4, 2);
	}
}