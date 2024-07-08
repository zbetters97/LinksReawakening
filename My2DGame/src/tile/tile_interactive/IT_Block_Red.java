package tile.tile_interactive;

import application.GamePanel;

public class IT_Block_Red extends InteractiveTile {

	public static final String itName = "Block";
	GamePanel gp;
	
	public IT_Block_Red(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		name = itName;
		life = 3;
		switchedOn = false;
		
		direction = "up";
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		up1 = setup("/tiles_interactive/block_red_off");
		down1 = setup("/tiles_interactive/block_red_on");		
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
								
				// WAIT UNTIL PLAYER MOVES OFF BLOCK
				if (gp.iTile[gp.currentMap][i].switchedOn && !gp.cChecker.checkPlayer(this)) {
					collision = true;
					switchedOn = false;
				}
				// TURN OFF IF SWITCH IS OFF
				else  {
					collision = false;			
					switchedOn = true;					
				}				
			}
		}	
	}
}