package xmlParser;

import java.io.File;
import java.util.Deque;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import model.*;

public class XMLWiriter {

	private Map<String, Movie> movies;
	private Map<String, Actor> actors;
	private Map<String, Set<Actor>> path;

	public XMLWiriter(Map<String, Movie> movies, Map<String, Actor> actors, Map<String, Set<Actor>> path) {
		this.movies = movies;
		this.actors = actors;
		this.path = path;
	}

	public void writeXMLResultFile(String filename, Deque<Actor> chemin, String source, String destination) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			// TODO construction of xml file
			
			
			

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			DOMImplementation domImpl = doc.getImplementation();
			DocumentType doctype = domImpl.createDocumentType("doctype", null, "result.dtd");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
			DOMSource sources = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filename));
			transformer.transform(sources, result);

			// Output to console for testing
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(sources, consoleResult);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
