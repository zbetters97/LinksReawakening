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
		
		gp.enemy[mapNum][i] = new EMY_Slime_Green(gp, 28, 90); i++;
		gp.enemy[mapNum][i] = new EMY_Slime_Green(gp, 10, 89); i++;
		gp.enemy[mapNum][i] = new EMY_Slime_Green(gp, 12, 89); i++;
		gp.enemy[mapNum][i] = new EMY_Slime_Green(gp, 37, 71); i++;
		gp.enemy[mapNum][i] = new EMY_Slime_Green(gp, 47, 70); i++;
		gp.enemy[mapNum][i] = new EMY_Slime_Green(gp, 40, 57); i++;
		
		gp.enemy[mapNum][i] = new EMY_Slime_Red(gp, 48, 73); i++;	
		gp.enemy[mapNum][i] = new EMY_Slime_Red(gp, 37, 60); i++;			
		gp.enemy[mapNum][i] = new EMY_Slime_Red(gp, 43, 61); i++;
		gp.enemy[mapNum][i] = new EMY_Slime_Red(gp, 33, 54); i++;		
		gp.enemy[mapNum][i] = new EMY_Slime_Red(gp, 28, 32); i++;
		
		gp.enemy[mapNum][i] = new EMY_Goblin_Combat(gp, 19, 55); i++;
		gp.enemy[mapNum][i] = new EMY_Goblin_Combat(gp, 29, 68); i++;
		
		gp.enemy[mapNum][i] = new EMY_Wizzrobe(gp, 50, 63); i++;		
		gp.enemy[mapNum][i] = new EMY_Wizzrobe(gp, 38, 52); i++;
		gp.enemy[mapNum][i] = new EMY_Wizzrobe(gp, 62, 70); i++;
		
		gp.enemy[mapNum][i] = new EMY_Buzzblob(gp, 22, 55); i++;		
		
		if (!Progress.bossDefeated_1_1) {
			gp.enemy[mapNum][i] = new BOS_Gohma(gp, 64, 83);	
		}		
		
		mapNum = 3;
		i = 0;
		
		if (!Progress.bossDefeated_1_2) {
			gp.enemy[mapNum][i] = new BOS_Skeleton(gp);
			gp.enemy[mapNum][i].worldX = gp.tileSize * 23;
			gp.enemy[mapNum][i].worldY = gp.tileSize * 16;
		}
		i++;		
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
		gp.obj[mapNum][i].worldX = gp.tileSize * 45;
		gp.obj[mapNum][i].worldY = gp.tileSize * 89;
		gp.obj[mapNum][i].direction = "left";
		i++;			
		
		gp.obj[mapNum][i] = new OBJ_Door_Closed(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 26;
		gp.obj[mapNum][i].worldY = gp.tileSize * 54;	
		gp.obj[mapNum][i].direction = "right";
		i++;		
		
		gp.obj[mapNum][i] = new OBJ_Block_Locked(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 51;
		gp.obj[mapNum][i].worldY = gp.tileSize * 68;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Door_Locked(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 59;
		gp.obj[mapNum][i].worldY = gp.tileSize * 67;	
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new ITM_Bomb(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 55;
		gp.obj[mapNum][i].worldY = gp.tileSize * 83;	
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new COL_Key(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 21;
		gp.obj[mapNum][i].worldY = gp.tileSize * 53;	
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp, new COL_Key_Boss(gp));
		gp.obj[mapNum][i].worldX = gp.tileSize * 58;
		gp.obj[mapNum][i].worldY = gp.tileSize * 59;	
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Door_Boss(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize * 70;
		gp.obj[mapNum][i].worldY = gp.tileSize * 67;	
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
	}	
	protected void setInteractiveObjects() {
		
		int mapNum = 2;
		int i = 0;
				
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
		
		gp.iTile[mapNum][i] = new IT_Pot(gp, 36, 86, new COL_Heart(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 44, 86, new COL_Heart(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 9, 92, new COL_Heart(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 8, 91, new COL_Heart(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 37, 77, new COL_Heart(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 43, 77, new COL_Heart(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 57, 88, new COL_Heart(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 57, 90, new COL_Heart(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 33, 70, new COL_Heart(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 34, 70, new COL_Heart(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 16, 66, new COL_Heart(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 18, 57, new COL_Heart(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 24, 57, new COL_Heart(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 55, 80, new COL_Bomb(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 55, 68, new COL_Bomb(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 63, 68, new COL_Bomb(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 70, 70, new COL_Bomb(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 70, 72, new COL_Bomb(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 69, 71, new COL_Heart(gp)); i++;
		gp.iTile[mapNum][i] = new IT_Pot(gp, 71, 71, new COL_Heart(gp)); i++;
		
		gp.iTile[mapNum][i] = new IT_Switch(gp, 27, 89); i++;	
		gp.iTile[mapNum][i] = new IT_Block_Red(gp, 34, 89); i++;
		gp.iTile[mapNum][i] = new IT_Block_Blue(gp, 20, 89); i++;
		
		gp.iTile[mapNum][i] = new IT_Wall_01(gp, 51, 75, "up"); i++;
		gp.iTile[mapNum][i] = new IT_Wall_01(gp, 51, 76, "down"); i++;
		
		gp.iTile[mapNum][i] = new IT_Wall_01(gp, 53, 86, "up"); i++;
		gp.iTile[mapNum][i] = new IT_Wall_01(gp, 53, 87, "down"); i++;
		
		gp.iTile[mapNum][i] = new IT_Plate_Metal(gp, 38, 70); i++;
		gp.iTile[mapNum][i] = new IT_Plate_Metal(gp, 35, 62); i++;
		
		gp.iTile[mapNum][i] = new IT_Wall_01(gp, 29, 58, "up"); i++;
		gp.iTile[mapNum][i] = new IT_Wall_01(gp, 29, 59, "down"); i++;
		
		gp.iTile[mapNum][i] = new IT_Block_Red(gp, 44, 80); i++;		
				
		gp.iTile[mapNum][i] = new IT_Block_Blue(gp, 29, 53); i++;
		
		gp.iTile[mapNum][i] = new IT_Switch(gp, 50, 60); i++;	
		gp.iTile[mapNum][i] = new IT_Block_Blue(gp, 49, 62); i++;
		gp.iTile[mapNum][i] = new IT_Block_Blue(gp, 50, 62); i++;
		gp.iTile[mapNum][i] = new IT_Block_Blue(gp, 51, 62); i++;
		
		gp.iTile[mapNum][i] = new IT_Block_Red(gp, 64, 71); i++;
		
		gp.iTile[mapNum][i] = new IT_Block_Blue(gp, 49, 80); i++;
	}
}