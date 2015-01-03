package codieval.evaluators;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.event.*;

import java.nio.charset.*;
import java.nio.file.*;

import codieval.exceptions.CompilationErrorException;
import codieval.problem.*;
import codieval.ucompiler.UniversalCompiler;
import codieval.hasher.Hasher;

public class Evaluator2 extends JFrame {

Font myFont1 = new Font("Verdana", Font.BOLD, 20);
Font myFont2 = new Font("Verdana", Font.BOLD, 16);
Font myFont3 = new Font("SansSerif", Font.BOLD, 13);
Font myFont4 = new Font("Consolas", Font.BOLD, 14);

JButton btnSampleInputFile = new JButton("Export sample input file");
JButton btnInputFile = new JButton("Export input file");
JButton btnOutputFile = new JButton("Import source code");
JButton btnSubmit = new JButton("Submit");
JButton btnRequirements = new JButton("View Requirements");

final JTextArea txtEvaluation = new JTextArea(20, 20);
final JTextField txtOutputFile = new JTextField(20);
final JTextArea txtDesc = new JTextArea(20, 20);

ArrayList problems;
JList listProblems = new JList();

JScrollPane scrlEvaluation = new JScrollPane(txtEvaluation);
JScrollPane scrlProblems;
JScrollPane scrlDesc = new JScrollPane(txtDesc, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

JPanel pnlDescOptions = new JPanel();
JPanel pnlDesc = new JPanel();
JPanel pnlOptions = new JPanel();
JPanel pnlSubmit = new JPanel();
JPanel pnlProblems = new JPanel();
JPanel pnlTimer = new JPanel();
JPanel pnlWest = new JPanel();
JPanel pnlNorth = new JPanel();
JPanel pnlCentr = new JPanel();
JPanel pnlSouth = new JPanel();
JPanel pnlEvaluation = new JPanel();
JPanel pnlLanguage = new JPanel();
JPanel pnlCompiler = new JPanel();

JFileChooser chooser = new JFileChooser();
private File selectedFile;

JLabel lblLanguage = new JLabel("Choose your language: ");
JLabel lblTime = new JLabel("PROBLEMS [ Timer: " + getTimeForHumans(0) + " ]");

JComboBox cmbLanguage = new JComboBox(new String[] {"C", "Java", "C#", "Python"});

JLabel lblCompiler = new JLabel(UniversalCompiler.getCompiler("C"));

int seconds = 0;

Timer timer = new Timer(1000, new ActionListener() {
	public void actionPerformed(ActionEvent ae) {
		seconds++;
		lblTime.setText("PROBLEMS [ Timer: " + getTimeForHumans(seconds) + " ]");
	}
});

public Evaluator2() {
	super("CODE EVALUATOR");

	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

	setSize(1000, 600);
	setResizable(true);

	setLayout(new BorderLayout());

	txtEvaluation.setEditable(false);
	txtDesc.setEditable(false);
	txtOutputFile.setForeground(Color.BLUE);
	txtOutputFile.setEditable(false);

	pnlWest.setLayout(new BorderLayout());
	pnlProblems.setLayout(new GridLayout(1, 1));
	pnlTimer.setLayout(new FlowLayout());
	pnlLanguage.setLayout(new FlowLayout());
	pnlNorth.setLayout(new GridLayout(1, 1));
	pnlCentr.setLayout(new GridLayout(2, 1));
	pnlSubmit.setLayout(new FlowLayout());
	pnlDescOptions.setLayout(new BorderLayout());
	pnlDesc.setLayout(new GridLayout(1, 1));
	pnlOptions.setLayout(new FlowLayout());
	pnlEvaluation.setLayout(new GridLayout(1, 1));
	pnlSouth.setLayout(new BorderLayout());

	problems = new ArrayList();

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

	listProblems = new JList(problems.toArray());
	listProblems.setSelectedIndex(0);

	txtDesc.setLineWrap(true);
	txtDesc.setText(((Problem)listProblems.getSelectedValue()).getDataString());
	txtDesc.setCaretPosition(0);

	scrlProblems = new JScrollPane(listProblems);

	pnlProblems.add(scrlProblems);

	pnlTimer.add(lblTime);

	pnlLanguage.add(lblLanguage);
	pnlLanguage.add(cmbLanguage);

	pnlWest.add(pnlTimer, BorderLayout.NORTH);
	pnlWest.add(pnlProblems, BorderLayout.CENTER);
	pnlWest.add(pnlLanguage, BorderLayout.SOUTH);

	btnSubmit.setEnabled(false);

	pnlSubmit.add(btnOutputFile);
	pnlSubmit.add(txtOutputFile);
	pnlSubmit.add(btnSubmit);

	pnlNorth.add(pnlSubmit);

	pnlDesc.add(scrlDesc);

	pnlEvaluation.add(scrlEvaluation);

	pnlCompiler.add(lblCompiler);

	pnlSouth.add(pnlNorth, BorderLayout.NORTH);
	pnlSouth.add(pnlEvaluation, BorderLayout.CENTER);
	pnlSouth.add(pnlCompiler, BorderLayout.SOUTH);

	pnlOptions.add(btnRequirements);
	pnlOptions.add(btnSampleInputFile);
	pnlOptions.add(btnInputFile);

	pnlDescOptions.add(pnlDesc, BorderLayout.CENTER);
	pnlDescOptions.add(pnlOptions, BorderLayout.SOUTH);

	pnlCentr.add(pnlDescOptions);
	pnlCentr.add(pnlSouth);

	add(pnlWest, BorderLayout.WEST);
	add(pnlCentr, BorderLayout.CENTER);

	pnlProblems.setBackground(Color.LIGHT_GRAY);
	pnlNorth.setBackground(Color.LIGHT_GRAY);
	pnlCentr.setBackground(Color.LIGHT_GRAY);
	pnlEvaluation.setBackground(Color.LIGHT_GRAY);

	txtDesc.setFont(myFont4);
	txtEvaluation.setFont(myFont4);
	txtOutputFile.setFont(myFont3);

	btnSampleInputFile.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			chooser.setSelectedFile(new File("sample_input.txt"));
			chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
				public boolean accept(File f) {
					return f.getName().toLowerCase().endsWith(".txt")||f.isDirectory();
				}
				public String getDescription() {
					return "Textfile";
				}
			});
			if(chooser.showSaveDialog(Evaluator2.this) == JFileChooser.APPROVE_OPTION) {
				try {
					PrintWriter writer = new PrintWriter(new FileWriter(chooser.getSelectedFile()));
					writer.println(((Problem)listProblems.getSelectedValue()).sampleInput);
					writer.close();
				}
				catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	});
	btnInputFile.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			chooser.setSelectedFile(new File("input.txt"));
			chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
				public boolean accept(File f) {
					return f.getName().toLowerCase().endsWith(".txt")||f.isDirectory();
				}
				public String getDescription() {
					return "Textfile";
				}
			});
			if(chooser.showSaveDialog(Evaluator2.this) == JFileChooser.APPROVE_OPTION) {
				try {
					Files.copy(((Problem)listProblems.getSelectedValue()).inputFilePath, new FileOutputStream(chooser.getSelectedFile()));
				}
				catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	});
	btnOutputFile.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent ae) {
			chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
				public boolean accept(File f) {
					return true;
				}

				public String getDescription() {
					return cmbLanguage.getSelectedItem() + " source codes";
				}
			});

			int option = chooser.showOpenDialog(Evaluator2.this);
			if (option == JFileChooser.APPROVE_OPTION) {
				selectedFile = chooser.getSelectedFile();
				txtOutputFile.setText(selectedFile.getAbsolutePath());
				btnSubmit.setEnabled(true);
			}
		}
	});

	btnRequirements.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			setFocusableWindowState(false);

			Problem problem = ((Problem)listProblems.getSelectedValue());
			try {
				new Requirements(problem.title, "Input:\n" +  new String(Files.readAllBytes(problem.inputFilePath), StandardCharsets.UTF_8), "Output:\n" + problem.expectedOutput.toString());
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
			setFocusableWindowState(true);
		}
	});

	cmbLanguage.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			String compilerVersion = UniversalCompiler.getCompiler(cmbLanguage.getSelectedItem().toString());
			lblCompiler.setText(compilerVersion);

			if(compilerVersion.startsWith("Error"))
				btnSubmit.setEnabled(false);
			else if(selectedFile != null)
				btnSubmit.setEnabled(true);
		}
	});

	listProblems.addListSelectionListener(new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
			txtDesc.setText(((Problem)listProblems.getSelectedValue()).getDataString());
			txtDesc.setCaretPosition(0);
		}
	});

	btnSubmit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ae) {

			try {
				ArrayList<String> realityLines = new ArrayList<String>();

				String dir = selectedFile.getParent();
				String programmingLanguage = cmbLanguage.getSelectedItem().toString();
				String filename = selectedFile.getName();

				for (String line : UniversalCompiler.compileAndRun(dir, programmingLanguage, filename).split("\n")) {
					realityLines.add(line);
				}

				Problem currentProblem = (Problem)listProblems.getSelectedValue();
				String result = currentProblem.expectedOutput.compare(realityLines);
				txtEvaluation.setText(result);
				if(result.equals("OK!") && ! currentProblem.done) {
					seconds = 0;
					lblTime.setText("PROBLEMS [ Timer: " + getTimeForHumans(seconds) + " ]");
					currentProblem.done = true;
					listProblems.repaint();
				}
			}
			catch(IOException e) {
				txtEvaluation.setText("Error: Compiler not found");
				System.out.println(e.getMessage());
			}
			catch(CompilationErrorException error) {
				txtEvaluation.setText(error.toString());
			}
		}
	});

	timer.start();
	}

	public String getTimeForHumans(int seconds) {
		int hours = seconds / 3600;
		int minutes = (seconds % 3600) / 60;
		seconds = (seconds % 60);

		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
	public static void main(String args[]) {
		Evaluator2 sfc = new Evaluator2();
		sfc.setVisible(true);
	}
}
