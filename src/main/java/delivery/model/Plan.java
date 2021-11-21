package delivery.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
	
public static double getMaxLatitude(Map<String,Intersection> listInter) {
		
		double max = 0;
		Set<String> setInterId = listInter.keySet();
		for(String id : setInterId) {
			if(listInter.get(id).getLatitude() > max) max = listInter.get(id).getLatitude();
		}
		
		return max;
	}
	
	public static double getMaxLongitude(Map<String,Intersection> listInter) {
		
		double max = 0;
		Set<String> setInterId = listInter.keySet();
		for(String id : setInterId) {
			if(listInter.get(id).getLongitude() > max) max = listInter.get(id).getLongitude();
		}
		
		return max;
	}
	
	public static double getMinLongitude(Map<String,Intersection> listInter) {
		
		double min = Double.MAX_VALUE;
		Set<String> setInterId = listInter.keySet();
		for(String id : setInterId) {
			if(listInter.get(id).getLongitude() < min) min = listInter.get(id).getLongitude();
		}
		
		return min;
	}
	
	public static double getMinLatitude(Map<String,Intersection> listInter) {
		
		double min = Double.MAX_VALUE;
		Set<String> setInterId = listInter.keySet();
		for(String id : setInterId) {
			if(listInter.get(id).getLatitude() < min) min = listInter.get(id).getLatitude();
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
	
	static public Object[][] displayRequest(List<Request> requests) {
		
		Object[][] tabRequest = new Object[requests.size()][4];
						
		String deliveryTextGrid;
		String pickupTextGrid;
		String pickupTimeTextGrid;
		String deliveryTimeTextGrid;
	
		for(int i = 0 ; i<requests.size();i++) {
			pickupTimeTextGrid = "-";
			deliveryTimeTextGrid = "-";
			deliveryTextGrid = requests.get(i).getDelivery().getAddress().getId();
			pickupTextGrid = requests.get(i).getPickup().getAddress().getId();
			if(requests.get(i).getPickup().getTime() != null || requests.get(i).getDelivery().getTime() != null) {
				pickupTimeTextGrid = requests.get(i).getPickup().getTime().toString();
				deliveryTimeTextGrid = requests.get(i).getDelivery().getTime().toString();
			}
			
			
			String[] line = {deliveryTextGrid, pickupTextGrid, pickupTimeTextGrid, deliveryTimeTextGrid};
			tabRequest[i] =  line; 
			
		}
			
		return tabRequest;
	}

	
}
