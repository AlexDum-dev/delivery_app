package delivery.model;

import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

/**
 * Represents an intersection of the XML File
 * 
 * @author 4IF Group H4144
 * @version 1.0 17 Nov 2021
 */
public class Segment {
	private Intersection origin;
	private Intersection destination;
	private double length;
	private String name;
	
	public Segment(Intersection origin, Intersection destination, 
			double length, String name) {
		this.origin = origin;
		this.destination = destination;
		this.length = length;
		this.name = name;
	}
	
	

	public Intersection getOrigin() {
		return origin;
	}

	public Intersection getDestination() {
		return destination;
	}

	public double getLength() {
		return length;
	}

	public String getName() {
		return name;
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
	
	public Boolean equals(Segment s) {
		if (s.getDestination().equals(this.getDestination())
				&& s.getOrigin().equals(this.getOrigin())
				&& s.getLength() == this.getLength()
				&& s.getName().equals(this.getName())){
			return true;
		}
		return false;
	}
}
