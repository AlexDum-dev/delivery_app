package delivery.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import observer.Observable;

/**
 * Class that handles the entire map
 * 
 * @author 4IF Group H4144
 * @version 1.0 20 Nov 2021
 */
public class Plan extends Observable {
	private Map<String, Intersection> intersections;
	private List<Segment> segments;
	//Graph
	private List<Request> requests;
	private Depot depot;

	public Plan() {
		intersections = new HashMap<String, Intersection>();
		segments = new ArrayList<Segment>();
		requests = new ArrayList<Request>();
		depot = null;
	}
	
	public void clearPlan() {
		intersections.clear();
		segments.clear();
		clearRequests();
	}
	
	public void clearRequests() {
		requests.clear();
	}
	
	public Graph createGraph() {
		Graph g = new Graph();
		for (Segment s : segments) {
			List<Segment> l = new ArrayList<Segment>();
			l.add(s);
			Edge e = new Edge(l, s.getOrigin(), s.getDestination());
			g.addEdge(e);
		}
		return g;
	}
	
	public void addRequest(Request r) {
		requests.add(r);
	}

	public void addIntersection(Intersection i) {
		intersections.put(i.getId(), i);
	}
	
	public Intersection getIntersection(String id) {
		return intersections.get(id);
	}
	
	public void addSegment(Segment s) {
		segments.add(s);
	}
	
	public Map<String, Intersection> getIntersections() {
		return intersections;
	}

	public List<Segment> getSegments() {
		return segments;
	}

	public List<Request> getRequests() {
		return requests;
	}

	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}
	
}
