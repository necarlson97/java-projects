import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

// here is where we use everything written in the other file to gather our data,
// and then write out the entire excell file
public class GenderTallyDriver {
	
	// here is a container of our results for the 50 books we have
	// each value will become one row of the excell sheet
	private static String[] csvResults = new String[50];
	
	public static void main(String[] args) throws Exception{
		// these are here just so I use 'm' as shorthand for adding male to the
		// excell row, and 'f' for female
		String m = ",Male\n";
		String f = ",Female\n";
		
		File femaleFolder = new File("/Users/mac/Documents/workspace/Stats240Gender/FemaleAuthors");
		File[] femaleList = femaleFolder.listFiles();
		
		File maleFolder = new File("/Users/mac/Documents/workspace/Stats240Gender/MaleAuthors");
		File[] maleList = maleFolder.listFiles();
		
		int i=0;
		for (File femaleFile: femaleList) {
			if( femaleFile.getName().contains(".txt") ) {
				String fileName = "FemaleAuthors/"+femaleFile.getName();
				GenderTally gt = new GenderTally(fileName);
				gt.printResults();
				csvResults[i] = gt.getCsv()+f;
				i++;
			}
		}
		for (File maleFile: maleList) {
			if( maleFile.getName().contains(".txt") ) {
				String fileName = "MaleAuthors/"+maleFile.getName();
				GenderTally gt = new GenderTally(fileName);
				gt.printResults();
				csvResults[i] = gt.getCsv()+m;
				i++;
			}
		}
		
		// down here is where we call 'csv()' to start the proccess of creating the excell file
		csv();
	}
	
	
	public static void csv() throws Exception {
		// Just lets us know down below we have begun this process
		System.out.println("Writing CSV file");

		// Goes looking for the 'stats240.csv' file in my documents
		String csvFileName = "/Users/mac/Documents/stats240.csv";
		// Clears out any existing file to make it blank
		File csvFile = new File(csvFileName);
		// Creates a 'writer' that is like our 'scanner', only it writes instead of reading
        FileWriter writer = new FileWriter(csvFile);
        
        // This is the writer addding our first row
        // It is just the headers of each of the columns
        writer.append("Name of Text,Publish Date,Male Pronouns,Female Pronouns,Total Pronouns,"
        		+ "Total Words,Author Gender\n");
        
        // Here, we go through each of the 15 slots in the container, 
        // and take each value and write it as the next row in the excell file
        for(int i=0; i<csvResults.length; i++) {
        	writer.append(csvResults[i]);
        }
        
        // This closes our our writer, saves, and finishes our excell sheet
        writer.flush();
        writer.close();
        
        // And this just lets us know we have finished!
        System.out.println("Finished CSV file");
	}

}
