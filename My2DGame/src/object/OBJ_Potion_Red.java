package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {

	GamePanel gp;
	int value = 4;
	
	public OBJ_Potion_Red(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		type = type_consumable;
		name = "Red Potion";
		description = "[" + name + "]\nHeals two hearts.";
		down1 = setup("objects/potion_red", gp.tileSize, gp.tileSize);
	}
	
	public void use(Entity entity) {
		
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "Ah... you feel regenerated a little bit!";
		
		entity.life += value;		
		if (gp.player.life > gp.player.maxLife) 
			gp.player.life = gp.player.maxLife;
	}
}