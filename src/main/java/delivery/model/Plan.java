package delivery.model;

import java.util.ArrayList;
import java.util.List;

import observer.Observable;

/**
 * Class that handles the entire map
 * 
 * @author 4IF Group H4144
 * @version 1.0 20 Nov 2021
 */
public class Plan extends Observable {
	private List<Intersection> intersections;
	//Graph
	private List<Request> requests;
	private CheckPoint depot;
	
	public Plan() {
		intersections = new ArrayList<Intersection>();
		requests = new ArrayList<Request>();
		depot = null;
	}
	
	public void clearPlan() {
		intersections.clear();
		clearRequests();
	}
	
	
	public void clearRequests() {
		requests.clear();
		depot = null;
	}
	
	public void addRequest(Request r) {
		r.setIndex(requests.size());
		requests.add(r);
	}

	public void addIntersection(Intersection i) {
		i.setIndex(intersections.size());
		intersections.add(i);
	}
	
	public Intersection getIntersection(String id) {
		Intersection inter = null;
		for (Intersection i : intersections) {
			if (i.getId().equals(id)) {
				inter = i;
			}
		}
		return inter;
	}
	
	public List<Intersection> getIntersections() {
		return intersections;
	}
	
	public List<Request> getRequests() {
		return requests;
	}

	public CheckPoint getDepot() {
		return depot;
	}

	public void setDepot(CheckPoint depot) {
		this.depot = depot;
	}
	
	public double getMaxLatitude() {
		double max = 0;
		for(Intersection i : intersections) {
			if(i.getLatitude() > max) max = i.getLatitude();
		}
		
		return max;
	}
	
	public double getMaxLongitude() {
		double max = 0;
		for(Intersection i : intersections) {
			if(i.getLongitude() > max) max = i.getLongitude();
		}
		
		return max;
	}
	
	public double getMinLongitude() {
		double min = Double.MAX_VALUE;
		for(Intersection i : intersections) {
			if(i.getLongitude() < min) min = i.getLongitude();
		}
		
		return min;
	}
	
	public double getMinLatitude() {
		double min = Double.MAX_VALUE;
		for(Intersection i : intersections) {
			if(i.getLatitude() < min) min = i.getLatitude();
		}
		
		return min;
	}
	

	public Boolean equals(Plan p) {
		
		if (!p.getDepot().equals(this.getDepot())) {
			return false;
		}		
		
		if (intersections.size()!=p.intersections.size()) {
			return false;
		}
		for (int i=0; i<intersections.size(); ++i) {
			if (!p.intersections.get(i).equals(this.intersections.get(i))) {
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
