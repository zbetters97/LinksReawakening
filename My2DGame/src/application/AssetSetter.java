package application;

import data.Progress;
import entity.collectable.*;
import entity.enemy.*;
import entity.equipment.*;
import entity.item.*;
import entity.npc.*;
import entity.object.*;
import entity.object.object_interactive.*;
import tile.tile_interactive.*;

public class AssetSetter {
	
	private GamePanel gp;
	
	protected AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
		
	protected void setObject() {		
		
		int mapNum = 0;
		int i = 0;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new ITM_Shovel(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 38;
		gp.obj[mapNum][i].worldY = gp.tileSize * 41;	
		i++;		
		gp.obj[mapNum][i] = new ITM_Bomb(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 15;
		gp.obj[mapNum][i].worldY = gp.tileSize * 21;
		i++;
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new ITM_Boots(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 17;
		gp.obj[mapNum][i].worldY = gp.tileSize * 15;
		i++;	
		gp.obj[mapNum][i] = new OBJ_Tent(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 40;
		gp.obj[mapNum][i].worldY = gp.tileSize * 11;
		i++;
		gp.obj[mapNum][i] = new OBJ_Block(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 12;
		gp.obj[mapNum][i].worldY = gp.tileSize * 21;
		i++;
		gp.obj[mapNum][i] = new OBJ_Block(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 27;
		gp.obj[mapNum][i].worldY = gp.tileSize * 21;
		i++;
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new EQP_Flippers(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 38;
		gp.obj[mapNum][i].worldY = gp.tileSize * 7;		
		i++;	
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new ITM_Feather(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 12;
		gp.obj[mapNum][i].worldY = gp.tileSize * 32;
		i++;		
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new ITM_Bow(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 8;
		gp.obj[mapNum][i].worldY = gp.tileSize * 9;
		i++;	
		
		mapNum = 2;
		i = 0;
				
		gp.obj[mapNum][i] = new OBJ_Door_Locked(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 18;
		gp.obj[mapNum][i].worldY = gp.tileSize * 34;	
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new COL_Key(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 41;
		gp.obj[mapNum][i].worldY = gp.tileSize * 39;
		i++;	
		
		gp.obj[mapNum][i] = new OBJ_Door_Closed(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 18;
		gp.obj[mapNum][i].worldY = gp.tileSize * 23;	
		i++;	
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new COL_Key_Boss(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 27;
		gp.obj[mapNum][i].worldY = gp.tileSize * 15;	
		i++;	
		
		gp.obj[mapNum][i] = new OBJ_Door_Boss(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 9;
		gp.obj[mapNum][i].worldY = gp.tileSize * 9;	
		i++;	
		
		mapNum = 3;
		i = 0;
		
		gp.obj[mapNum][i] = new OBJ_Door_Closed(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 25;
		gp.obj[mapNum][i].worldY = gp.tileSize * 15;	
		i++;			
		gp.obj[mapNum][i] = new OBJ_BlueHeart(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 25;
		gp.obj[mapNum][i].worldY = gp.tileSize * 8;	
		i++;	
		
		mapNum = 4;
		i = 0;
		
		gp.obj[mapNum][i] = new OBJ_Door_Locked(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 40;
		gp.obj[mapNum][i].worldY = gp.tileSize * 85;	
		i++;		
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new COL_Key(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 11;
		gp.obj[mapNum][i].worldY = gp.tileSize * 87;	
		i++;	
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new COL_Key(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 17;
		gp.obj[mapNum][i].worldY = gp.tileSize * 62;	
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Door_Closed(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 25;
		gp.obj[mapNum][i].worldY = gp.tileSize * 71;
		gp.obj[mapNum][i].direction = "right";
		i++;					
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new ITM_Bomb(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 20;
		gp.obj[mapNum][i].worldY = gp.tileSize * 71;	
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Door_Closed(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 26;
		gp.obj[mapNum][i].worldY = gp.tileSize * 54;	
		gp.obj[mapNum][i].direction = "right";
		gp.obj[mapNum][i].temp = true;
		i++;		
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new COL_Key(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 21;
		gp.obj[mapNum][i].worldY = gp.tileSize * 72;	
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Block_Locked(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 51;
		gp.obj[mapNum][i].worldY = gp.tileSize * 67;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Block_Locked(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 42;
		gp.obj[mapNum][i].worldY = gp.tileSize * 77;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Door_Locked(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 59;
		gp.obj[mapNum][i].worldY = gp.tileSize * 67;	
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new COL_Key(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 16;
		gp.obj[mapNum][i].worldY = gp.tileSize * 79;	
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new COL_Key_Boss(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 58;
		gp.obj[mapNum][i].worldY = gp.tileSize * 59;	
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Door_Boss(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 70;
		gp.obj[mapNum][i].worldY = gp.tileSize * 67;	
		i++;	
	}	
	protected void setNPC() {
		
		int mapNum = 0;
		int i = 0;
						
		gp.npc[mapNum][i] = new NPC_Traveler_1(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 20;
		gp.npc[mapNum][i].worldY = gp.tileSize * 21;
		i++;		
		gp.npc[mapNum][i] = new NPC_OldMan(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 30;
		gp.npc[mapNum][i].worldY = gp.tileSize * 30;
		i++;		
		gp.npc[mapNum][i] = new NPC_Dialogue_Exchange(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 20;
		gp.npc[mapNum][i].worldY = gp.tileSize * 10;
		gp.npc[mapNum][i].direction = "up";
		i++;
		
		mapNum = 1;
		i = 0;
		
		gp.npc[mapNum][i] = new NPC_Merchant(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize * 12;
		gp.npc[mapNum][i].worldY = gp.tileSize * 7;
		i++;
	}	
	public void setEnemy() {		
		
		int mapNum = 0;
		int i = 0;
		
		gp.enemy[mapNum][i] = new EMY_Bat(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 23;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 40;
		i++;
		gp.enemy[mapNum][i] = new EMY_Bat(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 21;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 37;
		i++;
		gp.enemy[mapNum][i] = new EMY_Slime_Green(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 38;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 40;
		i++;
		gp.enemy[mapNum][i] = new EMY_Slime_Green(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 37;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 41;
		i++;
		gp.enemy[mapNum][i] = new EMY_Octorok(gp, "up");
		gp.enemy[mapNum][i].worldX = gp.tileSize * 12;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 46;
		i++;
		gp.enemy[mapNum][i] = new EMY_Goblin_Combat(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 36;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 27;
		i++;
		gp.enemy[mapNum][i] = new EMY_Octorok(gp,"left");
		gp.enemy[mapNum][i].worldX = gp.tileSize * 43;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 21;
		i++;	
		gp.enemy[mapNum][i] = new EMY_Octorok(gp, "left");
		gp.enemy[mapNum][i].worldX = gp.tileSize * 43;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 25;
		i++;	
		gp.enemy[mapNum][i] = new EMY_Zora(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 38;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 9;
		i++;
		gp.enemy[mapNum][i] = new EMY_Goblin_Combat(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 12;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 29;
		i++;
		gp.enemy[mapNum][i] = new EMY_Slime_Red(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 12;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 31;
		i++;
		
		mapNum = 2;
		i = 0;
	
		gp.enemy[mapNum][i] = new EMY_Buzzblob(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 18;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 35;
		i++;
		gp.enemy[mapNum][i] = new EMY_Bat(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 17;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 25;
		i++;
		gp.enemy[mapNum][i] = new EMY_Wizzrobe(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 17;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 25;
		i++;
		gp.enemy[mapNum][i] = new EMY_Wizzrobe(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 28;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 19;
		i++;					
		gp.enemy[mapNum][i] = new EMY_Goblin_Combat(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 18;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 10;
		i++;
		gp.enemy[mapNum][i] = new EMY_Wizzrobe(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 20;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 11;
		i++;
		gp.enemy[mapNum][i] = new EMY_Buzzblob(gp);
		gp.enemy[mapNum][i].worldX = gp.tileSize * 19;
		gp.enemy[mapNum][i].worldY = gp.tileSize * 9;
		i++;
		
		mapNum = 3;
		i = 0;
		
		if (!Progress.bossDefeated_1) {
			gp.enemy[mapNum][i] = new BOS_Skeleton(gp);
			gp.enemy[mapNum][i].worldX = gp.tileSize * 23;
			gp.enemy[mapNum][i].worldY = gp.tileSize * 16;
		}
		i++;
	}
	protected void setInteractiveObjects() {
		
		int mapNum = 2;
		int i = 0;
		
		gp.obj_i[mapNum][i] = new OI_Block_Pushable(gp);
		gp.obj_i[mapNum][i].worldX = gp.tileSize * 35;
		gp.obj_i[mapNum][i].worldY = gp.tileSize * 26;
		i++;
		gp.obj_i[mapNum][i] = new OI_Block_Pushable(gp);
		gp.obj_i[mapNum][i].worldX = gp.tileSize * 11;
		gp.obj_i[mapNum][i].worldY = gp.tileSize * 20;
		i++;
		gp.obj_i[mapNum][i] = new OI_Block_Pushable(gp);
		gp.obj_i[mapNum][i].worldX = gp.tileSize * 27;
		gp.obj_i[mapNum][i].worldY = gp.tileSize * 11;
		i++;
		
		mapNum = 4;
		i = 0;
		
		gp.obj_i[mapNum][i] = new OI_Block_Pushable(gp);
		gp.obj_i[mapNum][i].worldX = gp.tileSize * 37;
		gp.obj_i[mapNum][i].worldY = gp.tileSize * 69;
		i++;
		
		gp.obj_i[mapNum][i] = new OI_Block_Pushable(gp);
		gp.obj_i[mapNum][i].worldX = gp.tileSize * 41;
		gp.obj_i[mapNum][i].worldY = gp.tileSize * 61;
		i++;	
		
		gp.obj_i[mapNum][i] = new OI_Block_Pushable(gp);
		gp.obj_i[mapNum][i].worldX = gp.tileSize * 28;
		gp.obj_i[mapNum][i].worldY = gp.tileSize * 54;
		i++;
		
		gp.obj_i[mapNum][i] = new OI_Block_Pushable(gp);
		gp.obj_i[mapNum][i].worldX = gp.tileSize * 52;
		gp.obj_i[mapNum][i].worldY = gp.tileSize * 78;
		i++;	
	}
	protected void setInteractiveTiles() {
		
		int mapNum = 0;
		int i = 0;
		
		gp.iTile[mapNum][i] = new IT_DigSpot(gp, mapNum, 30, 36, new COL_Rupee_Red(gp)); i++;
		gp.iTile[mapNum][i] = new IT_DigSpot(gp, mapNum, 27, 16, new COL_Rupee_Red(gp)); i++;
		gp.iTile[mapNum][i] = new IT_DigSpot(gp, mapNum, 8, 24, new EQP_Sword_Master(gp)); i++;
		
		gp.iTile[mapNum][i] = new IT_Wall(gp, 20, 16); i++;
		
		gp.iTile[mapNum][i] = new IT_Wall(gp, 35, 30); i++;
		gp.iTile[mapNum][i] = new IT_Wall(gp, 36, 30); i++;
		gp.iTile[mapNum][i] = new IT_Wall(gp, 37, 30); i++;
		gp.iTile[mapNum][i] = new IT_Wall(gp, 38, 30); i++;
		
		mapNum = 1;
		i = 0;
		
		gp.iTile[mapNum][i] = new IT_Pot(gp, 10, 12);
		gp.iTile[mapNum][i].setLoot(new COL_Rupee_Blue(gp), new COL_Heart(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 14, 12);
		gp.iTile[mapNum][i].setLoot(new COL_Rupee_Green(gp), new COL_Potion_Red(gp)); i++;
		
		mapNum = 2;
		i = 0;
		
		gp.iTile[mapNum][i] = new IT_Pot(gp, 26, 38);
		gp.iTile[mapNum][i].setLoot(new COL_Heart(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 32, 25);
		gp.iTile[mapNum][i].setLoot(new COL_Heart(gp)); i++;
		
		gp.iTile[mapNum][i] = new IT_Wall(gp, 10, 22); i++;
		gp.iTile[mapNum][i] = new IT_Wall(gp, 30, 18); i++;
		gp.iTile[mapNum][i] = new IT_Wall(gp, 30, 19); i++;
		
		gp.iTile[mapNum][i] = new IT_Switch(gp, 26, 34); i++;
		gp.iTile[mapNum][i] = new IT_Block_Blue(gp, 38, 28); i++;
		gp.iTile[mapNum][i] = new IT_Block_Blue(gp, 39, 28); i++;
		gp.iTile[mapNum][i] = new IT_Block_Blue(gp, 40, 28); i++;
		gp.iTile[mapNum][i] = new IT_Block_Red(gp, 38, 18); i++;
		
		gp.iTile[mapNum][i] = new IT_Plate_Metal(gp, 20, 22); i++;
		gp.iTile[mapNum][i] = new IT_Plate_Metal(gp, 9, 18); i++;
		gp.iTile[mapNum][i] = new IT_Plate_Metal(gp, 39, 30); i++;
		
		mapNum = 4;
		i = 0;
		
		gp.iTile[mapNum][i] = new IT_Switch(gp, 25, 88); i++;	
		gp.iTile[mapNum][i] = new IT_Block_Red(gp, 34, 89); i++;
		gp.iTile[mapNum][i] = new IT_Block_Blue(gp, 20, 89); i++;
		
		gp.iTile[mapNum][i] = new IT_Plate_Metal(gp, 38, 70); i++;
		gp.iTile[mapNum][i] = new IT_Plate_Metal(gp, 35, 62); i++;
		
		gp.iTile[mapNum][i] = new IT_Wall_01(gp, 29, 58, "up"); i++;
		gp.iTile[mapNum][i] = new IT_Wall_01(gp, 29, 59, "down"); i++;
		
		gp.iTile[mapNum][i] = new IT_Block_Blue(gp, 38, 77); i++;
		
		gp.iTile[mapNum][i] = new IT_Switch(gp, 40, 78); i++;	
		gp.iTile[mapNum][i] = new IT_Block_Red(gp, 44, 80); i++;
		gp.iTile[mapNum][i] = new IT_Block_Blue(gp, 49, 80); i++;
				
		gp.iTile[mapNum][i] = new IT_Block_Blue(gp, 29, 53); i++;
		
		gp.iTile[mapNum][i] = new IT_Block_Red(gp, 64, 71); i++;
		
		gp.iTile[mapNum][i] = new IT_Wall_01(gp, 23, 80, "left"); i++;
		gp.iTile[mapNum][i] = new IT_Wall_01(gp, 24, 80, "right"); i++;
		
		gp.iTile[mapNum][i] = new IT_Block_Blue(gp, 19, 79); i++;
		gp.iTile[mapNum][i] = new IT_Block_Blue(gp, 19, 80); i++;
		gp.iTile[mapNum][i] = new IT_Block_Blue(gp, 19, 81); i++;
	}
}