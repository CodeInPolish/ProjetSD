package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import exceptions.NoRouteBetweenSourceAndDestination;

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

	public void calculerCheminLePlusCourt(String act1, String act2, String file)
			throws NoRouteBetweenSourceAndDestination {
		List<Link> result = bfs(act1, act2);
		if (result != null) {
			writeFile(result, file);
		} else {
			throw new NoRouteBetweenSourceAndDestination();
		}
	}

	public void calculerCheminCoutMinimum(String act1, String act2, String file)
			throws NoRouteBetweenSourceAndDestination {
		List<Link> result = dijkstra(act1, act2);
		if (result != null) {
			writeFile(result, file);
		} else {
			throw new NoRouteBetweenSourceAndDestination();
		}
	}

	private List<Link> bfs(String start, String finish) {
		ArrayDeque<Actor> openSet = new ArrayDeque<>();
		ArrayDeque<Actor> closedSet = new ArrayDeque<>();
		HashMap<Actor, Actor> meta = new HashMap<>();

		Actor startActor = actors.get(start);
		Actor finishActor = actors.get(finish);

		openSet.addLast(startActor);

		while (!openSet.isEmpty()) {
			Actor currentActor = openSet.removeFirst();

			if (finishActor.equals(currentActor)) {
				return constructPath(startActor, meta, finishActor);
			}

			for (Movie m : startActor.getMovies()) {
				for (Actor a : m.getActors()) {
					if (closedSet.contains(a)) {
						continue;
					}

					if (currentActor != a && !openSet.contains(a)) {
						meta.put(a, currentActor);
						openSet.addLast(a);
					}
				}
			}

			closedSet.addLast(currentActor);
			System.out.println("currentActor: " + currentActor.getId());
		}

		return null;
	}

	private List<Link> dijkstra(String start, String finish) {

		Map<Actor, Double> temporaryLabel = new HashMap<>();
		Map<Actor, Double> definitiveLabel = new HashMap<>();
		Map<Actor, Link> parents = new HashMap<>();

		// Parent array to store shortest path tree
		Actor begening = actors.get(start);
		Actor end = actors.get(finish);
		Actor currentActor = begening;
		temporaryLabel.put(currentActor, 0.0);
		
		
		while (currentActor != end) {
			
			

			// dijkstra algo
			
			
			definitiveLabel.put(currentActor, temporaryLabel.get(currentActor));
			temporaryLabel.remove(currentActor);
			
			if (temporaryLabel.isEmpty()) { // no path
				return null;
			} else {
				Entry<Actor, Double> min = Collections.min(temporaryLabel.entrySet(),
						Comparator.comparing(Entry::getValue));
				currentActor = min.getKey();
			}
						
		}
		
		// path construction 
		LinkedList<Link> pathStartFinish = new LinkedList<>();
		Actor a = currentActor;
		while(a!=begening) {
			Link parent = parents.get(a);
			pathStartFinish.push(parents.get(actors.get(parent.getFinish())));
			a = actors.get(parent.getStart());
		}
		return pathStartFinish;
	}

	private void writeFile(List<Link> path, String file) {

	}

	private List<Link> constructPath(Actor start, Map<Actor, Actor> path, Actor finish) {
		List<Actor> list = new ArrayList<Actor>();

		Actor currentActor = finish;
		while (currentActor != start) {
			list.add(currentActor);
			currentActor = actors.get(currentActor.getName());
			System.out.println("blblbl");
		}

		for (Actor a : list) {
			System.out.println(a.toString());
		}

		return null;
	}

}
