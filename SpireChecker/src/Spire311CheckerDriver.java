 import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Spire311CheckerDriver {
	
	public static void main(String[] args) {
		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable() {
		    @Override
		    public void run() {
		    	try {
					Spire311Checker.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		}, 0, 30, TimeUnit.MINUTES);	
	}
}
