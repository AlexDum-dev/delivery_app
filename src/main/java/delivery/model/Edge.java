package delivery.model;

import java.util.List;

/**
 * Represents an edge of a graph
 * 
 * @author 4IF Group H4144
 * @version 1.0 20 Nov 2021
 */
public class Edge {
	private List<Segment> path;
	private Intersection destination;
	private Intersection origin;
	
	public Edge(List<Segment> path, Intersection origin, Intersection destination) {
		super();
		this.path = path;
		this.destination = destination;
		this.origin = origin;
	}

	public List<Segment> getPath() {
		return path;
	}

	public void setPath(List<Segment> path) {
		this.path = path;
	}

	public Intersection getDestination() {
		return destination;
	}

	public void setDestination(Intersection destination) {
		this.destination = destination;
	}

	public Intersection getOrigin() {
		return origin;
	}

	public void setOrigin(Intersection origin) {
		this.origin = origin;
	}
	
	public boolean equals(Edge e) {
		if (!this.getDestination().equals(e.getDestination())) {
			return false;
		}
		if (!this.getOrigin().equals(e.getOrigin())) {
			return false;
		}
		
		if (!(this.getPath().size() == e.getPath().size())) {
			return false;
		}
		for (int i = 0; i<this.getPath().size(); i++) {
			if (!this.getPath().get(i).equals(e.getPath().get(i))) {
				return false;
			}
		}		
		
		return true;
	}	

}
