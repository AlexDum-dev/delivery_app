package controller;

import java.util.List;

import algorithm.Dijkstra;
import algorithm.DijkstraResult;
import algorithm.tsp.DeliveryGraph;
import algorithm.tsp.TSP3;
import algorithm.tsp.TemplateTSP;
import model.CheckPoint;
import model.Path;
import model.Plan;
import model.Tour;
import observer.Observable;
import observer.Observer;
import view.Window;

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
	private Window window;
	
	public ComputeTourThread(Controller c, Plan p, Tour t, Window w) {
		plan = p;
		tour = t;
		this.controller = c;
		tsp = new TSP3();
		this.window=w;
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
		window.setStopComputingButtonFalse();
		window.setLoadMapButtonTrue();
		window.setLoadRequestButtonTrue();
		window.setAddRequestTrue();
		window.setComputeTourButtonFalse();
		window.setMessageVisible(window.getMessage1(), false);
		window.setMessageVisible(window.getMessage2(), true);
		window.setDeleteButton(true);
		window.setModifyButton(true);
	}

	@Override
	public void update(Observable observed, Object arg) {
		tour.clearPath();
		
		tour.actualizeTime(g.getNbVertices(), tsp, listPath, tsp.getSolution(0), check, tour);
		
		tour.notifyObservers();
	}

	public TemplateTSP getTsp() {
		return tsp;
	}
}
