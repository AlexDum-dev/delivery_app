package delivery.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import observer.Observable;

public class Tour extends Observable {
	List<Path> path;
	LocalTime time;

	public Tour() {
		path = new ArrayList<Path>();
	}

	public List<Path> getPath() {
		return path;
	}

	public void clearPath() {
		this.path = new ArrayList<Path>();
	}
	
	public void addPath(Path p) {
		this.path.add(p);
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}
}
