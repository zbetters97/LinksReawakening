package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.*;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable {
	
	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3; // scale rate to accommodate for large screen
	public final int tileSize = originalTileSize * scale; // scaled tile (16*3 = 48px)	
	public final int maxScreenCol = 16; // columns (width)
	public final int maxScreenRow = 12; // rows (height)
	public final int screenWidth = tileSize * maxScreenCol; // screen width (in tiles) 768px
	public final int screenHeight = tileSize * maxScreenRow; // screen height (in tiles) 576px
	
	// WORLD SIZE (assigned in Tile Manager)
	public int maxWorldCol;
	public int maxWorldRow;
	
	// AMOUNT OF TOTAL MAPS
	public final int maxMap = 10;
	public int currentMap = 0;
	
	// FULL SCREEN SETTINGS
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	
	public boolean fullScreenOn = false;
	Config config = new Config(this);
	
	// FPS
	int FPS = 60;
	
	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;	
	public final int characterState = 4;
	public final int itemState = 5;
	public final int gameOverState = 6;
	public final int transitionState = 7;
	public final int tradeState = 8;
	public final int itemGetState = 9;
	
	// CONTROLS / SOUND / UI
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();	
	public UI ui = new UI(this);
	
	Thread gameThread;
	
	// TILE / COLLISION / EVENT
	public TileManager tileM = new TileManager(this);
	public CollisionChecker cChecker = new CollisionChecker(this);	
	public EventHandler eHandler = new EventHandler(this);
	
	// PLAYER / ENTITY / ENEMY / OBJECT
	public Player player = new Player(this, keyH);	
	public Entity npc[][] = new Entity[maxMap][10]; // total amount of npc displayed at once	
	public Entity enemy[][] = new Entity[maxMap][20]; // total amount of enemies displayed at once
	public Entity obj[][] = new Entity[maxMap][20]; // total amount of items displayed at once
	public Entity hiddenItems[][] = new Entity[maxMap][50];
	public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
	public ArrayList<Entity> particleList = new ArrayList<>();
	public ArrayList<Entity> projectileList = new ArrayList<>();
	public ArrayList<Entity> entityList = new ArrayList<>();	
	
	public AssetSetter aSetter = new AssetSetter(this);
		
	/** CONSTRUCTOR **/
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // screen size
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); // improves rendering performance
		
		this.addKeyListener(keyH);
		this.setFocusable(true); // GamePanel in focus to receive input
	}
	
	public void setupGame() {
		
//		gameState = playState;
		
		setupMusic();
												
		aSetter.setNPC();
		aSetter.setEnemy();
		aSetter.setInteractiveTiles();
		aSetter.setObject();
		
		// TEMP GAME WINDOW (before drawing to window)
		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D)tempScreen.getGraphics();
		
		if (fullScreenOn)
			setFullScreen();
	}
		
	public void setFullScreen() {
		
		// GET SYSTEM SCREEN
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Driver.window);
		
		// GET FULL SCREEN WIDTH AND HEIGHT
		screenWidth2 = Driver.window.getWidth();
		screenHeight2 = Driver.window.getHeight();
	}

	public void startGameThread() {		
		gameThread = new Thread(this); // new Thread with GamePanel class
		gameThread.start(); // calls run() method
	}
	
	public void retry() {	
		stopMusic();		
		
		player.alive = true;		
		player.setDefaultPosition();
		player.restoreHearts();
		
		ui.deathSprite = 0;
		ui.deathCounter = 0;
		
		aSetter.setNPC();
		aSetter.setEnemy();
						
		setupMusic();
	}
	
	public void restart() {	
		stopMusic();
		
		player.alive = true;
		player.setDefaultPosition();
		player.restoreHearts();
		player.setDefaultValues();
		player.inventory.clear();
		player.setItems();
		
		ui.deathSprite = 0;
		ui.deathCounter = 0;
		
		aSetter.setNPC();
		aSetter.setEnemy();
		aSetter.setInteractiveTiles();
		aSetter.setObject();		

		setupMusic();
	}
	
	@Override
	public void run() {
		
		long currentTime;
		long lastTime = System.nanoTime();
		double drawInterval = 1000000000 / FPS; // 1/60th of a second
		double delta = 0;
		long timer = 0;
//		int drawCount = 0;
		
		// updates and repaints gameThread x times per second (x fps)
		while(gameThread != null) {
			
			currentTime = System.nanoTime();			
			delta += (currentTime - lastTime) / drawInterval; // time passed / 1/60th second
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
									
				// UPDATE GAME INFORMATION
				update();
				
				// DRAW TEMP SCREEN WITH NEW INFORMATION
				drawToTempScreen(); // calls paintCompoment() method
				
				// SEND TEMP SCREEN TO MONITOR
				drawToScreen();
				
				delta--;				
//				drawCount++;
			}
			// DISPLAY FPS (PER SEC)
			if (timer >= 1000000000) {
//				System.out.println("FPS: " + drawCount);
//				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void update() {
				
		// GAME PLAYING
		if (gameState == playState || gameState == itemState) {
			
			// UPDATE PLAYER
			player.update();	
				
			// UPDATE NPCs
			for (int i = 0; i < npc[1].length; i++) {
				if (npc[currentMap][i] != null)
					npc[currentMap][i].update();
			}
			
			// UPDATE ENEMIES			
			for (int i = 0; i < enemy[1].length; i++) {
				if (enemy[currentMap][i] != null) {
					
					if (enemy[currentMap][i].alive && !enemy[currentMap][i].dying) 
						enemy[currentMap][i].update();			
					
					if (!enemy[currentMap][i].alive) {
						enemy[currentMap][i].checkDrop();
						enemy[currentMap][i] = null;	
					}
				}
			}
			
			// UPDATE OBJECTS
			for (int i = 0; i < obj[1].length; i++) {
				if (obj[currentMap][i] != null) 
					obj[currentMap][i].update();
			}
			
			// UPDATE INTERACTIVE TILES
			for (int i = 0; i < iTile[1].length; i++) {
				if (iTile[currentMap][i] != null) 
					iTile[currentMap][i].update();
			}
			
			// UPDATE PROJECTILES			
			for (int i = 0; i < projectileList.size(); i++) {
				if (projectileList.get(i) != null) {
					if (projectileList.get(i).alive) 
						projectileList.get(i).update();	
				
					if (!projectileList.get(i).alive)
						projectileList.remove(i);
				}
			}
			
			// UPDATE PARTICLES			
			for (int i = 0; i < particleList.size(); i++) {
				if (particleList.get(i) != null) {
					if (particleList.get(i).alive) 
						particleList.get(i).update();	
					
					if (!particleList.get(i).alive)
						particleList.remove(i);	
				}
			}
		}
		
		// GAME PAUSED
		if (gameState == pauseState) {			
		}
	}
	
	public void drawToTempScreen() {
		
		// TITLE SCREEN
		if (gameState == titleState) {
			ui.draw(g2);
		}
		// GAME START
		else {
			
			// TILES
			tileM.draw(g2);	
			for (int i = 0; i < iTile[1].length; i++) {
				if (iTile[currentMap][i] != null) 
					iTile[currentMap][i].draw(g2);
			}
			
			// ADD TO ENTITY LIST
			entityList.add(player);			
			for (Entity n : npc[currentMap]) { if (n != null) entityList.add(n); }
			for (Entity e : enemy[currentMap]) { if (e != null) entityList.add(e); }
			for (Entity o : obj[currentMap]) { if (o != null) entityList.add(o); }
			for (Entity p : projectileList) { if (p != null) entityList.add(p); }
			for (Entity a : particleList) { if (a != null) entityList.add(a); }
			
			// SORT BY WORLD Y
			Collections.sort(entityList, new Comparator<Entity>() {

				public int compare(Entity e1, Entity e2) {					
					int entityTop = Integer.compare(e1.worldY, e2.worldY);					
					return entityTop;
				}
			});
			
			// DRAW ENTITIES
			for (Entity e : entityList) { e.draw(g2); }
			
			// EMPTY ENTITY LIST
			entityList.clear();
			
			// UI
			ui.draw(g2);	
		}		
		
		// DEBUG HUD
		if (keyH.debug) {			
			g2.setFont(new Font("Arial", Font.PLAIN, 20));
			g2.setColor(Color.white);
			int x = 10, y = 500, lineHeight = 20;
			
			g2.drawString("WorldX: " + player.worldX, x , y); 
			y += lineHeight;
			g2.drawString("WorldY: " + player.worldY, x , y); 
			y += lineHeight;
			g2.drawString("Column: " + (player.worldX + player.hitBox.x) / tileSize, x , y);
			y += lineHeight;
			g2.drawString("Row: " + (player.worldY + player.hitBox.y) / tileSize, x , y);
		}
	}
	
	public void drawToScreen() {		
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();
	}
	
	public void setupMusic() {
		
		if (gameState == titleState) {
			playMusic(0);		
		}
		else {			
			if (currentMap == 0) playMusic(2);
			else if (currentMap == 1) playMusic(4);
		}
	}	
	public void playMusic(int c) {		
		music.setFile(0, c);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSE(int i, int c) {
		se.setFile(i, c);
		se.play();
	}
}