package controller;

import java.awt.Component;
import java.util.List;

import algorithm.Dijkstra;
import delivery.model.CheckPoint;
import delivery.model.Intersection;
import delivery.model.Path;
import delivery.model.Plan;
import delivery.model.Request;
import delivery.model.Tour;
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
	public void loadMap(Controller c, Plan plan, Tour tour, Component frame, Window w) {
		CommonActions.loadMap(c, plan, tour, frame, w);
		/*w.setLoadRequestButton();
		w.setComputeTourButton();
		w.setComputeTourButton();*/
	}
	
	@Override
	public void loadRequest(Controller c, Plan plan, Tour tour, Component frame, Window w) {
		CommonActions.loadRequest(c, plan, tour, frame, w);
	}
	
}
