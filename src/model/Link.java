package model;

public class Link {
	private Actor start;
	private Movie link;
	private Actor finish;
	
	public Link(Actor s, Movie l, Actor f) {
		this.start = s;
		this.link = l;
		this.finish = f;
	}

	public Actor getStart() {
		return start;
	}

	public Movie getLink() {
		return link;
	}

	public Actor getFinish() {
		return finish;
	}

	@Override
	public String toString() {
		return "Link [start=" + start + ", link=" + link + ", finish=" + finish + "]";
	}
	
	
	
	
}
