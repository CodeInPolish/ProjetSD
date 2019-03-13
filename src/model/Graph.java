package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import exceptions.NoRouteBetweenSourceAndDestination;
import xmlParser.XMLWiriter;

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
		Deque<Actor> openSet = new ArrayDeque<>();
		Set<Actor> closedSet = new HashSet<>();
		
		Map<Actor, Actor> meta = new HashMap<>();
		Set<Movie> closedMovieSet = new HashSet<>();

		Actor startActor = actors.get(start);
		Actor finishActor = actors.get(finish);

		openSet.addLast(startActor);
		closedSet.add(startActor);
		meta.put(startActor, null);

		while (!openSet.isEmpty()) {
			Actor currentActor = openSet.removeFirst();
			
			if (finishActor.equals(currentActor)) {
				return constructPath(startActor, meta, finishActor);
			}
			
			for (Movie m : currentActor.getMovies()) {		
				if(!closedMovieSet.contains(m)) {
					//System.out.println("movie: "+m.getName());
					for (Actor a : m.getActors()) {
						if (closedSet.contains(a)) {
							continue;
						}
						meta.put(a, currentActor);
						openSet.addLast(a);
						closedSet.add(a);						
					}
					closedMovieSet.add(m);
				}
			}
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
		Collections.reverse(path);
		path.forEach(t->System.out.println(t.toString()));
		XMLWiriter xw = new XMLWiriter();
		xw.writeXMLResultFile(file, path);

	}

	private List<Link> constructPath(Actor start, Map<Actor, Actor> path, Actor finish) {
		List<Link> list = new LinkedList<Link>();


		System.out.println("Path found! " + finish.getName() );
		Actor currentActor = finish;
		while (path.get(currentActor) != null) {
			Movie tempMovie = getLink(path.get(currentActor), currentActor);
			list.add(new Link(path.get(currentActor),tempMovie , currentActor));
			currentActor = path.get(currentActor);
		}		
		
		return list;
	}
	
	private Movie getLink(Actor a1, Actor a2) {
		
		System.out.println("a1 : " + a1.getName());
		System.out.println("a2 : " + a2.getName());
		
		System.out.println("a1 movies : ");
		for (Movie m : a1.getMovies()) {
			for (Movie m2 : a2.getMovies()) {
				if(m.equals(m2)) {
					System.out.println("BIM !");
					return m;
				}
			}
		}
//
//		for (Movie m : a1.getMovies()) {
//			if(a2.getMovies().contains(m)) {
//				System.out.println("## " + m.getName());
//				return m;
//			}
//		}
		return null;
	}

}
