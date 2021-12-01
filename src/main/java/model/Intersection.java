package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an intersection of the XML File
 * 
 * @author 4IF Group H4144
 * @version 1.0 17 Nov 2021
 */
public class Intersection {
	private String id;
	private double latitude;
	private double longitude;
	private List<Segment> segments;
	private int index;
	
	public Intersection(String id, double latitude, double longitude) {
		this.id = id;
		this.index = -1;
		this.latitude = latitude;
		this.longitude = longitude;
		this.segments = new ArrayList<Segment>();
	}

	public String getId() {
		return id;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public List<Segment> getSegments() {
		return segments;
	}

	public String getAddress() {
		if (segments.isEmpty()) {
			return "";
		}
		return segments.get(0).getName();
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public void addSegment(Segment segment) {
		this.segments.add(segment);
	}

	public boolean equals(Object i) {
		// If the object is compared with itself then return true 
        if (i == this) {
            return true;
        }
        
        if (!(i instanceof Intersection)) {
            return false;
        }
         
        Intersection tmpIntersection = (Intersection) i;
        
        if (tmpIntersection.getId().equals(this.getId())
        		&& tmpIntersection.getLatitude() == this.getLatitude()
        		&& tmpIntersection.getLongitude() == this.getLongitude()
        		&& tmpIntersection.getIndex() == this.getIndex()) {
        	return true;
        }
		return false;
	}
}
