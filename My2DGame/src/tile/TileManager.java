package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	
	public int mapTileNum[][];
	
	/** CONSTRUCTOR **/
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[10]; // total number of all tile types 
		
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; // store numbered tiles for map
		
		getTileImage();
		loadMap("/maps/world01.txt");
	}
	
	public void getTileImage() {		
		setup(0, "grass", false);
		setup(1, "wall", true);
		setup(2, "water", true);
		setup(3, "sand", false);
		setup(4, "tree", true);
		setup(5, "earth", false);
	}
	
	public void setup(int index, String imageName, boolean collision) {
		
		UtilityTool utility = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+imageName+".png"));
			tile[index].image = utility.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath) {
		
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
					
					mapTileNum[col][row] = num; // store tile # from map in array
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
	
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow]; // get number from map txt data
			
			int worldX = worldCol * gp.tileSize; // where player is on map
			int worldY = worldRow * gp.tileSize; // where player is on map
			
			// screen coordinates added to world coordinates to shift player to center 
			int screenX = worldX - gp.player.worldX + gp.player.screenX; // where player is on screen
			int screenY = worldY - gp.player.worldY + gp.player.screenY; // where player is on screen
						
			// only draw tiles within player boundary
			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
								
			worldCol++; // advance to next column
			
			if (worldCol == gp.maxWorldCol) { 
				worldCol = 0; // do not advance column
				worldRow++; // advance row
			}
		}
		
	}
}














