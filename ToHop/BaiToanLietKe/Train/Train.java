
public class Train {

	static final String INPUT = "INPUT";
	static final String OUTPUT = "OUTPUT";
	 
	public static void main (String[] args) {
		int n = 4;
		
		int[] a = new int[n];
		
		for (int i = 0; i < n; i++) {
			a[i] = 0;
		}
		
		//Train.writeOut (a);
		
		int i = n - 1;
		int[] arr = new int [n + 1];
		arr[0] = 1;
		
		do {
			i = n - 1;
			while (i >= 0 && a[i] == 1) {i--;}
			if (i >= 0) {
				a[i] = 1;
				for (int j = i + 1; j < n; j++) {
					a[j] = 0;
				}
				arr[Train.findLongest (a)]++;
			} else break;
		} while (true);
		
		Train.writeOut (arr);
		int numString = (int) Math.pow (2, n);
		System.out.println ("Total BinaryString : " + numString);
	}
	
	public static void writeOut (int[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print (a[i] + " ");
		}
		System.out.println ();
	}
	
	public static int findLongest (int[] a) {
		int max = 0;
		int curMax = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] == 0) {
				if (max < curMax) max = curMax;
				curMax = 0;
			} else {
				curMax ++;
			}
		}
		
		if (curMax > max) return curMax;
		
		return max;
	}
}
