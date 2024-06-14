package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import ai.PathFinder;
import data.SaveLoad;
import entity.*;
import environment.EnvironmentManager;
import tile.Map;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable {
	
	// GENERAL CONFIG
	Config config = new Config(this);
	Graphics2D g2;
	Thread gameThread;
	int FPS = 60;
	
	// CONTROLS / SOUND / UI
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();	
	public UI ui = new UI(this);	
	
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
	public boolean fullScreenOn = false;
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;	
	
	public int volumeSet = 0;
	
	// GAME STATES
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;		
	public final int mapState = 3;
	public final int characterState = 4;
	public final int dialogueState = 5;		
	public final int tradeState = 6;
	public final int itemGetState = 7;
	public final int objectState = 8;	
	public final int transitionState = 9;	
	public final int sleepState = 10;
	public final int gameOverState = 11;
	
	// AREA STATES
	public int currentArea;
	public int nextArea;
	public final int outside = 50;
	public final int inside = 51;
	public final int dungeon = 52;
	
	// PLAYER / ENTITY / ENEMY / OBJECT
	public Player player = new Player(this, keyH);	
	public Entity npc[][] = new Entity[maxMap][10]; // total amount of npc displayed at once	
	public Entity enemy[][] = new Entity[maxMap][20]; // total amount of enemies displayed at once
	public Entity obj[][] = new Entity[maxMap][20]; // total amount of items displayed at once
	public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
	public ArrayList<Entity> entityList = new ArrayList<>();
	public Entity projectile[][] = new Entity[maxMap][20];
	public ArrayList<Entity> particleList = new ArrayList<>();

	// HANDLERS
	public TileManager tileM = new TileManager(this);
	public EnvironmentManager eManager = new EnvironmentManager(this);
	public CollisionChecker cChecker = new CollisionChecker(this);	
	public EventHandler eHandler = new EventHandler(this);	
	public AssetSetter aSetter = new AssetSetter(this);
	public PathFinder pFinder = new PathFinder(this);
	public EntityGenerator eGenerator = new EntityGenerator(this);
	
	// SAVE LOAD MANAGER
	SaveLoad saveLoad = new SaveLoad(this);
	
	// MAP
	Map map = new Map(this);
	
	/** CONSTRUCTOR **/
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // screen size
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); // improves rendering performance
		
		this.addKeyListener(keyH);
		this.setFocusable(true); // GamePanel in focus to receive input
	}
	
	public void setupGame() {		
		
		gameState = titleState;	
		currentArea = outside;
//		gameState = playState;
// 		currentArea = inside;
		
		setupMusic();

		eManager.setup();
		
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
	
	@Override
	public void run() {
		
		long currentTime;
		long lastTime = System.nanoTime();
		double drawInterval = 1000000000 / FPS; // 1/60th of a second
		double delta = 0;
		
		// UPDATE AND REPAINT gameThread (60 FPS)
		while (gameThread != null) {
			
			currentTime = System.nanoTime();			
			delta += (currentTime - lastTime) / drawInterval; // time passed (1/60th second)
			lastTime = currentTime;
			
			if (delta >= 1) {
									
				// UPDATE GAME INFORMATION
				update();
				
				// DRAW TEMP SCREEN WITH NEW INFORMATION
				drawToTempScreen();
				
				// SEND TEMP SCREEN TO MONITOR
				drawToScreen();
				
				delta--;		
			}
		}
	}
	
	public void update() {
				
		// GAME PLAYING
		if (gameState == playState || gameState == objectState) {
			
			// UPDATE PLAYER
			player.update();	
			
			// UPDATE ENVIRONMENT
			eManager.update();
									
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
				if (obj[currentMap][i] != null) {
					obj[currentMap][i].update();
					if (!obj[currentMap][i].alive)
						obj[currentMap][i] = null;
				}
			}
			
			// UPDATE INTERACTIVE TILES
			for (int i = 0; i < iTile[1].length; i++) {
				if (iTile[currentMap][i] != null) 
					iTile[currentMap][i].update();
			}
			
			// UPDATE PROJECTILES			
			for (int i = 0; i < projectile[1].length; i++) {
				if (projectile[currentMap][i] != null) {
					if (projectile[currentMap][i].alive) 
						projectile[currentMap][i].update();	
				
					if (!projectile[currentMap][i].alive)
						projectile[currentMap][i] = null;
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
		if (gameState == pauseState) { }
	}
	
	public void changeArea() {
		
		if (nextArea != currentArea) {
			stopMusic();			
			setupMusic();
		}
		
		currentArea = nextArea;
		aSetter.setEnemy();
	}
	
	public void resetGame(boolean restart) {
		stopMusic();
		
		currentArea = outside;
		player.alive = true;		
		player.restoreStatus();
		player.setDefaultPosition();	
		player.resetCounter();
		aSetter.setNPC();
		aSetter.setEnemy();		
		
		if (restart) {
			player.inventory.clear();
			player.setDefaultValues();	
			aSetter.setInteractiveTiles();
			aSetter.setObject();
			
			eManager.lighting.resetDay();
		}
		
		setupMusic();
	}
	
	// SOUND HANDLER **/
	public void setupMusic() {
		
		if (gameState == titleState) playMusic(0);			
		else {			
			if (currentMap == 0) playMusic(2);
			else if (currentMap == 1) playMusic(3);
			else if (currentMap == 2) playMusic(4);
			else if (currentMap == 3) playMusic(4);
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
	/** END SOUND HANDLER **/
	
	public void drawToTempScreen() {
		
		// TITLE SCREEN
		if (gameState == titleState) {
			ui.draw(g2);
		}		
		// MAP SCREEN
		else if (gameState == mapState) {
			map.drawFullMapScreen(g2);
		}		
		// GAME START
		else {			
			// DRAW TILES
			tileM.draw(g2);	
			for (int i = 0; i < iTile[1].length; i++) {
				if (iTile[currentMap][i] != null) 
					iTile[currentMap][i].draw(g2);
			}
						
			// POPULATE ENTITY LIST
			entityList.add(player);			
			for (Entity n : npc[currentMap]) { if (n != null) entityList.add(n); }
			for (Entity e : enemy[currentMap]) { if (e != null) entityList.add(e); }
			for (Entity o : obj[currentMap]) { if (o != null) entityList.add(o); }
			for (Entity a : particleList) { if (a != null) entityList.add(a); }
			
			for (int i = 0; i < projectile[1].length; i++) {
				if (projectile[currentMap][i] != null)
					entityList.add(projectile[currentMap][i]);
			}
			
			// SORT DRAW ORDER BY Y COORD
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
			
			// DRAW ENVIRONMENT
			eManager.draw(g2);
			
			// DRAW UI
			ui.draw(g2);			

			// DRAW MINIMAP
			map.drawMiniMap(g2);
		}		
	}
	public void drawToScreen() {		
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();
	}
}