package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bow extends Entity {

	GamePanel gp;
	
	public OBJ_Bow(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_item;
		name = "Hylian Bow";
		description = "[" + name + "]\nPress F to fire an arrow!";
		down1 = setup("/objects/ITEM_BOW");
	}
}