import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

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
}