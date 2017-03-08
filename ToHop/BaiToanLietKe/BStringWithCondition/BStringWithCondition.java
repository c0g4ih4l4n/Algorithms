
/**
	* Description:
	* Liet ke tat ca day nhi phan do dai n
	* co '01' xuat hien dung 2 lan
	* input : n
	* output : cac day nhi phan
	* process : Kiem Tra day bang vong for hoac phuong phap sinh
**/

public class BStringWithCondition {
	
	public static void main (String[] args) {
		int n = 4;
		Solution1.main (n);
	}
	
	public static void print (int[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print (a[i] + " ");
		}
		System.out.println ();
	}

}

// Using loop to check each BString
class Solution1 {
	
	public static void main (int n) {
		int[] a = new int[n];
		int i;
		do {
			i = n - 1;
			while (i >= 0 && a[i] == 1) {i--;}
			if (i < 0) break;
			a[i] = 1;
			for (int j = i + 1; j < n; j++) {
				a[j] = 0;
			}
			if (checkCondition (a, 2))
			BStringWithCondition.print (a);
		} while (true);
	}
	
	public static boolean checkCondition (int[] a, int k) {
		boolean result = false;
		boolean check = false;
		int num = 0;
		int i = 0;
		int length = a.length;
		
		do {
			if (a[i] == 0) check = true;
			else if (check = true) {
				num ++;
				check = false;
			}
			i ++;
		} while (i < length);
		
		if (num == k) return true;
		return false;
	}
}

// Solution 2 using generation method
class Solution2 {
	
	public static void main(int n) {
	
	}
}
