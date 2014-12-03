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
		return (this.done? "*" : "") + " " + this.title;
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
	public int size;

	public ExpectedOutput(ArrayList<String> expectedOutput) {
		String[] countsString = expectedOutput.get(0).split(" ");
		int[] counts = new int[countsString.length];

		for(int i = 0; i < countsString.length; i++)
			counts[i] = Integer.parseInt(countsString[i]);

		this.size = counts[0] + counts[1] + counts[2];

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

	public String compare(ArrayList<String> realityOutput) {
		String additionalErrors = "";
		String errors = "";

		if(this.size != realityOutput.size())
			additionalErrors = "**Textfile line count**\n" +
				"Expected: " + this.size + "\n" +
				"Reality:  " + realityOutput.size() + "\n\n";

		for (int i = 0; i < plain1.length; i++)
			if( ! plain1[i].equals(realityOutput.get(i))) {
				errors = "**Error at line " + (i+1) + "**\n" +
					"Expected: \"" + plain1[i] + "\"\n" +
					"Reality:  \"" + realityOutput.get(i) + "\"";

				return additionalErrors + errors;
			}

		for (int i = 0; i < encrypted.length; i++)
			if( ! encrypted[i].equals(Hasher.hash(realityOutput.get(plain1.length+i)))) {
				errors = "**Error at line " + (plain1.length+i+1) + "**\n" +
					"\"" + realityOutput.get(plain1.length+i) + "\"";

				return additionalErrors + errors;
			}

		for (int i = 0; i < plain2.length; i++)
			if( ! plain2[i].equals(realityOutput.get(plain1.length+encrypted.length+i))) {
				errors = "**Error at line " + (plain1.length+encrypted.length+i+1) + "**\n" +
					"Expected: \"" + plain2[i] + "\"\n" +
					"Reality:  \"" + realityOutput.get(plain1.length+encrypted.length+i) + "\"";

				return additionalErrors + errors;
			}

		return additionalErrors + "OK!";
	}
}
