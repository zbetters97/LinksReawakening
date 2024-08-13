package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStorage implements Serializable {
	
	private static final long serialVersionUID = -5792031433632402979L;
	
	// FILE INFO
	String file_date;
	boolean gameCompleted;
	
	// DAY STATE
	int dayState, dayCounter, bloodMoonCounter;
	float filterAlpha;
	
	// PROGRESS
	boolean enemy_room_1_1, enemy_room_1_2, enemy_room_1_3, puzzle_1_1, bossDefeated_1_1, bossDefeated_1_2;
	
	// PLAYER DATA
	int cMap, cArea, pWorldX, pWorldY;
	String name, direction;
	int level, maxLife, life, strength, dexterity, attack, defense;
	int rupees, maxArrows, arrows, maxBombs, bombs;
	boolean canSwim;	
	String sword, shield;
	
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
	boolean npcDrawing[][];
	Map<String, List<String>> npcInventory = new HashMap<>();
	
	// ENEMIES
	int enemyWorldX[][];
	int enemyWorldY[][];
	int enemyLife[][];
	boolean enemyAlive[][];
	boolean enemyAsleep[][];
	
	// MAP OBJECTS
	String mapObjectNames[][];
	int mapObjectWorldX[][];
	int mapObjectWorldY[][];
	String mapObjectDirections[][];
	boolean mapObjectSwitchedOn[][];
	String mapObjectLootNames[][];
	boolean mapObjectOpened[][];
	
	// iTILES
	String iTileNames[][];
	int iTileWorldX[][];
	int iTileWorldY[][];
	String iTileDirections[][];
	boolean iTileSwitchedOn[][];
	String iTileLootNames[][];
	
	public String toString() {
		return "[" + name + "]  " + file_date;
	}
}