package xmlParser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.Actor;
import model.Graph;
import model.Movie;

public class DomParse {
	private Map<String, Actor> actors;
	private Map<String, Movie> movies;
	private Map<String, String> actorsId;
	private Graph graph;
	private Actor actor;
	private Movie movie;

	public DomParse() {
		this.graph = new Graph();
		this.actors = new HashMap<>();
		this.movies = new HashMap<>();
		this.actorsId = new HashMap<>();
	}

	public void parsing(File file) {
		long start = System.currentTimeMillis();
		System.out.println("\n\t- Start XML file parsing !");
		System.out.println("\t\t- Graph constructing...");
		try {
			File xmlFile = file;
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);

			NodeList actor = doc.getElementsByTagName("actor");
			for (int i = 0; i < actor.getLength(); i++) {
				Node nActor = actor.item(i);
				Element eActor = (Element) nActor;
				String id = eActor.getAttribute("id");
				String name = eActor.getAttribute("name");
				this.actor = new Actor(name, id);
				this.actors.put(name, this.actor);
				this.actorsId.put(id, name);

			}

			NodeList movie = doc.getElementsByTagName("movie");
			for (int i = 0; i < movie.getLength(); i++) {
				Node nMovie = movie.item(i);
				Element eMovie = (Element) nMovie;
				String name = eMovie.getTextContent();
				String year = eMovie.getAttribute("year");
				String actorList = eMovie.getAttribute("actors");
				if (year.isEmpty())
					year = "0";
				this.movie = new Movie(name, Integer.parseInt(year));
				this.addListMovie(actorList);
				this.movies.put(name, this.movie);

			}
			this.graph.setActors(actors);
			System.out.println("\t\t- Graph construct in : " + (System.currentTimeMillis() - start + "ms"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addListMovie(String list) {
		String[] temp = list.split("\\s+");
		for (String string : temp) {
			Actor a = this.actors.get(this.actorsId.get(string));
			this.movie.addActor(a);
			a.addMovie(this.movie);
		}
	}

	public Graph getGraph() {
		return this.graph;
	}

}
