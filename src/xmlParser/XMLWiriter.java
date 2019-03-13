package xmlParser;

import java.io.File;
import java.util.Deque;
import java.util.List;
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

	public XMLWiriter() {
	}

	public void writeXMLResultFile(String filename, List<Link> links) {
		try {
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			// Element root - path
			Element path = doc.createElement("path");
			doc.appendChild(path);
			
			Attr attr = doc.createAttribute("cost");
			int val = 0;
			for (Link link : links) {
				val+=link.getLink().getNbActor();
			}
			attr.setValue(""+val);
			path.setAttributeNode(attr);
			
			Attr attr2 = doc.createAttribute("nbMovies");
			attr2.setValue(""+links.size());
			path.setAttributeNode(attr2);
			
			int cpt = 0;
			for (Link link : links) {
				if(cpt==0) {
					Element actor = doc.createElement("actor");
					actor.appendChild(doc.createTextNode(link.getStart().getName()));
					path.appendChild(actor);
				}
				
				Element movie = doc.createElement("movie");
				movie.appendChild(doc.createTextNode(link.getLink().getName()));
				path.appendChild(movie);

				Attr attrMovieName = doc.createAttribute("name");
				attrMovieName.setValue(link.getLink().getName());
				movie.setAttributeNode(attrMovieName);
				
				Attr attrMovieYear = doc.createAttribute("year");
				attrMovieYear.setValue(""+link.getLink().getYear());
				movie.setAttributeNode(attrMovieYear);
			

				
				Element actor2 = doc.createElement("actor");
				actor2.appendChild(doc.createTextNode(link.getFinish().getName()));
				path.appendChild(actor2);
				cpt++;

			}

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
			e.printStackTrace();
		}

	}

}
