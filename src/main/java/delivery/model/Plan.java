package delivery.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	private Depot depot;

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
	}
	
	public void addRequest(Request r) {
		requests.add(r);
	}

	public void addIntersection(Intersection i) {
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

	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
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
	
	
	/* Return a list of double array which contains 4 elements : x1, y1 and x2, y2. x1,y1  and x2,y2 are points that are connected
	 * @return : List<Double[]>
	 */
	
	static public List<Double[]> getAllLine(List<Segment> segments){
		List<Double[]> listLine = new ArrayList<Double[]>();
		for(Segment segment : segments) {
			Intersection origin = segment.getOrigin();
			Intersection destination = segment.getDestination();
			//System.out.println("*****************************************");
			//System.out.println("X1 : "+origin.getOrigin+ " Y1 "+pointOrigin.getY()+" X1 : "+pointDestination.getX()+ " Y1 "+pointDestination.getY());
			//Point pointOrigin = new Point((int)origin.getLatitude(),(int) origin.getLongitude());
			//Point pointDestination = new Point((int)destination.getLatitude(),(int) destination.getLongitude());
			Double[] pairPointOriginDestination = {origin.getLatitude(), origin.getLongitude(), destination.getLatitude(), destination.getLongitude()};
			//System.out.println("X1 : "+pointOrigin.getX()+ " Y1 "+pointOrigin.getY()+" X1 : "+pointDestination.getX()+ " Y1 "+pointDestination.getY());
			listLine.add(pairPointOriginDestination);
			
		}
		return listLine;
		
	}

	
}
