package model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import algorithm.dijkstra.Dijkstra;
import algorithm.tsp.TSP;
import observer.Observable;

/**
 * Class that contains the computed tour
 * 
 * @author 4IF Group H4144
 * @version 1.0 7 Dec 2021
 */
public class Tour extends Observable {
	List<CheckPoint> checkPoints;
	List<Path> pathList;
	LocalTime time;

	public Tour() {
		clearPath();
	}

	public void clearPath() {
		pathList = new ArrayList<Path>();
		checkPoints = new ArrayList<CheckPoint>();
		time = null;
	}
	
	
	/**
	 * Delete a specific checkpoint and reconnect paths
	 * 
	 * @param i index of the checkpoint to delete
	 * @param intersections list of intersections to reconnect
	 * @return the deleted checkpoint
	 */
	public CheckPoint deleteCheckPoint(int i, 
			List<Intersection> intersections) {
		CheckPoint c = this.checkPoints.remove(i);
		this.pathList.remove(i-1);
		this.pathList.remove(i-1);

		Path p = null;
		List<Integer> predecesorCheckpoint = Dijkstra.dijkstra(intersections, 
				this.checkPoints.get(i-1).getAddress());
		if(i == checkPoints.size()) {
			p = Dijkstra.createPath(intersections,predecesorCheckpoint, 
					this.checkPoints.get(i-1).getAddress().getIndex(), 
					this.checkPoints.get(0).getAddress().getIndex());
		} else {
			p = Dijkstra.createPath(intersections,predecesorCheckpoint,
					this.checkPoints.get(i-1).getAddress().getIndex(), 
					this.checkPoints.get(i).getAddress().getIndex());
		}
		this.pathList.add(i-1, p);
		return c;
	}
	
	/**
	 * Insert a checkpoint into the tour and connects path
	 * 
	 * @param i index where to insert the checkpoint
	 * @param c the checkpoint
	 * @param intersections list of intersections to reconnect
	 * @return paths are correct
	 */
	public boolean insertCheckPoint(int i, CheckPoint c, 
			List<Intersection> intersections) {
		this.checkPoints.add(i, c);
		this.pathList.remove(i-1);

		Path p1 = null;
		Path p2 = null;
		List<Integer> predCk1 = Dijkstra.dijkstra(intersections, 
				this.checkPoints.get(i-1).getAddress());
		List<Integer> predCk2 = Dijkstra.dijkstra(intersections, 
				c.getAddress());

		p1 = Dijkstra.createPath(intersections,predCk1,
				this.checkPoints.get(i-1).getAddress().getIndex(), 
				this.checkPoints.get(i).getAddress().getIndex());
		if(i+1 == checkPoints.size()) {
			p2 = Dijkstra.createPath(intersections,predCk2,
					this.checkPoints.get(i).getAddress().getIndex(), 
					this.checkPoints.get(0).getAddress().getIndex());
		} else {
			p2 = Dijkstra.createPath(intersections,predCk2,
					this.checkPoints.get(i).getAddress().getIndex(), 
					this.checkPoints.get(i+1).getAddress().getIndex());
		}
		this.pathList.add(i-1, p2);
		this.pathList.add(i-1, p1);
		return (p1!=null) && (p2!=null);
	}

	/**
	 * Feed the tour with TSP results
	 * 
	 * @param tsp
	 * @param nbVertices
	 * @param listPath
	 * @param check
	 * @return
	 */
	public LocalTime feedTour(TSP tsp, int nbVertices,
			List<? extends List<Path>> listPath,
			List<CheckPoint> check) {
		int previous = tsp.getSolution(0);
		int current;
		Path p;
		for (int i=1;i<nbVertices;++i) {
			current = tsp.getSolution(i);
			p = listPath.get(previous).get(current);
			addPath(p, check.get(previous));
			previous = current;
		}
		current = tsp.getSolution(0);
		p = listPath.get(previous).get(current);
		addPath(p, check.get(previous));
		return actualizeTime();
	}
	
	/**
	 * Actualize each time of the tour
	 */
	public LocalTime actualizeTime() {
		
		double mPerSec = 15000.0/3600.0;
		
		for(int i = 1; i<this.getCheckPoints().size();i++) {
			LocalTime t = this.getCheckPoints().get(i-1).getTime();
			t = t.plusSeconds(this.getCheckPoints().get(i-1).getDuration());
			t = t.plusNanos((long) (this.getPathList().get(i-1).getLength()/mPerSec)*1000000000);
			this.getCheckPoints().get(i).setTime(t);
		}
		
		int indexLast = this.getCheckPoints().size() -1;
		LocalTime t = this.getCheckPoints().get(indexLast).getTime();
		t = t.plusSeconds(this.getCheckPoints().get(indexLast).getDuration());
		t = t.plusNanos((long) (this.getPathList().get(indexLast).getLength()/mPerSec)*1000000000);
		this.setTime(t);
		
		return t;
	}
	
	public List<Path> getPathList() {
		return pathList;
	}
	
	public void addPath(Path p, CheckPoint c) {
		this.pathList.add(p);
		this.checkPoints.add(c);
	}

	public List<CheckPoint> getCheckPoints() {
		return checkPoints;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}
	
}
