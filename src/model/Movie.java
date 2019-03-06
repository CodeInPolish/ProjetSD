package model;

import java.util.HashSet;

public class Movie {
	private String name;
	private int year;
	private HashSet<Actor> actors;
	
	public Movie() {
		this.actors = new HashSet<Actor>();
	}
	
	public Movie(String name, int year) {
		this.name = name;
		this.year = year;
		this.actors = new HashSet<Actor>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public void addActor(Actor a) {
		this.actors.add(a);
	}
	
	public boolean played(Actor a) {
		return this.actors.contains(a);
	}
	
	public HashSet<Actor> getActors(){
		return this.actors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + year;
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
		Movie other = (Movie) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	
	
	
}
