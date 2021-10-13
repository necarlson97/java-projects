import java.util.Random;

public class HitEffects {
	static Random rand = new Random();
	
	static void applyEffect(int hit, Player p) {
		if(p == null) return;
		int r = rand.nextInt(5);
		switch (r) {
		case 1: p.bruses++;
		case 2: p.cuts++;
		}
		if(p.bruses >= 5) p.kill();
		if(p.cuts >= 5) p.kill();
	}

}
