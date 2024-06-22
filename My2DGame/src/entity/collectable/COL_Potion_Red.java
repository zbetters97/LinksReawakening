package entity.collectable;

import application.GamePanel;
import entity.Entity;

public class COL_Potion_Red extends Entity implements Cloneable {

	public static final String colName = "COL Red Potion";
	GamePanel gp;
	
	public COL_Potion_Red(GamePanel gp) {
		super(gp);		
		this.gp = gp;
		
		type = type_consumable;
		name = colName;
		description = "[Red Potion]\nHeals two hearts.";
		value = 4;
		price = 20;
		stackable = true;
		
		down1 = setup("/colellectables/COL_POTION_RED");
		setDialogue();
	}
	
	public void setDialogue() {
		dialogues[0][0] = "Ah... you feel regenerated a little bit!";
	}
	
	public boolean use(Entity user) {		
		user.life += value;
		startDialogue(this, 0);
		return true;
	}
	@Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}