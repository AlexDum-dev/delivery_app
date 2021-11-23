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
