package model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Actor {
	private String id;
	private String name;
	private Set<Movie> movies;
	private int cost;

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public Actor() {
		this.movies = new HashSet<Movie>();
	}

	public Actor(String name, String id) {
		this();
		this.name = name;
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void addMovie(Movie m) {
		movies.add(m);
	}

	public boolean playedInMovie(Movie m) {
		return movies.contains(m);
	}

	public Iterator<Movie> getMovies() {
		return movies.iterator();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Actor [id=" + id + ", name=" + name + "]";
	}

}
