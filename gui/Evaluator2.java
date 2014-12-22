import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.event.*;

import java.nio.charset.*;
import java.nio.file.*;

public class Evaluator2 extends JFrame {

Font myFont1 = new Font("Verdana", Font.BOLD, 20);
Font myFont2 = new Font("Verdana", Font.BOLD, 16);
Font myFont3 = new Font("SansSerif", Font.BOLD, 13);
Font myFont4 = new Font("Consolas", Font.BOLD, 14);

JButton btnSampleInputFile = new JButton("Export sample input file");
JButton btnInputFile = new JButton("Export input file");
JButton btnOutputFile = new JButton("Import output file");
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

JFileChooser chooser = new JFileChooser();
private File selectedFile;

JLabel lblTime = new JLabel("PROBLEMS [ Timer: " + getTimeForHumans(0) + " ]");

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
	pnlNorth.setLayout(new GridLayout(1, 1));
	pnlCentr.setLayout(new GridLayout(2, 1));
	pnlSubmit.setLayout(new FlowLayout());
	pnlDescOptions.setLayout(new BorderLayout());
	pnlDesc.setLayout(new GridLayout(1, 1));
	pnlOptions.setLayout(new FlowLayout());
	pnlEvaluation.setLayout(new GridLayout(1, 1));
	pnlSouth.setLayout(new BorderLayout());

	problems = new ArrayList();

	File problemsDirectory = new File("..\\problems");
	for (String name : problemsDirectory.list()) {
		File file = new File("..\\problems\\"+name);
		if(file.isDirectory()) {
			try {
				ArrayList<String> fileContents = new ArrayList<String>();

				BufferedReader br = new BufferedReader(new FileReader(new File("..\\problems\\"+name+"\\desc.germ")));

				for(String line = br.readLine(); line != null; line = br.readLine())
					fileContents.add(line);

				br.close();
				problems.add(new Problem(FileSystems.getDefault().getPath("..\\problems\\"+name, "input.txt"), fileContents));
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

	pnlWest.add(pnlProblems, BorderLayout.CENTER);
	pnlWest.add(pnlTimer, BorderLayout.NORTH);

	pnlSubmit.add(btnOutputFile);
	pnlSubmit.add(txtOutputFile);
	pnlSubmit.add(btnSubmit);

	pnlNorth.add(pnlSubmit);

	pnlDesc.add(scrlDesc);

	pnlEvaluation.add(scrlEvaluation);

	pnlSouth.add(pnlNorth, BorderLayout.NORTH);
	pnlSouth.add(pnlEvaluation, BorderLayout.CENTER);

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
					return f.getName().toLowerCase().endsWith(".txt")||f.isDirectory();
				}

				public String getDescription() {
					return "Textfiles";
				}
			});

			int option = chooser.showOpenDialog(Evaluator2.this);
			if (option == JFileChooser.APPROVE_OPTION) {
				File sf = chooser.getSelectedFile();
				txtOutputFile.setText(sf.getName());
				selectedFile = chooser.getSelectedFile();
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

	listProblems.addListSelectionListener(new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
			txtDesc.setText(((Problem)listProblems.getSelectedValue()).getDataString());
			txtDesc.setCaretPosition(0);
		}
	});

	btnSubmit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ae) {

			try{
				String strLine;
				FileInputStream in = new FileInputStream(selectedFile);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));

				ArrayList<String> realityLines = new ArrayList<String>();
				while ((strLine = br.readLine()) != null) {
					realityLines.add(strLine);
				}

				br.close();
				Problem currentProblem = (Problem)listProblems.getSelectedValue();
				String result = currentProblem.expectedOutput.compare(realityLines);
				txtEvaluation.setText(result);
				if(result.equals("OK!") && ! currentProblem.done) {
					seconds = 0;
					lblTime.setText("PROBLEMS [ Timer: " + getTimeForHumans(seconds) + " ]");
					currentProblem.done = true;
					listProblems.repaint();
				}

			}catch(Exception e){
				System.out.println("Please see\t" + e);
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
