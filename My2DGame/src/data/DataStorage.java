package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStorage implements Serializable {
	
	// DAY STATE
	int dayState, dayCounter, bloodMoonCounter;
	
	// PROGRESS
	boolean enemy_room_1_1, bossDefeated_1;
	
	// PLAYER DATA
	int cMap, cArea, pWorldX, pWorldY;
	String name;
	int level, maxLife, life, strength, dexterity, attack, defense;
	int rupees, maxArrows, arrows, maxBombs, bombs;
	boolean canSwim;	
	String sword, shield, currentItem;
	
	// PLAYER COLLECTABLES
	ArrayList<String> colNames = new ArrayList<>();
	ArrayList<Integer> colAmounts = new ArrayList<>();
	
	// PLAYER ITEMS
	ArrayList<String> itemNames = new ArrayList<>();
	
	// NPCs
	String npcNames[][];
	int npcWorldX[][];
	int npcWorldY[][];
	int npcDialogueSet[][];
	boolean npcHasCutscene[][];
	boolean npcHasItem[][];
	Map<String, List<String>> npcInventory = new HashMap<>();
	
	// ENEMY
	boolean emyAlive[][];
	
	// MAP OBJECTS
	String mapObjectNames[][];
	int mapObjectWorldX[][];
	int mapObjectWorldY[][];
	String mapObjectLootNames[][];
	boolean mapObjectOpened[][];
}