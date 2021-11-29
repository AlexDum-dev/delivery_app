package controller;

import java.awt.Component;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import algorithm.Dijkstra;
import algorithm.DijkstraResult;
import algorithm.tsp.DeliveryGraph;
import algorithm.tsp.TSP;
import algorithm.tsp.TSP1;
import delivery.model.CheckPoint;
import delivery.model.Path;
import delivery.model.Plan;
import delivery.model.Request;
import delivery.model.Tour;

/**
 * RequestsLoaded State
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
public class RequestsLoaded implements State {
	private static RequestsLoaded instance = null;
	
	private RequestsLoaded() {
		
	}
	
	protected static RequestsLoaded getInstance() {
		if (instance==null) {
			instance = new RequestsLoaded();
		}
		return instance;
	}

	@Override
	public void loadMap(Controller c, Plan plan, Tour tour, Component frame) {
		CommonActions.loadMap(c, plan, tour, frame);
	}
	
	@Override
	public void loadRequest(Controller c, Plan plan, Tour tour, Component frame) {
		CommonActions.loadRequest(c, plan, tour, frame);
	}
	
	@Override
	public void computeTour(Controller c, Plan plan, Tour tour) {
		//TODO: Compute the tour
		DijkstraResult result = Dijkstra.computePaths(plan.getIntersections(), 
				plan.getRequests(), plan.getDepot());
		
		List<? extends List<Path>> listPath = result.getPaths();
		List<CheckPoint> check = result.getCheckpoints();

		DeliveryGraph g = new DeliveryGraph(listPath, check);
		TSP tsp = new TSP1();
		tsp.searchSolution(20000, g);
		
		tour.clearPath();
		
		tour.actualizeTime(g.getNbVertices(), tsp, listPath, tsp.getSolution(0), check, tour);
		
		/*
		double mPerSec = 15000.0/3600.0;
		
		int previous = tsp.getSolution(0);
		int current;
		Path p;
		System.out.println("Vertices: ");
		for (int i=1;i<g.getNbVertices();++i) {
			System.out.println(previous);
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
		*/
		tour.notifyObservers();
		c.setCurrentState(TourComputed.getInstance());
	
	}
	
	@Override
	public void addRequest(Controller c, Plan plan, Tour tour) {
		CommonActions.addRequest(c, plan, tour);
	}
}
