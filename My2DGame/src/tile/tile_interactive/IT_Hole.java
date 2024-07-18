package tile.tile_interactive;

import java.awt.Rectangle;

import application.GamePanel;

public class IT_Hole extends InteractiveTile {

	public static final String itName = "Hole";
	
	public IT_Hole(GamePanel gp) {
		super(gp);
		
		name = itName;		
		collision = false;
	
		hitbox = new Rectangle(0,0,0,0);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	public IT_Hole(GamePanel gp, int col, int row) {
		super(gp, col, row);		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		name = itName;
		collision = false;
	
		hitbox = new Rectangle(0,0,0,0);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		hitboxDefaultWidth = hitbox.width;
		hitboxDefaultHeight = hitbox.height;
	}
	
	public void getImage() {
		down1 = setup("/tiles_interactive/hole");
	}
}