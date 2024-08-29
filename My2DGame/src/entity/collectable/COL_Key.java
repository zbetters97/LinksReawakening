package entity.collectable;

import application.GamePanel;
import entity.Entity;

public class COL_Key extends Entity {
	
	public static final String keyName = "Dungeon Key";

	public COL_Key(GamePanel gp) {		
		super(gp);	
		
		type = type_key;
		name = keyName;	
	}	
	
	public void getImage() {
		image = down1 = setup("/collectables/key");
	}
}