package model;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import exceptions.NoRouteException;
import xmlParser.WriterXML;

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

	/**
	 * Method public for asking the shortly path
	 * 
	 * @param act1 : name of source actor
	 * @param act2 : name of destination actor
	 * @param file : name of output XML file
	 * @throws NoRouteException
	 */
	public void calculerCheminLePlusCourt(String act1, String act2, String file) throws NoRouteException {
		List<Link> result = bfs(act1, act2);
		if (result != null) {
			writeFile(result, file);
		} else {
			throw new NoRouteException();
		}
	}

	/**
	 * Method public for asking the less costly path
	 * 
	 * @param act1 : name of source actor
	 * @param act2 : name of destination actor
	 * @param file : name of output XML file
	 * @throws NoRouteException
	 */
	public void calculerCheminCoutMinimum(String act1, String act2, String file) throws NoRouteException {
		List<Link> result = dijkstra(act1, act2);
		if (result != null) {
			writeFile(result, file);
		} else {
			throw new NoRouteException();
		}
	}

	/**
	 * bfs algorithme for find the shortly path
	 * 
	 * @param start  : name of source actor
	 * @param finish : name of destination actor
	 * @return path
	 */
	private List<Link> bfs(String start, String finish) {
		long startTime = System.currentTimeMillis();

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
				System.out.println("\n\t- BFS executed in : " + (System.currentTimeMillis() - startTime) + "ms");
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
	 * Comparator for dijkstra oderder Treemap TemporaryLabel
	 */
	private Comparator<Actor> comparator = new Comparator<Actor>() {
		@Override
		public int compare(Actor a1, Actor a2) {
			int costA1 = a1.getCost();
			int costA2 = a2.getCost();
			if (costA1 != costA2) {
				return costA1 - costA2;
			} else {
				return a1.getId().compareTo(a2.getId());
			}
		}
	};

	/**
	 * dijkstra algorithme for find the less costly path
	 * 
	 * @param start  : name of source actor
	 * @param finish : name of destination actor
	 * @return path
	 */
	private List<Link> dijkstra(String start, String finish) {
		long startTime = System.currentTimeMillis();

		SortedMap<Actor, Integer> temporaryLabel = new TreeMap<Actor, Integer>(comparator);
		Map<Actor, Integer> definitiveLabel = new HashMap<>();
		Map<Actor, Actor> parents = new HashMap<>();
		Map<Actor, Movie> links = new HashMap<>();
		Set<Movie> closedMovieSet = new HashSet<>();

		Actor begening = actors.get(start);
		Actor end = actors.get(finish);
		Actor currentActor = begening;
		currentActor.setCost(0);
		temporaryLabel.put(currentActor, 0);
		definitiveLabel.put(currentActor, 0);

		while (!temporaryLabel.isEmpty()) {
			for (Movie m : currentActor.getMovies()) {
				if (!closedMovieSet.contains(m)) {
					for (Actor a : m.getActors()) {
						if (temporaryLabel.containsKey(a)) {
							if (temporaryLabel.get(a) > definitiveLabel.get(currentActor) + m.getNbActor()) {
								temporaryLabel.remove(a);
								a.setCost(definitiveLabel.get(currentActor) + m.getNbActor());
								temporaryLabel.put(a, a.getCost());
								parents.put(a, currentActor);
								links.put(a, m);
							}
						} else if (!definitiveLabel.containsKey(a) && !temporaryLabel.containsKey(a)) {
							a.setCost(definitiveLabel.get(currentActor) + m.getNbActor());
							temporaryLabel.put(a, a.getCost());
							parents.put(a, currentActor);
							links.put(a, m);
						}
					}
					closedMovieSet.add(m);
				}
			}

			currentActor = temporaryLabel.firstKey();
			if (currentActor.equals(end)) {
				System.out.println("\n\t- Dijkstra executed in : " + (System.currentTimeMillis() - startTime) + "ms");
				return constructPath(begening, parents, links, end);
			}
			definitiveLabel.put(currentActor, currentActor.getCost());
			temporaryLabel.remove(currentActor);

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
		displayPath(path);
		WriterXML xw = new WriterXML();
		xw.writeXMLResultFile(file, path);
	}

	/**
	 * Display the path in consol
	 * 
	 * @param path
	 */
	private void displayPath(List<Link> path) {
		System.out.println("\t\t- Path :");
		int cpt = 0;
		for (Link t : path) {
			if (cpt == 0)
				System.out.println("\t\t\t- " + t.getStart().getName());
			System.out.println("\t\t\t- " + t.getFinish().getName());
			cpt++;

		}
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

}
