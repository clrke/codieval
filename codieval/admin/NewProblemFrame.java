package codieval.admin;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import codieval.problem.*;

public class NewProblemFrame extends JFrame {
	public NewProblemFrame() {
		super("New Problem");
		setSize(600, 600);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
