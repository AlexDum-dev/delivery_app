package delivery.model;

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
	
	public Intersection(String id, double latitude, double longitude) {
		this.id = id;
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

	public void addSegment(Segment segment) {
		this.segments.add(segment);
	}
	
}
