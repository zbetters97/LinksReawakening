package environment;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class Lighting {

	GamePanel gp;
	BufferedImage darknessFilter;
	Entity eventMaster;
	
	// DAY AND NIGHT CYCLE
	public int dayCounter;
	final int dayLength = 3200; // 60 SECONDS (1/60 * 3200)
	public final int day = 0;
	final int dusk = 1;
	final int night = 2;
	final int dawn = 3;
	public int dayState = day;
	public int bloodMoonCounter = 0;
	public int bloodMoonMax = 5;
	public float filterAlpha = 0f;
	
	public Lighting(GamePanel gp) {
		this.gp = gp;
		eventMaster = new Entity(gp);
		setLightSource();
		setDialogue();
	}
	
	public void setDialogue() {
		eventMaster.dialogues[0][0] = "\"It's getting dark.\nI better find a light...\"";
		eventMaster.dialogues[1][0] = "The blood moon rises once again...";
	}
	
	public void update() {
		
		// CHECK PLAYER LIGHT SOURCE
		if (gp.player.lightUpdated) {
			setLightSource();
			gp.player.lightUpdated = false;
		}
		
		// DAY
		if (dayState == day) {
			dayCounter++;
			if (dayCounter > dayLength) { // 100 SEC (60/1 SEC)
				dayState = dusk;
				dayCounter = 0;
				bloodMoonCounter++;				
				setLightSource();
				
				if (gp.player.currentLight == null && gp.gameState == gp.playState) {
					gp.keyH.actionPressed = false;
					eventMaster.startDialogue(eventMaster, 0);
				}
			}
		}
		// DUSK
		else if (dayState == dusk) {
			filterAlpha += 0.001f; // 16 SEC ([1/0.001] / 60)
			if (filterAlpha > 1f) { // NO OPACITY (DARK)
				dayState = night;
				filterAlpha = 1f;		
				
				// BLOOD MOON CYCLE
				if (bloodMoonCounter == bloodMoonMax) {
					bloodMoonCounter = 0;
					gp.aSetter.setEnemy();					
					eventMaster.startDialogue(eventMaster, 1);
				}
			}
		}
		// NIGHT
		else if (dayState == night) {
			dayCounter++;
			if (dayCounter > dayLength) {
				dayState = dawn;
				dayCounter = 0;				
			}
		}
		// DAWN
		else if (dayState == dawn) {
			filterAlpha -= 0.001f;
			if (filterAlpha < 0f) { // FULL OPACITY (LIGHT)
				dayState = day;
				filterAlpha = 0f;
			}
		}
	}
	
	public void setLightSource() {
		
		darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();
		
		if (gp.player.currentLight == null) {
			
			// BLUE OR RED MOON FILTER
			float red = 0;
			float blue = 0;						
			if (bloodMoonCounter == bloodMoonMax) red = 0.1f; 
			else blue = 0.1f;
			g2.setColor(new Color(red,0,blue,0.98f));
		}
		else {
			// LIGHTING CIRCLE CENTER POINT ON PLAYER
			int centerX = gp.player.screenX + gp.tileSize / 2;
			int centerY = gp.player.screenY + gp.tileSize / 2;
			
			// GRADIENT AFFECT
			Color color[] = new Color[12];	
			float fraction[] = new float[12];
				
			
			// BLUE OR RED MOON FILTER
			float red = 0;
			float blue = 0;					
			if (bloodMoonCounter == bloodMoonMax) red = 0.1f;			
			else blue = 0.1f;			
			
			// OPACITY OF GRADIENT (1 = DARK, 0 = LIGHT)
			color[0] = new Color(red,0,blue,0.1f);
			color[1] = new Color(red,0,blue,0.42f);
			color[2] = new Color(red,0,blue,0.52f);
			color[3] = new Color(red,0,blue,0.61f);
			color[4] = new Color(red,0,blue,0.69f);
			color[5] = new Color(red,0,blue,0.76f);
			color[6] = new Color(red,0,blue,0.82f);
			color[7] = new Color(red,0,blue,0.87f);
			color[8] = new Color(red,0,blue,0.91f);
			color[9] = new Color(red,0,blue,0.94f);
			color[10] = new Color(red,0,blue,0.96f);
			color[11] = new Color(red,0,blue,0.98f);
			
			// DISTANCE FROM CIRCLE (1 = EDGE, 0 = CENTER)
			fraction[0] = 0f;
			fraction[1] = 0.4f;
			fraction[2] = 0.5f;
			fraction[3] = 0.6f;
			fraction[4] = 0.65f;
			fraction[5] = 0.7f;
			fraction[6] = 0.75f;
			fraction[7] = 0.8f;
			fraction[8] = 0.85f;
			fraction[9] = 0.9f;
			fraction[10] = 0.95f;
			fraction[11] = 1f;	
			
			// DRAW GRAIDENT ON LIGHT CIRCLE
			RadialGradientPaint gPaint = new RadialGradientPaint(
					centerX, centerY, (gp.player.currentLight.lightRadius / 2), fraction, color
			);
			g2.setPaint(gPaint);
		}
		
		// DRAW DARKNESS RECTANGLE
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.dispose();
	}
	
	public void resetDay() {
		
		dayState = day;
		filterAlpha = 0f;
		bloodMoonCounter = 0;
	}
	
	public void draw(Graphics2D g2) {
		
		// DAY LIGHTING
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
		g2.drawImage(darknessFilter, 0, 0, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		// DEBUG
		if (gp.keyH.debug) {
			
			String timeOfDay = "";
			switch (dayState) {
				case day: timeOfDay = "DAY"; break;
				case dusk: timeOfDay = "DUSK"; break;
				case night: timeOfDay = "NIGHT"; break;
				case dawn: timeOfDay = "DAWN"; break;
			}
			
			g2.setColor(Color.WHITE);
			g2.setFont(g2.getFont().deriveFont(50f));		
			g2.drawString(timeOfDay, 10, gp.tileSize * 5);
		}
	}
}