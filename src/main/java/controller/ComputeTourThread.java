package controller;

import java.util.List;

import algorithm.dijkstra.Dijkstra;
import algorithm.dijkstra.DijkstraResult;
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
		
		if (result==null) {
			window.setStopComputingButtonEnabled(false);
			window.setComputeTourButtonEnabled(false);
			window.setLoadMapButtonEnabled(true);
			window.setLoadRequestButtonEnabled(true);
			window.getMessage().setText("Can't compute tour.");
			controller.setCurrentState(RequestsLoaded.getInstance());
		}
		else {
			listPath = result.getPaths();
			check = result.getCheckpoints();
		
			g = new DeliveryGraph(listPath, check);
			tsp.addObserver(this);
			tsp.searchSolution(20000, g);
			controller.setCurrentState(TourComputed.getInstance());
			window.setDoButtonsEnabled(true);
			window.setStopComputingButtonEnabled(false);
			window.setLoadMapButtonEnabled(true);
			window.setLoadRequestButtonEnabled(true);
			window.setAddRequestEnabled(true);
			window.setComputeTourButtonEnabled(false);
			window.setDeleteButtonEnabled(true);
			window.setModifyButtonsEnabled(true);
			window.getMessage().setText("Tour computed!");
		}
	}

	@Override
	public void update(Observable observed, Object arg) {
		tour.clearPath();
		
		tour.feedTour(tsp, g.getNbVertices(), listPath, check);
		
		tour.notifyObservers();
	}

	public TemplateTSP getTsp() {
		return tsp;
	}
}
