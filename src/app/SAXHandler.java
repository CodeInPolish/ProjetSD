package app;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import model.Actor;
import model.Graph;
import model.Movie;

public class SAXHandler extends DefaultHandler {
	private Graph graph;
	private long start;
	Actor actor;
	Movie movie;
	boolean bName = false;
	private Map<String, Actor> actors;
	private Map<String, Movie> movies;
	private Map<String, String> actorsId;

	public SAXHandler() {
		this.graph = new Graph();
		this.start = System.currentTimeMillis();
		this.actors = new HashMap<>();
		this.movies = new HashMap<>();
		this.actorsId = new HashMap<>();
	}

	@Override
	public void startDocument() throws SAXException {
		System.out.println("Start reading !");
		System.out.println("Graph constructing...");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("actor")) {
			String id = attributes.getValue("id");
			String name = attributes.getValue("name");
			this.actor = new Actor(name, id);

		} else if (qName.equals("movie")) {
			String name = attributes.getValue("name");
			bName = true;
			this.movie = new Movie(name, 2000);
			this.addListMovie(attributes.getValue("actors"));
			

		}

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(bName) {
			String nameMovie = new String(ch, start, length);
			this.movie.setName(nameMovie);
			bName = false;
		}
		
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("actor")) {
			this.actors.put(this.actor.getName(), this.actor);
			this.actorsId.put(this.actor.getId(), this.actor.getName());
		} else if (qName.equals("movie")) {
			this.movies.put(this.movie.getName(), this.movie);
		}
	}

	@Override
	public void endDocument() throws SAXException {
		// graphe set methode
		this.graph.setActors(actors);
		this.graph.setMovies(movies);
		System.out.println("End reading ! : execution time: " + (System.currentTimeMillis() - start + "ms"));
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
