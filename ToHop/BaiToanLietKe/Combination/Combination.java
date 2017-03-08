
// Doc vao 2 bien n va max
// in ra cac to hop chap n cua max phan tu

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;

public class Combination {

	static final String INPUT_FILE = "SUBSET.INP";
	static final String OUTPUT_FILE = "SUBSET.OUT";

	public static void main (String[] args) {
		int[] result = Combination.getInput (Combination.INPUT_FILE);
		System.out.println (result[0] + " " + result[1]);
		int n = result[0];
		Combination.deleteOldOutput (Combination.OUTPUT_FILE);
		int max = result[1];
		
		if (n == 0 && n > max) {
			System.out.println ("Variable's not valid !!");
			System.exit (0);
		}
		
		int[] a = new int[n];
		int i;
		
		for (i = 0 ; i < n; i++) {
			a[i] = i + 1;
		}
		
		Combination.writeOutput (Combination.OUTPUT_FILE , a , n);
		do {
			// tim vi tri dau tien tu cuoi co a[i] != max_value
			i = n - 1;
			while (i >= 0 && a[i] == (max - n + i + 1)) {i--;}
			
			// neu tim thay
			if (i >= 0) {
				a[i] ++;
				// dat cac phan tu phia sau thanh gia tri nho nhat
				for (int j = i + 1; j < n ; j++) {
					a[j] = a[j-1] + 1;
				}
				Combination.writeOutput (Combination.OUTPUT_FILE , a , n);
			}
			
		} while (i >= 0);
			
	} // end main
	
	public static int[] getInput (String fileName) {
		int[] result = new int[2];
		
		try (FileReader fr = new FileReader (fileName))
		{
			result[0] = Character.getNumericValue (fr.read());
			while ((result[1] = Character.getNumericValue (fr.read())) == -1) {};
			fr.close();
		} catch (FileNotFoundException e) {
			System.out.println ("File Not Found ...");
		} catch (IOException e) {
			System.out.println ("Unable to process .. ");
		}
		
		return result;
	}
	
	public static void writeOutput (String fileName , int[] a , int n) {
		try (FileWriter fw = new FileWriter(fileName, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter writer = new PrintWriter(bw))
		{	
			String line = "";
			
			for (int i = 0 ; i < n ; i++) {
				line += a[i] + " ";
			}
			writer.println(line);
			writer.close();
		} catch (FileNotFoundException e) { 
			System.out.println ("File Not Found ...");
		} catch (IOException e) {
			System.out.println ("Unable to process ...");
		}
	}
	
	public static void deleteOldOutput (String fileName) {
		try {
			File file = new File (fileName);
			file.delete();
		} catch (Exception e) {
		
		}
	}

	public static void print (int[] a , int n) {
		for (int i = 0 ; i < n ; i++) {
			System.out.print (" " + a[i]);
		}
		System.out.println ("");
	}
}
