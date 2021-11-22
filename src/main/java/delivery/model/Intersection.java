package delivery.model;

import java.util.Map;
import java.util.Set;

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
	
	public Intersection(String id, double latitude, double longitude) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
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
	
}
