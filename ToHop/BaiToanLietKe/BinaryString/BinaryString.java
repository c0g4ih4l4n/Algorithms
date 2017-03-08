import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.File;

public class BinaryString {

	static final String INPUT_FILE = "BSTR.INP";
	static final String OUTPUT_FILE = "BSTR.OUT";

	public static void main(String[] args) {
		// get input from file and delete old output file
		int n = BinaryString.getInput (BinaryString.INPUT_FILE);
		if (n == 0) {
			System.out.println ("N = 0");
			System.exit(0);
		}
		BinaryString.deleteOldFile (BinaryString.OUTPUT_FILE);
		
		int[] a = new int[n];
		
		int i = 0;
		
		// Khoi tao mang ban dau
		for (i = 0; i < n; i++) {
			a[i] = 0;
		}
		
		// in phan tu dau tien
		BinaryString.writeOutput (BinaryString.OUTPUT_FILE , a , n);
		
		// Vong lap in tat ca phan tu
		while (true) {
			
			//khoi tao i va tim vi tri 0 dau tien tu cuoi
			i = n - 1;
			while (i >= 0) {
				if (a[i] == 0)
					break;
				i--; 
			}
			
			// neu khong tim thay thi ket thuc
			if (i < 0)
				break;
			
			// tim thay
			// set phan tu hien tai = 1
			// thay the cac phan tu phia sau = 0
			a[i] = 1;
			i ++;
			while (i < n) {	
				a[i] = 0;
				i++;
			}
			
			// in phan tu vua sinh
			BinaryString.writeOutput (BinaryString.OUTPUT_FILE , a , n);	
		}
	}
	
	public static int getInput (String fileName) {
		int n = 0;
		
		try {
			FileReader fileReader = new FileReader(fileName);
			n = Character.getNumericValue (fileReader.read ());
			fileReader.close ();
		} catch (FileNotFoundException e) {
			System.out.println ("Unable to open file " + fileName);
		} catch (IOException e) {
			System.out.println ("Unable to process " + fileName);
		}
		
		return n;
	}
	
	public static void deleteOldFile (String fileName) {
		try {
			File file = new File (fileName);
			file.delete ();
		} catch (Exception e) {
		}
	}
	
	public static void writeOutput (String fileName, int[] a , int n) {
	
		try (
			FileWriter fileWriter = new FileWriter (fileName , true);
			BufferedWriter bw = new BufferedWriter (fileWriter);
			PrintWriter writer = new PrintWriter (bw))
		{
			String line = "";
			for (int i = 0 ; i < n ; i++) {
				line += " " + a[i];
			}
			writer.println (line);
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println ("Unable to open file " + fileName);
		} catch (IOException e) {
			System.out.println ("Unable to process " + fileName);
		}
	}
	
	public static void print (int[] a , int n) {
		for (int i = 0 ; i < n ; i++) {
			System.out.print (" " + a[i]);
		}
		System.out.println ("");
	}
}
