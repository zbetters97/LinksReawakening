package collectable;

import entity.Entity;
import main.GamePanel;

public class COL_Key extends Entity {
	
	public static final String itmName = "Collectable Key";
	GamePanel gp;

	public COL_Key(GamePanel gp) {		
		super(gp);	
		this.gp = gp;
		
		type = type_consumable;
		name = itmName;
		description = "[" + name + "]\nThis can unlock a door.\nSingle-use only.";		
		stackable = true;
		
		down1 = setup("/objects/ITEM_KEY");
		setDialogue();
	}	
	
	public void setDialogue() {
		dialogues[0][0] = "It doesn't seem to be useful right now.";
		dialogues[1][0] = "*click*";
	}
		
	public boolean use(Entity entity) {
		
		int objIndex = getDetected(entity, gp.obj, "Door");
		if (objIndex != -1) {
			playSE();
			startDialogue(this, 1);
			gp.obj[gp.currentMap][objIndex] = null;
			return true;
		}
		else {
			startDialogue(this, 0);
			return false;
		}
	}
	
	public void playSE() {
		
	}
}