
public class Driver {

	public static void main (String args[]) {
		// TODO Auto-generated constructor stub
		
		Grade grade=new Grade(65);
		
		System.out.println("Letter grade is: "+grade.getLetterGrade());
		
		int score1= -100;
		
		System.out.println(score1+ " is a valid score? True or false: "+grade.isValidScore(score1));

		int[] subScores_to_add={30, 20, 50};

		System.out.println("Total score is: "+grade.getTotalScoreAndUpdateScore(subScores_to_add));
		System.out.println("Now new Letter grade is: "+grade.getLetterGrade());

	}

}
