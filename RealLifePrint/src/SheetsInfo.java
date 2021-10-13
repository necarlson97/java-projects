import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;


import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.util.ServiceException;

public class SheetsInfo {
	
	private static final String SERVICE_NAME = "Google Sheets API Test";

	private static final String CLIEND_ID = 
			"";
	private static final String CLIENT_SECRET = 
			"";
	
	private static final String USER = "";
	private static final String PASS = "";
	
	private static final String ATTRIBUTES  = 
			"https://docs.google.com/spreadsheets/d/1ntjx1taeV7Ijs888TqJMRwt-yfy75kLHZ6z6B2aOQ6M/edit#gid=0";
	
	private static final String INJURIES = 
			"https://docs.google.com/spreadsheets/d/1n7mLZ6BLZZ_X-pQQSSs_Sy-Os5WFZ7heOmuoQMUxSXo/edit#gid=0";
	
	private static final String BUFFS = 
			"https://docs.google.com/spreadsheets/d/1mCx-VdoZYPyFpqPfdkmh9MvlX6CmP7sEeTOHt35O4wc/edit#gid=0";
	
	private static final String DISORDERS = 
			"https://docs.google.com/spreadsheets/d/1YQ0tZPrIOEddarEMzAlgB4Ve_ezpncTpdRlC6UiRSXs/edit#gid=0";
	
	private static final String DISEASES =
			"https://docs.google.com/spreadsheets/d/1mMSEXc6Fwe1tUZo0v8fdbrjoLWSMPomVgWGCv9mRJDU/edit#gid=0";
	
	SheetsInfo() {
		String[] SCOPESArray = {"https://spreadsheets.google.com/feeds", "https://spreadsheets.google.com/feeds/spreadsheets/private/full", "https://docs.google.com/feeds"};
        final List SCOPES = Arrays.asList(SCOPESArray);
        GoogleCredential credential = new GoogleCredential.Builder()
                .setServiceAccountId("cliend_ID")
                .setServiceAccountScopes(SCOPES)
                .setServiceAccountPrivateKeyFromP12File(p12)
                .build();
        
		SpreadsheetService service = new SpreadsheetService(SERVICE_NAME);
		
		try {
			service.setUserCredentials(USER, PASS);
			URL metafeedUrl = new URL(ATTRIBUTES);
			
			SpreadsheetEntry spreadsheet = service.getEntry(metafeedUrl,SpreadsheetEntry.class);
	        URL listFeedUrl = spreadsheet.getWorksheets().get(0).getListFeedUrl();
	        
	        ListFeed feed = service.getFeed(listFeedUrl, ListFeed.class);
	        for (ListEntry entry : feed.getEntries()) {
	            System.out.println("");
	            for (String tag : entry.getCustomElements().getTags()) {
	                System.out.print(tag + ": "+ entry.getCustomElements().getValue(tag)+", ");
	            }
	        }
		} catch (IOException | ServiceException e) {
			e.printStackTrace();
		}
	}
	
	
}
