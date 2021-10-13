import java.util.LinkedList;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TweetLoader implements Runnable{
	
	private static final String CONSUMER_KEY = "";
	private static final String CONSUMER_SECRET = "";
	private static final String ACCESS_KEY = "";
	private static final String ACCESS_SECRET = "";
	
	Twitter twitter;
	User user;
	List<Status> statuses;
	
	final int BATCHSIZE = 10;
	int loadedFrom = 0;
	int loadedTo = 0;
	int batch = 0;
	
	LinkedList<TweetEnemy> nextBatch = new LinkedList<TweetEnemy>();
	
	Thread thread;
	boolean running = true;
	
	volatile String requestName = null;
	
	public TweetLoader(){
		setupConfiguration();
		
		thread = new Thread(this);
		thread.start();
	}
	
	private void setupConfiguration() {
    	ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
	        .setOAuthConsumerKey(CONSUMER_KEY)
	        .setOAuthConsumerSecret(CONSUMER_SECRET)
	        .setOAuthAccessToken(ACCESS_KEY)
	        .setOAuthAccessTokenSecret(ACCESS_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}
	
	@Override
	public void run() {
		
		while (running) {
			//System.out.println(requestName);
			if(requestName != null) {
				nextBatch = getTimeline(requestName);
				requestName = null;
			}
		}
	}
	
	public LinkedList<TweetEnemy>  requestEnemies(String request){
		if(nextBatch.isEmpty()) requestName = request;
		LinkedList<TweetEnemy> ret = nextBatch;
		nextBatch = new LinkedList<TweetEnemy>();
		return ret;
	}
	
	LinkedList<TweetEnemy> getTimeline(String username) {
		
		loadedFrom = loadedTo+1;
        loadedTo += BATCHSIZE;
        batch++;
		
		LinkedList<TweetEnemy> batchEnemies = new LinkedList<TweetEnemy>();
    	try {
			user = twitter.showUser(username);
			statuses = twitter.getUserTimeline(username, new Paging(loadedFrom, loadedTo));
		}
		catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            batchEnemies.add(new TweetEnemy("Sorry, seems "+username+"'s timeline could not be found... Oh well!", 1, 2));
            return batchEnemies;
        }
      
    	int i=0;
        for (Status s : statuses) {
            batchEnemies.add(new TweetEnemy(s.getText(), loadedFrom+i, batch));
            i++;
        }   
        
        return batchEnemies;
    }

}
