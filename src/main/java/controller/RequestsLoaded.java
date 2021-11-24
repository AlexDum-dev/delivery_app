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
		c.setCurrentState(TourComputing.getInstance());
		ComputeTourThread thread = new ComputeTourThread(c, plan, tour);
		c.setThread(thread);
		thread.start();
	}
}
