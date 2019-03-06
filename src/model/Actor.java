package model;

import java.util.HashSet;
import java.util.Set;

public class Actor {
	private String id;
	private String name;
	private Set<Movie> movies;
	
	public Actor() {
		this.movies = new HashSet<Movie>();
	}
	
	public Actor(String name, String id) {
		this.name = name;
		this.id = id;
		this.movies = new HashSet<Movie>();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName(String name) {
		return this.name;
	}
	
	public void addMovie(Movie m) {
		movies.add(m);
	}
	
	public boolean playedInMovie(Movie m) {
		return movies.contains(m);
	}
	
	public Set<Movie> getMovies() {
		return movies;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}



	
}
