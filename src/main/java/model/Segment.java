package model;

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
	
	public boolean equals(Object s) {
		// If the object is compared with itself then return true 
        if (s == this) {
            return true;
        }
        
        if (!(s instanceof Segment)) {
            return false;
        }
         
        Segment tmpSegment = (Segment) s;
        
		if (tmpSegment.getDestination().equals(this.getDestination())
				&& tmpSegment.getOrigin().equals(this.getOrigin())
				&& tmpSegment.getLength() == this.getLength()
				&& tmpSegment.getName().equals(this.getName())){
			return true;
		}
		return false;
	}
}
