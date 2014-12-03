import java.io.*;

public class Problem1 {
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")));
			PrintWriter pw = new PrintWriter(new FileWriter(new File("output.txt")));

			for (String line = br.readLine(); line != null; line = br.readLine())
				pw.println("Hello " + line + "!");

			br.close();
			pw.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
