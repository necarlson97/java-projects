import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;
import java.util.Random;


public class TwitterTest {
	
	
	private static final String CONSUMER_KEY = "";
	private static final String CONSUMER_SECRET = "";
	private static final String ACCESS_KEY = "";
	private static final String ACCESS_SECRET = "";


	public static void main(String[] args){
		new TwitterTest();
	}
	
	Twitter twitter;
	User user;
	List<Status> statuses;
	
	public TwitterTest(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
	        .setOAuthConsumerKey(CONSUMER_KEY)
	        .setOAuthConsumerSecret(CONSUMER_SECRET)
	        .setOAuthAccessToken(ACCESS_KEY)
	        .setOAuthAccessTokenSecret(ACCESS_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
		
		
		getTimeline("BarackObama");
		getTimeline("realDonaldTrump");
	}
	


    private void getTimeline(String username) {
    	
    	try {
			user = twitter.showUser(username);
			statuses = twitter.getUserTimeline(username, new Paging(1, 20));
		}
		catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
      
        // gets Twitter instance with default credentials
        System.out.println("Showing @" + user.getScreenName() + "'s home timeline.");
     
        for (Status status : statuses) {
            System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
        }   
        System.out.println();
    }

}
