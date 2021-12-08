package model;

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
	
	public Intersection getNearestIntersection(double lat, double lon) {
		Intersection nearest = null;
		double nearestRange = Double.MAX_VALUE;
		for (Intersection i : intersections) {
			double range = Math.pow(lat-i.getLatitude(), 2)+Math.pow(lon-i.getLongitude(), 2);
			if(range<nearestRange){
				nearestRange = range;
				nearest = i;
			}
		}
		return nearest;
	}

	/**
	 * Adds a request to the list
	 * @param r
	 */
	public void addRequest(Request r) {
		r.setIndex(requests.size());
		requests.add(r);
	}

	/**
	 * actualize the index of requests by assigning the index to the position
	 * in the list
	 */
	public void actualizeRequestsIndex() {
		for(int i = 0; i<this.requests.size();i++){
			//set index actualizes the index for the two checkpoints as well
			this.requests.get(i).setIndex(i); 
		}
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
}
