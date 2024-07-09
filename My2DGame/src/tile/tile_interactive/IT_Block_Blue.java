package tile.tile_interactive;

import java.awt.Rectangle;

import application.GamePanel;

public class IT_Block_Blue extends InteractiveTile {

	public static final String itName = "Block";
	GamePanel gp;
	
	public IT_Block_Blue(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		name = itName;
		switchedOn = false;
		
		direction = "up";
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		hitbox = new Rectangle(0, 0, 48, 48); 	
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
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
				// WAIT UNTIL PLAYER MOVES OFF BLOCK
				else if (!gp.cChecker.checkPlayer(this))  {
					collision = true;			
					switchedOn = true;					
				}
			}
		}	
	}
}