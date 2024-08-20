package entity.object;

import application.GamePanel;
import entity.Entity;

public class OBJ_BlueHeart extends Entity {
	
	public static final String objName = "Blue Heart";
	
	public OBJ_BlueHeart(GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.worldX = worldX *= gp.tileSize;
		this.worldY = worldY *= gp.tileSize;
		
		type = type_pickupOnly;
		name = objName;
		
		setDialogue();
	}	
	
	public void getImage() {
		image = down1 = setup("/objects/OBJ_BLUEHEART");
	}
	
	public void setDialogue() {
		dialogues[0][0] = "You found the Blue Heart!";
		dialogues[0][1] = "It shines and sparkles as you move it\naround!";
		dialogues[0][2] = "...";
		dialogues[0][3] = "...";
		dialogues[0][4] = "...?";
		dialogues[0][5] = "Is this what everyone has been\nsearching for?...";
		dialogues[0][6] = "...what they've been fighting over?";
		dialogues[0][7] = "...";
	}
	
	public boolean use(Entity entity) {
		gp.csManager.scene = gp.csManager.ending;
		gp.gameState = gp.cutsceneState;
				
		return true;
	}
}