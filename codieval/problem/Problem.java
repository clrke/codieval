package codieval.problem;

import java.util.ArrayList;
import java.io.*;
import java.nio.file.*;

import codieval.complexity.BigO;

public class Problem {
	public String title;
	public String description;
	public String sampleInput;
	public String sampleOutput;
	public String input;
	public String output;
	public Path inputFilePath;
	public ExpectedOutput expectedOutput;
	public boolean done;
	public float correctness;
	public float correctnessGrade;
	public float timeComplexityGrade;
	public float grade;
	public float equivalentGrade;
	public BigO bigO;
	public boolean enabled = false;

	public Problem(Path inputFilePath, ArrayList<String> fileContents) {
		this.inputFilePath = inputFilePath;
		this.bigO = null;

		int i = 0;
		for (; ! fileContents.get(i).equals("====="); i++) {
			String[] infoSplit = fileContents.get(i).split("#####");

			this.title = this.title != null ?
				this.title + "\n" + infoSplit[0] : infoSplit[0];

			if(infoSplit.length > 1) {
				this.bigO = new BigO(infoSplit[1]);
			}
		}

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
		this.correctnessGrade = -1;
		this.timeComplexityGrade = -1;
		this.grade = -1;
	}

	public Problem(String title, String description, String sampleInput, String sampleOutput) {
		this.title = title;
		this.description = description;
		this.sampleInput = sampleInput;
		this.sampleOutput = sampleOutput;
		this.inputFilePath = null;
		this.expectedOutput = null;
		this.done = false;
		this.correctness = -1;
		this.correctnessGrade = -1;
		this.timeComplexityGrade = -1;
		this.grade = -1;
	}

	@Override
	public String toString() {
		return " " + (grade != -1? String.format("%.2f ", equivalentGrade) : "") + (this.done? "* " : "") + this.title;
	}

	public String getDataString() {
		return 	"**Title**\n" + this.title + "\n\n" +
				"**Description**\n" + this.description + "\n\n" +
				"**Sample Input**\n" + this.sampleInput + "\n\n" +
				"**Sample Output**\n" + this.sampleOutput;
	}

	public void setCorrectness(float correctness) {
		this.correctness = correctness;
		this.correctnessGrade = getEquivalentGrade(correctness);
	}

	public void setTimeComplexityGrade(BigO bigO2) {
		if(bigO == null) {
			this.timeComplexityGrade = 100;
			return;
		}
		int value1 = bigO.getValue(2);
		int value2 = bigO2.getValue(2);

		if(value1 > value2) {
			this.timeComplexityGrade = 100;
		} else {
			this.timeComplexityGrade = (float)value1*100/value2;
		}
	}

	public void computeGrade() {
		this.grade = (this.correctness*85 + this.timeComplexityGrade*15)/100;
		this.equivalentGrade = getEquivalentGrade(grade);
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
