package tile.tile_interactive;

import java.awt.Rectangle;

import application.GamePanel;

public class IT_Trunk extends InteractiveTile {

	public static final String itName = "Tree Trunk";
	GamePanel gp;
	
	public IT_Trunk(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		name = itName;
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		collision = false;
	
		hitbox = new Rectangle(0,0,0,0);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		down1 = setup("/tiles_interactive/trunk");
	}
}