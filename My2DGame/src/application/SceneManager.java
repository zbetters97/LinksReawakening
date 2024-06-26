package application;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import data.Progress;
import entity.Entity;
import entity.Entity.Action;
import entity.enemy.BOS_Skeleton;
import entity.npc.NPC_Traveler_2;
import entity.object.OBJ_BlueHeart;
import entity.object.OBJ_Door_Closed;
import entity.player.PlayerDummy;

public class SceneManager {

	private GamePanel gp;
	private Graphics2D g2;
	public int scene;
	public int phase;
	private int counter = 0;
	private float alpha = 0f;
	private int y;
	private String credits;
	
	public final int NA = 0;
	public final int npc = 1;
	public final int enemy_spawn = 2;
	public final int boss_1 = 3;
	public final int boss_1_defeat = 4;
	public final int ending = 5;
	
	private Entity npc1, npc2;
	
	public SceneManager(GamePanel gp) {
		this.gp = gp;
		
		npc1 = null;
		npc2 = null;
		
		credits = "Director/Programmer/Writer\n"
				+ "Zachary Betters"
				+ "\n\n\n\n\n\n\n\n\n\n"
				+ "Special thanks to...\n"
				+ "RyiSnow\n"
				+ "Vi_22\n"
				+ "Nintendo\n"
				+ "Jenna Betters\n"
				+ "You, the player!"
				+ "\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
				+ "THE END?";
	}
	
	protected void draw(Graphics2D g2) {
		this.g2 = g2;
		
		switch(scene) {
			case npc: scene_npc_1(); break;	
			case enemy_spawn: scene_enemy_spawn(); break;		
			case boss_1: scene_boss_1(); break;
			case boss_1_defeat: scene_boss_defeat(1); break;
			case ending: scene_ending(); break;
		}
	}
	
