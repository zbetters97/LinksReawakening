package environment;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Lighting {

	GamePanel gp;
	BufferedImage darknessFilter;
	
	// DAY AND NIGHT CYCLE
	public int timeCounter;
	public final int day = 0;
	final int dusk = 1;
	final int night = 2;
	final int dawn = 3;
	public int timeState = day;
	public float filterAlpha = 0f;
	
	public Lighting(GamePanel gp) {
		this.gp = gp;
		setLightSource();
	}
	
	public void update() {
		
		// CHECK PLAYER LIGHT SOURCE
		if (gp.player.lightUpdated) {
			setLightSource();
			gp.player.lightUpdated = false;
		}
		
		// CHECK TIME
		if (timeState == day) {
			timeCounter++;
			if (timeCounter > 6000) { // 100 SEC (60/1 SEC)
				timeState = dusk;
				timeCounter = 0;
			}
		}
		else if (timeState == dusk) {
			filterAlpha += 0.001f; // 16 SEC ([1/0.001] / 60)
			if (filterAlpha > 1f) { // NO OPACITY (DARK)
				filterAlpha = 1f;
				timeState = night;
			}
		}
		else if (timeState == night) {
			timeCounter++;
			if (timeCounter > 600) { // 10 SECONDS (60/1 SEC)
				timeState = dawn;
				timeCounter = 0;
			}
		}
		else if (timeState == dawn) {
			filterAlpha -= 0.001f;
			if (filterAlpha < 0f) { // FULL OPACITY (LIGHT)
				filterAlpha = 0f;
				timeState = day;
			}
		}
	}
	
	public void setLightSource() {
		
		darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();
		
		if (gp.player.currentLight == null) {
			g2.setColor(new Color(0,0,0.1f,0.98f));
		}
		else {			
			// LIGHTING CIRCLE CENTER POINT ON PLAYER
			int centerX = gp.player.screenX + gp.tileSize / 2;
			int centerY = gp.player.screenY + gp.tileSize / 2;
			
			// GRADIENT AFFECT
			float fraction[] = new float[12];
			Color color[] = new Color[12];		
			
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
	
			// OPACITY OF GRADIENT (0.98 = DARK, 0.1 = LIGHT)
			color[0] = new Color(0,0,0.1f,0.1f);
			color[1] = new Color(0,0,0.1f,0.42f);
			color[2] = new Color(0,0,0.1f,0.52f);
			color[3] = new Color(0,0,0.1f,0.61f);
			color[4] = new Color(0,0,0.1f,0.69f);
			color[5] = new Color(0,0,0.1f,0.76f);
			color[6] = new Color(0,0,0.1f,0.82f);
			color[7] = new Color(0,0,0.1f,0.87f);
			color[8] = new Color(0,0,0.1f,0.91f);
			color[9] = new Color(0,0,0.1f,0.94f);
			color[10] = new Color(0,0,0.1f,0.96f);
			color[11] = new Color(0,0,0.1f,0.98f);		
			
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
	
	public void draw(Graphics2D g2) {
		// DAY LIGHTING
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
		g2.drawImage(darknessFilter, 0, 0, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		// DEBUG
		String timeOfDay = "";
		switch (timeState) {
			case day: timeOfDay = "DAY"; break;
			case dusk: timeOfDay = "DUSK"; break;
			case night: timeOfDay = "NIGHT"; break;
			case dawn: timeOfDay = "DAWN"; break;
		}
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(50f));
		
		if (gp.keyH.debug) 
			g2.drawString(timeOfDay, gp.tileSize, gp.tileSize * 9);
	}
}