package codieval.admin;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import codieval.problem.*;

public class ProblemPanel extends JPanel {

	public Problem problem;

	public ProblemPanel(final Problem problem) {
		super(new FlowLayout(FlowLayout.LEFT));

		final JPanel panel = this;
		this.problem = problem;

		JButton btnInfo = new JButton("Info");

		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
				frame.setFocusableWindowState(false);

				try {
					new Requirements(problem);
				}
				catch(Exception e) {
					System.out.println(e.getMessage());
				}
				frame.setFocusableWindowState(true);
			}
		});
		add(btnInfo);
		final Checkbox chkProblem = new Checkbox(problem.toString(), problem.enabled);
		chkProblem.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				problem.enabled = chkProblem.getState();
			}
		});
		add(chkProblem);
	}
}
