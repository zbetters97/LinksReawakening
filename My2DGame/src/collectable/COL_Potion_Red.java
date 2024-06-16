package collectable;

import entity.Entity;
import main.GamePanel;

public class COL_Potion_Red extends Entity implements Cloneable {

	public static final String itmName = "Collectable Red Potion";
	GamePanel gp;
	
	public COL_Potion_Red(GamePanel gp) {
		super(gp);		
		this.gp = gp;
		
		type = type_consumable;
		name = itmName;
		description = "[" + name + "]\nHeals two hearts.";
		value = 4;
		price = 20;
		stackable = true;
		
		down1 = setup("/objects/potion_red");
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