package delivery.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import algorithm.tsp.TSP;
import observer.Observable;

public class Tour extends Observable {
	List<CheckPoint> checkPoint;
	List<Path> path;
	LocalTime time;

	public Tour() {
		path = new ArrayList<Path>();
		checkPoint = new ArrayList<CheckPoint>();
		time = null;
	}

	public void clearPath() {
		this.path = new ArrayList<Path>();
		checkPoint = new ArrayList<CheckPoint>();
		time = null;
	}
	
	/*
	 * Add a request -> add a path at the end of the list.
	 * 
	 */
	public void removeLastPath() {
		//determine the path between the last checkpoint and the pickup and between the pickup and the delivery
		//then between the delivery and the pickup
		//erase the last path
		
		this.path.remove(this.path.size() - 1); //remove last path
	}
	
	public LocalTime actualizeTime(int nbVertices, TSP tsp, List<? extends List<Path>> listPath, int previous,
			List<CheckPoint> check, Tour tour) {
		int current;
		Path p;
		double mPerSec = 15000.0/3600.0;
		
		for (int i=1;i<nbVertices;++i) {
			//System.out.println(previous);
			current = tsp.getSolution(i);
			// TODO: Method
			p = listPath.get(previous).get(current);
			tour.addPath(p, check.get(previous));
			LocalTime t = check.get(previous).getTime();
			t = t.plusSeconds(check.get(previous).getDuration());
			t = t.plusNanos((long) (p.getLength()/mPerSec)*1000000000);
			check.get(current).setTime(t);
			previous = current;
		}
		
		System.out.println(previous);
		current = tsp.getSolution(0);
		// TODO: Method to compute the time
		p = listPath.get(previous).get(current);
		tour.addPath(p, check.get(previous));
		LocalTime t = check.get(previous).getTime();
		t = t.plusSeconds(check.get(previous).getDuration());
		t = t.plusNanos((long) (p.getLength()/mPerSec)*1000000000);
		tour.setTime(t);
		
		return t;
	}
	
	public void actualizeTime() {
		
		double mPerSec = 15000.0/3600.0;
		
		for(int i = 1; i<this.getCheckPoint().size();i++) {
			LocalTime t = this.getCheckPoint().get(i-1).getTime();
			t = t.plusSeconds(this.getCheckPoint().get(i-1).getDuration());
			t = t.plusNanos((long) (this.getPath().get(i-1).getLength()/mPerSec)*1000000000);
			this.getCheckPoint().get(i).setTime(t);
		}
		
		int indexLast = this.getCheckPoint().size() -1;
		LocalTime t = this.getCheckPoint().get(indexLast).getTime();
		t = t.plusSeconds(this.getCheckPoint().get(indexLast).getDuration());
		t = t.plusNanos((long) (this.getPath().get(indexLast).getLength()/mPerSec)*1000000000);
		this.setTime(t);
	}
	
	public CheckPoint removeLastCheckPoint() {
		CheckPoint checkpoint = this.checkPoint.get(this.checkPoint.size() - 1);
		this.checkPoint.remove(this.checkPoint.size() - 1);
		return checkpoint;	
	}
	
	public Intersection getLastCheckPoint() {
		Path lastPath = this.getPath().get(this.getPath().size() - 1);
		Intersection newLastCheckPoint = lastPath.getPath().get(lastPath.getPath().size() - 1).getDestination();
		
		return newLastCheckPoint;
	}
	
	public List<Path> getPath() {
		return path;
	}
	
	public void addPath(Path p, CheckPoint c) {
		this.path.add(p);
		this.checkPoint.add(c);
	}

	public List<CheckPoint> getCheckPoint() {
		return checkPoint;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}
}
