package controller;

import java.time.LocalTime;
import java.util.List;

import algorithm.Dijkstra;
import algorithm.DijkstraResult;
import algorithm.tsp.DeliveryGraph;
import algorithm.tsp.TSP3;
import algorithm.tsp.TemplateTSP;
import delivery.model.CheckPoint;
import delivery.model.Path;
import delivery.model.Plan;
import delivery.model.Tour;
import observer.Observable;
import observer.Observer;

/**
 * Thread that computes the minimal tour
 * 
 * @author 4IF Group H4144
 * @version 1.0 24 Nov 2021
 */
public class ComputeTourThread extends Thread implements Observer {

	private Plan plan;
	private Tour tour;
	private DeliveryGraph g;
	private TemplateTSP tsp;
	private List<? extends List<Path>> listPath;
	private List<CheckPoint> check;
	private Controller controller;
	
	public ComputeTourThread(Controller c, Plan p, Tour t) {
		plan = p;
		tour = t;
		this.controller = c;
		tsp = new TSP3();
	}

	public void run(){
		DijkstraResult result = Dijkstra.computePaths(plan.getIntersections(), 
				plan.getRequests(), plan.getDepot());
		
		listPath = result.getPaths();
		check = result.getCheckpoints();
	
		g = new DeliveryGraph(listPath, check);
		tsp.addObserver(this);
		tsp.searchSolution(20000, g);
		controller.setCurrentState(TourComputed.getInstance());
	}

	@Override
	public void update(Observable observed, Object arg) {
		tour.clearPath();
		
		double mPerSec = 15000.0/3600.0;
		
		int previous = tsp.getSolution(0);
		int current;
		
		Path p;
		for (int i=1;i<g.getNbVertices();++i) {
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
		// TODO: Method
		p = listPath.get(previous).get(current);
		tour.addPath(p, check.get(previous));
		LocalTime t = check.get(previous).getTime();
		t = t.plusSeconds(check.get(previous).getDuration());
		t = t.plusNanos((long) (p.getLength()/mPerSec)*1000000000);
		tour.setTime(t);
		tour.notifyObservers();
	}

	public TemplateTSP getTsp() {
		return tsp;
	}
}
