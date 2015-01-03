package codieval.admin;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import codieval.problem.Problem;

public class ProblemPanel extends JPanel {

	public Problem problem;

	public ProblemPanel(Problem problem) {
		super(new FlowLayout(FlowLayout.LEFT));

		this.problem = problem;

		add(new Checkbox(problem.toString(), true));
	}
}
