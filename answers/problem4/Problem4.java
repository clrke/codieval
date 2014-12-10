import java.io.*;
import java.math.BigInteger;

public class Problem4 {
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")));
			PrintWriter pw = new PrintWriter(new FileWriter(new File("output.txt")));

			for (String line = br.readLine(); line != null; line = br.readLine()) {
				int n = Integer.parseInt(line);

				BigInteger x = new BigInteger("0");
				BigInteger y = new BigInteger("1");

				for(int i = 1; i < n; i++) {
					BigInteger z = x.add(y);
					x = y;
					y = z;
				}
				pw.println(y.toString().length());
			}

			br.close();
			pw.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
