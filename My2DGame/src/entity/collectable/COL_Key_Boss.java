package entity.collectable;

import application.GamePanel;
import entity.Entity;

public class COL_Key_Boss extends Entity {
	
	public static final String keyName = "Boss Key";

	public COL_Key_Boss(GamePanel gp) {		
		super(gp);	
		
		type = type_boss_key;
		name = keyName;	
	}	
	
	public void getImage() {
		image = down1 = setup("/collectables/key_boss");
	}
}