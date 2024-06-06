package miniprojtemplate;

import javafx.scene.image.Image;

public class Salt extends Powerup{
	Image image;
	private final static Image SALT_IMAGE = new Image("images/salt.png", POWERUP_WIDTH, POWERUP_WIDTH, false, false);

	Salt(int x, int y) {
		super(x, y);
		this.image = SALT_IMAGE;
		this.loadImage(SALT_IMAGE);

	}

	@Override
	public void checkCollision(Ship myShip) {
		if (this.collidesWith(myShip)){
			System.out.println(myShip.getName() + " has collected a salt!");
			this.vanish();

			myShip.setStrength(myShip.getStrength());
			
			
		}
	}

}
