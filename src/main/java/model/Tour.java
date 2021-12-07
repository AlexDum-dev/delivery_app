package model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import algorithm.Dijkstra;
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
		if (this.path.size() > 0) {
			this.path.remove(this.path.size() - 1); //remove last path
		}		
	}
	
	/**
	 * Remove paths connected to checkpoint (at checkpointIndex) and reconnect them
	 * @param checkPointIndex
	 */
	public void deleteCheckPoint(int i, List<Intersection> intersections) {
		this.checkPoint.remove(i);
		this.path.remove(i-1);
		this.path.remove(i-1);

		Path p = null;
		List<Integer> predecesorCheckpoint = Dijkstra.dijkstra(intersections, 
				this.checkPoint.get(i-1).getAddress());
		if(i == checkPoint.size()) {
			p = Dijkstra.createPath(intersections,predecesorCheckpoint, 
					this.checkPoint.get(i-1).getAddress().getIndex(), 
					this.checkPoint.get(0).getAddress().getIndex());
		} else {
			p = Dijkstra.createPath(intersections,predecesorCheckpoint,
					this.checkPoint.get(i-1).getAddress().getIndex(), 
					this.checkPoint.get(i).getAddress().getIndex());
		}
		this.path.add(i-1, p);
	}
	
	/**
	 * Remove paths connected to checkpoint (at checkpointIndex) and reconnect them
	 * @param checkPointIndex
	 */
	public void insertCheckPoint(int i, CheckPoint c, List<Intersection> intersections) {
		this.checkPoint.add(i, c);
		this.path.remove(i-1);

		Path p1 = null;
		Path p2 = null;
		List<Integer> predCk1 = Dijkstra.dijkstra(intersections, 
				this.checkPoint.get(i-1).getAddress());
		List<Integer> predCk2 = Dijkstra.dijkstra(intersections, 
				c.getAddress());

		p1 = Dijkstra.createPath(intersections,predCk1,
				this.checkPoint.get(i-1).getAddress().getIndex(), 
				this.checkPoint.get(i).getAddress().getIndex());
		if(i+1 == checkPoint.size()) {
			p2 = Dijkstra.createPath(intersections,predCk2,
					this.checkPoint.get(i).getAddress().getIndex(), 
					this.checkPoint.get(0).getAddress().getIndex());
		} else {
			p2 = Dijkstra.createPath(intersections,predCk2,
					this.checkPoint.get(i).getAddress().getIndex(), 
					this.checkPoint.get(i+1).getAddress().getIndex());
		}
		this.path.add(i-1, p2);
		this.path.add(i-1, p1);
	}
	/**
	 * replace the path at index indexCheckPoint and at indexCheckPoint - 1
	 * with default path which has a length = - 1
	 * @param indexCheckpoint
	 */
	public void removeConnectedPath(int indexCheckpoint) {
		//remove index et index -1
		this.path.remove(indexCheckpoint);
		this.path.add(indexCheckpoint, new Path());
		//this.path.add(indexCheckpoint, new Path());
		this.path.remove(indexCheckpoint - 1);
		//this.path.add(indexCheckpoint - 1, new Path());
		//this.path.add(indexCheckpoint-1, new Path());
		/*
		for(int i = 0;i<this.path.size();i++) {
			if(this.path.get(i).getPath().get(0).getOrigin().getAddress().equals(addressCheckpoint) ||
					this.path.get(i).getDestination().getAddress().equals(addressCheckpoint)) {
				this.path.remove(i);
				this.path.add(i-1, new Path());
			}
		}
		*/
	}
	
	/**
	 * Find each time of the tour after the tsp is computed
	 * @param nbVertices
	 * @param tsp
	 * @param listPath
	 * @param previous
	 * @param check
	 * @param tour
	 * @return
	 */
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
	
	/**
	 * Actualize each time of the tour
	 */
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
	
	public Intersection getIntersectionCheckPointByIndex(int index) {
		if(index == this.checkPoint.size()) {
			return this.checkPoint.get(0).getAddress(); //return the depot
		}
		
		return this.checkPoint.get(index).getAddress();
	}
	
	public int getIndexInListCheckPoint(Intersection inter) {
		for(int i = 0; i < this.checkPoint.size();i++) {
			if(this.checkPoint.get(i).getAddress().equals(inter)) {
				return i;
			}
		}
		return -1;
	}
}
