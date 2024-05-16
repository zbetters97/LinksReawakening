package main;

public class EventHandler {
	
	GamePanel gp;
	EventRect eventRect[][];
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	
	public EventHandler(GamePanel gp) {
		
		this.gp = gp;
		
		// EVENT RECTANGLE ON EVERY WORLD MAP TILE
		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
		
		int col = 0;
		int row = 0;		
		while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
			// DRAW HIT BOX ON EACH EVENT
			eventRect[col][row] = new EventRect();			
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].width = 2;
			eventRect[col][row].height = 2;
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
			
			col++;
			if (col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}
	
	public void checkEvent() {
		
		// CHECK IF PLAYER IS >1 TILE AWAY FROM PREVIOUS EVENT
		int xDistance = Math.abs(gp.player.worldX - previousEventX); // convert - to +
		int yDistance = Math.abs(gp.player.worldY - previousEventY); // convert - to +
		int distance = Math.max(xDistance, yDistance); // find greater of two
		
		if (distance > gp.tileSize) 
			canTouchEvent = true;
		
		// IF EVENT CAN HAPPEN AT X/Y FACING DIRECTION
		if (canTouchEvent) {
			if (hit(27, 16, "right")) damagePit(27, 16,gp.dialogueState);		
			if (hit(23, 12, "any")) healingPool(23, 12, gp.dialogueState);
		}
	}
	
	public boolean hit(int col, int row, String reqDirection) {
		
		boolean hit = false;
		
		// PLAYER HITBOX
		gp.player.solidArea.x += gp.player.worldX;		
		gp.player.solidArea.y += gp.player.worldY;
		
		// EVENT HITBOX
		eventRect[col][row].x += col * gp.tileSize;
		eventRect[col][row].y += row * gp.tileSize;
		
		// PLAYER INTERACTS WITH EVENT AND EVENT CAN HAPPEN
		if (gp.player.solidArea.intersects(eventRect[col][row]) && 
				!eventRect[col][row].eventDone) {
			
			// EVENT OCCURS ONLY ON DIRECTION
			if (gp.player.direction.equals(reqDirection) || 
					reqDirection.equals("any")) {
				hit = true;
				
				// RECORD PLAYER X/Y
				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;
			}
		}
		
		// RESET HITBOX
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
		
		return hit;		
	}
	
	public void damagePit(int col, int row, int gameState) {		
		gp.gameState = gameState;
		gp.ui.currentDialogue = "Ouch! You got stung by a bee!";
		gp.player.life--;
		canTouchEvent = false;
	}
	
	public void healingPool(int col, int row, int gameState) {
		if (gp.keyH.spacePressed) {
			gp.gameState = gameState;
			gp.ui.currentDialogue = "Ah... The water is pure and heals you.";
			gp.player.life++;		
		}
	}
}














