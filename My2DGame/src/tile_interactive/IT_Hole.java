package tile_interactive;

import java.awt.Rectangle;

import main.GamePanel;

public class IT_Hole extends InteractiveTile {

	public static final String itName = "Hole";
	GamePanel gp;
	
	public IT_Hole(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		name = itName;
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
	
		hitbox = new Rectangle(0,0,0,0);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		down1 = setup("/tiles_interactive/hole");
	}
}