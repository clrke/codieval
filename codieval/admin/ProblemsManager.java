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

	GridLayout lytProblems = new GridLayout(1, 1);

	JPanel pnlSearch = new JPanel(new FlowLayout());
	JPanel pnlProblems = new JPanel(lytProblems);
	JPanel pnlAddProblem = new JPanel(new FlowLayout());

	JTextField txtSearch = new JTextField(20);
	JButton btnSearch = new JButton("Search");

	JScrollPane scrlProblems = new JScrollPane(pnlProblems);

	public ProblemsManager() {
		super("Problems Manager");

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowClosing(WindowEvent e){
				int result = JOptionPane.showConfirmDialog(null, "Save changes before exiting?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

				if(result == JOptionPane.YES_OPTION){
					System.out.println("Saving...");
					System.exit(0);
				} else if(result == JOptionPane.NO_OPTION) {
					System.exit(0);
				} else {
	               //Do nothing
				}
		 	}
			public void windowOpened(WindowEvent e) {System.out.println();}
			public void windowClosed(WindowEvent e) {}
		});

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

		lytProblems.setRows(problems.size());

		for(Problem problem : problems)
			pnlProblems.add(new ProblemPanel(problem));

		txtSearch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				search(txtSearch.getText());
			}
		});

		btnSearch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				search(txtSearch.getText());
			}
		});

		pnlSearch.add(txtSearch);
		pnlSearch.add(btnSearch);

		pnlAddProblem.add(new JButton("New Problem"));

		add(pnlSearch, BorderLayout.NORTH);
		add(scrlProblems, BorderLayout.CENTER);
		add(pnlAddProblem, BorderLayout.SOUTH);

		setSize(400, 400);
		setResizable(true);
		setVisible(true);
	}

	public void search(String query) {
		 ArrayList<Problem> problemsSearched = new ArrayList<Problem>();
		for (Problem problem : problems) {
			if(problem.title.contains(query))
				problemsSearched.add(problem);
		}

		pnlProblems.removeAll();

		lytProblems.setRows(problemsSearched.size());

		for(Problem problem : problemsSearched)
			pnlProblems.add(new ProblemPanel(problem));

		pnlProblems.updateUI();
	}
	public static void main(String[] args) {
		new ProblemsManager();
	}
}
