package tile.tile_interactive;

import java.awt.Rectangle;

import application.GamePanel;

public class IT_Switch extends InteractiveTile {

	public static final String itName = "Switch";
	GamePanel gp;
	
	public IT_Switch(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = itName;
		life = 3;
		switchedOn = false;
		bombable = true;		
		direction = "down";
		
		hitbox = new Rectangle(0,0,48,48);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		up1 = setup("/tiles_interactive/switch_on");
		down1 = setup("/tiles_interactive/switch_off");		
	}
	public IT_Switch(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		name = itName;
		life = 3;
		switchedOn = false;
		bombable = true;		
		direction = "down";
		
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		
		hitbox = new Rectangle(0,0,48,48);
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		up1 = setup("/tiles_interactive/switch_on");
		down1 = setup("/tiles_interactive/switch_off");		
	}
		
	public void interact() {
		playSE();
		
		for (int i = 0; i < gp.iTile[1].length; i++) {
			
			// FIND SWITCH IN iTILE LIST
			if (gp.iTile[gp.currentMap][i] != null && 
					gp.iTile[gp.currentMap][i].name != null &&
					gp.iTile[gp.currentMap][i].name.equals(name)) {
				gp.iTile[gp.currentMap][i].switchedOn = !gp.iTile[gp.currentMap][i].switchedOn;
				if (gp.iTile[gp.currentMap][i].switchedOn) gp.iTile[gp.currentMap][i].direction = "up";
				else gp.iTile[gp.currentMap][i].direction = "down";
			}
		}
	}
		
	public void playSE() {
		gp.playSE(6, 0);
	}
}