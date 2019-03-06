package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		List<Link> result = bfs(act1, act2);
		if(result != null) {
			writeFile(result, file);
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	public void calculerCheminCoutMinimum(String act1, String act2, String file) {
		List<Link> result = dijkstra(act1, act2);
		if(result != null) {
			writeFile(result, file);
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	private List<Link> bfs(String start, String finish) {
		ArrayDeque<Actor> openSet = new ArrayDeque<>();
		ArrayDeque<Actor> closedSet = new ArrayDeque<>();
		HashMap<Actor, Actor> meta = new HashMap();
		
		Actor startActor = actors.get(start);
		Actor finishActor = actors.get(finish);		
		
		openSet.add(startActor);
		
		while(!openSet.isEmpty()) {
			Actor current = openSet.remove();
			
			if(finishActor.equals(current)) {
				return constructPath(meta);
			}
			
			for(Movie m : startActor.getMovies()) {
				for(Actor a : m.getActors()) {
					if(closedSet.contains(a)) {
						continue;
					}
					
					if(!openSet.contains(a)) {
						meta.put(a, current);
						openSet.add(a);
					}
				}
			}
			
			closedSet.add(current);
			
		}
		
		return null;
	}
	
	private List<Link> dijkstra(String start, String finish) {
		return new ArrayList<>();
	}
	
	private void writeFile(List<Link> path, String file) {
		
	}
	
	private List<Link> constructPath(Map<Actor, Actor> path){
		System.out.println(path);
		return null;
	}
		
}
