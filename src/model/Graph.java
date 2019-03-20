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

import exceptions.NoRouteException;
import xmlParser.WriterXML;

public class Graph {
	private Map<String, Actor> actors;
	private long startTime;

	public Graph() {
	}

	public Map<String, Actor> getActors() {
		return actors;
	}

	public void setActors(Map<String, Actor> actors) {
		this.actors = actors;
	}

	public void calculerCheminLePlusCourt(String act1, String act2, String file) throws NoRouteException {
		List<Link> result = bfs(act1, act2);
		if (result != null) {
			writeFile(result, file);
		} else {
			throw new NoRouteException();
		}
	}

	public void calculerCheminCoutMinimum(String act1, String act2, String file) throws NoRouteException {
		List<Link> result = dijkstra(act1, act2);
		if (result != null) {
			writeFile(result, file);
		} else {
			throw new NoRouteException();
		}
	}

	/**
	 * bfs algorithme
	 * 
	 * @param start  : name of source actor
	 * @param finish : name of destination actor
	 * @return path
	 */
	private List<Link> bfs(String start, String finish) {
		this.startTime = System.currentTimeMillis();

		Deque<Actor> openSet = new ArrayDeque<>();
		Set<Actor> closedSet = new HashSet<>();
		Map<Actor, Actor> meta = new HashMap<>();
		Map<Actor, Movie> link = new HashMap<>();
		Set<Movie> closedMovieSet = new HashSet<>();

		Actor startActor = actors.get(start);
		Actor finishActor = actors.get(finish);

		openSet.addLast(startActor);
		closedSet.add(startActor);
		meta.put(startActor, null);

		while (!openSet.isEmpty()) {
			Actor currentActor = openSet.removeFirst();

			if (finishActor.equals(currentActor)) {
				// debug
				System.out.println("End bfs ! : execution time: " + (System.currentTimeMillis() - startTime) + "ms");
				return constructPath(startActor, meta, link, finishActor);
			}

			for (Movie m : currentActor.getMovies()) {
				if (!closedMovieSet.contains(m)) {
					for (Actor a : m.getActors()) {
						if (closedSet.contains(a)) {
							continue;
						}
						meta.put(a, currentActor);
						link.put(a, m);
						openSet.addLast(a);
						closedSet.add(a);
					}
					closedMovieSet.add(m);
				}
			}
		}
		return null;
	}

	/**
	 * dijkstra algorithme
	 * 
	 * @param start  : name of source actor
	 * @param finish : name of destination actor
	 * @return path
	 */
	private List<Link> dijkstra(String start, String finish) {
		this.startTime = System.currentTimeMillis();

		Map<Actor, Integer> temporaryLabel = new HashMap<>();
		Map<Actor, Integer> definitiveLabel = new HashMap<>();
		Map<Actor, Actor> parents = new HashMap<>();
		Set<Movie> closedMovieSet = new HashSet<>();

		// Parent array to store shortest path tree
		Actor begening = actors.get(start);
		Actor end = actors.get(finish);
		Actor currentActor = begening;
		temporaryLabel.put(currentActor, 0);
		definitiveLabel.put(currentActor, 0);

		while (!temporaryLabel.isEmpty()) {
			for (Movie m : currentActor.getMovies()) {
				if (!closedMovieSet.contains(m)) {
					for (Actor a : m.getActors()) {
						if (!definitiveLabel.containsKey(a)) {
							if (temporaryLabel.containsKey(a)) {
								if (temporaryLabel.get(a) > definitiveLabel.get(currentActor) + m.getNbActor()) {
									temporaryLabel.put(a, definitiveLabel.get(currentActor) + m.getNbActor());
									parents.put(a, currentActor);
								}
							}

						} else {
							temporaryLabel.put(a, definitiveLabel.get(currentActor) + m.getNbActor());
							parents.put(a, currentActor);
						}
					}

					closedMovieSet.add(m);
				}

			}
			if (currentActor.equals(end)) {
				System.out.println("End dijkstra ! : execution time: " + (System.currentTimeMillis() - startTime) + "ms");
				return new LinkedList<Link> ();//constructPath(begening, parents, end);
			}

			definitiveLabel.put(currentActor, temporaryLabel.get(currentActor));
			temporaryLabel.remove(currentActor);
			Entry<Actor, Integer> min = Collections.min(temporaryLabel.entrySet(),
					Comparator.comparing(Entry::getValue));
			currentActor = min.getKey();
			//currentActor = temporaryLabel.
		}

		return null;

	}

	/**
	 * Create the output files after bfs and dijkstra
	 * 
	 * @param path : path to write
	 * @param file : name of the file to write in
	 */
	private void writeFile(List<Link> path, String file) {
		Collections.reverse(path);
		path.forEach(t -> System.out.println(t.toString()));
		WriterXML xw = new WriterXML();
		xw.writeXMLResultFile(file, path);

	}

	/**
	 * Build the path after bfs and dijkstra algorithme
	 * 
	 * @param start  : actor source
	 * @param path   :
	 * @param finish : actor destination
	 * @return list : list of link (path)
	 */
	private List<Link> constructPath(Actor start, Map<Actor, Actor> path, Map<Actor, Movie> link, Actor finish) {
		List<Link> list = new LinkedList<Link>();
		Actor currentActor = finish;
		while (path.get(currentActor) != null) {
			list.add(new Link(path.get(currentActor), link.get(currentActor), currentActor));
			currentActor = path.get(currentActor);
		}
		return list;
	}

	/**
	 * Get the movie link between two actors
	 * 
	 * @param a1 : actor source
	 * @param a2 : actor destination
	 * @return m : the movie making link between both actor
	 */
	private Movie getLink(Actor a1, Actor a2) {
		for (Movie m : a1.getMovies()) {
			for (Movie m2 : a2.getMovies()) {
				if (m.equals(m2)) {
					return m;
				}
			}
		}

//		for (Movie m : a1.getMovies()) {
//			if(a2.playedInMovie(m)) {
//				System.out.println("## " + m.getName());
//				return m;
//			}
//		}
		return null;
	}

}
