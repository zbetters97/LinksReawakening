package application;
import tile.tile_interactive.*;

public class iTileGenerator {

	private GamePanel gp;
	
	protected iTileGenerator(GamePanel gp) {
		this.gp = gp;
	}
	
	public InteractiveTile getTile(String itemName) {
		
		InteractiveTile iTile = null;
		
		switch (itemName) {		
			case IT_Block_Blue.itName: iTile = new IT_Block_Blue(gp); break;
			case IT_Block_Red.itName: iTile = new IT_Block_Red(gp); break;
			case IT_DigSpot.itName: iTile = new IT_DigSpot(gp); break;
			case IT_Hole.itName: iTile = new IT_Hole(gp); break;
			case IT_Plate_Metal.itName: iTile = new IT_Plate_Metal(gp); break;
			case IT_Pot.itName: iTile = new IT_Pot(gp); break;
			case IT_Switch.itName: iTile = new IT_Switch(gp); break;
			case IT_Wall_01.itName: iTile = new IT_Wall_01(gp); break;
			case IT_Wall.itName: iTile = new IT_Wall(gp); break;
		}
		
		return iTile;		
	}	
}