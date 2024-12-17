package application;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	
	// CLIP HOLDERS
	public Clip clip;
	private URL sounds[][] = new URL[10][];
	public URL music[] = new URL[20];
	public URL menu[] = new URL[20];
	private URL player[] = new URL[20];
	private URL enemies[] = new URL[20];	
	private URL objects[] = new URL[20];	
	private URL items[] = new URL[20];
	private URL misc[] = new URL[20];
	private URL voice[] = new URL[20];
	private URL npc[] = new URL[20];
	private URL instrument[] = new URL[10];
	
	// VOLUME SLIDER
	private FloatControl fc;
	public int volumeScale = 3;
	public float volume;
	
	public Sound() {
		
		// 0
		music[0] = getClass().getResource("/sound/MUSIC_MENU_MAIN.wav");
		music[1] = getClass().getResource("/sound/MUSIC_WORLD_LIGHT.wav");
		music[2] = getClass().getResource("/sound/MUSIC_WORLD_DARK.wav");		
		music[3] = getClass().getResource("/sound/MUSIC_SHOP.wav");
		music[4] = getClass().getResource("/sound/MUSIC_DUNGEON_LIGHT.wav");
		music[5] = getClass().getResource("/sound/MUSIC_DUNGEON_DARK.wav");
		music[6] = getClass().getResource("/sound/MUSIC_BOSS.wav");
		music[7] = getClass().getResource("/sound/MUSIC_BOSS_DEFEAT.wav");
		music[8] = getClass().getResource("/sound/MUSIC_CREDITS.wav");
				
		// 1
		menu[0] = getClass().getResource("/sound/MENU_CURSOR.wav");
		menu[1] = getClass().getResource("/sound/MENU_SELECT.wav");
		menu[2] = getClass().getResource("/sound/MENU_ERROR.wav");
		menu[3] = getClass().getResource("/sound/MENU_OPEN.wav");
		menu[4] = getClass().getResource("/sound/MENU_CLOSE.wav");
		menu[5] = getClass().getResource("/sound/MENU_DIALOGUE.wav");
		menu[6] = getClass().getResource("/sound/MENU_DIALOGUE_FINISH.wav");
		menu[7] = getClass().getResource("/sound/MENU_MAP.wav");
		
		// 2
		player[0] = getClass().getResource("/sound/PLAYER_WALLET.wav");	
		player[1] = getClass().getResource("/sound/PLAYER_LOCKON.wav");	
		player[2] = getClass().getResource("/sound/PLAYER_GRUNT_1.wav");
		player[3] = getClass().getResource("/sound/PLAYER_GRUNT_2.wav");	
		player[4] = getClass().getResource("/sound/PLAYER_GRUNT_3.wav");			
		player[5] = getClass().getResource("/sound/PLAYER_GRUNT_4.wav");
		player[6] = getClass().getResource("/sound/PLAYER_HURT_1.wav");
		player[7] = getClass().getResource("/sound/PLAYER_HURT_2.wav");
		player[8] = getClass().getResource("/sound/PLAYER_HURT_3.wav");
		player[9] = getClass().getResource("/sound/PLAYER_SPIN_1.wav");		
		player[10] = getClass().getResource("/sound/PLAYER_SPIN_2.wav");
		player[11] = getClass().getResource("/sound/PLAYER_PULL.wav");
		player[12] = getClass().getResource("/sound/PLAYER_PUSH.wav");
		player[13] = getClass().getResource("/sound/PLAYER_SWIM.wav");
		player[14] = getClass().getResource("/sound/PLAYER_DROWN.wav");		
		player[15] = getClass().getResource("/sound/PLAYER_FALL.wav");		
		player[16] = getClass().getResource("/sound/PLAYER_LOWHP.wav");
		player[17] = getClass().getResource("/sound/PLAYER_DIE.wav");
		
		// 3
		enemies[0] = getClass().getResource("/sound/ENEMY_SMALL_HIT.wav");
		enemies[1] = getClass().getResource("/sound/ENEMY_NORMAL_HIT.wav");		
		enemies[2] = getClass().getResource("/sound/ENEMY_SMALL_DIE.wav");
		enemies[3] = getClass().getResource("/sound/ENEMY_SWORD_LARGE.wav");
		enemies[4] = getClass().getResource("/sound/BOSS_HIT.wav");
		enemies[5] = getClass().getResource("/sound/BOSS_DIE.wav");
		enemies[6] = getClass().getResource("/sound/ENEMY_SHOCK.wav");
		enemies[7] = getClass().getResource("/sound/ENEMY_TELEPORT.wav");
		
		// 4
		objects[0] = getClass().getResource("/sound/OBJ_SWORD_SWING.wav");			
		objects[1] = getClass().getResource("/sound/OBJ_SWORD_SPIN_CHARGE.wav");	
		objects[2] = getClass().getResource("/sound/OBJ_SWORD_SPIN.wav");	
		objects[3] = getClass().getResource("/sound/OBJ_SWORD_BEAM.wav");		
		objects[4] = getClass().getResource("/sound/OBJ_TINK.wav");	
		objects[5] = getClass().getResource("/sound/OBJ_MOVE.wav");					
		objects[6] = getClass().getResource("/sound/OBJ_LIFT.wav");	
		objects[7] = getClass().getResource("/sound/OBJ_THROW.wav");	
		objects[8] = getClass().getResource("/sound/OBJ_FIREBALL.wav");	
		objects[9] = getClass().getResource("/sound/OBJ_POT.wav");	
		objects[10] = getClass().getResource("/sound/OBJ_CHEST_OPEN.wav");
		objects[11] = getClass().getResource("/sound/OBJ_DOOR_OPEN.wav");		
		objects[12] = getClass().getResource("/sound/OBJ_DOOR_CLOSE.wav");
		objects[13] = getClass().getResource("/sound/OBJ_DOOR_UNLOCK.wav");				
								
		// 5
		items[0] = getClass().getResource("/sound/ITEM_GET.wav");	
		items[1] = getClass().getResource("/sound/ITEM_SHOVEL.wav");		
		items[2] = getClass().getResource("/sound/ITEM_BOOMERANG.wav");
		items[3] = getClass().getResource("/sound/ITEM_BOOTS.wav");
		items[4] = getClass().getResource("/sound/ITEM_BOMB_LAY.wav");
		items[5] = getClass().getResource("/sound/ITEM_BOMB_EXPLODE.wav");
		items[6] = getClass().getResource("/sound/ITEM_FEATHER.wav");
		items[7] = getClass().getResource("/sound/ITEM_ARROW.wav");
		items[8] = getClass().getResource("/sound/ITEM_HOOKSHOT.wav");		
		items[9] = getClass().getResource("/sound/ITEM_CAPE.wav");
		items[10] = getClass().getResource("/sound/ITEM_ROD.wav");
		items[11] = getClass().getResource("/sound/ITEM_ROD_CAPTURE.wav");
		
		// 6
		misc[0] = getClass().getResource("/sound/MISC_BUTTON.wav");
		misc[1] = getClass().getResource("/sound/MISC_RUPEE.wav");
		misc[2] = getClass().getResource("/sound/MISC_HEART.wav");	
		misc[3] = getClass().getResource("/sound/MISC_FAIRY.wav");
		misc[4] = getClass().getResource("/sound/MISC_STAIRS_UP.wav");		
		misc[5] = getClass().getResource("/sound/MISC_STAIRS_DOWN.wav");
		misc[6] = getClass().getResource("/sound/MISC_SOLVE.wav");
		
		// 7
		voice[0] = getClass().getResource("/sound/VOICE_SLASH1.wav");
		voice[1] = getClass().getResource("/sound/VOICE_SLASH2.wav");
		voice[2] = getClass().getResource("/sound/VOICE_ITEM.wav");
		voice[3] = getClass().getResource("/sound/VOICE_HURT.wav");
		voice[4] = getClass().getResource("/sound/VOICE_PUSH.wav");
		
		// 8
		npc[0] = getClass().getResource("/sound/NPC_CUCCO.wav");
		
		// 9
		instrument[0] = getClass().getResource("/sound/NOTES_A.wav");
		instrument[1] = getClass().getResource("/sound/NOTES_D.wav");
		instrument[2] = getClass().getResource("/sound/NOTES_R.wav");
		instrument[3] = getClass().getResource("/sound/NOTES_L.wav");
		instrument[4] = getClass().getResource("/sound/NOTES_U.wav");
		
		sounds[0] = music; 
		sounds[1] = menu; 
		sounds[2] = player; 
		sounds[3] = enemies; 
		sounds[4] = objects;
		sounds[5] = items;
		sounds[6] = misc;
		sounds[7] = voice;
		sounds[8] = npc;
		sounds[9] = instrument;
	}	
	
	public void setFile(int category, int record) {	
		
		try {			
			AudioInputStream ais = AudioSystem.getAudioInputStream(sounds[category][record]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			checkVolume();
		}
		catch (Exception e) {
			return;
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