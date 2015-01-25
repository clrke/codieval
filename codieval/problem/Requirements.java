package codieval.problem;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import java.nio.charset.*;
import java.nio.file.*;

public class Requirements extends JFrame {
	public Requirements(String title, String input, String output) {
		super(title);

		this.setLayout(new GridLayout(1, 2));

		Font consolas14 = new Font("Consolas", Font.BOLD, 14);

		JTextArea txtInput = new JTextArea(input);
		txtInput.setEditable(false);
		txtInput.setFont(consolas14);

		JTextArea txtOutput = new JTextArea(output);
		txtOutput.setEditable(false);
		txtOutput.setFont(consolas14);

		JScrollPane scrlInput = new JScrollPane(txtInput);
		JScrollPane scrlOutput = new JScrollPane(txtOutput);

		JPanel pnlInput = new JPanel();
		pnlInput.setLayout(new BorderLayout());
		pnlInput.add(scrlInput);

		JPanel pnlOutput = new JPanel();
		pnlOutput.setLayout(new BorderLayout());
		pnlOutput.add(scrlOutput);

		this.add(pnlInput);
		this.add(pnlOutput);

		this.setSize(800, 600);
		this.setResizable(true);
		this.setVisible(true);
	}
	public Requirements(Problem problem) throws Exception {
		super(problem.title);

		String input, output;

		input = "Input:\n" + (
			problem.inputFilePath != null ?
				new String(Files.readAllBytes(problem.inputFilePath), StandardCharsets.UTF_8) :
				problem.input);

		output = "Output:\n" + (
			problem.expectedOutput != null?
				problem.expectedOutput.toString() :
				problem.output);

		this.setLayout(new GridLayout(2, 1));
		JPanel pnlDesc = new JPanel(new GridLayout(1, 1));
		JPanel pnlIO = new JPanel(new GridLayout(1, 2));


		Font consolas14 = new Font("Consolas", Font.BOLD, 14);

		JTextArea txtInput = new JTextArea(input);
		txtInput.setEditable(false);
		txtInput.setFont(consolas14);

		JTextArea txtOutput = new JTextArea(output);
		txtOutput.setEditable(false);
		txtOutput.setFont(consolas14);

		JScrollPane scrlInput = new JScrollPane(txtInput);
		JScrollPane scrlOutput = new JScrollPane(txtOutput);

		JPanel pnlInput = new JPanel();
		pnlInput.setLayout(new BorderLayout());
		pnlInput.add(scrlInput);

		JPanel pnlOutput = new JPanel();
		pnlOutput.setLayout(new BorderLayout());
		pnlOutput.add(scrlOutput);

		JTextArea txtDesc = new JTextArea(problem.getDataString(), 20, 20);
		txtDesc.setFont(consolas14);
		txtDesc.setLineWrap(true);
		txtDesc.setEditable(false);

		JScrollPane scrlDesc = new JScrollPane(txtDesc, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		pnlDesc.add(scrlDesc);

		pnlIO.add(pnlInput);
		pnlIO.add(pnlOutput);

		this.add(pnlDesc);
		this.add(pnlIO);

		this.setSize(800, 600);
		this.setResizable(true);
		this.setVisible(true);
	}
}
