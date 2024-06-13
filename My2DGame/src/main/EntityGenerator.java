package main;
import entity.Entity;
import equipment.*;
import item.*;
import collectable.*;
import object.*;

public class EntityGenerator {

	GamePanel gp;
	
	public EntityGenerator(GamePanel gp) {
		this.gp = gp;
	}
	
	public Entity getObject(String itemName) {
		
		Entity obj = null;
		
		switch (itemName) {		
			case EQP_Sword_Old.itmName: obj = new EQP_Sword_Old(gp); break;
			case EQP_Shield_Old.itmName: obj = new EQP_Shield_Old(gp); break;
			
			case ITM_Axe.itmName: obj = new ITM_Axe(gp); break;
			case ITM_Bomb.itmName: obj = new ITM_Bomb(gp); break;		
			case ITM_Boomerang.itmName: obj = new ITM_Boomerang(gp); break;
			case ITM_Boots.itmName: obj = new ITM_Boots(gp); break;
			case ITM_Bow.itmName: obj = new ITM_Bow(gp); break;
			case ITM_Feather.itmName: obj = new ITM_Feather(gp); break;
			case ITM_Hookshot.itmName: obj = new ITM_Hookshot(gp); break;
			case ITM_Lantern.itmName: obj = new ITM_Lantern(gp); break;
			case ITM_Shovel.itmName: obj = new ITM_Shovel(gp); break;	
									
			case COL_Arrow.itmName: obj = new COL_Arrow(gp); break;
			case COL_Heart.itmName: obj = new COL_Heart(gp); break;
			case COL_Key.itmName: obj = new COL_Key(gp); break;
			case COL_Potion_Red.itmName: obj = new COL_Potion_Red(gp); break;
			case COL_Rupee_Blue.itmName: obj = new COL_Rupee_Blue(gp); break;
			case COL_Rupee_Green.itmName: obj = new COL_Rupee_Green(gp); break;
			case COL_Rupee_Red.itmName: obj = new COL_Rupee_Red(gp); break;
			
			case OBJ_Door.itmName: obj = new OBJ_Door(gp); break;
			case OBJ_Chest.itmName: obj = new OBJ_Chest(gp); break;
			case OBJ_Tent.itmName: obj = new OBJ_Tent(gp); break;
		}
		
		return obj;		
	}	
}