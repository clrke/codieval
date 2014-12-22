import java.io.*;

public class Problem1 {
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")));

			for (String line = br.readLine(); line != null; line = br.readLine())
				System.out.println("Hello " + line + "!");

			br.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
