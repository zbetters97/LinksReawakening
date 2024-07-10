package tile.tile_interactive;

import java.awt.Rectangle;

import application.GamePanel;

public class IT_Block_Red extends InteractiveTile {

	public static final String itName = "Block";
	GamePanel gp;
	
	public IT_Block_Red(GamePanel gp, int col, int row) {
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
		
		up1 = setup("/tiles_interactive/block_red_off");
		down1 = setup("/tiles_interactive/block_red_on");		
	}
	public void update() { 		
		if (switchedOn) direction = "down";
		else direction = "up";
		detectSwitch();
	}
	
	public void detectSwitch() {
					
		for (int i = 0; i < gp.iTile[1].length; i++) {
			
			// FIND SWITCH IN iTILE LIST
			if (gp.iTile[gp.currentMap][i] != null && 
					gp.iTile[gp.currentMap][i].name != null &&
					gp.iTile[gp.currentMap][i].name.equals(IT_Switch.itName)) {
							
				// TURN ON IF SWITCH IS ON
				if (!gp.iTile[gp.currentMap][i].switchedOn) {
					collision = false;
					switchedOn = false;
				}
				// TURN OFF IF NO COLLISION
				else if (!gp.cChecker.checkPlayer(this)) {
					collision = true;
					switchedOn = true;
				}							
			}
		}	
	}
}