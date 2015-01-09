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
	}

	public Problem(String title, String description, String sampleInput, String sampleOutput) {
		this.title = title;
		this.description = description;
		this.sampleInput = sampleInput;
		this.sampleOutput = sampleOutput;
		this.done = false;
		this.correctness = -1;
	}

	@Override
	public String toString() {
		return " " + (correctness != -1? String.format("%.2f%% ", correctness) : "") + (this.done? "* " : "") + this.title;
	}

	public String getDataString() {
		return 	"**Title**\n" + this.title + "\n\n" +
				"**Description**\n" + this.description + "\n\n" +
				"**Sample Input**\n" + this.sampleInput + "\n\n" +
				"**Sample Output**\n" + this.sampleOutput;
	}
}
