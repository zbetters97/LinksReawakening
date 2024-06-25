package tile.tile_interactive;

import application.GamePanel;

public class IT_Plate_Metal extends InteractiveTile {

	public static final String itName = "Metal Plate";
	GamePanel gp;
	
	public IT_Plate_Metal(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		name = itName;	
		collision = false;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		down1 = setup("/tiles_interactive/plate_metal");
	}
	
	public void playSE() {
		gp.playSE(6, 1);
	}
}