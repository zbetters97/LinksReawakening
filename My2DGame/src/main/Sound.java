package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	
	Clip clip;
	URL sounds[][] = new URL[5][];
	URL music[] = new URL[30];
	URL player[] = new URL[30];
	URL enemy[] = new URL[30];	
	URL objects[] = new URL[30];
	URL misc[] = new URL[30];
	
	// VOLUME SLIDER
	FloatControl fc;
	int volumeScale = 3;
	float volume;
	
	public Sound() {
		
		// 0
		music[0] = getClass().getResource("/sound/MUSIC_TITLESCREEN.wav");
		music[1] = getClass().getResource("/sound/MUSIC_LIGHT_WORLD.wav");
		music[2] = getClass().getResource("/sound/MUSIC_DARK_WORLD.wav");		
		music[3] = getClass().getResource("/sound/MUSIC_LIGHT_DUNGEON.wav");
		
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
		
		// 2
		player[0] = getClass().getResource("/sound/PLAYER_HURT.wav");
		player[1] = getClass().getResource("/sound/PLAYER_DIE.wav");
		
		// 3
		objects[0] = getClass().getResource("/sound/OBJ_SWORD_SWING.wav");
		objects[1] = getClass().getResource("/sound/OBJ_ITEM_GET.wav");
		objects[2] = getClass().getResource("/sound/OBJ_ARROW.wav");
		objects[3] = getClass().getResource("/sound/OBJ_FIREBALL.wav");
		objects[4] = getClass().getResource("/sound/OBJ_SWORD_BEAM.wav");
		objects[5] = getClass().getResource("/sound/OBJ_HOOKSHOT.wav");
		
		// 4
		enemy[0] = getClass().getResource("/sound/EMY_NORMAL_HIT.wav");
		enemy[1] = getClass().getResource("/sound/EMY_SMALL_HIT.wav");
		enemy[2] = getClass().getResource("/sound/EMY_DIE.wav");
		
		sounds[0] = music; sounds[1] = misc; 
		sounds[2] = player; sounds[3] = objects; 
		sounds[4] = enemy;
	}	
	public void setFile(int category, int record) {		
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
	public void play() {		
		clip.start();
	}	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);		
	}	
	public void stop() {
		clip.stop();
	}
	public void checkVolume() {
		
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