import java.io.*;
import java.math.BigInteger;

public class Hasher {
	public static void main(String[] args) throws Exception {
		if(args.length != 5) {
			System.out.print("Incorrect number of arguments.\nUsage: java Hasher <file to be hashed> "+
				"<output file> <plain line count> <encrypted line cout> <plain line count>");
			return;
		}
		else {
			BufferedReader br 	= new BufferedReader(new FileReader(new File(args[0])));
			PrintWriter pw		= new PrintWriter(new FileWriter(new File(args[1])));

			int plainLineCount1 = Integer.parseInt(args[2]);
			int plainLineCount2 = Integer.parseInt(args[4]);
			int encrptLineCount = Integer.parseInt(args[3]);

			for(int i = 0; i < plainLineCount1; i++)
				pw.println(br.readLine());
			for(int i = 0; i < encrptLineCount; i++)
				pw.println(hash(br.readLine()));
			for(int i = 0; i < plainLineCount2; i++)
				pw.println(br.readLine());

			br.close();
			pw.close();
		}
	}
	public static String hash(String str) {
		BigInteger hash = new BigInteger("7");
		for (int i = 0; i < str.length(); i++) {
			hash = hash.multiply(new BigInteger("31")).add(new BigInteger(((int)str.charAt(i))+""));
		}
		return hash.toString(16);
	}
}
