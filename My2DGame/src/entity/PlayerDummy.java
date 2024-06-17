package entity;

import main.GamePanel;

public class PlayerDummy extends Entity {

	public static final String npcName = "Dummy";
	
	public PlayerDummy(GamePanel gp) {
		super(gp);
		
		name = npcName;		
		getImage();
	}
	
	public void getImage() {			
		up1 = setup("/player/boy_up_1"); 
		up2 = setup("/player/boy_up_2"); 
		down1 = setup("/player/boy_down_1"); 
		down2 = setup("/player/boy_down_2"); 
		left1 = setup("/player/boy_left_1"); 
		left2 = setup("/player/boy_left_2"); 
		right1 = setup("/player/boy_right_1"); 
		right2 = setup("/player/boy_right_2"); 
	}	
}