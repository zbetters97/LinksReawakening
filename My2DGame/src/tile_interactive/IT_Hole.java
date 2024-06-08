package tile_interactive;

import java.awt.Rectangle;

import main.GamePanel;

public class IT_Hole extends InteractiveTile {

	GamePanel gp;
	
	public IT_Hole(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
	
		hitbox = new Rectangle(0,0,0,0);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		down1 = setup("/tiles/017");
	}
}