package controller;

import delivery.model.Plan;

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

	public void loadMap(Controller c, Plan plan) {
		CommonActions.loadMap(c, plan);
	}
	
	public void loadRequest(Controller c, Plan plan) {
		CommonActions.loadRequest(c, plan);
	}
	
	public void computeTour() {
		//TODO: Compute the tour
	}
}
