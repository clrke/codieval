package codieval.problem;

import codieval.hasher.Hasher;

import java.util.ArrayList;
import java.util.LinkedList;

import name.fraser.neil.plaintext.diff_match_patch;

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

	public String compare(ArrayList<String> realityOutput, boolean competition) {
		String additionalErrors = "";
		String errors = "";
		String error = "";

		if(this.size != realityOutput.size())
			additionalErrors = "**Textfile line count**\n" +
				"Expected: " + this.size + "\n" +
				"Reality:  " + realityOutput.size() + "\n\n";

		for (int i = 0; i < plain1.length && i < realityOutput.size(); i++)
			if( ! plain1[i].equals(realityOutput.get(i))) {
				error = "**Error at line " + (i+1) + "**\n" +
					"Expected: \"" + plain1[i] + "\"\n" +
					"Reality:  \"" + realityOutput.get(i) + "\"\n\n";

				if( ! competition)
					errors += error;
				else
					return additionalErrors + error;
			}

		for (int i = 0; i < encrypted.length && plain1.length+i < realityOutput.size(); i++)
			if( ! encrypted[i].equals(Hasher.hash(realityOutput.get(plain1.length+i)))) {
				error = "**Error at line " + (plain1.length+i+1) + "**\n" +
					"\"" + realityOutput.get(plain1.length+i) + "\"\n\n";

				if( ! competition)
					errors += error;
				else
					return additionalErrors + error;
			}

		for (int i = 0; i < plain2.length && plain1.length+encrypted.length+i < realityOutput.size(); i++)
			if( ! plain2[i].equals(realityOutput.get(plain1.length+encrypted.length+i))) {
				error = "**Error at line " + (plain1.length+encrypted.length+i+1) + "**\n" +
					"Expected: \"" + plain2[i] + "\"\n" +
					"Reality:  \"" + realityOutput.get(plain1.length+encrypted.length+i) + "\"\n\n";

				if( ! competition)
					errors += error;
				else
					return additionalErrors + error;
			}

		if(additionalErrors.length() > 0 || errors.length() > 0)
			return additionalErrors + errors;
		else
			return "OK!";
	}

	public float getCorrectness(ArrayList<String> realityOutput) {
		float totalDiff = 0;

		for (int i = 0; i < plain1.length; i++)
			if(i < realityOutput.size())
				totalDiff += getSimilarity(plain1[i], realityOutput.get(i));
			else
				totalDiff += 0;

		for (int i = 0; i < encrypted.length; i++)
			if(plain1.length+i < realityOutput.size())
				totalDiff += getSimilarity(encrypted[i], Hasher.hash(realityOutput.get(plain1.length+i)));
			else
				totalDiff += 0;

		for (int i = 0; i < plain2.length; i++)
			if(plain1.length+encrypted.length+i < realityOutput.size())
				totalDiff += getSimilarity(plain2[i], realityOutput.get(plain1.length+encrypted.length+i));
			else
				totalDiff += 0;

		return totalDiff / size;
	}

	public float getSimilarity(String s1, String s2) {
		diff_match_patch diffGetter = new diff_match_patch();
		LinkedList differences = diffGetter.diff_main(s1, s2);
		int differencesCount = diffGetter.diff_levenshtein(differences);

		int maxLength = Math.max(Math.max(s1.length(), s2.length()), differencesCount);

		System.out.println(s1);
		System.out.println(s2);
		System.out.println(differencesCount);
		System.out.println(s1.length());
		System.out.println(100 - (float)differencesCount/(maxLength == 0? 1: maxLength) * 100);
		System.out.println("=========");

		return 100 - (float)differencesCount/(maxLength == 0? 1: maxLength) * 100;
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
