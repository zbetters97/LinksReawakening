package data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {
	
	// DAY STATE
	int dayState, dayCounter, bloodMoonCounter;
	
	// PLAYER DATA
	int pWorldX, pWorldY;
	String name;
	int level, maxLife, life, strength, dexterity, attack, defense;
	int rupees, maxArrows, arrows, maxBombs, bombs;
	boolean hasItem;	
	
	// PLAYER INVENTORY
	ArrayList<String> itemNames = new ArrayList<>();
	ArrayList<Integer> itemAmounts = new ArrayList<>();
	
	// PLAYER EQUIPMENT
	int currentWeaponSlot;
	int currentShieldSlot;
	
	// MAP OBJECTS
	String mapObjectNames[][];
	int mapObjectWorldX[][];
	int mapObjectWorldY[][];
	String mapObjectLootNames[][];
	boolean mapObjectOpened[][];
	
}