package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class IT_Button extends InteractiveTile {

	GamePanel gp;
	
	public IT_Button(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		destructible = true;
		grabbale = true;
		life = 1;
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		down1 = setup("/tiles_interactive/metalplate");
	}
	
	public boolean isCorrectItem(Entity entity) {		
		
		boolean isCorrectItem = false;		
		if (entity.name.equals("Arrow"))
			isCorrectItem = true;		
		
		return isCorrectItem;
	}
	
	public void playSE() {
	}
}