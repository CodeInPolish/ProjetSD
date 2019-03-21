package app;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import model.Graph;
import xmlParser.SAXHandler;

public class Main {
	public static void main(String[] args) {
		try {
			System.out.println("# Start treatment !");
			File inputFile = new File("./res/movies.xml");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			SAXHandler userhandler = new SAXHandler();
			saxParser.parse(inputFile, userhandler);
			Graph g = userhandler.getGraph();
			g.calculerCheminLePlusCourt("Macaulay Culkin", "Guillaume Canet", "output.xml");
			g.calculerCheminCoutMinimum("Macaulay Culkin", "Guillaume Canet", "output2.xml");
			System.out.println("\n# End of treatment !");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
