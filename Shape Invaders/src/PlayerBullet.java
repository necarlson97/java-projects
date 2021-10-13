
public class PlayerBullet extends Bullet{

	public PlayerBullet(Player p) {
		super(-5, p);
		killsEnemies = true;
	}

}
