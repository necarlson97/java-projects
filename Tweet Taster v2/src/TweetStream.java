import twitter4j.FilterQuery;
import twitter4j.HashtagEntity;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetStream {
	
	private static final String CONSUMER_KEY = "";
	private static final String CONSUMER_SECRET = "";
	private static final String ACCESS_KEY = "";
	private static final String ACCESS_SECRET = "";
	
	TwitterStream twitterStream;
	
	static int batchSize = 100;
	Status[] batch = new Status[batchSize];
	
	TweetStream() {
		 setup();
	}
	
	void setup() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
			.setOAuthConsumerKey(CONSUMER_KEY)
			.setOAuthConsumerSecret(CONSUMER_SECRET)
			.setOAuthAccessToken(ACCESS_KEY)
			.setOAuthAccessTokenSecret(ACCESS_SECRET);
		
		twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		StatusListener listener = new StatusListener(){
	        public void onStatus(Status status) {
	            TweetStream.this.processStatus(status);
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
		
		
		FilterQuery fq = new FilterQuery(new String[] {"#happy"});
		twitterStream.filter(fq);
	}

	void processStatus(Status status) {
		for(HashtagEntity h : status.getHashtagEntities()) {
			System.out.println(h.getText());
		}
	}

}
