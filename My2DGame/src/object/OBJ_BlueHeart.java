package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueHeart extends Entity {
	
	public static final String objName = "Blue Heart";
	GamePanel gp;
	
	public OBJ_BlueHeart(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_pickupOnly;
		name = objName;
		image1 = setup("/objects/blueheart");
		down1 = image1;
		
		setDialogue();
	}	
	
	public void setDialogue() {
		dialogues[0][0] = "You found the treasure!";
		dialogues[0][1] = "Is this what everyone was fighting over?...";
	}
	
	public boolean use(Entity entity) {
		gp.csManager.scene = gp.csManager.ending;
		gp.gameState = gp.cutsceneState;
				
		return true;
	}
}