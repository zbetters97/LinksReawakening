package object;

import entity.Entity;
import main.GamePanel;

public class COL_Potion_Red extends Entity {

	GamePanel gp;
	
	public COL_Potion_Red(GamePanel gp) {
		super(gp);		
		this.gp = gp;
		
		type = type_consumable;
		name = "Red Potion";
		description = "[" + name + "]\nHeals two hearts.";
		value = 4;
		price = 20;
		stackable = true;
		
		down1 = setup("/objects/potion_red");
	}
	
	public boolean use(Entity user) {		
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "Ah... you feel regenerated a little bit!";		
		user.life += value;
		return true;
	}
}