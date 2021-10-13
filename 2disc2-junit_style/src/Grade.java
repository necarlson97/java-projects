/**
 * A class for grade 
 */

public class Grade {

	/**Score ranging from 0 to 100*/
	int score; 
	
	/**Constructor*/
	public Grade(int _score) {
		this.score=_score;
	}
	
	/**Returns letter grade for score ranging from 0 to 100*/
	public String getLetterGrade(){
		
		String grade="";
		
		if (score > 0){
			grade = "F";
		}
		if (score >= 60 ){
			grade= "D";
		}
		if (score >= 70 ){
			grade= "C";
		}
		if (score >= 80 ){
			grade= "B";
		}
		if (score >= 90 ){
			grade= "A";
		}
		
		return grade;
		
	}
	
	/**Add all subScores and return total score. It also updates the score.*/
	public int getTotalScoreAndUpdateScore(int[] subScores_to_add){
		int total=0;
		
		//adding sub scores to get total
		for(int i=0; i<subScores_to_add.length; i++){
			total+=subScores_to_add[i];
		}
		
		//handling exception case when score is over 100.
		if(isValidScore(total)){
			score=total;
		}
		//update score with new total value
		else{
			throw new IllegalArgumentException("Total score value is not valid.");
			
		}
		
		return total;
	}
	
	/**Check if score is valid, ranging from 0 to 100*/
	public boolean isValidScore(int score){
		return (score>=0 && score<=100);
	}
	
	/**Return score (Accessor)*/
	public int getScore(){
		return score;
	}

	/**Set new score (Mutator)*/
	public void setScore(int newScore){
		score=newScore;
	}

}

