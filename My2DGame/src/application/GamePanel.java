package application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.JPanel;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import entity.collectable.COL_Fairy;
import entity.object.OBJ_Door_Closed;
import environment.EnvironmentManager;
import event.EventHandler;
import tile.TileManager;
import tile.tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable {
	
	// GENERAL CONFIG
	private static final long serialVersionUID = 5792031433632402979L;
	public ConfigManager config = new ConfigManager(this);
	private Graphics2D g2;
	private Thread gameThread;
	private int FPS = 60;
	public int fileSlot = 0;
	
	// CONTROLS / SOUND / UI
	public KeyHandler keyH = new KeyHandler(this);
	public UI ui = new UI(this);
	public Sound music = new Sound();
	public Sound se = new Sound();		
	
	// BUTTON MAPPING
	public int button_dirUP = KeyEvent.VK_UP;
	public int button_dirDOWN = KeyEvent.VK_DOWN;
	public int button_dirLEFT = KeyEvent.VK_LEFT;
	public int button_dirRIGHT = KeyEvent.VK_RIGHT;
	public int button_action = KeyEvent.VK_SPACE;		
	public int button_guard = KeyEvent.VK_Z;
	public int button_target = KeyEvent.VK_F;
	public int button_roll = KeyEvent.VK_R;
	public int button_grab = KeyEvent.VK_G;	
	public int button_item = KeyEvent.VK_Q;
	public int button_tab = KeyEvent.VK_T;	
	public int button_inventory = KeyEvent.VK_E;		
	public int button_map = KeyEvent.VK_M;
	public int button_minimap = KeyEvent.VK_N;
	public int button_pause = KeyEvent.VK_ESCAPE;
	public int button_debug = KeyEvent.VK_SHIFT;
	
	// SCREEN SETTINGS
	private final int originalTileSize = 16; // 16x16 tile
	private final int scale = 3; // scale rate to accommodate for large screen
	public final int tileSize = originalTileSize * scale; // scaled tile (16*3 = 48px)	
	public final int maxScreenCol = 16; // columns (width)
	public final int maxScreenRow = 12; // rows (height)
	public final int screenWidth = tileSize * maxScreenCol; // screen width (in tiles) 768px
	public final int screenHeight = tileSize * maxScreenRow; // screen height (in tiles) 576px
		
	// WORLD SIZE (assigned in Tile Manager)
	public int maxWorldCol;
	public int maxWorldRow;
	public int worldWidth;
	public int worldHeight;
	
	// AMOUNT OF TOTAL MAPS	
	public String[] mapFiles = { "worldmap.txt", "indoor01.txt", "dungeon_01_01.txt", "dungeon_01_02.txt" };
	public final int maxMap = mapFiles.length;
	public int currentMap = 0;
	private HashMap<Integer, String> mapNames;
	
	// FULL SCREEN SETTINGS
	public boolean fullScreenOn = false;
	private int screenWidth2 = screenWidth;
	private int screenHeight2 = screenHeight;
	private BufferedImage tempScreen;	
	
	// MAP HANDLER
	public Map map = new Map(this);
	
	// VOLUME ADJUSTMENT
	public int volumeSet = 0;
	
	// GAME STATES
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;		
	public final int mapState = 3;
	public final int inventoryState = 4;
	public final int dialogueState = 5;		
	public final int tradeState = 6;
	public final int itemGetState = 7;
	public final int objectState = 8;	
	public final int fallingState = 9;
	public final int drowningState = 10;
	public final int transitionState = 11;	
	public final int cutsceneState = 12;
	public final int sleepState = 13;
	public final int gameOverState = 14;
	public final int endingState = 15;
	
	// AREA STATES
	public int currentArea;
	public int nextArea;	
	public final int outside = 1;
	public final int house = 2;
	public final int shop = 3;
	public final int dungeon = 4;
	
	public boolean bossBattleOn = false;
	
	// PLAYER / ENTITY / ENEMY / OBJECT
	public Player player = new Player(this);	
	public Entity npc[][] = new Entity[maxMap][10]; 
	public Entity enemy[][] = new Entity[maxMap][50]; 
	public Entity obj[][] = new Entity[maxMap][20];
	public Entity obj_i[][] = new Entity[maxMap][20]; 
	public InteractiveTile iTile[][] = new InteractiveTile[maxMap][100];
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
	public iTileGenerator iGenerator = new iTileGenerator(this);
	public SceneManager csManager = new SceneManager(this);
	
	// SAVE LOAD MANAGER
	public SaveLoad saveLoad = new SaveLoad(this);
	
/** CONSTRUCTOR **/
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // screen size
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); // improves rendering performance
		
		this.addKeyListener(keyH);
		this.setFocusable(true); // GamePanel in focus to receive input
	}
	
	protected void setupGame() {	
						
 		gameState = playState;	
		currentArea = outside;
		currentMap = 0;
/*		
		gameState = playState;
		currentArea = dungeon;
		currentMap = 2;
*/
		
		tileM.loadMap();
		map.loadWorldMap();
		
		setupMusic(false);
		setupAreaNames();

		eManager.setup();
		
		player.setDefaultValues();	
		
		aSetter.setNPC();
		aSetter.setEnemy();
		aSetter.setObject();
		aSetter.setInteractiveObjects();
		aSetter.setInteractiveTiles(true);	
		
		// TEMP GAME WINDOW (before drawing to window)
		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D)tempScreen.getGraphics();
		
		if (fullScreenOn) setFullScreen();
	}
		
	private void setFullScreen() {
		
		// GET SYSTEM SCREEN
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Driver.window);
		
		// GET FULL SCREEN WIDTH AND HEIGHT
		screenWidth2 = Driver.window.getWidth();
		screenHeight2 = Driver.window.getHeight();
	}
	
	protected void startGameThread() {		
		gameThread = new Thread(this); // new Thread with GamePanel class
		gameThread.start(); // calls run() method
	}
	
	@Override
	public void run() {		
		
		long currentTime;
		long lastTime = System.nanoTime();
		double drawInterval = 1000000000 / FPS; // 1/60th of a second
		double delta = 0;
		
		// UPDATE AND REPAINT gameThread
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

//				delta--;
				delta = 0;		
			}		
		}
	}
	
	private void update() {
		
		// GAME PLAYING
		if (gameState == playState || gameState == objectState) {
			
			eManager.update();
			
			player.update();	
			updateNPC();
			updateEnemies();
			updateObjects();
			updateiTiles();
			updateProjectiles();
			updateParticles();
			updateFairies();
		}
		// CUTSCENE
		else if (gameState == cutsceneState) {			
			updateNPC();			
			updateObjects();
			updateObjects();
			updateiTiles();
			updateParticles();	
			updateFairies();
		}		
		// DISABLE KEY INPUTS WHEN FALLING/DROWNING
		else if (gameState == fallingState || gameState == drowningState) { 
			player.takingDamage(); 
		}
		
		// GAME PAUSED
		if (gameState == pauseState) { }
	}
	
	private void updateNPC() {
		for (int i = 0; i < npc[1].length; i++) {
			if (npc[currentMap][i] != null)
				npc[currentMap][i].update();
		}
	}	
	private void updateEnemies() {		
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
	}	
	private void updateObjects() {
		for (int i = 0; i < obj[1].length; i++) {
			if (obj[currentMap][i] != null) {
				obj[currentMap][i].update();
				if (!obj[currentMap][i].alive)
					obj[currentMap][i] = null;
			}
		}
		for (int i = 0; i < obj_i[1].length; i++) {
			if (obj_i[currentMap][i] != null) {
				obj_i[currentMap][i].update();
				if (!obj_i[currentMap][i].alive)
					obj_i[currentMap][i] = null;
			}
		}
	}	
	private void updateiTiles() {
		for (int i = 0; i < iTile[1].length; i++) {
			if (iTile[currentMap][i] != null) {
				iTile[currentMap][i].update();
				if (!iTile[currentMap][i].alive) 
					iTile[currentMap][i] = null;				
			}
		}
	}
	private void updateProjectiles() {
		for (int i = 0; i < projectile[1].length; i++) {
			if (projectile[currentMap][i] != null) {
				projectile[currentMap][i].update();	
				if (!projectile[currentMap][i].alive) 
					projectile[currentMap][i] = null;
			}
		}
	}
	private void updateParticles() {
		for (int i = 0; i < particleList.size(); i++) {
			if (particleList.get(i) != null) {
				particleList.get(i).update();	
				if (!particleList.get(i).alive) 
					particleList.remove(i);						
			}
		}
	}
	private void updateFairies() {
		for (int i = 0; i < obj[1].length; i++) {
			if (obj[currentMap][i] != null && obj[currentMap][i].name.equals(COL_Fairy.colName))
				obj[currentMap][i].update();
		}
	}	
	
	public void changeArea() {
					
		if (nextArea != currentArea) {
			stopMusic();			
			setupMusic(false);
		}		
				
		removeCollectables();		
		player.resetValues();
		
		tileM.loadMap();
		map.loadWorldMap();		
		
		if (nextArea != currentArea) {
			stopMusic();			
			setupMusic(false);
			setAreaTitle();
		}	
		
		currentArea = nextArea;
		
		aSetter.setInteractiveObjects();
		aSetter.setInteractiveTiles(false);
	}
	
	private void removeCollectables() {		

		for (int mapNum = 0; mapNum < maxMap; mapNum++) {
			
			for (int i = 0; i < obj[1].length; i++) {
				if (obj[mapNum][i] != null && obj[mapNum][i].type == obj[mapNum][i].type_collectable) {
					obj[mapNum][i] = null;
				}
			}				
		}
	}
	
	public void removeTempEntity(boolean reset) {		

		for (int mapNum = 0; mapNum < maxMap; mapNum++) {
			
			for (int i = 0; i < npc[1].length; i++) {
				if (npc[mapNum][i] != null && npc[mapNum][i].temp) {
					npc[mapNum][i] = null;
				}
			}	
			for (int i = 0; i < enemy[1].length; i++) {
				if (enemy[mapNum][i] != null && enemy[mapNum][i].temp) {
					enemy[mapNum][i] = null;
				}
			}	
			for (int i = 0; i < obj[1].length; i++) {
				if (obj[mapNum][i] != null && obj[mapNum][i].temp) {
					
					if (obj[mapNum][i].name.equals(OBJ_Door_Closed.objName)) {
						if (!reset) obj[mapNum][i].playSE();
						obj[mapNum][i].opening = true;
					}
					else {
						obj[mapNum][i] = null;	
					}
				}
			}	
			for (int i = 0; i < obj_i[1].length; i++) {
				if (obj_i[mapNum][i] != null && obj_i[mapNum][i].temp) {
					obj_i[mapNum][i] = null;
				}
			}	
		}
	}
	public void removeProjectiles() {		
		for (int i = 0; i < projectile[1].length; i++) {
			if (projectile[currentMap][i] != null) {
				projectile[currentMap][i].resetValues();
				projectile[currentMap][i].alive = false; 
				projectile[currentMap][i] = null;
			}
		}
	}
	public void openDoor(int dWorldX, int dWorldY, String dName) {
		
		dWorldX *= tileSize;
		dWorldY *= tileSize;
		
		for (Entity d : obj[currentMap]) {
			if (d != null && d.name != null && d.name.equals(dName) && !d.opening) {
				if (d.worldX == dWorldX && d.worldY == dWorldY) {
					d.playSE();
					d.opening = true;
				}
			}
		}
	}
	
	public void resetGame() {
		stopMusic();
		
		removeTempEntity(true);
		bossBattleOn = false;
		csManager.scene = csManager.NA;
		csManager.phase = 0;
		
		player.alive = true;	
		player.inventory.clear();
		player.inventory_item.clear();
		player.restoreStatus();
		player.setDefaultValues();
		player.setDefaultPosition();	
		player.resetCounter();		
		
		ui.rupeeCount = 0;
		
		aSetter.setNPC();
		aSetter.setEnemy();		
		aSetter.setObject();
		aSetter.setInteractiveObjects();
		aSetter.setInteractiveTiles(true);
		
		eManager.lighting.resetDay();	
	}
	
	public void setupMusic(boolean reset) {		
		
		if (gameState == titleState) playMusic(0);
		else if (reset || nextArea != currentArea) {							
			if (currentMap == 0) playMusic(1);
			else if (currentMap == 1) playMusic(3);
			else if (currentMap == 2) playMusic(4);
			else if (currentMap == 3) playMusic(4);
		}
	}	
	public void setupAreaNames() {
		
		// GIVE EACH MAP A NAME
		mapNames = new HashMap<Integer, String>();		
		mapNames.put(0,"Hyrule Field");
		mapNames.put(1, "NULL");
		mapNames.put(2, "Dungeon 1");		
		mapNames.put(3, "NULL");
	}
	
	public void setAreaTitle() {
		
		// MAP HAS NAME
		if (!mapNames.get(currentMap).equals("NULL")) {
			ui.mapName = mapNames.get(currentMap);
			ui.mapNameCounter = 240;		
			ui.mapNameAlpha = 0;
		}
	}
	
	private void drawToTempScreen() {
		
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
			
			// DRAW iTILES (NOT GRABBED OR THROWN)
			for (InteractiveTile t : iTile[currentMap]) {
				if (t != null && (!t.grabbed || !t.thrown)) t.draw(g2);
			}
						
			// ALWAYS DRAW DIVING PLAYER FIRST
			if (player.diving) player.draw(g2);			
			else entityList.add(player);						
			
			// POPULATE ENTITY LIST
			for (Entity n : npc[currentMap]) { if (n != null) entityList.add(n); }
			for (Entity e : enemy[currentMap]) { if (e != null) entityList.add(e); }
			for (Entity o : obj[currentMap]) { if (o != null) entityList.add(o); }
			for (Entity oi : obj_i[currentMap]) { if (oi != null) entityList.add(oi); }
			for (Entity t : iTile[currentMap]) { if (t != null && t.type == t.type_obstacle) entityList.add(t); }
			for (Entity pr : projectile[currentMap]) { if (pr != null && !pr.grabbed) entityList.add(pr); }
			for (Entity pa : particleList) { if (pa != null) entityList.add(pa); }									
			
			// SORT DRAW ORDER BY Y COORD
			Collections.sort(entityList, new Comparator<Entity>() {
				public int compare(Entity e1, Entity e2) {					
					int entityTop = Integer.compare(e1.worldY, e2.worldY);					
					return entityTop;
				}
			});
			
			// DRAW ENTITIES
			for (Entity e : entityList) { e.draw(g2); }
			
			// DRAW GRABBED iTILES
			for (InteractiveTile i : iTile[currentMap]) {
				if (i != null && i.grabbed) i.draw(g2);
			}
			
			// DRAW GRABBED OR THROWN PROJECTILES
			for (Entity p : projectile[currentMap]) {
				if (p != null && (p.grabbed || p.thrown)) p.draw(g2);				
			}
			
			// EMPTY ENTITY LIST
			entityList.clear();
			
			// DRAW ENVIRONMENT			
			eManager.draw(g2);
			
			// DRAW UI
			ui.draw(g2);			

			// DRAW MINIMAP
			map.drawMiniMap(g2);
			
			// DRAW CUTSCENE
			csManager.draw(g2);
		}		
	}
	private void drawToScreen() {		
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);	
		g.dispose();
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