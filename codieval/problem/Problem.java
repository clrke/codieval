package codieval.problem;

import java.util.ArrayList;
import java.io.*;
import java.nio.file.*;

public class Problem {
	public String title;
	public String description;
	public String sampleInput;
	public String sampleOutput;
	public Path inputFilePath;
	public ExpectedOutput expectedOutput;
	public boolean done;
	public float correctness;
	public float grade;

	public Problem(Path inputFilePath, ArrayList<String> fileContents) {
		this.inputFilePath = inputFilePath;

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
		this.correctness = -1;
		this.grade = -1;
	}

	public Problem(String title, String description, String sampleInput, String sampleOutput) {
		this.title = title;
		this.description = description;
		this.sampleInput = sampleInput;
		this.sampleOutput = sampleOutput;
		this.done = false;
		this.correctness = -1;
		this.grade = -1;
	}

	@Override
	public String toString() {
		return " " + (grade != -1? String.format("%.2f ", grade) : "") + (this.done? "* " : "") + this.title;
	}

	public String getDataString() {
		return 	"**Title**\n" + this.title + "\n\n" +
				"**Description**\n" + this.description + "\n\n" +
				"**Sample Input**\n" + this.sampleInput + "\n\n" +
				"**Sample Output**\n" + this.sampleOutput;
	}

	public void setCorrectness(float correctness) {
		this.correctness = correctness;
		this.grade = getEquivalentGrade(correctness);
	}

	public float getEquivalentGrade(float correctness) {
		if(correctness < 75)
			return 5.00f;
		if(correctness < 76)
			return 3.00f;
		if(correctness < 79)
			return 2.75f;
		if(correctness < 82)
			return 2.5f;
		if(correctness < 85)
			return 2.25f;
		if(correctness < 88)
			return 2f;
		if(correctness < 91)
			return 1.75f;
		if(correctness < 94)
			return 1.5f;
		if(correctness < 97)
			return 1.25f;
		else
			return 1f;
	}
}
