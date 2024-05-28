package tile_interactive;

import java.awt.Graphics2D;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {

	GamePanel gp;
	public boolean destructible = false;
	
	public InteractiveTile(GamePanel gp, int col, int row) {
		super(gp);
		this.gp = gp;
	}
	
	public boolean isCorrectItem(Entity entity) {		
		boolean isCorrectItem = false;		
		return isCorrectItem;
	}
	
	public void playSE() {
		
	}
	
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = null;
		return tile;
	}
	
	public void update() {
		
		// SHIELD AFTER HIT
		if (invincible) {
			invincibleCounter++;
			
			// 1 SECOND REFRESH TIME 
			if (invincibleCounter > 40) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		// convert world map coordinates to screen coordinates
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
					
		// only draw tiles within player boundary
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {				
			
			g2.drawImage(down1, screenX, screenY, null);
		}
	}
}