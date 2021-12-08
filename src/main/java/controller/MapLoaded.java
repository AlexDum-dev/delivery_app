package controller;

import model.Plan;
import model.Tour;
import view.Window;

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

	@Override
	public void loadMap(Controller c, Plan plan, Tour tour, Window w) {
		CommonActions.loadMap(c, plan, tour, w);
	}
	
	@Override
	public void loadRequest(Controller c, Plan plan, Tour tour, Window w) {
		CommonActions.loadRequest(c, plan, tour, w);
	}
	
}
