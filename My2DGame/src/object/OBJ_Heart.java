package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Heart extends SuperObject {
	
	GamePanel gp;

	public OBJ_Heart(GamePanel gp) {
		
		name = "Heart";
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/heart_full.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_half.png"));
			image3 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_blank.png"));
			image = utility.scaleImage(image, gp.tileSize / 2, gp.tileSize / 2);
			image2 = utility.scaleImage(image2, gp.tileSize / 2, gp.tileSize / 2);
			image3 = utility.scaleImage(image3, gp.tileSize / 2, gp.tileSize / 2);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		// object can have unique solid area
		solidArea.x = 0;
		solidArea.y = 0;
	}	
}