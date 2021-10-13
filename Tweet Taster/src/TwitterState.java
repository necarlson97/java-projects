import java.awt.Graphics;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterState {
	
	private static final String CONSUMER_KEY = "";
	private static final String CONSUMER_SECRET = "";
	private static final String ACCESS_KEY = "";
	private static final String ACCESS_SECRET = "";
	
	Twitter twitter;
	
	Emotion[] emotions = {new Emotion("Happy"), 
						new Emotion("Angry"),
						new Emotion("Sad") };
	Query[] queries;
	
	Emotion topEmotion = emotions[0];
	
	TwitterState() {
		setupTwitter();
		setupQueries();
		
		getSnapshot();
	}

	private void setupTwitter() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
			.setOAuthConsumerKey(CONSUMER_KEY)
			.setOAuthConsumerSecret(CONSUMER_SECRET)
			.setOAuthAccessToken(ACCESS_KEY)
			.setOAuthAccessTokenSecret(ACCESS_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}
	
	private void setupQueries() {
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		Date date = new Date();
		String today = dateFormat.format(date);
		System.out.println(today);
		queries = new Query[emotions.length];
		for(int i=0; i<queries.length; i++) {
			queries[i] = new Query("#"+emotions[i].name);
			queries[i].setLang("en");
			queries[i].setSince(today);
			queries[i].setCount(100);
		}
	}

	void update() {
		
	}
	
	void getSnapshot() {
		QueryResult result;
		int totalCount = 0;
	    try {
	    		for(int i=0; i<emotions.length; i++) {
	    			result = twitter.search(queries[i]);
	    			printResult(result);
	    			totalCount += result.getTweets().size();
	    			emotions[i].count = result.getTweets().size();
	    			System.out.println(emotions[i].name+" : "+emotions[i].count);
	    		}
	    		System.out.println("Total: "+totalCount);
	    		
	    		int max = 0;
	    		for(int i=0; i<emotions.length; i++) {
	    			if(emotions[i].count > max) {
	    				max = emotions[i].count;
	    				topEmotion = emotions[i];
	    			}
	    			emotions[i].intensity = (float)emotions[i].count/totalCount;
	    		}
			
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	    
	}
	
	void printResult(QueryResult r) {
		for(Status s : r.getTweets()) {
			System.out.println(s.getText());
		}
	}
	
	void render(Graphics g) {
		int x = 0;
		int y = TweetTaster.windowHeight;
		int height = TweetTaster.windowHeight/10;
		int width = TweetTaster.windowWidth/emotions.length;
		int barHeight;
		
		for(int i=0; i<emotions.length; i++) {
			g.setColor(emotions[i].color);
			barHeight = (int) (height*emotions[i].intensity);
			g.fillRect(x, y-barHeight, width, barHeight);
			x+=width;
		}
	}
	
	Emotion getEmotion() {
		return topEmotion;
	}

}
