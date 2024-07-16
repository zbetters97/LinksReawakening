package application;
import entity.Entity;
import entity.collectable.*;
import entity.equipment.*;
import entity.item.*;
import entity.object.*;
import entity.object.object_interactive.OI_Block_Pushable;

public class EntityGenerator {

	private GamePanel gp;
	
	protected EntityGenerator(GamePanel gp) {
		this.gp = gp;
	}
	
	public Entity getObject(String itemName) {
		
		Entity obj = null;
		
		switch (itemName) {				
			case COL_Arrow.colName: obj = new COL_Arrow(gp); break;
			case COL_Bomb.colName: obj = new COL_Bomb(gp); break;
			case COL_Heart.colName: obj = new COL_Heart(gp); break;
			case COL_Key.keyName: obj = new COL_Key(gp); break;
			case COL_Potion_Red.colName: obj = new COL_Potion_Red(gp); break;
			case COL_Rupee_Blue.colName: obj = new COL_Rupee_Blue(gp); break;
			case COL_Rupee_Green.colName: obj = new COL_Rupee_Green(gp); break;
			case COL_Rupee_Red.colName: obj = new COL_Rupee_Red(gp); break;
		
			case EQP_Shield.eqpName: obj = new EQP_Shield(gp); break;	
			case EQP_Sword_Old.eqpName: obj = new EQP_Sword_Old(gp); break;
			case EQP_Sword_Master.eqpName: obj = new EQP_Sword_Master(gp); break;			
			
			case ITM_Bomb.itmName: obj = new ITM_Bomb(gp); break;		
			case ITM_Boomerang.itmName: obj = new ITM_Boomerang(gp); break;
			case ITM_Boots.itmName: obj = new ITM_Boots(gp); break;
			case ITM_Bow.itmName: obj = new ITM_Bow(gp); break;
			case ITM_Feather.itmName: obj = new ITM_Feather(gp); break;
			case ITM_Hookshot.itmName: obj = new ITM_Hookshot(gp); break;
			case ITM_Lantern.itmName: obj = new ITM_Lantern(gp); break;
			case ITM_Shovel.itmName: obj = new ITM_Shovel(gp); break;	
			
			case OBJ_BlueHeart.objName: obj = new OBJ_BlueHeart(gp); break;
			case OBJ_Block.objName: obj = new OBJ_Block(gp); break;
			case OBJ_Block_Locked.objName: obj = new OBJ_Block_Locked(gp); break;
			case OBJ_Chest.objName: obj = new OBJ_Chest(gp); break;
			case OBJ_Door_Boss.objName: obj = new OBJ_Door_Boss(gp); break;
			case OBJ_Door_Closed.objName: obj = new OBJ_Door_Closed(gp); break;			
			case OBJ_Door_Locked.objName: obj = new OBJ_Door_Locked(gp); break;
			case OBJ_Door_Oneway.objName: obj = new OBJ_Door_Oneway(gp); break;
			case OBJ_Tent.objName: obj = new OBJ_Tent(gp); break;
			
			case OI_Block_Pushable.obj_iName: obj = new OI_Block_Pushable(gp); break;		
		}
		
		return obj;		
	}	
}