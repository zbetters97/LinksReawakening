package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	
	// CLIP HOLDERS
	protected Clip clip;
	private URL sounds[][] = new URL[5][];
	private URL music[] = new URL[30];	
	private URL misc[] = new URL[30];
	private URL player[] = new URL[30];
	private URL objects[] = new URL[30];
	private URL enemy[] = new URL[30];	
	
	// VOLUME SLIDER
	private FloatControl fc;
	protected int volumeScale = 3;
	protected float volume;
	
	protected Sound() {
		
		// 0
		music[0] = getClass().getResource("/sound/MUSIC_MENU_MAIN.wav");
		music[1] = getClass().getResource("/sound/MUSIC_WORLD_LIGHT.wav");
		music[2] = getClass().getResource("/sound/MUSIC_WORLD_DARK.wav");		
		music[3] = getClass().getResource("/sound/MUSIC_SHOP.wav");
		music[4] = getClass().getResource("/sound/MUSIC_DUNGEON_LIGHT.wav");
		music[5] = getClass().getResource("/sound/MUSIC_BOSS.wav");
		
		// 1
		misc[0] = getClass().getResource("/sound/MENU_CURSOR.wav");
		misc[1] = getClass().getResource("/sound/MENU_SELECT.wav");
		misc[2] = getClass().getResource("/sound/MENU_ERROR.wav");
		misc[3] = getClass().getResource("/sound/MISC_LEVELUP.wav");
		misc[4] = getClass().getResource("/sound/MISC_FAIRY.wav");
		misc[5] = getClass().getResource("/sound/COL_RUPEE.wav");
		misc[6] = getClass().getResource("/sound/COL_HEART.wav");
		misc[7] = getClass().getResource("/sound/MISC_TREE_CUT.wav");
		misc[8] = getClass().getResource("/sound/MENU_OPEN.wav");
		misc[9] = getClass().getResource("/sound/MENU_CLOSE.wav");
		misc[10] = getClass().getResource("/sound/OBJ_DOOR_ENTER.wav");
		misc[11] = getClass().getResource("/sound/MENU_DIALOGUE.wav");
		misc[12] = getClass().getResource("/sound/MENU_DIALOGUE_FINISH.wav");
		misc[14] = getClass().getResource("/sound/MENU_MAP.wav");
		misc[15] = getClass().getResource("/sound/MISC_WALLET.wav");
		misc[16] = getClass().getResource("/sound/MISC_BUTTON.wav");
		
		// 2
		player[0] = getClass().getResource("/sound/PLAYER_HURT.wav");
		player[1] = getClass().getResource("/sound/PLAYER_DIE.wav");
		player[2] = getClass().getResource("/sound/PLAYER_FALL.wav");
		player[3] = getClass().getResource("/sound/PLAYER_LOCKON.wav");
		
		// 3
		objects[0] = getClass().getResource("/sound/OBJ_SWORD_SWING.wav");
		objects[1] = getClass().getResource("/sound/MISC_ITEM_GET.wav");
		objects[2] = getClass().getResource("/sound/ITEM_ARROW.wav");
		objects[3] = getClass().getResource("/sound/OBJ_FIREBALL.wav");
		objects[4] = getClass().getResource("/sound/OBJ_SWORD_BEAM.wav");
		objects[5] = getClass().getResource("/sound/ITEM_HOOKSHOT.wav");
		objects[6] = getClass().getResource("/sound/ITEM_BOOTS.wav");
		objects[7] = getClass().getResource("/sound/ITEM_SHOVEL.wav");
		objects[8] = getClass().getResource("/sound/ITEM_BOMB_LAY.wav");
		objects[9] = getClass().getResource("/sound/ITEM_BOMB_EXPLODE.wav");
		objects[10] = getClass().getResource("/sound/ITEM_BOOMERANG.wav");
		objects[11] = getClass().getResource("/sound/ITEM_FEATHER.wav");
		objects[12] = getClass().getResource("/sound/OBJ_MOVE.wav");
		objects[13] = getClass().getResource("/sound/OBJ_TINK.wav");
		objects[14] = getClass().getResource("/sound/OBJ_DOOR_OPEN.wav");
		
		// 4
		enemy[0] = getClass().getResource("/sound/ENEMY_SMALL_HIT.wav");
		enemy[1] = getClass().getResource("/sound/ENEMY_NORMAL_HIT.wav");		
		enemy[2] = getClass().getResource("/sound/ENEMY_SMALL_DIE.wav");
		enemy[3] = getClass().getResource("/sound/ENEMY_SWORD_LARGE.wav");
		enemy[4] = getClass().getResource("/sound/BOSS_HIT.wav");
		enemy[5] = getClass().getResource("/sound/BOSS_DIE.wav");
		
		
		sounds[0] = music; 
		sounds[1] = misc; 
		sounds[2] = player; 
		sounds[3] = objects; 
		sounds[4] = enemy;
	}	
	protected void setFile(int category, int record) {		
		try {			
			AudioInputStream ais = AudioSystem.getAudioInputStream(sounds[category][record]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
			// VOLUME
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			checkVolume();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void play() {		
		clip.start();
	}	
	protected void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);		
	}	
	protected void stop() {
		clip.stop();
	}
	protected void checkVolume() {
		
		switch(volumeScale) {
			case 0: volume = -80f; break;
			case 1: volume = -20f; break;
			case 2: volume = -12f; break;
			case 3: volume = -5f; break;
			case 4: volume = 1f;break;
			case 5: volume = 6f; break;
		}
		fc.setValue(volume);
	}
}