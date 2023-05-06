package application.sprite;

import javafx.scene.image.Image;

public class UnshieldedInvader extends Invader {
	public final static Image UnshieldedInvader_IMAGE = new Image("images/invader2.png", Invader.Invader_WIDTH,
            Invader.Invader_WIDTH, false, false);

	public UnshieldedInvader (int x, int y) {
		super(x, y, 1);
		this.loadImage(UnshieldedInvader_IMAGE);
	}
}
