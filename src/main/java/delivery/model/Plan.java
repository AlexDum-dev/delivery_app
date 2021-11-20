package delivery.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Plan {
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