	private void scene_npc_1() {
		
		if (phase == 0) {	
			resetPlayerCounters();	
			gp.ui.drawDialogueScreen();
			npc1 = gp.ui.npc;
		}
		else if (phase == 1) {
			for (int i = 0; i < gp.npc[1].length; i++) {
				if (gp.npc[gp.currentMap][i] != null && 
						gp.npc[gp.currentMap][i].name.equals(NPC_Traveler_2.npcName)) {

					npc2 = gp.npc[gp.currentMap][i];
					
					gp.ui.npc = npc2;
					npc2.setPath(21, 21);
					npc2.onPath = true;
					
					phase++;
					
					break;
				}
			}
		}
		else if (phase == 2) {		
			
			if (npc2 != null) {
				
				// WAIT UNTIL TRAVELER GETS TO PLAYER
				if (!npc2.onPath) {

					npc1.direction = "right";
					
					npc2.dialogueSet = 1;
					gp.ui.drawDialogueScreen();
					
					// LOOK AT SPEAKER
					if (gp.ui.npc.dialogueIndex % 2 == 0) {						
						gp.player.direction = gp.player.findTargetDirection(npc2);
					}
					else {
						gp.player.direction = gp.player.findTargetDirection(npc1);
					}
				}
			}	
			// FAILSAFE
			else
				gp.gameState = gp.playState;			
		}
		else if (phase == 3) {
			
			npc1.setPath(19, 40);
			npc2.setPath(21, 41);
			
			npc1.onPath = true;
			npc2.onPath = true;
			npc1.pathCompleted = false;
			npc2.pathCompleted = false;
			
			npc1.hasCutscene = false;
			npc1.dialogueSet = 1;
			npc2.dialogueSet = 2;
			
			gp.ui.npc = null;
			
			scene = NA;
			phase = 0;
			
			gp.gameState = gp.playState;
		}
	}
	private void scene_enemy_spawn() {
		if (phase == 0) {
			resetPlayerCounters();
			playDoorCloseSE();
			phase++;
		}
		if (phase == 1) {
			if (counterReached(60)) {
				phase++;
			}
		}
		else if (phase == 2) {
			scene = NA;
			phase = 0;
			
			gp.gameState = gp.playState;
		}
	}	
	private void scene_boss_1() {
		
		if (phase == 0) {
			resetPlayerCounters();
			gp.stopMusic();	
			gp.bossBattleOn = true;
			
			// ADD IRON DOOR BEHIND PLAYER
			for (int i = 0; i < gp.obj[1].length; i++) {
				if (gp.obj[gp.currentMap][i] == null) {
										
					gp.obj[gp.currentMap][i] = new OBJ_Door_Closed(gp);
					gp.obj[gp.currentMap][i].worldX = gp.tileSize * 25;
					gp.obj[gp.currentMap][i].worldY = gp.tileSize * 28;
					gp.obj[gp.currentMap][i].direction = "up";
					gp.obj[gp.currentMap][i].temp = true;
					gp.obj[gp.currentMap][i].playCloseSE();
					
					break;
				}
			}
			
			// PLACE DUMMY IN NPC SLOT
			for (int i = 0; i < gp.npc[1].length; i++) {
				if (gp.npc[gp.currentMap][i] == null) {
					gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
					gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
					gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
					gp.npc[gp.currentMap][i].direction = gp.player.direction;
					
					break;
				}
			}
			
			gp.player.drawing = false;			
			phase++;
		}
		else if (phase == 1) {
			
			gp.player.worldY -=2;
			
			if (gp.player.worldY < gp.tileSize * 20) {
				phase++;
			}
		}
		else if (phase == 2) {
			
			// SEARCH FOR BOSS
			for (int i = 0; i < gp.enemy[1].length; i++) {
				if (gp.enemy[gp.currentMap][i] != null && 
						gp.enemy[gp.currentMap][i].name.equals(BOS_Skeleton.emyName)) {
					
					gp.enemy[gp.currentMap][i].sleep = false;
					gp.ui.npc = gp.enemy[gp.currentMap][i];
					
					phase++;
					
					break;
				}
			}				
		}
		else if (phase == 3) {
			gp.ui.drawDialogueScreen();
		}
		else if (phase == 4) {
			playBossMusic();
			
			// RETURN CAMERA TO PLAYER
			for (int i = 0; i < gp.npc[1].length; i++) {
				if (gp.npc[gp.currentMap][i] != null && 
						gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)) {
					gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
					gp.player.worldY = gp.npc[gp.currentMap][i].worldY;
					gp.npc[gp.currentMap][i] = null;
					
					break;
				}
			}

			gp.player.drawing = true;
			
			scene = NA;
			phase = 0;
			
			gp.gameState = gp.playState;			
		}
	}	
	private void scene_boss_defeat(int num) {
		if (phase == 0) {
			resetPlayerCounters();
			gp.stopMusic();
			gp.playMusic(6);

			gp.bossBattleOn = false;
			
			if (num == 1) Progress.bossDefeated_1 = true;
			
			phase++;
		}
		else if (phase == 1) {
			// PAUSE FOR MUSIC
			if (counterReached(610)) {
				phase++;
			}
		}
		else if (phase == 2) {
			gp.stopMusic();
			
			scene = NA;
			phase = 0;
			
			gp.gameState = gp.playState;
		}
	}
	private void scene_ending() {
						
		if (phase == 0) {
			resetPlayerCounters();
			gp.stopMusic();
			gp.ui.npc = new OBJ_BlueHeart(gp);
			phase++;
		}
		else if (phase == 1) {
			gp.ui.drawDialogueScreen();
		}		
		else if (phase == 2) {
			playEndingMusic();
			phase++;
		}
		else if (phase == 3) {
			
			// PAUSE FOR 3 SECONDS
			if (counterReached(180)) {
				phase++;
			}
		}
		else if (phase == 4) {
			
			// DARKEN SCREEN
			alpha += 0.005;			
			if (alpha > 1f) {
				alpha = 1f;
			}						
			drawBlackScreen(alpha);
			
			if (alpha == 1f) {
				alpha = 0;
				phase++;
			}
		}
		else if (phase == 5) {
			
			drawBlackScreen(1f);
			alpha += 0.005;			
			if (alpha > 1f) {
				alpha = 1f;
			}		
			
			String text = "Your long journey has finally come to an end.\n"
					+ "No evil spirit can ever retain this treasure.\n"
					+ "Time to return home and rest well\n"
					+ "knowing the world is at peace...";			
			drawString(alpha, 38f, 150, text, 70);
			
			// WAIT 10 SECONDS
			if (counterReached(600)) {
				phase++;
			}
		}
		else if (phase == 6) {
			drawBlackScreen(1f);			
			
			// SUBTITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
			String text = "The Legend of Zelda";
			int x = gp.ui.getXforCenteredText(text);
			int y = gp.tileSize * 5;
			g2.setColor(Color.RED);
			g2.drawString(text, x, y);
			
			// TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));
			text = "LINK'S REAWAKENING";
			x = gp.ui.getXforCenteredText(text);
			y += gp.tileSize * 1.5;
			g2.setColor(Color.WHITE);
			g2.drawString(text, x+10, y);
						
			// WAIT 8 SECONDS
			if (counterReached(360)) {
				gp.gameState = gp.endingState;
				phase++;
			}
		}
		else if (phase == 7) {
			drawBlackScreen(1f);
			
			y = gp.screenHeight + gp.tileSize;
			drawString(1f, 38f, y, credits, 40);
			
			// WAIT 1 SECOND
			if (counterReached(60)) {
				phase++;
			}
		}
		else if (phase == 8) {
			drawBlackScreen(1f);
			
			// SCROLL CREDITS			
			if (y >= -900) {
				y--;
			}	
			else {
				// AFTER CREDITS STOP
			}
			drawString(1f, 38f, y, credits, 40);
		}
	}
	
	private void resetPlayerCounters() {
		
		gp.player.action = Action.IDLE;
		gp.player.onGround = true;
		
		gp.player.digNum = 0;
		gp.player.digCounter = 0;	
		gp.player.jumpNum = 0;
		gp.player.jumpCounter = 0;			
		gp.player.rodNum = 0;
		gp.player.rodCounter = 0;
		gp.player.damageNum = 0;
		gp.player.damageCounter = 0;
	}
	
	private boolean counterReached(int target) {
		
		boolean counterReached = false;
		
		counter++;
		if (counter > target) {
			counterReached = true;
			counter = 0;
		}
		
		return counterReached;
	}
	
	private void drawBlackScreen(float alpha) {		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
	
	private void drawString(float alpha, float fontSize, int y, String text, int lineHeight) {
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(fontSize));
		
		for (String line : text.split("\n")) {
			
			int x = gp.ui.getXforCenteredText(line);
			g2.drawString(line, x, y);
			
			y += lineHeight;						
		}
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
	
	private void playDoorCloseSE() {
		gp.playSE(4, 5);
	}
	private void playBossMusic() {
		gp.playMusic(5);
	}
	private void playEndingMusic() {
		gp.playMusic(7);
	}
}