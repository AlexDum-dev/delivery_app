package controller;

import delivery.model.Plan;
import delivery.model.Tour;

/**
 * TourComputed State
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
public class TourComputed implements State {
	private static TourComputed instance = null;
	
	private TourComputed() {
		
	}
	
	protected static TourComputed getInstance() {
		if (instance==null) {
			instance = new TourComputed();
		}
		return instance;
	}

	@Override
	public void loadMap(Controller c, Plan plan, Tour tour) {
		CommonActions.loadMap(c, plan, tour);
	}
	
	@Override
	public void loadRequest(Controller c, Plan plan, Tour tour) {
		CommonActions.loadRequest(c, plan, tour);
	}
}
