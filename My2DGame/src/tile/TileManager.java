package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import application.GamePanel;
import application.UtilityTool;

public class TileManager {
	
	private GamePanel gp;
	public Tile[] tile;
	
	public int blockTile1 = 38;
	public int blockTile2 = 49;
	
	public int waterTile = 4;
	public int oceanTile1 = 19;
	public int oceanTile2 = 39;
	
	public int spikeTile = 77;
	
	private int waterCounter = 0;
	private int waterCounterMax = 45;
	private int waterNum = 1;
		
	// [MAP NUMBER][ROW][COL]
	public int mapTileNum[][][];
	
	ArrayList<String> fileNames = new ArrayList<>();
	ArrayList<String> collisionStatus = new ArrayList<>();
	ArrayList<String> waterStatus = new ArrayList<>();
	ArrayList<String> pitStatus = new ArrayList<>();
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		// IMPORT TILE DATA		
		InputStream is = getClass().getResourceAsStream("/maps/tiledata.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		// ADD TILE DATA TO ARRAYS
		try {
			String line;
			while ((line = br.readLine()) != null) {
				fileNames.add(line);
				collisionStatus.add(br.readLine());	
				waterStatus.add(br.readLine());
				pitStatus.add(br.readLine());
			}						
			br.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// ASSIGN TILE COLLISION
		tile = new Tile[fileNames.size()];
		getTileImage(); 
		
		// IMPORT MAP SIZE
		is = getClass().getResourceAsStream("/maps/worldmap.txt");	
		br = new BufferedReader(new InputStreamReader(is));

		try {
			String line = br.readLine();
			String maxTile[] = line.split(" ");			
			
			gp.maxWorldCol = maxTile.length * 2;
			gp.maxWorldRow = maxTile.length * 2;
			mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
			
			gp.worldWidth = gp.tileSize * gp.maxWorldCol;
			gp.worldHeight = gp.tileSize * gp.maxWorldRow;
			
			br.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}		

		loadMap("/maps/worldmap.txt", 50, 50, 0);
		loadMap("/maps/indoor01.txt", 50, 50, 1);
//		loadMap("/maps/dungeon01.txt", 2);
		loadMap("/maps/dungeon_01_01.txt", 2);
		loadMap("/maps/dungeon02.txt", 50, 50, 3);
	}
	
	public void getTileImage() {		
		
		// loop through all tile data in fileNames
		for (int i = 0; i< fileNames.size(); i++) {
			
			String fileName;
			boolean collision, water, pit;
			
			// assign each name to fileName
			fileName = fileNames.get(i);
			
			// assign tile status
			if (collisionStatus.get(i).equals("true")) 
				collision = true;
			else
				collision = false;
			
			if (waterStatus.get(i).equals("true")) 
				water = true;
			else
				water = false;
			
			if (pitStatus.get(i).equals("true")) 
				pit = true;
			else
				pit = false;
						
			setup(i, fileName, collision, water, pit);
		}
	}
	
	public void setup(int index, String imageName, 
			boolean collision, boolean water, boolean pit) {
		
		UtilityTool utility = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName));
			tile[index].image = utility.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			tile[index].water = water;
			tile[index].pit = pit;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath, int mapNum) {
		
		try {			
			InputStream is = getClass().getResourceAsStream(filePath); // import map txt file
			BufferedReader br = new BufferedReader(new InputStreamReader(is)); // read map
			
			int col = 0;
			int row = 0;
			
			// loop until map boundaries are hit
			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
								
				String line = br.readLine(); // read entire row
				
				while (col < gp.maxWorldCol) {
					
					String numbers[] = line.split(" "); // space is separator
					int num = Integer.parseInt(numbers[col]); // convert txt to int
					
					mapTileNum[mapNum][col][row] = num; // store tile # from map in array
					col++; // advance to next column					
				}
				
				if (col == gp.maxWorldCol) {
					col = 0; // do not advance column
					row++; // advance to next row
				}
			}
			
			br.close(); // flush from memory
		} 
		catch(Exception e) {
			e.printStackTrace();
		}		
	}	
	
	public void loadMap(String filePath, int width, int height, int mapNum) {
		
		try {			
			InputStream is = getClass().getResourceAsStream(filePath); // import map txt file
			BufferedReader br = new BufferedReader(new InputStreamReader(is)); // read map
			
			int col = 0;
			int row = 0;
			
			// loop until map boundaries are hit
			while (col < width && row < height) {
								
				String line = br.readLine(); // read entire row
				
				while (col < width) {
					
					String numbers[] = line.split(" "); // space is separator
					int num = Integer.parseInt(numbers[col]); // convert txt to int
					
					mapTileNum[mapNum][col][row] = num; // store tile # from map in array
					col++; // advance to next column					
				}
				
				if (col == width) {
					col = 0; // do not advance column
					row++; // advance to next row
				}
			}
			
			br.close(); // flush from memory
		} 
		catch(Exception e) {
			e.printStackTrace();
		}		
	}	
	
	public void draw(Graphics2D g2) {				
				
		int worldCol = 0;
		int worldRow = 0;
		boolean offCenter = false;
		
		waterCounter++;
		if (waterCounter >= waterCounterMax) {
			waterCounter = 0;
			if (waterNum == 1) waterNum = 2;
			else waterNum = 1;
		}
				
		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			// TILE NUMBERS FROM MAP TXT
			int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
			
			// WORLD X,Y
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			
			// PLAYER SCREEN POSITION X,Y OFFSET TO CENTER
			int screenX = worldX - gp.player.worldX + gp.player.screenX; 
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			// STOP CAMERA MOVEMENT AT WORLD BOUNDARY
			if (gp.player.screenX > gp.player.worldX) {
				screenX = worldX;
				offCenter = true;
			}
			if (gp.player.screenY > gp.player.worldY) {
				screenY = worldY;
				offCenter = true;
			}
			
			// FROM PLAYER TO RIGHT-EDGE OF SCREEN
			int rightOffset = gp.screenWidth - gp.player.screenX;		
			
			// FROM PLAYER TO RIGHT-EDGE OF WORLD
			if (rightOffset > gp.worldWidth - gp.player.worldX) {
				screenX = gp.screenWidth - (gp.worldWidth - worldX);
				offCenter = true;
			}			
			
			// FROM PLAYER TO BOTTOM-EDGE OF SCREEN
			int bottomOffSet = gp.screenHeight - gp.player.screenY;
			
			// FROM PLAYER TO BOTTOM-EDGE OF WORLD
			if (bottomOffSet > gp.worldHeight - gp.player.worldY) {
				screenY = gp.screenHeight - (gp.worldHeight - worldY);
				offCenter = true;
			}
			
			// DRAW TILES WITHIN PLAYER BOUNDARY
			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				if (tileNum == oceanTile1) {
					if (waterNum == 2) {
						tileNum = oceanTile2;
					}						
				}
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			else if (offCenter) {		
				if (tileNum == oceanTile1) {
					if (waterNum == 2) {
						tileNum = oceanTile2;
					}						
				}
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
										
			// TO NEXT COLUMN
			worldCol++;
			
			// TO NEXT ROW
			if (worldCol == gp.maxWorldCol) { 
				worldCol = 0;
				worldRow++; 
			}
		}
	}
}