package enemy;

import java.awt.Rectangle;

import data.Progress;
import entity.Entity;
import item.ITM_Hookshot;
import main.GamePanel;
import object.OBJ_Door_Iron;

public class BOS_Skeleton extends Entity {

	public static final String emyName = "Skeleton King";
	GamePanel gp;
	
	public BOS_Skeleton(GamePanel gp) {
		super(gp);				
		this.gp = gp;
		
		type = type_enemy;
		boss = true;
		sleep = true;
		name = emyName;
		speed = 1; defaultSpeed = speed; 
		animationSpeed = 10;
		attack = 10; defense = 2;
		knockbackPower = 5;
		exp = 50;
		maxLife = 3; life = maxLife;
		currentBossPhase = bossPhase_1;
		
		swingSpeed1 = 45;
		swingSpeed2 = 80;
						
		int hbScale = gp.tileSize * 5;
		hitbox = new Rectangle(gp.tileSize, gp.tileSize, hbScale - (gp.tileSize * 2), hbScale - gp.tileSize); 
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		attackbox.width = 170;
		attackbox.height = 170;
		
		getImage();
		getAttackImage();
		setDialogue();
	}
	
	public void getImage() {
		
		int scale = gp.tileSize * 5;
		
		if (currentBossPhase == bossPhase_1) {
			up1 = setup("/enemy/skeletonlord_up_1", scale, scale);
			up2 = setup("/enemy/skeletonlord_up_2", scale, scale);
			down1 = setup("/enemy/skeletonlord_down_1", scale, scale);
			down2 = setup("/enemy/skeletonlord_down_2", scale, scale);
			left1 = setup("/enemy/skeletonlord_left_1", scale, scale);
			left2 = setup("/enemy/skeletonlord_left_2", scale, scale);
			right1 = setup("/enemy/skeletonlord_right_1", scale, scale);
			right2 = setup("/enemy/skeletonlord_right_2", scale, scale);
		}
		else {
			up1 = setup("/enemy/skeletonlord_phase2_up_1", scale, scale);
			up2 = setup("/enemy/skeletonlord_phase2_up_2", scale, scale);
			down1 = setup("/enemy/skeletonlord_phase2_down_1", scale, scale);
			down2 = setup("/enemy/skeletonlord_phase2_down_2", scale, scale);
			left1 = setup("/enemy/skeletonlord_phase2_left_1", scale, scale);
			left2 = setup("/enemy/skeletonlord_phase2_left_2", scale, scale);
			right1 = setup("/enemy/skeletonlord_phase2_right_1", scale, scale);
			right2 = setup("/enemy/skeletonlord_phase2_right_2", scale, scale);
		}
	}	
	public void getAttackImage() {	
		
		int scale = gp.tileSize * 5;
		
		if (currentBossPhase == bossPhase_1) {
			attackUp1 = setup("/enemy/skeletonlord_attack_up_1", scale, scale * 2); 
			attackUp2 = setup("/enemy/skeletonlord_attack_up_2", scale, scale * 2);		
			attackDown1 = setup("/enemy/skeletonlord_attack_down_1", scale, scale * 2); 
			attackDown2 = setup("/enemy/skeletonlord_attack_down_2", scale, scale * 2);		
			attackLeft1 = setup("/enemy/skeletonlord_attack_left_1", scale * 2, scale); 
			attackLeft2 = setup("/enemy/skeletonlord_attack_left_2", scale * 2, scale);		
			attackRight1 = setup("/enemy/skeletonlord_attack_right_1", scale * 2, scale); 
			attackRight2 = setup("/enemy/skeletonlord_attack_right_2", scale * 2, scale);			
		}
		else {
			attackUp1 = setup("/enemy/skeletonlord_phase2_attack_up_1", scale, scale * 2); 
			attackUp2 = setup("/enemy/skeletonlord_phase2_attack_up_2", scale, scale * 2);		
			attackDown1 = setup("/enemy/skeletonlord_phase2_attack_down_1", scale, scale * 2); 
			attackDown2 = setup("/enemy/skeletonlord_phase2_attack_down_2", scale, scale * 2);		
			attackLeft1 = setup("/enemy/skeletonlord_phase2_attack_left_1", scale * 2, scale); 
			attackLeft2 = setup("/enemy/skeletonlord_phase2_attack_left_2", scale * 2, scale);		
			attackRight1 = setup("/enemy/skeletonlord_phase2_attack_right_1", scale * 2, scale); 
			attackRight2 = setup("/enemy/skeletonlord_phase2_attack_right_2", scale * 2, scale);	
		}
	}
	
	public void setDialogue() {
		dialogues[0][0] = "No one may enter the tressure room!";
		dialogues[0][1] = "Taste the blade of my sword!";
	}
	
	public void setAction() {
		
		if (currentBossPhase == 1) {
			
			// DON'T CHASE PLAYER WHEN ATTACKING
			if (getTileDistance(gp.player) < 10 && !attacking) {			
				approachPlayer(90);
			}
			else if (!attacking) {			
				getDirection(90);
			}						
			
			if (!attacking) {
				isAttacking(90, gp.tileSize * 7, gp.tileSize * 5);
				speed = defaultSpeed;
			}
			// STOP MOVEMENT WHEN ATTACKING
			else
				speed = 0;
			
			if (life < maxLife / 2) {
				currentBossPhase = 2;
				attack++; defense--;
				defaultSpeed++; speed = defaultSpeed;
				getImage();
				getAttackImage();
			}
		}
		else if (currentBossPhase == 2) {
			
			// DON'T CHASE PLAYER WHEN ATTACKING
			if (getTileDistance(gp.player) < 10 && !attacking) {			
				approachPlayer(60);
			}
			else if (!attacking) {			
				getDirection(60);
			}			
			
			if (!attacking) {
				isAttacking(60, gp.tileSize * 7, gp.tileSize * 5);
				speed = defaultSpeed;
			}
			// STOP MOVEMENT WHEN ATTACKING
			else
				speed = 0;
		}	
	}
	
	public void damageReaction() {
		actionLockCounter = 0;
	}
	
	public void playAttackSE() {
		gp.playSE(4, 3);
	}
	public void playHurtSE() {
		gp.playSE(4, 4);
	}
	public void playDeathSE() {
		gp.playSE(4, 5);
	}
	
	// DROPPED ITEM
	public void checkDrop() {		
		gp.stopMusic();		
		// PLAY VICTORY MUSIC
		
		gp.bossBattleOn = false;
		Progress.bossDefeated = true;		
		
		// REMOVE IRON DOOR
		for (int i = 0; i < gp.obj[1].length; i++) {
			if (gp.obj[gp.currentMap][i] != null &&
					gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName) ) {						
				gp.obj[gp.currentMap][i] = null;				
				break;
			}
		}
		
		dropItem(new ITM_Hookshot(gp));
	}
}