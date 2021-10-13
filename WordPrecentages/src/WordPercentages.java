import java.io.IOException;

public class WordPercentages extends FileAccessor{
	
	private double[] percentageArray =  new double[16];
	private int[] wordCount = new int[16];
	private int totalWordCount;
	private int totalLetterCount;
	private int avgWord;
	
	
	public WordPercentages(String filename) throws IOException{
		super(filename);
		
	}

	
	public void processLine(String line){
		
		String[] w = line.split("[,.;:?!-() ]");
		for(int i = 1; i<16; i++){
			for(String s : w){
				if(i == 15 && s.length() >= 15){
					wordCount[i] ++;
					totalWordCount ++;
					totalLetterCount += s.length();
				}
				else if(s.length() == i){
					wordCount[i] ++;
					totalWordCount ++;
					totalLetterCount += i;
				}
			}
		}
		
	}

	public double[] getWordPercentages() {		
		for(int i = 1; i<wordCount.length; i++ ){
			percentageArray[i] = 100*(double)wordCount[i] / totalWordCount;
		}
		return percentageArray;
	}

	public double getAvgWordLength() {
		return (double)totalLetterCount / totalWordCount;
	}

}
