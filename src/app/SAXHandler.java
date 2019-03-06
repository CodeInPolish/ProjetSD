package app;
import org.xml.sax.helpers.DefaultHandler;
import model.Graph;

public class SAXHandler extends DefaultHandler {
	public Graph getGraph() {
		return new Graph();
	}
}
