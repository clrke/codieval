package codieval.problem;

import codieval.hasher.Hasher;

import java.util.ArrayList;

public class ExpectedOutput {
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

	@Override
	public String toString() {
		String str = "";
		for (String plain : plain1) {
			str += plain + "\n";
		}
		str += 	" .\n .\n .\n" +
				"=================================\n" +
				"=================================\n" +
				"============"+encrypted.length+" lines OMITTED=====\n" +
				"=================================\n" +
				"=================================\n" +
				" .\n .\n .\n";
		for (String plain : plain2) {
			str += plain + "\n";
		}
		return str;
	}
}