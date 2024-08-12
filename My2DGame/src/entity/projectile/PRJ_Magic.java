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
		up1 = up2 = setup("/projectiles/magic_up_1");
		down1 = down2 = setup("/projectiles/magic_down_1");
		left1 = left2 = setup("/projectiles/magic_left_1");
		right1 = right2 = setup("/projectiles/magic_right_1");
	}
	
	public void playSE() {
		gp.playSE(4, 2);
	}
}