
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class Evaluator extends JFrame {

/*private JPanel panel1 = new JPanel(new GridLayout(3,5 ));
private JPanel panel2 = new JPanel(new FlowLayout());
private JPanel panel3 = new JPanel(new FlowLayout());
private JPanel panel4 = new JPanel(new GridLayout(3, 5));
*/
Font myFont1 = new Font("Verdana", Font.BOLD, 20);
Font myFont2 = new Font("Verdana", Font.BOLD, 16);
Font myFont3 = new Font("SansSerif", Font.BOLD, 13);
//Color myColor1 = (Color.RED);

JButton btn1 = new JButton("CHOOSE:\t");
JButton btn2 = new JButton("CHOOSE:\t");
JButton btn3 = new JButton("SUBMIT");
final JTextArea ta=new JTextArea(20, 40);
final JTextField tf1 = new JTextField(20);
final JTextField tf2 = new JTextField(20);
final JTextField tf3 = new JTextField("1.00");

final JLabel lbl1 = new JLabel ("    E V A L U A T I O N\n");
final JLabel lbl2 = new JLabel ("Grade:\t");
final JLabel statusbar1 = new JLabel("");
final JLabel statusbar2 = new JLabel("");

JPanel panel1 = new JPanel();
JPanel panel2 = new JPanel();
//JPanel panel3 = new JPanel();
JScrollPane scroll = new JScrollPane(ta);

private File selectedFile;

GridBagConstraints gbc = new GridBagConstraints();

public Evaluator() {
	super("CODE EVALUATOR");
	setSize(700, 700);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setResizable(true);
	setLayout(new BorderLayout());

	ta.setEditable(false);
	tf1.setForeground(Color.BLUE);
	tf2.setForeground(Color.DARK_GRAY);
	lbl2.setForeground(Color.RED);

//sets panel layout
	panel1.setLayout(new GridBagLayout());
	panel2.setLayout(new FlowLayout());

//sets panel contents
	gbc.fill = GridBagConstraints.BOTH;

	gbc.insets = new Insets(30, 10, 10 ,0);
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridwidth = 1;
	gbc.gridheight = 1;
	panel1.add(btn1, gbc);

	gbc.gridx = 1;
	gbc.gridy = 0;
	gbc.gridwidth = 4;
	panel1.add(tf1, gbc);

/*gbc.gridx = 2;
gbc.gridy = 0;
gbc.gridwidth = 7;
panel1.add(statusbar1, gbc);*/

gbc.insets = new Insets(0, 10, 10 ,0);
gbc.gridx = 0;
gbc.gridy = 1;
gbc.gridwidth = 1;
panel1.add(btn2, gbc);

gbc.gridx = 1;
gbc.gridy = 1;
gbc.gridwidth = 4;
panel1.add(tf2, gbc);

gbc.insets = new Insets(20, 0, 20,0);
gbc.gridx = 3;
gbc.gridy = 2;
gbc.gridwidth = 2;
panel1.add(btn3, gbc);

gbc.insets = new Insets(0, 10, 0,0);
gbc.gridx = 5;
gbc.gridy = 3;
gbc.gridwidth = 1;
panel1.add(lbl2, gbc);

gbc.gridx = 6;
gbc.gridy = 3;
panel1.add(tf3, gbc);

gbc.insets = new Insets(70, 0, 50,0);
gbc.gridx = 2;
gbc.gridy = 4;
gbc.gridwidth = 3;
panel1.add(lbl1, gbc);
//lbl1.setColor(myColor1);


panel2.add(scroll);


//container
add(panel2, BorderLayout.SOUTH);
add(panel1, BorderLayout.NORTH);
panel1.setBackground(Color.LIGHT_GRAY);
panel2.setBackground(Color.LIGHT_GRAY);

ta.setFont(myFont3);
tf1.setFont(myFont3);
tf2.setFont(myFont3);
lbl1.setFont(myFont1);
lbl2.setFont(myFont2);
tf3.setFont(myFont3);



btn1.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent ae) {

		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);

		chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".java")||f.isDirectory();
			}

			public String getDescription() {
				return "Java Files";
			}
		});


		int option = chooser.showOpenDialog(Evaluator.this);
		if (option == JFileChooser.APPROVE_OPTION) {
			File[] sf = chooser.getSelectedFiles();
			String filelist = "nothing";
			if (sf.length > 0) filelist = sf[0].getName();
			for (int i = 1; i < sf.length; i++) {
				filelist += ", " + sf[i].getName();
			}
			tf1.setText(filelist);
			statusbar1.setText("You chose the program " + filelist);

			selectedFile = chooser.getSelectedFile();
		}
		else {
			statusbar1.setText("You canceled.");
		}
	}
});


btn2.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent ae) {

		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);

		chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".txt")||f.getName().toLowerCase().endsWith(".docx")||f.isDirectory();
			}

			public String getDescription() {
				return "Text Files";
			}
		});


		int option = chooser.showOpenDialog(Evaluator.this);
		if (option == JFileChooser.APPROVE_OPTION) {
			File[] sf = chooser.getSelectedFiles();
			String filelist = "nothing";
			if (sf.length > 0) filelist = sf[0].getName();
			for (int i = 1; i < sf.length; i++) {
				filelist += ", " + sf[i].getName();
			}
			tf2.setText(filelist);
			statusbar2.setText("You chose " + filelist + " for the output file of the program.\n");

		}
		else {
			statusbar2.setText("You canceled.");
		}
	}
});


btn3.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent ae) {
		try{
			String strLine;
			FileInputStream in = new FileInputStream(selectedFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			while ((strLine = br.readLine()) != null) {
				ta.append(strLine + "\n");
			}
		}catch(Exception e){
			System.out.println("Please see\t" + e);
		}
	}
});


}

public static void main(String args[]) {
	Evaluator sfc = new Evaluator();
	sfc.setVisible(true);
}
}
