package object;

import entity.Entity;
import main.GamePanel;

public class COL_Key extends Entity {
	
	GamePanel gp;

	public COL_Key(GamePanel gp) {		
		super(gp);	
		this.gp = gp;
		
		type = type_consumable;
		name = "Key";
		description = "[" + name + "]\nThis can unlock a door.\nSingle-use only.";		
		stackable = true;
		
		down1 = setup("/objects/ITEM_KEY");
	}	
		
	public boolean use(Entity entity) {
		
		gp.gameState = gp.dialogueState;
		
		int objIndex = getDetected(entity, gp.obj, "Door");
		if (objIndex != -1) {
			playSE();
			gp.ui.currentDialogue = "*click*";
			gp.obj[gp.currentMap][objIndex] = null;
			return true;
		}
		else {
			gp.ui.currentDialogue = "It doesn't seem to be useful right now.";
			return false;
		}
	}
	public void playSE() {
		
	}
}