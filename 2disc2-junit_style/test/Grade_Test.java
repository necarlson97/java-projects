import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class Grade_Test {

	private Grade grade;
	
	@Before
	public void before() {
		grade = new Grade(0);
	}
	
	@Test (timeout = 1000)
	public void test_getTotalScoreAndUpdateScore() {
		int[] subScores_to_add= new int[]{0, 0};
		assertEquals(0, grade.getTotalScoreAndUpdateScore(subScores_to_add));
		
		subScores_to_add=new int[]{10, 1, 1, 70};
		assertEquals(82, grade.getTotalScoreAndUpdateScore(subScores_to_add));
		
		subScores_to_add=new int[]{50, 50};
		assertEquals(100, grade.getTotalScoreAndUpdateScore(subScores_to_add));
			
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void test_getTotalScore_IllegalArgumentException(){
		
		int[] subScores_to_add=new int[]{-10, -10, 5};
		grade.getTotalScoreAndUpdateScore(subScores_to_add);
		
		subScores_to_add=new int[]{-200};
		grade.getTotalScoreAndUpdateScore(subScores_to_add);
		
		subScores_to_add=new int[]{200};
		grade.getTotalScoreAndUpdateScore(subScores_to_add);
		
		
		

	}

	@Test (timeout = 1000)
	public void test_getLetterGrade() {
		grade=new Grade(95);
		assertEquals("A",grade.getLetterGrade());
		
		grade=new Grade(83);
		assertEquals("B",grade.getLetterGrade());
		
		grade=new Grade(70);
		assertEquals("C",grade.getLetterGrade());
		
		grade=new Grade(69);
		assertEquals("D",grade.getLetterGrade());
		
		grade=new Grade(50);
		assertEquals("F",grade.getLetterGrade());
		
		grade=new Grade(2);
		assertEquals("F",grade.getLetterGrade());
		
		
		
		
		
		
		
		
		
		
		
	}
	
	@Test (timeout = 1000)
	public void test_isValidScore() {
		
		int invalidGrade = 120;
		assertEquals(false, grade.isValidScore(invalidGrade));
	
		invalidGrade = -15;
		assertEquals(false, grade.isValidScore(invalidGrade));
		
		invalidGrade = 200;
		assertEquals(false, grade.isValidScore(invalidGrade));


		
		
		
		
		
		
	}
	
	
}
