package miniprojtemplate;

import javafx.scene.image.Image;

public class Bible extends Powerup{
	Image image;
	private final static Image BIBLE_IMAGE = new Image("images/bible.png", POWERUP_WIDTH, POWERUP_WIDTH, false, false);

	Bible (int x, int y) {
		super(x, y);
		this.image = BIBLE_IMAGE;
		this.loadImage(BIBLE_IMAGE);
	}
	@Override
	public void checkCollision(Ship myShip) {
		if (this.collidesWith(myShip)){
			System.out.println(myShip.getName() + " has collected the Bible!");
			myShip.loadImage(Ship.UPGRADED_SHIP_IMAGE);
			myShip.setHittability(false);
			this.vanish();

		}
	}
}
