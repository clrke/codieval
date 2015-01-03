package codieval.admin;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.event.*;

import codieval.problem.Problem;

import java.nio.file.*;

public class ProblemsManager extends JFrame {

	ArrayList<Problem> problems;

	JList<Problem> listProblems;

	JPanel pnlSearch = new JPanel(new FlowLayout());
	JPanel pnlProblems = new JPanel(new GridLayout(9, 1));
	JPanel pnlAddProblem = new JPanel(new FlowLayout());

	JScrollPane scrlProblems = new JScrollPane(pnlProblems);

	public ProblemsManager() {
		super("Problems Manager");

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setLayout(new BorderLayout());

		problems = new ArrayList<Problem>();

		File problemsDirectory = new File("problems/");
		for (String name : problemsDirectory.list()) {
			File file = new File("problems/"+name);
			if(file.isDirectory()) {
				try {
					ArrayList<String> fileContents = new ArrayList<String>();

					BufferedReader br = new BufferedReader(new FileReader(new File("problems/"+name+"/desc.germ")));

					for(String line = br.readLine(); line != null; line = br.readLine())
						fileContents.add(line);

					br.close();
					problems.add(new Problem(FileSystems.getDefault().getPath("problems/"+name, "input.txt"), fileContents));
				}
				catch(IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}

		Problem[] problemsArray = new Problem[problems.size()];
		problemsArray = problems.toArray(problemsArray);
		listProblems = new JList<Problem>(problemsArray);

		listProblems.setSelectedIndex(0);

		for(Problem problem : problems)
			pnlProblems.add(new ProblemPanel(problem));

		pnlSearch.add(new JTextField(20));
		pnlSearch.add(new JButton("Search"));

		pnlAddProblem.add(new JButton("New Problem"));

		add(pnlSearch, BorderLayout.NORTH);
		add(scrlProblems, BorderLayout.CENTER);
		add(pnlAddProblem, BorderLayout.SOUTH);

		setSize(400, 400);
		setResizable(true);
		setVisible(true);
	}
	public static void main(String[] args) {
		new ProblemsManager();
	}
}
