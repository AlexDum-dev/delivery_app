package delivery.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
