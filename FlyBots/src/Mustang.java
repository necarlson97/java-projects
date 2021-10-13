import java.awt.image.BufferedImage;

public class Mustang extends Plane {

	Mustang() {
		super(9.83f, 4.08f, 3465, 1590, 700);
		sprites[0] = SpriteLoader.loadSprite("img/mustang", "Plane Full");
		sprites[1] = SpriteLoader.loadSprite("img/mustang", "Plane");
	}

}
