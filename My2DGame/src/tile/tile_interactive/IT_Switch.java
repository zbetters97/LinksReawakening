package tile.tile_interactive;

import application.GamePanel;
import entity.Entity;

public class IT_Switch extends InteractiveTile {

	public static final String itName = "Switch";
	
	public IT_Switch(GamePanel gp) {
		super(gp);
		direction = "down";
		
		name = itName;
		life = 3;
		switchedOn = false;	
	}
	public IT_Switch(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		direction = "down";
		
		name = itName;
		life = 3;
		switchedOn = false;	
	}
	
	public void getImage() {
		up1 = setup("/tiles_interactive/switch_on");
		down1 = setup("/tiles_interactive/switch_off");		
	}
	
	public boolean correctItem(Entity entity) {		
		boolean correctItem = true;		
		return correctItem;
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