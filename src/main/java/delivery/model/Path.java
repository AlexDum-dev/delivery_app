package delivery.model;

import java.util.List;

public class Path {
	private List<Segment> path;
	private double length;
	private Intersection destination;
	
	
	public Path(List<Segment> path1) {
		super();
		this.path = path1;
		int len = 0;
		for (Segment s : this.path) {
			len++;
		}
		this.length = len;
		int lastIndex = len-1;
		//System.out.println("lastIndex = "+lastIndex);
		//System.out.println("path.get(lastIndex) = "+path.get(0));
		this.destination = path.get(lastIndex).getDestination();
		
	}
	public List<Segment> getPath() {
		return path;
	}
	public void setPath(List<Segment> path) {
		int len = 0;
		for (Segment s : path) {
			len++;
		}
		this.path = path;
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
