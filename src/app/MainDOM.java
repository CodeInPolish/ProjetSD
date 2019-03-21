package app;

import java.io.File;

import model.Graph;
import xmlParser.DomParse;

public class MainDOM {
	public static void main(String[] args) {
		try {
			System.out.println("# Start treatment !");
			File inputFile = new File("./res/movies.xml");
			DomParse dm = new DomParse();
			dm.parsing(inputFile);
			Graph g = dm.getGraph();
			g.calculerCheminLePlusCourt("Macaulay Culkin", "Guillaume Canet", "output.xml");
			g.calculerCheminCoutMinimum("Macaulay Culkin", "Guillaume Canet", "output2.xml");
			System.out.println("\n# End of treatment !");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
