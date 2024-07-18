package tile.tile_interactive;

import application.GamePanel;

public class IT_Block_Blue extends InteractiveTile {

	public static final String itName = "Blue Block";
	
	public IT_Block_Blue(GamePanel gp) {
		super(gp);
		direction = "up";
		
		name = itName;
		switchedOn = false;					
	}
	public IT_Block_Blue(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		direction = "up";
		
		name = itName;
		switchedOn = false;		
	}
	
	public void getImage() {
		up1 = setup("/tiles_interactive/block_blue_on");
		down1 = setup("/tiles_interactive/block_blue_off");		
	}
	
	public void update() { 		
		if (switchedOn) direction = "up";
		else direction = "down";
		detectSwitch();
	}
	
	public void detectSwitch() {
					
		for (int i = 0; i < gp.iTile[1].length; i++) {
			
			// FIND SWITCH IN iTILE LIST
			if (gp.iTile[gp.currentMap][i] != null && 
					gp.iTile[gp.currentMap][i].name != null &&
					gp.iTile[gp.currentMap][i].name.equals(IT_Switch.itName)) {
				
				// TURN OFF IF SWITCH IS ON
				if (gp.iTile[gp.currentMap][i].switchedOn) {
					collision = false;
					switchedOn = false;
				}
				// TURN ON IF NO COLLISION
				else if (!gp.cChecker.checkPlayer(this))  {
					collision = true;			
					switchedOn = true;					
				}
			}
		}	
	}
}