package delivery.model;

import java.util.List;

public class Path {
	private List<Segment> path;
	private double length;
	private Intersection destination;
	private boolean active;
	
	
	
	public Path(List<Segment> path) {
		super();
		this.path = path;
		int len = 0;
		for (Segment s : this.path) {
			len += s.getLength();
		}
		this.length = len;
		//System.out.println("lastIndex = "+lastIndex);
		//System.out.println("path.get(lastIndex) = "+path.get(0));
		this.destination = path.get(path.size()-1).getDestination();
		
	}
	public List<Segment> getPath() {
		return path;
	}
	public double getLength() {
		return length;
	}
	public Intersection getDestination() {
		return destination;
	}
	public void setDestination(Intersection destination) {
		this.destination = destination;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean equals(Path p) {
		if (!this.getDestination().equals(p.getDestination())) {
			return false;
		}
		
		if (!(this.getPath().size() == p.getPath().size())) {
			return false;
		}
		for (int i = 0; i<this.getPath().size(); i++) {
			if (!this.getPath().get(i).equals(p.getPath().get(i))) {
				return false;
			}
		}		
		
		return true;
	}
}
