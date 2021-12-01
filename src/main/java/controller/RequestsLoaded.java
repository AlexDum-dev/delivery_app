package controller;

import java.awt.Component;

import delivery.model.Plan;
import delivery.model.Tour;
import view.Window;


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
	public void loadMap(Controller c, Plan plan, Tour tour, Component frame, Window w) {
		CommonActions.loadMap(c, plan, tour, frame, w);
	}
	
	@Override
	public void loadRequest(Controller c, Plan plan, Tour tour, Component frame, Window w) {
		CommonActions.loadRequest(c, plan, tour, frame, w);
	}
	
	@Override
	public void computeTour(Controller c, Plan plan, Tour tour, Window w) {
		w.setLoadMapButtonFalse();
		w.setLoadRequestButtonFalse();
		w.setStopComputingButtonTrue();
		w.setComputeTourButtonFalse();
		w.setMessageVisible(w.getMessage1(), true);
		w.setMessageVisible(w.getMessage2(), false);
		c.setCurrentState(TourComputing.getInstance());
		ComputeTourThread thread = new ComputeTourThread(c, plan, tour, w);
		c.setThread(thread);
		thread.start();
	}
	
}
