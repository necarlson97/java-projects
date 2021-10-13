import java.util.Arrays;
import java.util.LinkedList;

public class help {
	
	public static void main(String[] args){
		
		cs311Help2();
		
	}
	
	private static void cs311Help2() {
		int[] a = {4, 12, 1, 6, 5};
		int[] b = {3, 2, 10, 14, 9};
		
		int asIs = asIs(a, b);
		int ascAsc = ascAsc(a, b);
		int ascDec = ascDec(a, b);
		int decDec = decDec(a, b);
		int decAsc = decAsc(a, b);
		
		int max = max(a, b);
		
		System.out.println("as is: "+asIs);
		System.out.println("asc asc: "+ascAsc);
		System.out.println("asc dec: "+ascDec);
		System.out.println("dec asc: "+decAsc);
		System.out.println("dec dec: "+decDec);
		System.out.println("max: "+max);
		
	}
	
    static void swap(int[] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }

    static int[][] permute(int[] arr) {
    	LinkedList<int[]> l = new LinkedList<int[]>();
        permute(arr, 0, arr.length - 1, l);
        return l.toArray(new int[arr.length][l.size()]);
    }

    static void permute(int[] arr, int i, int n, LinkedList<int[]> l) {
        int j;
        if (i == n) {
            l.add(arr);
        }
        else {
            for (j = i; j <= n; j++) {
                swap(arr, i, j);
                permute(arr, i + 1, n, l);
                swap(arr, i, j); // backtrack
            }
        }
    }
	
	private static int max(int[] a, int[] b) {
		int[][] allA = permute(a);
		int[][] allB = permute(b);
		
		int max = 0;
		
		for(int i=0; i<allA.length; i++){
			for(int j=0; j<allB.length; j++){
				int temp = product(allA[i], allB[j]);
				if(temp > max) max = temp;
			}
		}
		return max;
	}

	private static int product(int[] a, int[] b) {
		int sum = 0;
		for(int i = 0; i<a.length; i++)
			sum += Math.pow(a[i], b[i]);
		return sum;
	}
	
	private static int[] reverse(int[] a){
		for(int i=0; i<a.length/2; i++){
			int temp = a[i];
			a[i] = a[a.length - i - 1];
			a[a.length - i - 1] = temp;
		}
		return a;
	}
	
	private static int asIs(int[] a, int[] b) {
		return product(a, b);
	}

	private static int decAsc(int[] a, int[] b) {
		Arrays.sort(a);
		a = reverse(a);
		
		Arrays.sort(b);
		return product(a, b);
	}

	private static int decDec(int[] a, int[] b) {
		Arrays.sort(a);
		a = reverse(a);
		
		Arrays.sort(b);
		b = reverse(b);
		return product(a, b);
	}

	private static int ascDec(int[] a, int[] b) {
		Arrays.sort(a);
		
		Arrays.sort(b);
		b = reverse(b);
		return product(a, b);
	}

	private static int ascAsc(int[] a, int[] b) {
		Arrays.sort(a);
		
		Arrays.sort(b);
		return product(a, b);
	}

	private static void cs230Help() {
		int x = 24;
		System.out.println(x+" gave "+phase5(x));
		
	}

	private static int phase5(int x) {
		int c = 97;
		for(int i=97; i<106; i++){

		}
		return c;
	}

	private static boolean phase4(int given) {
		// TODO Auto-generated method stub
		int ebpC = 0;
		int ebp8 = 0;
		while(ebp8 <= 2){
			int ebp4 = 4;
			while(ebp4 > 0){
				ebpC = ebpC + ebp8 + ebp8;
				ebp4 -= 1;
			}
			ebp8 += 1;
		}
		System.out.println("ebpC: "+ebpC);
		return (given == ebpC);
	}

	private static void cs11Help1() {
		char a = 2;
		int b = 13;
		int n = 44;
		
		System.out.println("Was: "+rightLeftModExp(a, b, n));
		double shouldBe = (Math.pow(a, b)) % n;
		System.out.println("Should be: "+shouldBe);
		System.out.println("Should be: "+leftRightModExp(a, b, n));
		
	}

	static int rightLeftModExp(char a, int b, int n){
		int d = 1;
		
		boolean[] bits = bitsFromInt(b);
	    
		int zeroCount = 0;
	    for(int i=0; i<bits.length; i++){
	    	
	    	if(bits[i] == true){
	    		
	    		while(zeroCount>0){
	    			//System.out.println("\td="+d);
	    			d = (d*d) % n;
	    			zeroCount--;
	    		}
	    		d = (d*a) % n;
	    		d = (d*d) % n;
	    		
	    		
	    	}
	    	else zeroCount++;
	    			
	    	//System.out.println(i+": d="+d+" bit="+bits[i]+" zeros: "+zeroCount);
	    }
		return d;
	}
	
	static int leftRightModExp(char a, int b, int n){
		int d = 1;
		
		boolean[] bits = bitsFromInt(b);
	    
	    for(int i=bits.length-1; i>=0; i--){
	    	d = (d*d) % n;
	    	if(bits[i] == true)
	    		d = (d*a) % n;
	    	//System.out.println(i+": d="+d+" bit="+bits[i]);
	    }
		return d;
	}

	private static boolean[] bitsFromInt(int b) {
		boolean[] bits = new boolean[8];
	    for (int i = bits.length-1; i >= 0; i--) {
	        bits[i] = (b & (1 << i)) != 0;
	        if(bits[i]) System.out.print("1");
	        else System.out.print("0");
	    }
	    System.out.println();
	    return bits;
	}

}
