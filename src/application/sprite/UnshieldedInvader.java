package application.sprite;

import javafx.scene.image.Image;

public class UnshieldedInvader extends Invader {
	public final static Image UnshieldedInvader_IMAGE = new Image("images/invader2.png", Invader.Invader_WIDTH,
            Invader.Invader_WIDTH, false, false);

	public final static int UI_DAMAGE = 5;
	public final static int UI_LIFE = 1;

	public UnshieldedInvader (int x, int y) {
		super(x, y, UI_LIFE, UI_DAMAGE);
		this.loadImage(UnshieldedInvader_IMAGE);
	}
}
