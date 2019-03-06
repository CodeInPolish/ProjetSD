package model;

import java.util.HashMap;
import java.util.Map;

public class Graph {
	private Map<String, Actor> actors;
	private Map<String, Movie> movies;
	
	public Graph() {
	}
	
	public Map<String, Actor> getActors() {
		return actors;
	}

	public void setActors(Map<String, Actor> actors) {
		this.actors = actors;
	}

	public Map<String, Movie> getMovies() {
		return movies;
	}

	public void setMovies(Map<String, Movie> movies) {
		this.movies = movies;
	}

	public void calculerCheminLePlusCourt(String act1, String act2, String file) {
		String result = bfs(act1, act2);
	}
	
	public void calculerCheminCoutMinimum(String act1, String act2, String file) {
		String result = dijkstra(act1, act2);
	}
	
	private String bfs(String start, String finish) {
		return "";
	}
	
	private String dijkstra(String start, String finish) {
		return "";
	}
	
	private void writeFile(String data, String file) {
		
	}
		
}
