import java.io.*;
import java.math.BigInteger;

public class Problem4 {
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")));

			for (String line = br.readLine(); line != null; line = br.readLine()) {
				int n = Integer.parseInt(line);

				BigInteger x = new BigInteger("0");
				BigInteger y = new BigInteger("1");

				for(int i = 1; i < n; i++) {
					BigInteger z = x.add(y);
					x = y;
					y = z;
				}
				System.out.println(y.toString().length());
			}

			br.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
