package PP4;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UserGUI extends JPanel {

	private JButton scrapeButton;
	private JButton closeButton;
	// add more UI components as needed
	private Scraper scraper;

	private JLabel labelOutput;
	private static JTextArea textArea;
	private JScrollPane ScrollPane;
	JPanel panel = new JPanel();
	private JScrollPane jp;

	private String url;

	public UserGUI() {

		// uses the url provided in the document

		url = "http://www.nfl.com/players/search?category=position&filter=defensiveback&conferenceAbbr=null&playerType=current&conference=ALL";
		scraper = new Scraper(url);

		initGUI();
		doTheLayout();

		scrapeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrape();

			}

		});

		closeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});

	} // end of constructor

	// Creates and initialize the GUI objects
	private void initGUI() {
		labelOutput = new JLabel("Output: ");
		scrapeButton = new javax.swing.JButton("Scrape NFL Page");
		closeButton = new javax.swing.JButton("Close");

		textArea = new javax.swing.JTextArea(50, 75);
		ScrollPane = new javax.swing.JScrollPane(textArea);

	}// end of creating objects method

	// Layouts the UI components as shown in the project document
	private void doTheLayout() {

		JPanel top = new JPanel();
		JPanel center = new JPanel();
		JPanel centerTop = new JPanel();
		JPanel centerBottom = new JPanel();
		JPanel bottom = new JPanel();

		top.setLayout(new FlowLayout());
		top.add(scrapeButton);

		center.setLayout(new FlowLayout());
		center.add(labelOutput);
		center.add(textArea);
		center.add(centerTop, BorderLayout.NORTH);
		center.add(centerBottom, BorderLayout.SOUTH);

		bottom.setLayout(new FlowLayout());
		bottom.add(closeButton);

		setLayout(new BorderLayout());
		add(top, "North");
		add(center, "Center");
		add(bottom, "South");

	}// end of Layout method

	// Uses the Scraper object reference to return and display the data as shown in
	// the project document
	void scrape() {
		scraper.parseData(url);
		try {
			textArea.setText(scraper.display());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		}
	}// end of scrape action event method

	void close() {
		System.exit(0);
	}// end of close action event method

	public static void main(String[] args) {

		JFrame f = new JFrame("NFL Stats");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = f.getContentPane();
		contentPane.add(new UserGUI());
		f.pack();
		f.setVisible(true);
	}// end of main method

}// end of class
