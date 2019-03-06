package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Graph {
	private Map<String, Actor> actors;
	
	public Graph() {
	}
	
	public Map<String, Actor> getActors() {
		return actors;
	}

	public void setActors(Map<String, Actor> actors) {
		this.actors = actors;
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
		HashMap<Actor, Actor> meta = new HashMap<>();
		
		Actor startActor = actors.get(start);
		Actor finishActor = actors.get(finish);		
		
		openSet.addLast(startActor);
		
		while(!openSet.isEmpty()) {
			Actor current = openSet.removeFirst();
			
			if(finishActor.equals(current)) {
				return constructPath(startActor, meta, finishActor);
			}
			
			for(Movie m : startActor.getMovies()) {
				for(Actor a : m.getActors()) {
					if(closedSet.contains(a)) {
						continue;
					}
					
					if(current != a && !openSet.contains(a)) {
						meta.put(a, current);
						openSet.addLast(a);
					}
				}
			}
			
			closedSet.addLast(current);
		}
		
		return null;
	}
	
	private List<Link> dijkstra(String start, String finish) {
		return new ArrayList<>();
	}
	
	private void writeFile(List<Link> path, String file) {
		
	}
	
	private List<Link> constructPath(Actor start, Map<Actor, Actor> path, Actor finish){
		List<Actor> list = new ArrayList<Actor>();
		
		Actor current = finish;
		while(current != start) {
			list.add(current);
			current = actors.get(current.getName());
			System.out.println("blblbl");
		}
		
		for(Actor a : list) {
			System.out.println(a.toString());
		}
		
		return null;
	}
		
}
