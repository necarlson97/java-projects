import java.awt.Graphics;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import twitter4j.FilterQuery;
import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterState {
	
	private static final String CONSUMER_KEY = "";
	private static final String CONSUMER_SECRET = "";
	private static final String ACCESS_KEY = "";
	private static final String ACCESS_SECRET = "";
	
	TwitterStream twitterStream;
	
	static int batchSize = 100;
	int[] batch = new int[batchSize];
	int batchIndex = 0;
	
	Emotion[] emotions = {new Emotion("Happy"), 
						new Emotion("Angry"),
						new Emotion("Sad") };
	
	Emotion topEmotion = emotions[0];
	
	TwitterState() {
		setupTwitter();
		for(int i=0; i<batchSize; i++)
			batch[i] = -1;
	}

	private void setupTwitter() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
			.setOAuthConsumerKey(CONSUMER_KEY)
			.setOAuthConsumerSecret(CONSUMER_SECRET)
			.setOAuthAccessToken(ACCESS_KEY)
			.setOAuthAccessTokenSecret(ACCESS_SECRET);
		
		twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		StatusListener listener = new StatusListener(){
	        public void onStatus(Status status) {
	            TwitterState.this.processStatus(status);
	        }
	        
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
	        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
	        public void onException(Exception ex) {
	            ex.printStackTrace();
	        }
			@Override
			public void onScrubGeo(long arg0, long arg1) {}
			@Override
			public void onStallWarning(StallWarning arg0) {}
	    };
		twitterStream.addListener(listener);
		
		String[] track = new String[emotions.length];
		for(int i=0; i<track.length; i++) {
			track[i] = emotions[i].name;
		}
		
		FilterQuery fq = new FilterQuery(track);
		fq.language("en");
		// USA
		fq.locations(new double[][]{new double[]{-126.562500,30.448674},
            new double[]{-61.171875,44.087585}});
		twitterStream.filter(fq);
	}
	
	void processStatus(Status status) {
		for(int i=0; i<emotions.length; i++) {
			if(status.getText().contains(emotions[i].name)) {
				emotions[i].statusCount++;
				batchIndex = (batchIndex+1)%batchSize;
				batch[batchIndex] = i;
			}
		}
		
	}

	void update() {
		
	}
	
	void render(Graphics g) {
		int x = TweetTaster.windowHeight;
		int y = TweetTaster.windowHeight;
		int height = TweetTaster.windowHeight;
		int width = (TweetTaster.windowWidth-TweetTaster.windowHeight)/emotions.length;
		int barHeight;
		
		float[] intensity = intensities();
		
		for(int i=0; i<emotions.length; i++) {
			g.setColor(emotions[i].color);
			barHeight = (int) (height*intensity[i]);
			g.fillRect(x, y-barHeight, width, barHeight);
			x+=width;
		}
	}
	
	private float[] intensities() {
		float[] ret = new float[emotions.length];
		int[] count = new int[emotions.length];
		for(int i=0; i<batchSize; i++) {
			if(batch[i] < 0) continue;
			count[batch[i]]++;
		}
		for(int i=0; i<ret.length; i++) {
			ret[i] = (float)count[i] / batchSize;
		}
		return ret;
	}

	Emotion getEmotion() {
		return topEmotion;
	}

}

