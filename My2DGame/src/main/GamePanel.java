package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;

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
	
	// FPS
	int FPS = 60;
	
	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;	
	public final int characterState = 4;
	
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
	public Entity npc[] = new Entity[10]; // total amount of npc displayed at once	
	public Entity enemy[] = new Entity[20]; // total amount of enemies displayed at once
	public Entity obj[] = new Entity[10]; // total amount of items displayed at once
	ArrayList<Entity> entityList = new ArrayList<>();
	
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
		
		gameState = titleState;
		playMusic(0);
		
//		gameState = playState;;
//		playMusic(1);
		
		aSetter.setNPC();
		aSetter.setEnemy();
		aSetter.setObject();	
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
		long timer = 0;
//		int drawCount = 0;
		
		// updates and repaints gameThread x times per second (x fps)
		while(gameThread != null) {
			
			currentTime = System.nanoTime();			
			delta += (currentTime - lastTime) / drawInterval; // time passed / 1/60th second
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
									
				// UPDATE INFORMATION (character positions...)
				update();
				
				// DRAW SCREEN WITH INFORMATION
				repaint(); // calls paintCompoment() method
				
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
		if (gameState == playState) {
			
			// UPDATE PLAYER
			player.update();	
				
			// UPDATE NPCs
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null)
					npc[i].update();
			}
			
			// UPDATE ENEMIES			
			for (int i = 0; i < enemy.length; i++) {
				if (enemy[i] != null) {
					if (enemy[i].alive && !enemy[i].dying) 
						enemy[i].update();					
					if (!enemy[i].alive)
						enemy[i] = null;					
				}
			}
			
			// UPDATE OBJECTS
			for (int i = 0; i < obj.length; i++) {
				if (obj[i] != null) obj[i].update();
			}
		}
		
		// GAME PAUSED
		if (gameState == pauseState) {			
		}
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g); // required for paintCompoment() method
		
		Graphics2D g2 = (Graphics2D)g; // Graphics2D has more functionality than Graphics
		
		// TITLE SCREEN
		if (gameState == titleState) {
			ui.draw(g2);
		}
		// GAME START
		else {
			// TILES
			tileM.draw(g2);	
			
			// ADD PLAYER, ENTITIES, AND OBJECTS TO LIST
			entityList.add(player);			
			for (Entity e : npc) { if (e != null) entityList.add(e); }
			for (Entity e : enemy) { if (e != null) entityList.add(e); }
			for (Entity o : obj) { if (o != null) entityList.add(o); }
			
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
		
		if (keyH.debug) {			
			g2.setFont(new Font("Arial", Font.PLAIN, 20));
			g2.setColor(Color.white);
			int x = 10, y = 500, lineHeight = 20;
			
			g2.drawString("WorldX: " + player.worldX, x , y); 
			y += lineHeight;
			g2.drawString("WorldY: " + player.worldY, x , y); 
			y += lineHeight;
			g2.drawString("Column: " + (player.worldX + player.solidArea.x) / tileSize, x , y);
			y += lineHeight;
			g2.drawString("Row: " + (player.worldY + player.solidArea.y) / tileSize, x , y);
		}
		
		// MEMORY DUMP
		g2.dispose(); 
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