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
	
	public Boolean equals(Plan p) {
		
		if (!p.getDepot().equals(this.getDepot())) {
			return false;
		}		
		
		for (String key : intersections.keySet()) {
			if (!p.getIntersections().get(key).equals(this.getIntersections().get(key))) {
				return false;
			}	
		}
		if (this.segments.size()!=p.getSegments().size()) {
			return false;
		}
		for (int i=0; i<segments.size(); i++) {
			boolean found=false;
			for (int j=0; j<segments.size(); j++) {
				if (p.getSegments().get(i).equals(this.getSegments().get(j))) {
					found=true;
				}	
			}
			if (!found) {
				return false;
			}
		}
		
		if (this.requests.size()!=p.getRequests().size()) {
			return false;
		}
		for (int i=0; i<requests.size(); i++) {
			boolean found=false;
			for (int j=0; j<requests.size(); j++) {
				if (p.getRequests().get(i).equals(this.getRequests().get(j))) {
					found=true;
				}	
			}
			if (!found) {
				return false;
			}
		}
		
		return true;
	}
	
}
