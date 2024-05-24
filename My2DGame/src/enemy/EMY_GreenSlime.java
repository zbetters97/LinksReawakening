package enemy;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class EMY_GreenSlime extends Entity {

	GamePanel gp;
	
	public EMY_GreenSlime(GamePanel gp) {
		super(gp);		
		
		this.gp = gp;
		
		type = type_enemy;
		name = "Green Slime";
		speed = 1;
		baseSpeed = speed;
		animationSpeed = 15;
		maxLife = 4;
		life = maxLife;
		attack = 3;
		defense = 0;
		exp = 4;
		
		// HIT BOX
		solidArea.x = 2;
		solidArea.y = 18;
		solidArea.width = 44;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/enemy/greenslime_down_1");
		up2 = setup("/enemy/greenslime_down_2");
		down1 = setup("/enemy/greenslime_down_1");
		down2 = setup("/enemy/greenslime_down_2");
		left1 = setup("/enemy/greenslime_down_1");
		left2 = setup("/enemy/greenslime_down_2");
		right1 = setup("/enemy/greenslime_down_1");
		right2 = setup("/enemy/greenslime_down_2");
	}
	
	public void setAction() {

		actionLockCounter++;
		
		if (actionLockCounter == 120) {		
						
			Random random = new Random();
			int i = random.nextInt(100) + 1; // random number 1-100
						
			if (i <= 25) direction = "up";
			if (i > 25 && i <= 50) direction = "down";
			if (i > 50 && i <= 75) direction = "left";
			if (i > 75) direction = "right";
			
			actionLockCounter = 0;
		}
	}
	
	// SLIME RUNS AWAY WHEN HIT
	public void damageReaction() {
		actionLockCounter = 0;
		direction = gp.player.direction; 
	}
}