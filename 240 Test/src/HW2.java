
public class HW2 {
	
	public static void main(String[] args) {
		
		int count = 0;
		for(int x1=1; x1<100; x1++) {
			for(int x2=2; x2<100; x2++) {
				for(int x3=0; x3<100; x3++) {
					for(int x4=0; x4<100; x4++) {
						if(x1+x2+x3+x4 == 100) count++;
					}
				}
			}
		}
		System.out.println(count);
	}

	private static boolean varablesAreUnique(int[] xs) {
		for(int i=0; i<xs.length; i++) {
			for(int j=0; j<xs.length; j++) {
				if(i != j && xs[i] == xs[j]) return false;
			}
		}
		return true;
	}

}
