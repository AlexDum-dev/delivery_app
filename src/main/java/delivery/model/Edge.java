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
	
	
	

}
