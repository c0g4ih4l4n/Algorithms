
// Bai toan 
// cho n ten nguoi
// liet ke tat ca truong hop k nguoi co the

public class LuckyGuys {
	
	public static void main (String[] args) {
		int n = 4;
		int k = 3;
		String[] lists = {"Alice", "Bob", "Christine", "Dadan"};
		
		int[] a = new int[3];
		for (int i = 0; i < k; i++) {
			a[i] = i;
		}
		
		IO.print (lists, a);
		
		int j = k - 1;
		
		do {
			j = k - 1;
			while (j >= 0 && a[j] == (n - k + j)) {j--;}
		
			if (j < 0) break;
			
			a[j] ++;
			for (int i = j + 1; i < k; i++) {
				a[i] = a[i-1] + 1;
			}
			
			IO.print (lists, a);
		} while (true);
	}
}

class IO {
	
	public static void print (String[] lists, int[] a) {
		int length = a.length;
		for (int i = 0; i < length; i++) {
			System.out.print (lists[a[i]] + " ");
		}
		System.out.println ();
	}
	
	public static Input getInput (String fileName) {
		Input input = new Input();
		
		try (FileReader fr = new FileReader (fileName)) {
			input.n = Character.getNumericValue (fr.read());
			fr.read();
			input.k = Character.getNumericValue (fr.read());
		}
	}

}

class Input {
	int n;
	int k;
	String[] lists;
}
