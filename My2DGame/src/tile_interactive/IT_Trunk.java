package tile_interactive;

import java.awt.Rectangle;

import main.GamePanel;

public class IT_Trunk extends InteractiveTile {

	GamePanel gp;
	
	public IT_Trunk(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
	
		hitBox = new Rectangle(0,0,0,0);
		hitBoxDefaultX = hitBox.x;
		hitBoxDefaultY = hitBox.y;
		
		down1 = setup("/tiles_interactive/trunk");
	}
}