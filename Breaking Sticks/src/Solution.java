import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
	
	static Hashtable<Long, Long> table = new Hashtable<Long, Long>();

	static long longestSequence(long[] a) {
		//long bef = System.currentTimeMillis();
		int s = 0;
		for(long l : a) 
			s += seq(l);
		//System.out.println(System.currentTimeMillis() - bef);
		return s;
	}

	private static long seq(long n) {
		if(n == 1) return 1;
		Long score = 1 + n;
		
		if(table.containsKey(n)) return table.get(n);
		if(isPrime(n)) {
			table.put(n, score);
			return score;
		}
		for(long i = n/2; i>1; i--) {
			if(n % i == 0) {
				long s = 1 + (seq(n/i)*i);
				if (s > score) score = s;
			}
		}
		table.put(n, score);
		return score;
	}

	private static boolean isPrime(long n) {
		if(n > 2 && n % 2 == 0) return false;
		int max = (int) (Math.sqrt(n) + 1);
		for(int i=3; i<max; i+=2) {
			if(n % i == 0) return false;
		}
		return true;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		long[] a = new long[n];
		for(int a_i = 0; a_i < n; a_i++){
			a[a_i] = in.nextLong();
		}
		long result = longestSequence(a);
		System.out.println(result);
		in.close();
	}
}
