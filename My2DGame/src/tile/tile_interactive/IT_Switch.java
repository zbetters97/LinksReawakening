package tile.tile_interactive;

import java.awt.Rectangle;

import application.GamePanel;

public class IT_Switch extends InteractiveTile {

	public static final String itName = "Switch";
	GamePanel gp;
	
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
		switchedOn = !switchedOn;
		if (switchedOn) direction = "up";
		else direction = "down";
	}
		
	public void playSE() {
		gp.playSE(1, 16);
	}
}