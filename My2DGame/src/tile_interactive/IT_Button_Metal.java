package tile_interactive;

import main.GamePanel;

public class IT_Button_Metal extends InteractiveTile {

	public static final String itName = "Metal Plate";
	GamePanel gp;
	
	public IT_Button_Metal(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		name = itName;	
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		down1 = setup("/tiles_interactive/button_metal");
	}
	
	public void playSE() {
		gp.playSE(1, 16);
	}
}