package tile.tile_interactive;

import application.GamePanel;

public class IT_Plate_Metal extends InteractiveTile {

	public static final String itName = "Metal Plate";
	
	public IT_Plate_Metal(GamePanel gp) {
		super(gp);
		
		name = itName;	
		collision = false;
	}
	public IT_Plate_Metal(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		name = itName;	
		collision = false;
	}
	
	public void getImage() {
		down1 = setup("/tiles_interactive/plate_metal");
	}
	
	public void playSE() {
		gp.playSE(6, 0);
	}
}