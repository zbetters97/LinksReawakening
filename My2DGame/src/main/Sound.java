package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	Clip clip;
	URL sounds[][] = new URL[5][];
	URL music[] = new URL[30];
	URL player[] = new URL[30];
	URL enemy[] = new URL[30];	
	URL objects[] = new URL[30];
	URL misc[] = new URL[30];
	
	public Sound() {
		
		// 0
		music[0] = getClass().getResource("/sound/MUSIC_TITLESCREEN.wav");
		music[1] = getClass().getResource("/sound/MUSIC_OVERWORLD.wav");
		
		// 1
		misc[0] = getClass().getResource("/sound/MENU_CURSOR.wav");
		misc[1] = getClass().getResource("/sound/MENU_SELECT.wav");
		misc[2] = getClass().getResource("/sound/MENU_ERROR.wav");
		misc[3] = getClass().getResource("/sound/MISC_LEVELUP.wav");
		misc[4] = getClass().getResource("/sound/MISC_FAIRY.wav");
		misc[5] = getClass().getResource("/sound/COL_RUPEE.wav");
		misc[6] = getClass().getResource("/sound/COL_HEART.wav");
		misc[7] = getClass().getResource("/sound/MISC_TREE_CUT.wav");
		
		// 2
		player[0] = getClass().getResource("/sound/PLAYER_HURT.wav");
		objects[0] = getClass().getResource("/sound/OBJ_SWORD_SWING.wav");
		objects[1] = getClass().getResource("/sound/OBJ_ITEM_GET.wav");
		objects[2] = getClass().getResource("/sound/OBJ_ARROW.wav");
		objects[3] = getClass().getResource("/sound/OBJ_FIREBALL.wav");
		objects[4] = getClass().getResource("/sound/OBJ_SWORD_BEAM.wav");
		
		// 3
		enemy[0] = getClass().getResource("/sound/EMY_NORMAL_HIT.wav");
		enemy[1] = getClass().getResource("/sound/EMY_SMALL_HIT.wav");
		enemy[2] = getClass().getResource("/sound/EMY_DIE.wav");
		
		// 4
		sounds[0] = music; sounds[1] = misc; 
		sounds[2] = player; sounds[3] = objects; 
		sounds[4] = enemy;
	}	
	public void setFile(int category, int record) {		
		try {			
			AudioInputStream ais = AudioSystem.getAudioInputStream(sounds[category][record]);
			clip = AudioSystem.getClip();
			clip.open(ais);
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
}