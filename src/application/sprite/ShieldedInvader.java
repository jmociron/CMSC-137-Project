package application.sprite;

import javafx.scene.image.Image;

public class ShieldedInvader extends Invader{
	public final static Image ShieldedInvader_IMAGE = new Image("images/invader1.png", Invader.Invader_WIDTH,
            Invader.Invader_WIDTH, false, false);

	public ShieldedInvader (int x, int y) {
		super(x, y, 2);
		this.loadImage(ShieldedInvader_IMAGE);
	}
}
