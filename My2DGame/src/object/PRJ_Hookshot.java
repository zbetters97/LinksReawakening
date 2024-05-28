package object;

import entity.Projectile;
import main.GamePanel;

public class PRJ_Hookshot extends Projectile {

	GamePanel gp;
	
	public PRJ_Hookshot(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Hookshot";
		speed = 10; // speed of travel
		maxLife = 30; // length of life (1 second)
		life = maxLife;	
		alive = false;
		
		getImage();
	}
	
	public void getImage() {
		image = setup("/projectile/hookshot_grab_1", gp.tileSize, gp.tileSize);
		image2 = setup("/projectile/hookshot_chain_1", gp.tileSize, gp.tileSize);
		up1 = setup("/projectile/hookshot_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/hookshot_down_1", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/hookshot_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/hookshot_down_1", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/hookshot_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/hookshot_down_1", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/hookshot_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/hookshot_down_1", gp.tileSize, gp.tileSize);
	}
}