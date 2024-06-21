package enemy;

import java.awt.Rectangle;
import java.util.Random;

import collectable.COL_Heart;
import collectable.COL_Rupee_Blue;
import entity.Entity;
import main.GamePanel;
import projectile.PRJ_Fireball;

public class EMY_Slime_Green extends Entity {

	public static final String emyName = "Green Slime";
	GamePanel gp;
	
	public EMY_Slime_Green(GamePanel gp) {
		super(gp);			
		this.gp = gp;
		
		type = type_enemy;
		name = emyName;
		speed = 1; defaultSpeed = speed;
		animationSpeed = 13;
		attack = 4; defense = 0;
		knockbackPower = 1;
		exp = 6;
		maxLife = 9; life = maxLife;
		
		hitbox = new Rectangle(2, 18, 44, 30);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;

		projectile = new PRJ_Fireball(gp);
		
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
		if (onPath) {			
			isOffPath(gp.player, 8);				
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
		}
		else {	
			getDirection(120);
		}
	}
	
	// ONLY FOLLOW PLAYER WHEN HIT
	public void damageReaction() {
		actionLockCounter = 0;
		onPath = true;
	}
	
	public void playHurtSE() {
		gp.playSE(4, 0);
	}
	public void playDeathSE() {
		gp.playSE(4, 2);
	}
	
	// DROPPED ITEM
	public void checkDrop() {
		
		int i = new Random().nextInt(100) + 1;
		
		if (i < 50) dropItem(new COL_Heart(gp));
		else dropItem(new COL_Rupee_Blue(gp));
	}
}