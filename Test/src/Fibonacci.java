import java.util.Scanner;

public class Fibonacci {
	public static int fib(int n) {
		// computes fibonacci function for any non-negative integer n
		if (n <= 1)
			return n;
		return fib(n - 1) + fib(n - 2);
	}

	/**
	 *  Computes the Fibonacci function non-recursively using int type to store fibonacci numbers
	 * <code>n</code>
	 */
	public static int fastFib(int n) {
		
		int a = 1;
		int b = 0;
		
		int temp = 0;
		for(int i=0; i<n-1; i++){
			temp = a;
			a += b;
			b = temp;
			if(a<0){
				System.out.println("Int overflow");
				break;
			}
		}
		
        return a;   
        
	}
	
	
	/**
	 * Computes the Fibonacci function non-recursively using long type to store fibonacci numbers
	 * <code>n</code>
	 */
	public static long longFastFib(int n) {
		long a = 1;
		long b = 0;
		
		long temp = 0;
		for(int i=0; i<n-1; i++){
			temp = a;
			a += b;
			b = temp;
			if(a<0){
				System.out.println("Int overflow");
				break;
			}
		}
		
        return a;   

















    
    
    }
	
	
	
	public static void main(String[] args) {

		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("To compute nth fibonacci number\nEnter n value: ");
		int n = reader.nextInt(); // Scans the next token of the input as an int.
				
		System.out.println();
		
		//System.out.println("fib:         "+fib(n));
		System.out.println("fastFib:     "+fastFib(n));
		System.out.println("longFastFib: "+longFastFib(n));

	}
}
