package PP4;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Scraper {

	private Matcher matcher;
	private Regex regex;
	private String url;
	private String display;
	private PrintWriter writefile;
	private String StrOutput;
	private String regex1;
	private String pages = "";

	// constructor
	public Scraper(String url) {
		this.url = url;

	}// end Scraper

	public void parseData(String pages) {

		String next_url = this.url;
		do {
			next_url = readNextPage(next_url);
		} while (next_url != "");

	}// end parseData

	// Define a PrintWriter object to store the program output
	private String readNextPage(String pageURL) {
		
		String next_url = "";
		StringBuilder content = new StringBuilder();

		try {
			// creating the URL

			URL url = new URL(pageURL);

			// Create the http url connection object
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

			// Reading the stream
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String line;
			

			int c;
			while ((line = bufferedReader.readLine()) != null) {
				content.append(line + "\n");
			} // end reading loop
			bufferedReader.close();
		} // end try block
		catch (MalformedURLException ex) {
			JOptionPane.showMessageDialog(null,
					"url + \" is not a valid URL. Please enter a URL starting with http://");
		} // end catch for improper URL
		catch (IOException ie) {
			JOptionPane.showMessageDialog(null, "Error while reading: " + ie.getMessage());
		} // end catch for io reasons

		// pattern
		Pattern pattern1 = Pattern.compile("<tr class=\"([\\s\\S]*?)</tr>");
		Pattern pattern2 = Pattern.compile("<td.*?</td>");
		Pattern pattern3 = Pattern.compile("</strong>.*?<a href=\"/players.*?</a>");
		Matcher matcher1 = pattern1.matcher(content);
		display = display + String.format("%-10s %-10s %-30s %-10s %-10s %-10s %-10s %-10s","Pos", "Num", "Player Name", "Status", "Tckl", "Sck", "Int", "Team") +"\r\n";
		
		// Loop every record
		while (matcher1.find())

		{
			String player = matcher1.group(0);
			// System.out.println(record);

			Matcher matcher2 = pattern2.matcher(player);
			List<String> fields = new ArrayList<String>();
			while (matcher2.find()) {
				String field = matcher2.group(0).replaceAll("</td>", "").replaceAll("</a>", "").trim();
				field = field.substring(field.indexOf(">") + 1);
				field = field.substring(field.indexOf(">") + 1);
				fields.add(field);
			}
			display = display
					+ String.format("%-10s %-10s %-30s %-10s %-10s %-10s %-10s %-10s", 
							fields.get(0), fields.get(1),fields.get(2), 
							fields.get(3), fields.get(4), fields.get(5), 
							fields.get(6), fields.get(7)) + "\r\n";
			
		}

		Matcher matcher3 = pattern3.matcher(content);
		if (matcher3.find()) {
			String url_html = matcher3.group(0);
			url_html = url_html.substring(url_html.indexOf("\"") + 1);
			url_html = url_html.substring(0, url_html.indexOf("\""));
			next_url = "http://www.nfl.com" + url_html.replace("&amp;", "&");
			}

		
		return next_url;

	}

	// shows the output (scraped data) in a text-area
	public String display() throws FileNotFoundException {

		java.io.File outputFile = new java.io.File("NFLStat.txt");
		// create a file
		java.io.PrintWriter output = new java.io.PrintWriter(outputFile);
		output.print(display);
		output.close();// close the file
		return display;
	}

} // end class
