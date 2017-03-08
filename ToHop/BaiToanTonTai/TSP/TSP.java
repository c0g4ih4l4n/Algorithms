
import java.lang.Math;

public class TSP {

	public static void main (String[] args) {
		int n = 2;
		int[][] a = new int[n][n];
		
		Init.initCost (a, n);
		
	}
}

class Init {
	
	// init cost of each road
	public static void initCost (int[][] a, int n) {
	
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i = j) {
					a[i][j] = 0;
				} else a[i][j] = (int) (Math.random() * 100);	
			}
		}
		// end init array Cost
	}
	
	public static void init
}

class State {
	int[] x = new int[n];
	
	public int calculateTSP (int[][] a, int[] x) {
		
	}
}
