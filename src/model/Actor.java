package model;

import java.util.HashSet;

public class Actor {
	private int id;
	private String firstname;
	private String lastname;
	private HashSet<Movie> movies;
	
	public Actor() {
		this.movies = new HashSet<Movie>();
	}
	
	public Actor(String firstname, String lastname, int id) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.id = id;
		this.movies = new HashSet<Movie>();
	}
	
	public String getFirstname() {
		return this.firstname;
	}
	
	public String getLastname() {
		return this.lastname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public void addMovie(Movie m) {
		movies.add(m);
	}
	
	public boolean playedInMovie(Movie m) {
		return movies.contains(m);
	}
	
	public HashSet<Movie> getMovies() {
		return movies;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
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
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		return true;
	}
	
	
}
