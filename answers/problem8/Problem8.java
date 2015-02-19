import java.io.*;
import java.util.ArrayList;
import java.math.BigInteger;

public class Problem8
{
	public Problem8(File file)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));

			int T = Integer.parseInt(reader.readLine());

			for(int t = 0; t < T; t++) {
				String input = reader.readLine();
				System.out.println(String.format("Case #%d: %s", t+1, minimumSeconds(input).toString()));
			}

			reader.close();
		}
		catch(Exception error)
		{
			error.printStackTrace();
		}
	}

	public BigInteger minimumSeconds(String input)
	{
		ArrayList<Integer> digitRepresentation = new ArrayList<Integer>();
		ArrayList<Character> digits = new ArrayList<Character>();

		for(char c : input.toCharArray())
		{
			if(!digits.contains(c))
			{
				if(digits.size() == 0)
					digitRepresentation.add(1);
				else if(digits.size() == 1)
					digitRepresentation.add(0);
				else digitRepresentation.add(digits.size());
				digits.add(c);
			}
			else
			{
				for(int i = 0; i < digits.size(); i++)
					if(digits.get(i) == c)
					{
						if(i == 0)
							digitRepresentation.add(1);
						else if(i == 1)
							digitRepresentation.add(0);
						else digitRepresentation.add(i);
						break;
					}
			}
		}

		BigInteger value = BigInteger.ZERO;
		int place = 0;
		for(int i = digitRepresentation.size()-1; i >= 0; i--)
		{
			value = value.add(new BigInteger(new BigInteger(digitRepresentation.get(i)+"").multiply(new BigInteger(digits.size()+"").pow(place))+""));
			place++;
		}

		return value;
	}

	public static void main(String[] args)
	{
		new Problem8(new File("input.txt"));
	}
}
