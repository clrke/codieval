import java.io.*;
import java.lang.Math;

public class Problem2 {
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")));

			for (String line = br.readLine(); line != null; line = br.readLine()) {
				int n = Integer.parseInt(line);

				for(int row = -n+1; row < n; row++) {
					for(int i = 0; i < Math.abs(row); i++)
						System.out.print(" ");
					for(int i = 0; i < n-Math.abs(row); i++)
						if(i == 0)
							System.out.print("*");
						else
							System.out.print(" *");

					System.out.println();
				}

			}

			br.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
