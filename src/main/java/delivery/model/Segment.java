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
