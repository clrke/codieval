import java.util.ArrayList;

public class Problem {
	public String title;
	public String description;
	public String sampleInput;
	public String sampleOutput;
	public ExpectedOutput expectedOutput;
	public boolean done;

	public Problem(ArrayList<String> fileContents) {
		int i = 0;
		for (; ! fileContents.get(i).equals("====="); i++)
			this.title = this.title != null? this.title + "\n" + fileContents.get(i) : fileContents.get(i);

		for (i++; ! fileContents.get(i).equals("====="); i++)
			this.description = this.description != null? this.description + "\n" + fileContents.get(i) : fileContents.get(i);

		for (i++; ! fileContents.get(i).equals("====="); i++)
			this.sampleInput = this.sampleInput != null? this.sampleInput + "\n" + fileContents.get(i) : fileContents.get(i);

		for (i++; ! fileContents.get(i).equals("====="); i++)
			this.sampleOutput = this.sampleOutput != null? this.sampleOutput + "\n" + fileContents.get(i) : fileContents.get(i);

		ArrayList<String> expectedOutput = new ArrayList<String>();

		for (i++; i < fileContents.size(); i++)
			expectedOutput.add(fileContents.get(i));

		this.expectedOutput = new ExpectedOutput(expectedOutput);

		this.done = false;
	}

	public Problem(String title, String description, String sampleInput, String sampleOutput) {
		this.title = title;
		this.description = description;
		this.sampleInput = sampleInput;
		this.sampleOutput = sampleOutput;
		this.done = false;
	}

	@Override
	public String toString() {
		return this.title;
	}

	public String getDataString() {
		return 	"**Title**\n" + this.title + "\n\n" +
				"**Description**\n" + this.description + "\n\n" +
				"**Sample Input**\n" + this.sampleInput + "\n\n" +
				"**Sample Output**\n" + this.sampleOutput;
	}
}

class ExpectedOutput {
	public String[] plain1;
	public String[] encrypted;
	public String[] plain2;

	public ExpectedOutput(ArrayList<String> expectedOutput) {
		String[] countsString = expectedOutput.get(0).split(" ");
		int[] counts = new int[countsString.length];

		for(int i = 0; i < countsString.length; i++)
			counts[i] = Integer.parseInt(countsString[i]);

		plain1 = new String[counts[0]];
		encrypted = new String[counts[1]];
		plain2 = new String[counts[2]];

		for (int i = 0; i < counts[0]; i++)
			plain1[i] = expectedOutput.get(i+1);

		for (int i = 0; i < counts[1]; i++)
			encrypted[i] = expectedOutput.get(i+counts[0]+1);

		for (int i = 0; i < counts[2]; i++)
			plain2[i] = expectedOutput.get(i+counts[0]+counts[1]+1);
	}
}
