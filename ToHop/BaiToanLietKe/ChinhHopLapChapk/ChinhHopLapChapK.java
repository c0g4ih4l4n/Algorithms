

// problem :
/**
	* Liet Ke cac chinh hop chap k cua n phan tu
	* input: n va k
	* output: Cac chinh hop chap k cua n phan tu
	* process: phuong phap sinh (thu tu tu dien)
**/

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Exception;

public class ChinhHopLapChapK {

	static final String INPUT = "INPUT";
	static final String OUTPUT = "OUTPUT";
	
	public static void main (String[] args) {
		
		int[] input = IO.getInput (ChinhHopLapChapK.INPUT);
		IO.deleteOld (ChinhHopLapChapK.OUTPUT);
		int n = input[0];
		int k = input[1];
		int[] a = new int[k];
		int i = k - 1;
		
		IO.writeOut (ChinhHopLapChapK.OUTPUT, a);
		do {
			i = k - 1;
			while (i >= 0 && a[i] == n) {i--;}
			if (i < 0) break;
			a[i]++;
			for (int j = i + 1; j < k; j++) {
				a[j] = 0;
			}
			IO.writeOut (ChinhHopLapChapK.OUTPUT, a);
		} while (true);
	}
	
	public static void print (int[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print (a[i]);
		}
		System.out.println ();
	}
	

}

class IO {
	public static int[] getInput (String fileName) {
		int[] result = new int[2];
		try (FileReader fr = new FileReader (fileName)) {
			
			result[0] = Character.getNumericValue (fr.read());
			fr.read();
			result[1] = Character.getNumericValue (fr.read());
			fr.close();	
		} catch (FileNotFoundException e) {
			System.out.println ("File Not Found ...");
			System.exit(0);
		} catch (IOException e) {
			System.out.println ("Unable to process ...");
			System.exit (0);
		}
		return result;
	}
	
	public static void writeOut (String fileName, int[] a) {
		try (FileWriter fw = new FileWriter (fileName, true);
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter writer = new PrintWriter (bw))
		{
			String line = "";
			int length = a.length;
			for (int i = 0; i < length; i++) {
				line += a[i];
			}
			writer.println (line);
			writer.close();
		} catch (IOException e) {
			System.out.println ("Unable to process ...");
			System.exit (0);
		}
	
	}
	
	public static void deleteOld (String fileName) {
		try {
			File file = new File (fileName);
			file.delete();
		} catch (Exception e) {
			System.out.println ("Cannot Delete Old Output ..");
			System.exit (0);
		}
	}

}
