package controller;

import delivery.model.Plan;

/**
 * MapLoaded State
 * 
 * @author 4IF Group H4144
 * @version 1.0 22 Nov 2021
 */
public class MapLoaded implements State {
	private static MapLoaded instance = null;
	
	private MapLoaded() {
		
	}
	
	protected static MapLoaded getInstance() {
		if (instance==null) {
			instance = new MapLoaded();
		}
		return instance;
	}

	public void loadMap(Controller c, Plan plan) {
		CommonActions.loadMap(c, plan);
	}
	
	public void loadRequest(Controller c, Plan plan) {
		CommonActions.loadRequest(c, plan);
	}
}
