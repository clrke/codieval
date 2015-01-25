package codieval.admin;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import codieval.problem.*;

import codieval.ucompiler.UniversalCompiler;

import codieval.complexity.*;

import java.io.*;

import java.nio.charset.*;
import java.nio.file.*;

public class NewProblemFrame extends JFrame {
	JTextField txtTitle = new JTextField(20);
	JTextArea txtDescription = new JTextArea(20, 20);
	JTextArea txtSampleInput = new JTextArea(20, 20);
	JTextArea txtSampleOutput = new JTextArea(20, 20);
	JTextArea txtInput = new JTextArea(20, 20);
	JTextArea txtOutput = new JTextArea(20, 20);

	JFileChooser chooser = new JFileChooser();

	File sourceCode;

	JButton btnImportSampleInput = new JButton("Import sample input");
	JButton btnImportInput = new JButton("Import input");
	JButton btnImportSourceCode = new JButton("Import source code");
	JButton btnGenerate = new JButton("Generate output");
	JButton btnInfo = new JButton("View Information");
	JButton btnSave = new JButton("Save");
	JButton btnCancel = new JButton("Cancel");

	JLabel lblSourceCode = new JLabel();

	public NewProblemFrame() {
		super("New Problem");

		setLayout(new GridLayout(2, 1));

		add(getTopPanel());
		add(getBottomPanel());

		setSize(800, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setVisible(true);
	}
	private JPanel getTopPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1));

		panel.add(getGeneralPanel());
		panel.add(getSamplesPanel());

		return panel;
	}
	private JPanel getGeneralPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2));

		panel.add(getInfoPanel());
		panel.add(getOptionsPanel());

		return panel;
	}
	private JPanel getSamplesPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2));

		panel.add(getSampleInputPanel());
		panel.add(getSampleOutputPanel());

		return panel;
	}
	private JPanel getBottomPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2));

		panel.add(getInputPanel());
		panel.add(getOutputPanel());

		return panel;
	}
	private JPanel getInfoPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		panel.add(getTitlePanel(), BorderLayout.NORTH);
		panel.add(getDescriptionPanel(), BorderLayout.CENTER);

		return panel;
	}
	private JPanel getOptionsPanel() {
		JPanel panel = new JPanel(new GridLayout(4, 2));

		btnImportSampleInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
					public boolean accept(File f) {
						return true;
					}

					public String getDescription() {
						return "Textfile";
					}
				});

				int option = chooser.showOpenDialog(NewProblemFrame.this);
				if (option == JFileChooser.APPROVE_OPTION) {
					try {
						try(BufferedReader br = new BufferedReader(
								new FileReader(chooser.getSelectedFile()))) {
							String sampleInput = "";
							for(String line = br.readLine(); line != null; line = br.readLine()) {
								sampleInput += line + "\n";
							}
							txtSampleInput.setText(sampleInput);
							refreshGenerateOutputEnability();
						}
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		btnImportInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
					public boolean accept(File f) {
						return true;
					}

					public String getDescription() {
						return "Textfile";
					}
				});

				int option = chooser.showOpenDialog(NewProblemFrame.this);
				if (option == JFileChooser.APPROVE_OPTION) {
					try {
						try(BufferedReader br = new BufferedReader(
								new FileReader(chooser.getSelectedFile()))) {
							String input = "";
							for(String line = br.readLine(); line != null; line = br.readLine()) {
								input += line + "\n";
							}
							txtInput.setText(input);
							refreshGenerateOutputEnability();
						}
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		btnImportSourceCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
					public boolean accept(File f) {
						return true;
					}

					public String getDescription() {
						return "Source Code";
					}
				});

				int option = chooser.showOpenDialog(NewProblemFrame.this);
				if (option == JFileChooser.APPROVE_OPTION) {
					sourceCode = chooser.getSelectedFile();

					BigO bigO = new BigO();

					try {
						bigO = Complexity.getBigO(new String(
							Files.readAllBytes(sourceCode.toPath()), StandardCharsets.UTF_8));
					} catch(IOException e) {
						e.printStackTrace();
					}

					lblSourceCode.setText(sourceCode.getName() + " " + bigO);

					refreshGenerateOutputEnability();
				}
			}
		});

		btnGenerate.setEnabled(false);

		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					String result;

					try(PrintWriter pw = new PrintWriter(
							new FileWriter(new File(sourceCode.getParent(), "input.txt")))) {
						pw.write(txtSampleInput.getText());
					}
					result = UniversalCompiler.compileAndRun(
						sourceCode.getParent(), sourceCode.getName());

					txtSampleOutput.setText(result);

					try(PrintWriter pw = new PrintWriter(
							new FileWriter(new File(sourceCode.getParent(), "input.txt")))) {
						pw.write(txtInput.getText());
					}
					result = UniversalCompiler.compileAndRun(
						sourceCode.getParent(), sourceCode.getName());

					txtOutput.setText(result);

					btnInfo.setEnabled(true);
					btnSave.setEnabled(true);
				} catch(Exception e) {
					JOptionPane.showMessageDialog(NewProblemFrame.this, e);
					e.printStackTrace();
				}
			}
		});

		btnInfo.setEnabled(false);

		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Problem problem = new Problem(
					txtTitle.getText(),
					txtDescription.getText(),
					txtSampleInput.getText(),
					txtSampleOutput.getText()
				);
				problem.input = txtInput.getText();
				problem.output = txtOutput.getText();

				try {
					new Requirements(problem);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});

		btnSave.setEnabled(false);

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int i;
				String name;

				for (i = 1; ; i++) {
					name = String.format("problem%04d", i);
					File file = new File("problems/"+name);

					if( ! file.exists())
						break;
				}

				System.out.println("Todo: Make "+name);
				NewProblemFrame.this.dispatchEvent(
					new WindowEvent(NewProblemFrame.this, WindowEvent.WINDOW_CLOSING));
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				NewProblemFrame.this.dispatchEvent(
					new WindowEvent(NewProblemFrame.this, WindowEvent.WINDOW_CLOSING));
			}
		});

		panel.add(btnImportSampleInput);
		panel.add(btnImportInput);
		panel.add(getSourceCodePanel());
		panel.add(btnImportSourceCode);
		panel.add(btnGenerate);
		panel.add(btnInfo);
		panel.add(btnSave);
		panel.add(btnCancel);

		return panel;
	}
	private JPanel getTitlePanel() {
		JPanel panel = new JPanel(new FlowLayout());

		panel.add(new JLabel("Title:"));
		panel.add(txtTitle);

		return panel;
	}
	private JPanel getDescriptionPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		JPanel labelPanel = new JPanel(new GridLayout(1, 1));
		labelPanel.add(new JLabel("Description:"));

		JPanel textPanel = new JPanel(new GridLayout(1, 1));
		textPanel.add(new JScrollPane(txtDescription));

		panel.add(labelPanel, BorderLayout.NORTH);
		panel.add(textPanel, BorderLayout.CENTER);

		return panel;
	}
	private JPanel getSourceCodePanel() {
		JPanel panel = new JPanel(new FlowLayout());

		panel.add(lblSourceCode);

		return panel;
	}
	private JPanel getSampleInputPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		JPanel labelPanel = new JPanel(new GridLayout(1, 1));
		labelPanel.add(new JLabel("Sample Input:"));

		txtSampleInput.setEditable(false);
		JPanel textPanel = new JPanel(new GridLayout(1, 1));
		textPanel.add(new JScrollPane(txtSampleInput));

		panel.add(labelPanel, BorderLayout.NORTH);
		panel.add(textPanel, BorderLayout.CENTER);

		return panel;
	}
	private JPanel getSampleOutputPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		JPanel labelPanel = new JPanel(new GridLayout(1, 1));
		labelPanel.add(new JLabel("Sample Expected Output:"));

		txtSampleOutput.setEditable(false);
		JPanel textPanel = new JPanel(new GridLayout(1, 1));
		textPanel.add(new JScrollPane(txtSampleOutput));

		panel.add(labelPanel, BorderLayout.NORTH);
		panel.add(textPanel, BorderLayout.CENTER);

		return panel;
	}
	private JPanel getInputPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		JPanel labelPanel = new JPanel(new GridLayout(1, 1));
		labelPanel.add(new JLabel("Input:"));

		txtInput.setEditable(false);
		JPanel textPanel = new JPanel(new GridLayout(1, 1));
		textPanel.add(new JScrollPane(txtInput));

		panel.add(labelPanel, BorderLayout.NORTH);
		panel.add(textPanel, BorderLayout.CENTER);

		return panel;
	}
	private JPanel getOutputPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		JPanel labelPanel = new JPanel(new GridLayout(1, 1));
		labelPanel.add(new JLabel("Expected Output:"));

		txtOutput.setEditable(false);
		JPanel textPanel = new JPanel(new GridLayout(1, 1));
		textPanel.add(new JScrollPane(txtOutput));

		panel.add(labelPanel, BorderLayout.NORTH);
		panel.add(textPanel, BorderLayout.CENTER);

		return panel;
	}
	private void refreshGenerateOutputEnability() {
		if(txtInput.getText().length() > 0 &&
				txtSampleInput.getText().length() > 0 &&
				sourceCode != null) {
			btnGenerate.setEnabled(true);
			btnInfo.setEnabled(false);
			btnSave.setEnabled(false);

			txtSampleOutput.setText("");
			txtOutput.setText("");
		}
	}
}
