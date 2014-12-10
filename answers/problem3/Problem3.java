import java.io.*;

public class Problem3 {
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")));
			PrintWriter pw = new PrintWriter(new FileWriter(new File("output.txt")));

			for (String line = br.readLine(); line != null; line = br.readLine()) {
				int n = Integer.parseInt(line);

				boolean first = true;
				for(;n != 1; n = n % 2 == 0 ? n / 2 : 3*n+1)
					if(first) {
						first = false;
						pw.print(n);
					}
					else {
						pw.print(" "+n);
					}
				pw.println(" 1");
			}

			br.close();
			pw.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
