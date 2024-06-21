package entity.collectable;

import entity.Entity;
import main.GamePanel;

public class COL_Key extends Entity {
	
	public static final String keyName = "Dungeon Key";
	GamePanel gp;

	public COL_Key(GamePanel gp) {		
		super(gp);	
		this.gp = gp;
		
		type = type_key;
		name = keyName;	
		
		down1 = setup("/collectables/COL_KEY");
	}	
}