package tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class Map extends TileManager {

	GamePanel gp;
	BufferedImage worldMap[];
	public boolean miniMapOn = false;
	
	public Map(GamePanel gp) {
		super(gp);
		this.gp = gp;		
		createWorldMap();
	}
	
	public void createWorldMap() {
		
		worldMap = new BufferedImage[gp.maxMap];
		int worldMapWidth = gp.tileSize * gp.maxWorldCol;
		int worldMapHeight = gp.tileSize * gp.maxWorldRow;
		
		for (int i = 0; i < gp.maxMap; i++) {
			
			worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = (Graphics2D)worldMap[i].createGraphics();
			
			int col = 0; 
			int row = 0;
			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				int tileNum = mapTileNum[i][col][row];
				int x = gp.tileSize * col;
				int y = gp.tileSize * row;				
				g2.drawImage(tile[tileNum].image, x, y, null);
				
				col++;
				if (col == gp.maxWorldCol)  {
					col = 0;
					row++;
				}
			}
			g2.dispose();
		}
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
		double scale = (double)(gp.tileSize * gp.maxWorldCol) / width;
		int playerX = (int)(x + (gp.player.worldX / scale));
		int playerY = (int)(y + (gp.player.worldY / scale) - 2);		
		int playerSize = (int)(gp.tileSize / 4);
		g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);
		
		// DRAW ENTITIES
		drawEntities(g2, gp.npc, Color.BLUE, x, y, scale, false);
		drawEntities(g2, gp.enemy, Color.RED, x, y, scale, false);
		drawEntities(g2, gp.enemy_r, Color.RED, x, y, scale, false);
		drawEntities(g2, gp.obj, Color.YELLOW, x, y, scale, false);
		
		// TEXT HINT (BOTTOM CENTER)
		g2.setFont(gp.ui.PK_DS.deriveFont(30f));
		g2.setColor(Color.WHITE);
		String text = "[Press M to close]";
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
			double scale = (double)(gp.tileSize * gp.maxWorldCol) / width;
			int playerX = (int)(x + (gp.player.worldX / scale) - 3);
			int playerY = (int)(y + (gp.player.worldY / scale) - 3);		
			int playerSize = (int)(gp.tileSize / 5);			
			graphics.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);
			
			// DRAW ENTITIES
			drawEntities(graphics, gp.npc, Color.BLUE, x, y, scale, true);
			drawEntities(graphics, gp.enemy, Color.RED, x, y, scale, true);
			drawEntities(graphics, gp.enemy_r, Color.RED, x, y, scale, true);
			drawEntities(graphics, gp.obj, Color.YELLOW, x, y, scale, true);
			
			graphics.dispose();
		}	
	}
	
	public void drawEntities(Graphics2D g2, Entity[][] entity, Color color, int x, int y, double scale, boolean minimap) {
				
		int eX;
		int eY;
		for (int i = 0; i < entity[1].length; i++) {				
			if (entity[gp.currentMap][i] != null) {		
				eX = (int)(x + (entity[gp.currentMap][i].worldX / scale));
				eY = (int)(y + (entity[gp.currentMap][i].worldY / scale));
				
				g2.setPaint(color);
				if (minimap) g2.fillOval(eX + 1, eY, 3, 3);
				else g2.fillOval(eX + 3, eY + 2, 4, 4);
			}
		}
	}
}