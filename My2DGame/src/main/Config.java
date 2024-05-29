package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
	
	GamePanel gp;
	
	public Config(GamePanel gp) {
		this.gp = gp;
	}
	
	public void saveConfig() {
		
		try {
			// IMPORT FILE
			BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
			
			// PLAYER NAME
			bw.write("Player name\n" + gp.player.name);
			bw.newLine();
			
			// FULLSCREEN
			if (gp.fullScreenOn) bw.write("Fullscreen\nTrue");
			else bw.write("Fullscreen\nFalse");
			bw.newLine();
			
			// MUSIC VOLUME
			bw.write("Music volume\n" + String.valueOf(gp.music.volumeScale));			
			bw.newLine();
			
			// SOUND EFFECTS VOLUME
			bw.write("Sound effects volume\n" + String.valueOf(gp.se.volumeScale));			
			bw.newLine();
			
			// CLOSE FILE
			bw.close();			
		} 
		catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	public void loadConfig() {
		
		try {
			// IMPORT FILE
			BufferedReader br = new BufferedReader(new FileReader("config.txt"));
			
			br.readLine(); 
						
			// PLAYER NAME
			String s = br.readLine();
			gp.player.name = s;
			br.readLine();
			
			// FULL SCREEN
			s = br.readLine();
			if (s.equals("True")) gp.fullScreenOn = true;
			else gp.fullScreenOn = false;
			br.readLine();
			
			// MUSIC VOLUME
			s = br.readLine();
			gp.music.volumeScale = Integer.parseInt(s);
			br.readLine();
			
			// SOUND EFFECTS VOLUME
			s = br.readLine();
			gp.se.volumeScale = Integer.parseInt(s);
			
			br.close();			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}










