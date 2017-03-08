
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.File;

public class Permutation {
	static final String INPUT_FILE = "PERMUTE.INP";
	static final String OUTPUT_FILE = "PERMUTE.OUT";
	
	public static void main (String[] args) {
		
		int n = Permutation.getInput (Permutation.INPUT_FILE);
		if (n == 0) {
			System.out.println ("N = 0");
			System.exit (0);
		}
		Permutation.deleteOldFile (Permutation.OUTPUT_FILE);
		int[] a = new int[n];
		int i = n - 1;
		
		// init
		for (int k = 0 ; k < n ; k++) {
			a[k] = k + 1;
		}
		
		Permutation.writeOutput (Permutation.OUTPUT_FILE , a , n);
		
		// loop
		while (true) {
			i = n - 1;
			while (i > 0 && a[i-1] > a[i]) {
				i--;
			}
		
			if (i == 0) break;
		
			int j = i; i--;
			int min = a[j] , jmin = j ; j++;
		
			while (j < n) {
				if (a[j] < min) {
					if (a[j] < a[i]) break;
					min = a[j];
					jmin = j;
				}
				j++;
			}
			Permutation.swap (a , i , jmin);
		
			j = 0; i++;
		
			while (i + j < n - 1 - j) {
				Permutation.swap (a , i + j , n - 1 - j);
				j++;
			}
			Permutation.writeOutput (Permutation.OUTPUT_FILE , a , n);
		}
	}
	
	public static void swap (int[] a , int i , int j) {
		int temp;
		temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	public static void writeOut (int[] a , int n) {
		for (int i = 0 ; i < n ; i++) {
			System.out.print (" " + a[i]);
		}
		System.out.println ("");
	}
	
	public static void deleteOldFile (String fileName) {
		try {
			File file = new File (fileName);
			file.delete();
		} catch (Exception e) {
			System.out.println ("Unable to delete old file ... ");
		}
	}
	
	public static void writeOutput (String fileName , int[] a , int n) {
		try (FileWriter fw = new FileWriter (fileName , true);
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter writer = new PrintWriter (bw))
		{
			String line = "";
			for (int i = 0 ; i < n ; i++) {
				line += " " + a[i];
			}
			writer.println (line);
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println ("File Not Found ... ");
		} catch (IOException e) {
			System.out.println ("Unable to process ... ");
		}
	}
	
	public static int getInput (String fileName) {
		int result = 0;
		
		try (FileReader fr = new FileReader (fileName))
		{
			while ((result = Character.getNumericValue(fr.read ())) != -1) {
				fr.close();
				return result;
			}
		} catch (FileNotFoundException e) {
			System.out.println ("File Not Found ... ");
		} catch (IOException e) {
			System.out.println ("Unable to process ... ");
		}
		
		return result;
	}
}
