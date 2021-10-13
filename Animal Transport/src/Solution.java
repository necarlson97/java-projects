import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
	
	class Animal {
		char type;
		int source;
		int dest;
		Animal(char _type, int _source, int _dest) {
			type = _type;
			source = _source;
			dest = _dest;
		}
		
		boolean conflict(Animal a) {
			if(dest > a.source && source < a.dest) {
				if(type == 'D' && a.type =='E') return true;
				if(type == 'E' && a.type =='D') return true;
				
				if(type == 'C' && a.type =='D') return true;
				if(type == 'D' && a.type =='C') return true;
				
				if(type == 'M' && a.type =='C') return true;
				if(type == 'C' && a.type =='M') return true;
				
				if(type == 'E' && a.type =='M') return true;
				if(type == 'M' && a.type =='E') return true;
			}
			return false;
		}
		
		public String toString() {
			return type+" "+source+" "+dest;
		}
	}
	
	class AnimalComparator implements Comparator {

		@Override
		public int compare(Object o1, Object o2) {
			Animal a1 = (Animal) o1;
			Animal a2 = (Animal) o2;
			return a2.dest - a1.dest;
		}
		
	}

	static Solution sol = new Solution();
	static int[] minimumZooNumbers(int m, int n, char[] t, int[] s, int[] d) {
		
		int[] ret = new int[n];
		Animal a[] = new Animal[n];
		
		for(int i=0; i<n; i++) {
			ret[i] = -1;
			a[i] = sol.new Animal(t[i], s[i], d[i]);
		}
		
		PriorityQueue<Animal> q = new PriorityQueue<Animal>(sol.new AnimalComparator());
		
		for(int numb=n-1; numb>0; numb--) {
			q.clear();
			for(int i=0; i<numb; i++) {
				boolean badInsert = false;
				for(Animal ani : q) {
					if(a[i].conflict(ani)) {
						if(ani.dest > a[i].dest) q.remove(ani);
						else {
							badInsert = true;
							break;
						}
					}
				}
				if(!badInsert) q.add(a[i]);
			}
			
			System.out.println(q);
			while(!q.isEmpty()) {
				ret[numb] = q.poll().dest;
				numb--;
			}
		}
		
		System.out.println(Arrays.toString(a));

		return ret;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int cases = in.nextInt();
		for(int a0 = 0; a0 < cases; a0++){
			int m = in.nextInt();
			int n = in.nextInt();
			char[] t = new char[n];
			for(int t_i = 0; t_i < n; t_i++){
				t[t_i] = in.next().charAt(0);
			}
			int[] s = new int[n];
			for(int s_i = 0; s_i < n; s_i++){
				s[s_i] = in.nextInt();
			}
			int[] d = new int[n];
			for(int d_i = 0; d_i < n; d_i++){
				d[d_i] = in.nextInt();
			}
			int[] result = minimumZooNumbers(m, n, t, s, d);
			for (int i = 0; i < result.length; i++) {
				System.out.print(result[i] + (i != result.length - 1 ? " " : ""));
			}
			System.out.println("");


		}
		in.close();
	}
}
