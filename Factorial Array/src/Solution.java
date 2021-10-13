import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int[] A = new int[n];
        for(int A_i = 0; A_i < n; A_i++){
            A[A_i] = in.nextInt();
        }
        for(int a0 = 0; a0 < m; a0++){
        		int action = in.nextInt();
        		if(action == 1) increase(A, in.nextInt(), in.nextInt());
        		else if(action == 2) sum(A, in.nextInt(), in.nextInt());
        		else if(action == 3) set(A, in.nextInt(), in.nextInt());
        }
        in.close();
    }

	private static void increase(int[] a, int l, int r) {
		for(int i=l-1; i<r; i++)
			a[i]++;
	}

	private static long mod = 1000000000;
	private static void sum(int[] a, int l, int r) {
		long sum = 0;
		for(int i=l-1; i<r; i++)
			sum += fact(a[i]) % mod;
		System.out.println(sum);
	}

	static Hashtable<Integer, Long> table = new Hashtable<Integer, Long>();
	private static long fact(int i) {
		if(i == 1 || i == 0) return 1;
		if(i > 65) return 0;
		if(table.containsKey(i)) return table.get(i);
		long f = i * fact(i-1) % mod;
		table.put(i, f);
		return f;
	}

	private static void set(int[] a, int i, int v) {
		a[i] = v;
	}
}
