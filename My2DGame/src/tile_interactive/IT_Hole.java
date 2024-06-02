package tile_interactive;

import main.GamePanel;

public class IT_Hole extends InteractiveTile {

	GamePanel gp;
	
	public IT_Hole(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		down1 = setup("/tiles/017", gp.tileSize, gp.tileSize);
	
		hitBox.x = 0;
		hitBox.y = 0;
		hitBox.width = 0;
		hitBox.height = 0;
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
	}
}