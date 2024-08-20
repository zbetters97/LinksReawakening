package application;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import entity.object.OBJ_Chest;

public class Map {

	private GamePanel gp;
	private BufferedImage worldMap[];
	public boolean miniMapOn = false;
	private int maxWorld[];
	
	public Map(GamePanel gp) {
		this.gp = gp;	
		worldMap = new BufferedImage[gp.maxMap];
		maxWorld = new int[gp.maxMap];
		createWorldMap();
	}
	
	public void createWorldMap() {
		
		int worldMapMaxCol = 0;
		int worldMapMaxRow = 0;
		
		for (int i = 0; i < gp.maxMap; i++) {			
		
			String currentMap = "/maps/" + gp.mapFiles[i];	
			InputStream is = getClass().getResourceAsStream(currentMap);	
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
				
			try {
				String line = br.readLine();
				String maxTile[] = line.split(" ");				
				maxWorld[i] = maxTile.length;
				
				worldMapMaxCol = maxWorld[i] * gp.tileSize;
				worldMapMaxRow = maxWorld[i] * gp.tileSize;
				
				worldMap[i] = new BufferedImage(worldMapMaxCol, worldMapMaxRow, BufferedImage.TYPE_INT_ARGB);
				
				br.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
		
	public void loadWorldMap() {
		
		Graphics2D g2 = (Graphics2D)worldMap[gp.currentMap].createGraphics();
	
		int col = 0; 
		int row = 0;
		while (col < maxWorld[gp.currentMap] && row < maxWorld[gp.currentMap]) {
							int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];			int x = gp.tileSize * col;			int y = gp.tileSize * row;					g2.drawImage(gp.tileM.tile[tileNum].image, x, y, null);
						col++;			if (col == maxWorld[gp.currentMap])  {				col = 0;				row++;			}		}		g2.dispose();	
	}
	
	public void drawFullMapScreen(Graphics2D g2) {
			
		// BACKGROUND COLOR
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// DRAW MAP
		int width = 600;
		int height = 600;
		int x = (gp.screenWidth / 2) - (width / 2);
		int y = (gp.screenHeight / 2) - (height / 2);
		g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);
		
		// DRAW PLAYER
		double scale = (double)(gp.tileSize * maxWorld[gp.currentMap]) / width;
		int playerX = (int)(x + (gp.player.worldX / scale));
		int playerY = (int)(y + (gp.player.worldY / scale) - 2);		
		int playerSize = (int)(gp.tileSize / 4);
		g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);
		
		// DRAW ENTITIES
		drawNPCs(g2, x, y, scale, false);
		drawEnemies(g2, x, y, scale, false);
		drawChests(g2, x, y, scale, false);
		
		// TEXT HINT (BOTTOM CENTER)		
		g2.setColor(new Color(0,0,0,200));
		g2.fillRect(288, 543, gp.tileSize * 4, 30);		
		g2.setColor(Color.WHITE);
		g2.setFont(gp.ui.PK_DS.deriveFont(30f));
		String text = "[Press " + KeyEvent.getKeyText(gp.btn_DUP) + " to close]";
		g2.drawString(text, gp.ui.getXforCenteredText(text), 565);
	}
	
	public void drawMiniMap(Graphics2D g2) {
		
		if (miniMapOn) {
						
			// MINI MAP ATTRIBUTES
			int width = 200;
			int height = 200;
			int x = (gp.tileSize / 2) - 15;
			int y = (gp.tileSize * 7) + 30;			

			// NEW GRAPHICS PANEL
			Graphics2D graphics = (Graphics2D)g2.create();		
			
			// SMOOTH OUT SHAPE
			RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		    graphics.setRenderingHints(qualityHints);
			
			// MINI MAP BORDER
			graphics.setColor(Color.BLACK);
			graphics.setStroke(new BasicStroke(4));
			graphics.drawOval(x, y, width, height);
			
			// DRAW MINI MAP ON CIRCLE
			graphics.clip(new RoundRectangle2D.Double(x, y, width, height, width, height));
			graphics.drawImage(worldMap[gp.currentMap], x, y, width, height, null);			
			
			// DRAW PLAYER
			double scale = (double)(gp.tileSize * maxWorld[gp.currentMap]) / width;
			int playerX = (int)(x + (gp.player.worldX / scale) - 3);
			int playerY = (int)(y + (gp.player.worldY / scale) - 3);		
			int playerSize = (int)(gp.tileSize / 5);			
			graphics.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);
			
			// DRAW ENTITIES
			drawNPCs(graphics, x, y, scale, true);
			drawEnemies(graphics, x, y, scale, true);
			drawChests(graphics, x, y, scale, true);
			
			graphics.dispose();
		}	
	}
	
	public void drawNPCs(Graphics2D g2, int x, int y, double scale, boolean minimap) {
				
		int nX;
		int nY;
		for (int i = 0; i < gp.npc[1].length; i++) {				
			if (gp.npc[gp.currentMap][i] != null) {		
				nX = (int)(x + (gp.npc[gp.currentMap][i].worldX / scale));
				nY = (int)(y + (gp.npc[gp.currentMap][i].worldY / scale));
				
				g2.setPaint(Color.BLUE);
				if (minimap) g2.fillOval(nX + 1, nY, 3, 3);
				else g2.fillOval(nX + 3, nY + 2, 4, 4);
			}
		}
	}
	public void drawEnemies(Graphics2D g2, int x, int y, double scale, boolean minimap) {
		
		int eX;
		int eY;
		for (int i = 0; i < gp.enemy[1].length; i++) {				
			if (gp.enemy[gp.currentMap][i] != null) {	
				eX = (int)(x + (gp.enemy[gp.currentMap][i].worldX / scale));
				eY = (int)(y + (gp.enemy[gp.currentMap][i].worldY / scale));
				
				g2.setPaint(Color.RED);
				if (minimap) g2.fillOval(eX + 1, eY, 3, 3);
				else g2.fillOval(eX + 3, eY + 2, 4, 4);
			}
		}
	}	
	public void drawChests(Graphics2D g2, int x, int y, double scale, boolean minimap) {
		
		int cX;
		int cY;
		for (int i = 0; i < gp.obj[1].length; i++) {				
			if (gp.obj[gp.currentMap][i] != null && 
					gp.obj[gp.currentMap][i].name.equals(OBJ_Chest.objName) &&
					!gp.obj[gp.currentMap][i].opened) {		
				cX = (int)(x + (gp.obj[gp.currentMap][i].worldX / scale));
				cY = (int)(y + (gp.obj[gp.currentMap][i].worldY / scale));
				
				g2.setPaint(Color.YELLOW);
				if (minimap) g2.fillOval(cX + 1, cY, 3, 3);
				else g2.fillOval(cX + 3, cY + 2, 4, 4);
			}
		}
	}
}