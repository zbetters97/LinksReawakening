package entity.enemy;

import java.awt.Rectangle;

import entity.Entity;
import entity.object.OBJ_Door_Closed;
import main.GamePanel;

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
		attack = 4; defense = 1;
		knockbackPower = 5;
		exp = 50;
		maxLife = 12; life = maxLife;
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
			up1 = setup("/boss/skeletonlord_up_1", scale, scale);
			up2 = setup("/boss/skeletonlord_up_2", scale, scale);
			down1 = setup("/boss/skeletonlord_down_1", scale, scale);
			down2 = setup("/boss/skeletonlord_down_2", scale, scale);
			left1 = setup("/boss/skeletonlord_left_1", scale, scale);
			left2 = setup("/boss/skeletonlord_left_2", scale, scale);
			right1 = setup("/boss/skeletonlord_right_1", scale, scale);
			right2 = setup("/boss/skeletonlord_right_2", scale, scale);
		}
		else {
			up1 = setup("/boss/skeletonlord_phase2_up_1", scale, scale);
			up2 = setup("/boss/skeletonlord_phase2_up_2", scale, scale);
			down1 = setup("/boss/skeletonlord_phase2_down_1", scale, scale);
			down2 = setup("/boss/skeletonlord_phase2_down_2", scale, scale);
			left1 = setup("/boss/skeletonlord_phase2_left_1", scale, scale);
			left2 = setup("/boss/skeletonlord_phase2_left_2", scale, scale);
			right1 = setup("/boss/skeletonlord_phase2_right_1", scale, scale);
			right2 = setup("/boss/skeletonlord_phase2_right_2", scale, scale);
		}
	}	
	public void getAttackImage() {	
		
		int scale = gp.tileSize * 5;
		
		if (currentBossPhase == bossPhase_1) {
			attackUp1 = setup("/boss/skeletonlord_attack_up_1", scale, scale * 2); 
			attackUp2 = setup("/boss/skeletonlord_attack_up_2", scale, scale * 2);		
			attackDown1 = setup("/boss/skeletonlord_attack_down_1", scale, scale * 2); 
			attackDown2 = setup("/boss/skeletonlord_attack_down_2", scale, scale * 2);		
			attackLeft1 = setup("/boss/skeletonlord_attack_left_1", scale * 2, scale); 
			attackLeft2 = setup("/boss/skeletonlord_attack_left_2", scale * 2, scale);		
			attackRight1 = setup("/boss/skeletonlord_attack_right_1", scale * 2, scale); 
			attackRight2 = setup("/boss/skeletonlord_attack_right_2", scale * 2, scale);			
		}
		else {
			attackUp1 = setup("/boss/skeletonlord_phase2_attack_up_1", scale, scale * 2); 
			attackUp2 = setup("/boss/skeletonlord_phase2_attack_up_2", scale, scale * 2);		
			attackDown1 = setup("/boss/skeletonlord_phase2_attack_down_1", scale, scale * 2); 
			attackDown2 = setup("/boss/skeletonlord_phase2_attack_down_2", scale, scale * 2);		
			attackLeft1 = setup("/boss/skeletonlord_phase2_attack_left_1", scale * 2, scale); 
			attackLeft2 = setup("/boss/skeletonlord_phase2_attack_left_2", scale * 2, scale);		
			attackRight1 = setup("/boss/skeletonlord_phase2_attack_right_1", scale * 2, scale); 
			attackRight2 = setup("/boss/skeletonlord_phase2_attack_right_2", scale * 2, scale);	
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
				
		// REMOVE IRON DOOR
		for (int i = 0; i < gp.obj[1].length; i++) {
			if (gp.obj[gp.currentMap][i] != null &&
					gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Closed.objName) ) {						
				gp.obj[gp.currentMap][i].playOpenSE();
				gp.obj[gp.currentMap][i] = null;				
				break;
			}
		}
		
		gp.csManager.scene = gp.csManager.boss_1_defeat;
		gp.gameState = gp.cutsceneState;		
	}
}