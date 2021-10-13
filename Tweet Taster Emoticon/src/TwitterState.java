import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterState {
	
	private static final String CONSUMER_KEY = "";
	private static final String CONSUMER_SECRET = "";
	private static final String ACCESS_KEY = "";
	private static final String ACCESS_SECRET = "";
	
	TwitterStream twitterStream;
	
	static Emotion[] emotions = {new Emotion("Happy"), 
			new Emotion("Angry"),
			new Emotion("Sad"), 
			new Emotion("Afraid")};
	
	StatusBatch batch;
	
	TwitterState() {
		setupTwitter();
		batch = new StatusBatch();
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
		
		LinkedList<String> track = new LinkedList<String>();
		for(int i=0; i<emotions.length; i++) {
			for(int j=0; j<emotions[i].words.length; j++) {
				track.add(emotions[i].words[j]);
			}
		}
		
		FilterQuery fq = new FilterQuery(track.toArray(new String[track.size()]));
		fq.language("en");
		// USA
		fq.locations(new double[][]{new double[]{-126.562500,30.448674},
            new double[]{-61.171875,44.087585}});
		twitterStream.filter(fq);
	}
	
	void processStatus(Status status) {
		for(int i=0; i<emotions.length; i++) {
			if(statusContains(status, emotions[i])) {
				batch.add(i);
			}
		}
	}
	
	

	private boolean statusContains(Status s, Emotion e) {

		String str = s.getText();
		for(int i=0; i<e.words.length; i++) {
			if(str.contains(e.words[i])) return true;
		}
		
		return false;
	}

	void update() {
		
	}
	
	void render(Graphics g) {
		
		batch.render(g);
	}

	public Emotion getEmotion() {
		return emotions[0];
	}

}

