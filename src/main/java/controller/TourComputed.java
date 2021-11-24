package controller;

import java.awt.Component;

import delivery.model.Plan;
import delivery.model.Request;
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
	public void loadMap(Controller c, Plan plan, Tour tour, Component frame) {
		CommonActions.loadMap(c, plan, tour, frame);
	}
	
	@Override
	public void loadRequest(Controller c, Plan plan, Tour tour, Component frame) {
		CommonActions.loadRequest(c, plan, tour, frame);
	}
	
	@Override
	public void addRequest(Controller c, Plan plan, Tour tour) {
		System.out.println("Dans add request de TourComputed");
		CommonActions.addRequest(c, plan, tour);
	}
}
