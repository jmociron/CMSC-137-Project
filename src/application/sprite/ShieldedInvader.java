package application.sprite;

import javafx.scene.image.Image;

public class ShieldedInvader extends Invader{
	public final static Image ShieldedInvader_IMAGE = new Image("images/invader1.png", Invader.Invader_WIDTH,
            Invader.Invader_WIDTH, false, false);

	public final static int SI_DAMAGE = 5;
	public final static int SI_LIFE = 2;

	public ShieldedInvader (int x, int y) {
		super(x, y, SI_LIFE, SI_DAMAGE);
		this.loadImage(ShieldedInvader_IMAGE);
	}
}
